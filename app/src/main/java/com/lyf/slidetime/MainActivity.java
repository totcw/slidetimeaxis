package com.lyf.slidet;

import android.text.TextUtils;
import android.widget.Toast;

import com.betterda.mylibrary.ShapeLoadingDialog;
import com.lyf.slidetime.BaseCallModel;
import com.lyf.slidetime.R;
import com.lyf.slidetime.ReadView;
import com.lyf.slidetime.api.MyObserver;
import com.lyf.slidetime.api.NetWork;
import com.lyf.slidetime.base.BaseActivity;
import com.lyf.slidetime.base.IPresenter;
import com.lyf.slidetime.javabean.Book;
import com.lyf.slidetime.utils.UiUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private ReadView rv;
    private String bookName = "痞女军王全文阅读";
    private int total = 94;
    private ShapeLoadingDialog dialog;

    @Override
    protected IPresenter onLoadPresenter() {
        return null;
    }


    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_main);
        rv = (ReadView) findViewById(R.id.rv);
    }

    @Override
    public void init() {
        super.init();
        dialog = UiUtils.createDialog(getmActivity(), "正在加载...");

        mRxManager.add(NetWork.getNetService()
                .getRegister(bookName, 92 + "")
                .compose(NetWork.handleResult(new BaseCallModel<Book>()))
                .subscribe(new MyObserver<Book>() {
                    @Override
                    protected void onSuccess(Book data, String resultMsg) {

                        if (data != null && !TextUtils.isEmpty(data.getContent())) {

                            rv.drawCurPageBitmap(data.getTitle(), data.getContent(), 92, total);
                        } else {
                            Toast.makeText(getApplicationContext(), "加载当前内容失败", 0).show();
                        }
                    }

                    @Override
                    public void onFail(String resultMsg) {
                        System.out.println("res:" + resultMsg);
                        Toast.makeText(getApplicationContext(), "resultMsg:" + resultMsg, 0).show();
                    }

                    @Override
                    public void onExit() {
                        Toast.makeText(getApplicationContext(), "resu", 0).show();
                    }
                }));


        rv.setmLoadPageListener(new ReadView.LoadPageListener() {
            @Override
            public void prePage(final int chapter) {
                //TODO 根据书名和chapter 去数据库或者网络加载数据

              //  UiUtils.showDialog(getmActivity(),dialog);
                mRxManager.add(NetWork.getNetService()
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
                                    rv.setCurrentChapter(chapter + 1);
                                    Toast.makeText(getApplicationContext(), "加载上一页失败", 0).show();
                                }
                                rv.setLodaing(false);
                              //  UiUtils.dissmissDialog(getmActivity(),dialog);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                               // UiUtils.dissmissDialog(getmActivity(),dialog);
                                rv.setLodaing(false);
                            }

                            @Override
                            public void onExit() {
                               // UiUtils.dissmissDialog(getmActivity(),dialog);
                                rv.setLodaing(false);
                            }
                        }));
            }

            @Override
            public void nextPage(final int chapter) {
              //  UiUtils.showDialog(getmActivity(),dialog);
                mRxManager.add(NetWork.getNetService()
                        .getRegister(bookName, chapter + "")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyObserver<Book>() {
                            @Override
                            protected void onSuccess(Book data, String resultMsg) {
                                if (data != null && !TextUtils.isEmpty(data.getContent())) {

                                    rv.drawNextPage(data.getTitle(), data.getContent());
                                } else {
                                    rv.setCurrentChapter(chapter - 1);
                                    Toast.makeText(getApplicationContext(), "加载下一页失败", 0).show();
                                }
                              //  UiUtils.dissmissDialog(getmActivity(),dialog);
                                rv.setLodaing(false);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                //UiUtils.dissmissDialog(getmActivity(),dialog);
                                rv.setLodaing(false);
                            }

                            @Override
                            public void onExit() {
                               // UiUtils.dissmissDialog(getmActivity(),dialog);
                                rv.setLodaing(false);
                            }
                        }));

            }
        });
    }
}
