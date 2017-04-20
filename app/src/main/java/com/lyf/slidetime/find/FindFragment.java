package com.lyf.slidetime.find;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lyf.slidetime.R;
import com.lyf.slidetime.base.BaseFragment;
import com.lyf.slidetime.find.contract.FindContract;
import com.lyf.slidetime.find.presenter.FindPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/8.
 */

public class FindFragment extends BaseFragment<FindContract.Presenter> implements FindContract.View {


    @BindView(R.id.rv_find)
    RecyclerView mRvFind;


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_find, null);
    }

    @Override
    protected FindContract.Presenter onLoadPresenter() {
        return new FindPresenterImpl();
    }

    @Override
    public void initData() {
        super.initData();
        initRv();
    }

    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/20
     * @功能说明： 初始化recycleview
     */
    private void initRv() {

        mRvFind.setLayoutManager(new GridLayoutManager(getmActivity(), 3));
        mRvFind.setAdapter(getPresenter().getAdapter());

    }



    @OnClick(R.id.linear_find_searche)
    public void onClick() {
        Toast.makeText(getmActivity(),"此功能暂未开通",0).show();
    }
}
