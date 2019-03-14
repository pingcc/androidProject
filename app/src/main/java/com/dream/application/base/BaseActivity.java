package com.dream.application.base;


/**
 * Created on 2017/7/11.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */

public abstract class BaseActivity<T extends BasePresenter> extends BaseLoadingActivity<T> {

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
