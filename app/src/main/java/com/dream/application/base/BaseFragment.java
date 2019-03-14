package com.dream.application.base;


/**
 * Created on 2017/7/11.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 * desc limit load more method
 */

public abstract class BaseFragment<T extends BasePresenter> extends BaseLoadingFragment<T> {
    @Override
    public void onLoadMoreFail() {

    }

    @Override
    public void onRefreshError() {

    }

    @Override
    public int onLoadDataSize() {
        return 0;
    }

    @Override
    public void onRefreshSuccess() {

    }

    @Override
    public void onNoMoreData() {

    }

    @Override
    public void onLoadMoreFirstDataEnd() {

    }

    @Override
    public void showLoadPageError() {

    }

}
