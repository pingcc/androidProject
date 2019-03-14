package com.dream.application.di.component;

import android.content.Context;

import com.dream.application.di.module.ApplicationModule;
import com.dream.application.di.scope.ContextLife;
import com.dream.application.di.scope.PerApp;

import dagger.Component;

/**
 * Created on 2017/8/31.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();

}
