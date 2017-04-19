package com.lyf.slidetime.find;

import android.view.LayoutInflater;
import android.view.View;


import com.lyf.slidetime.R;
import com.lyf.slidetime.base.BaseFragment;
import com.lyf.slidetime.find.contract.FindContract;
import com.lyf.slidetime.find.presenter.FindPresenterImpl;

/**
 * Created by Administrator on 2016/12/8.
 */

public class FindFragment extends BaseFragment<FindContract.Presenter> implements FindContract.View {




    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_find, null);
    }

    @Override
    protected FindContract.Presenter onLoadPresenter() {
        return new FindPresenterImpl();
    }



}
