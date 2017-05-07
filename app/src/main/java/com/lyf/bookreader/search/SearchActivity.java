package com.lyf.bookreader.search;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;

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
        if (historySearchList == null) {
            // TODO: 2017/5/7 emptyview
            return;
        }
        localHistoryContainer.removeAllViews();
        for (String history : historySearchList) {
            SearchKeyView view = new SearchKeyView(this);
            view.setTextColor(Color.parseColor("#fafafa"));
            ViewGroup.MarginLayoutParams params =
                    new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 10;
            params.bottomMargin = 10;
            params.topMargin = 10;
            params.rightMargin = 10;
            view.setPadding(10, 5, 10, 5);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            view.setLayoutParams(params);
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
