
package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.NotifyListModel;
import com.shihuo.shihuo.models.NotifyModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppSchemeHelper;
import com.shihuo.shihuo.util.AppUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by cm_qiujiaheng on 2016/11/3. 消息中心
 */

public class NotifyListActivity extends AbstractBaseListActivity {

    private List<NotifyModel> notifyModels = new ArrayList<>();

    public static void start(Context context) {
        Intent intent = new Intent(context, NotifyListActivity.class);
        context.startActivity(intent);
    }

    private int pageNum = 1;

    @Override
    public void setTitle() {
        title.setText("消息中心");
    }

    @Override
    protected BaseAdapter getCustomAdapter() {
        return new MyAddressAdapter();
    }

    @Override
    protected void refreshData() {
        request(true);
    }

    private void request(final boolean isRefresh) {
        if (isRefresh) {
            pageNum = 1;
        }
        try {
            OkHttpUtils
                    .get()
                    .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_NOTIFICATIONS))
                    .addParams("token", AppShareUitl.getToken(NotifyListActivity.this))
                    .addParams("pageNum", String.valueOf(mPageNum))
                    .build()
                    .execute(new ShihuoStringCallback() {
                @Override
                public void onResponse(ShiHuoResponse response, int id) {
                    refreshFrame.refreshComplete();
                    if (response.code == ShiHuoResponse.SUCCESS
                            && !TextUtils.isEmpty(response.resultList)) {
                        notifyModels = NotifyListModel.parseStrJson(response.resultList);
                        refreshFrame.refreshComplete();
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    hideProgressDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void loadMoreData() {
        request(false);
    }

    class MyAddressAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return notifyModels.size();
        }

        @Override
        public Object getItem(int position) {
            return notifyModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            MyAddressAdapter.ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(NotifyListActivity.this).inflate(R.layout.item_my_messagecenter, null);
                viewHolder = new MyAddressAdapter.ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (MyAddressAdapter.ViewHolder) convertView.getTag();
            //TODO  设置item元素
            if (!notifyModels.isEmpty() && notifyModels.get(position) != null) {
                if (notifyModels.get(position).type < 5) {
                    viewHolder.itemMsgCenterTitle.setText("订单消息");
                    viewHolder.detailLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AppSchemeHelper.dealScheme(NotifyListActivity.this,
                                    notifyModels.get(position).schemeUrl);
                        }
                    });
                } else if (notifyModels.get(position).type == 5) {
                    viewHolder.itemMsgCenterTitle.setText("你商铺认证已成功");
                } else if (notifyModels.get(position).type == 6) {
                    viewHolder.itemMsgCenterTitle.setText("你商铺认证失败,客服电话 0359-6382822");
                    viewHolder.detailLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AppUtils.callPhone(NotifyListActivity.this, "0359-6382822");
                        }
                    });
                }
                viewHolder.itemCreatetime.setText(notifyModels.get(position).createTime);
            }

            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.item_msg_center_title)
            TextView itemMsgCenterTitle;
            @BindView(R.id.item_content)
            TextView itemContent;
            @BindView(R.id.item_createtime)
            TextView itemCreatetime;
            @BindView(R.id.detail_layout)
            LinearLayout detailLayout;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
