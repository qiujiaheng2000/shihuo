package com.shihuo.shihuo.Activities.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.jph.takephoto.model.TResult;
import com.shihuo.shihuo.Activities.BaseActivity;
import com.shihuo.shihuo.Activities.shop.models.GoodsPropertyModel;
import com.shihuo.shihuo.Activities.shop.views.AddImageView;
import com.shihuo.shihuo.Activities.shop.views.PublishPropertyView;
import com.shihuo.shihuo.BuildConfig;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.models.LoginModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.Toaster;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by cm_qiujiaheng on 2016/12/4.
 * 商品发布界面
 */

public class PublishGoodsActivity extends BaseActivity implements PublishPropertyView.RemoveListener, AddImageView.OnAddImageClickListener {

    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.spinner_shop_type)
    AppCompatSpinner spinnerShopType;
    @BindView(R.id.spinner_goods_type)
    AppCompatSpinner spinnerGoodsType;
    @BindView(R.id.edittext_goods_name)
    EditText edittextGoodsName;
    @BindView(R.id.edittext_goods_desc)
    EditText edittextGoodsDesc;
    @BindView(R.id.layout_properties)
    LinearLayout layoutProperties;
    @BindView(R.id.btn_addproperties)
    Button btnAddproperties;
    @BindView(R.id.radiogroup_distribution)
    RadioGroup radiogroupDistribution;
    @BindView(R.id.btn_publishgoods)
    Button btnPublishgoods;
    @BindView(R.id.addiamge_1)
    AddImageView addiamge1;
    @BindView(R.id.addiamge_2)
    AddImageView addiamge2;

    //本店分类列表
    private ArrayList<GoodsTypeModel> goodsTypeModels = new ArrayList<>();

    private GoodsTypeSpinnerAdapter goodsTypeSpinnerAdapter;

    private AddImageView currentAddImageView;//当前点击的图片选择器
    private ArrayList<PublishPropertyView> publishPropertyViews;

    public static void start(Context context) {
        Intent intent = new Intent(context, PublishGoodsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_publishgoods);
        ButterKnife.bind(this);
        initViews();
        getGoodsTypeList();
    }

    private void getGoodsTypeList() {
        final LoginModel userModel = AppShareUitl.getUserInfo(PublishGoodsActivity.this);
        //本店商品分类
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_GOODSTYPELIST))
                .addParams("token", userModel.token)
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
                                goodsTypeSpinnerAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(PublishGoodsActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                });
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(R.string.title_publish_new_goods);
        addiamge1.setOnAddImageClickListener(this);
        addiamge2.setOnAddImageClickListener(this);

        goodsTypeModels = new ArrayList<>();
        goodsTypeSpinnerAdapter = new GoodsTypeSpinnerAdapter();
        spinnerShopType.setAdapter(goodsTypeSpinnerAdapter);
    }

    @OnClick({R.id.imag_left, R.id.btn_addproperties, R.id.btn_publishgoods})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_addproperties:
                addProperties();
                break;
            case R.id.btn_publishgoods:
                publishGoods();
                break;
        }
    }

    private void publishGoods() {
        if (TextUtils.isEmpty(edittextGoodsName.getText().toString())) {
            edittextGoodsName.setError("请输入商品名称");
            return;
        }
        if (TextUtils.isEmpty(edittextGoodsDesc.getText().toString())) {
            edittextGoodsDesc.setError("请输入商品描述");
            return;
        }

        JSONArray jsonArray = new JSONArray();

        //判断商品属性规格是否完全,并获取商品属性规格json对象
        for (int i = 0; i < publishPropertyViews.size(); i++) {
            PublishPropertyView publishPropertyView = publishPropertyViews.get(i);
            if (!publishPropertyView.isCompleted()) {
                return;
            }
            GoodsPropertyModel goodsPropertyModel = publishPropertyView.getPropertyModel();
            try {
                jsonArray.put(new JSONObject(goodsPropertyModel.toJsonStr()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //获取滑动图片列表json
        JSONArray jsonArraypic = new JSONArray();
        for (int i = 0; i < addiamge1.getImageNames().size(); i++) {
            jsonArraypic.put(addiamge1.getImageNames().get(i));
        }

        JSONArray jsonArrayDetailPic = new JSONArray();
        for (int i = 0; i < addiamge2.getImageNames().size(); i++) {
            jsonArrayDetailPic.put(addiamge2.getImageNames().get(i));
        }

        JSONObject params = new JSONObject();
        try {
            params.put("sysGoodsTypeId", 1);
            params.put("goodsTypeId", spinnerGoodsType.getSelectedItemId());
            params.put("goodsName", edittextGoodsName.getText().toString());
            params.put("goodsDetail", edittextGoodsDesc.getText().toString());
            params.put("goodsSpec", jsonArray.toString());//商品规格
            params.put("goodsPic", jsonArraypic.toString());//滑动图片
            params.put("goodsDetailPic", jsonArrayDetailPic.toString());//详情图片

        } catch (JSONException e) {
            e.printStackTrace();
        }
        LoginModel userModel = AppShareUitl.getUserInfo(this);
        if (null == userModel.token) {
            Toaster.toastShort("用户token错误");
            return;
        }
        OkHttpUtils
                .postString()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_OPENSHOP) + "?token=" + userModel.token)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            if (BuildConfig.DEBUG) {
                                Toast.makeText(PublishGoodsActivity.this, response.data, Toast.LENGTH_SHORT).show();
                            } else {
                                Toaster.toastShort(getResources().getString(R.string.shoplocated_ok));
                            }
                        } else {
                            Toast.makeText(PublishGoodsActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                });


    }

    private void addProperties() {
        PublishPropertyView publishPropertyView = new PublishPropertyView(PublishGoodsActivity.this);
        layoutProperties.addView(publishPropertyView);
        publishPropertyViews.add(publishPropertyView);
        publishPropertyView.setRemoveListener(this);
    }

    @Override
    public void removeFromParent(View view) {
        layoutProperties.removeView(view);
        publishPropertyViews.remove(view);
    }

    @Override
    public void onBtnClick(AddImageView addImageView) {
        currentAddImageView = addImageView;
        getPhoto();
    }

    @Override
    public void takeSuccess(TResult result) {
        Log.i("takePhoto", "takeSuccess：" + result.getImage().getCompressPath());
        currentAddImageView.addImageView(result.getImage().getCompressPath());
        AliyunHelper.getInstance().asyncUplodaFile(result.getImage().getCompressPath(), new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
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
            }
        });

    }

    /**
     * 商圈区域spinner适配器
     */
    class GoodsTypeSpinnerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return goodsTypeModels.size();
        }

        @Override
        public Object getItem(int position) {
            return goodsTypeModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return goodsTypeModels.get(position).typeId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(PublishGoodsActivity.this).inflate(R.layout.item_spinner, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            GoodsTypeModel goodsTypeModel = (GoodsTypeModel) getItem(position);
            viewHolder.spinnerItem.setText(goodsTypeModel.circleName);
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.spinner_item)
            TextView spinnerItem;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}