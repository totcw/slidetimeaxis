package com.lyf.bookreader.bookdetail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lyf.bookreader.R;
import com.lyf.bookreader.base.BaseActivity;
import com.lyf.bookreader.bookdetail.contract.BookDetailContract;
import com.lyf.bookreader.bookdetail.presenter.BookDetailPresenterImpl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/4/20
 * @功能说明： 书本详情界  面
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class BookDetailActivity extends BaseActivity<BookDetailContract.Presenter> implements BookDetailContract.View {
    @BindView(R.id.iv_addbookcase_bookimage)
    ImageView mIvAddbookcaseBookimage;
    @BindView(R.id.iv_addbookcase_bookname)
    TextView mIvAddbookcaseBookname;
    @BindView(R.id.iv_addbookcase_author)
    TextView mIvAddbookcaseAuthor;

    @BindView(R.id.iv_addbookcase_status)
    TextView mIvAddbookcaseStatus;
    @BindView(R.id.iv_addbookcase_add)
    TextView mIvAddbookcaseAdd;
    @BindView(R.id.tv_addbookcase_introduce)
    TextView mTvAddbookcaseIntroduce;



    @Override
    protected BookDetailContract.Presenter onLoadPresenter() {
       return new BookDetailPresenterImpl();

    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_addbookcase);
    }


    /**
     * 设置 书本信息
     */
    public void setBookInformation(String author,String bookname,String time,String finish,String img) {

        mIvAddbookcaseAuthor.setText(author);
        mIvAddbookcaseBookname.setText(bookname);
        mIvAddbookcaseStatus.setText(finish);
        mIvAddbookcaseAdd.setVisibility(View.GONE);
        mTvAddbookcaseIntroduce.setText(time);
        Glide.with(getmActivity()).load(img).placeholder(R.mipmap.zwt).fitCenter().into(mIvAddbookcaseBookimage);
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
