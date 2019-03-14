package com.dream.application.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.dream.application.R;
import com.dream.application.functions.BackHandlerAction;
import com.user.fun.library.functions.Presenter;


/**
 * Created on 2017/7/17.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 * desc
 * <p> 相同布局的activity   防止创建太多的activity  ,共用同一个activity
 * <p>当需要显示右边布局需实现 OnMenuRightClickListener
 */

public class CommonActivity extends BaseActivity {

    public static final String COMMON_TITLE = "common_title";
    public static final String COMMON_TOOLBAR = "ToolBar";

    public static final String COMMON_FRAGMENT_CLASS_NAME = "common_fragment_class_name";
    public static final String COMMON_FRAGMENT_ARGUMENTS = "common_fragment_arguments";
    /**
     * 1.当需要自定义toolbar时,无需创建toolbar，表示true,默认创建false
     * 2.当符合标准 返回键，标题，图标（isContain）,isContain==true(实现 {@link OnMenuRightClickListener}接口)?false
     */
    private boolean isHiddenToolBar;

    @Override
    public int getLayoutId() {
        return R.layout.act_common;
    }

    @Override
    public void initToolBar() {
        if (!isHiddenToolBar) {
            //设置标题
            String title = getIntent().getStringExtra(COMMON_TITLE);
            mCustomTitleLayout.setTitle(title);
        }
    }

    @Override
    protected View toolBarLayout() {
        isHiddenToolBar = getIntent().getBooleanExtra(COMMON_TOOLBAR, false);
        if (isHiddenToolBar) {
            return null;
        } else
            return super.toolBarLayout();
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initView() {
        String fragmentClassName = getIntent().getStringExtra(COMMON_FRAGMENT_CLASS_NAME);
        Bundle fragmentArgument = getIntent().getBundleExtra(COMMON_FRAGMENT_ARGUMENTS);

//            fragment = (Fragment) Class.forName(fragmentClassName).newInstance();
        // 采用 instantiate 方法初始化 Fragment, 可将 fragment 需要的参数传入,  方便使用
        Fragment fragment = Fragment.instantiate(mContext, fragmentClassName, fragmentArgument);

        if (fragment == null) {
            throw new RuntimeException("fragment will not null " + fragmentClassName);
        }
        //显示fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fl_common, fragment).commit();

        if (fragment instanceof OnMenuRightClickListener) {  //显示右边menu
            if (isHiddenToolBar)
                throw new RuntimeException("isHiddenToolBar true hidden toolbar ,implements OnMenuRightClickListener error,need cancel implements");
            OnMenuRightClickListener mListener = (OnMenuRightClickListener) fragment;
            if (!TextUtils.isEmpty(mListener.getMenuRightTitle())) {
                mCustomTitleLayout.setRightTitleVisible(true)
                        .setRightTitle(mListener.getMenuRightTitle());
            } else if (mListener.getMenuRightIcon() != 0) {
                mCustomTitleLayout.setRightImgVisible(true)
                        .setRightImage(mListener.getMenuRightIcon());
            }
            mCustomTitleLayout.setOnRightImgListener(v -> {
                mListener.onMenuRightClick(v);
            }).setOnRightTextClickListener(v -> {
                mListener.onMenuRightClick(v);
            });
        }


    }

    @Override
    public void initData() {

    }

    private BackHandlerAction mBackHandlerAction;

    public void setBackHandlerAction(BackHandlerAction backHandlerAction) {
        mBackHandlerAction = backHandlerAction;
    }


    @Override
    public Presenter initPresenter() {
        return null;
    }

    @Override
    public void onBackPressed() {
        if (mBackHandlerAction != null) {
            mBackHandlerAction.onCustomBackPressed();
        } else
            super.onBackPressed();
    }

    /**
     * 对于需要显示又边menu的fragment需要实现该接口
     */
    public interface OnMenuRightClickListener {

        /**
         * item 显示的文字
         *
         * @return
         */
        String getMenuRightTitle();

        /**
         * item 显示的图标   若没有返回0
         *
         * @return
         */
        int getMenuRightIcon();

        /**
         * 当右边的view被点击的回调
         *
         * @param view (instanceof ImageView or TextView)
         */
        void onMenuRightClick(View view);

    }
}
