package com.lyf.bookreader.search.presenter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lyf.bookreader.R;
import com.lyf.bookreader.api.MyObserver;
import com.lyf.bookreader.api.NetWork;
import com.lyf.bookreader.base.BasePresenter;
import com.lyf.bookreader.bookdetail.BookDetailActivity;
import com.lyf.bookreader.javabean.BaseCallModel;
import com.lyf.bookreader.javabean.BookCase;
import com.lyf.bookreader.search.contract.SearchResultContract;
import com.lyf.bookreader.utils.UiUtils;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * 搜索结果
 * Created by lyf o n 2017/4/24.
 */

public class SearchResultPresenterImpl extends BasePresenter<SearchResultContract.View, SearchResultContract.Model> implements SearchResultContract.Presenter {


    private List<BookCase> mBookCaseList;
    private CommonAdapter<BookCase> mAdapter;
    private String type; //书的类型

    @Override
    public void start() {
        type = getView().getmActivity().getIntent().getStringExtra("type");
        getView().getTitleView().setText(type);
        mBookCaseList = new ArrayList<>();
        getData();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mAdapter = new CommonAdapter<BookCase>(getView().getmActivity(), R.layout.item_rv_searchresult, mBookCaseList) {
            @Override
            public void convert(ViewHolder holder, final BookCase bookCase) {
                if (bookCase != null) {
                    ImageView view = holder.getView(R.id.iv_shouye_bookimage);
                    Glide.with(getView().getmActivity()).load(bookCase.getImg()).placeholder(R.mipmap.zwt).fitCenter().into(view);
                    holder.setText(R.id.iv_shouye_bookname, bookCase.getBookname());
                    holder.setText(R.id.iv_shouye_author, bookCase.getAuthor());
                    holder.setText(R.id.iv_shouye_time, bookCase.getTime());
                    holder.setText(R.id.iv_shouye_status, bookCase.getFinish());
                    holder.setOnClickListener(R.id.linear_item_searchresult, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getView().getmActivity(), BookDetailActivity.class);
                            intent.putExtra("bookname", bookCase.getBookname());
                            intent.putExtra("author", bookCase.getAuthor());
                            intent.putExtra("time", bookCase.getTime());
                            intent.putExtra("finish", bookCase.getFinish());
                            intent.putExtra("img", bookCase.getImg());
                            intent.putExtra("total", bookCase.getTotal());
                            intent.putExtra("type", bookCase.getType());
                            UiUtils.startIntent(getView().getmActivity(), intent);
                        }
                    });
                }
            }
        };
        return mAdapter;
    }

    /**
     * @param
     * @return
     * @author : lyf
     * @email:totcw@qq.com
     * @创建日期： 2017/4/20
     * @功能说明：从服务器获取数据
     */
    private void getData() {
        getView().getLoadPager().setLoadVisable();
        type = "类    别："+type;

        getView().getRxManager().add(NetWork.getNetService().getBookList(type)
                .compose(NetWork.handleResult(new BaseCallModel<List<BookCase>>()))
                .subscribe(new MyObserver<List<BookCase>>() {
                    @Override
                    protected void onSuccess(List<BookCase> data, String resultMsg) {
                        if (data != null&&data.size()>0) {
                            parserResult(data);
                        } else {
                            //隐藏加载动画为空
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


    private void parserResult(List<BookCase> data) {
        getView().getRxManager().add(
                Observable.from(data)
                        .subscribe(new Action1<BookCase>() {
                            @Override
                            public void call(BookCase bookCase) {
                                if (bookCase != null) {
                                    if ("yiwanjie".equals(bookCase.getFinish())) {
                                        bookCase.setFinish("已完结");
                                    } else {
                                        bookCase.setFinish("连载中");
                                    }
                                    if (mBookCaseList != null) {
                                        mBookCaseList.add(bookCase);
                                    }
                                    if (mAdapter != null) {
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }
                                //隐藏加载动画
                                getView().getLoadPager().hide();
                            }
                        })
        );

    }

    @Override
    public void destroy() {
        if (mBookCaseList != null) {
            mBookCaseList.clear();
            mBookCaseList = null;

        }
    }
}
