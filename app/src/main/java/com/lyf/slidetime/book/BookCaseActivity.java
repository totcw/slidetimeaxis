package com.lyf.slidetime.book;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lyf.slidetime.R;
import com.lyf.slidetime.base.BaseActivity;
import com.lyf.slidetime.book.contract.BookContract;

import com.lyf.slidetime.db.BookCaseDao;
import com.lyf.slidetime.javabean.BookCase;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/4/20
 * @功能说明： 书本界面
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class BookCaseActivity extends BaseActivity<BookContract.Presenter> implements BookContract.View {
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

    private String bookname;
    private String author;
    private String time;
    private String finish;
    private String img;
    private String type;
    private int  total;
    private BookCaseDao mBookCaseDao;

    @Override
    protected BookContract.Presenter onLoadPresenter() {
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
        bookname = getIntent().getStringExtra("bookname");
        author = getIntent().getStringExtra("author");
        time = getIntent().getStringExtra("time");
        finish = getIntent().getStringExtra("finish");
        img = getIntent().getStringExtra("img");
        total = getIntent().getIntExtra("total",0);
        type = getIntent().getStringExtra("type");
        mBookCaseDao = new BookCaseDao(getmActivity());
        setText();
        BookCase bookCase = mBookCaseDao.query(bookname);
        if (bookCase != null && bookCase.getBookname() != null) {
            mIvAddbookcaseAdd.setVisibility(View.GONE);
        } else {
            mIvAddbookcaseAdd.setVisibility(View.VISIBLE);
        }
    }

    private void setText() {
        mIvAddbookcaseAuthor.setText(author);
        mIvAddbookcaseBookname.setText(bookname);
        mIvAddbookcaseTime.setText("最近更新时间:"+time);
        mIvAddbookcaseStatus.setText(finish);
        mIvAddbookcaseAdd.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_addbookcase_add)
    public void onClick() {
        Toast.makeText(getmActivity(),"加入书架",0).show();
        BookCase bookCase = new BookCase();
        bookCase.setBookname(bookname);
        bookCase.setAuthor(author);
        bookCase.setFinish(finish);
        bookCase.setTime(time);
        bookCase.setImg(img);
        bookCase.setCurPage(1);
        bookCase.setPosition(0);
        bookCase.setTotal(total);
        bookCase.setType(type);
        if (mBookCaseDao.add(bookCase) > 0) {
            mIvAddbookcaseAdd.setVisibility(View.GONE);
        }
    }
}
