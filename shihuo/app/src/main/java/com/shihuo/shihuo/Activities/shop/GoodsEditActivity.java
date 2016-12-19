package com.shihuo.shihuo.Activities.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.shihuo.shihuo.Activities.shop.models.GoodsPropertyModel;
import com.shihuo.shihuo.Activities.shop.views.PublishPropertyView;
import com.shihuo.shihuo.BuildConfig;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.GoodsDetailModel;
import com.shihuo.shihuo.models.GoodsModel;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.models.LoginModel;
import com.shihuo.shihuo.models.SpecificationModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.Toaster;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by cm_qiujiaheng on 2016/12/11.
 * 商品编辑界面
 */

public class GoodsEditActivity extends PublishGoodsActivity {
    public static final String KEY_GOODS_MODEL = "goodsModel";

    private GoodsModel goodsModel;
    private GoodsDetailModel goodsDetailModel;

    public static void start(Context context, GoodsModel goodsModel) {
        Intent intent = new Intent(context, GoodsEditActivity.class);
        intent.putExtra(KEY_GOODS_MODEL, goodsModel);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goodsModel = getIntent().getParcelableExtra(KEY_GOODS_MODEL);
    }

    @Override
    public void initViews() {
        super.initViews();
        title.setText(R.string.title_edit_goods);
    }

