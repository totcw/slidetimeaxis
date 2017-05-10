package com.lyf.bookreader.search;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lyf.bookreader.R;
import com.lyf.bookreader.api.MyObserver;
import com.lyf.bookreader.api.NetWork;
import com.lyf.bookreader.base.BaseActivity;
import com.lyf.bookreader.bookdetail.BookDetailActivity;
import com.lyf.bookreader.javabean.BaseCallModel;
import com.lyf.bookreader.javabean.BookCase;
import com.lyf.bookreader.search.contract.SearchContract;
import com.lyf.bookreader.search.presenter.SearchPresenterImpl;
import com.lyf.bookreader.utils.UiUtils;
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
    @BindView(R.id.searchview)
    SearchView mSearchView;

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
    public void init() {
        super.init();
        mPresenter.getHistorySearchList();
        mSearchView.setIconified(false);
        mSearchView.setBackgroundColor(getResources().getColor(R.color.bg_blue));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }

    private void getData(String query) {
        if (TextUtils.isEmpty(query)) {
            Toast.makeText(getmActivity(),"没有相应的书籍",0).show();
            return;
        }
        getRxManager().add(
                NetWork.getNetService().getSearch(query)
                .compose(NetWork.handleResult(new BaseCallModel<List<BookCase>>()))
                .subscribe(new MyObserver<List<BookCase>>() {
                    @Override
                    protected void onSuccess(List<BookCase> data, String resultMsg) {
                        if (data != null && data.size() > 0) {
                            BookCase bookCase = data.get(0);
                            Intent intent = new Intent(getmActivity(), BookDetailActivity.class);
                            intent.putExtra("bookname", bookCase.getBookname());
                            intent.putExtra("author", bookCase.getAuthor());
                            intent.putExtra("time", bookCase.getTime());
                            intent.putExtra("finish", bookCase.getFinish());
                            intent.putExtra("img", bookCase.getImg());
                            intent.putExtra("total", bookCase.getTotal());
                            intent.putExtra("type", bookCase.getType());
                            UiUtils.startIntent(getmActivity(), intent);
                        } else {
                            Toast.makeText(getmActivity(),"没有相应的书籍",0).show();
                        }

                    }

                    @Override
                    public void onFail(String resultMsg) {

                    }

                    @Override
                    public void onExit() {

                    }
                })
        );
    }

}
