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
import com.lyf.bookreader.readbook.presenter.BookReadPresenterImpl;
import com.lyf.bookreader.utils.FileUtils;
import com.lyf.bookreader.utils.RxManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
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
        mRxManager = new RxManager();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //加入缓存队列
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
                    //将任务加入缓存队列
                    downloadQueues.add(bookname);
                    mRxManager.post(BookReadPresenterImpl.SERVICE_DOWNLOAD_REPLY, "加入缓存队列成功");
                    //设置下载的通知栏
                    setNotification();
                    //开启下载
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
     * @创建日期： 2017/5/9
     * @功能说明：设置下载的通知栏
     */
    private void setNotification() {

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_reader_ab_download)
                .setContentTitle("正在下载")
                .setContentText("Downloading File")
                .setAutoCancel(true);

        notificationManager.notify(0, notificationBuilder.build());
    }


    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/28
     * @功能说明：下载书籍
     */
    private void downloadBook(final String bookname, final int page, final int total) {
        //获取保存书本的路径
        outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + bookname, page + ".txt");
        if (outputFile.exists()) {
            outputFile.delete();
        }

        DownloadAPI.getmDownloadService().download(bookname, page + "")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {
                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        try {
                            FileUtils.writeFile(inputStream, outputFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InputStream>() {
                    @Override
                    public void onCompleted() {
                        downloadCompleted(page, total);

                        int nextPage = page + 1;
                        if (nextPage <= total) {
                            downloadBook(bookname, nextPage, total);
                        } else {
                            //将任务从缓存队列中清除
                            if (downloadQueues != null && bookname != null) {
                                downloadQueues.remove(bookname);
                            }
                            sendNotification(nextPage + "/" + total, "下载完成");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        sendNotification("", "下载失败");
                    }

                    @Override
                    public void onNext(InputStream inputStream) {

                    }
                });


    }

    /**
     *@author : lyf
     *@email:totcw@qq.com
     *@创建日期： 2017/5/9
     *@功能说明： 更新通知栏
     *@param
     *@return
     */
    private void sendNotification(String content, String title) {
        notificationBuilder.setContentTitle(title);
        if (!TextUtils.isEmpty(content)) {
            notificationBuilder.setContentText(content);
        }
        notificationManager.notify(0, notificationBuilder.build());
    }


    private void downloadCompleted(int page, int total) {
        sendNotification(page + "/" + total, "正在下载");
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
