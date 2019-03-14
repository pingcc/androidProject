package com.dream.application.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.dream.application.R;
import com.user.fun.library.util.UiUtils;


/**
 * Created on 2017/6/30.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */

public class CircleProgressView extends View {
    private Paint mPaint;
    private int mCenterX;
    private float ract;
    // 动画执行的时间
    private final long mDuration = 1000;
    private final float strokeWidth = UiUtils.dp2px(2);//dp -->px
    private final int ractColor = R.color.color_999;
    private ObjectAnimator animator;

    public CircleProgressView(Context context) {
        super(context);
        init();
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        animator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360.0f);
        animator.setDuration(mDuration);
        animator.setRepeatCount(ObjectAnimator.INFINITE); //重复动画
        animator.setInterpolator(new LinearInterpolator()); // 线性插值器 匀速旋转

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mCenterX = getMeasuredWidth() / 2;
        ract = mCenterX - strokeWidth / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.setColor(ContextCompat.getColor(getContext(), ractColor));

        //rectF 外切矩形的左上角坐标和右下角坐标 (即画笔中心起点和画笔中心终止的坐标)
//        new RectF(paintSize/2, paintSize/2, mCenterX*2 -  paintSize/2 ,mCenterX*2 -  paintSize/2 );
        //startAngle (右边中点开始为0度)起始角度
        //  sweepAngle, 从起点开始扫描到截至的中点角度"-"表示逆时针旋转）
        RectF oval = new RectF(mCenterX - ract, mCenterX - ract,
                mCenterX + ract, mCenterX + ract);
        canvas.drawArc(oval, -90, 60, false, mPaint);
    }

    public void stop() {
        animator.end();
    }

    public void start() {
        animator.start();
    }
}
