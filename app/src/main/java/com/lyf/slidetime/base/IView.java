package com.lyf.slidetime.base;

import android.app.Activity;
import android.content.Context;

import com.lyf.slidetime.utils.RxManager;


/**
 * view的基类接口
 * Created by Administrator on 2016/12/2.
 */

public interface IView {
    Activity getmActivity();

    Context getContext();

    RxManager getRxManager();

}
