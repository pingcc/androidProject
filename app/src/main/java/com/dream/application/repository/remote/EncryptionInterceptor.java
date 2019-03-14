package com.dream.application.repository.remote;

/**
 * Created on 2017/8/31.
 * @author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */


import com.dream.application.config.Constants;
import com.dream.application.util.UserInfoUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 对表单数据加密的请求操作
 */
public class EncryptionInterceptor implements Interceptor {


    /**
     * 拦截请求连接需要添加的数据
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
      String mToken = UserInfoUtils.getInstance().getString(UserInfoUtils.TOKEN, null);

        Request.Builder builder = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json");

        if (mToken != null) {
            builder.addHeader(Constants.token, mToken);
        }
        Request original = builder.build();

//        RequestBody requestBody = original.body();
//
//        if (requestBody != null && requestBody instanceof FormBody) {
//            FormBody formBody = (FormBody) requestBody;
//            FormBody.Builder builder = new FormBody.Builder();
//            for (int x = 0; x < formBody.size(); x++) {
//                String encodedValue = formBody.encodedValue(x);
//                builder.addEncoded(formBody.encodedName(x), encodedValue);
//            }
//            Request build = original.newBuilder().post(builder.build()).build();
//            return chain.proceed(build);
//        }

        return chain.proceed(original);
    }
}

