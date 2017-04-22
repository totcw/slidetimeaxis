package com.lyf.bookreader.base;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.betterda.mylibrary.LoadingPager;
import com.lyf.bookreader.utils.RxManager;
import com.lyf.bookreader.utils.UiUtils;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/12/5.
 */

public abstract class BaseFragment <P extends IPresenter> extends Fragment implements IView {
    private Activity mActivity;
    protected P mPresenter;
    protected RxManager mRxManager;
    private PopupWindow popupWindow;
    private AlertDialog.Builder builder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity; //在这里获取到acitiviy,防止内存不够,activity被销毁,调用getactivity方法时返回null
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = initView(inflater);
        ButterKnife.bind(this, view);
        mRxManager = new RxManager();
        mPresenter = onLoadPresenter();
        if(getPresenter() != null) {
            getPresenter().attachView(this);
        }
        return view;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        if(getPresenter() != null) {
            //开始presenter的逻辑
            getPresenter().start();
        }
        initData();
        initListener();


    }


    protected abstract P onLoadPresenter();

    /**
     * 设置监听
     */
    public void initListener() {

    }


    /**
     * 子类必须实现此方法, 返回一个View对象, 作为当前Fragment的布局来展示.
     *
     * @return
     */
    public abstract View initView(LayoutInflater inflater);

    /**
     * 如果子类需要初始化自己的数据, 把此方法给覆盖.
     */
    public void initData() {

    }

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public Activity getmActivity() {
        return mActivity;
    }


    public void log(String msg) {
        Log.i("BaseFragment", msg);
    }


    @Override
    public RxManager getRxManager() {
        return mRxManager;
    }


    @Override
    public LoadingPager getLoadPager() {
        return null;
    }



    /**
     * 初始化并显示PopupWindow
     *
     * @param view     要显示的界面
     * @param showView 显示在哪个控件下面
     * @param x 宽度
     */
    public void setUpPopupWindow(View view, View showView, int x, int y) {
        // 如果activity不在运行 就返回
        if (getActivity().isFinishing()) {
            return;
        }
        if (null == showView) {
            popupWindow = new PopupWindow(view, -1, -2);
        } else {
            if (x != 0 && y != 0) {

                popupWindow = new PopupWindow(view, x, y);
            } else {
                popupWindow = new PopupWindow(view, -1, -1);
            }
        }

        // 设置点到外面可以取消,下面这2句要一起
        if (null == showView) {
            popupWindow.setOutsideTouchable(true);
        }
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //设置为true 会拦截事件,pop外部的控件无法获取到事件
        if (null == showView) {
            popupWindow.setFocusable(true);
        } else {
            popupWindow.setFocusable(true);
        }
        if (null == showView) {
            UiUtils.backgroundAlpha(0.7f, getmActivity());

        }
        //设置可以触摸
        popupWindow.setTouchable(true);

        if (popupWindow != null) {
            if (!popupWindow.isShowing()) {
                if (null == showView) {
                    //设置动画
                   // popupWindow.setAnimationStyle(R.style.popwin_anim_style);
                    popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                } else {
                  //  popupWindow.setAnimationStyle(R.style.popupwindow_anim);
                    popupWindow.showAsDropDown(showView);
                }

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
    public void onDestroyView() {
        if (getPresenter() != null) {
            //解除presenter和view的关联
            getPresenter().detachView();
            //调用presenter的销毁方法
            getPresenter().destroy();
        }
        mRxManager.clear();
        closePopupWindow();
        super.onDestroyView();
    }
}
