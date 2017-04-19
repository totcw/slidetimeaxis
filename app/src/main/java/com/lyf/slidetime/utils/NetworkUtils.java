package com.lyf.slidetime.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;



/**
 * Created by cundong on 2015/10/9.
 * <p/>
 * 网络使用工具类
 */
public class NetworkUtils {

    private static Snackbar snackbar;

    /**
     * 判断是不是wifi网络状态
     *
     * @param paramContext
     * @return
     */
    public static boolean isWifi(Context paramContext) {
        return "2".equals(getNetType(paramContext)[0]);
    }

    /**
     * 判断是不是2/3G网络状态
     *
     * @param paramContext
     * @return
     */
    public static boolean isMobile(Context paramContext) {
        return "1".equals(getNetType(paramContext)[0]);
    }

    /**
     * 网络是否可用
     *
     * @param paramContext
     * @return
     */
    public static boolean isNetAvailable(Context paramContext) {
        if ("1".equals(getNetType(paramContext)[0]) || "2".equals(getNetType(paramContext)[0])) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前网络状态 返回2代表wifi,1代表2G/3G
     *
     * @param paramContext
     * @return
     */
    public static String[] getNetType(Context paramContext) {
        String[] arrayOfString = {"Unknown", "Unknown"};
        PackageManager localPackageManager = paramContext.getPackageManager();
        if (localPackageManager.checkPermission("android.permission.ACCESS_NETWORK_STATE", paramContext.getPackageName()) != 0) {
            arrayOfString[0] = "Unknown";
            return arrayOfString;
        }

        ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext.getSystemService("connectivity");
        if (localConnectivityManager == null) {
            arrayOfString[0] = "Unknown";
            return arrayOfString;
        }

        NetworkInfo localNetworkInfo1 = localConnectivityManager.getNetworkInfo(1);
        if (localNetworkInfo1 != null && localNetworkInfo1.getState() == NetworkInfo.State.CONNECTED) {
            arrayOfString[0] = "2";
            return arrayOfString;
        }

        NetworkInfo localNetworkInfo2 = localConnectivityManager.getNetworkInfo(0);
        if (localNetworkInfo2 != null && localNetworkInfo2.getState() == NetworkInfo.State.CONNECTED) {
            arrayOfString[0] = "1";
            arrayOfString[1] = localNetworkInfo2.getSubtypeName();
            return arrayOfString;
        }

        return arrayOfString;
    }

    public static void toSetingWork(Activity activity) {
        Intent intent=null;
        //判断手机系统的版本  即API大于10 就是3.0或以上版本
        if(android.os.Build.VERSION.SDK_INT>10){
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        }else{
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        activity.startActivity(intent);
    }


    /**
     * 是否有网络并弹出设置
     */
    public static void isNetWork(Activity context, View view, SetDataInterface setDataInterface) {
        //判断是否有网络
        boolean netAvailable = NetworkUtils.isNetAvailable(context);
        if (!netAvailable) {
            showSnackBar(view, "网络异常", "立即设置", context);
         /*   if (view instanceof LoadingPager) {//显示加载错误
                if (view != null) {
                    ((LoadingPager) view).setErrorVisable();
                }
            }*/

        } else {
            //如果有网络就请求服务器获取数据
            if (setDataInterface != null) {
                setDataInterface.getDataApi();
            }
        }

    }

    /**
     * 显示提示信息
     *
     * @param view    任何一个view 即可
     * @param message
     */
    public static   void showSnackBar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    public static void showSnackBar(View view, String message, String title, final Activity context) {
        if (snackbar != null) {
            if (snackbar.isShown()) {
                return;
            }
        }
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(title, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //开启网络设置界面
                        NetworkUtils.toSetingWork(context);
                    }
                });
        snackbar.show();
    }


    public interface SetDataInterface {
        void getDataApi();
    }
}