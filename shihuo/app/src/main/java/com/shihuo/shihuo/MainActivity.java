package com.shihuo.shihuo;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.baoyz.actionsheet.ActionSheet;
import com.jph.takephoto.model.TResult;
import com.shihuo.shihuo.Activities.BaseActivity;
import com.shihuo.shihuo.Views.MyViewPager;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.dialog.AlertDialog;
import com.shihuo.shihuo.fragments.HomeFragment;
import com.shihuo.shihuo.fragments.MeFragment;
import com.shihuo.shihuo.fragments.ServiceFragment;
import com.shihuo.shihuo.fragments.VideoFragment;
import com.shihuo.shihuo.models.UpdateModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.FileUtils;
import com.shihuo.shihuo.util.Toaster;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

public class MainActivity extends BaseActivity {


    @BindView(R.id.viewpager)
    MyViewPager viewpager;
    @BindView(R.id.tabbar)
    RadioGroup tabbar;
    @BindView(R.id.home)
    RadioButton home;
    @BindView(R.id.video)
    RadioButton video;
    @BindView(R.id.service)
    RadioButton service;
    @BindView(R.id.me)
    RadioButton me;

    HomeFragment mHomeFragment;
    VideoFragment mVideoFragment;
    ServiceFragment mServiceFragment;
    MeFragment mMeFragment;
    MainViewPagerAdapter mainViewPagerAdapter;

    public static final int TAB_HOME = 0;
    public static final int TAB_VIDEO = 1;
    public static final int TAB_SERVICE = 2;
    public static final int TAB_ME = 3;
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    private Handler mHandler = new MyHandler(this);

    private Fragment mCurrentFrg;

    private long downloadReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(receiver, filter);
        checkVersion();
    }

    /**
     * 版本更新
     */
    private void checkVersion() {
        String url = NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_UPDATE_APP) + "?platform=android";
        try {
            OkHttpUtils.
                    get()
                    .url(url)
                    .build()
                    .execute(new ShihuoStringCallback() {
                @Override
                public void onResponse(ShiHuoResponse response, int id) {
                    if (response.code == ShiHuoResponse.SUCCESS
                            && !TextUtils.isEmpty(response.data)) {
                        try {
                            UpdateModel model = UpdateModel.parseStrJson(response.data);
                            int versionCode = AppUtils.getVersionName(MainActivity.this);
                            if (model != null && model.versionNum > versionCode) {
                                showVersionDialog(model);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initViews() {

    }

    private void initView() {
        mHomeFragment = HomeFragment.newInstance();
        mVideoFragment = VideoFragment.newInstance();
        mServiceFragment = ServiceFragment.newInstance();
        mMeFragment = MeFragment.newInstance();
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFrgment(mHomeFragment);
        mainViewPagerAdapter.addFrgment(mVideoFragment);
        mainViewPagerAdapter.addFrgment(mServiceFragment);
        mainViewPagerAdapter.addFrgment(mMeFragment);
        viewpager.setAdapter(mainViewPagerAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case TAB_HOME:
                        home.setChecked(true);
                        home.setSelected(true);
                        video.setSelected(false);
                        service.setSelected(false);
                        me.setSelected(false);
                        mCurrentFrg = mHomeFragment;
                        break;
                    case TAB_VIDEO:
                        video.setChecked(true);
                        home.setSelected(false);
                        video.setSelected(true);
                        service.setSelected(false);
                        me.setSelected(false);
                        mCurrentFrg = mVideoFragment;
                        break;
                    case TAB_SERVICE:
                        service.setChecked(true);
                        home.setSelected(false);
                        video.setSelected(false);
                        service.setSelected(true);
                        me.setSelected(false);
                        mCurrentFrg = mServiceFragment;
                        break;
                    case TAB_ME:
                        me.setChecked(true);
                        home.setSelected(false);
                        video.setSelected(false);
                        service.setSelected(false);
                        me.setSelected(true);
                        mCurrentFrg = mMeFragment;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setNoScroll(true);
        tabbar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.home:
                        viewpager.setCurrentItem(TAB_HOME);
                        break;
                    case R.id.video:
                        viewpager.setCurrentItem(TAB_VIDEO);
                        break;
                    case R.id.service:
                        viewpager.setCurrentItem(TAB_SERVICE);
                        break;
                    case R.id.me:
                        viewpager.setCurrentItem(TAB_ME);
                        break;
                    default:
                        break;
                }
            }
        });
        home.setChecked(true);
        home.setSelected(true);
    }

    public static class MainViewPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments = new ArrayList<>();

        public void addFrgment(Fragment frg) {
            fragments.add(frg);
        }

        public void removeFragment(Fragment frg) {
            fragments.remove(frg);
        }


        public MainViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position > fragments.size() - 1) {
                return null;
            }
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() == null) {
                return;
            }
            isExit = false;
        }
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toaster.toastShort(getResources().getString(R.string.exit_show));
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void takeSuccess(final TResult takeResult) {
        Log.i("takePhoto", "takeSuccess：" + takeResult.getImage().getCompressPath());

        if (mCurrentFrg instanceof MeFragment) {
            ((MeFragment) mCurrentFrg).setPhoto(takeResult);
        }

        AliyunHelper.getInstance().asyncUplodaFile(takeResult.getImage().getCompressPath(), new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                //阿里云上传成功了，上传到自己的服务器
                modifyAvatar(FileUtils.getFileName(takeResult.getImage().getCompressPath()));
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
                Toaster.toastShort("修改头像失败");
            }
        });


    }

    /**
     * 修改用户头像
     */
    private void modifyAvatar(String avatarUrl) {
//        showProgressDialog();
        JSONObject params = new JSONObject();
        try {
            params.put("avatarPic", avatarUrl);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_POST_USERICON) + "?token=" + AppShareUitl.getUserInfo(this).token)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
//                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            Toaster.toastShort("修改头像成功");
                        } else {
                            AppUtils.showToast(MainActivity.this, response.msg);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        hideProgressDialog();
                        Toaster.toastShort("修改头像失败");
                    }
                });
    }

    public void getPhoto() {
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("拍照", "相册")
                .setCancelableOnTouchOutside(true)
                .setListener(this).show();
    }

    private void showVersionDialog(final UpdateModel model) {
        final AlertDialog dialog = new AlertDialog(MainActivity.this);
        dialog.setDialogContent("请更新识货最新版本-" + model.versionName, model.versionInfo, "取消", "下载更新");
        dialog.setLeftRightClick(new AlertDialog.OnLeftRightClick() {
            @Override
            public void onLeftClick() {
                dialog.dismiss();
            }

            @Override
            public void onRightClick() {
                dialog.dismiss();
                AppUtils.update(MainActivity.this, model,
                        new AppUtils.UpdateListener() {
                            @Override
                            public void download(long reference) {
                                downloadReference = reference;
                            }
                        });
            }
        });
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (myDwonloadID == downloadReference) {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(
                        new File(Environment.getExternalStorageDirectory() + "/download/shihuo.apk")),
                        "application/vnd.android.package-archive");
                startActivity(intent);
            }
        }
    };
}
