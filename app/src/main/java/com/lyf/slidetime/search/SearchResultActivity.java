package com.lyf.slidetime.search;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.mylibrary.LoadingPager;
import com.lyf.slidetime.R;
import com.lyf.slidetime.api.MyObserver;
import com.lyf.slidetime.api.NetWork;
import com.lyf.slidetime.base.BaseActivity;
import com.lyf.slidetime.base.IPresenter;
import com.lyf.slidetime.book.BookCaseActivity;
import com.lyf.slidetime.javabean.BaseCallModel;
import com.lyf.slidetime.javabean.BookCase;
import com.lyf.slidetime.utils.UiUtils;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/4/20
 * @功能说明： 搜索结果
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class SearchResultActivity extends BaseActivity {
    @BindView(R.id.rv_serachresult)
    RecyclerView mRvSerachresult;
    @BindView(R.id.loadpager_searchresult)
    LoadingPager mLoadpagerSearchresult;
    private List<BookCase> mBookCaseList;
    private CommonAdapter<BookCase> mAdapter;
    private String type;
    @Override
    protected IPresenter onLoadPresenter() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_searchresult);
    }

    @Override
    public void init() {
        super.init();
        type = getIntent().getStringExtra("type");
        initRv();
        getData();
    }
    /**
     *@author : lyf
     *@email:totcw@qq.com
     *@创建日期： 2017/4/20
     *@功能说明：从服务器获取数据
     *@param
     *@return
     */
    private void getData() {
        mLoadpagerSearchresult.setLoadVisable();
        type = "全本" + type + "小说";
        mRxManager.add(NetWork.getNetService().getBook(type)
                      .compose(NetWork.handleResult(new BaseCallModel<List<BookCase>>()))
                      .subscribe(new MyObserver<List<BookCase>>() {
                          @Override
                          protected void onSuccess(List<BookCase> data, String resultMsg) {
                              if (data != null && data.size() > 0) {
                                  mBookCaseList.addAll(data);
                                  mAdapter.notifyDataSetChanged();
                                  mLoadpagerSearchresult.hide();
                              } else {
                                  mLoadpagerSearchresult.setEmptyVisable();
                              }
                          }

                          @Override
                          public void onFail(String resultMsg) {
                              mLoadpagerSearchresult.setErrorVisable();
                          }

                          @Override
                          public void onExit() {

                          }
                      }) );
    }

    /**
     *@author : lyf
     *@email:totcw@qq.com
     *@创建日期： 2017/4/20
     *@功能说明：初始化recycleview
     *@param
     *@return
     */
    private void initRv() {
        mBookCaseList = new ArrayList<>();

        mRvSerachresult.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mRvSerachresult.addItemDecoration(new DividerItemDecoration(getmActivity(),DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new CommonAdapter<BookCase>(getmActivity(), R.layout.item_rv_searchresult,mBookCaseList) {
            @Override
            public void convert(ViewHolder holder, final BookCase bookCase) {
                if (bookCase != null) {
                    holder.setText(R.id.iv_shouye_bookname, bookCase.getBookname());
                    holder.setText(R.id.iv_shouye_author, bookCase.getAuthor());
                    holder.setText(R.id.iv_shouye_time, bookCase.getTime());
                    holder.setText(R.id.iv_shouye_status, bookCase.getFinish());
                    holder.setOnClickListener(R.id.linear_item_searchresult, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getmActivity(), BookCaseActivity.class);
                            intent.putExtra("bookname", bookCase.getBookname());
                            intent.putExtra("author", bookCase.getAuthor());
                            intent.putExtra("time", bookCase.getTime());
                            intent.putExtra("finish", bookCase.getFinish());
                            intent.putExtra("img", bookCase.getImg());
                            intent.putExtra("total", bookCase.getTotal());
                            intent.putExtra("type", bookCase.getType());
                            UiUtils.startIntent(getmActivity(),intent);
                        }
                    });
                }
            }
        };
        mRvSerachresult.setAdapter(mAdapter);
    }


}
