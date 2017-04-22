package com.lyf.bookreader.readbook.contract;

import com.lyf.bookreader.base.IModel;
import com.lyf.bookreader.base.IPresenter;
import com.lyf.bookreader.base.IView;
import com.lyf.bookreader.view.ReadView;

/**
 * Created by lyf on 2017/4/22.
 */

public class BookReadContract {

    public interface View extends IView{
        ReadView getReadView();
    }

    public interface Presenter extends IPresenter<View>{
    }

    public interface Model extends IModel{
    }


}