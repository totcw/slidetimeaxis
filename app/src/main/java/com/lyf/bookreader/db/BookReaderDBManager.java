package com.lyf.bookreader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author ganlin
 * @email ganlin@zhangkongapp.com
 * @time 2017/5/15 16:24
 * @company 长沙掌控信息科技有限公司
 * @desc 数据库管理类
 */

public final class BookReaderDBManager {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static final String DB_NAME = "book_reader.db";

    private static class BookReaderDBManagerHolder {
        private static final BookReaderDBManager INSTANCE = new BookReaderDBManager();
    }

    private BookReaderDBManager() {
    }

    public static final BookReaderDBManager getInstance() {
        return BookReaderDBManagerHolder.INSTANCE;
    }


    public final DaoSession getDaoSession() {
        return mDaoSession;
    }

    public final SQLiteDatabase getDb() {
        return db;
    }

    public final void init(Context context) {
        mHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Ses sion 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
}
