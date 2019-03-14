package com.dream.application.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.user.fun.library.util.ToastUtils;


/**
 * Created on 2017/7/17.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 * desc manage base common method
 */

public class BaseCommonMethodActivity extends OriginalActivity {
    /**
     * 打开公用界面
     *
     * @param title 标题
     * @param cls   获取fragment的Class   eg: ConfirmOrderFragment.class
     */
    public void jumpCommonActivity(String title, Class<? extends Fragment> cls) {
        jumpCommonActivity(title, cls, null);
    }



    /**
     * 打开公用界面
     *
     * @param title 标题
     * @param cls   获取fragment的Class   eg: ConfirmOrderFragment.class
     * @param args  fragment 所需要的传入的参数
     */
    public void jumpCommonActivity(String title, Class<? extends Fragment> cls, Bundle args) {
        jumpCommonActivityForResult(title, cls, args, 0);
    }

    /**
     * 跳转公共界面
     *
     * @param title
     * @param cls
     * @param args
     * @param request 请求的code
     */
    public void jumpCommonActivityForResult(String title, Class<? extends Fragment> cls, Bundle args, int request) {
        jumpCommonActivityForResult(title,false, cls.getName(), args, request);
    }
    /** hidden toolbar*/
    public void jumpHiddenToolBarCommonActivity(Class<? extends Fragment> cls) {
        jumpHiddenToolBarCommonActivity(  cls,null);
    }
    public void jumpHiddenToolBarCommonActivity(Class<? extends Fragment> cls, Bundle args) {
        jumpCommonActivityForResult("",true, cls.getName(), args, 0);
    }
    /**
     * 跳转公共界面
     *
     * @param title
     * @param cls
     * @param args
     * @param request 请求的code
     */
    public void jumpCommonActivityForResult(String title, boolean isHiddenToolBar, String cls, Bundle args, int request) {
        Intent intent = new Intent(getBaseContext(), CommonActivity.class);
        intent.putExtra(CommonActivity.COMMON_FRAGMENT_CLASS_NAME, cls);
        intent.putExtra(CommonActivity.COMMON_TITLE, title);
        intent.putExtra(CommonActivity.COMMON_TOOLBAR, isHiddenToolBar);
        intent.putExtra(CommonActivity.COMMON_FRAGMENT_ARGUMENTS, args);
        super.startActivityForResult(intent, request);
    }
    public void showToast(String str){
        ToastUtils.showSingleToast(str);
    }

}
