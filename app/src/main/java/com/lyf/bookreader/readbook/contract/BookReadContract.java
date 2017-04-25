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

        //System.out.println("ff");
        TextView getBookReadMode();

        void showReadBar();
    }

    public interface Presenter extends IPresenter<View>{
        void changMode(boolean isNight);
        /**
         *@author : lyf
         *@创建日期： 2017/4/25
         *@功能说明： 缓存
         */
        void download();
    }

    public interface Model extends IModel{
    }


}