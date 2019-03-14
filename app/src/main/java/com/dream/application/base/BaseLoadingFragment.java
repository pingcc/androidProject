package com.dream.application.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dream.application.App;
import com.dream.application.di.component.DaggerFragmentComponent;
import com.dream.application.di.component.FragmentComponent;
import com.dream.application.di.module.FragmentModule;
import com.dream.application.functions.UiBaseAction;
import com.dream.application.widget.CustomTitleLayout;
import com.dream.application.widget.InItPageEmptyLayout;
import com.dream.application.widget.InItPageErrorLayout;
import com.tbruyelle.rxpermissions2.Permission;
import com.user.fun.library.functions.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

/**
 * Created on 2017/6/28.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */

public abstract class BaseLoadingFragment<T extends BasePresenter> extends Fragment implements
        UiBaseAction, IBaseView {
    private Unbinder mBind;
    protected T mPresenter;
    protected FragmentComponent mFragmentComponent;
    private View mFragmentView;
    protected BaseLoadingActivity mBaseActivity;
    private LoadingDialogManager mLoadingDialogManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        if (mFragmentView == null) {
            mFragmentView = inflater.inflate(getLayoutId(), container, false);
            mBind = ButterKnife.bind(this, mFragmentView);
            initFragmentComponent();//生成注解对象
            if (getActivity() instanceof BaseLoadingActivity) {
                mBaseActivity = (BaseLoadingActivity) getActivity();
            }
            initInjector();
            mPresenter = (T) initPresenter();
            if (mPresenter != null) {
                mPresenter.attachView(this);
                mPresenter.onCreate();
            }
            mLoadingDialogManager = new LoadingDialogManager();
            initView();
            initData();
        }
        return mFragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initFragmentComponent() {
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(((App) getActivity().getApplication())
                        .getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Override
    public void initToolBar() {

    }

    public  final CustomTitleLayout getToolBarLayout(){
        return mBaseActivity.mCustomTitleLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onPause();
        }
    }

    public void checkPermissions(Consumer<Permission> onNext, String... permissions) {
        mPresenter.addDisposable(mBaseActivity.permissionsAnalyze(onNext, permissions));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBind != null)
            mBind.unbind();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }


    @Override
    public void showProgress(String progressMessage) {
        showProgress(progressMessage, true);
    }

    @Override
    public void showProgress(String progressMessage, boolean isCancel) {
        mLoadingDialogManager.showProgress(mBaseActivity, progressMessage, isCancel, this::cancelNetRequest);
    }

    @Override
    public void hideProgress() {
        mLoadingDialogManager.hideProgress();
    }

    @Override
    public void cancelNetRequest() {
        mBaseActivity.cancelNetRequest();
    }

    @Override
    public void onResponseError(int resultCode, String tip) {
        mBaseActivity.onResponseError(resultCode, tip);
    }

    @Override
    public void onNetError(String errorMsg) {
        mBaseActivity.onNetError(errorMsg);
    }


    public void showToast(String str) {
        mBaseActivity.showToast(str);
    }


    public void showLoadPageErrorReload(BaseQuickAdapter adapter,int viewHeight) {
        showLoadPageErrorReload(adapter,viewHeight);
    }

    public void showLoadPageErrorReload(BaseQuickAdapter adapter, int viewHeight, OnInItPageErrorReLoadListener onInItPageErrorReLoadListener) {
        adapter.setNewData(null);
        InItPageErrorLayout inItPageErrorLayout = new InItPageErrorLayout(getContext());
        inItPageErrorLayout.getLayoutParams().height=viewHeight;
        inItPageErrorLayout.requestLayout();
        adapter.setEmptyView(inItPageErrorLayout);
        inItPageErrorLayout.setReLoadListener(v -> {
            if (onInItPageErrorReLoadListener != null) {
                onInItPageErrorReLoadListener.onInItPageErrorReLoadListener();
            }
            mPresenter.onInitLoadData();
        });
    }

    public interface OnInItPageErrorReLoadListener {
        void onInItPageErrorReLoadListener();

    }


    public void showLoadPageEmpty(BaseQuickAdapter adapter,  int viewHeight,String str ) {
        showLoadPageEmpty(adapter, viewHeight, str, -1);
    }

    public void showLoadPageEmpty(BaseQuickAdapter adapter,int viewHeight, String string, int mipmapId) {
        adapter.setNewData(null);
        InItPageEmptyLayout mEmptyView = new InItPageEmptyLayout(getContext(),string,mipmapId);

        mEmptyView.getLayoutParams().height=viewHeight;
        mEmptyView.requestLayout();

        adapter.setEmptyView(mEmptyView);

    }





    public void showLoadPageEmpty(BaseQuickAdapter adapter, int viewHeight) {
        showLoadPageEmpty(adapter,viewHeight,null,0);
    }


}

