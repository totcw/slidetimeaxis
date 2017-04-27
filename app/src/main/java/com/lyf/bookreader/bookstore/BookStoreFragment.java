package com.lyf.bookreader.bookstore;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.lyf.bookreader.R;
import com.lyf.bookreader.base.BaseFragment;
import com.lyf.bookreader.bookstore.contract.BookStoreContract;
import com.lyf.bookreader.bookstore.presenter.BookStorePresenterImpl;
import com.lyf.bookreader.search.SearchActivity;
import com.lyf.bookreader.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Admini strator on 2016/12/8.
 * 书城
 */

public class BookStoreFragment extends BaseFragment<BookStoreContract.Presenter> implements BookStoreContract.View {


    public static BookStoreFragment newInstance(Bundle args) {
        BookStoreFragment fragment = new BookStoreFragment();
        if (args != null) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @BindView(R.id.rv_find)
    RecyclerView mRecycleviewFind;


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_bookstore, null);
    }

    @Override
    protected BookStoreContract.Presenter onLoadPresenter() {
        return new BookStorePresenterImpl();
    }

    @Override
    public void initData() {
        super.initData();
        initRececleview();
    }

    /**
     * @param
     * @return
     * @author : lyf
     * @email:totc w@qq.com
     * @创建日期： 2017/4/20
     * @功能说明： 初始化recycleview
     */
    private void initRececleview() {

        mRecycleviewFind.setLayoutManager(new GridLayoutManager(getmActivity(), 3));
        mRecycleviewFind.setAdapter(getPresenter().getAdapter());

    }


    @OnClick(R.id.linear_find_searche)
    public void onClick() {
        UiUtils.startIntent(getmActivity(), SearchActivity.class);
    }
}
