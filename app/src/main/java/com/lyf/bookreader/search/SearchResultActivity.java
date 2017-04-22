package com.lyf.bookreader.search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.betterda.mylibrary.LoadingPager;
import com.lyf.bookreader.R;
import com.lyf.bookreader.base.BaseActivity;
import com.lyf.bookreader.search.contract.SearchResultContract;
import com.zhy.base.adapter.recyclerview.DividerItemDecoration;

import butterknife.BindView;

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

public class SearchResultActivity extends BaseActivity<SearchResultContract.Presenter> implements SearchResultContract.View {


    @BindView(R.id.rv_serachresult)
    RecyclerView mRvSerachresult;
    @BindView(R.id.loadpager_searchresult)
    LoadingPager mLoadPagerSearchResult;

    @Override
    protected SearchResultContract.Presenter onLoadPresenter() {
//        return new SearchPresenterImpl();
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_searchresult);
    }


    @Override
    public void init() {
        super.init();
        initRecycleView();


    }


    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/20
     * @功能说明：初始化recycleview
     */
    private void initRecycleView() {

        mRvSerachresult.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mRvSerachresult.addItemDecoration(new DividerItemDecoration(getmActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRvSerachresult.setAdapter(getPresenter().getAdapter());
    }


    @Override
    public LoadingPager getLoadPager() {
        return mLoadPagerSearchResult;
    }


}