    protected void getGoodsGoodsById() {
        final LoginModel userModel = AppShareUitl.getUserInfo(GoodsEditActivity.this);
        //本店商品分类
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_SHOP_DETAIL))
                .addParams("token", userModel.token)
                .addParams("goodsId", goodsModel.goodsId)
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        hideProgressDialog();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.data);
                                goodsDetailModel = GoodsDetailModel.parseStrJson(response.data);
                                refreshView();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(GoodsEditActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                });
    }

    /**
     * 获取到商品详情以后刷新界面
     */
    private void refreshView() {
        int position = 0;
        //商品系统一级分类
        for (int i = 0; i < sysGoodsTypeModels.size(); i++) {
            GoodsTypeModel goodsTypeModel = sysGoodsTypeModels.get(i);
            if (goodsTypeModel.typeId == goodsDetailModel.sysGoodsTypeId) {
                position = i;
                sysGoodsTypeModelsTwo.clear();
                sysGoodsTypeModelsTwo.addAll(goodsTypeModel.shSysGoodsTypeList);
                break;
            }
        }
        spinnerSystemTypeOne.setSelection(position);

        //商品系统二级分类
        for (int i = 0; i < sysGoodsTypeModelsTwo.size(); i++) {
            GoodsTypeModel goodsTypeModel = sysGoodsTypeModelsTwo.get(i);
            if (goodsTypeModel.typeId == goodsDetailModel.sysGoodsTypeSecondId) {
                position = i;
                break;
            }
        }
        spinnerSystemTypeTwo.setSelection(position);
        //店铺商品分类
        for (int i = 0; i < goodsTypeModels.size(); i++) {
            GoodsTypeModel goodsTypeModel = goodsTypeModels.get(i);
            if (goodsTypeModel.typeId == goodsDetailModel.goodsTypeId) {
                position = i;
                break;
            }
        }
        spinnerGoodsType.setSelection(position);

        edittextGoodsName.setText(goodsDetailModel.goodsName);
        edittextGoodsDesc.setText(goodsDetailModel.goodsDetail);
        //商品规格属性
        if (goodsDetailModel.goodsSpecList != null) {
            for (int i = 0; i < goodsDetailModel.goodsSpecList.size(); i++) {
                SpecificationModel goodsSpecListEntity = goodsDetailModel.goodsSpecList.get(i);
                addProperties(goodsSpecListEntity);
            }
        }
        //商品照片
        if (goodsDetailModel.goodsPicsList != null) {
            for (int i = 0; i < goodsDetailModel.goodsPicsList.size(); i++) {
                GoodsDetailModel.GoodsPicsListEntity goodsSpecListEntity = goodsDetailModel.goodsPicsList.get(i);
                addiamge1.addImageView(AliyunHelper.getFullPathByName(goodsSpecListEntity.picUrl),false);
            }
        }

        //商品详情照片
        if (goodsDetailModel.goodsDetailPicsList != null) {
            for (int i = 0; i < goodsDetailModel.goodsDetailPicsList.size(); i++) {
                GoodsDetailModel.GoodsDetailPicsListEntity goodsDetailPicsListEntity = goodsDetailModel.goodsDetailPicsList.get(i);
                addiamge2.addImageView(AliyunHelper.getFullPathByName(goodsDetailPicsListEntity.picUrl),false);
            }
        }

        if (goodsModel.noShipFees == 1) {
            checkboxExemption.setChecked(true);
        }
        if (goodsModel.takeGoods == 1) {
            checkboxPickUp.setChecked(true);
        }
        if (goodsModel.courierDelivery == 1) {
            checkboxKuaidian.setChecked(true);
        }
    }

    private void addProperties(SpecificationModel goodsSpecListEntity) {
        PublishPropertyView publishPropertyView = new PublishPropertyView(GoodsEditActivity.this);
        publishPropertyView.setValue(goodsSpecListEntity);
        layoutProperties.addView(publishPropertyView);
        publishPropertyViews.add(publishPropertyView);
        publishPropertyView.setRemoveListener(this);
    }


    protected void publishGoods() {
        if (TextUtils.isEmpty(edittextGoodsName.getText().toString())) {
            edittextGoodsName.setError("请输入商品名称");
            return;
        }
        if (TextUtils.isEmpty(edittextGoodsDesc.getText().toString())) {
            edittextGoodsDesc.setError("请输入商品描述");
            return;
        }

        if (publishPropertyViews.size() == 0) {
            Toaster.toastShort("请添加商品规格");
            return;
        }
        JSONObject params = new JSONObject();
        try {
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
                JSONObject imagObjc = new JSONObject();
                imagObjc.put("picUrl", addiamge1.getImageNames().get(i));
                jsonArraypic.put(imagObjc);
            }
            //获取详情图片列表json
            JSONArray jsonArrayDetailPic = new JSONArray();
            for (int i = 0; i < addiamge2.getImageNames().size(); i++) {
                JSONObject imagObjc = new JSONObject();
                imagObjc.put("picUrl", addiamge2.getImageNames().get(i));
                jsonArrayDetailPic.put(imagObjc);
            }
            params.put("storeId", AppShareUitl.getUserInfo(this).storeId);
            params.put("goodsId", goodsDetailModel.goodsId);
            params.put("sysGoodsTypeId", spinnerSystemTypeOne.getSelectedItemId());
            params.put("sysGoodsTypeSecondId", spinnerSystemTypeTwo.getSelectedItemId());
            params.put("goodsTypeId", spinnerGoodsType.getSelectedItemId());
            params.put("goodsName", edittextGoodsName.getText().toString());
            params.put("goodsDetail", edittextGoodsDesc.getText().toString());
            params.put("goodsSpec", jsonArray);//商品规格
            params.put("goodsPic", jsonArraypic);//滑动图片
            params.put("goodsDetailPic", jsonArrayDetailPic);//详情图片

            if (checkboxExemption.isChecked()) {
                params.put("noShipFees", 1);
            } else {
                params.put("noShipFees", 0);
            }
            if (checkboxPickUp.isChecked()) {
                params.put("takeGoods", 1);
            } else {
                params.put("takeGoods", 0);
            }

            if (checkboxKuaidian.isChecked()) {
                params.put("courierDelivery", 1);
            } else {
                params.put("courierDelivery", 0);
            }


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
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_GOODS_UPDATE) + "?token=" + userModel.token)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(params.toString())
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            if (BuildConfig.DEBUG) {
                                Toast.makeText(GoodsEditActivity.this, response.data, Toast.LENGTH_SHORT).show();
                            } else {
                                Toaster.toastShort(getResources().getString(R.string.publis_goods_ok));
                            }
                            finish();
                        } else {
                            Toast.makeText(GoodsEditActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                });


    }

}
