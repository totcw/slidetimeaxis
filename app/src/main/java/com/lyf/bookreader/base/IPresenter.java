package com.lyf.bookreader.base;

/**
 * IPresenter的基类接口
 * Created by Admin istrator on 2016/12/2.
 */

public interface IPresenter<T> {

    /**
     * 关联view
     * @param view
     */
    void attachView(T view);

    void detachView();


    void start();

    void destroy();
}
