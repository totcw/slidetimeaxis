package com.lyf.bookreader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 书架的数据库管理类
 * Created by Administrator on 2017/4/19.
 */

public class BookCaseDbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "bookcase.db";
    public static final String TABLE_NAME = "bookcase";
    private static BookCaseDbHelper mInstance;
    public BookCaseDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }



    public synchronized static BookCaseDbHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BookCaseDbHelper(context);
        }
        return mInstance;
    };

    @Override
    public void onCreate(SQLiteDatabase db) {
        // bookname 书名 curpage 当前阅读的章节 position 当前阅读的章节的位置 author 作者 time 时间 finish 完成状态, img 图片

        String sql = "create table if not exists " + TABLE_NAME + " (Id integer primary key autoincrement, bookname text, curPage integer,position integer, author text,time text,finish text,img text,type text,total integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
