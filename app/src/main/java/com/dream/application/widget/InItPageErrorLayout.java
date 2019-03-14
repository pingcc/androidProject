package com.dream.application.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dream.application.R;
import com.user.fun.library.util.UiUtils;

/**
 * Created  on 2017/10/9.
 * author  CPing
 * Email yy_cping@163.com
 * edit androidStudio
 */

public class InItPageErrorLayout extends LinearLayout {
    private View.OnClickListener mOnClickListener;

    public InItPageErrorLayout(@NonNull Context context) {
        this(context,  "", R.mipmap.ic_page_wrong);
    }

    public InItPageErrorLayout(@NonNull Context context, String desc, int imgId) {
        super(context);
        LayoutParams layoutParams = new LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        setOrientation(LinearLayout.VERTICAL);
        layoutParams.gravity = Gravity.CENTER;
        this.setLayoutParams(layoutParams);
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(imgId);
        this.addView(imageView);

        TextView textView = new TextView(getContext());
        textView.setText(desc);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) (UiUtils.dp2px(15) + 0.5f);
        textView.setLayoutParams(textParams);
        this.addView(imageView);

        imageView.setOnClickListener(view -> {
            if (mOnClickListener != null)
                mOnClickListener.onClick(view);
        });
        textView.setOnClickListener(view -> {
            if (mOnClickListener != null)
                mOnClickListener.onClick(view);
        });
    }




    public InItPageErrorLayout setReLoadListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
        return this;
    }
}
