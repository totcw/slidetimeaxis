package com.lyf.slidetime.javabean;

/**
 * 定义一个网络请求的回调基类
 * Created by Administrator on 2016/7/27.
 */
public class BaseCallModel<T> {
    private int  code;
    private String resultMsg;
   // private String content;
   // private String title;
    private T data;

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

 /*   public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }*/
}
