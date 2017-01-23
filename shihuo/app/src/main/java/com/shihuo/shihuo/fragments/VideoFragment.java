
package com.shihuo.shihuo.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.VideoPlayActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.CustomAutolabelHeaderView;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.Views.loadmore.LoadMoreListViewContainer;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.models.StoreDetailModel;
import com.shihuo.shihuo.models.VideoModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Call;

/**
 * Created by jiahengqiu on 2016/10/23. 微视频
 */
public class VideoFragment extends BaseFragment implements
        CustomAutolabelHeaderView.LabelChangeListener {

    @BindView(R.id.leftbtn)
    Button leftbtn;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.rightbtn)
    Button rightbtn;

    @BindView(R.id.rotate_header_list_view)
    ListView rotateHeaderListView;

    @BindView(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout rotateHeaderListViewFrame;

    @BindView(R.id.load_more_list_view_container)
    LoadMoreListViewContainer loadMoreListViewContainer;

    private Handler mHandler = new Handler();

    private MyListViewAdapter mAdapter;

    public ArrayList<VideoModel> mVideoModels = new ArrayList<>();

    public static ArrayList<VideoModel> testVideoModels = new ArrayList<>();

    // 当前选中的类型id
    private int mTypeId = 0;

    private int mPageNum = 0;

    // banner图集合
    private ArrayList<GoodsTypeModel> banners = new ArrayList<>();

    // 视频类型
    private ArrayList<GoodsTypeModel> types = new ArrayList<>();

    private CustomAutolabelHeaderView customAutolabelHeaderView;

    // 是否只刷新list
    private boolean isOnlyRefreshList = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.video_activity, null);
        ButterKnife.bind(this, view);

        initViews();
        return view;
    }

    private void initViews() {

        title.setText(R.string.tab_video);
        rotateHeaderListViewFrame.setLoadingMinTime(1000);
        rotateHeaderListViewFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPageNum = 0;
                mVideoModels.clear();
                if (isOnlyRefreshList) {
                    isOnlyRefreshList = false;
                    getVideoList();
                } else {
                    getBannerAndType();
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, rotateHeaderListView,
                        header);
            }
        });

        mAdapter = new MyListViewAdapter();

        customAutolabelHeaderView = new CustomAutolabelHeaderView(getContext(), this);
        customAutolabelHeaderView.addAutoLabels(types, new ArrayList<StoreDetailModel>(), banners);
        rotateHeaderListView.addHeaderView(customAutolabelHeaderView);
        customAutolabelHeaderView.setTypeName("微视频分类");

        loadMoreListViewContainer.setAutoLoadMore(false);
        loadMoreListViewContainer.useDefaultFooter();
        rotateHeaderListView.setAdapter(mAdapter);

        rotateHeaderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mVideoModels.isEmpty() && mVideoModels.get(position - 1) != null) {
                    VideoPlayActivity.start(getContext(), mVideoModels.get(position - 1).videoUrl,
                            mVideoModels.get(position - 1).isFav,
                            mVideoModels.get(position - 1).mId);
                }
            }
        });

        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getVideoList();
            }
        });
        rotateHeaderListViewFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                rotateHeaderListViewFrame.autoRefresh();
            }
        }, 100);
    }

    public static VideoFragment newInstance() {
        VideoFragment frament = new VideoFragment();
        Bundle bundle = new Bundle();
        frament.setArguments(bundle);
        return frament;
    }

    private void getBannerAndType() {
        // 获取分类信息和banner
        OkHttpUtils.get().url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_VIDEO_BANNER)).build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {

                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                if (!TextUtils.isEmpty(response.data)) {
                                    JSONObject jsonObject = new JSONObject(response.data);
                                    // 解析分类
                                    if (!TextUtils.isEmpty(jsonObject
                                            .getString("shServerTypesList"))) {
                                        JSONArray jsonArray = jsonObject
                                                .getJSONArray("shServerTypesList");
                                        types.clear();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            GoodsTypeModel goodsTypeModel = GoodsTypeModel
                                                    .parseJsonStr(jsonArray.getJSONObject(i));
                                            types.add(goodsTypeModel);
                                            if (i == 0) {
                                                mTypeId = goodsTypeModel.typeId;
                                            }
                                        }
                                    }
                                    // 解析banner
                                    if (!TextUtils.isEmpty(jsonObject
                                            .getString("shAdvertisingList"))) {
                                        JSONArray jsonArray = jsonObject
                                                .getJSONArray("shAdvertisingList");
                                        banners.clear();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            GoodsTypeModel goodsTypeModel = GoodsTypeModel
                                                    .parseJsonStr(jsonArray.getJSONObject(i));
                                            banners.add(goodsTypeModel);
                                        }
                                    }
                                    customAutolabelHeaderView.addAutoLabels(types,
                                            new ArrayList<StoreDetailModel>(), banners);
                                    getVideoList();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getContext(), response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        rotateHeaderListViewFrame.refreshComplete();
                        AppUtils.showToast(getContext(), "获取视频分类出错");
                    }
                });
    }

    /**
     * 获取视频列表
     */
    private void getVideoList() {
        try {
            OkHttpUtils.get().url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_VIDEO_LIST)+"?token="+ AppShareUitl.getToken(getContext()))
                    .addParams("pageNum", String.valueOf(mPageNum))
                    .addParams("typeId", String.valueOf(mTypeId)).build()
                    .execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            rotateHeaderListViewFrame.refreshComplete();
                            try {
                                if (response.code == ShiHuoResponse.SUCCESS
                                        && !TextUtils.isEmpty(response.data)) {
                                    if (!TextUtils.isEmpty(response.resultList)) {
                                        mPageNum += 1;
                                        JSONArray jsonArray = new JSONArray(response.resultList);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            VideoModel videoModel = VideoModel
                                                    .parseFromJsonStr(jsonArray.getString(i));
                                            mVideoModels.add(videoModel);
                                        }
                                        loadMoreListViewContainer.loadMoreFinish(
                                                mVideoModels.isEmpty(), true);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    AppUtils.showToast(getActivity(), response.msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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

    @OnClick({
            R.id.leftbtn, R.id.rightbtn
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftbtn:

                break;
            case R.id.rightbtn:

                break;
        }
    }

    @Override
    public void onTypeLabelChanged(GoodsTypeModel goodsTypeModel) {
        if (!TextUtils.isEmpty(goodsTypeModel.typeName)) {
            mTypeId = goodsTypeModel.typeId;
        } else if (!TextUtils.isEmpty(goodsTypeModel.cTypeName)) {
            mTypeId = goodsTypeModel.cTypeId;
        } else {
            mTypeId = 0;
        }
        isOnlyRefreshList = true;
        rotateHeaderListViewFrame.autoRefresh();
    }

    @Override
    public void onStoreLabelChanged(StoreDetailModel storeDetailModel) {

    }

    public class MyListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mVideoModels.size();
        }

        @Override
        public Object getItem(int position) {
            return mVideoModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.videos_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder)convertView.getTag();
            VideoModel videoModel = (VideoModel)getItem(position);
            if (videoModel != null) {
                viewHolder.itemTitle.setText(TextUtils.isEmpty(videoModel.mName) ? ""
                        : videoModel.mName);
                viewHolder.itemDesc.setText(TextUtils.isEmpty(videoModel.mDetail) ? ""
                        : videoModel.mDetail);
                viewHolder.numbs.setText(videoModel.browseNum + "");
                viewHolder.date.setText(TextUtils.isEmpty(videoModel.createTime) ? ""
                        : videoModel.createTime);
                viewHolder.imageView.setImageURI(AppUtils.parse(Contants.IMAGE_URL
                        + videoModel.imgUrl));
            }
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.imageView)
            SimpleDraweeView imageView;

            @BindView(R.id.imageView_arrow)
            ImageView imageViewArrow;

            @BindView(R.id.item_title)
            TextView itemTitle;

            @BindView(R.id.item_desc)
            TextView itemDesc;

            @BindView(R.id.prefix_numbs)
            TextView prefixNumbs;

            @BindView(R.id.numbs)
            TextView numbs;

            @BindView(R.id.date)
            TextView date;

            @BindView(R.id.detail_layout)
            LinearLayout detailLayout;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
