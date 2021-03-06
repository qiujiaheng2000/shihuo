package com.shihuo.shihuo.Activities.shop.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.shihuo.shihuo.Activities.shop.models.GoodsPropertyModel;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.models.SpecificationModel;
import com.shihuo.shihuo.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/12/5.
 * 发布新品界面的属性规格自定义
 */

public class PublishPropertyView extends LinearLayout {


    public void setValue(SpecificationModel goodsSpecListEntity) {
        editOriginal.setText(String.valueOf(goodsSpecListEntity.prePrice));
        editCurrent.setText(String.valueOf(goodsSpecListEntity.curPrice));
        editRepertory.setText(String.valueOf(goodsSpecListEntity.stockNum));
        editStandard.setText(String.valueOf(goodsSpecListEntity.specName));
    }

    public interface RemoveListener {
        void removeFromParent(View view);
    }

    public RemoveListener getRemoveListener() {
        return removeListener;
    }

    public void setRemoveListener(RemoveListener removeListener) {
        this.removeListener = removeListener;
    }

    private RemoveListener removeListener;

    @BindView(R.id.edit_original)
    EditText editOriginal;
    @BindView(R.id.edit_current)
    EditText editCurrent;
    @BindView(R.id.edit_repertory)
    EditText editRepertory;
    @BindView(R.id.edit_standard)
    EditText editStandard;
    private Context context;

    public PublishPropertyView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public PublishPropertyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public PublishPropertyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.layout_publish_property, null);
        ButterKnife.bind(this, view);

        addView(view);
    }

    /**
     * 数据是否填写完整
     *
     * @return
     */
    public boolean isCompleted() {
        if (TextUtils.isEmpty(editOriginal.getText().toString())) {
            AppUtils.showToast(context,getResources().getString(R.string.hint_original));
            return false;
        }
        if (TextUtils.isEmpty(editCurrent.getText().toString())) {
            AppUtils.showToast(context,getResources().getString(R.string.hint_current));
            return false;
        }
        if (TextUtils.isEmpty(editRepertory.getText().toString())) {
            AppUtils.showToast(context,getResources().getString(R.string.hint_repertory));
            return false;
        }
        if (TextUtils.isEmpty(editStandard.getText().toString())) {
            AppUtils.showToast(context,getResources().getString(R.string.hint_standard));
            return false;
        }
        return true;
    }

    /**
     * 获取属性对象
     *
     * @return
     */
    public GoodsPropertyModel getPropertyModel() {


        return new GoodsPropertyModel(Float.parseFloat(editOriginal.getText().toString()),
                Float.parseFloat(editCurrent.getText().toString()),
                Integer.parseInt(editRepertory.getText().toString()),
                editStandard.getText().toString());
    }

    @OnClick(R.id.btn_close)
    public void onClick() {
        if (removeListener != null) {
            removeListener.removeFromParent(this);
        }
    }
}
