package com.lyf.bookreader.personalcenter;

import android.view.LayoutInflater;
import android.view.View;


import com.lyf.bookreader.R;
import com.lyf.bookreader.base.BaseFragment;
import com.lyf.bookreader.personalcenter.contract.PersonalCenterContract;
import com.lyf.bookreader.personalcenter.presenter.MyPresenterImpl;

/**
 * Created by Administrator on 2016/12/8.
 * 个人中心
 */

public class PersonalCenterFragment extends BaseFragment<PersonalCenterContract.Presenter> implements PersonalCenterContract.View {


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_my, null);
    }

    @Override
    protected PersonalCenterContract.Presenter onLoadPresenter() {
        return new MyPresenterImpl();
    }



    @Override
    public void initData() {
        super.initData();

    }


}
