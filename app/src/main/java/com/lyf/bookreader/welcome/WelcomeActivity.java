package com.lyf.bookreader.welcome;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.lyf.bookreader.base.BaseActivity;
import com.lyf.bookreader.base.IPresenter;
import com.lyf.bookreader.home.HomeActivity;
import com.lyf.bookreader.utils.PermissionUtil;
import com.lyf.bookreader.utils.UiUtils;
import com.lyf.bookreader.view.DeleteDialog;
import com.lyf.bookreader.view.PermissionDialog;

import java.util.HashMap;
import java.util.List;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/5/23
 * @功能说明： 欢迎界面
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class WelcomeActivity extends AppCompatActivity {
    private String[] REQUEST_PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private HashMap<String,String> map;//管理权限的map
    private static final int REQUEST_PERMISSION_CODE_TAKE_PIC = 9; //权限的请求码
    private static final int REQUEST_PERMISSION_SEETING = 8; //去设置界面的请求码
    private PermissionDialog permissionDialog;//权限请求对话框


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTopermission();
    }

    /**
     * 获取权限
     */
    private void startTopermission() {
        if (Build.VERSION.SDK_INT < 23) {
            //6.0一下直接去主页
            starToHome();

        } else {
            //6.0以上请求权限
            checkPermiss();
        }
    }

    //跳转到首页
    public void  starToHome(){
        if (Build.VERSION.SDK_INT < 23) {
            UiUtils.startIntent(this, HomeActivity.class);
            finish();
        } else {
            UiUtils.startIntent(this, HomeActivity.class);
            finish();
        }
    }

    /**
     * 请求权限
     */
    private void checkPermiss() {
        PermissionUtil.checkPermission(this,  REQUEST_PERMISSIONS, new PermissionUtil.permissionInterface() {
            @Override
            public void success() {
                //请求成功
                starToHome();
            }

            @Override
            public void fail(final List<String> permissions) {

                if (map == null) {
                    map = new HashMap<>();
                    map.put("android.permission.WRITE_EXTERNAL_STORAGE", "存储空间");


                }

                requestPermission(permissions.toArray(new String[permissions.size()]));


            }
        });
    }


    /**
     * 请求权限
     * @param permissions
     */
    private void requestPermission(final String[] permissions) {

        if (permissionDialog != null) {
            permissionDialog.dismiss();
        }
        System.out.println("ss");
        //请求权限
        permissionDialog = new PermissionDialog(WelcomeActivity.this, new PermissionDialog.onConfirmListener() {
            @Override
            public void comfirm() {
                //请求权限
                PermissionUtil.requestContactsPermissions(WelcomeActivity.this,permissions,REQUEST_PERMISSION_CODE_TAKE_PIC);
            }

            @Override
            public void cancel() {
                WelcomeActivity.this.finish();
            }
        });

        StringBuilder sb = new StringBuilder();
        for (String permission : permissions) {
            if (map != null) {
                String s = map.get(permission);
                if (!TextUtils.isEmpty(s)) {
                    sb.append(s + " ");
                }
            }
        }

        permissionDialog.setTvcontent(sb.toString());
        permissionDialog.show();

    }

    /**
     * 请求权限2
     * @param permissions
     */
    private void requestPermission2(final String[] permissions) {
        DeleteDialog deleteDialog = new DeleteDialog(WelcomeActivity.this, new DeleteDialog.onConfirmListener() {
            @Override
            public void comfirm() {
                //去掉已经请求过的权限
                List<String> deniedPermissions = PermissionUtil.findDeniedPermissions(WelcomeActivity.this, permissions);
                //请求权限
                PermissionUtil.requestContactsPermissions(WelcomeActivity.this,deniedPermissions.toArray(new String[deniedPermissions.size()]),REQUEST_PERMISSION_CODE_TAKE_PIC);
            }

            @Override
            public void cancel() {
                WelcomeActivity.this.finish();
            }
        });

        StringBuilder sb = new StringBuilder();
        for (String permission : permissions) {
            if (map != null) {
                String s = map.get(permission);
                if (!TextUtils.isEmpty(s)) {
                    sb.append(s + " ");
                }
            }
        }
        deleteDialog.setTvcontent("请允许"+sb+"权限请求");
        deleteDialog.show();
    }

    private void startToSetting() {
        DeleteDialog deleteDialog = new DeleteDialog(WelcomeActivity.this, new DeleteDialog.onConfirmListener() {
            @Override
            public void comfirm() {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, REQUEST_PERMISSION_SEETING);
            }

            @Override
            public void cancel() {
                WelcomeActivity.this.finish();
            }
        });


        deleteDialog.setTvcontent("去设置界面开启权限?");
        deleteDialog.show();
    }



    /**
     * 检测权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION_CODE_TAKE_PIC) {
            if (PermissionUtil.verifyPermissions(grantResults)) {//有权限
                starToHome();

            } else {
                //没有权限
                if (!PermissionUtil.shouldShowPermissions(this, permissions)) {//这个返回false 表示勾选了不再提示

                    startToSetting();

                } else {
                    //表示没有权限 ,但是没勾选不再提示
                    for (String s : permissions) {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(WelcomeActivity.this,
                                s)) {
                            //去掉已经允许的
                            if (map != null) {
                                map.remove(s);
                            }
                        }
                    }
                    requestPermission2(permissions);
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //如果是从设置界面返回,就继续判断权限
        if (requestCode == REQUEST_PERMISSION_SEETING) {
            checkPermiss();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (map != null) {
            map.clear();
            map = null;
        }
    }
}
