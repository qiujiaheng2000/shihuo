
package com.shihuo.shihuo.Adapters;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.GoodsDetailActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.GoodsView;
import com.shihuo.shihuo.Views.autolabel.CustomAutoLabelUi;
import com.shihuo.shihuo.models.SearchModel;
import com.shihuo.shihuo.models.StoreDetailModel;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SearchAdapter extends LoadMoreRecyclerViewAdapter{

    private List<SearchModel> data;

    private Context context;

    public SearchAdapter(Context context, List<SearchModel> list) {
        super(context);
        this.context = context;
        this.data = list;
    }

    public List<SearchModel> getData() {
        return data;
    }

    public void setData(List<SearchModel> data) {
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
        SearchModel model = getItem(position);
        if (model == null) {
            return super.getItemViewType(position);
        }
        return model.item_type;
    }

    public SearchModel getItem(int position) {
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
            case 0: { //搜索历史item
                view = LayoutInflater.from(context).inflate(R.layout.item_search_title, null);
                TitleViewHolder holder = new TitleViewHolder(view);
                view.setTag(holder);
                return holder;
            }
            case 1: { //清除搜索历史
                view = LayoutInflater.from(context).inflate(R.layout.item_search_clear, null);
                ClearViewHolder holder = new ClearViewHolder(view);
                view.setTag(holder);
                return holder;
            }
            case 2: { //热门搜索
                view = LayoutInflater.from(context).inflate(R.layout.item_search_hot, null);
                HotViewHolder holder = new HotViewHolder(view);
                view.setTag(holder);
                return holder;
            }
            case 3: { //搜索结果title
                view = LayoutInflater.from(context).inflate(R.layout.item_search_title, null);
                TitleViewHolder holder = new TitleViewHolder(view);
                view.setTag(holder);
                return holder;
            }
            case 4: { //店铺
                view = LayoutInflater.from(context).inflate(R.layout.item_fav_shops, null);
                StoreViewHolder holder = new StoreViewHolder(view);
                view.setTag(holder);
                return holder;
            }
            case 5: { //商品
                view = LayoutInflater.from(context).inflate(R.layout.item_view_goods, null);
                GoodsItemViewHolder holder = new GoodsItemViewHolder(view);
                view.setTag(holder);
                return holder;
            }
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final SearchModel model = getItem(position);
        if (model == null) {
            super.onBindViewHolder(holder, position);
            return;
        }
        switch (getItemViewType(position)) {
            case 0: { //搜索历史item
                TitleViewHolder viewHolder = (TitleViewHolder)holder;
                if (data.get(position) != null) {
                    viewHolder.tv_title.setText(AppUtils.isEmpty(data.get(position).item_type_title));
                    viewHolder.tv_title.setTextColor(context.getResources().getColor(
                            R.color.common_font_black));
                    viewHolder.tv_more.setVisibility(View.GONE);
                }
                break;
            }
            case 1: { //清除搜索历史
                ClearViewHolder viewHolder = (ClearViewHolder)holder;
                if (data.get(position) != null) {
                    viewHolder.tv_title.setText(AppUtils.isEmpty(data.get(position).item_type_title));
                }
                break;
            }
            case 2: { //热门搜索
                HotViewHolder viewHolder = (HotViewHolder)holder;
                viewHolder.view_label.clear();
                if (data.get(position) != null && !data.get(position).dataList.isEmpty()) {
                    for (int i = 0; i < data.get(position).dataList.size(); i++) {
                        if (data.get(position).dataList.get(i) != null) {
                            viewHolder.view_label.addLabel(data.get(position).dataList.get(i));
                        }
                    }
                }
                break;
            }
            case 3: { //搜索结果title
                TitleViewHolder viewHolder = (TitleViewHolder)holder;
                if (data.get(position) != null) {
                    viewHolder.tv_title.setText(AppUtils.isEmpty(data.get(position).item_type_title));
                }
                break;
            }
            case 4: { //店铺
                StoreViewHolder viewHolder = (StoreViewHolder)holder;
                if (data.get(position) != null && data.get(position).shStores != null) {
                    StoreDetailModel storeDetail = data.get(position).shStores;
                    viewHolder.item_title.setText(storeDetail.storeName);
                    viewHolder.item_desc.setText(storeDetail.storeDetail);
                    viewHolder.prefixNumbs.setText("销量：");
                    viewHolder.numbs.setText("" + storeDetail.orderNum);
                    viewHolder.shop_add.setText(storeDetail.circleName);
                    viewHolder.imageView.setImageURI(AppUtils.parse(AliyunHelper.getFullPathByName(storeDetail.storeLogoPicUrl)));
                }
                break;
            }

            case 5: { //商品
                GoodsItemViewHolder viewHolder = (GoodsItemViewHolder)holder;
                if (data.get(position) != null && data.get(position).shGoods != null) {
                    if (data.get(position).shGoods.goodsLeftModel != null) {
                        viewHolder.mGoodsLeftView.setVisibility(View.VISIBLE);
                        viewHolder.mGoodsLeftView
                                .bindData(data.get(position).shGoods.goodsLeftModel);
                    } else {
                        viewHolder.mGoodsLeftView.setVisibility(View.INVISIBLE);
                    }
                    if (data.get(position).shGoods.goodsRightModel != null) {
                        viewHolder.mGoodsRightView.setVisibility(View.VISIBLE);
                        viewHolder.mGoodsRightView
                                .bindData(data.get(position).shGoods.goodsRightModel);
                    } else {
                        viewHolder.mGoodsRightView.setVisibility(View.INVISIBLE);
                    }
                }
                viewHolder.mGoodsLeftView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoodsDetailActivity.start(context,
                                data.get(position).shGoods.goodsLeftModel.goodsId);
                    }
                });
                viewHolder.mGoodsRightView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoodsDetailActivity.start(context,
                                data.get(position).shGoods.goodsRightModel.goodsId);
                    }
                });
                break;
            }
        }
    }

    public void bindData(List<SearchModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class TitleViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_more;

        TitleViewHolder(View view) {
            super(view);
            tv_title = (TextView)view.findViewById(R.id.tv_title);
            tv_more = (TextView)view.findViewById(R.id.tv_more);
        }
    }

    static class ClearViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;

        ClearViewHolder(View view) {
            super(view);
            tv_title = (TextView)view.findViewById(R.id.tv_title);
        }
    }

    static class HotViewHolder extends RecyclerView.ViewHolder {
        CustomAutoLabelUi view_label;

        HotViewHolder(View view) {
            super(view);
            view_label = (CustomAutoLabelUi)view.findViewById(R.id.view_label);
        }
    }

    static class StoreViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView imageView;
        TextView item_title;
        TextView item_desc;
        TextView prefixNumbs;
        TextView numbs;
        TextView shop_add;

        StoreViewHolder(View view) {
            super(view);
            imageView = (SimpleDraweeView)view.findViewById(R.id.imageView);
            item_title = (TextView)view.findViewById(R.id.item_title);
            item_desc = (TextView)view.findViewById(R.id.item_desc);
            prefixNumbs = (TextView)view.findViewById(R.id.prefix_numbs);
            numbs = (TextView)view.findViewById(R.id.numbs);
            shop_add = (TextView)view.findViewById(R.id.shop_add);
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
