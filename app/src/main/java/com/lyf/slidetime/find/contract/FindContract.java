package com.lyf.slidetime.find.contract;


import com.lyf.slidetime.base.IModel;
import com.lyf.slidetime.base.IPresenter;
import com.lyf.slidetime.base.IView;

/**
 * Created by Administrator on 2016/12/8.
 */

public class FindContract {

    public interface View extends IView {
    }

    public interface Presenter extends IPresenter<View> {
    }

    public interface Model extends IModel {
    }


}