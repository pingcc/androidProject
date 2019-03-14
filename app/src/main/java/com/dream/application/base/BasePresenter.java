package com.dream.application.base;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.dream.application.functions.LoadMoreAction;
import com.dream.application.repository.network.BaseModel;
import com.user.fun.library.functions.IBaseView;
import com.user.fun.library.functions.ResponseDataError;
import com.user.fun.library.functions.ResponseDataSuccess;
import com.user.fun.library.functions.ResponseNetError;
import com.user.fun.library.presenter.DisposablePresenter;
import com.user.fun.library.repository.APIException;
import com.user.fun.library.repository.CustomSubscriber;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created on 2017/6/29.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */

public abstract class BasePresenter<T extends IBaseView> extends DisposablePresenter implements
        LoadMoreAction {
    protected T mView;
    private CustomSubscriber customSubscriber;
    public static final int INITSTATUS = 1;//初始化状态
    public static final int REFRASHSTATUS = 2;//刷新状态
    public static final int LOADMORESTATUS = 3;//加载更多状态


    @IntDef({INITSTATUS, REFRASHSTATUS, LOADMORESTATUS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadStatus {//加载状态的枚举类型
    }


    protected int page = 1;
    private int prePage; //上一个page 避免刷新失败
    protected int pageSize; //default pageSize =10

    protected int getPageSize() {
        return 10;
    }

    @Override
    public void attachView(@NonNull IBaseView view) {
        mView = (T) view;
    }

    @Override
    public void onCreate() {
//        loadData(INITSTATUS);//初始化
        pageSize = getPageSize();
    }

    protected <T> ResourceSubscriber newFunctionSubscriber(String tip, ResponseDataSuccess<T>
            onNext) {
        return newFunctionSubscriber(tip, onNext, null, null, true);
    }

    protected <T> ResourceSubscriber newFunctionSubscriber(ResponseDataSuccess<T>
                                                                   onNext, boolean isProgress) {
        return newFunctionSubscriber("", onNext, null, null, isProgress);
    }

    protected <T> ResourceSubscriber newFunctionSubscriber(ResponseDataSuccess<T> onNext) {
        return newFunctionSubscriber("", onNext, null, null, true);
    }

    protected <T> ResourceSubscriber newFunctionSubscriber(ResponseDataSuccess<T> onNext, ResponseDataError responseDataError) {
        return newFunctionSubscriber("", onNext, responseDataError, null, true);
    }

    protected <T> ResourceSubscriber newFunctionSubscriber(ResponseDataSuccess<T> onNext, ResponseDataError responseDataError, ResponseNetError responseNetError) {
        return newFunctionSubscriber("", onNext, responseDataError, responseNetError, true);
    }

    /**
     * @param tip                 提示信息
     * @param responseDataSuccess 响应数据成功的结果回调
     * @param responseDataError   响应数据失败的回调
     * @param responseNetError    响应网络失败的回调
     * @param isProgress          是否显示进度条
     * @param <T>                 返回的结果泛型
     * @param <D>                 BaseModel<T>
     * @return
     */
    protected <T, D extends BaseModel<T>> ResourceSubscriber newFunctionSubscriber(String tip,
                                                                                   ResponseDataSuccess<T> responseDataSuccess,
                                                                                   ResponseDataError responseDataError,
                                                                                   ResponseNetError responseNetError,
                                                                                   boolean isProgress) {
        //这个可以处理
        customSubscriber = new CustomSubscriber<D>() {
            @Override
            protected void onStart() {
                super.onStart();
                if (isProgress)
                    mView.showProgress(tip);
            }

            @Override
            public void onNext(D t) {
                if (t.isSuccess())
                    responseDataSuccess.onNext(t.data);
                else {
                    if (responseDataError != null)
                        responseDataError.onNext(t.code, t.msg);
                    mView.onResponseError(t.code, t.msg);
                }

            }

            @Override
            public void onComplete() {
                mView.hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                mView.hideProgress();
                if (e instanceof APIException) {//自定义异常，非访问网路失败
                    APIException apiException = (APIException) e;
                    if (responseDataError != null)
                        responseDataError.onNext(apiException.code, apiException.message);
                    mView.onResponseError(apiException.code, apiException.message);
                } else {//访问网络失败
                    mView.onNetError(e.getMessage());
                    if (responseNetError != null)
                        responseNetError.onNetError(e.getMessage());
                }
            }

        };
        return customSubscriber;
    }

    protected <T> ResourceSubscriber newInitPageSubscriber(ResponseDataSuccess<?
            super T> responseDataSuccess, @LoadStatus int status) {
        return newInitPageSubscriber(responseDataSuccess, status, true);
    }

    protected <T> ResourceSubscriber newInitPageSubscriber(ResponseDataSuccess<?
            super T> responseDataSuccess, @LoadStatus int status, boolean isLoadPage) {

        //这个可以处理
        CustomSubscriber customSubscriber = new CustomSubscriber<BaseModel<T>>() {
            @Override
            protected void onStart() {
                super.onStart();
                if (status == INITSTATUS)
                    mView.showProgress("", false);

            }

            @Override
            public void onNext(BaseModel<T> t) {
                if (t.isSuccess())
                    responseDataSuccess.onNext(t.data);
                else {
                    mView.onResponseError(t.code, t.msg);
                }
            }

            @Override
            public void onComplete() {
                mView.hideProgress();
                if (status == REFRASHSTATUS)
                    mView.onRefreshSuccess();
            }

            @Override
            public void onError(Throwable e) {
                mView.hideProgress();
                if (isLoadPage && status == INITSTATUS)
                    mView.showLoadPageError();
                mView.onNetError(e.getMessage());
                onLoadError(status);
            }

        };
        addDisposable(customSubscriber);
        return customSubscriber;
    }

    @Override
    public void onRefresh() {
        prePage = page;
        page = 1;
        loadData(REFRASHSTATUS);
    }


    public void onInitLoadData() {
        prePage = 1;
        page = 1;
        loadData(INITSTATUS);
    }

    private void onLoadError(int status) {

        if (status == LOADMORESTATUS) {
            page--;
            mView.onLoadMoreFail();
        } else if (status == REFRASHSTATUS) {
            page = prePage;
            mView.onRefreshError();
        }
    }

    @Override
    public void onLoadMore() {
        if (mView.onLoadDataSize() < page * pageSize) {
            if (page == 1)
                mView.onLoadMoreFirstDataEnd();
            else
                mView.onNoMoreData();
            return;
        }
        page++;
        loadData(LOADMORESTATUS);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    //取消功能的网络请求
    public void cancelNetRequest() {
        if (customSubscriber != null) {//防止不是接口访问的判断
            customSubscriber.cancelNetRequest();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (customSubscriber != null) {
            cancelNetRequest();
            customSubscriber = null;
        }
        mView = null;

    }


}
