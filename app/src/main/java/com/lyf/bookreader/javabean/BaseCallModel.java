package com.lyf.bookreader.javabean;

/**
 * 定义一个网络请求的回调基类
 * Created by Administrator on 2016/7/27.
 */
public class BaseCallModel<T> {
    private int  code; //服务器返回的状态码
    private String resultMsg;//返回的结果信息
    private T data; //返回的数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
