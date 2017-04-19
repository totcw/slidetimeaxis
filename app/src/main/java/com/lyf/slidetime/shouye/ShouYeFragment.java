package com.lyf.slidetime.shouye;

import android.view.LayoutInflater;
import android.view.View;


import com.lyf.slidetime.R;
import com.lyf.slidetime.base.BaseFragment;
import com.lyf.slidetime.shouye.contract.ShouYeContract;
import com.lyf.slidetime.shouye.presenter.ShouYePresenterImpl;

/**
 * Created by Administrator on 2016/12/8.
 */

public class ShouYeFragment extends BaseFragment<ShouYeContract.Presenter> implements ShouYeContract.View {



    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_shouye, null);
    }

    @Override
    protected ShouYeContract.Presenter onLoadPresenter() {
        return new ShouYePresenterImpl();
    }

    @Override
    public void initData() {
        super.initData();

    }

}
