package com.lyf.bookreader.search.presenter;

import com.lyf.bookreader.application.MyApplication;
import com.lyf.bookreader.base.BasePresenter;
import com.lyf.bookreader.db.SearchEntityDao;
import com.lyf.bookreader.javabean.SearchEntity;
import com.lyf.bookreader.search.contract.SearchContract;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lyf  on 2017/4/24.
 */

public class SearchPresenterImpl extends BasePresenter<SearchContract.View,SearchContract.Model> implements SearchContract.Presenter {
    private SearchEntityDao dao;
    private List<String> historySearchStringList;
    private List<String> hotSearchStringList;
    
    @Override
    public void getHotSearchList() {
      hotSearchStringList = new ArrayList<>();

    }

    @Override
    public void getHistorySearchList() {
        dao = MyApplication.getInstance().getDaoSession().getSearchEntityDao();
        historySearchStringList = new ArrayList<>();
        getView().getRxManager().add(Observable.create(new Observable.OnSubscribe<List<SearchEntity>>() {
            @Override
            public void call(Subscriber<? super List<SearchEntity>> subscriber) {
                subscriber.onNext(dao.loadAll());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<SearchEntity>>() {
            @Override
            public void call(List<SearchEntity> searchEntities) {
                if (searchEntities != null && searchEntities.size()>0) {
                    for (SearchEntity entity:searchEntities) {
                        historySearchStringList.add(entity.getKey());

                    }
                }
                getView().showHistorySearchList(historySearchStringList);
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
