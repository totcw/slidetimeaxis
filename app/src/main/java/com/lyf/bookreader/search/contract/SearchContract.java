package com.lyf.bookreader.search.contract;

import com.lyf.bookreader.base.IModel;
import com.lyf.bookreader.base.IPresenter;
import com.lyf.bookreader.base.IView;

import java.util.List;

/**
 * Created by ganli n on 2017/4/22.
 */

public class SearchContract {
    public interface View extends IView {
        void showHotSearchList(List<String> hotSearchList);

        void showHistorySearchList(List<String> historySearchList);
    }

    public interface Presenter extends IPresenter<View> {
       void getHotSearchList();

        void getHistorySearchList();
    }

    public interface Model extends IModel {
    }
}
