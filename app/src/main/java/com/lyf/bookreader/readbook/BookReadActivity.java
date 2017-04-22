package com.lyf.bookreader.readbook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.betterda.mylibrary.ShapeLoadingDialog;
import com.lyf.bookreader.R;
import com.lyf.bookreader.api.MyObserver;
import com.lyf.bookreader.api.NetWork;
import com.lyf.bookreader.base.BaseActivity;
import com.lyf.bookreader.javabean.BaseCallModel;
import com.lyf.bookreader.javabean.Chapter;
import com.lyf.bookreader.readbook.contract.BookReadContract;
import com.lyf.bookreader.readbook.presenter.BookReadPresenterImpl;
import com.lyf.bookreader.utils.UiUtils;
import com.lyf.bookreader.view.ReadView;

import butterknife.BindView;
import butterknife.ButterKnife;
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

public class BookReadActivity extends BaseActivity<BookReadContract.Presenter> implements BookReadContract.View {

    @BindView(R.id.readview)
    ReadView mReadView;//阅读控件



    @Override
    protected BookReadContract.Presenter onLoadPresenter() {
        return new BookReadPresenterImpl();
    }


    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_bookread);


    }



    @Override
    public ReadView getReadView() {
        return mReadView;
    }
}
