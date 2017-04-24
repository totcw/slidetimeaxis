package com.lyf.bookreader.home.presenter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.lyf.bookreader.R;
import com.lyf.bookreader.base.BasePresenter;
import com.lyf.bookreader.bookcase.BookCaseFragment;
import com.lyf.bookreader.bookstore.BookStoreFragment;
import com.lyf.bookreader.home.contract.HomeContract;
import com.lyf.bookreader.personalcenter.PersonalCenterFragment;


/**
* Created by Administ rator on 2016/12/05
*/

public class HomePresenterImpl extends BasePresenter<HomeContract.View,HomeContract.Model> implements HomeContract.Presenter {

    private BookCaseFragment mBookCaseFragment;
    private BookStoreFragment mBookStoreFragment;
    private PersonalCenterFragment mPersonalCenterFragment;
    private FragmentManager mFragmentManager;

    @Override
    public void start() {
        initFragment();

    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        FragmentActivity mFragmentActivity = (FragmentActivity) getView().getmActivity();
        mFragmentManager = mFragmentActivity.getSupportFragmentManager();

        mBookCaseFragment = new BookCaseFragment();
        mBookStoreFragment = new BookStoreFragment();
        mPersonalCenterFragment = new PersonalCenterFragment();

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (!getView().getmActivity().isFinishing()) {
            fragmentTransaction.add(R.id.frame_activity_home, mBookCaseFragment)
                    .add(R.id.frame_activity_home, mBookStoreFragment).add(R.id.frame_activity_home, mPersonalCenterFragment);

            fragmentTransaction.commitAllowingStateLoss();
        }

      switchFragmentTo(mBookCaseFragment);
    }

    /**
     * 切换到对应的fragment
     * @param fragment
     */
    public void switchFragmentTo(Fragment fragment) {
        if (null != mFragmentManager) {
            FragmentTransaction fragmentTransaction2 = mFragmentManager.beginTransaction();
            if (null != fragmentTransaction2 && getView().getmActivity() != null) {

                if (!getView().getmActivity().isFinishing()) {
                    fragmentTransaction2.hide(mBookCaseFragment);
                    fragmentTransaction2.hide(mBookStoreFragment);
                    fragmentTransaction2.hide(mPersonalCenterFragment);
                    fragmentTransaction2.show(fragment);
                    fragmentTransaction2.commitAllowingStateLoss();
                }
            }
        }
    }

    /**
     * 切换到对应的fragment
     * @param id
     */
    @Override
    public void switchToFragment(int id) {
        switch (id) {
            case R.id.idv_activity_main_shouye:

                switchFragmentTo(mBookCaseFragment);

                break;
            case R.id.idv_activity_main_find:

                switchFragmentTo(mBookStoreFragment);

                break;
            case R.id.idv_activity_main_my:
                switchFragmentTo(mPersonalCenterFragment);

                break;
        }
    }

    @Override
    public void destroy() {

    }


}