package com.lyf.bookreader.readbook;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.betterda.mylibrary.LoadingPager;
import com.lyf.bookreader.R;
import com.lyf.bookreader.base.BaseActivity;
import com.lyf.bookreader.readbook.contract.DirectoryContract;
import com.lyf.bookreader.readbook.presenter.DirectoryPresenterImpl;
import com.zhy.base.adapter.recyclerview.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/4/26
 * @功能说明： 目录界面
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class DirectoryActivity extends BaseActivity<DirectoryContract.Presenter> implements DirectoryContract.View {
    @BindView(R.id.tv_directory_bookname)
    TextView mTvDirectoryBookname;
    @BindView(R.id.rv_directory)
    RecyclerView mRvDirectory;
    @BindView(R.id.tv_directory_close)
    TextView mTvDirectoryClose;
    @BindView(R.id.loadpager_directory)
    LoadingPager mLoadpagerDirectory;

    @Override
    protected DirectoryContract.Presenter onLoadPresenter() {
        return new DirectoryPresenterImpl();
    }


    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_directory);
    }

    @Override
    public void init() {
        super.init();
        initRecycleview();
    }

    private void initRecycleview() {
        mRvDirectory.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mRvDirectory.addItemDecoration(new DividerItemDecoration(getmActivity(),DividerItemDecoration.VERTICAL_LIST));
        mRvDirectory.setAdapter(getPresenter().getAdapter());
    }


    @OnClick({R.id.tv_directory_bookname, R.id.tv_directory_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_directory_bookname:
                break;
            case R.id.tv_directory_close:
                back();
                break;
        }
    }

    @Override
    public LoadingPager getLoadPager() {
        return mLoadpagerDirectory;
    }
}
