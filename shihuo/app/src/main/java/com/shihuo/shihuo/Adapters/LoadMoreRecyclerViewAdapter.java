
package com.shihuo.shihuo.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.shihuo.shihuo.Views.pullrefresh.XListViewFooter;

/**
 * Created by Hujh on 15/6/8.
 */
public abstract class LoadMoreRecyclerViewAdapter extends RecyclerView.Adapter {
    public final static int LOAD_MORE_ITEM_VIEW_TYPE = 100;
    boolean loadMoreEnable = true;
    int loadMoreState = 0;
    protected Context context;

    public LoadMoreRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (LOAD_MORE_ITEM_VIEW_TYPE == viewType) {
            return new LoadMoreViewHolder(getFooterView());
        }
        return null;
    }

    XListViewFooter getFooterView() {
        XListViewFooter footer = new XListViewFooter(context);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        footer.setLayoutParams(lp);
        return footer;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadMoreViewHolder) {
            ((LoadMoreViewHolder)holder).footer.setState(loadMoreState);
            return;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadMoreItemView(position))
            return LOAD_MORE_ITEM_VIEW_TYPE;
        return super.getItemViewType(position);
    }

    public boolean isLoadMoreItemView(int position) {
        return position == getItemCount() - 1 && loadMoreEnable;
    }

    @Override
    public int getItemCount() {
        int count = getCount();
        if (count != 0 && loadMoreEnable)
            return count + 1;
        return count;
    }

    public void setLoadMoreEnable(boolean enable) {
        this.loadMoreEnable = enable;
    }

    public void setLoadMoreState(int state) {
        this.loadMoreState = state;
    }

    abstract int getCount();

    static class LoadMoreViewHolder extends RecyclerView.ViewHolder {
        public XListViewFooter footer;

        public LoadMoreViewHolder(XListViewFooter itemView) {
            super(itemView);
            this.footer = itemView;
        }
    }

}
