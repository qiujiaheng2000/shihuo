
package com.shihuo.shihuo.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.shihuo.shihuo.models.LoginModel;

/**
 * SharePreferences工具类
 */
public class AppShareUitl {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "FireMan_SharePreferences";

    private static final String DATA_DEFAULT = "";

    private Context mContext;

    private static SharedPreferences sp;

    private static SharedPreferences.Editor editor;

    private AppShareUitl() {

    }

    public static SharedPreferences getInstance(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            editor = sp.edit();
        }
        return sp;
    }

    /**
     * 保存用户信息
     * 
     * @param context
     * @param strJson
     */
    public static void saveUserInfo(Context context, String strJson) {
        getInstance(context);
        editor.putString("USER_INFO", strJson);
        if (!TextUtils.isEmpty(strJson)){
            LoginModel model = LoginModel.parseStrJson(strJson);
            if(model != null){
                saveToken(context, model.token);
            }
        }
        editor.commit();
    }

    public static LoginModel getUserInfo(Context context) {
        getInstance(context);
        LoginModel model = new LoginModel();
        String strJson = sp.getString("USER_INFO", "");
        if (!TextUtils.isEmpty(strJson)){
            model = LoginModel.parseStrJson(strJson);
        }
        return model;
    }

    public static void saveToken(Context context, String token) {
        getInstance(context);
        editor.putString("USER_TOKEN", token);
        editor.commit();
    }

    public static String getToken(Context context) {
        getInstance(context);
        String token = sp.getString("USER_TOKEN", "");
        return token;
    }

    /**
     * 判断是否登录
     * @param context
     * @return
     */
    public static boolean isLogin(Context context) {
        LoginModel model = getUserInfo(context);
        if (TextUtils.isEmpty(model.token))
            return false;
        return true;
    }
}
