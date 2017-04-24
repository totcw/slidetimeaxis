package com.lyf.bookreader.javabean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 代表每一章节的实体类
 * Created by Administrator on 2017/4/18.
 */

public class Chapter {

    private String content;//每一章节的内容
    private String title; //章节名
    public Chapter(String content, String title) {
        this.content = content;
        this.title = title;
    }

    public Chapter() {
    }


    public String getContent() {
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
    }
}
