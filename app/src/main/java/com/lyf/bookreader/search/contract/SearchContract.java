package com.lyf.bookreader.search.contract;

import android.support.v7.widget.RecyclerView;

import com.lyf.bookreader.base.IModel;
import com.lyf.bookreader.base.IPresenter;
import com.lyf.bookreader.base.IView;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
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

public class SearchContract {


    public interface View extends IView {
    }

    public interface Presenter extends IPresenter<View> {
        RecyclerView.Adapter getAdapter();
    }

    public interface Model extends IModel {
    }


}