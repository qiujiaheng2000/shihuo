
package com.shihuo.shihuo.Views;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.GoodsListByTypeActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 折扣优惠区二view
 */
public class HorizontalDiscountView extends LinearLayout {

    @BindView(R.id.listview)
    RecyclerView listview;

    private List<GoodsTypeModel> mDatas;

    private Context context;

    private GalleryAdapter mAdapter;

    public HorizontalDiscountView(Context context) {
        super(context);
        this.context = context;
        initViews(context);
    }

    public HorizontalDiscountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews(context);
    }

    public HorizontalDiscountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews(context);
    }

    private void initViews(Context context) {
        this.context = context;
        mDatas = new ArrayList<>();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_horizontal_type, null);
        ButterKnife.bind(this, view);
        addView(view);

        // 设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        listview.setLayoutManager(linearLayoutManager);
        mAdapter = new GalleryAdapter(context);
        listview.setAdapter(mAdapter);
    }

    /**
     * 绑定数据
     *
     * @param list
     */
    public void setData(List<GoodsTypeModel> list) {
        if (list.isEmpty())
            return;
        mDatas.clear();
        mDatas.addAll(list);
        mAdapter.notifyDataSetChanged();
    }


    public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
        private LayoutInflater mInflater;

        public GalleryAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View arg0) {
                super(arg0);
            }
            SimpleDraweeView image;
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = mInflater
                    .inflate(R.layout.view_horscroll_discount_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);

            viewHolder.image = (SimpleDraweeView)view.findViewById(R.id.image);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            final GoodsTypeModel model = mDatas.get(i);
            if (model != null) {
                LinearLayout.LayoutParams params = new LayoutParams(AppUtils.dip2px(context, 120),
                        AppUtils.dip2px(context, 60));
                params.setMargins(AppUtils.dip2px(context, 5), 0, 0, 0);
                viewHolder.image.setLayoutParams(params);
                viewHolder.image.setImageURI(AppUtils.parse(Contants.IMAGE_URL + model.discountPicUrl));
                final int tempIndex = i;
                viewHolder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoodsListByTypeActivity.start(getContext(), tempIndex);

                    }
                });
            }
        }

    }

}
