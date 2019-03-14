package com.dream.application.functions;

import com.user.fun.library.functions.Presenter;

/**
 * Created on 2017/6/28.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */

public interface UiBaseAction {
    int getLayoutId();
    void initToolBar();
    void initInjector();
    void initView();
    void initData();
    Presenter initPresenter();
}

