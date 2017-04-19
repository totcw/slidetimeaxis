package com.lyf.slidetime.shouye;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyf.slidetime.R;
import com.lyf.slidetime.base.BaseFragment;
import com.lyf.slidetime.javabean.BookCase;
import com.lyf.slidetime.myadapter.BookCaseItemAdapter;
import com.lyf.slidetime.shouye.contract.ShouYeContract;
import com.lyf.slidetime.shouye.presenter.ShouYePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/8.
 */

public class ShouYeFragment extends BaseFragment<ShouYeContract.Presenter> implements ShouYeContract.View {


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
        list.add(null);
        list.add(null);

        rvShouye.setLayoutManager(new GridLayoutManager(getmActivity(),3));
        mBookCaseItemAdapter = new BookCaseItemAdapter(getmActivity(), list);
        rvShouye.setAdapter(mBookCaseItemAdapter);

    }


    @OnClick({R.id.iv_shouye_bookimage, R.id.iv_shouye_read})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_shouye_bookimage:
                break;
            case R.id.iv_shouye_read:
                break;
        }
    }
}
