package com.lyf.bookreader.bookcase.contract;


import com.lyf.bookreader.base.IModel;
import com.lyf.bookreader.base.IPresenter;
import com.lyf.bookreader.base.IView;

/**
 * Created by Administrator on 2016/12/8.
 */

public class BookCaseContract {
    
public interface View extends IView {
}

public interface Presenter extends IPresenter<View> {
}

public interface Model extends IModel {
}


}