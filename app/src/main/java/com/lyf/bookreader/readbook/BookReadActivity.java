package com.lyf.bookreader.readbook;


import com.lyf.bookreader.R;
import com.lyf.bookreader.base.BaseActivity;
import com.lyf.bookreader.readbook.contract.BookReadContract;
import com.lyf.bookreader.view.ReadView;

import butterknife.BindView;

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
        //return new BookReadPresenterImpl();
        return null;
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
