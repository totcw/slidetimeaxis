package com.lyf.bookreader.readbook.presenter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyf.bookreader.R;
import com.lyf.bookreader.api.MyObserver;
import com.lyf.bookreader.api.NetWork;
import com.lyf.bookreader.base.BasePresenter;
import com.lyf.bookreader.javabean.BaseCallModel;
import com.lyf.bookreader.javabean.Book;
import com.lyf.bookreader.readbook.contract.DirectoryContract;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
* Created by Administrator on 2017/04/26
*/

public class DirectoryPresenterImpl extends BasePresenter<DirectoryContract.View,DirectoryContract.Model> implements DirectoryContract.Presenter{
    private List<Book> mDirectoryList;
    private CommonAdapter<Book> mAdapter;
    private String bookname;
    @Override
    public void start() {
        mDirectoryList = new ArrayList<>();
        bookname=getView().getmActivity().getIntent().getStringExtra("bookname");
        getData();
        getView().getLoadPager().setonErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }



    @Override
    public RecyclerView.Adapter getAdapter() {
        mAdapter = new CommonAdapter<Book>(getView().getmActivity(), R.layout.item_rv_directory,mDirectoryList) {
            @Override
            public void convert(ViewHolder holder, final Book book) {
                if (book != null&&holder!=null) {
                    holder.setText(R.id.tv_item_directory, book.getChaptername());
                    holder.setOnClickListener(R.id.linear_item_directory, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra("page", book.getPage());
                            getView().getmActivity().setResult(0,intent);
                            getView().getmActivity().finish();
                        }
                    });
                }
            }
        };
        return mAdapter;
    }


    private void getData() {
        getView().getLoadPager().setLoadVisable();
        getView().getRxManager().add(NetWork.getNetService()
        .getBookDirectory(bookname)
        .compose(NetWork.handleResult(new BaseCallModel<List<Book>>()))
        .subscribe(new MyObserver<List<Book>>() {
            @Override
            protected void onSuccess(List<Book> data, String resultMsg) {
                if (data != null && data.size() > 0) {
                    mDirectoryList.addAll(data);
                    mAdapter.notifyDataSetChanged();
                    getView().getLoadPager().hide();
                } else {
                    getView().getLoadPager().setEmptyVisable();
                }
            }

            @Override
            public void onFail(String resultMsg) {
                getView().getLoadPager().setErrorVisable();
            }

            @Override
            public void onExit() {

            }
        }));
    }




    @Override
    public void destroy() {

    }


}