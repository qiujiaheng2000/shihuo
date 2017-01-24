
package com.shihuo.shihuo.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher;
import com.mylhyl.crlayout.SwipeRefreshRecyclerView;
import com.mylhyl.crlayout.internal.LoadConfig;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.BaseApplication;
import com.shihuo.shihuo.models.UpdateModel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lishuai on 16/11/26.
 *
 */

public class AppUtils {

    private static final String SEARCH_HISTORY = "search_history";

    private static final String SEARCH_HISTORY_SPLIT = ",";

    public static Map<String, String> getOAuthMap(Context context) {
        if (context == null)
            context = BaseApplication.getInstance();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Pragma", "no-cache");
        headers.put("Cache-Control", "no-cache");
        headers.put("charset", "UTF-8");
        // headers.put("Authorization", token);
        return headers;
    }

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

    public static void initFresco(Context context) {

        ProgressiveJpegConfig pjpegConfig = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int scanNumber) {
                return scanNumber + 2;
            }

            public QualityInfo getQualityInfo(int scanNumber) {
                boolean isGoodEnough = (scanNumber >= 5);
                return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
            }
        };

        // 内存配置
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(20 * 1024 * 1024, // 内存缓存中总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE, // 内存缓存中图片的最大数量。
                20 * 1024 * 1024, // 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE, // 内存缓存中准备清除的总图片的最大数量。
                Integer.MAX_VALUE); // 内存缓存中单个图片的最大大小。

        // 修改内存图片缓存数量，空间策略（这个方式有点恶心）
        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };
        NoOpMemoryTrimmableRegistry.getInstance().registerMemoryTrimmable(new MemoryTrimmable() {
            @Override
            public void trim(MemoryTrimType trimType) {
                final double suggestedTrimRatio = trimType.getSuggestedTrimRatio();
                if (MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground
                                .getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground
                                .getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnAppBackgrounded.getSuggestedTrimRatio() == suggestedTrimRatio) {
                    ImagePipelineFactory.getInstance().getImagePipeline().clearMemoryCaches();
                }
            }
        });
        final ImagePipelineConfig config = ImagePipelineConfig
                .newBuilder(context)
                .setProgressiveJpegConfig(pjpegConfig)
                .setNetworkFetcher(new HttpUrlConnectionNetworkFetcher())
                .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)
                .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(context).setBaseDirectoryName("image_cache")
                                .setBaseDirectoryPath(new File(getPicturePath(context)))
                                .setMaxCacheSize(100 * ByteConstants.MB)
                                .setMaxCacheSizeOnLowDiskSpace(50 * ByteConstants.MB)
                                .setMaxCacheSizeOnVeryLowDiskSpace(20 * ByteConstants.MB).build())
                .build();
        Fresco.initialize(context, config);
    }

    public static String getPicturePath(Context context) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
            return context.getFilesDir().getAbsolutePath();
        File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (file != null)
            return file.getAbsolutePath();
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (file != null)
            return file.getAbsolutePath();
        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * fresco 设置图片
     * 
     * @param url
     * @return
     */
    public static Uri parse(String url) {
        if (TextUtils.isEmpty(url))
            return Uri.parse("");
        return Uri.parse(url);
    }
  /**
     * fresco 设置图片
     *
     * @param url
     * @return
     */
    public static Uri parseFromSDCard(String url) {
        if (TextUtils.isEmpty(url))
            return Uri.parse("");
        return Uri.parse("file://"+url);
    }

    /**
     * 初始化listview
     * 
     * @param context
     * @param swipeRefresh
     */
    public static void initSwipeRefresh(Context context, SwipeRefreshRecyclerView swipeRefresh) {
        swipeRefresh.setLoadAnimator(true);
        swipeRefresh.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        LoadConfig loadConfig = swipeRefresh.getLoadConfig();
        loadConfig.setLoadCompletedText("亲，数据加载完了！");
        loadConfig.setLoadText("正在加载数据...");
        loadConfig.setProgressBarVisibility(View.VISIBLE);
    }

    /**
     * 判断是否为空, 适用于textview
     * 
     * @param string
     * @return
     */
    public static String isEmpty(String string) {
        if (TextUtils.isEmpty(string))
            return "";
        return string;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    /**
     * Toast提示
     *
     * @param message
     */
    public static void showToast(Context context, Object message) {
        showToast(context, message, true);
    }

    /**
     * Toast提示
     * 
     * @param message
     */
    public static void showToast(Context context, Object message, boolean isShort) {
        showToast(message, isShort);
    }

    /**
     * Toast提示
     * 
     * @param message
     */
    public static void showToast(Object message, boolean isShort) {
        BaseApplication.getInstance().showToast(message,
                isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
    }

    /**
     * 获取屏幕宽高
     *
     * @param activity
     * @return
     */
    public static int[] getScreenWidthAndHeight(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int wh[] = new int[2];
        wh[0] = metric.widthPixels; // 屏幕宽度（像素）
        wh[1] = metric.heightPixels;
        return wh;
    }

    public static void callPhone(Context context, String phoneNum){
        if(!TextUtils.isEmpty(phoneNum)){
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            context.startActivity(intent);
        }else{
            showToast(context, "暂时还没有客服电话哦");
        }
    }

    public static  Uri getResourceUri(int resId,String packageName)
    {
        return Uri.parse("res://"+packageName+"/"+resId);
    }

    /**
     * 隐藏键盘
     * 
     * @param context
     * @param view
     */
    public static void hideKeyBord(Context context, View view) {
        InputMethodManager imm = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
    }

    /**
     * 显示键盘
     * 
     * @param context
     * @param view
     */
    public static void showKeyBord(Context context, View view) {
        InputMethodManager imm = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 手机号验证
     * @author ：shijing
     * 2016年12月5日下午4:34:46
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 历史搜索数据 保存本地
     */
    public static void saveHistory(Context context, String text) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_APPEND);
        String oldText = preferences.getString(SEARCH_HISTORY, "");
        StringBuilder builder = new StringBuilder(text);
        builder.append(",").append(oldText);
        if (!TextUtils.isEmpty(text) && !oldText.contains(text + SEARCH_HISTORY_SPLIT)) {
            SharedPreferences.Editor myEditor = preferences.edit();
            myEditor.putString(SEARCH_HISTORY, builder.toString());
            myEditor.apply();
        }
    }

    /**
     * 清空本地历史
     */
    public void cleanHistory(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_APPEND);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    //版本号
    public static int getVersionName(Context context) {
        return getPackageInfo(context).versionCode;
    }

    public static void update(Context context, UpdateModel model, UpdateListener listener) {
        String serviceString = Context.DOWNLOAD_SERVICE;
        DownloadManager downloadManager;
        downloadManager = (DownloadManager) context.getSystemService(serviceString);
//        Uri uri = Uri.parse("http://alidown.yizhibo.tv/android/easylive-v3.9.3.apk");
        Uri uri = Uri.parse(model.downloadUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("识货");
        request.setDescription(model.versionInfo);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "shihuo.apk");
        long reference = downloadManager.enqueue(request);
        listener.download(reference);
    }

    public interface UpdateListener {
        public void download(long reference);
    }


//    /**
//     * 获取本地历史搜索数据
//     */
//
//    public List<String> searchHistory(Context context) {
//        List<String> list = new ArrayList<>();
//        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_APPEND);
//        String text = preferences.getString(SEARCH_HISTORY, null);
//        if (!TextUtils.isEmpty(text)) {
//            String[] arr = text.split(SEARCH_HISTORY_SPLIT);
////            for (String[] arr:) {
////
////            }
//        }
//    }

}
