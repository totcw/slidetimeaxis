package com.lyf.slidetime.javabean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/18.
 */
@Entity
public class Book {
    private String content;
    private String title;

    @Generated(hash = 1372161843)
    public Book(String content, String title) {
        this.content = content;
        this.title = title;
    }

    @Generated(hash = 1839243756)
    public Book() {
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
