package com.lyf.slidetime.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.lyf.slidetime.utils.RxManager;
import com.lyf.slidetime.utils.UiUtils;

import butterknife.ButterKnife;


/**
 * 基类
 * Created by Administrator on 2016/12/2.
 */

public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IView {
    private String[] REQUEST_PERMISSIONS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE};
    private static final int REQUEST_PERMISSION_CODE_TAKE_PIC = 9; //权限的请求码
    protected P mPresenter;
    protected RxManager mRxManager;
    private PopupWindow popupWindow;
    private AlertDialog.Builder builder;
    private boolean isDismiss;//token 失效对话框 是否已经显示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRxManager = new RxManager();

        mPresenter = onLoadPresenter();
        if (getPresenter() != null) {
            getPresenter().attachView(this);
        }
        initView();
        ButterKnife.bind(this);
        initListener();
        init();
        if (getPresenter() != null) {
            //开始presenter的逻辑
            getPresenter().start();
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        //统一检查权限
 /*       PermissionUtil.checkPermission(getmActivity(), REQUEST_PERMISSIONS, new PermissionUtil.permissionInterface() {
            @Override
            public void success() {

            }

            @Override
            public void fail(List<String> permissions) {
                //没有权限就回到欢迎页面
                UiUtils.startIntent(getmActivity(), WelcomeActivity.class);

            }
        });*/
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


}
