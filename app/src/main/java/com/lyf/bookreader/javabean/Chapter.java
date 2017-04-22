package com.lyf.bookreader.javabean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 代表每一章节的实体类
 * Created by Administrator on 2017/4/18.
 */
@Entity
public class Chapter {
    private String content;//每一章节的内容
    private String title; //章节名
    @Generated(hash = 1559191096)
    public Chapter(String content, String title) {
        this.content = content;
        this.title = title;
    }
    @Generated(hash = 393170288)
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
