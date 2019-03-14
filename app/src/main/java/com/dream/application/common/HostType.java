package com.dream.application.common;


import com.dream.application.config.Api;

/**
 * Created on 2017/6/26.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */
public class HostType {
    /**
     * 多少种Host类型
     */
    public static final int ROOT_COUNT = 3;

    public static final int ROOT_1 = 1;
    public static final int ROOT_2 = 2;
    public static final int ROOT_3 = 3;

    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        String host;
        switch (hostType) {
            case HostType.ROOT_1:
                host = Api.BASE_ROOT_1;
                break;
            case HostType.ROOT_2:
                host = Api.BASE_ROOT_1;
                break;
            case HostType.ROOT_3:
                host = Api.BASE_ROOT_1;
                break;
            default:
                throw (new ArrayIndexOutOfBoundsException("类型越界"));
        }
        return host;
    }
}
