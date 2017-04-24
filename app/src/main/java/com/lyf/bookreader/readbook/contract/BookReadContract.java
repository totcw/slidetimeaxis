package com.lyf.bookreader.readbook.contract;

import android.widget.TextView;

import com.lyf.bookreader.base.IModel;
import com.lyf.bookreader.base.IPresenter;
import com.lyf.bookreader.base.IView;
import com.lyf.bookreader.view.ReadView;

/**
 * Created by lyf o n 2017/4/22.
 */

public class BookReadContract {

    public interface View extends IView{
        ReadView getReadView();


        TextView getBookReadMode();

        void showReadBar();
    }

    public interface Presenter extends IPresenter<View>{
        void changMode(boolean isNight);
    }

    public interface Model extends IModel{
    }


}