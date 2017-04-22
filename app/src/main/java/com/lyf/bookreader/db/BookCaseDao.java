package com.lyf.bookreader.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.lyf.bookreader.javabean.BookCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public class BookCaseDao {
    private BookCaseDbHelper mBookCaseDbHelper;
    private Context mContext;

    public BookCaseDao(Context mContext) {
        this.mContext = mContext;
        mBookCaseDbHelper = BookCaseDbHelper.getInstance(mContext);
    }

    public long add(BookCase bookCase) {
        long count =0;
        synchronized (mBookCaseDbHelper) {


            SQLiteDatabase db = mBookCaseDbHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put("bookname", bookCase.getBookname());
            contentValues.put("curPage", bookCase.getCurPage());
            contentValues.put("position", bookCase.getPosition());
            contentValues.put("author", bookCase.getAuthor());
            contentValues.put("time", bookCase.getTime());
            contentValues.put("finish", bookCase.getFinish());
            contentValues.put("img", bookCase.getImg());
            contentValues.put("total", bookCase.getTotal());
            contentValues.put("type", bookCase.getType());
            count = db.insertOrThrow(BookCaseDbHelper.TABLE_NAME, null, contentValues);
            System.out.println("count:"+count);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        return count;
    }

    public void delete(String bookname) {
        synchronized (mBookCaseDbHelper) {

            SQLiteDatabase db = mBookCaseDbHelper.getWritableDatabase();
            db.beginTransaction();

            db.delete(BookCaseDbHelper.TABLE_NAME, "bookname = ?", new String[]{bookname});
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
    }

    public void update(String bookname, int page, int position) {
        synchronized (mBookCaseDbHelper) {

            SQLiteDatabase db = mBookCaseDbHelper.getWritableDatabase();
            db.beginTransaction();

// update Orders set OrderPrice = 800 where Id = 6
            ContentValues cv = new ContentValues();
            cv.put("curPage", page);
            cv.put("position", position);
            db.update(BookCaseDbHelper.TABLE_NAME,
                    cv,
                    "bookname = ?",
                    new String[]{bookname});
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
    }

    public BookCase query(String bookname) {
        BookCase bookCase = new BookCase();
        synchronized (mBookCaseDbHelper) {

            SQLiteDatabase db = mBookCaseDbHelper.getReadableDatabase();


            //参数1：表名
            //参数2：要想显示的列
            //参数3：where子句
            //参数4：where子句对应的条件值
            //参数5：分组方式
            //参数6：having条件
            //参数7：排序方式
            // Cursor cursor = db.query(BookCaseDbHelper.TABLE_NAME, new String[]{"id", "bookname", "curPage", "position", "author", "time", "finish", "img"}, "bookname=?", new String[]{bookname}, null, null, null);
            Cursor cursor = db.query(BookCaseDbHelper.TABLE_NAME, new String[]{"id", "bookname", "curPage", "position", "author", "time", "finish", "img","type","total"}, "bookname=?", new String[]{bookname}, null, null, null);
            while (cursor.moveToNext()) {

                bookCase.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
                bookCase.setBookname(cursor.getString(cursor.getColumnIndex("bookname"))); ;
                bookCase.setCurPage(cursor.getInt(cursor.getColumnIndex("curPage")));
                bookCase.setPosition(cursor.getInt(cursor.getColumnIndex("position")));
                bookCase.setTime(cursor.getString(cursor.getColumnIndex("time")));
                bookCase.setFinish(cursor.getString(cursor.getColumnIndex("finish")));
                bookCase.setImg(cursor.getString(cursor.getColumnIndex("img")));
                bookCase.setType(cursor.getString(cursor.getColumnIndex("type")));
                bookCase.setTotal(cursor.getInt(cursor.getColumnIndex("total")));

            }
            System.out.println("bookname:"+bookCase.getBookname());
            db.close();
        }
        return bookCase;
    }

    public List<BookCase> queryAll() {
        List<BookCase> list = new ArrayList<>();
        synchronized (mBookCaseDbHelper) {

            SQLiteDatabase db = mBookCaseDbHelper.getReadableDatabase();


            //参数1：表名
            //参数2：要想显示的列
            //参数3：where子句
            //参数4：where子句对应的条件值
            //参数5：分组方式
            //参数6：having条件
            //参数7：排序方式
            // Cursor cursor = db.query(BookCaseDbHelper.TABLE_NAME, new String[]{"id", "bookname", "curPage", "position", "author", "time", "finish", "img"}, "bookname=?", new String[]{bookname}, null, null, null);
            Cursor cursor = db.query(BookCaseDbHelper.TABLE_NAME, new String[]{"id", "bookname", "curPage", "position", "author", "time", "finish", "img","type","total"}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                BookCase bookCase = new BookCase();
                bookCase.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
                bookCase.setBookname(cursor.getString(cursor.getColumnIndex("bookname"))); ;
                bookCase.setCurPage(cursor.getInt(cursor.getColumnIndex("curPage")));
                bookCase.setPosition(cursor.getInt(cursor.getColumnIndex("position")));
                bookCase.setTime(cursor.getString(cursor.getColumnIndex("time")));
                bookCase.setFinish(cursor.getString(cursor.getColumnIndex("finish")));
                bookCase.setImg(cursor.getString(cursor.getColumnIndex("img")));
                bookCase.setType(cursor.getString(cursor.getColumnIndex("type")));
                bookCase.setTotal(cursor.getInt(cursor.getColumnIndex("total")));
                list.add(bookCase);

            }

            db.close();
        }
        return list;
    }
}
