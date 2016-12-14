
package com.shihuo.shihuo.fragments;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.GsonRequest;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.FavGoodsListActivity;
import com.shihuo.shihuo.Activities.GoodsDetailActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.GoodsFavItemView;
import com.shihuo.shihuo.Views.HomeHeaderView;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreGridViewContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.GoodsDetailListModel;
import com.shihuo.shihuo.models.GoodsDetailModel;
import com.shihuo.shihuo.models.GoodsModel;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.models.HomeHorScrollConfigModel;
import com.shihuo.shihuo.models.SysTypeModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import in.srain.cube.views.GridViewWithHeaderAndFooter;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品列表frag Created by lishuai on 16/12/13.
 */

public class CircleListFragment extends BaseListFragment {

    public static final String TAG = "CircleListFragment";

    public static CircleListFragment newInstance(GoodsTypeModel model, int position) {
        CircleListFragment f = new CircleListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", model);
        bundle.putInt("position", position);
        f.setArguments(bundle);
        return f;
    }

    private int pageNum;

    private List<GoodsDetailModel> mGoodsFavList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        HomeHeaderView homeHeaderView = new HomeHeaderView(getContext());
////        homeHeaderView.setHorScrollViewDatas(HomeHorScrollConfigModel.getTestDatas());
//        listView.addHeaderView(homeHeaderView);
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }

    private void request(final boolean isRefresh) {
        if (isRefresh) {
            pageNum = 0;
        }
        String url = NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_GOODS_FAV_LIST) + "?token="
                + AppShareUitl.getToken(getContext()) + "&pageNum=" + pageNum;
        try {
            OkHttpUtils.get().url(url).build().execute(new ShihuoStringCallback() {
                @Override
                public void onResponse(ShiHuoResponse response, int id) {
                    if (response.code == ShiHuoResponse.SUCCESS
                            && !TextUtils.isEmpty(response.resultList)) {
                        mGoodsFavList = GoodsDetailListModel.parseStrJson(response.resultList);
                        if (isRefresh) {
                            refreshFrame.refreshComplete();
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BaseAdapter getCustomAdapter() {
        return new FavGoodsAdapter();
    }

    @Override
    protected void refreshData() {
        request(true);
    }

    @Override
    protected void loadMoreData() {
        request(false);
    }

    public class FavGoodsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mGoodsFavList.size();
        }

        @Override
        public Object getItem(int position) {
            return mGoodsFavList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.view_goods_fav_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder)convertView.getTag();
            viewHolder.view_goods_fav.setData(mGoodsFavList.get(position));
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.view_goods_fav)
            GoodsFavItemView view_goods_fav;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
