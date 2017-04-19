package com.lyf.slidetime.my;

import android.view.LayoutInflater;
import android.view.View;


import com.lyf.slidetime.R;
import com.lyf.slidetime.base.BaseFragment;
import com.lyf.slidetime.my.contract.MyContract;
import com.lyf.slidetime.my.presenter.MyPresenterImpl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/8.
 */

public class MyFragment extends BaseFragment<MyContract.Presenter> implements MyContract.View {


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_my, null);
    }

    @Override
    protected MyContract.Presenter onLoadPresenter() {
        return new MyPresenterImpl();
    }



    @Override
    public void initData() {
        super.initData();

    }


}
