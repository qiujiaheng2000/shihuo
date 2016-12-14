package com.shihuo.shihuo.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.GoodsModel;
import com.shihuo.shihuo.models.HomeModel;
import com.shihuo.shihuo.util.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cm_qiujiaheng on 2016/12/15.
 * 分类商品列表界面
 */

public class GoodsGrideListAdapter extends BaseAdapter {

    private List<HomeModel> mData;

    private Context mContext;


    public GoodsGrideListAdapter(Context mContext,List<HomeModel> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_goods, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        GoodsModel goodsModel = (GoodsModel) getItem(position);
        viewHolder.imageView.setImageURI(AppUtils.parse(Contants.IMAGE_URL + goodsModel.picUrl));
        viewHolder.goodsTitle.setText(AppUtils.isEmpty(goodsModel.goodsName));

        if (goodsModel.curPrice == goodsModel.curPrice) {
            viewHolder.goodsNewPrice.setText(String.format(
                    mContext.getResources().getString(R.string.price), goodsModel.curPrice + ""));
            viewHolder.goodsOriginPrice.setVisibility(View.GONE);
        } else {
            viewHolder.goodsNewPrice.setText(String.format(mContext.getResources().getString(R.string.price),
                    goodsModel.curPrice + ""));
            if (goodsModel.prePrice == 0 && TextUtils.isEmpty(goodsModel.prePrice + "")) {
                viewHolder.goodsOriginPrice.setVisibility(View.GONE);
            } else {
                viewHolder.goodsOriginPrice.setVisibility(View.VISIBLE);
                viewHolder.goodsOriginPrice.setText(String.format(
                        mContext.getResources().getString(R.string.price), goodsModel.prePrice + ""));
                viewHolder.goodsOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }

        viewHolder.sales.setText(AppUtils.isEmpty(String.format(
                mContext.getResources().getString(R.string.sales), goodsModel.salesNum + "")));

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.imageView)
        SimpleDraweeView imageView;
        @BindView(R.id.goods_title)
        TextView goodsTitle;
        @BindView(R.id.goods_new_price)
        TextView goodsNewPrice;
        @BindView(R.id.goods_origin_price)
        TextView goodsOriginPrice;
        @BindView(R.id.sales)
        TextView sales;
        @BindView(R.id.detail_layout)
        LinearLayout detailLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
