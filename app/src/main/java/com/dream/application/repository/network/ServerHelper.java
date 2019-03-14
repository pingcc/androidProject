package com.dream.application.repository.network;

import com.user.fun.library.repository.BaseServerHelper;

/**
 * Created on 2017/6/26.
 * @author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */

/**
 * todo 处理网络请求类
 */
public class ServerHelper extends BaseServerHelper {

    private ServerHelper() {
    }

    private final static ServerHelper mServerHelper = new ServerHelper();

    public static ServerHelper getInstance() {
        return mServerHelper;
    }




 /*  example :
  public Flowable<BaseModel<LoginUserInfo>> loginPost(LoginBody loginBody) {
        return RetrofitManager.getInstance().getNetApiService().login(loginBody).compose(handleResult());
    }
    public Flowable<BaseModel<String>> sendCodePost(SendVCodeBody sendVCodeBody) {
        sendVCodeBody.Application = 1;
        return rxSchedulers(RetrofitManager.getInstance().getNetApiService().sendVCode(sendVCodeBody));
    }
   */

}
