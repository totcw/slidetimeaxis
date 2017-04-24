package com.lyf.bookreader.home.contract;


import com.lyf.bookreader.base.IModel;
import com.lyf.bookreader.base.IPresenter;
import com.lyf.bookreader.base.IView;

/**
 * h ome的mvp接口管理
 * Created by Administrator on 2016/12/5.
 */

public class HomeContract {
    
public interface View extends IView {
}

public interface  Presenter extends IPresenter<View> {


    void switchToFragment(int id);
}

public interface Model extends IModel {
}


}