package com.lyf.bookreader.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lyf.bookreader.R;
import com.lyf.bookreader.application.MyApplication;
import com.lyf.bookreader.db.BookCaseDao;
import com.lyf.bookreader.db.RecentlyReadDao;
import com.lyf.bookreader.home.HomeActivity;
import com.lyf.bookreader.javabean.BookCase;
import com.lyf.bookreader.javabean.RecentlyRead;
import com.lyf.bookreader.readbook.BookReadActivity;
import com.lyf.bookreader.utils.UiUtils;

import java.util.List;

/**
 * 书架i tem的适配器
 * Created by Administrator on 2017/4/13.
 */

public class BookCaseItemAdapter<T> extends RecyclerView.Adapter< RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;//普通item
    private static final int TYPE_ADD = 1;//最后一个添加按钮

    private Activity mContext;
    private List<BookCase> data;

    private RecentlyReadDao mRecentlyReadDao;
    private BookCaseDao mBookCaseDao;
    private boolean isVisableDelte;//是否显示删除

    public BookCaseItemAdapter(Activity mContext, List<BookCase> data ) {

        this.data = data;
        this.mContext = mContext;
        mRecentlyReadDao = MyApplication.getInstance().getDaoSession().getRecentlyReadDao();
        mBookCaseDao = MyApplication.getInstance().getDaoSession().getBookCaseDao();
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
            //添加
            ((AddHolder) holder).mLinearBookcaseAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isVisableDelte) {
                        isVisableDelte = false;
                        notifyDataSetChanged();
                        return;
                    }
                    ((HomeActivity) mContext).toBookStore();

                }
            });
        } else if (holder instanceof MainViewHolder) {
            if (data != null && position < data.size() && data.get(position) != null) {
                final BookCase bookCase = data.get(position);
                if (bookCase != null) {
                    ((MainViewHolder) holder).tv_name.setText(bookCase.getBookname());
                    Glide.with(mContext).load(bookCase.getImg()).placeholder(R.mipmap.zwt).fitCenter().into(((MainViewHolder) holder).mIvBookImage);
                    //进入书本阅读
                    ((MainViewHolder) holder).mLinearBookcase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (isVisableDelte) {
                                isVisableDelte = false;
                                notifyDataSetChanged();
                                return;
                            }
                            Intent intent = new Intent(mContext, BookReadActivity.class);
                            intent.putExtra("bookname", bookCase.getBookname());
                            intent.putExtra("total", bookCase.getTotal());
                            UiUtils.startIntent(mContext, intent);
                            List<RecentlyRead> recentlyReads = mRecentlyReadDao.loadAll();
                            if (recentlyReads != null && recentlyReads.size() > 0) {
                                RecentlyRead recentlyRead = recentlyReads.get(0);
                                setData(recentlyRead, bookCase);
                                mRecentlyReadDao.update(recentlyRead);
                            } else {
                                RecentlyRead recentlyRead = new RecentlyRead();
                                setData(recentlyRead,bookCase);
                                mRecentlyReadDao.insert(recentlyRead);
                            }

                        }
                    });
                    ((MainViewHolder) holder).mLinearBookcase.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            isVisableDelte = !isVisableDelte;
                            notifyDataSetChanged();
                            return true;
                        }
                    });

                    ((MainViewHolder) holder).mIvDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mBookCaseDao.delete(bookCase);
                            data.remove(bookCase);
                            isVisableDelte = false;
                            notifyDataSetChanged();
                        }
                    });
                }

                ((MainViewHolder) holder).mIvDelete.setVisibility(isVisableDelte?View.VISIBLE:View.INVISIBLE);

            }

        }
    }

    private void setData(RecentlyRead recentlyRead, BookCase bookCase) {
        recentlyRead.setBookname(bookCase.getBookname());
        recentlyRead.setBeginPos(bookCase.getBeginPos());
        recentlyRead.setEndPos(bookCase.getEndPos());
        recentlyRead.setAuthor(bookCase.getAuthor());
        recentlyRead.setCurPage(bookCase.getCurPage());
        recentlyRead.setFinish(bookCase.getFinish());
        recentlyRead.setImg(bookCase.getImg());
        recentlyRead.setType(bookCase.getType());
        recentlyRead.setTime(bookCase.getTime());
        recentlyRead.setTotal(bookCase.getTotal());
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
        TextView tv_name;
        ImageView mIvDelete,mIvBookImage;

        public MainViewHolder(View itemView) {
            super(itemView);
            mLinearBookcase = (LinearLayout) itemView.findViewById(R.id.linear_rv_bookcase);
            tv_name = (TextView) itemView.findViewById(R.id.tv_rv_bookcase_bookimage);
            mIvDelete = (ImageView) itemView.findViewById(R.id.iv_item_bookcase_delete);
            mIvBookImage = (ImageView) itemView.findViewById(R.id.iv_rv_bookcase_bookimage);

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
