package com.dream.application.base;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dream.application.App;
import com.dream.application.R;
import com.dream.application.dialog.ProgressDialog;


/**
 * Created on 2017/9/15.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */

public class LoadingDialogManager {
    private ProgressDialog mProgressDialog;


    public interface CancelNetRequestListener {
        void cancelNetRequest();
    }

    //todo 初始化页面时,点击返回键不需要取消网络(false)
    public void showProgress(Context context, String progressMessage, boolean isCancel,
                             @NonNull CancelNetRequestListener cancelNetRequestListener) {
        if (cancelNetRequestListener == null)
            throw new NullPointerException("cancelNetRequest listener not null");

        if (TextUtils.isEmpty(progressMessage)) {
            progressMessage = App.getInstance().getString(R.string.page_tips_loading);
        }

        if (Looper.getMainLooper() != Looper.myLooper()) {
            return;
        }
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(context);
        if (mProgressDialog.isShowing()) {
            mProgressDialog.stopProgressAnimal().dismiss();
        }
        mProgressDialog.showDialog().setTip(progressMessage).startProgressAnimal();
        if (isCancel) {//todo 取消请求对话框时，取消网络请求，（需设置只能点击物理返回键）
            mProgressDialog.setOnCancelListener(dialog -> {
                if (cancelNetRequestListener != null)
                    cancelNetRequestListener.cancelNetRequest();
            });
        }

    }


    public void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.stopProgressAnimal().dismiss();
        }
    }


    public void onDestroy() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
