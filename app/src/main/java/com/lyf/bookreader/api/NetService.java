package com.lyf.bookreader.api;


import com.lyf.bookreader.javabean.BaseCallModel;
import com.lyf.bookreader.javabean.Book;
import com.lyf.bookreader.javabean.BookCase;
import com.lyf.bookreader.javabean.Chapter;
import com.lyf.bookreader.javabean.SearchEntity;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 封装retrofit 请求需要的接口
 * Created by Administrator on 2016/7/29.
 */
public interface NetService {
    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/20
     * @功能说明： 根据书名和章节数 加载书的内容
     */
    @FormUrlEncoded
    @POST("Test")
    Observable<BaseCallModel<Chapter>> getChpater(@Field("bookname") String bookname,
                                                  @Field("page") String page);

    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/20
     * @功能说明： 根据书的类型获取书籍
     */
    @FormUrlEncoded
    @POST("BookCase")
    Observable<BaseCallModel<List<BookCase>>> getBookList(@Field("type") String type);


    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/20
     * @功能说明： 获取书的目录
     */
    @FormUrlEncoded
    @POST("BookDirectory")
    Observable<BaseCallModel<List<Book>>> getBookDirectory(@Field("bookname") String bookname);


    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/5/2
     * @功能说明： 获取热门词汇
     */

    @GET("HotWord")
    Observable<BaseCallModel<List<SearchEntity>>> getHotWord();



    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/2/2
     * @功能说明： 搜索
     */
    @FormUrlEncoded
    @POST("Search")
    Observable<BaseCallModel<List<BookCase>>> getSearch(@Field("key") String key);



}
