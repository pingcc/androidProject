package com.dream.application.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.dream.application.R;
import com.dream.application.widget.CircleProgressView;
import com.user.fun.library.dialog.base.BaseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created on 2017/7/3.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */

public class ProgressDialog extends BaseDialog {


    @BindView(R.id.cpv_progress)
    CircleProgressView circleProgressView;
    @BindView(R.id.tv_progress_title)
    TextView tvProgressTitle;

    public ProgressDialog(@NonNull Context context) {
        super(context, R.style.TransparentDialog);
    }

    @Override
    protected void setWindowAttribute() {
//        super.setWindowAttribute();
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_progress;
    }

    @Override
    public void bindButterKnife() {
        ButterKnife.bind(this);

    }

    @Override
    public void initDialogData() {
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    public ProgressDialog startProgressAnimal() {
        circleProgressView.start();
        return this;
    }

    public ProgressDialog stopProgressAnimal() {
        circleProgressView.stop();
        return this;
    }

    public ProgressDialog showDialog() {
        show();
        return this;
    }

    public ProgressDialog setTip(String title) {
        tvProgressTitle.setText(title);
        return this;
    }
}
