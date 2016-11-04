package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.MyOrderHeaderView;
import com.shihuo.shihuo.models.OrderModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cm_qiujiaheng on 2016/11/3.
 * 我的订单列表
 */

public class MyOrdersListActivity extends AbstractBaseListActivity implements MyOrderHeaderView.ButtonOnClickListener {

    private ArrayList<OrderModel> orderModelArrayList = new ArrayList<>();


    public static void startMyOrdersListActivity(Context context) {
        Intent intent = new Intent(context, MyOrdersListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected View getHeaderView() {
        MyOrderHeaderView myOrderHeaderView = new MyOrderHeaderView(this);
        myOrderHeaderView.setButtonOnClickListener(this);
        myOrderHeaderView.setHeaders(new MyOrderHeaderView.OrderHeaderModel());
        return myOrderHeaderView;
    }

    @Override
    public void setTitle() {
        title.setText(R.string.myorder_list);
    }

    @Override
    protected BaseAdapter getCustomAdapter() {
        return new MyOrderAdapter();
    }

    @Override
    protected void refreshData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                orderModelArrayList.clear();
                orderModelArrayList.addAll(OrderModel.getTestDatas(15));
                refreshFrame.refreshComplete();
                mAdapter.notifyDataSetChanged();
                loadMoreListViewContainer.setAutoLoadMore(true);
                loadMoreListViewContainer.loadMoreFinish(orderModelArrayList.isEmpty(), true);
            }
        }, 2000);
    }

    @Override
    protected void loadMoreData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // load more complete
                orderModelArrayList.addAll(OrderModel.getTestDatas(15));
                refreshFrame.refreshComplete();
                loadMoreListViewContainer.loadMoreFinish(orderModelArrayList.isEmpty(), true);
                mAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }

    @Override
    public void onClick(int type) {
        //TODO 查看不同类型的订单按钮的点击
        Toast.makeText(this, "type = " + type, Toast.LENGTH_SHORT).show();
    }

    class MyOrderAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return orderModelArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return orderModelArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MyOrdersListActivity.this).inflate(R.layout.my_orders_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            OrderModel orderModel = (OrderModel) getItem(position);
            viewHolder.itemTitle.setText(orderModel.orderTitle);
            viewHolder.itemDesc.setText(orderModel.orderDesc);
            viewHolder.orderPrice.setText(orderModel.orderPrice);
            viewHolder.numbs.setText(orderModel.orderNums);

            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.imageView)
            ImageView imageView;
            @BindView(R.id.item_title)
            TextView itemTitle;
            @BindView(R.id.item_desc)
            TextView itemDesc;
            @BindView(R.id.order_price)
            TextView orderPrice;
            @BindView(R.id.numbs)
            TextView numbs;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
