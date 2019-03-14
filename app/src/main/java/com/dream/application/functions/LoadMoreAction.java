package com.dream.application.functions;


import com.dream.application.base.BasePresenter;

/**
 * Created on 2017/7/11.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */

public interface LoadMoreAction {
    void onRefresh();
    void onLoadMore();
    void loadData(@BasePresenter.LoadStatus int status);
}
