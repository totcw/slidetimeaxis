package com.lyf.bookreader.bookdetail.contract;

import com.lyf.bookreader.base.IModel;
import com.lyf.bookreader.base.IPresenter;
import com.lyf.bookreader.base.IView;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门  北特达 软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/4/20
 * @功能说明：
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class BookDetailContract {


    public interface View extends IView {
        /**
         * 设置最近读的书籍信息
         */
        void setBookInformation(String author, String bookname, String time, String finish,String img);

        //设置加入书架按钮的状态
        void setAddBookcaseStatus(boolean isVisable);
    }

    public interface Presenter extends IPresenter<View> {
        //加入书架
        void addToBookcase();////System.out.println("ff");
    }

    public interface Model extends IModel {
    }

}