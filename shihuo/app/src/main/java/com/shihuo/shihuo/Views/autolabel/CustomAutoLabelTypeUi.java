package com.shihuo.shihuo.Views.autolabel;

import android.content.Context;
import android.util.AttributeSet;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.dpizarro.autolabel.library.Label;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.models.SpecificationModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cm_qiujiaheng on 2016/12/11.
 */

public class CustomAutoLabelTypeUi extends AutoLabelUI {

    public interface LabelClickListner {
        void onLabelClick(GoodsTypeModel goodsTypeModel);
    }

    private LabelClickListner labelClickListner;

    private List<GoodsTypeModel> goodsTypeModels = new ArrayList<>();

    public GoodsTypeModel getCheckedSpecificationModel() {
        return checkedSpecificationModel;
    }

    private GoodsTypeModel checkedSpecificationModel;//当前选中的属性

    public CustomAutoLabelTypeUi(Context context) {
        super(context);
    }

    public CustomAutoLabelTypeUi(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomAutoLabelTypeUi(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean addLabel(GoodsTypeModel goodsTypeModel) {
        boolean success = this.addLabel(goodsTypeModel.typeName);
        goodsTypeModels.add(goodsTypeModel);
        if (goodsTypeModels.size() == 1) {
            checkedSpecificationModel = goodsTypeModel;
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
        checkedSpecificationModel = goodsTypeModels.get(position);
        label.setBackgroundRes(R.drawable.autolabel_bg_checked);
        if (labelClickListner != null) {
            labelClickListner.onLabelClick(checkedSpecificationModel);
        }

    }

    public LabelClickListner getLabelClickListner() {
        return labelClickListner;
    }

    public void setLabelClickListner(LabelClickListner labelClickListner) {
        this.labelClickListner = labelClickListner;
    }

}

