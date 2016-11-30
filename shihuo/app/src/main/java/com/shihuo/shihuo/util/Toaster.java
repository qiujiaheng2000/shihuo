
package com.shihuo.shihuo.util;



import android.widget.Toast;

import com.shihuo.shihuo.application.BaseApplication;


/**
 * 只会显示一个toast </br> 不会有延迟的toast
 * 
 * @author pengjun
 */
public class Toaster {

    /**
     * 唯一的toast
     */
    private static Toast mToast = null;

    private Toaster() {
    }

    public static void toastLong(final String tip){
    	showToast(tip, Toast.LENGTH_LONG);
    }
    
    public static void toastShort(final String tip){
    	showToast(tip, Toast.LENGTH_SHORT);
    }
    
    /**
     * 显示的可以及时清除
     * 
     * @param lastTime
     * @return
     */
    private static void showToast(final int stringid, final int lastTime) {
    	 if (mToast != null) {
             // mToast.cancel();
         } else {
             mToast = Toast.makeText(BaseApplication.app, stringid, lastTime);
         }
         mToast.setText(stringid);
         mToast.show();
    }

    /**
     * 显示的可以及时清除
     * 
     * @param tips
     * @param lastTime
     * @return
     */
    private static void showToast(final String tips, final int lastTime) {
    	if (mToast != null) {
            // mToast.cancel();
        } else {
            mToast = Toast.makeText(BaseApplication.app, tips, lastTime);
        }
        mToast.setText(tips);
        mToast.show();
    }


}
