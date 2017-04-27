package com.lyf.bookreader.search.presenter;

import com.lyf.bookreader.base.BasePresenter;
import com.lyf.bookreader.search.contract.SearchContract;

import java.util.ArrayList;
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
        List<String> list = new ArrayList();
        list.add("武侠");
        list.add("修仙");
        list.add("诺克塞斯之手");
        list.add("德玛西亚之力");
        list.add("影流之主");
        list.add("暗夜猎手");
        list.add("探险家");
        list.add("武器大师");
        list.add("暗影之权");
        list.add("人民的名义");
        list.add("西湖公园");
        mView.showHistorySearchList(list);
        return list;
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }
}
