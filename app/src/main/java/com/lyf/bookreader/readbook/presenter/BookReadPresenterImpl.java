package com.lyf.bookreader.readbook.presenter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.betterda.mylibrary.ShapeLoadingDialog;
import com.lyf.bookreader.R;
import com.lyf.bookreader.api.DownloadAPI;
import com.lyf.bookreader.api.NetWork;
import com.lyf.bookreader.application.MyApplication;
import com.lyf.bookreader.base.BasePresenter;
import com.lyf.bookreader.db.BookCaseDao;
import com.lyf.bookreader.db.BookReaderDBManager;
import com.lyf.bookreader.javabean.BookCase;
import com.lyf.bookreader.javabean.Chapter;
import com.lyf.bookreader.readbook.DirectoryActivity;
import com.lyf.bookreader.readbook.contract.BookReadContract;
import com.lyf.bookreader.service.DownloadBookService;
import com.lyf.bookreader.utils.CacheUtils;
import com.lyf.bookreader.utils.Constants;
import com.lyf.bookreader.utils.FileUtils;
import com.lyf.bookreader.utils.UiUtils;
import com.lyf.bookreader.view.ReadView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 阅读界面
 * Created by lyf on 2017/04/22
 */

public class BookReadPresenterImpl extends BasePresenter<BookReadContract.View, BookReadContract.Model> implements BookReadContract.Presenter {
    private final static int REQUEST_DIRECTORY = 0;
    public final static String SERVICE_DOWNLOAD_REPLY = "SERVICE_DOWNLOAD_REPLY";
    private String bookName; //书名
    private int chapter = 1;//当前阅读章节
    private int mBeginPos = 0;//当前章节阅读开始位置
    private int mEndPos = 0;//当前章节阅读结束位置
    private int total = 1;
    private ShapeLoadingDialog dialog; //加载对话框
    private BookCaseDao mBookCaseDao;

    @Override
    public void start() {
        dialog = UiUtils.createDialog(getView().getmActivity(), "正在加载...");

        mBookCaseDao = BookReaderDBManager.getInstance().getDaoSession().getBookCaseDao();

        getIntents();

        boolean isNight = CacheUtils.getBoolean(getView().getmActivity(), Constants.ISNIGHT, false);
        changMode(isNight);

        getSaveProgress();

        setReadViewListener();
        /**
         * 注册接收缓存的回复信息
         */
        getView().getRxManager().on(SERVICE_DOWNLOAD_REPLY, new Action1<String>() {
            @Override
            public void call(String string) {

                if (getView() != null && getView().getmActivity() != null) {
                    UiUtils.showToast(getView().getmActivity(), string);
                }

            }
        });


    }

