package com.lyf.slidetime.api;



import com.lyf.slidetime.BaseCallModel;
import com.lyf.slidetime.javabean.Book;
import com.lyf.slidetime.utils.Constants;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * 封装retrofit请求需要的接口
 * Created by Administrator on 2016/7/29.
 */
public interface NetService {
    /**
     *
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Test")
    Observable<BaseCallModel<Book>> getRegister(@Field("bookname") String bookname,
                                                @Field("page") String page);






}
