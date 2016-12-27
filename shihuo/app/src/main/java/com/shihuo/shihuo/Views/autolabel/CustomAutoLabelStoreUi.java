package com.shihuo.shihuo.Views.autolabel;

import android.content.Context;
import android.util.AttributeSet;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.dpizarro.autolabel.library.Label;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.models.SpecificationModel;
import com.shihuo.shihuo.models.StoreDetailModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cm_qiujiaheng on 2016/12/11.
 * 根据店铺添加label，返回label
 */

public class CustomAutoLabelStoreUi extends AutoLabelUI {

    public interface LabelClickListner {
        void onLabelClick(StoreDetailModel storeDetailModel);
    }

    private LabelClickListner labelClickListner;

    private List<StoreDetailModel> storeDetailModels = new ArrayList<>();

    public StoreDetailModel getCheckedSpecificationModel() {
        return checkedStoreDetailModel;
    }

    private StoreDetailModel checkedStoreDetailModel;//当前选中的属性

    public CustomAutoLabelStoreUi(Context context) {
        super(context);
    }

    public CustomAutoLabelStoreUi(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomAutoLabelStoreUi(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean addLabel(StoreDetailModel storeDetailModel) {
        boolean success = this.addLabel(storeDetailModel.storeName);
        storeDetailModels.add(storeDetailModel);
        if (storeDetailModels.size() == 1) {
            checkedStoreDetailModel = storeDetailModel;
            setCheckedLabel(0);
        }
        return success;
    }

    protected void setCheckedLabel(int position) {
        labelArrayList.get(position).setBackgroundRes(R.drawable.autolabel_bg_checked);
    }

    @Override
    public void onClickLabel(Label label) {
        super.onClickLabel(label);
        int position = labelArrayList.indexOf(label);
        checkedStoreDetailModel = storeDetailModels.get(position);
        label.setBackgroundRes(R.drawable.autolabel_bg_checked);
        if (labelClickListner != null) {
            labelClickListner.onLabelClick(checkedStoreDetailModel);
        }

    }

    public LabelClickListner getLabelClickListner() {
        return labelClickListner;
    }

    public void setLabelClickListner(LabelClickListner labelClickListner) {
        this.labelClickListner = labelClickListner;
    }

}

