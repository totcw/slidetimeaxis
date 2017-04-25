package com.lyf.bookreader.readbook;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyf.bookreader.R;
import com.lyf.bookreader.base.BaseActivity;
import com.lyf.bookreader.readbook.contract.BookReadContract;
import com.lyf.bookreader.readbook.presenter.BookReadPresenterImpl;
import com.lyf.bookreader.utils.CacheUtils;
import com.lyf.bookreader.utils.Constants;
import com.lyf.bookreader.view.ReadView;

import butterknife.BindView;
import butterknife.ButterKnife;
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

public class BookReadActivity extends BaseActivity<BookReadContract.Presenter> implements BookReadContract.View {

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
        showReadBar(mLinearBookreadTop,mLinearBookreadBottom);
    }


    @OnClick({R.id.iv_bookread_back, R.id.tv_bookread_source, R.id.tv_bookread_mode, R.id.tv_bookread_setting, R.id.tv_bookread_download, R.id.tv_bookread_directory})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bookread_back:
                break;
            case R.id.tv_bookread_source:
                break;
            case R.id.tv_bookread_mode://模式选择
                boolean isNight = !CacheUtils.getBoolean(getmActivity(), Constants.ISNIGHT, false);
                getPresenter().changMode(isNight);
                break;
            case R.id.tv_bookread_setting:
                break;
            case R.id.tv_bookread_download:
                getPresenter().download();
                break;
            case R.id.tv_bookread_directory:
                break;
        }
    }


    /**
     *@author : lyf
     *@email:totcw@qq.com
     *@创建日期： 2017/4/24
     *@功能说明：显示阅读状态栏
     *@param
     *@return
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
}
