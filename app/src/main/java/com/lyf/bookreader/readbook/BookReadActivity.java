package com.lyf.bookreader.readbook;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lyf.bookreader.R;
import com.lyf.bookreader.base.BaseActivity;
import com.lyf.bookreader.readbook.contract.BookReadContract;
import com.lyf.bookreader.readbook.presenter.BookReadPresenterImpl;
import com.lyf.bookreader.utils.CacheUtils;
import com.lyf.bookreader.utils.Constants;
import com.lyf.bookreader.utils.UiUtils;
import com.lyf.bookreader.view.ReadView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/4/20
 * @功能说明： 阅读 界面
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class BookReadActivity extends BaseActivity<BookReadContract.Presenter> implements BookReadContract.View, View.OnClickListener {

    @BindView(R.id.readview)
    ReadView mReadView;//阅读控件
    @BindView(R.id.linear_bookread_top)
    LinearLayout mLinearBookreadTop;
    @BindView(R.id.linear_bookread_bottom)
    LinearLayout mLinearBookreadBottom;
    @BindView(R.id.tv_bookread_mode)
    TextView mTvBookreadMode;

    @Override
    protected BookReadContract.Presenter onLoadPresenter() {
        return new BookReadPresenterImpl();

    }


    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_bookread);

    }


    @Override
    public ReadView getReadView() {
        return mReadView;
    }

    @Override
    public TextView getBookReadMode() {
        return mTvBookreadMode;
    }

    @Override
    public void showReadBar() {
        showReadBar(mLinearBookreadTop, mLinearBookreadBottom);
    }


    @OnClick({R.id.iv_bookread_back, R.id.tv_bookread_source, R.id.tv_bookread_mode, R.id.tv_bookread_setting, R.id.tv_bookread_download, R.id.tv_bookread_directory})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bookread_back: //退出界面,保存进度
                getPresenter().saveProgress();
                finish();
                break;
            case R.id.tv_bookread_source:
                UiUtils.showToast(getmActivity(), "功能暂未开通!");
                break;
            case R.id.tv_bookread_mode://模式选择

                boolean isNight = !CacheUtils.getBoolean(getmActivity(), Constants.ISNIGHT, false);
                getPresenter().changMode(isNight);
                break;
            case R.id.tv_bookread_setting:
                showFontView();
                break;
            case R.id.tv_bookread_download://缓存
                setDownloadView();
                break;
            case R.id.tv_bookread_directory://获取目录
                getPresenter().getDirectory();
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pp_download_cancel://取消
                closePopupWindow();
                break;
            case R.id.tv_pp_download_cachefive://缓存后面50章节
                getPresenter().download(1);
                closePopupWindow();
                break;
            case R.id.tv_pp_download_cacheleave://缓存剩余章节
                getPresenter().download(2);
                closePopupWindow();
                break;
            case R.id.tv_pp_download_cacheall://缓存全本
                getPresenter().download(0);
                closePopupWindow();
                break;
            case R.id.btn_font_del://减小字体
                getPresenter().delFont();
                break;
            case R.id.btn_font_add://增大字体
                getPresenter().addFont();
                break;
        }
    }

    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/6/28
     * @功能说明： 设置字体大小的界面
     */
    private void showFontView() {
        View view = View.inflate(getmActivity(), R.layout.popupwindow_font, null);
        Button mBtnDel = (Button) view.findViewById(R.id.btn_font_del);
        Button mBtnAdd = (Button) view.findViewById(R.id.btn_font_add);
        mBtnAdd.setOnClickListener(this);
        mBtnDel.setOnClickListener(this);
        // 如果activity不在运行 就返回
        if (this.isFinishing()) {
            return;
        }

       PopupWindow  popupWindow = new PopupWindow(view, -2, -2);
        // 设置点到外面可以取消,下面这2句要一起
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置为true 会拦截事件,pop外部的控件无法获取到事件
        popupWindow.setFocusable(true);

        //设置可以触摸
        popupWindow.setTouchable(true);
        popupWindow.update();
        if (popupWindow != null) {
            if (!popupWindow.isShowing()) {
                //设置动画
                popupWindow.setAnimationStyle(R.style.popwin_anim_style_scale);
                popupWindow.showAtLocation(mLinearBookreadBottom, Gravity.BOTTOM, 0, mLinearBookreadBottom.getMeasuredHeight());

            }
        }



    }

    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/5/2
     * @功能说明：设置缓存的popouwindow
     */
    private void setDownloadView() {
        View view = View.inflate(getmActivity(), R.layout.pp_download, null);
        TextView mTvCancel = (TextView) view.findViewById(R.id.tv_pp_download_cancel);
        TextView mTvCacheFive = (TextView) view.findViewById(R.id.tv_pp_download_cachefive);
        TextView mTvCacheLeave = (TextView) view.findViewById(R.id.tv_pp_download_cacheleave);
        TextView mTvCacheAll = (TextView) view.findViewById(R.id.tv_pp_download_cacheall);
        mTvCancel.setOnClickListener(this);
        mTvCacheFive.setOnClickListener(this);
        mTvCacheLeave.setOnClickListener(this);
        mTvCacheAll.setOnClickListener(this);
        setUpPopupWindow(view);
    }


    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/24
     * @功能说明：显示阅读状态栏
     */
    public void showReadBar(View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    if (view.getVisibility() == View.GONE) {
                        view.setVisibility(View.VISIBLE);
                    } else {
                        view.setVisibility(View.GONE);
                    }
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getPresenter().onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getPresenter().saveProgress();
        super.onSaveInstanceState(outState);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        UiUtils.backgroundAlpha(1.0f, getmActivity());
    }

    @Override
    public void onBackPressed() {
        getPresenter().saveProgress();
        super.onBackPressed();
    }


}
