
package com.shihuo.shihuo.Views;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.autolabel.CustomAutoLabelUi;
import com.shihuo.shihuo.models.CircleListTopModel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;

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

    private ArrayList<View> viewList = new ArrayList<>();

    public CircleListHeaderView(Context context) {
        super(context);
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
    }

    /**
     * 绑定数据
     */
    public void bindData(CircleListTopModel model) {
        if (model != null) {
            // 分区
            if(!model.shSysStoreCircleList.isEmpty()){
                for (int i = 0; i < model.shSysStoreCircleList.size(); i++) {
                    if(model.shSysStoreCircleList != null){
                        mPartitionLabelView.addLabel(model.shSysStoreCircleList.get(i));
                    }
                }
            }
            // 推荐店铺
            if(!model.shStoresList.isEmpty()){
                for (int i = 0; i < model.shStoresList.size(); i++) {
                    if(model.shStoresList.get(i) != null){
                        mStartStoreLabelView.addLabel(model.shStoresList.get(i));
                    }
                }
            }

            // 设置banner
            mBannerView.setData(model.shAdvertisingList);
        }
    }

}
