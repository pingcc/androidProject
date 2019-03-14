package com.dream.application.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dream.application.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created on 2017/7/10.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */

public class CustomTitleLayout extends FrameLayout implements View.OnClickListener {
    @BindView(R.id.rl_toolbar)
    RelativeLayout rl_toolbar;
    @BindView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @BindView(R.id.tv_title_center)
    TextView tvTitleCenter;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.iv_title_right)
    ImageView ivTitleRight;
    private Context mContext;


    public CustomTitleLayout(@NonNull Context context) {
        super(context);
        mContext = context;

    }

    public CustomTitleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_custom_toolbar, this);
        ButterKnife.bind(this);

    }

    public CustomTitleLayout setRightImgVisible(boolean IsVisible) {
        ivTitleRight.setVisibility(IsVisible ? VISIBLE : GONE);
        return this;
    }

    public CustomTitleLayout setRightTitleVisible(boolean IsVisible) {
        tvTitleRight.setVisibility(IsVisible ? VISIBLE : GONE);
        return this;
    }

    public CustomTitleLayout setRightLeft(int ImgId) {
        ivTitleLeft.setImageResource(ImgId);
        return this;
    }

    public CustomTitleLayout setRightImage(int ImgId) {
        ivTitleRight.setImageResource(ImgId);
        return this;
    }

    public CustomTitleLayout setToobalBackgroundColor(int ImgId) {
        rl_toolbar.setBackgroundColor(ImgId);
        return this;

    }

    public CustomTitleLayout setRightTitle(String text) {
        tvTitleRight.setText(text);
        return this;

    }

    public CustomTitleLayout setRightTitle(@StringRes int stingRes) {
        tvTitleRight.setText(getContext().getString(stingRes));
        return this;

    }

    public CustomTitleLayout setTitle(String text) {
        tvTitleCenter.setText(text);
        return this;

    }

    public CustomTitleLayout setTitle(@StringRes int stingRes) {
        tvTitleCenter.setText(getContext().getString(stingRes));
        return this;

    }


    private OnClickListener onRightImgClickListener;
    private OnClickListener onRightTextClickListener;
    private OnClickListener onLeftImgClickListener;

    public CustomTitleLayout setOnRightImgListener(OnClickListener listener) {
        onRightImgClickListener = listener;
        return this;
    }

    public CustomTitleLayout setOnLeftImgClickListener(OnClickListener listener) {
        onLeftImgClickListener = listener;
        return this;
    }

    public CustomTitleLayout setOnRightTextClickListener(OnClickListener listener) {
        onRightTextClickListener = listener;
        return this;
    }


    @Override
    public void onClick(View view) {

    }

    @OnClick({R.id.iv_title_left, R.id.tv_title_right, R.id.iv_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                if (onLeftImgClickListener != null) {
                    onLeftImgClickListener.onClick(view);
                } else
                    ((Activity) mContext).finish();
                break;
            case R.id.tv_title_right:
                if (onRightTextClickListener != null)
                    onRightTextClickListener.onClick(view);
                break;
            case R.id.iv_title_right:
                if (onRightImgClickListener != null)
                    onRightImgClickListener.onClick(view);
                break;
        }
    }
}
