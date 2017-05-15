package com.lyf.bookreader.bookcase.presenter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.lyf.bookreader.adapter.BookCaseItemAdapter;
import com.lyf.bookreader.application.MyApplication;
import com.lyf.bookreader.base.BasePresenter;
import com.lyf.bookreader.bookcase.contract.BookCaseContract;
import com.lyf.bookreader.db.BookCaseDao;
import com.lyf.bookreader.db.BookReaderDBManager;
import com.lyf.bookreader.db.RecentlyReadDao;
import com.lyf.bookreader.javabean.BookCase;
import com.lyf.bookreader.javabean.RecentlyRead;
import com.lyf.bookreader.readbook.BookReadActivity;
import com.lyf.bookreader.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/08
 */

public class BookCasePresenterImpl extends BasePresenter<BookCaseContract.View, BookCaseContract.Model> implements BookCaseContract.Presenter {
    private List<BookCase> mBookCaseList; //存放书本信息的容器
    private BookCaseItemAdapter mBookCaseItemAdapter; //适配器
    private BookCaseDao mBookCaseDao;
    private RecentlyReadDao mRecentlyReadDao;
    private RecentlyRead mRecentlyRead;

    @Override
    public void start() {

        mRecentlyReadDao = BookReaderDBManager.getInstance().getDaoSession().getRecentlyReadDao();

    }


    @Override
    public RecyclerView.Adapter getAdapter() {
        mBookCaseList = new ArrayList<>();
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
        mBookCaseDao = BookReaderDBManager.getInstance().getDaoSession().getBookCaseDao();
        List<BookCase> mBookCaseList = mBookCaseDao.loadAll();
        if (mBookCaseList != null && mBookCaseItemAdapter != null) {
            this.mBookCaseList.clear();
            this.mBookCaseList.addAll(mBookCaseList);
            mBookCaseItemAdapter.notifyDataSetChanged();
        }
    }

    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/27
     * @功能说明：继续阅读
     */
    @Override
    public void continueRead() {
        if (mRecentlyRead != null) {
            Intent intent = new Intent(getView().getmActivity(), BookReadActivity.class);
            intent.putExtra("bookname", mRecentlyRead.getBookname());
            intent.putExtra("total", mRecentlyRead.getTotal());
            UiUtils.startIntent(getView().getmActivity(), intent);
        }

    }

    @Override
    public void onStart() {
        getRecentlyRead();
    }

    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/27
     * @功能说明：获取最近阅读的一本书的信息
     */
    private void getRecentlyRead() {
        getView().getRxManager().add(
                Observable.create(new Observable.OnSubscribe<List<RecentlyRead>>() {
                    @Override
                    public void call(Subscriber<? super List<RecentlyRead>> subscriber) {
                        subscriber.onNext(mRecentlyReadDao.loadAll());
                    }
                }).map(new Func1<List<RecentlyRead>, RecentlyRead>() {
                    @Override
                    public RecentlyRead call(List<RecentlyRead> recentlyReads) {
                        if (recentlyReads != null && recentlyReads.size() > 0) {
                            return recentlyReads.get(0);
                        }
                        return null;
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<RecentlyRead>() {
                            @Override
                            public void call(RecentlyRead recentlyRead) {
                                if (recentlyRead != null) {
                                    mRecentlyRead = recentlyRead;
                                    getView().setInformation(recentlyRead);
                                }
                            }
                        })
        );
    }

    @Override
    public void destroy() {
        System.out.println("ff");
    }

}