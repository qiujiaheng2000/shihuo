
package com.shihuo.shihuo.Activities.share;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.Contants;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Created by cm_qiujiaheng on 2017/1/9. 分享工具类
 */

public class ShareManager {

    public static void share(final Activity context, SHARE_MEDIA share_media, String shareContent) {
        new ShareAction(context)
                .setPlatform(share_media)
                .withTitle("运城识货购物网")
                .withText(shareContent)
                .withMedia(new UMImage(context, R.mipmap.ic_launcher))
                .withTargetUrl(Contants.SHARE_URL)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onResult(SHARE_MEDIA platform) {
                        Log.d("plat", "platform" + platform);
                        Toast.makeText(context, platform + " 分享成功", Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onError(SHARE_MEDIA platform, Throwable t) {
                        Toast.makeText(context, platform + " 分享失败", Toast.LENGTH_SHORT).show();
                        if (t != null) {
                            Log.d("throw", "throw:" + t.getMessage());
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(context, platform + " 分享取消", Toast.LENGTH_SHORT).show();
                    }
                }).share();
    }
}
