package com.lyf.bookreader.readbook.presenter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.betterda.mylibrary.ShapeLoadingDialog;
import com.lyf.bookreader.R;
import com.lyf.bookreader.api.MyObserver;
import com.lyf.bookreader.api.NetWork;
import com.lyf.bookreader.base.BasePresenter;
import com.lyf.bookreader.javabean.BaseCallModel;
import com.lyf.bookreader.javabean.Book;
import com.lyf.bookreader.javabean.Chapter;
import com.lyf.bookreader.readbook.contract.BookReadContract;
import com.lyf.bookreader.utils.CacheUtils;
import com.lyf.bookreader.utils.Constants;
import com.lyf.bookreader.utils.UiUtils;
import com.lyf.bookreader.view.ReadView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 阅读界面
* Created by lyf on 2017/04/22
*/

public class BookReadPresenterImpl extends BasePresenter<BookReadContract.View,BookReadContract.Model> implements BookReadContract.Presenter{
    private String bookName; //书名
    private int chapter = 1;//当前阅读章节
    private int position = 0;//当前章节阅读位置
    private int total = 1;
    private ShapeLoadingDialog dialog; //加载对话框
    @Override
    public void start() {
        dialog = UiUtils.createDialog(getView().getmActivity(), "正在加载...");
        getIntents();
        boolean isNight = CacheUtils.getBoolean(getView().getmActivity(), Constants.ISNIGHT, false);
        changMode(isNight);
        getData(0);
        setReadViewListener();

    }

    private void setReadViewListener() {
        getView().getReadView().setmLoadPageListener(new ReadView.LoadPageListener() {
            @Override
            public void prePage(final int chapter) {
                //TODO 根据书名和chapter 去数据库或者网络加载数据
               getData(1);
            }

            @Override
            public void nextPage(final int chapter) {
              ;
                getData(2);

            }

            @Override
            public void showReadBar() {
                getView().showReadBar();
            }
        });
    }


    /**
     * 日间/夜间模 式 切换
     */
    public void changMode(boolean isNight) {
        CacheUtils.putBoolean(getView().getmActivity(),Constants.ISNIGHT,isNight);
        getView().getBookReadMode().setText(getView().getmActivity().getString(isNight ? R.string.book_read_mode_day_manual_setting
                : R.string.book_read_mode_night_manual_setting));
        Drawable drawable = ContextCompat.getDrawable(getView().getmActivity(), isNight ? R.mipmap.ic_menu_mode_day_normal
                : R.mipmap.ic_menu_mode_night_normal);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        getView().getBookReadMode().setCompoundDrawables(null, drawable, null, null);

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
        Intent intent = getView().getmActivity().getIntent();
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
    private void getData(final int type) {
        UiUtils.showDialog(getView().getmActivity(), dialog);
        getView().getRxManager().add(NetWork.getNetService()
                .getChpater(bookName, chapter + "")
                .compose(NetWork.handleResult(new BaseCallModel<Chapter>()))
                .subscribe(new MyObserver<Chapter>() {
                    @Override
                    protected void onSuccess(Chapter data, String resultMsg) {
                        if (type == 0) {
                            getCurContent(data);
                        } else if (type == 1) {
                            getPreContent(data);
                        } else if (type == 2) {
                            getNextContent(data);
                        }

                    }



                    @Override
                    public void onFail(String resultMsg) {

                        Toast.makeText(getView().getmActivity(), "resultMsg:" + resultMsg, Toast.LENGTH_SHORT).show();
                        UiUtils.dissmissDialog(getView().getmActivity(), dialog);
                    }

                    @Override
                    public void onExit() {
                        UiUtils.dissmissDialog(getView().getmActivity(), dialog);
                    }
                }));
    }

    /**
     * 获取下一章节的内容
     * @param data
     */
    private void getNextContent(Chapter data) {
        if (data != null && !TextUtils.isEmpty(data.getContent())) {

            getView().getReadView().drawNextPage(data.getTitle(), data.getContent());
        } else {
            getView().getReadView().setCurrentChapter(chapter - 1);
            Toast.makeText(getView().getmActivity(), "加载下一页失败", Toast.LENGTH_SHORT).show();
        }
        UiUtils.dissmissDialog(getView().getmActivity(), dialog);
        getView().getReadView().setLodaing(false);
    }

    /**
     * 获取前一章节的内容
     * @param data
     */
    private void getPreContent(Chapter data) {
        if (data != null && !TextUtils.isEmpty(data.getContent())) {

            getView().getReadView().drawPrePage(data.getTitle(), data.getContent());
        } else {
            //加载内存失败 记得要将章节调回去
            getView().getReadView().setCurrentChapter(chapter + 1);
            Toast.makeText(getView().getmActivity(), "加载上一页失败", Toast.LENGTH_SHORT).show();
        }
        getView().getReadView().setLodaing(false);
        UiUtils.dissmissDialog(getView().getmActivity(), dialog);
    }


    /**
     * 加载当前章节的内容
     * @param data
     */
    private void getCurContent(Chapter data) {
        if (data != null && !TextUtils.isEmpty(data.getContent())) {

            getView().getReadView().drawCurPageBitmap(data.getTitle(), data.getContent(), chapter, total);
        } else {
            Toast.makeText(getView().getmActivity(), "加载当前内容失败", Toast.LENGTH_SHORT).show();
        }
        UiUtils.dissmissDialog(getView().getmActivity(), dialog);
    }

    @Override
    public void download() {
        UiUtils.showToast(getView().getmActivity(),"缓存");
        RequestParams params=new RequestParams("http://192.168.0.112:8080/book/BookDownload");
        params.addBodyParameter("bookname",bookName);
        params.addBodyParameter("page",chapter+"");
        params.addBodyParameter("total",total+"");
        //设置断点续传
        params.setAutoResume(true);
        params.setMaxRetryCount(0);
        params.setConnectTimeout(1000*3600*10);//设置为10个小时
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
               // Toast.makeText(getView().getmActivity(),result,0).show();
                System.out.println(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void destroy() {

    }
}