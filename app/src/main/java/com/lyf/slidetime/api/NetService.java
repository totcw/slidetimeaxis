package com.lyf.slidetime.api;



import com.lyf.slidetime.javabean.BaseCallModel;
import com.lyf.slidetime.javabean.Book;
import com.lyf.slidetime.javabean.BookCase;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
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

 /**
  *@author : lyf
  *@email:totcw@qq.com
  *@创建日期： 2017/4/20
  *@功能说明：
  *@param
  *@return
  */
    @FormUrlEncoded
    @POST("BookCase")
    Observable<BaseCallModel<List<BookCase>>> getBook(@Field("type") String type);






}
