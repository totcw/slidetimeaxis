package com.lyf.slidetime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2017/4/19.
 */

public class BookCaseDao {
    private BookCaseDbHelper mBookCaseDbHelper;
    private Context mContext;

    public BookCaseDao(Context mContext) {
        this.mContext = mContext;
        mBookCaseDbHelper = new BookCaseDbHelper(mContext);
    }

    public void add() {
        SQLiteDatabase db = mBookCaseDbHelper.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put("bookname", "Jne");
        contentValues.put("curPage", 1);
        contentValues.put("position", "1");
        contentValues.put("author", "作者");
        contentValues.put("time", "17-04-18");
        contentValues.put("finish", "已完结");
        contentValues.put("img", "imgurl");
        db.insertOrThrow(BookCaseDbHelper.TABLE_NAME, null, contentValues);
        db.setTransactionSuccessful();
        db.close();
    }

    public void delete(String bookname) {
        SQLiteDatabase db = mBookCaseDbHelper.getWritableDatabase();
        db.beginTransaction();

        db.delete(BookCaseDbHelper.TABLE_NAME, "bookname = ?", new String[]{bookname});
        db.setTransactionSuccessful();
        db.close();
    }

    public void update(String bookname, int page, int position) {
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
        db.close();
    }

    public void query(String bookname) {
        SQLiteDatabase db = mBookCaseDbHelper.getReadableDatabase();


        //参数1：表名
//参数2：要想显示的列
//参数3：where子句
//参数4：where子句对应的条件值
//参数5：分组方式
//参数6：having条件
//参数7：排序方式
        // Cursor cursor = db.query(BookCaseDbHelper.TABLE_NAME, new String[]{"id", "bookname", "curPage", "position", "author", "time", "finish", "img"}, "bookname=?", new String[]{bookname}, null, null, null);
        Cursor cursor = db.query(BookCaseDbHelper.TABLE_NAME, new String[]{"id", "bookname", "curPage", "position", "author", "time", "finish", "img"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("bookname"));
            int curPage = cursor.getInt(cursor.getColumnIndex("curPage"));
            int position = cursor.getInt(cursor.getColumnIndex("position"));
            String author = cursor.getString(cursor.getColumnIndex("author"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String finish = cursor.getString(cursor.getColumnIndex("finish"));
            String img = cursor.getString(cursor.getColumnIndex("img"));

        }

        db.close();
    }
}
