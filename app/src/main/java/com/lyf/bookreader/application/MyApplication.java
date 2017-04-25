package com.lyf.bookreader.application;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.lyf.bookreader.db.DaoMaster;
import com.lyf.bookreader.db.DaoSession;
import com.lyf.bookreader.utils.AppUtils;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * Created by Administrator on 2016/7/28.
 */
public class MyApplication extends Application {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static final String DB_NAME = "book_reader.db";
    private List<Activity> list;


    private static MyApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        if (null == list) {
            list = new ArrayList<>();
        }
        AppUtils.init(this);

        //捕获异常
        // CrashHandler.getInstance().init(getApplicationContext());
        //初始化GreenDao
        initGreenDao();
        //初始化xutils3
        x.Ext.init(this);
    }

    private void initGreenDao() {
        mHelper = new DaoMaster.DevOpenHelper(this, DB_NAME, null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Ses sion 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    /**
     * 将activity添加到容器中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (null != list) {
            list.add(activity);
        }

    }

    /**
     * 退出程序
     */
    public void exitProgress() {

        if (null != list) {

            for (Activity activity : list) {
                activity.finish();
            }
            //将容器清空
            list.clear();


        }


    }

    /**
     * 当activity销毁时调用该方法,防止内存泄漏
     *
     * @param activity
     */
    public void removeAcitivty(Activity activity) {
        if (null != list && activity != null) {

            list.remove(activity);
        }
    }


    public static MyApplication getInstance() {
        return instance;
    }


}
