
package com.shihuo.shihuo.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.dpizarro.autolabel.library.Label;
import com.shihuo.shihuo.Activities.GoodsDetailActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.GoodsView;
import com.shihuo.shihuo.Views.StoreItemView;
import com.shihuo.shihuo.models.SearchModel;
import com.shihuo.shihuo.util.AppUtils;

import java.util.List;

public class SearchAdapter extends LoadMoreRecyclerViewAdapter {

    private List<SearchModel> data;

    private Context context;


    public interface OnSearchItermClickListener {
        /**
         * 关键字搜索
         *
         * @param keyWords
         */
        void onSearCh(String keyWords);

        /**
         * 清除历史搜索记录
         */
        void onClear();

        /**
         * 搜索结果的 "更多"点击
         *
         * @param moreStr
         */
        void onMoreClick(String moreStr);
    }

    private OnSearchItermClickListener onSearchItermClickListener;

    public SearchAdapter(Context context, List<SearchModel> list, OnSearchItermClickListener onSearchItermClickListener) {
        super(context);
        this.context = context;
        this.data = list;
        this.onSearchItermClickListener = onSearchItermClickListener;
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
                TitleViewHolder holder = new TitleViewHolder(view, new MyItemOnClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        SearchModel searchModel = data.get(position);
                        if (onSearchItermClickListener != null) {
                            onSearchItermClickListener.onSearCh(searchModel.item_type_title);
                        }
                    }

                    @Override
                    public void onLabelClick(String keyWord) {

                    }

                    @Override
                    public void onMoreClick(String moreString) {

                    }
                });
                view.setTag(holder);
                return holder;
            }
            case 1: { //清除搜索历史
                view = LayoutInflater.from(context).inflate(R.layout.item_search_clear, null);
                ClearViewHolder holder = new ClearViewHolder(view, new MyItemOnClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (onSearchItermClickListener != null) {
                            onSearchItermClickListener.onClear();
                        }
                    }

                    @Override
                    public void onLabelClick(String keyWord) {

                    }

                    @Override
                    public void onMoreClick(String moreString) {

                    }
                });
                view.setTag(holder);
                return holder;
            }
            case 2: { //热门搜索
                view = LayoutInflater.from(context).inflate(R.layout.item_search_hot, null);
                HotViewHolder holder = new HotViewHolder(view, new MyItemOnClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void onLabelClick(String keyWord) {
                        if (onSearchItermClickListener != null) {
                            onSearchItermClickListener.onSearCh(keyWord);
                        }
                    }

                    @Override
                    public void onMoreClick(String moreString) {

                    }
                });
                view.setTag(holder);
                return holder;
            }
            case 3: { //搜索结果title
                view = LayoutInflater.from(context).inflate(R.layout.item_search_title, null);
                TitleViewHolder holder = new TitleViewHolder(view, new MyItemOnClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void onLabelClick(String keyWord) {

                    }

                    @Override
                    public void onMoreClick(String moreString) {
                        if (onSearchItermClickListener != null) {
                            onSearchItermClickListener.onMoreClick(moreString);
                        }
                    }
                });
                view.setTag(holder);
                return holder;
            }
            case 4: { //店铺
                view = LayoutInflater.from(context).inflate(R.layout.item_store, null);
                StoreViewHolder holder = new StoreViewHolder(view, null);
                view.setTag(holder);
                return holder;
            }
            case 5: { //商品
                view = LayoutInflater.from(context).inflate(R.layout.item_view_goods, null);
                GoodsItemViewHolder holder = new GoodsItemViewHolder(view, null);
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
                TitleViewHolder viewHolder = (TitleViewHolder) holder;
                if (data.get(position) != null) {
                    viewHolder.tv_title.setText(AppUtils.isEmpty(data.get(position).item_type_title));
                    viewHolder.tv_title.setTextColor(context.getResources().getColor(
                            R.color.common_font_black));
                    viewHolder.tv_more.setVisibility(View.GONE);
                }
                break;
            }
            case 1: { //清除搜索历史
                ClearViewHolder viewHolder = (ClearViewHolder) holder;
                if (data.get(position) != null) {
                    viewHolder.tv_title.setText(AppUtils.isEmpty(data.get(position).item_type_title));
                }
                break;
            }
            case 2: { //热门搜索
                HotViewHolder viewHolder = (HotViewHolder) holder;
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
                TitleViewHolder viewHolder = (TitleViewHolder) holder;
                if (data.get(position) != null) {
                    viewHolder.tv_title.setText(AppUtils.isEmpty(data.get(position).item_type_title));
                }
                break;
            }
            case 4: { //店铺
                StoreViewHolder viewHolder = (StoreViewHolder) holder;
                if (data.get(position) != null && data.get(position).shStores != null) {
                    viewHolder.view_store.bindData(data.get(position).shStores);
                }
                break;
            }

            case 5: { //商品
                GoodsItemViewHolder viewHolder = (GoodsItemViewHolder) holder;
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

    public interface MyItemOnClickListener {
        void onItemClick(View view, int position);

        void onLabelClick(String keyWord);

        void onMoreClick(String moreString);
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MyItemOnClickListener myItemOnClickListener;
        View rootView;

        public BaseViewHolder(View itemView, MyItemOnClickListener myItemOnClickListener) {
            super(itemView);
            rootView = itemView;
            this.myItemOnClickListener = myItemOnClickListener;
            rootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null) {
                myItemOnClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }


    static class TitleViewHolder extends BaseViewHolder {
        TextView tv_title;
        TextView tv_more;

        public TitleViewHolder(View itemView, final MyItemOnClickListener myItemOnClickListener) {
            super(itemView, myItemOnClickListener);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_more = (TextView) itemView.findViewById(R.id.tv_more);
            tv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myItemOnClickListener != null) {
                        myItemOnClickListener.onMoreClick(tv_title.getText().toString());
                    }
                }
            });

        }

    }

    static class ClearViewHolder extends BaseViewHolder {
        TextView tv_title;

        public ClearViewHolder(View itemView, MyItemOnClickListener myItemOnClickListener) {
            super(itemView, myItemOnClickListener);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }

    }

    static class HotViewHolder extends BaseViewHolder {
        AutoLabelUI view_label;

        public HotViewHolder(final View itemView, final MyItemOnClickListener myItemOnClickListener) {
            super(itemView, myItemOnClickListener);
            view_label = (AutoLabelUI) itemView.findViewById(R.id.view_label);
            view_label.setOnLabelClickListener(new AutoLabelUI.OnLabelClickListener() {
                @Override
                public void onClickLabel(Label labelClicked) {
                    if (myItemOnClickListener != null) {
                        myItemOnClickListener.onLabelClick(labelClicked.getText());
                    }
                }
            });
        }
    }

    static class StoreViewHolder extends BaseViewHolder {
        StoreItemView view_store;

        public StoreViewHolder(View itemView, MyItemOnClickListener myItemOnClickListener) {
            super(itemView, myItemOnClickListener);
            view_store = (StoreItemView) itemView.findViewById(R.id.view_store);
        }
    }

    static class GoodsItemViewHolder extends BaseViewHolder {
        GoodsView mGoodsLeftView;

        GoodsView mGoodsRightView;

        public GoodsItemViewHolder(View itemView, MyItemOnClickListener myItemOnClickListener) {
            super(itemView, myItemOnClickListener);
            mGoodsLeftView = (GoodsView) itemView.findViewById(R.id.view_goods_left);
            mGoodsRightView = (GoodsView) itemView.findViewById(R.id.view_goods_right);
        }
    }
}
