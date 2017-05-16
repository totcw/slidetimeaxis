package com.lyf.bookreader.search.presenter;

import android.net.Network;

import com.lyf.bookreader.api.MyObserver;
import com.lyf.bookreader.api.NetWork;
import com.lyf.bookreader.application.MyApplication;
import com.lyf.bookreader.base.BasePresenter;
import com.lyf.bookreader.db.BookReaderDBManager;
import com.lyf.bookreader.db.SearchEntityDao;
import com.lyf.bookreader.javabean.BaseCallModel;
import com.lyf.bookreader.javabean.SearchEntity;
import com.lyf.bookreader.search.contract.SearchContract;
import com.lyf.bookreader.utils.AppUtils;
import com.lyf.bookreader.utils.GLToast;
import com.lyf.bookreader.utils.GLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lyf  on 2017/4/24.
 */

public class SearchPresenterImpl extends BasePresenter<SearchContract.View, SearchContract.Model> implements SearchContract.Presenter {
    private SearchEntityDao dao;
    private List<String> historySearchStringList;
    private List<String> hotSearchStringList;

    @Override
    public void getHotSearchList() {
//        AppUtils.runOnUIDelayed(new Runnable() {
//            @Override
//            public void run() {
//                hotSearchStringList = new ArrayList<>();
//                hotSearchStringList.add("武侠");
//                hotSearchStringList.add("修仙");
//                hotSearchStringList.add("诺克塞斯之手-德莱厄斯");
//                hotSearchStringList.add("德玛西亚之力-盖伦");
//                hotSearchStringList.add("影流之主");
//                hotSearchStringList.add("暗夜猎手-薇恩");
//                hotSearchStringList.add("探险家");
//                hotSearchStringList.add("武器大师-贾科斯");
//                hotSearchStringList.add("暗影之权-阿卡丽");
//                hotSearchStringList.add("人民的名义");
//                hotSearchStringList.add("西湖公园");
//                getView().showHotSearchList(hotSearchStringList);
//            }
//        }, 2000);

        getView().getRxManager().add(
                NetWork.getNetService()
                        .getHotWord()
                        .compose(NetWork.handleResult(new BaseCallModel<List<SearchEntity>>()))
                        .subscribe(new MyObserver<List<SearchEntity>>() {
                            @Override
                            protected void onSuccess(List<SearchEntity> data, String resultMsg) {
                                hotSearchStringList = new ArrayList<>();
                                for (SearchEntity entity : data) {
                                    hotSearchStringList.add(entity.getKey());
                                }
                                getView().showHotSearchList(hotSearchStringList);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                GLToast.show(AppUtils.getAppContext(), resultMsg);
                            }

                            @Override
                            public void onExit() {
                                GLToast.show(AppUtils.getAppContext(), "onExit");
                            }
                        }));


    }

    @Override
    public void getHistorySearchList() {
        dao = BookReaderDBManager.getInstance().getDaoSession().getSearchEntityDao();
        historySearchStringList = new ArrayList<>();
        getView().getRxManager().add(Observable.create(
                new Observable.OnSubscribe<List<SearchEntity>>() {
                    @Override
                    public void call(Subscriber<? super List<SearchEntity>> subscriber) {
                        subscriber.onNext(dao.loadAll());
                    }
                })
                .map(new Func1<List<SearchEntity>, List<String>>() {
                    @Override
                    public List<String> call(List<SearchEntity> searchEntities) {
                        if (searchEntities != null && searchEntities.size() > 0) {
                            for (SearchEntity entity : searchEntities) {
                                historySearchStringList.add(entity.getKey());
                            }
                            Collections.reverse(historySearchStringList);
                            if (historySearchStringList.size() > 10) {
                                return historySearchStringList.subList(0, 10);
                            }
                            return historySearchStringList;
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        getView().showHistorySearchList(strings);
                    }
                }));

//        list.add("武侠");
//        list.add("修仙");
//        list.add("诺克塞斯之手-德莱厄斯");
//        list.add("德玛西亚之力-盖伦");
//        list.add("影流之主");
//        list.add("暗夜猎手-薇恩");
//        list.add("探险家");
//        list.add("武器大师-贾科斯");
//        list.add("暗影之权-阿卡丽");
//        list.add("人民的名义");
//        list.add("西湖公园");
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }
}
