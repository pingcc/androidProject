package com.dream.application.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.dream.application.App;
import com.dream.application.R;
import com.dream.application.config.Constants;
import com.dream.application.di.component.ActivityComponent;
import com.dream.application.di.component.DaggerActivityComponent;
import com.dream.application.di.module.ActivityModule;
import com.dream.application.functions.UiBaseAction;
import com.dream.application.widget.CustomTitleLayout;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.user.fun.library.BuildConfig;
import com.user.fun.library.functions.IBaseView;
import com.user.fun.library.helper.ActivityManager;
import com.user.fun.library.util.FastClickUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created on 2017/6/28.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */

public abstract class BaseLoadingActivity<T extends BasePresenter> extends BaseCommonMethodActivity
        implements
        UiBaseAction, IBaseView {
    private LinearLayout layout;
    private Unbinder mBind;
    private RxPermissions mRxPermissions;
    protected T mPresenter;
    protected ActivityComponent mActivityComponent;
    protected CustomTitleLayout mCustomTitleLayout;
    private LoadingDialogManager mLoadingDialogManager;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().pushActivity(this);
        setContentView(R.layout.act_base);
        layout = findViewById(R.id.act_root);
        if (toolBarLayout() != null) {
            layout.addView(toolBarLayout());
        }
        addContentView();
        mBind = ButterKnife.bind(this);
        initActivityComponent();//生成注解对象
        mContext = this;
        setStatusBar();
        initInjector();
        initToolBar();
        mPresenter = (T) initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
            mPresenter.onCreate();
        }
        mLoadingDialogManager = new LoadingDialogManager();
        initView();
        initData();
    }

    private void addContentView() {
        View view = LayoutInflater.from(getBaseContext()).inflate(getLayoutId(), null, false);
        layout.addView(view);
        view.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
        view.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
        view.requestLayout();
    }

    //初始化toolbar
    protected View toolBarLayout() {
        mCustomTitleLayout = (CustomTitleLayout) LayoutInflater.from(getBaseContext()).inflate(R
                .layout
                .view_toolbar, null, false);
        return mCustomTitleLayout;
    }

    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((App) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }


    public final boolean onClickedHelper() {
        return FastClickUtils.getInstance().isFastClick();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        mLoadingDialogManager.onDestroy();
        ActivityManager.getActivityManager().popActivity(this);
    }

    public Disposable permissionsAnalyze(Consumer<Permission> onNext, String... permissions) {
        if (mRxPermissions == null) {
            mRxPermissions = new RxPermissions(this);
            mRxPermissions.setLogging(BuildConfig.DEBUG);
        }
        return mRxPermissions.requestEach(permissions)
                .subscribe(onNext, Throwable::printStackTrace);
    }

    /**
     * 检查权限 Permission
     *
     * @param onNext      结果处理
     * @param permissions 需要申请的权限  Manifest.permission.ACCESS_CHECKIN_PROPERTIES...
     */
    public void checkPermissions(Consumer<Permission> onNext, String... permissions) {
        mPresenter.addDisposable(permissionsAnalyze(onNext, permissions));
    }


    @Override
    public void onResponseError(int resultCode, String tip) {
        showToast(tip);
        if (resultCode == Constants.TOKEN_ERROR) {
//            LoginActivity.start(this);
        }

    }

    @Override
    public void onNetError(String errorMsg) {
        Logger.d(errorMsg);
        showToast(getString(R.string.page_tips_error));
    }

    @Override
    public final void showProgress(String progressMessage) {
        showProgress(progressMessage, true);
    }

    /**
     * @param progressMessage
     * @param isCancel        点击返回键是否取消网络请求，默认取消
     * todo 初始化页面时,isCancel (false,点击返回键不需要取消网络)
     */
    @Override
    public final void showProgress(String progressMessage, boolean isCancel) {
        mLoadingDialogManager.showProgress(this, progressMessage, isCancel,
                this::cancelNetRequest);
    }

    @Override
    public final void hideProgress() {
        mLoadingDialogManager.hideProgress();

    }

    @Override
    public final void cancelNetRequest() {
        if (mPresenter != null)
            mPresenter.cancelNetRequest();
    }

    public void logout() {
//        UserInfoUtils.getInstance().clearLoginData();
//        LoginActivity.start(this);
        ActivityManager.getActivityManager().popAllActivityExceptOne();
    }
}
