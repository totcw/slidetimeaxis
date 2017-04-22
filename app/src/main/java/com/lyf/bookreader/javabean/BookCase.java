package com.lyf.bookreader.javabean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 书架
 * Created by lyf on 2017/4/19.
 */
public class BookCase {
    private String bookname;//书名
    private String author; //作者
    private String time; //最后更新时间
    private String finish;//是否完结
    private String img; //书的图片
    private int curPage; //当前阅读的章节数
    private int position; //当前阅读的章节位置
    private int total;//总的章节数
    private String type;//书的类型
    @Generated(hash = 2119959442)
    public BookCase(String bookname, String author, String time, String finish,
            String img, int curPage, int position, int total, String type) {
        this.bookname = bookname;
        this.author = author;
        this.time = time;
        this.finish = finish;
        this.img = img;
        this.curPage = curPage;
        this.position = position;
        this.total = total;
        this.type = type;
    }

    @Generated(hash = 563409161)
    public BookCase() {
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
