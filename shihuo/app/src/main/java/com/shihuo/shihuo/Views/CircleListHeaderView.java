package com.shihuo.shihuo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.autolabel.CustomAutoLabelUi;
import com.shihuo.shihuo.models.CircleListTopModel;
import com.shihuo.shihuo.models.SpecificationModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiahengqiu on 2016/10/23. 商圈列表二级页面的header
 */

public class CircleListHeaderView extends LinearLayout {

    @BindView(R.id.view_label_partition)
    CustomAutoLabelUi mPartitionLabelView;

    @BindView(R.id.view_label_start_store)
    CustomAutoLabelUi mStartStoreLabelView;

    /**
     * banner图
     */
    @BindView(R.id.view_banner)
    BannerView mBannerView;
    @BindView(R.id.circle_layout)
    LinearLayout circleLayout;
    @BindView(R.id.store_layout)
    LinearLayout storeLayout;

    private OnLabelChangeListener onLabelChangeListener;

    public interface OnLabelChangeListener {
        void onCircleChanged(SpecificationModel specificationModel);

        void onStoreChanged(SpecificationModel specificationModel);
    }


    private ArrayList<View> viewList = new ArrayList<>();

    public CircleListHeaderView(Context context, OnLabelChangeListener onLabelChangeListener) {
        super(context);
        this.onLabelChangeListener = onLabelChangeListener;
        initView();
    }

    public CircleListHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CircleListHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.view_circle_list_header, null);
        ButterKnife.bind(this, view);
        addView(view);
        mPartitionLabelView.setLabelClickListner(new CustomAutoLabelUi.LabelClickListner() {
            @Override
            public void onLabelClick(SpecificationModel specificationModel) {
                if (onLabelChangeListener != null) {
                    onLabelChangeListener.onCircleChanged(specificationModel);
                }
            }
        });
        mStartStoreLabelView.setLabelClickListner(new CustomAutoLabelUi.LabelClickListner() {
            @Override
            public void onLabelClick(SpecificationModel specificationModel) {
                if (onLabelChangeListener != null) {
                    onLabelChangeListener.onStoreChanged(specificationModel);
                }
            }
        });
    }

    /**
     * 绑定数据
     */
    public void bindData(CircleListTopModel model) {
        if (model != null) {
            mPartitionLabelView.clear();
            mStartStoreLabelView.clear();
            // 分区
            if (!model.shSysStoreCircleList.isEmpty()) {
                for (int i = 0; i < model.shSysStoreCircleList.size(); i++) {
                    if (model.shSysStoreCircleList != null) {
                        mPartitionLabelView.addLabel(model.shSysStoreCircleList.get(i));
                    }
                }
            } else {
                circleLayout.setVisibility(GONE);
            }
            // 推荐店铺
            if (!model.shStoresList.isEmpty()) {
                for (int i = 0; i < model.shStoresList.size(); i++) {
                    if (model.shStoresList.get(i) != null) {
                        mStartStoreLabelView.addLabel(model.shStoresList.get(i));
                    }
                }
            } else {
                storeLayout.setVisibility(GONE);
            }

            // 设置banner
            mBannerView.setData(model.shAdvertisingList);
        }
    }

}
