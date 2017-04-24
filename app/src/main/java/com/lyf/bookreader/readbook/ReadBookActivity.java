package com.lyf.bookreader.readbook;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.ShapeLoadingDialog;
import com.lyf.bookreader.R;
import com.lyf.bookreader.api.MyObserver;
import com.lyf.bookreader.api.NetWork;
import com.lyf.bookreader.base.BaseActivity;
import com.lyf.bookreader.base.IPresenter;
import com.lyf.bookreader.javabean.BaseCallModel;
import com.lyf.bookreader.javabean.Book;
import com.lyf.bookreader.javabean.Chapter;
import com.lyf.bookreader.utils.CacheUtils;
import com.lyf.bookreader.utils.Constants;
import com.lyf.bookreader.utils.GLog;
import com.lyf.bookreader.utils.UiUtils;
import com.lyf.bookreader.view.ReadView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Observer;
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

    @BindView(R.id.linear_bookread_top)
    LinearLayout mLinearBookreadTop;
    @BindView(R.id.linear_bookread_bottom)
    LinearLayout mLinearBookreadBottom;
    @BindView(R.id.tv_bookread_mode)
    TextView mTvBookreadMode;
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
        setContentView(R.layout.activity_bookread);
        mReadView = (ReadView) findViewById(R.id.readview);

    }


    @Override
    public void init() {
        super.init();
        dialog = UiUtils.createDialog(getmActivity(), "正在加载...");
        getIntents();
        boolean isNight = CacheUtils.getBoolean(getmActivity(), Constants.ISNIGHT, false);
        changMode(isNight);
       // getData();
        mReadView.setmLoadPageListener(new ReadView.LoadPageListener() {
            @Override
            public void prePage(final int chapter) {
                //TODO 根据书名和chapter 去数据库或者网络加载数据

                UiUtils.showDialog(getmActivity(), dialog);
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
                                UiUtils.dissmissDialog(getmActivity(), dialog);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                UiUtils.dissmissDialog(getmActivity(), dialog);
                                mReadView.setLodaing(false);
                            }

                            @Override
                            public void onExit() {
                                UiUtils.dissmissDialog(getmActivity(), dialog);
                                mReadView.setLodaing(false);
                            }
                        }));
            }

            @Override
            public void nextPage(final int chapter) {
                UiUtils.showDialog(getmActivity(), dialog);
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
                                UiUtils.dissmissDialog(getmActivity(), dialog);
                                mReadView.setLodaing(false);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                UiUtils.dissmissDialog(getmActivity(), dialog);
                                mReadView.setLodaing(false);
                            }

                            @Override
                            public void onExit() {
                                UiUtils.dissmissDialog(getmActivity(), dialog);
                                mReadView.setLodaing(false);
                            }
                        }));

            }

            @Override
            public void showReadBar() {
                ReadBookActivity.this.showReadBar(mLinearBookreadTop,mLinearBookreadBottom);
            }
        });
    }


    /**
     *@author : lyf
     *@email:totcw@qq.com
     *@创建日期： 2017/4/24
     *@功能说明：显示阅读状态栏
     *@param
     *@return
     */
    public void showReadBar(View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    if (view.getVisibility() == View.GONE) {
                        view.setVisibility(View.VISIBLE);
                    } else {
                        view.setVisibility(View.GONE);
                    }
                }
            }
        }

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
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/20
     * @功能说明： 加载当前章节的内容
     */
    private void getData() {
        UiUtils.showDialog(getmActivity(), dialog);
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
                        UiUtils.dissmissDialog(getmActivity(), dialog);
                    }

                    @Override
                    public void onFail(String resultMsg) {

                        Toast.makeText(getApplicationContext(), "resultMsg:" + resultMsg, Toast.LENGTH_SHORT).show();
                        UiUtils.dissmissDialog(getmActivity(), dialog);
                    }

                    @Override
                    public void onExit() {
                        UiUtils.dissmissDialog(getmActivity(), dialog);
                    }
                }));
    }

    @Override
    public LoadingPager getLoadPager() {
        return null;
    }



    @OnClick({R.id.iv_bookread_back, R.id.tv_bookread_source, R.id.tv_bookread_mode, R.id.tv_bookread_setting, R.id.tv_bookread_download, R.id.tv_bookread_directory})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bookread_back:
                back();
                break;
            case R.id.tv_bookread_source:
                break;
            case R.id.tv_bookread_mode://模式选择
                boolean isNight = !CacheUtils.getBoolean(getmActivity(), Constants.ISNIGHT, false);
                changMode(isNight);
                break;
            case R.id.tv_bookread_setting:
                break;
            case R.id.tv_bookread_download://缓存
                Toast.makeText(getmActivity(),"缓存",0).show();
                downloadBook();
                break;
            case R.id.tv_bookread_directory:
                break;
        }
    }

    private void downloadBook() {
        mRxManager.add(NetWork.getNetService().getBook()
            .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getmActivity(),e.toString(),0).show();
                    }

                    @Override
                    public void onNext(ResponseBody book) {
                        Toast.makeText(getmActivity(),book.toString(),0).show();
                    }
                }));
    }

    /**
     * 日间/夜间模式切换
     */
    private void changMode(boolean isNight) {
        CacheUtils.putBoolean(getmActivity(),Constants.ISNIGHT,isNight);
        mTvBookreadMode.setText(getString(isNight ? R.string.book_read_mode_day_manual_setting
                : R.string.book_read_mode_night_manual_setting));
        Drawable drawable = ContextCompat.getDrawable(this, isNight ? R.mipmap.ic_menu_mode_day_normal
                : R.mipmap.ic_menu_mode_night_normal);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTvBookreadMode.setCompoundDrawables(null, drawable, null, null);

    }
}
