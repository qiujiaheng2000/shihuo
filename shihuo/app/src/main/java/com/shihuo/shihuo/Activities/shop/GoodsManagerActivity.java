package com.shihuo.shihuo.Activities.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shihuo.shihuo.Activities.BaseActivity;
import com.shihuo.shihuo.Activities.shop.fragments.GoodsManagerFragment;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.models.LoginModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.Toaster;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by cm_qiujiaheng on 2016/12/4.
 * 商品管理界面
 */
public class GoodsManagerActivity extends BaseActivity {

    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content_frame)
    FrameLayout contentFrame;
    @BindView(R.id.radiogroup_goodstype)
    RadioGroup radiogroupGoodstype;


    //本店分类列表
    private ArrayList<GoodsTypeModel> goodsTypeModels = new ArrayList<>();

    public static void start(Context context) {
        Intent intent = new Intent(context, GoodsManagerActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_goods_manager);
        ButterKnife.bind(this);
        initViews();
        getGoodsTypeList();
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(R.string.shopmanager_list_title);
        radiogroupGoodstype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment =fragmentManager.findFragmentByTag("fragment" + checkedId);
                if (fragment == null) {
                    fragment = GoodsManagerFragment.newInstance(checkedId);
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.commit();
            }
        });
    }

    @OnClick(R.id.imag_left)
    public void onClick() {
        finish();
    }

    private void getGoodsTypeList() {
        final LoginModel userModel = AppShareUitl.getUserInfo(GoodsManagerActivity.this);
        //本店商品分类
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_GOODSTYPELIST))
                .addParams("token", userModel.token)
                .addParams("storeId", userModel.storeId)
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                JSONArray jsonArray = new JSONObject(response.data).getJSONArray("dataList");
                                goodsTypeModels.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    GoodsTypeModel goodsTypeModel = GoodsTypeModel.parseJsonStr(jsonArray.getJSONObject(i));
                                    goodsTypeModels.add(goodsTypeModel);
                                }
                                refreshView();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(GoodsManagerActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                });
    }

    /**
     * 刷新左侧类别列表
     */
    private void refreshView() {
        for (int i = 0; i < goodsTypeModels.size(); i++) {
            //添加radiobutton
            RadioButton radioButton = (RadioButton) LayoutInflater.from(this).inflate(R.layout.layout_goodsmanager_radiobutton, null);
            radioButton.setId(goodsTypeModels.get(i).typeId);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setPadding(0, AppUtils.dip2px(GoodsManagerActivity.this, 10), 0, AppUtils.dip2px(GoodsManagerActivity.this, 10));
            radioButton.setText(goodsTypeModels.get(i).typeName);
            radiogroupGoodstype.addView(radioButton);
            //添加分割线
            View view = new View(this);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
            view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            view.setLayoutParams(layoutParams1);
            radiogroupGoodstype.addView(view);

            if (i == 0) {
                radioButton.setChecked(true);
            }
        }
    }


}
