package com.lyf.bookreader.bookcase;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lyf.bookreader.R;
import com.lyf.bookreader.application.MyApplication;
import com.lyf.bookreader.base.BaseFragment;
import com.lyf.bookreader.javabean.BookCase;
import com.lyf.bookreader.bookcase.contract.BookCaseContract;
import com.lyf.bookreader.bookcase.presenter.BookCasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/4/20
 * @功能说明：  数据界面
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class BookCaseFragment extends BaseFragment<BookCaseContract.Presenter> implements BookCaseContract.View {

    @BindView(R.id.iv_shouye_bookimage)
    ImageView ivShouyeBookimage;
    @BindView(R.id.iv_shouye_bookname)
    TextView ivShouyeBookname;
    @BindView(R.id.iv_shouye_author)
    TextView ivShouyeAuthor;
    @BindView(R.id.iv_shouye_time)
    TextView ivShouyeTime;
    @BindView(R.id.iv_shouye_status)
    TextView ivShouyeStatus;
    @BindView(R.id.iv_shouye_read)
    TextView ivShouyeRead;
    @BindView(R.id.rv_shouye)
    RecyclerView mBookCaseRecycleview;



    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_bookcase, null);
    }

    @Override
    protected BookCaseContract.Presenter onLoadPresenter() {
        return new BookCasePresenterImpl();
    }

    @Override
    public void initData() {
        super.initData();


        initRecycleview();

        getPresenter().getData();
    }

    /**
     * 当前fragment是否显示的回调方法
     * @param hidden true 表示隐藏
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getPresenter().getData();
        }
    }



    private void initRecycleview() {
        mBookCaseRecycleview.setLayoutManager(new GridLayoutManager(getmActivity(), 3));
        mBookCaseRecycleview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(5, 5, 5, 5);
            }
        });

        mBookCaseRecycleview.setAdapter(getPresenter().getAdapter());
    }


    @OnClick({R.id.iv_shouye_bookimage, R.id.iv_shouye_read})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_shouye_bookimage: //最近看的书籍

                break;
            case R.id.iv_shouye_read: //继续阅读

                Toast.makeText(getmActivity(),"此功能暂未开通",Toast.LENGTH_SHORT).show();
                break;
        }
    }



}
