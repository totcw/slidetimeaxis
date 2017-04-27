package com.lyf.bookreader.bookdetail.presenter;

import com.lyf.bookreader.application.MyApplication;
import com.lyf.bookreader.base.BasePresenter;
import com.lyf.bookreader.bookdetail.contract.BookDetailContract;
import com.lyf.bookreader.db.BookCaseDao;
import com.lyf.bookreader.javabean.BookCase;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by      lyf on 2017/04/22
 */

public class BookDetailPresenterImpl extends BasePresenter<BookDetailContract.View, BookDetailContract.Model> implements BookDetailContract.Presenter {
    private String bookname;//书 名
    private String author;//作者
    private String time;//时 间
    private String finish; //完结状态
    private String img; //书本图片url
    private String type; //书本类型
    private int total; //书本总的章节数

    private BookCaseDao mBookCaseDao;//

    @Override
    public void start() {
        getIntentData();
        getView().setBookInformation(author, bookname, time, finish);
        mBookCaseDao = MyApplication.getInstance().getDaoSession().getBookCaseDao();
        quereIsBook();

    }

    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/27
     * @功能说明：查询是否加入过书架
     */
    public void quereIsBook() {

        getView().getRxManager().add(
                Observable.create(new Observable.OnSubscribe<List<BookCase>>() {
                    @Override
                    public void call(Subscriber<? super List<BookCase>> subscriber) {
                        subscriber.onNext(mBookCaseDao.queryBuilder().where(BookCaseDao.Properties.Bookname.eq(bookname)).list());
                    }
                })
                .map(new Func1<List<BookCase>, BookCase>() {
                    @Override
                    public BookCase call(List<BookCase> bookCases) {
                        if (bookCases != null && bookCases.size() > 0) {
                            return bookCases.get(0);
                        }
                         return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BookCase>() {
                    @Override
                    public void call(BookCase bookCase) {
                        if (bookCase != null && bookCase.getBookname() != null) {

                            getView().setAddBookcaseStatus(false);
                        } else {
                            getView().setAddBookcaseStatus(true);
                        }
                    }
                })
        );
    }


    /**
     * 获取传递过来的数据
     */
    private void getIntentData() {
        bookname = getView().getmActivity().getIntent().getStringExtra("bookname");
        author = getView().getmActivity().getIntent().getStringExtra("author");
        time = getView().getmActivity().getIntent().getStringExtra("time");
        finish = getView().getmActivity().getIntent().getStringExtra("finish");
        img = getView().getmActivity().getIntent().getStringExtra("img");
        total = getView().getmActivity().getIntent().getIntExtra("total", 1);
        type = getView().getmActivity().getIntent().getStringExtra("type");

    }

    @Override
    public void destroy() {

    }

    @Override
    public void addToBookcase() {
        final BookCase bookCase = new BookCase();
        bookCase.setBookname(bookname);
        bookCase.setAuthor(author);
        bookCase.setFinish(finish);
        bookCase.setTime(time);
        bookCase.setImg(img);
        bookCase.setCurPage(1);
        bookCase.setEndPos(0);
        bookCase.setMEndPos(0);
        bookCase.setTotal(total);
        bookCase.setType(type);
        getView().getRxManager().add(
                Observable.create(new Observable.OnSubscribe<Long>() {
                    @Override
                    public void call(Subscriber<? super Long> subscriber) {
                        subscriber.onNext(mBookCaseDao.insert(bookCase));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (aLong > 0) {
                            getView().setAddBookcaseStatus(false);
                        }
                    }
                })
        );


    }
}