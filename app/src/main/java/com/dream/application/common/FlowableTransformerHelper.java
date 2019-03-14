package com.dream.application.common;


import com.dream.application.repository.network.BaseModel;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;


/**
 * Created by ping on 2016/11/5.
 * e_mail: yy_cping@163.com
 */
public class FlowableTransformerHelper {
    /**
     * 对结果进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<BaseModel<T>, T> defaultSchedulers() {

        return (Flowable<BaseModel<T>> upstream) ->
                upstream.flatMap((BaseModel<T> result) -> {
                    if (result.isSuccess())
                        return createData(result.data);
                    else
                        return Flowable.error(new APIException(result.code, result.msg));

                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());


    }


    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Flowable<T> createData(final T data) {
        return Flowable.create(source -> {
            try {
                source.onNext(data);
                source.onComplete();
            } catch (Exception e) {
                source.onError(e);
            }
        }, BackpressureStrategy.ERROR);

    }

    /**
     * 自定义异常，当接口返回的{@link Response#code}不为{@link BaseModel#isSuccess}时，需要跑出此异常
     * eg：登陆时验证码错误；参数为传递等
     */
    public static class APIException extends Exception {
        public int code;
        public String message;

        public APIException(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
