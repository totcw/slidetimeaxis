package com.lyf.bookreader.search.presenter;

import com.lyf.bookreader.base.BasePresenter;
import com.lyf.bookreader.search.contract.SearchContract;

import java.util.List;

/**
 * Created by lyf  on 2017/4/24.
 */

public class SearchPresenterImpl extends BasePresenter<SearchContract.View,SearchContract.Model> implements SearchContract.Presenter {
    @Override
    public List<String> getHotSearchList() {
        return null;
    }

    @Override
    public List<String> getHistorySearchList() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }
}
