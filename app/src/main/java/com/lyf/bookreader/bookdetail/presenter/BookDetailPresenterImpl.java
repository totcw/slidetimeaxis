package com.lyf.bookreader.bookdetail.presenter;

import com.lyf.bookreader.application.MyApplication;
import com.lyf.bookreader.base.BasePresenter;
import com.lyf.bookreader.bookdetail.contract.BookDetailContract;
import com.lyf.bookreader.db.BookCaseDao;
import com.lyf.bookreader.javabean.BookCase;

import java.util.List;

/**
* Created by      lyf on 2017/04/22
*/

public class BookDetailPresenterImpl extends BasePresenter<BookDetailContract.View,BookDetailContract.Model> implements BookDetailContract.Presenter{
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
        getView().setBookInformation(author,bookname,time,finish);
        mBookCaseDao = MyApplication.getInstance().getDaoSession().getBookCaseDao();
        //查询数据库判断改书是否加入到了书架中
        List<BookCase> bookCases = mBookCaseDao.queryBuilder().where(BookCaseDao.Properties.Bookname.eq(bookname)).list();
        if (bookCases != null && bookCases.size() > 0) {
            BookCase bookCase = bookCases.get(0);
            if (bookCase != null && bookCase.getBookname() != null) {

                getView().setAddBookcaseStatus(false);
            } else {
                getView().setAddBookcaseStatus(true);
            }
        } else {
            getView().setAddBookcaseStatus(true);
        }
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
        total = getView().getmActivity().getIntent().getIntExtra("total", 0);
        type = getView().getmActivity().getIntent().getStringExtra("type");
    }

    @Override
    public void destroy() {

    }

    @Override
    public void addToBookcase() {
        BookCase bookCase = new BookCase();
        bookCase.setBookname(bookname);
        bookCase.setAuthor(author);
        bookCase.setFinish(finish);
        bookCase.setTime(time);
        bookCase.setImg(img);
        bookCase.setCurPage(1);
        bookCase.setPosition(0);
        bookCase.setTotal(total);
        bookCase.setType(type);
        if (mBookCaseDao.insert(bookCase) > 0) {
            getView().setAddBookcaseStatus(false);
        }
        System.out.println("ff");
    }
}