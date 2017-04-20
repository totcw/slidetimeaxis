package com.lyf.slidetime.shouye;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lyf.slidetime.R;
import com.lyf.slidetime.base.BaseFragment;
import com.lyf.slidetime.db.BookCaseDao;
import com.lyf.slidetime.interfac.SetReadListener;
import com.lyf.slidetime.javabean.BookCase;
import com.lyf.slidetime.myadapter.BookCaseItemAdapter;
import com.lyf.slidetime.readbook.ReadBookActivity;
import com.lyf.slidetime.shouye.contract.ShouYeContract;
import com.lyf.slidetime.shouye.presenter.ShouYePresenterImpl;
import com.lyf.slidetime.utils.UiUtils;

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
 * @功能说明：
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class ShouYeFragment extends BaseFragment<ShouYeContract.Presenter> implements ShouYeContract.View, SetReadListener {


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
    RecyclerView rvShouye;

    private List<BookCase> list;
    private BookCaseItemAdapter mBookCaseItemAdapter;
    private BookCaseDao mBookCaseDao;
    private int chapter ;
    private int position;
    private int mTotal;
    private String bookname;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_shouye, null);
    }

    @Override
    protected ShouYeContract.Presenter onLoadPresenter() {
        return new ShouYePresenterImpl();
    }

    @Override
    public void initData() {
        super.initData();
        list = new ArrayList<>();

        initRv();

        getData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getData();
        }
    }

    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/20
     * @功能说明：获取书架的数据
     */
    private void getData() {
        mBookCaseDao = new BookCaseDao(getmActivity());
        List<BookCase> mBookCaseList = mBookCaseDao.queryAll();
        if (mBookCaseList != null) {
            list.clear();
            list.addAll(mBookCaseList);
            mBookCaseItemAdapter.notifyDataSetChanged();
        }
    }

    private void initRv() {
        rvShouye.setLayoutManager(new GridLayoutManager(getmActivity(), 3));
        rvShouye.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(5, 5, 5, 5);
            }
        });
        mBookCaseItemAdapter = new BookCaseItemAdapter(getmActivity(), list, this);

        rvShouye.setAdapter(mBookCaseItemAdapter);
    }


    @OnClick({R.id.iv_shouye_bookimage, R.id.iv_shouye_read})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_shouye_bookimage:
                break;
            case R.id.iv_shouye_read:
               /* Intent intent = new Intent(getmActivity(), ReadBookActivity.class);
                intent.putExtra("bookname", bookname);
                intent.putExtra("chapter", chapter);
                intent.putExtra("position", position);
                intent.putExtra("total", mTotal);
                UiUtils.startIntent(getmActivity(),intent);*/
                Toast.makeText(getmActivity(),"此功能暂未开通",0).show();
                break;
        }
    }


    @Override
    public void read(BookCase bookCase) {
        if (bookCase != null) {
            if (ivShouyeBookname != null) {
                ivShouyeBookname.setText(bookCase.getBookname());
            }
            if (ivShouyeAuthor != null) {
                ivShouyeAuthor.setText(bookCase.getAuthor());
            }
            if (ivShouyeTime != null) {
                ivShouyeTime.setText("更新时间:"+bookCase.getTime());
            }
            if (ivShouyeStatus != null) {
                ivShouyeStatus.setText(bookCase.getFinish());
            }
            chapter = bookCase.getCurPage();
            position = bookCase.getPosition();
            mTotal = bookCase.getTotal();
            bookname = bookCase.getBookname();

        }
    }
}
