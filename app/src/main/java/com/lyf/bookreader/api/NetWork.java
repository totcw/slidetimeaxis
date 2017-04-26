package com.lyf.bookreader.api;


import com.lyf.bookreader.application.MyApplication;
import com.lyf.bookreader.javabean.BaseCallModel;
import com.lyf.bookreader.utils.Constants;
import com.lyf.bookreader.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 封装网络请求
 * Created by A dministrator on 2016/7/29.
 */
public class NetWork {
    private static NetService netService; //封装了请求的接口

    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    /**
     * 通过retrofit返回接口的实现类
     *
     * @return
     */
    public static NetService getNetService() {


        if (netService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.Url.URL)
                    .client(getBuilder(60).build())
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            netService = retrofit.create(NetService.class);

        }

        return netService;
    }


    /**
     * 定义拦截器
     */
    static Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //设置请求时
            Request request = chain.request();
            if (!NetworkUtils.isNetAvailable(MyApplication.getInstance())) {//没网才读缓存
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            //设置数据返回时
            Response response = chain.proceed(request);
            if (NetworkUtils.isNetAvailable(MyApplication.getInstance())) {
                int maxAge = 0 * 60; // 有网络时 设置缓存超时时间0个小时
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
            return response;

        }
    };


    /**
     * 用Transformers 预处理observable,比如写好observeOn避免每次调用都要写这个 ,然后使用compose连接
     * 这里多一个参数为了确定T
     */

    public static <T> Observable.Transformer<BaseCallModel<T>, BaseCallModel<T>> handleResult(BaseCallModel<T> baseCallModel) {
        return new Observable.Transformer<BaseCallModel<T>, BaseCallModel<T>>() {

            @Override
            public Observable<BaseCallModel<T>> call(Observable<BaseCallModel<T>> baseCallModelObservable) {
                return baseCallModelObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }
        };
    }


    /**
     * 取消订阅
     *
     * @param subscription
     */
    public static void unsubscribe(Subscription subscription) {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
            subscription = null;
        }
    }

    public static  OkHttpClient.Builder getBuilder(long timeout) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(interceptor);
        //设置缓存
        builder.cache(new Cache(new File(MyApplication.getInstance().getCacheDir(), "responses"), 10 * 1024 * 1024))
        ;
        //设置超时
        builder.connectTimeout(timeout, TimeUnit.SECONDS);
        builder.readTimeout(timeout, TimeUnit.SECONDS);
        builder.writeTimeout(timeout, TimeUnit.SECONDS);

        return builder;
    }
}
