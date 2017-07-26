package com.lyf.bookreader.personalcenter;

import android.view.LayoutInflater;
import android.view.View;

import com.lyf.bookreader.R;
import com.lyf.bookreader.base.BaseFragment;
import com.lyf.bookreader.personalcenter.contract.PersonalCenterContract;
import com.lyf.bookreader.personalcenter.presenter.MyPresenterImpl;
import com.lyf.bookreader.utils.UiUtils;

import butterknife.OnClick;

/**
 * Created by Adm inistrator on 2016/12/8.
 * 个人中心
 */

public class PersonalCenterFragment extends BaseFragment<PersonalCenterContract.Presenter> implements PersonalCenterContract.View {


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_my, null);
    }

    @Override
    protected PersonalCenterContract.Presenter onLoadPresenter() {
        return new MyPresenterImpl();
    }


    @Override
    public void initData() {
        super.initData();

    }


    @OnClick({R.id.tv_my_about, R.id.tv_my_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_my_about:
                UiUtils.startIntent(getmActivity(), DisclaimerActivity.class);
                break;
            case R.id.tv_my_download:
                UiUtils.startIntent(getmActivity(), DownloadActivity.class);
                break;
        }
    }
}
