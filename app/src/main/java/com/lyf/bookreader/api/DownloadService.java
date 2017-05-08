package com.lyf.bookreader.api;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * Created by JokAr on 16/7/5.
 */
public interface DownloadService {


    /**
     * 下载书籍
     * @param filename
     * @param page
     * @return
     */
    @Streaming
    @FormUrlEncoded
    @POST("BookDownload2")
    Observable<ResponseBody> download(@Field("filename") String filename,
                                      @Field("page") String page);
}
