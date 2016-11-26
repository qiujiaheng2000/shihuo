package com.shihuo.shihuo.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.shihuo.shihuo.R;

/**
 * Created by lishuai on 16/11/26.
 */

public class AppUtils {
    /**
     * 沉浸式-指定颜色
     *
     * @param context
     */
    @TargetApi(19)
    public static void fullScreenColor(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = context.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (true) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
            SystemBarTintManager mTintManager = new SystemBarTintManager(context);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setNavigationBarTintEnabled(true);
            mTintManager.setTintColor(context.getResources().getColor(R.color.titlebar_bg));

        }
    }

    /**
     * 沉浸式-指定颜色
     *
     * @param context
     */
    @TargetApi(19)
    public static void fullScreenColor(Activity context, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = context.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (true) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
            SystemBarTintManager mTintManager = new SystemBarTintManager(context);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setNavigationBarTintEnabled(true);
            mTintManager.setTintColor(color);

        }
    }
}
