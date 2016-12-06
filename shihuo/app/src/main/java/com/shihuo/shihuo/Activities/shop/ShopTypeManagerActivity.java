package com.shihuo.shihuo.Activities.shop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.Activities.AbstractBaseListActivity;
import com.shihuo.shihuo.Activities.shop.models.ShopTypeModel;
import com.shihuo.shihuo.Activities.shop.views.ShopTypeChangeDialog;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.util.Toaster;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cm_qiujiaheng on 2016/12/4.
 * 店铺分类管理界面
 */
public class ShopTypeManagerActivity extends AbstractBaseListActivity {

    private ArrayList<ShopTypeModel> shopTypeModels = new ArrayList<>();


    public static void start(Context context) {
        Intent intent = new Intent(context, ShopTypeManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initViews() {
        super.initViews();
        txBtnRight.setVisibility(View.VISIBLE);
        txBtnRight.setText("+");
        txBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopTypeChangeDialog shopTypeChangeDialog = new ShopTypeChangeDialog(ShopTypeManagerActivity.this, R.style.CustomDialog)
                        .setTitle("添加分类")
                        .setHintText("请输入商品分类名称");
                shopTypeChangeDialog.show();
            }
        });
    }

    @Override
    public void setTitle() {
        title.setText(R.string.shop_type_manager);
    }

    @Override
    protected BaseAdapter getCustomAdapter() {
        return new ShopTypeManagerAdapter();
    }

    @Override
    protected void refreshData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shopTypeModels.clear();
                shopTypeModels.add(new ShopTypeModel("" + 1, "" + 1, "店铺商品类型 " + 1));
                shopTypeModels.add(new ShopTypeModel("" + 2, "" + 2, "店铺商品类型 " + 2));
                shopTypeModels.add(new ShopTypeModel("" + 3, "" + 3, "店铺商品类型 " + 3));
                refreshFrame.refreshComplete();
                mAdapter.notifyDataSetChanged();
                loadMoreListViewContainer.setAutoLoadMore(false);
                loadMoreListViewContainer.loadMoreFinish(shopTypeModels.isEmpty(), true);
            }
        }, 1000);
    }

    @Override
    protected void loadMoreData() {

    }

    public class ShopTypeManagerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return shopTypeModels.size();
        }

        @Override
        public Object getItem(int position) {
            return shopTypeModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return Long.parseLong(shopTypeModels.get(position).typeId);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ShopTypeManagerActivity.this).inflate(R.layout.item_shoptype_manager, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            ShopTypeModel shopTypeModel = (ShopTypeModel) getItem(position);

            viewHolder.textShoptypeName.setText(shopTypeModel.typeName);
            viewHolder.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toaster.toastShort("删除");
                }
            });
            viewHolder.imageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopTypeChangeDialog shopTypeChangeDialog = new ShopTypeChangeDialog(ShopTypeManagerActivity.this, R.style.CustomDialog)
                            .setTitle("修改分类")
                            .setHintText("请输入商品分类名称");
                    shopTypeChangeDialog.show();
                }
            });
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.text_shoptype_name)
            TextView textShoptypeName;
            @BindView(R.id.image_delete)
            ImageView imageDelete;
            @BindView(R.id.image_edit)
            ImageView imageEdit;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}
