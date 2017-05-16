package com.lyf.bookreader.base;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.betterda.mylibrary.LoadingPager;
import com.lyf.bookreader.R;
import com.lyf.bookreader.utils.DialogUtils;
import com.lyf.bookreader.utils.RxManager;
import com.lyf.bookreader.utils.UiUtils;

import butterknife.ButterKnife;


/**
 * 基类
 * Created by Administrato r on 2016/12/2.
 */

public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IView {

    protected P mPresenter;
    protected RxManager mRxManager;
    private PopupWindow popupWindow;
    private AlertDialog.Builder builder;
    protected Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mRxManager = new RxManager();

        mPresenter = onLoadPresenter();
        if (getPresenter() != null) {
            getPresenter().attachView(this);
        }
        initView();
        ButterKnife.bind(this);
        if (getPresenter() != null) {
            //开始presenter的逻辑
            getPresenter().start();
        }
        initListener();
        init();
    }


    /**
     * 处理业务逻辑
     */
    public void init() {

    }

    /**
     * 设置监听
     */
    public void initListener() {

    }

    /**
     * 初始化view
     */
    public void initView() {

    }


    public P getPresenter() {
        return mPresenter;
    }


    /**
     * 加载presenter
     *
     * @return
     */
    protected abstract P onLoadPresenter();

    @Override
    public LoadingPager getLoadPager() {
        return null;
    }

    /**
     * 关闭activity的方法
     */
    public void back() {
        finish();
        UiUtils.setOverdepengingOut(getmActivity());

    }


    public Activity getmActivity() {
        return this;
    }

    public Context getContext() {
        return this;
    }

    public void log(String msg) {
        Log.i("BaseActivity", msg);
    }

    @Override
    public RxManager getRxManager() {
        return mRxManager;
    }


    /**
     * 初始化并显示PopupWindow
     *
     * @param view 要显示的界面
     */
    public void setUpPopupWindow(View view) {
        // 如果activity不在运行 就返回
        if (this.isFinishing()) {
            return;
        }

        popupWindow = new PopupWindow(view, -1, -2);
        // 设置点到外面可以取消,下面这2句要一起
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置为true 会拦截事件,pop外部的控件无法获取到事件
        popupWindow.setFocusable(true);

        UiUtils.backgroundAlpha(0.5f, getmActivity());
        //设置可以触摸
        popupWindow.setTouchable(true);

        if (popupWindow != null) {
            if (!popupWindow.isShowing()) {
                //设置动画
                popupWindow.setAnimationStyle(R.style.popwin_anim_style);
                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);


            }
        }
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {

                dismiss();
                popupWindow = null;
            }
        });


    }

    /**
     * popupwindow消失回调方法
     */
    public void dismiss() {

    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    /**
     * 关闭popupwindow
     */
    public void closePopupWindow() {
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        UiUtils.setOverdepengingOut(this);
    }

    @Override
    protected void onDestroy() {
        if (getPresenter() != null) {
            //解除presenter和view的关联
            getPresenter().detachView();
            //调用presenter的销毁方法
            getPresenter().destroy();
        }
        mRxManager.clear();
        closePopupWindow();

        super.onDestroy();
    }

    private Dialog mProgressDialog;

    protected Dialog getLoadingDialog(String tips) {
        if (mProgressDialog == null) {
            return DialogUtils.createProgressDialog(this, tips);
        }
        return mProgressDialog;
    }

    protected void showLoadingDialog(String tips) {
        if (mProgressDialog == null) {
            mProgressDialog = DialogUtils.createProgressDialog(this, tips);
        }
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    protected void dismissLoadingDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
