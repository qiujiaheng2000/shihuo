package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.NotifyModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by cm_qiujiaheng on 2017/1/15.
 * 消息中心列表
 */

public class MessageCenterActivity extends AbstractBaseListActivity {

    private List<NotifyModel> notifyModels = new ArrayList<>();

    public static void startMessageCenterActivity(Context context) {
        Intent intent = new Intent(context, MessageCenterActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initViews() {
        super.initViews();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void setTitle() {
        title.setText("消息中心");
    }

    @Override
    protected BaseAdapter getCustomAdapter() {
        return null;
    }

    @Override
    protected void refreshData() {
        mPageNum = 0;
        notifyModels.clear();
        getMessages();
    }

    @Override
    protected void loadMoreData() {
        getMessages();
    }

    private void getMessages() {
        //当前用户消息中心列表
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_NOTIFICATIONS))
                .addParams("token", AppShareUitl.getToken(MessageCenterActivity.this))
                .addParams("pageNum", String.valueOf(mPageNum))
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        refreshFrame.refreshComplete();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                mPageNum++;
                                JSONObject jsonObject = new JSONObject(response.data);
                                JSONArray jsonArray = jsonObject.getJSONArray("dataList");
                                notifyModels.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    NotifyModel notifyModel = NotifyModel.parseFromJsonStr(jsonArray.getJSONObject(i).toString());
                                    notifyModels.add(notifyModel);
                                }
                                mAdapter.notifyDataSetChanged();
                                loadMoreListViewContainer.setAutoLoadMore(true);
                                loadMoreListViewContainer.loadMoreFinish(notifyModels.isEmpty(), true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MessageCenterActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        refreshFrame.refreshComplete();
                        AppUtils.showToast(MessageCenterActivity.this, "获取消息列表出错");
                    }
                });
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
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MessageCenterActivity.this).inflate(R.layout.item_my_messagecenter, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            //TODO  设置item元素

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
