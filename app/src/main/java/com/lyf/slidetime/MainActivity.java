package com.lyf.slidet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.lyf.slidetime.AppUtils;
import com.lyf.slidetime.MyObserver;
import com.lyf.slidetime.NetWork;
import com.lyf.slidetime.ReadView;
import com.lyf.slidetime.javabean.Book;
import com.lyf.slidetimeaxis.XLHStepView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ReadView rv;
    private String bookName = "痞女军王全文阅读";
    private int total = 94;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.init(this);
        setContentView(R.layout.activity_main);
        rv = (ReadView) findViewById(R.id.rv);

        NetWork.getNetService()
                .getRegister(bookName, 92+ "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<Book>() {
                    @Override
                    protected void onSuccess(Book data, String resultMsg) {

                        if (data != null && !TextUtils.isEmpty(data.getContent())) {

                            rv.drawCurPageBitmap(data.getTitle(), data.getContent(), 92,total);
                        } else {
                            Toast.makeText(getApplicationContext(),"加载当前内容失败",0).show();
                        }
                    }

                    @Override
                    public void onFail(String resultMsg) {
                        System.out.println("res:"+resultMsg);
                        Toast.makeText(getApplicationContext(),"resultMsg:"+resultMsg,0).show();
                    }

                    @Override
                    public void onExit() {
                        Toast.makeText(getApplicationContext(),"resu",0).show();
                    }
                });

        rv.setmLoadPageListener(new ReadView.LoadPageListener() {
            @Override
            public void prePage(final int chapter) {
                //TODO 根据书名和chapter 去数据库或者网络加载数据

                NetWork.getNetService()
                        .getRegister(bookName, chapter + "")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyObserver<Book>() {
                            @Override
                            protected void onSuccess(Book data, String resultMsg) {
                                if (data != null && !TextUtils.isEmpty(data.getContent())) {

                                    rv.drawPrePage(data.getTitle(), data.getContent());
                                } else {
                                    //加载内存失败 记得要将章节调回去
                                    rv.setCurrentChapter(chapter+1);
                                    Toast.makeText(getApplicationContext(),"加载上一页失败",0).show();
                                }
                            }

                            @Override
                            public void onFail(String resultMsg) {

                            }

                            @Override
                            public void onExit() {

                            }
                        });
            }

            @Override
            public void nextPage(final int chapter) {


                NetWork.getNetService()
                        .getRegister(bookName, chapter + "")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyObserver<Book>() {
                            @Override
                            protected void onSuccess(Book data, String resultMsg) {
                                if (data != null && !TextUtils.isEmpty(data.getContent())) {

                                    rv.drawNextPage(data.getTitle(), data.getContent());
                                } else {
                                    rv.setCurrentChapter(chapter-1);
                                    Toast.makeText(getApplicationContext(),"加载下一页失败",0).show();
                                }
                            }

                            @Override
                            public void onFail(String resultMsg) {

                            }

                            @Override
                            public void onExit() {

                            }
                        });

            }
        });
    }
}
