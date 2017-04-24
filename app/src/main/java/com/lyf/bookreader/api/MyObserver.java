package com.lyf.bookreader.api;

import com.lyf.bookreader.javabean.BaseCallModel;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 自定义的一 个rxjava的oberser
 * Created by Administrator on 2016/8/4.
 */
public abstract class MyObserver<T> extends Subscriber<BaseCallModel<T>> {

    @Override
    public void onStart() {
        //TODO 在这里可以显示进度对话框等
        start();
    }


    @Override
    public void onCompleted() {
        completed();
    }


    @Override
    public void onError(Throwable t) {

        String str = t.getMessage();
        if (t instanceof SocketTimeoutException) {
            str = "请求超时";
        } else if (t instanceof ConnectException) {
            str = "连接异常";
        }
        onFail(str);
    }

    @Override
    public void onNext(BaseCallModel<T> baseCallModel) {

        if (baseCallModel != null) {
            if (baseCallModel.getCode() == 0) {//token失效
                onExit();
            }else if (baseCallModel.getCode() == 1) {//成功
                onSuccess(baseCallModel.getData(),baseCallModel.getResultMsg());
            } else {//错误
                onFail(baseCallModel.getResultMsg());
            }
        } else {
            onFail("数据为空");
        }


    }



    /**
     * 访问网络之前调用的方法,比如可以显示对话框
     */
    private void start() {

    }

    /**
     * 请求成功的方法
     * @param data
     * @param resultMsg
     */
    protected abstract void onSuccess(T data, String resultMsg);

    /**
     * 请求错误的方法
     * @param resultMsg
     */
    public abstract void onFail(String resultMsg);


    /**
     * token失效强制用户退出
     */
    public abstract void onExit();

    /**
     * 完成的方法
     */
    public void completed() {

    }
}