    /**
     * 设置翻页监听
     */
    private void setReadViewListener() {
        getView().getReadView().setmLoadPageListener(new ReadView.LoadPageListener() {
            @Override
            public void prePage(final int chapter) {
                //TODO 根据书名和chapter 去本地或者网络加载数据
                BookReadPresenterImpl.this.chapter = chapter;
                loadCurrentChapter(1);
            }

            @Override
            public void nextPage(final int chapter) {
                BookReadPresenterImpl.this.chapter = chapter;
                loadCurrentChapter(2);

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
        CacheUtils.putBoolean(getView().getmActivity(), Constants.ISNIGHT, isNight);
        getView().getBookReadMode().setText(getView().getmActivity().getString(isNight ? R.string.book_read_mode_day_manual_setting
                : R.string.book_read_mode_night_manual_setting));
        Drawable drawable = ContextCompat.getDrawable(getView().getmActivity(), isNight ? R.drawable.ic_action_sun
                : R.drawable.ic_action_sun);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        getView().getBookReadMode().setCompoundDrawables(null, drawable, null, null);

        getView().getReadView().changeMode(isNight);

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
        }
    }

    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/27
     * @功能说明：加载当前章节内容
     */
    public void loadCurrentChapter(final int type) {

        getView().getRxManager().add(Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override
            public void call(Subscriber<? super byte[]> subscriber) {
                //根据书名和章节获取本地文件
                File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + bookName, chapter + ".txt");
                byte[] bytes = null;
                if (outputFile.exists()) {
                    try {
                        bytes = FileUtils.readStreamToBytes(new FileInputStream(outputFile));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onNext(bytes);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<byte[]>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(byte[] bytes) {
                        if (bytes != null) {
                            parserChapterForString(new String(bytes),type);
                        } else {
                            getData(type);
                        }
                    }
                }));


    }

    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/20
     * @功能说明： 从服务器加载当前章节的内容
     */
    private void getData(final int type) {
        UiUtils.showDialog(getView().getmActivity(), dialog);

        getView().getRxManager().add(
                DownloadAPI.getmDownloadService()
                        .download(bookName, chapter + "")
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .map(new Func1<ResponseBody, InputStream>() {
                            @Override
                            public InputStream call(ResponseBody responseBody) {
                                return responseBody.byteStream();
                            }
                        })
                        .map(new Func1<InputStream, String>() {
                            @Override
                            public String call(InputStream inputStream) {
                                //用字节输出流来当缓存,保存输入流数据
                                ByteArrayOutputStream out = new ByteArrayOutputStream();
                                byte[] buffer = new byte[1024 * 128];
                                int len = -1;
                                try {
                                    while ((len = inputStream.read(buffer)) != -1) {
                                        out.write(buffer, 0, len);
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        if (out != null) {

                                            out.flush();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        if (out != null) {

                                            out.close();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        if (inputStream != null) {

                                            inputStream.close();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                return out.toString();
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                getView().getReadView().setCurrentChapter(chapter - 1);
                                getView().getReadView().setLodaing(false);
                                UiUtils.dissmissDialog(getView().getmActivity(), dialog);
                            }

                            @Override
                            public void onNext(String content) {
                                parserChapterForString(content, type);
                            }
                        })
        );
    }



    /**
     * 获取下一章节的内容
     *
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
     *
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
     *
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
    /**
     *@author : lyf
     *@email:totcw@qq.com
     *@创建日期： 2017/5/9
     *@功能说明：从文本中解析章节信息
     *@param
     *@return
     */
    private void parserChapterForString(String content, int type) {

        int indexOf = content.indexOf("@@");
        //获取章节名
        String title = content.substring(0, indexOf);
        String book = content.substring(indexOf + 2);
        Chapter chapter = new Chapter();
        chapter.setTitle(title);
        //同时去除空格
        chapter.setContent(book.replaceAll("\\s*", ""));
        parseChapterContent(chapter, type);
    }

    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/27
     * @功能说明：解析章节信息
     */
    private void parseChapterContent(Chapter data, int type) {
        if (type == 0) {
            getCurContent(data);
        } else if (type == 1) {
            getPreContent(data);
        } else if (type == 2) {
            getNextContent(data);
        }
    }


    @Override
    public void download(int type) {
        Intent intent = new Intent(getView().getmActivity(), DownloadBookService.class);
        intent.putExtra("bookname", bookName);
        switch (type) {
            case 0://全本
                intent.putExtra("chapter", 1);
                intent.putExtra("total", total);
                break;
            case 1://后面50章
                intent.putExtra("chapter", chapter);
                intent.putExtra("total", chapter + 49);
                break;
            case 2://剩余章节
                intent.putExtra("chapter", chapter);
                intent.putExtra("total", total);
                break;
        }
        getView().getmActivity().startService(intent);


    }


    @Override
    public void getDirectory() {
        Intent intent = new Intent(getView().getmActivity(), DirectoryActivity.class);
        intent.putExtra("bookname", bookName);
        UiUtils.startIntentForResult(getView().getmActivity(), intent, REQUEST_DIRECTORY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_DIRECTORY == requestCode) {
            if (data != null) {
                chapter = data.getIntExtra("page", chapter);
                loadCurrentChapter(0);
            }
        }
    }

    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/27
     * @功能说明：保存阅读进度
     */
    @Override
    public void saveProgress() {
        if (getView() == null || getView().getReadView() == null) {
            return;
        }

        mBeginPos = getView().getReadView().getBeginPos();

        getView().getRxManager().add(
                Observable.create(new Observable.OnSubscribe<List<BookCase>>() {
                    @Override
                    public void call(Subscriber<? super List<BookCase>> subscriber) {
                        subscriber.onNext(mBookCaseDao.queryBuilder().where(BookCaseDao.Properties.Bookname.eq(bookName)).list());
                    }
                }).map(new Func1<List<BookCase>, BookCase>() {
                    @Override
                    public BookCase call(List<BookCase> bookCases) {
                        if (bookCases != null && bookCases.size() > 0) {
                            return bookCases.get(0);
                        }
                        return null;
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe(new Action1<BookCase>() {
                            @Override
                            public void call(BookCase bookCase) {

                                bookCase.setCurPage(chapter);
                                bookCase.setBeginPos(mBeginPos);
                                bookCase.setEndPos(mBeginPos);

                                if (bookCase != null) {
                                    mBookCaseDao.update(bookCase);
                                }
                            }
                        })
        );
    }

    @Override
    public void delFont() {
        int fontSize = getView().getReadView().getFontSize();
        setFontSize(--fontSize);
    }

    @Override
    public void addFont() {
        int fontSize = getView().getReadView().getFontSize();
        setFontSize(++fontSize);
    }

    private void setFontSize(int fontSize2) {
        getView().getReadView().setFontSize(fontSize2);
        getView().getReadView().reCalculate();
        getView().getReadView().reDrawContent();
    }

    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/27
     * @功能说明：获取阅读进度
     */
    public void getSaveProgress() {
        getView().getRxManager().add(
                Observable.create(new Observable.OnSubscribe<List<BookCase>>() {
                    @Override
                    public void call(Subscriber<? super List<BookCase>> subscriber) {
                        subscriber.onNext(mBookCaseDao.queryBuilder().where(BookCaseDao.Properties.Bookname.eq(bookName)).list());
                    }
                })
                        .map(new Func1<List<BookCase>, BookCase>() {
                            @Override
                            public BookCase call(List<BookCase> bookCases) {
                                if (bookCases != null && bookCases.size() > 0) {
                                    return bookCases.get(0);
                                }
                                return null;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<BookCase>() {
                            @Override
                            public void call(BookCase bookCase) {
                                if (bookCase != null) {
                                    mBeginPos = bookCase.getMBeginPos();
                                    mEndPos = bookCase.getEndPos();
                                    chapter = bookCase.getCurPage();

                                    getView().getReadView().setEndPos(mEndPos);
                                    getView().getReadView().setBeginPos(mBeginPos);
                                }
                                loadCurrentChapter(0);
                            }
                        })
        );
    }


    @Override
    public void destroy() {
        saveProgress();
    }
}