package com.lyf.bookreader.search.presenter;

import com.lyf.bookreader.base.BasePresenter;
import com.lyf.bookreader.search.contract.SearchContract;

import java.util.List;

/**
 * Created by ganlin on 2017/4/22.
 */

public class SearchPresenterImpl extends BasePresenter<SearchContract.View, SearchContract.Model> implements SearchContract.Presenter {
    @Override
    public List getHotSearchList() {
        return null;
    }

    @Override
    public List getHistorySearchList() {
        return null;
    }


    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }
}
