package com.lyf.bookreader.bookcase.presenter;


import android.support.v7.widget.RecyclerView;

import com.lyf.bookreader.adapter.BookCaseItemAdapter;
import com.lyf.bookreader.application.MyApplication;
import com.lyf.bookreader.base.BasePresenter;
import com.lyf.bookreader.bookcase.contract.BookCaseContract;
import com.lyf.bookreader.db.BookCaseDao;
import com.lyf.bookreader.javabean.BookCase;

import java.util.ArrayList;
import java.util.List;

/**
* Created by Administrator on 2016/12/08
*/

public class BookCasePresenterImpl extends BasePresenter<BookCaseContract.View,BookCaseContract.Model> implements BookCaseContract.Presenter{
    private List<BookCase> mBookCaseList; //存放书本信息的容器
    private BookCaseItemAdapter mBookCaseItemAdapter; //适配器
    private BookCaseDao mBookCaseDao;
    @Override
    public void start() {
        mBookCaseList = new ArrayList<>();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mBookCaseItemAdapter = new BookCaseItemAdapter(getView().getmActivity(), mBookCaseList);
        return mBookCaseItemAdapter;
    }
    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw @qq.com
     * @创建日期： 2017/4/20
     * @功能说明：获取书架的数据
     */
    @Override
    public void getData() {
        mBookCaseDao = MyApplication.getInstance().getDaoSession().getBookCaseDao();
        List<BookCase> mBookCaseList = mBookCaseDao.loadAll();
        if (mBookCaseList != null) {
            this.mBookCaseList.clear();
            this.mBookCaseList.addAll(mBookCaseList);
            mBookCaseItemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void destroy() {
        System.out.println("ff");
    }

}