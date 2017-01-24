
package com.shihuo.shihuo.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.CircleListActivity;
import com.shihuo.shihuo.Activities.GoodsListByTypeActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.util.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 水平滑动view Created by lishuai on 17/1/24.
 */

public class HorizontalTypeView extends LinearLayout {
    @BindView(R.id.listview)
    RecyclerView listview;

    private Context context;

    private boolean isGoods;

    private List<GoodsTypeModel> list;

    private GalleryAdapter mAdapter;

    public HorizontalTypeView(Context context) {
        super(context);
        this.context = context;
        initViews();
    }

    public HorizontalTypeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    public HorizontalTypeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }

    private void initViews() {
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
     * @param isGoods 判断是商圈类型还是商品类型
     */
    public void setData(List<GoodsTypeModel> list, boolean isGoods) {
        if (list.isEmpty())
            return;
        this.isGoods = isGoods;
        this.list = list;
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

            LinearLayout layout;

            SimpleDraweeView image;

            TextView name;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = mInflater
                    .inflate(R.layout.layout_home_horscrollview_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);

            viewHolder.layout = (LinearLayout)view.findViewById(R.id.layout);
            viewHolder.image = (SimpleDraweeView)view.findViewById(R.id.image);
            viewHolder.name = (TextView)view.findViewById(R.id.name);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            final GoodsTypeModel model = list.get(i);
            if (model != null) {
                if (isGoods) {
                    viewHolder.image.setImageURI(AppUtils.parse(Contants.IMAGE_URL + model.logo));
                    viewHolder.name.setText(AppUtils.isEmpty(model.typeName));
                    final int tempIndex = i;
                    viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GoodsListByTypeActivity.start(getContext(), tempIndex);

                        }
                    });
                } else {
                    viewHolder.image.setImageURI(AppUtils.parse(Contants.IMAGE_URL
                            + model.logoPicUrl));
                    viewHolder.name.setText(AppUtils.isEmpty(model.circleName));
                    final int tempIndex = i;
                    viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CircleListActivity.start(getContext(), tempIndex);
                        }
                    });
                }
            }
        }

    }
}
