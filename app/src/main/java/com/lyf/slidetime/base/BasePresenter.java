package com.lyf.slidetime.base;

/**
 * 一个实现IPresenter的基类presenter 策略模式
 * Created by Administrator on 2016/12/2.
 */

public abstract class BasePresenter<T extends IView,M extends IModel> implements IPresenter<T> {

    protected static final String TAG = "BasePresenter";
    protected T mView;
    protected M mModel;


    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void attachModel(M mModel) {
        this.mModel = mModel;
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public T getView() {
        return mView;
    }

    public M getModel() {
        return mModel;
    }

}
