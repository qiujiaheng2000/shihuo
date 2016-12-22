package com.shihuo.shihuo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.autolabel.CustomAutoLabelStoreUi;
import com.shihuo.shihuo.Views.autolabel.CustomAutoLabelTypeUi;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.models.StoreDetailModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiahengqiu on 2016/10/23.
 * 便民服务、微视频的headerview
 */

public class CustomAutolabelHeaderView extends LinearLayout {


    @BindView(R.id.label_view_syssecond)
    CustomAutoLabelTypeUi labelViewSyssecond;
    @BindView(R.id.label_view)
    CustomAutoLabelStoreUi labelView;

    public CustomAutolabelHeaderView(Context context) {
        super(context);
        initView();
    }

    public CustomAutolabelHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomAutolabelHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custom_autolabel_header_view, null);
        ButterKnife.bind(this, view);

        addView(view);
    }

    public void addAutoLabels(ArrayList<GoodsTypeModel> goodsTypeModels, ArrayList<StoreDetailModel> storeDetailModels) {

        for (GoodsTypeModel goodsTypeModel : goodsTypeModels) {
            labelViewSyssecond.addLabel(goodsTypeModel);
        }
        for (StoreDetailModel storeDetailModel : storeDetailModels) {
            labelView.addLabel(storeDetailModel);
        }

    }


//    public void setListeners(AutoLabelUI.OnLabelsCompletedListener onLabelsCompletedListener,
//                             AutoLabelUI.OnRemoveLabelListener onRemoveLabelListener,
//                             AutoLabelUI.OnLabelsEmptyListener onLabelsEmptyListener,
//                             AutoLabelUI.OnLabelClickListener onLabelClickListener) {
//        labelView.setOnLabelsCompletedListener(onLabelsCompletedListener);
//
//        labelView.setOnRemoveLabelListener(onRemoveLabelListener);
//
//        labelView.setOnLabelsEmptyListener(onLabelsEmptyListener);
//
//        labelView.setOnLabelClickListener(onLabelClickListener);
//
//    }


}
