package com.lyf.bookreader.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.lyf.bookreader.application.MyApplication;
import com.lyf.bookreader.db.BookDao;
import com.lyf.bookreader.javabean.Book;
import com.lyf.bookreader.readbook.presenter.BookReadPresenterImpl;
import com.lyf.bookreader.utils.GsonParse;
import com.lyf.bookreader.utils.NativeHelper;
import com.lyf.bookreader.utils.RxManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/4/28
 * @功能说明： 下载书籍的服务
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class DownloadBookService extends Service {

    private List<String> downloadQueues = new ArrayList<>();//缓存队列
    private RxManager mRxManager;
    private BookDao mBookDao;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookDao = MyApplication.getInstance().getDaoSession().getBookDao();
        mRxManager = new RxManager();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        addDownloadQueue(intent);
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/28
     * @功能说明：加入缓存队列
     */
    private void addDownloadQueue(Intent intent) {
        if (intent != null) {
            String bookname = intent.getStringExtra("bookname");
            int chapter = intent.getIntExtra("chapter", 1);
            int total = intent.getIntExtra("total", 1);
            if (!TextUtils.isEmpty(bookname)) {
                if (downloadQueues.contains(bookname)) {
                    mRxManager.post(BookReadPresenterImpl.SERVICE_DOWNLOAD_REPLY, "已经在缓存队列");
                } else {

                    downloadQueues.add(bookname);
                    mRxManager.post(BookReadPresenterImpl.SERVICE_DOWNLOAD_REPLY, "加入缓存队列成功");
                    downloadBook(bookname, chapter, total);

                }
            } else {
                mRxManager.post(BookReadPresenterImpl.SERVICE_DOWNLOAD_REPLY, "已经在缓存队列");
            }
        }
    }


    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/28
     * @功能说明：下载书籍
     */
    private void downloadBook(final String bookname, int page, int total) {
        RequestParams params = new RequestParams(NativeHelper.getUrl() + "BookDownload");
        params.addBodyParameter("bookname", bookname);
        params.addBodyParameter("page", page + "");
        params.addBodyParameter("total", total + "");
        //设置断点续传
        params.setAutoResume(true);
        params.setMaxRetryCount(0);
        params.setConnectTimeout(1000 * 3600 * 10);//设置为10个小时
        Callback.Cancelable cancelable = x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                //将任务从缓存队列中清除
                if (downloadQueues != null && bookname != null) {
                    downloadQueues.remove(bookname);
                }

                List<Book> bookList = GsonParse.getListBook(result);
                if (bookList != null) {
                    parserDownload(bookList);
                }

                Toast.makeText(DownloadBookService.this,"缓存完成",0).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }


    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/27
     * @功能说明：解析下载结果
     */
    private void parserDownload(List<Book> bookList) {
        mRxManager.add(
                Observable.from(bookList)
                        .map(new Func1<Book, Book>() {
                            @Override
                            public Book call(Book book) {
                                if (book != null && mBookDao != null) {
                                    List<Book> mBookList = mBookDao.queryBuilder().where(BookDao.Properties.Bookname.eq(book.getBookname()), BookDao.Properties.Page.eq(book.getPage())).list();
                                    if (mBookList != null && mBookList.size() > 0) {
                                        Book bookdao = mBookList.get(0);
                                        book.setId(bookdao.getId());
                                    }
                                    return book;
                                }
                                return null;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe(new Action1<Book>() {
                            @Override
                            public void call(Book book) {
                                if (mBookDao != null) {
                                    mBookDao.insertOrReplace(book);
                                }
                            }
                        })
        );
  /*      for (Book book : bookList) {
            if (book != null) {
                List<Book> mBookList = mBookDao.queryBuilder().where(BookDao.Properties.Bookname.eq(book.getBookname()), BookDao.Properties.Page.eq(book.getPage())).list();
                if (mBookList != null && mBookList.size() > 0) {
                    Book bookdao = mBookList.get(0);
                    book.setId(bookdao.getId());
                    mBookDao.update(book);
                } else {
                    mBookDao.insertOrReplace(book);
                }
            }
        }*/
    }

    @Override
    public void onDestroy() {
        if (downloadQueues != null) {
            downloadQueues.clear();
            downloadQueues = null;
        }
        super.onDestroy();
    }
}
