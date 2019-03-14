package com.dream.application.repository.remote;


import android.util.SparseArray;

import com.dream.application.App;
import com.dream.application.common.HostType;
import com.dream.application.repository.network.NetApiService;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 2017/6/26.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */

public class RetrofitManager {
    //类似于HashMap 但比map效率高，需要指定集合大小
    private static SparseArray<RetrofitManager> retrofitManagers = new SparseArray<>(HostType
            .ROOT_COUNT);
    private NetApiService mNetApiService;

    private final long timeoutTimes = 30* 1000;

    public static RetrofitManager getInstance(int hostType) {
        RetrofitManager retrofitManager = retrofitManagers.get(hostType);
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (retrofitManager == null) {
                    retrofitManager = new RetrofitManager(hostType);
                    retrofitManagers.put(hostType, retrofitManager);
                }
            }
        }
        return retrofitManager;
    }

    public static RetrofitManager getInstance() {
        return getInstance(HostType.ROOT_1);
    }

    private RetrofitManager(int hostType) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl(HostType.getHost(hostType))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mNetApiService = retrofit.create(NetApiService.class);
    }

    public final NetApiService getNetApiService() {
        return mNetApiService;
    }


    private OkHttpClient getOkHttpClient() {
/*      LoggingInterceptor logging = new LoggingInterceptor(msg -> Logger.i("http : " + msg));
        logging.setLevel(BuildConfig.DEBUG ? LoggingInterceptor.Level.BODY : LoggingInterceptor.Level.NONE);*/

        return new OkHttpClient.Builder()
                .connectTimeout(timeoutTimes, TimeUnit.MILLISECONDS)
                .readTimeout(timeoutTimes, TimeUnit.MILLISECONDS)
                .writeTimeout(timeoutTimes, TimeUnit.MILLISECONDS)
//                .retryOnConnectionFailure(true) // 失败重发
                .addInterceptor(new EncryptionInterceptor())   //加密使用
               .addInterceptor(new ChuckInterceptor(App.getInstance())) //拦截okhttp数据
//                 .addInterceptor(logging)  //Log日志
                .build();

    }
}
