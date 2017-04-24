package com.lyf.bookreader.bookdetail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyf.bookreader.R;
import com.lyf.bookreader.base.BaseActivity;
import com.lyf.bookreader.bookdetail.contract.BookDetailContract;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/4/20
 * @功能说明： 书本详情界面
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class BookCaseActivity extends BaseActivity<BookDetailContract.Presenter> implements BookDetailContract.View {
    @BindView(R.id.iv_addbookcase_bookimage)
    ImageView mIvAddbookcaseBookimage;
    @BindView(R.id.iv_addbookcase_bookname)
    TextView mIvAddbookcaseBookname;
    @BindView(R.id.iv_addbookcase_author)
    TextView mIvAddbookcaseAuthor;
    @BindView(R.id.iv_addbookcase_time)
    TextView mIvAddbookcaseTime;
    @BindView(R.id.iv_addbookcase_status)
    TextView mIvAddbookcaseStatus;
    @BindView(R.id.iv_addbookcase_add)
    TextView mIvAddbookcaseAdd;



    @Override
    protected BookDetailContract.Presenter onLoadPresenter() {
      //  return new BookDetailPresenterImpl();
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_addbookcase);
    }

    @Override
    public void init() {
        super.init();


    }

    /**
     * 设置书本信息
     */
    public void setBookInformation(String author,String bookname,String time,String finish) {

        mIvAddbookcaseAuthor.setText(author);
        mIvAddbookcaseBookname.setText(bookname);
        mIvAddbookcaseTime.setText("最近更新时间:" + time);
        mIvAddbookcaseStatus.setText(finish);
        mIvAddbookcaseAdd.setVisibility(View.GONE);
    }

    @Override
    public void setAddBookcaseStatus(boolean isVisable) {
        if (isVisable) {
            mIvAddbookcaseAdd.setVisibility(View.VISIBLE);
        } else {
            mIvAddbookcaseAdd.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.iv_addbookcase_add)
    public void onClick() {
        getPresenter().addToBookcase();

    }
}
