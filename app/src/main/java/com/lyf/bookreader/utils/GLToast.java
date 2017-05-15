package com.lyf.bookreader.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by janus on 2016/4/18.
 *
 */
public class GLToast {

    private static Toast mToast;
    public static void show(Context ctx, String content){
        if(mToast == null){
            mToast = Toast.makeText(ctx, content, Toast.LENGTH_LONG);
        }else {
            mToast.setText(content);
        }
        mToast.show();
    }

    public static void showBottom(Context ctx, String content){
        if(mToast == null){
            mToast = Toast.makeText(ctx, content, Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.BOTTOM, 0, 0);
        }else {
            mToast.setText(content);
        }
        mToast.show();
    }

    public static void showTop(Context ctx, String content){
        if(mToast == null){
            mToast = Toast.makeText(ctx, content, Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.TOP, 0, 0);
        }else {
            mToast.setText(content);
        }
        mToast.show();
    }

    public static void showCenter(Context ctx, String content){
        if(mToast == null){
            mToast = Toast.makeText(ctx, content, Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }else {
            mToast.setText(content);
        }
        mToast.show();
    }

    public static void showLeft(Context ctx, String content){
        if(mToast == null){
            mToast = Toast.makeText(ctx, content, Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.LEFT, 0, 0);
        }else {
            mToast.setText(content);
        }
        mToast.show();
    }

    public static void showRight(Context ctx, String content){
        if(mToast == null){
            mToast = Toast.makeText(ctx, content, Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.RIGHT, 0, 0);
        }else {
            mToast.setText(content);
        }
        mToast.show();
    }

}
