package com.lyf.bookreader.personalcenter;

import com.lyf.bookreader.R;
import com.lyf.bookreader.base.BaseActivity;
import com.lyf.bookreader.base.IPresenter;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/6/28
 * 功能说明：免责声明
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class DisclaimerActivity extends BaseActivity{
    @Override
    protected IPresenter onLoadPresenter() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_disclaimer);
    }
}
