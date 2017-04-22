package com.lyf.bookreader.bookstore.contract;


import android.support.v7.widget.RecyclerView;

import com.lyf.bookreader.base.IModel;
import com.lyf.bookreader.base.IPresenter;
import com.lyf.bookreader.base.IView;

/**
 * Created by Administrator on 2016/12/8.
 */

public class BookStoreContract {

    public interface View extends IView {
    }

    public interface Presenter extends IPresenter<View> {
        RecyclerView.Adapter getAdapter();
    }

    public interface Model extends IModel {
    }


}