package com.lyf.bookreader.bookcase.contract;


import android.support.v7.widget.RecyclerView;

import com.lyf.bookreader.base.IModel;
import com.lyf.bookreader.base.IPresenter;
import com.lyf.bookreader.base.IView;
import com.lyf.bookreader.javabean.RecentlyRead;

/**
 * Created by Administ rator on 2016/12/8.
 */

public class BookCaseContract {

    public interface View extends IView {
        void setInformation(RecentlyRead recentlyRead);
    }


    public interface Presenter extends IPresenter<View> {

        /**
         * 获取recycleview的适配器
         */
        RecyclerView.Adapter getAdapter();

        /**
         * 获取服务器  返回的数据
         */
        void getData();

        void continueRead();

        void onStart();
    }

    public interface Model extends IModel {
    }


}