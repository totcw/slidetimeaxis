package com.lyf.bookreader.welcome;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.lyf.yflibrary.Permission;
import com.example.lyf.yflibrary.PermissionResult;
import com.lyf.bookreader.home.HomeActivity;
import com.lyf.bookreader.utils.UiUtils;

import java.util.HashMap;

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
            Permission.checkPermisson(this, REQUEST_PERMISSIONS, new PermissionResult() {
                @Override
                public void success() {
                    starToHome();
                }

                @Override
                public void fail() {
                    finish();
                }
            });
        }
    }

    //跳转到首页
    public void  starToHome(){

        UiUtils.startIntent(this, HomeActivity.class);
        finish();

    }
















}
