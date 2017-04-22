package com.lyf.bookreader.readbook;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.ShapeLoadingDialog;
import com.lyf.bookreader.javabean.BaseCallModel;
import com.lyf.bookreader.R;
import com.lyf.bookreader.javabean.Chapter;
import com.lyf.bookreader.view.ReadView;
import com.lyf.bookreader.api.MyObserver;
import com.lyf.bookreader.api.NetWork;
import com.lyf.bookreader.base.BaseActivity;
import com.lyf.bookreader.base.IPresenter;
import com.lyf.bookreader.utils.UiUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/4/20
 * @功能说明： 阅读界面
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class ReadBookActivity extends BaseActivity {

    private ReadView mReadView; //阅读控件
    private String bookName; //书名
    private int chapter = 1;//当前阅读章节
    private int position = 0;//当前章节阅读位置
    private int total = 1;
    private ShapeLoadingDialog dialog; //加载对话框

    @Override
    protected IPresenter onLoadPresenter() {
        return null;
    }


    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_main);
        mReadView = (ReadView) findViewById(R.id.readview);

    }



    @Override
    public void init() {
        super.init();
        dialog = UiUtils.createDialog(getmActivity(), "正在加载...");
        getIntents();
        getData();

        mReadView.setmLoadPageListener(new ReadView.LoadPageListener() {
            @Override
            public void prePage(final int chapter) {
                //TODO 根据书名和chapter 去数据库或者网络加载数据

                UiUtils.showDialog(getmActivity(),dialog);
                mRxManager.add(NetWork.getNetService()
                        .getChpater(bookName, chapter + "")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyObserver<Chapter>() {
                            @Override
                            protected void onSuccess(Chapter data, String resultMsg) {
                                if (data != null && !TextUtils.isEmpty(data.getContent())) {

                                    mReadView.drawPrePage(data.getTitle(), data.getContent());
                                } else {
                                    //加载内存失败 记得要将章节调回去
                                    mReadView.setCurrentChapter(chapter + 1);
                                    Toast.makeText(getApplicationContext(), "加载上一页失败", Toast.LENGTH_SHORT).show();
                                }
                                mReadView.setLodaing(false);
                                  UiUtils.dissmissDialog(getmActivity(),dialog);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                 UiUtils.dissmissDialog(getmActivity(),dialog);
                                mReadView.setLodaing(false);
                            }

                            @Override
                            public void onExit() {
                                 UiUtils.dissmissDialog(getmActivity(),dialog);
                                mReadView.setLodaing(false);
                            }
                        }));
            }

            @Override
            public void nextPage(final int chapter) {
                  UiUtils.showDialog(getmActivity(),dialog);
                mRxManager.add(NetWork.getNetService()
                        .getChpater(bookName, chapter + "")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyObserver<Chapter>() {
                            @Override
                            protected void onSuccess(Chapter data, String resultMsg) {
                                if (data != null && !TextUtils.isEmpty(data.getContent())) {

                                    mReadView.drawNextPage(data.getTitle(), data.getContent());
                                } else {
                                    mReadView.setCurrentChapter(chapter - 1);
                                    Toast.makeText(getApplicationContext(), "加载下一页失败", Toast.LENGTH_SHORT).show();
                                }
                                  UiUtils.dissmissDialog(getmActivity(),dialog);
                                mReadView.setLodaing(false);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                UiUtils.dissmissDialog(getmActivity(),dialog);
                                mReadView.setLodaing(false);
                            }

                            @Override
                            public void onExit() {
                                UiUtils.dissmissDialog(getmActivity(),dialog);
                                mReadView.setLodaing(false);
                            }
                        }));

            }
        });
    }


    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/20
     * @功能说明： 获取传递过来的数据
     */
    private void getIntents() {
        Intent intent = getIntent();
        if (intent != null) {
            bookName = intent.getStringExtra("bookname");
            total = intent.getIntExtra("total", 1);
            chapter = intent.getIntExtra("chapter", 1);
            position = intent.getIntExtra("position", 0);
        }
    }

    /**
     *@author : lyf
     *@email:totcw@qq.com
     *@创建日期： 2017/4/20
     *@功能说明： 加载当前章节的内容
     *@param
     *@return
     */
    private void getData() {
        UiUtils.showDialog(getmActivity(),dialog);
        mRxManager.add(NetWork.getNetService()
                .getChpater(bookName, chapter + "")
                .compose(NetWork.handleResult(new BaseCallModel<Chapter>()))
                .subscribe(new MyObserver<Chapter>() {
                    @Override
                    protected void onSuccess(Chapter data, String resultMsg) {

                        if (data != null && !TextUtils.isEmpty(data.getContent())) {

                            mReadView.drawCurPageBitmap(data.getTitle(), data.getContent(), chapter, total);
                        } else {
                            Toast.makeText(getApplicationContext(), "加载当前内容失败", Toast.LENGTH_SHORT).show();
                        }
                        UiUtils.dissmissDialog(getmActivity(),dialog);
                    }

                    @Override
                    public void onFail(String resultMsg) {

                        Toast.makeText(getApplicationContext(), "resultMsg:" + resultMsg, Toast.LENGTH_SHORT).show();
                        UiUtils.dissmissDialog(getmActivity(),dialog);
                    }

                    @Override
                    public void onExit() {
                        UiUtils.dissmissDialog(getmActivity(),dialog);
                    }
                }));
    }

    @Override
    public LoadingPager getLoadPager() {
        return null;
    }
}
