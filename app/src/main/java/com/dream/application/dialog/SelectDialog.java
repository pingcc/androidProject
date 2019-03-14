package com.dream.application.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dream.application.R;
import com.user.fun.library.dialog.base.BaseDialog;
import com.user.fun.library.util.FastClickUtils;
import com.user.fun.library.util.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by mac on 2017/6/8.
 * <p>
 * 两种选择的dialog
 * android建造者模式
 */

public class SelectDialog extends BaseDialog {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.view_below)
    View mViewBelow;
    @BindView(R.id.view_middle)
    View mviewMiddle;
    @BindView(R.id.tv_subtitle)
    TextView mTvSubtitle;
    @BindView(R.id.btn_left)
    Button mBtnLeft;
    @BindView(R.id.btn_right)
    Button mBtnRight;

    private String mTitle;
    private String mLeftText;
    private String mRightText;

    private View.OnClickListener mLeftClick;
    private View.OnClickListener mRightClick;
    private String mSubtitle;
    private CharSequence mCharSequence;
    private boolean mShowLeftBtn;
    private boolean mShowRightBtn;
    private boolean mShowViewLine;
    private boolean mIsClose;

    private int mRightBtnTextColor = 0;
    private int mLeftBtnTextColor = 0;

    public SelectDialog(@NonNull Context context) {
        this(context, true, true,true);
    }

    public SelectDialog(@NonNull Context context, boolean showLeftBtn, boolean showRightBtn) {
        this(context, showRightBtn, showLeftBtn,true);
    }
    public SelectDialog(@NonNull Context context, boolean showViewLine) {
        this(context, true, true,showViewLine);
    }
    public SelectDialog(@NonNull Context context, boolean showLeftBtn, boolean showRightBtn, boolean showViewLine) {
        super(context, R.style.SelectStyle);
        mShowViewLine = showViewLine;
        mShowLeftBtn = showLeftBtn;
        mShowRightBtn = showRightBtn;
    }
    @Override
    public int getLayoutId() {
        return R.layout.dialog_select;
    }

    @Override
    public void bindButterKnife() {
        ButterKnife.bind(this);

    }

    @Override
    public void initDialogData() {//show 之后调用，控件已注入
        setCanceledOnTouchOutside(!mIsClose);//点击外部取消对话框
        setCancelable(!mIsClose);
        if (!TextUtils.isEmpty(mTitle))
            mTvTitle.setText(mTitle);
        if (!TextUtils.isEmpty(mLeftText))
            mBtnLeft.setText(mLeftText);
        if (!TextUtils.isEmpty(mRightText))
            mBtnRight.setText(mRightText);
        if (!TextUtils.isEmpty(mSubtitle))
            mTvSubtitle.setText(mSubtitle);
        if (!TextUtils.isEmpty(mCharSequence))
            mTvSubtitle.setText(mCharSequence);
        // 是否显示左边和右边按钮
        mBtnLeft.setVisibility(mShowLeftBtn ? View.VISIBLE : View.GONE);
        mBtnRight.setVisibility(mShowRightBtn ? View.VISIBLE : View.GONE);
        mViewBelow.setVisibility(mShowViewLine ? View.VISIBLE : View.GONE);
        mviewMiddle.setVisibility(mShowLeftBtn&&mShowRightBtn ? View.VISIBLE : View.GONE);


        // 左边和右边按钮文字
        int leftTextColor = mLeftBtnTextColor == 0 ? UiUtils.getColor( R.color.color_333): mLeftBtnTextColor;
        mBtnLeft.setTextColor(leftTextColor);
        int rightTextColor = mRightBtnTextColor == 0 ? UiUtils.getColor( R.color.color_default) : mRightBtnTextColor;
        mBtnRight.setTextColor(rightTextColor);
    }


    @OnClick({R.id.btn_left, R.id.btn_right})
    public void onViewClicked(View view) {
        cancel();
        if (FastClickUtils.getInstance().isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_left:
                if (mLeftClick != null) {
                    mLeftClick.onClick(view);
                }

                break;
            case R.id.btn_right:
                if (mRightClick != null) {
                    mRightClick.onClick(view);
                }
                break;
        }
    }

    public SelectDialog setIsClose(boolean update) {
        mIsClose = update;
        return this;
    }

    public SelectDialog setTitleText(String title) {
        if (mTvTitle != null) {
            mTvTitle.setText(title);
        }
        this.mTitle = title;
        return this;
    }

    public SelectDialog setTitleText(@StringRes int stingRes) {
        if (mTvTitle != null) {
            mTvTitle.setText(stingRes);
        }
        this.mTitle = getContext().getString(stingRes);
        return this;
    }

    public SelectDialog setSubtitleText(CharSequence title) {
        if (mTvSubtitle != null) {
            mTvSubtitle.setText(title);
        }
        this.mCharSequence = title;
        return this;
    }

    public SelectDialog setSubtitleText(@StringRes int stingRes) {
        if (mTvSubtitle != null) {
            mTvSubtitle.setText(stingRes);
        }
        this.mSubtitle = getContext().getString(stingRes);
        return this;
    }

    public SelectDialog setSubtitleText(String title) {
        if (mTvSubtitle != null) {
            mTvSubtitle.setText(title);
        }
        this.mSubtitle = title;
        return this;
    }


    public SelectDialog setLeftText(String title) {
        if (mBtnLeft != null) {
            mBtnLeft.setText(title);
        }
        this.mLeftText = title;
        return this;
    }

    public SelectDialog setLeftText(@StringRes int stingRes) {
        if (mBtnLeft != null) {
            mBtnLeft.setText(stingRes);
        }
        this.mLeftText = getContext().getString(stingRes);
        return this;
    }

    public SelectDialog setRightText(String title) {
        if (mBtnRight != null) {
            mBtnRight.setText(title);
        }
        this.mRightText = title;
        return this;
    }

    public SelectDialog setRightText(@StringRes int stingRes) {
        if (mBtnRight != null) {
            mBtnRight.setText(stingRes);
        }
        this.mRightText = getContext().getString(stingRes);
        return this;
    }

    public SelectDialog setLeftClickListener(View.OnClickListener onClickListener) {

        this.mLeftClick = onClickListener;
        return this;
    }

    public SelectDialog setRightClickListener(View.OnClickListener onClickListener) {

        this.mRightClick = onClickListener;
        return this;
    }


    public SelectDialog setLeftBtnTextColor(int leftBtnTextColor) {
        mLeftBtnTextColor = leftBtnTextColor;
        return this;
    }


    public SelectDialog setRightBtnTextColor(int rightTextColor) {
        mRightBtnTextColor = rightTextColor;
        return this;
    }


}
