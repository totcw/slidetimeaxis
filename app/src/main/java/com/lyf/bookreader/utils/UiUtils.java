package com.lyf.bookreader.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.betterda.mylibrary.ShapeLoadingDialog;



/**
 * 界面相关的工具类
 * Created by Administrator on 2016/12/5.
 */

public class UiUtils {

    /**
     * 显示土司
     *
     * @param message
     */
    public static void showToast(Context activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示提示信息
     *
     * @param view    任何一个view 即可
     * @param message
     */
    public static void showSnackBar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }


    public static void showSnackBar(View view, String message, String title, final showSnackBarListener listener) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(title, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.doSnack();
                        }
                    }
                }).show();
    }



    public interface showSnackBarListener{

        /**
         * 做snackbar对应的事情
         */
         void doSnack();
    }

    /**
     * 创建对话框
     */

    public static void showDialog(Activity activity, String title, String content, final showDialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title)
                .setMessage(content)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (listener != null) {
                            listener.comfirmDialog();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        if (listener != null) {
                            listener.exitDialog();
                        }
                    }
                }).show();

    }

    public interface  showDialogListener{

        /**
         * 对话框取消回调的方法
         */
         void exitDialog();

        /**
         * 对话框确定回调的方法
         */
         void comfirmDialog();
    }

    /**
     * 跳转activity
     *
     * @param context
     * @param cla
     */
    public static <T> void startIntent(Context context, Class<T> cla) {
        Intent intent = new Intent(context, cla);
        context.startActivity(intent);
        setOverdepengingIn((Activity) context);
    }


    /**
     * 跳转activity带参数
     *
     * @param context
     * @param intent
     */
    public static  void startIntent(Context context, Intent intent) {
        context.startActivity(intent);
        setOverdepengingIn((Activity) context);
    }

    /**
     * 跳转activity带返回参数
     *
     * @param context
     * @param intent
     */
    public static  void startIntentForResult(Context context, Intent intent, int resquetCode) {
        ((Activity)context).startActivityForResult(intent,resquetCode);
        setOverdepengingIn((Activity) context);
    }


    /**
     * 带伸缩动画的跳转
     * @param context
     * @param view
     */
    public static  void startIntentAndAnim(Context context, Intent intent, View view, String message) {

        ActivityOptionsCompat transitionActivityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,view,message
                );

        ActivityCompat.startActivity((Activity) context,
                intent, transitionActivityOptions.toBundle());
    }

    /**
     * 设置转场进入动画
     */
    public static void setOverdepengingIn(Activity activity) {
        //activity. overridePendingTransition(R.anim.activity_slide_in,R.anim.activity_slide_out);
    }
    /**
     * 设置转场退出动画
     */
    public static void setOverdepengingOut(Activity activity) {
       // activity. overridePendingTransition(R.anim.activity_slide_finish_in,R.anim.activity_slide_finish_out);
    }


    /**
     * 02.     * 设置添加popupwindow屏幕的背景透明度
     * 03.     * @param bgAlpha
     * 04.
     */
    public static void backgroundAlpha(float bgAlpha, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }


    /**
     * 得到自定义的一个加载对话框
     *
     * @param mContext
     * @param content
     * @return
     */
    public static ShapeLoadingDialog createDialog(Context mContext, String content) {
        ShapeLoadingDialog shapeLoadingDialog = new ShapeLoadingDialog(mContext);
        shapeLoadingDialog.setLoadingText(content);
        shapeLoadingDialog.setCanceledOnTouchOutside(false);

        return shapeLoadingDialog;
    }


    /**
     * 显示对话框
     */
    public static void showDialog(Activity activity, ShapeLoadingDialog dialog) {
        if (!activity.isFinishing()) {
            if (dialog != null) {
                dialog.show();
            }
        }
    }
    public static void showDialog(Activity activity, Dialog dialog) {
        if (!activity.isFinishing()) {
            if (dialog != null) {
                dialog.show();
            }
        }
    }

    /**
     * 关闭对话框
     */
    public static void dissmissDialog(Activity activity, ShapeLoadingDialog dialog) {
        if (!activity.isFinishing()) {
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }
    public static void dissmissDialog(Activity activity, Dialog dialog) {
        if (!activity.isFinishing()) {
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }

    /**
     * 打开软键盘
     */
    public static void openInput(Context context) {
        //打开软键盘
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     */
    public static void closeInput(Context context) {
        //打开软键盘
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }



}
