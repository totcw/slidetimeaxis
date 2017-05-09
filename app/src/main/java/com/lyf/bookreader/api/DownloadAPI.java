package com.lyf.bookreader.api;

import com.lyf.bookreader.utils.NativeHelper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * 下载文件的api
 * Created by JokAr on 16/7/5.
 */
public class DownloadAPI {
    private static final String TAG = "DownloadAPI";
    private static final int DEFAULT_TIMEOUT = 15;

    private static DownloadService mDownloadService;


    public  static DownloadService  getmDownloadService() {
        if (mDownloadService == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(NativeHelper.getUrl())
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            mDownloadService = retrofit.create(DownloadService.class);
        }


        return mDownloadService;
    }



}
