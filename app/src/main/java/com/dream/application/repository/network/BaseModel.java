package com.dream.application.repository.network;


import com.dream.application.config.Constants;

/**
 * Created on 2017/8/31.
 * @author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */
public class BaseModel<T>{

    public int code;
    public T data;
    public String msg;

    public boolean isSuccess() {
        return code == Constants.OK;
    }
}
