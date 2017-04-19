package com.lyf.slidetime.home.contract;


import com.lyf.slidetime.base.IModel;
import com.lyf.slidetime.base.IPresenter;
import com.lyf.slidetime.base.IView;

/**
 * home的mvp接口管理
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