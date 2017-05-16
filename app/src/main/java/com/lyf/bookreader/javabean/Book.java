package com.lyf.bookreader.javabean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 ( 厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/4/24
 * @功能说明：  存放书籍的类
 * @begin
 * @修改记录: (暂时 不需要了)
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */
@Entity
public class Book {
    @Id(autoincrement = true)
    private Long id;
    @Property
    private String bookname; //书名
    @Property
    private int  page ; //代表每一章的章节数字
    private String  chaptername ;//章节名
    private String content ;//每一章的内容

    public Book(String bookname, int page, String chaptername, String content) {
        this.bookname = bookname;
        this.page = page;
        this.chaptername = chaptername;
        this.content = content;
    }

    public Book() {
    }

    @Generated(hash = 1495440271)
    public Book(Long id, String bookname, int page, String chaptername,
            String content) {
        this.id = id;
        this.bookname = bookname;
        this.page = page;
        this.chaptername = chaptername;
        this.content = content;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getChaptername() {
        return chaptername;
    }

    public void setChaptername(String chaptername) {
        this.chaptername = chaptername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", bookname='" + bookname + '\'' +
                ", page=" + page +
                ", chaptername='" + chaptername + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (page != book.page) return false;
        if (id != null ? !id.equals(book.id) : book.id != null) return false;
        if (bookname != null ? !bookname.equals(book.bookname) : book.bookname != null)
            return false;
        if (chaptername != null ? !chaptername.equals(book.chaptername) : book.chaptername != null)
            return false;
        return content != null ? content.equals(book.content) : book.content == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (bookname != null ? bookname.hashCode() : 0);
        result = 31 * result + page;
        result = 31 * result + (chaptername != null ? chaptername.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
