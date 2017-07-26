package com.lyf.bookreader.welcome;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.lyf.yflibrary.Permission;
import com.example.lyf.yflibrary.PermissionResult;
import com.lyf.bookreader.api.MyObserver;
import com.lyf.bookreader.api.NetWork;
import com.lyf.bookreader.db.BookCaseDao;
import com.lyf.bookreader.db.BookReaderDBManager;
import com.lyf.bookreader.db.RecentlyReadDao;
import com.lyf.bookreader.home.HomeActivity;
import com.lyf.bookreader.javabean.BaseCallModel;
import com.lyf.bookreader.javabean.BookCase;
import com.lyf.bookreader.javabean.RecentlyRead;
import com.lyf.bookreader.readbook.BookReadActivity;
import com.lyf.bookreader.utils.CacheUtils;
import com.lyf.bookreader.utils.UiUtils;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

import static android.R.attr.data;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/5/23
 * @功能说明： 欢迎界面
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class WelcomeActivity extends AppCompatActivity {
    private String[] REQUEST_PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private RecentlyReadDao mRecentlyReadDao;
    private BookCaseDao mBookCaseDao;
    boolean first = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecentlyReadDao = BookReaderDBManager.getInstance().getDaoSession().getRecentlyReadDao();
        mBookCaseDao = BookReaderDBManager.getInstance().getDaoSession().getBookCaseDao();

        boolean isLoad = CacheUtils.getBoolean(this, "load", false);
        if (!isLoad) {
            //默认加载10本书
            getData();
        } else {
            startTopermission();
        }

    }

    /**
     * 获取权限
     */
    private void startTopermission() {


        if (Build.VERSION.SDK_INT < 23) {
            //6.0一下直接去主页
            starToHome();

        } else {
            //6.0以上请求权限
            Permission.checkPermisson(this, REQUEST_PERMISSIONS, new PermissionResult() {
                @Override
                public void success() {
                    starToHome();
                }

                @Override
                public void fail() {
                    finish();
                }
            });
        }
    }

    //跳转到首页
    public void  starToHome(){

        UiUtils.startIntent(this, HomeActivity.class);
        finish();

    }


    private void getData() {


        NetWork.getNetService().getBookList("类    别：玄幻奇幻","1","10")
                .compose(NetWork.handleResult(new BaseCallModel<List<BookCase>>()))
                .subscribe(new MyObserver<List<BookCase>>() {
                    @Override
                    protected void onSuccess(List<BookCase> data, String resultMsg) {
                        if (data != null&&data.size()>0) {
                            parserResult(data);
                            CacheUtils.putBoolean(WelcomeActivity.this,"load",true);
                        }
                        startTopermission();
                    }

                    @Override
                    public void onFail(String resultMsg) {

                    }

                    @Override
                    public void onExit() {

                    }
                });

    }

    private void parserResult(List<BookCase> data) {

                Observable.from(data)
                        .subscribe(new Action1<BookCase>() {
                            @Override
                            public void call(BookCase bookCase) {
                                if (bookCase != null) {
                                    if ("yiwanjie".equals(bookCase.getFinish())) {
                                        bookCase.setFinish("已完结");
                                    } else {
                                        bookCase.setFinish("连载中");
                                    }
                                    bookCase.setCurPage(1);
                                    if (first) {
                                        first = false;
                                        RecentlyRead recentlyRead = new RecentlyRead();
                                        setData(recentlyRead,bookCase);
                                        mRecentlyReadDao.insert(recentlyRead);
                                    }
                                    mBookCaseDao.insert(bookCase);
                                }

                            }
                        });


    }







    private void setData(RecentlyRead recentlyRead, BookCase bookCase) {
        recentlyRead.setBookname(bookCase.getBookname());
        recentlyRead.setBeginPos(bookCase.getBeginPos());
        recentlyRead.setEndPos(bookCase.getEndPos());
        recentlyRead.setAuthor(bookCase.getAuthor());
        recentlyRead.setCurPage(bookCase.getCurPage());
        recentlyRead.setFinish(bookCase.getFinish());
        recentlyRead.setImg(bookCase.getImg());
        recentlyRead.setType(bookCase.getType());
        recentlyRead.setTime(bookCase.getTime());
        recentlyRead.setTotal(bookCase.getTotal());
    }





}
