package com.lyf.bookreader.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.betterda.mylibrary.LoadingPager;
import com.lyf.bookreader.R;
import com.lyf.bookreader.base.BaseActivity;
import com.lyf.bookreader.search.contract.SearchContract;
import com.lyf.bookreader.search.presenter.SearchPresenterImpl;
import com.zhy.base.adapter.recyclerview.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/4/20
 * @功能说明： 搜索结果
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class SearchResultActivity extends BaseActivity<SearchContract.Presenter> implements SearchContract.View {


    @BindView(R.id.rv_serachresult)
    RecyclerView mRvSerachresult;
    @BindView(R.id.loadpager_searchresult)
    LoadingPager mLoadpagerSearchresult;

    @Override
    protected SearchContract.Presenter onLoadPresenter() {
        return new SearchPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_searchresult);
    }


    @Override
    public void init() {
        super.init();
        initRecycleview();


    }


    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/20
     * @功能说明：初始化recycleview
     */
    private void initRecycleview() {

        mRvSerachresult.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mRvSerachresult.addItemDecoration(new DividerItemDecoration(getmActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRvSerachresult.setAdapter(getPresenter().getAdapter());
    }


    @Override
    public LoadingPager getLoadpager() {
        return mLoadpagerSearchresult;
    }


}
