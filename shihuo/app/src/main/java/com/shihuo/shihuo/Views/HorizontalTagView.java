
package com.shihuo.shihuo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.CircleListActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.util.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页水平滑动type
 */
public class HorizontalTagView extends LinearLayout {

    @BindView(R.id.layout_container)
    LinearLayout mLayoutContainer;

    public HorizontalTagView(Context context) {
        super(context);
        initViews();
    }

    public HorizontalTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public HorizontalTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_horizontal_tag, null);
        ButterKnife.bind(this, view);
        addView(view);
    }

    /**
     * 绑定数据
     *
     * @param list
     * @param isGoods 判断是商圈类型还是商品类型
     */
    public void setData(List<GoodsTypeModel> list, boolean isGoods) {
        if (list.isEmpty())
            return;
        mLayoutContainer.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            final GoodsTypeModel model = list.get(i);
            View viewItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.layout_home_horscrollview_item, null);
            SimpleDraweeView imageView = (SimpleDraweeView)viewItem.findViewById(R.id.image);
            LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(AppUtils.dip2px(getContext(), 10), 0, 0, 0);
            viewItem.setLayoutParams(params);
            TextView mName = (TextView)viewItem.findViewById(R.id.name);
            if (model != null) {
                if (isGoods) {
                    imageView.setImageURI(AppUtils.parse(model.logo));
                    mName.setText(AppUtils.isEmpty(model.typeName));
                    imageView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppUtils.showToast(getContext(), model.typeName);
                        }
                    });
                } else {
                    imageView.setImageURI(AppUtils.parse(model.logoPicUrl));
                    mName.setText(AppUtils.isEmpty(model.circleName));
                    final int tempIndex = i;
                    imageView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            CircleListActivity.start(getContext(), tempIndex, model);
                        }
                    });
                }
            }

            mLayoutContainer.addView(viewItem);
        }
    }

}
