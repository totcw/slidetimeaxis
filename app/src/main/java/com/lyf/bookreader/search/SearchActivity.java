package com.lyf.bookreader.search;

import com.lyf.bookreader.base.BaseActivity;
import com.lyf.bookreader.search.contract.SearchContract;
import com.lyf.bookreader.search.contract.SearchResultContract;
import com.lyf.bookreader.search.presenter.SearchPresenterImpl;

import java.util.List;

/**
 * Created by ganlin on 2017/4/22.
 */

public class SearchActivity extends BaseActivity<SearchContract.Presenter> implements SearchContract.View {

    @Override
    protected SearchContract.Presenter onLoadPresenter() {
        return new SearchPresenterImpl();
    }

    @Override
    public void showHotSearchList(List hotSearchList) {

    }

    @Override
    public void showHistorySearchList(List historySearchList) {

    }
}
