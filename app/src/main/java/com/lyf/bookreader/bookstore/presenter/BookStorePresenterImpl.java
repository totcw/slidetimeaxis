package com.lyf.bookreader.bookstore.presenter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyf.bookreader.R;
import com.lyf.bookreader.base.BasePresenter;
import com.lyf.bookreader.bookstore.contract.BookStoreContract;
import com.lyf.bookreader.search.SearchResultActivity;
import com.lyf.bookreader.utils.UiUtils;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/08
 */

public class BookStorePresenterImpl extends BasePresenter<BookStoreContract.View, BookStoreContract.Model> implements BookStoreContract.Presenter {
    private List<String> mBookStoreList;

    @Override
    public void start() {


    }

    @Override
    public void destroy() {

    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mBookStoreList = new ArrayList<>();
        mBookStoreList.add("玄幻");
        mBookStoreList.add("武侠");
        mBookStoreList.add("都市");
        mBookStoreList.add("言情");
        mBookStoreList.add("穿越");
        mBookStoreList.add("网游");
        mBookStoreList.add("恐怖");
        mBookStoreList.add("科幻");
        mBookStoreList.add("其他");
        return new CommonAdapter<String>(getView().getmActivity(), R.layout.item_rv_bookstore, mBookStoreList) {
            @Override
            public void convert(ViewHolder holder, final String o) {
                holder.setText(R.id.tv_item_find,o);
                holder.setOnClickListener(R.id.linear_item_find, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getView().getmActivity(), SearchResultActivity.class);
                        intent.putExtra("type",o);
                        UiUtils.startIntent(getView().getmActivity(),intent);
                    }
                });
            }

        };
    }
}