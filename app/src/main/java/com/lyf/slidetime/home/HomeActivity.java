package com.lyf.slidetime.home;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.betterda.mylibrary.view.IndicatorView;

import com.lyf.slidetime.R;
import com.lyf.slidetime.base.BaseActivity;
import com.lyf.slidetime.home.contract.HomeContract;
import com.lyf.slidetime.home.presenter.HomePresenterImpl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页
 */

public class HomeActivity extends BaseActivity<HomeContract.Presenter> implements HomeContract.View {

    @BindView(R.id.idv_activity_main_shouye)
    IndicatorView mIdvShouye;
    @BindView(R.id.idv_activity_main_find)
    IndicatorView mIdvFind;
    @BindView(R.id.idv_activity_main_my)
    IndicatorView mIdvMy;
    @BindView(R.id.linear_activity_home)
    LinearLayout mLinearActivityMain;
    @BindView(R.id.frame_activity_home)
    FrameLayout mFrameActivityMain;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_home);
    }

    @Override
    protected HomeContract.Presenter onLoadPresenter() {
        return new HomePresenterImpl();
    }

    @Override
    public void init() {
        super.init();
        initIdv();
        getPresenter().start();

    }


    @OnClick({R.id.idv_activity_main_shouye, R.id.idv_activity_main_find, R.id.idv_activity_main_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.idv_activity_main_shouye:
                switchTo(mIdvShouye);
                getPresenter().switchToFragment(R.id.idv_activity_main_shouye);
                break;
            case R.id.idv_activity_main_find:
                switchTo(mIdvFind);
                getPresenter().switchToFragment(R.id.idv_activity_main_find);
                break;
            case R.id.idv_activity_main_my:
                switchTo(mIdvMy);
                getPresenter().switchToFragment(R.id.idv_activity_main_my);
                break;
        }
    }

    /**
     * 初始化底部导航
     */
    private void initIdv() {
        mIdvShouye.setIvBackground(R.mipmap.activity_main_shouye_normal,R.mipmap.activity_main_shouye_pressed);
        mIdvFind.setIvBackground(R.mipmap.activity_main_find_normal,R.mipmap.activity_main_find_pressed);
        mIdvMy.setIvBackground(R.mipmap.activity_main_my_nomal,R.mipmap.activity_main_my_pressed);

        mIdvShouye.setLineBackground(getResources().getColor(R.color.shouye_lv_tv),getResources().getColor(R.color.bg));
        mIdvFind.setLineBackground(getResources().getColor(R.color.shouye_lv_tv),getResources().getColor(R.color.bg));
        mIdvMy.setLineBackground(getResources().getColor(R.color.shouye_lv_tv),getResources().getColor(R.color.bg));

        mIdvShouye.setTitle("书架");
        mIdvFind.setTitle("书城");
        mIdvMy.setTitle("我的");

        mIdvShouye.setTabSelected(true);
    }

    /**
     * 设置底部导航的选中状态
     * @param idv
     */
    public void switchTo(IndicatorView idv) {

        if (null != mIdvShouye  && null != mIdvFind && null != idv && mIdvMy != null) {

            mIdvShouye.setTabSelected(false);
            mIdvFind.setTabSelected(false);
            mIdvMy.setTabSelected(false);
            idv.setTabSelected(true);

        }

    }

    public void toFind() {
        switchTo(mIdvFind);
        getPresenter().switchToFragment(R.id.idv_activity_main_find);
    }
}
