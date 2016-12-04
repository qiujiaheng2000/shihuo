
package com.shihuo.shihuo.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.GoodsView;
import com.shihuo.shihuo.Views.HomeHeaderView;
import com.shihuo.shihuo.models.HomeModel;

import java.util.List;

/**
 * 首页adapter
 */
public class HomeAdapter extends LoadMoreRecyclerViewAdapter {

    private List<HomeModel> data;

    private Context context;

    public HomeAdapter(Context context, List<HomeModel> list) {
        super(context);
        this.context = context;
        this.data = list;
    }

    public List<HomeModel> getData() {
        return data;
    }

    public void setData(List<HomeModel> data) {
        this.data = data;
    }

    public boolean clearData() {
        if (data != null && !data.isEmpty()) {
            data.clear();
            return true;
        } else
            return false;
    }

    @Override
    public int getItemViewType(int position) {
        HomeModel model = getItem(position);
        if (model == null) {
            return super.getItemViewType(position);
        }
        return model.item_type;
    }

    public HomeModel getItem(int position) {
        if (position >= data.size())
            return null;
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.isEmpty() ? 0 : data.size();
    }

    public int getSize() {
        return data.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0: {
                HomeHeaderViewHolder holder;
                view = LayoutInflater.from(context).inflate(R.layout.item_home_header_view, null);
                view.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                holder = new HomeHeaderViewHolder(view);
                view.setTag(holder);
                return holder;
            }
            case 1: {
                GoodsItemViewHolder holder;
                view = LayoutInflater.from(context).inflate(R.layout.item_view_goods, null);
                holder = new GoodsItemViewHolder(view);
                view.setTag(holder);
                return holder;
            }
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final HomeModel model = getItem(position);
        if (model == null) {
            super.onBindViewHolder(holder, position);
            return;
        }
        switch (getItemViewType(position)) {
            case 0: {
                HomeHeaderViewHolder viewHolder = (HomeHeaderViewHolder)holder;
                if (data.get(position) != null) {
                    viewHolder.homeHeaderView.setData(data.get(position).typeData);
                }
                break;
            }
            case 1: {
                GoodsItemViewHolder viewHolder = (GoodsItemViewHolder)holder;
                if (data.get(position) != null) {
                    viewHolder.mGoodsLeftView
                            .bindData(data.get(position).baseGoodsModel.goodsLeftModel);
                    viewHolder.mGoodsRightView
                            .bindData(data.get(position).baseGoodsModel.goodsRightModel);
                }
                break;
            }
        }
    }

    public void bindData(List<HomeModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class HomeHeaderViewHolder extends RecyclerView.ViewHolder {
        HomeHeaderView homeHeaderView;

        HomeHeaderViewHolder(View view) {
            super(view);
            homeHeaderView = (HomeHeaderView)view.findViewById(R.id.view_home_header_view);
        }
    }

    static class GoodsItemViewHolder extends RecyclerView.ViewHolder {
        GoodsView mGoodsLeftView;

        GoodsView mGoodsRightView;

        GoodsItemViewHolder(View view) {
            super(view);
            mGoodsLeftView = (GoodsView)view.findViewById(R.id.view_goods_left);
            mGoodsRightView = (GoodsView)view.findViewById(R.id.view_goods_right);
        }
    }
}
