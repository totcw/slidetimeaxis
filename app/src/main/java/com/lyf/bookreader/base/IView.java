package com.lyf.bookreader.base;

import android.app.Activity;
import android.content.Context;

import com.betterda.mylibrary.LoadingPager;
import com.lyf.bookreader.utils.RxManager;


/**
 * view的基类接口
 * Created by Administrator on 2016/12/2.
 */

public interface IView {
    Activity getmActivity();

    Context getContext();

    RxManager getRxManager();

    LoadingPager getLoadpager();
}
