package com.lyf.slidetime.find.presenter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyf.slidetime.R;
import com.lyf.slidetime.base.BasePresenter;
import com.lyf.slidetime.find.contract.FindContract;
import com.lyf.slidetime.search.SearchResultActivity;
import com.lyf.slidetime.utils.UiUtils;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/08
 */

public class FindPresenterImpl extends BasePresenter<FindContract.View, FindContract.Model> implements FindContract.Presenter {
    private List<String> list;

    @Override
    public void start() {


    }

    @Override
    public void destroy() {

    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        list = new ArrayList<>();
        list.add("玄幻");
        list.add("武侠");
        list.add("都市");
        list.add("言情");
        list.add("穿越");
        list.add("网游");
        list.add("恐怖");
        list.add("科幻");
        list.add("其他");
        return new CommonAdapter<String>(getView().getmActivity(), R.layout.item_rv_find, list) {
            @Override
            public void convert(ViewHolder holder, final String o) {
                holder.setText(R.id.tv_item_find,o);
                holder.setOnClickListener(R.id.linear_item_find, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getView().getmActivity(), SearchResultActivity.class);
                        intent.putExtra("type",o);

                        UiUtils.startIntent(getView().getmActivity(),intent);
                    }
                });
            }

        };
    }
}