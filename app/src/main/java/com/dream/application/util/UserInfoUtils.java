package com.dream.application.util;


import com.user.fun.library.util.SPUtils;
import com.user.fun.library.util.SecuritySPUtils;

/**
 * Created on 2017/8/31.
 * @author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 * desc save user info
 */

public class UserInfoUtils {
    private UserInfoUtils() {
        securitySPUtils = new SecuritySPUtils();
    }

    public static final String PHONE = "phone";
    public static final String USERID = "UserId";
    public static final String AVATAR = "Avatar";

    public static final String NICKNAME = "nickname";

    public static final String TOKEN = "token";


    private static UserInfoUtils instance;
    private static SecuritySPUtils securitySPUtils;

    public static UserInfoUtils getInstance() {

        if (instance == null) {
            synchronized (UserInfoUtils.class) {
                if (instance == null) {
                    instance = new UserInfoUtils();
                }
            }
        }
        return instance;
    }


    /**
     * todo 待完善，保存用户信息，登录时调用
     */
    public void saveUserInfo(Object obj) {
        putLong(USERID, 12345L);
        putString(AVATAR, "");
        putString(TOKEN, "");
        putString(PHONE, "");
        putString(NICKNAME, "");
    }

    public void putString(String key, String value) {
        securitySPUtils.putString(key, value);
    }

    public void putLong(String key, long value) {
        securitySPUtils.putLong(key, value);
    }

    public void putInt(String key, int value) {
        securitySPUtils.putInt(key, value);
    }

    public void putBoolean(String key, boolean value) {
        securitySPUtils.putBoolean(key, value);
    }

    public String getString(String key, String def) {
        return securitySPUtils.getString(key, def);
    }

    public long getLong(String key, long def) {
        return securitySPUtils.getLong(key, def);
    }

    public int getInt(String key, int def) {
        return securitySPUtils.getInt(key, def);
    }


    public boolean getBoolean(String key) {
        return securitySPUtils.getBoolean(key);
    }

    public void removeSpKey(String key) {
        SPUtils.getInstance().remove(key);
    }

    public void clearLoginData() {
        SPUtils.getInstance().remove(USERID)
                .remove(AVATAR)
                .remove(PHONE)
                .remove(NICKNAME)
                .remove(TOKEN);
    }
}
