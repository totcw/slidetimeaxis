package com.lyf.slidetime.myadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lyf.slidetime.R;
import com.lyf.slidetime.db.BookCaseDao;

import java.util.List;

/**
 * 书架item的适配器
 * Created by Administrator on 2017/4/13.
 */

public class BookCaseItemAdapter<T> extends RecyclerView.Adapter< RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;//普通item
    private static final int TYPE_ADD = 1;//最后一个添加按钮
    private BookCaseDao mBookCaseDao;
    private Context mContext;
    private List<T> data;
    public BookCaseItemAdapter(Context mContext,  List<T> data ) {

        this.data = data;
        this.mContext = mContext;
        mBookCaseDao = new BookCaseDao(mContext);
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_ITEM == viewType) {

        return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_bookcase, parent, false));
        } else if (TYPE_ADD == viewType) {
            return new AddHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_bookcase_add, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AddHolder) {
            ((AddHolder) holder).mLinearBookcaseAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"添加",0).show();
                    mBookCaseDao.add();
                }
            });
        } else if (holder instanceof MainViewHolder) {
           ((MainViewHolder) holder).mLinearBookcase.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Toast.makeText(mContext,"查询",0).show();
                   mBookCaseDao.query("Jne");
               }
           });
        }
    }

    @Override
    public int getItemCount() {
        if (null != data) {
            return data.size()+1;
        } else {
            return 1;
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (data == null) {
            return TYPE_ADD;
        } else {
            if (position < data.size()) {
                return TYPE_ITEM;
            } else {
                return TYPE_ADD;
            }
        }

    }

    static class MainViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearBookcase;
        public MainViewHolder(View itemView) {
            super(itemView);
            mLinearBookcase = (LinearLayout) itemView.findViewById(R.id.linear_rv_bookcase);
        }
    }

    static class AddHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearBookcaseAdd;
        public AddHolder(View itemView) {
            super(itemView);
            mLinearBookcaseAdd = (LinearLayout) itemView.findViewById(R.id.linear_rv_bookcase_add);
        }
    }
}
