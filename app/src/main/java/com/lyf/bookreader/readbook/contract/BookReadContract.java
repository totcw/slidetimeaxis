package com.lyf.bookreader.readbook.contract;

import android.content.Intent;
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
        void download(int type);
        /**
         *@author : lyf
         *@email:totcw@qq.com
         *@创建日期： 2017/4/26
         *@功能说明：获取目录
         *@param
         *@return
         */
        void getDirectory();

        void onActivityResult(int requestCode, int resultCode, Intent data);
        /**
         *@author : lyf
         *@email:totcw@qq.com
         *@创建日期： 2017/4/27
         *@功能说明：保存阅读进度
         *@param
         *@return
         */
        void saveProgress();
        /**
         *@author : lyf
         *@email:totcw@qq.com
         *@创建日期： 2017/6/28
         *@功能说明：减少字体
         *@param
         *@return
         */
        void delFont();

        void addFont();
    }

    public interface Model extends IModel{
    }


}