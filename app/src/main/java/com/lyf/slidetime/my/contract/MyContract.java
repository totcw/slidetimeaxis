package com.lyf.slidetime.my.contract;


import com.lyf.slidetime.base.IModel;
import com.lyf.slidetime.base.IPresenter;
import com.lyf.slidetime.base.IView;

/**
 * Created by Administrator on 2016/12/8.
 */

public class MyContract {
    
public interface View extends IView {
}

public interface Presenter extends IPresenter<View> {
}

public interface Model extends IModel {
}


}