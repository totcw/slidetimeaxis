package com.lyf.bookreader.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.lyf.bookreader.R;
import com.lyf.bookreader.api.DownloadAPI;
import com.lyf.bookreader.api.download.DownloadProgressListener;
import com.lyf.bookreader.application.MyApplication;
import com.lyf.bookreader.db.BookDao;
import com.lyf.bookreader.javabean.Book;
import com.lyf.bookreader.javabean.Download;
import com.lyf.bookreader.readbook.presenter.BookReadPresenterImpl;
import com.lyf.bookreader.utils.RxManager;
import com.lyf.bookreader.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
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
    private int downloadCount = 0;
    private File outputFile;
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;

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
      /*  notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_reader_ab_download)
                .setContentTitle("Download")
                .setContentText("Downloading File")
                .setAutoCancel(true);

        notificationManager.notify(0, notificationBuilder.build());*/
        DownloadProgressListener listener = new DownloadProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                //不频繁发送通知，防止通知栏下拉卡顿
             /*   int progress = (int) ((bytesRead * 100) / contentLength);
                if ((downloadCount == 0) || progress > downloadCount) {
                    Download download = new Download();
                    download.setTotalFileSize(contentLength);
                    download.setCurrentFileSize(bytesRead);
                    download.setProgress(progress);

                    sendNotification(download);
                }*/
            }
        };
        outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+bookname, page+".txt");
        if (outputFile.exists()) {
            outputFile.delete();
        }


        new DownloadAPI(listener).downloadAPK(bookname, page + "", outputFile, new Subscriber() {
            @Override
            public void onCompleted() {
                downloadCompleted("下载完成",bookname);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                downloadCompleted("下载失败",bookname);

            }

            @Override
            public void onNext(Object o) {

            }
        });

      /*  RequestParams params = new RequestParams(NativeHelper.getUrl() + "BookDownload");
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
        });*/
    }


    private void sendNotification(Download download) {


        notificationBuilder.setProgress(100, download.getProgress(), false);
        notificationBuilder.setContentText(
                StringUtils.getDataSize(download.getCurrentFileSize()) + "/" +
                        StringUtils.getDataSize(download.getTotalFileSize()));
        notificationManager.notify(0, notificationBuilder.build());
    }


    private void downloadCompleted(String content,String bookname) {
       /* notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText(content);
        notificationManager.notify(0, notificationBuilder.build());*/
        //将任务从缓存队列中清除
        if (downloadQueues != null && bookname != null) {
            downloadQueues.remove(bookname);
        }
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
