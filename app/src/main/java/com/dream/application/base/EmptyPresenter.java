package com.dream.application.base;



import javax.inject.Inject;

/**
 * Created on 2017/8/15.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 * desc empty presenter no function use of manage 6.0 checkPermissions
 */

public class EmptyPresenter extends BasePresenter {

    @Inject
    public EmptyPresenter() {
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void loadData(@LoadStatus int status) {

    }
}
