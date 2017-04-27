package com.lyf.bookreader.search;

import android.os.Bundle;

import com.lyf.bookreader.R;
import com.lyf.bookreader.base.BaseActivity;
import com.lyf.bookreader.search.contract.SearchContract;
import com.lyf.bookreader.search.presenter.SearchPresenterImpl;
import com.lyf.bookreader.view.SearchKeyLayout;
import com.lyf.bookreader.view.SearchKeyView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ganlin on 2017/4/22.
 */

public class SearchActivity extends BaseActivity<SearchContract.Presenter> implements SearchContract.View {

    @BindView(R.id.id_skl_activity_search_localHistoryContainer)
    SearchKeyLayout localHistoryContainer;
    @BindView(R.id.id_skl_activity_search_hotSearchContainer)
    SearchKeyLayout hotSearchContainer;

    @Override
    protected SearchContract.Presenter onLoadPresenter() {
        return new SearchPresenterImpl();
    }

    @Override
    public void showHotSearchList(List<String> hotSearchList) {


    }

    @Override
    public void showHistorySearchList(List<String> historySearchList) {
        localHistoryContainer.removeAllViews();
        for (String history : historySearchList) {
            SearchKeyView view = new SearchKeyView(this);
            view.init(history);
            localHistoryContainer.addView(view);
        }
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_search);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getHistorySearchList();
    }
}
