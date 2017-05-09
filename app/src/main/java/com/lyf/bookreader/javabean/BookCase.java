package com.lyf.bookreader.javabean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 *@版权：版权所有 (厦门北特达软件有限公司) 2017
 *@author : lyf
 *@version : 1.0.0
 *@email:totcw@qq.com
 *@see:
 *@创建日期： 2017/5/9
 *@功能说明：
 *@begin
 *@修改记录:
 *@修改后版本:
 *@修改人:
 *@修改内容: time修改成用来表示书的简介而不是更新时间了
 *@end
 */
@Entity
public class BookCase {
    @Id(autoincrement = true)
    private Long id;
    private String bookname;//书名
    private String author; //作者
    private String time; //简介
    private String finish;//是否完结
    private String img; //书的图片
    private int curPage; //当前阅读的章节数
    private int mBeginPos; //当前阅读的章节开始位置
    private int mEndPos; //当前阅读的章节结束位置
    private int total;//总的章节数
    private String type;//书的类型

   @Generated(hash = 680762424)
public BookCase(Long id, String bookname, String author, String time, String finish, String img, int curPage, int mBeginPos, int mEndPos, int total, String type) {
    this.id = id;
    this.bookname = bookname;
    this.author = author;
    this.time = time;
    this.finish = finish;
    this.img = img;
    this.curPage = curPage;
    this.mBeginPos = mBeginPos;
    this.mEndPos = mEndPos;
    this.total = total;
    this.type = type;
}



    @Generated(hash = 563409161)
    public BookCase() {
    }

    public Long getId() {
        return id;
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

    public int getBeginPos() {
        return mBeginPos;
    }

    public void setBeginPos(int beginPos) {
        mBeginPos = beginPos;
    }

    public int getEndPos() {
        return mEndPos;
    }

    public void setEndPos(int endPos) {
        mEndPos = endPos;
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
    public void setId(Long id) {
        this.id = id;
    }



    public int getMEndPos() {
        return this.mEndPos;
    }



    public void setMEndPos(int mEndPos) {
        this.mEndPos = mEndPos;
    }



    public int getMBeginPos() {
        return this.mBeginPos;
    }



    public void setMBeginPos(int mBeginPos) {
        this.mBeginPos = mBeginPos;
    }
}
