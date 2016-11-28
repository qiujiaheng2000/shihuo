package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.HeaderBannerView;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreGridViewContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.fragments.HomeFragment;
import com.shihuo.shihuo.models.GoodsModel;
import com.shihuo.shihuo.util.AppUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.shihuo.shihuo.fragments.HomeFragment.mGoodsListTest;

/**
 * Created by cm_qiujiaheng on 2016/11/5.
 * 10/20/30/40元专区列表
 */

public class PrefectureActivity extends BaseActivity {

    public static final String FLAG_PREFECTURE_TYPE = "flag_prefecture_type";

    @BindView(R.id.imag_left)
    ImageView leftbtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.load_more_grid_view)
    GridViewWithHeaderAndFooter loadMoreGridView;
    @BindView(R.id.load_more_grid_view_container)
    LoadMoreGridViewContainer loadMoreGridViewContainer;
    @BindView(R.id.load_more_grid_view_ptr_frame)
    PtrClassicFrameLayout loadMoreGridViewPtrFrame;

    public ArrayList<GoodsModel> mGoodsList = new ArrayList<>();
    private Handler mHandler = new Handler();
    private HomeFragment.MyHomeGridViewAdapter mAdapter;

    public static void startPrefectureActivity(Context context, int listType) {
        Intent intent = new Intent(context, PrefectureActivity.class);
        intent.putExtra(FLAG_PREFECTURE_TYPE, listType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.prefecture_gridlist_activity);
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {
        String titleStr = "";
        switch (getIntent().getIntExtra(FLAG_PREFECTURE_TYPE, 1)) {
            case 1:
                titleStr = getString(R.string.ten_prefecture);
                break;
            case 2:
                titleStr = getString(R.string.twenty_prefecture);
                break;
            case 3:
                titleStr = getString(R.string.thirty_prefecture);
                break;
            case 4:
                titleStr = getString(R.string.forty_prefecture);
                break;
            default:
                break;
        }
        title.setText(titleStr);
        leftbtn.setVisibility(View.VISIBLE);

        initRefreshView();
    }

    private void initRefreshView() {


        loadMoreGridViewPtrFrame.setLoadingMinTime(1000);
        loadMoreGridViewPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // here check list view, not content.
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, loadMoreGridView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mGoodsList.clear();
                        mGoodsList.addAll(mGoodsListTest);
                        loadMoreGridViewPtrFrame.refreshComplete();
                        mAdapter.notifyDataSetChanged();
                        loadMoreGridViewContainer.setAutoLoadMore(true);
                        loadMoreGridViewContainer.loadMoreFinish(mGoodsList.isEmpty(), true);
                    }
                }, 2000);
            }
        });

        loadMoreGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("grid-view", String.format("onItemClick: %s %s", position, id));
            }
        });
        mAdapter = new HomeFragment.MyHomeGridViewAdapter(this, mGoodsList);

        HeaderBannerView bannerView = new HeaderBannerView(this);
        loadMoreGridView.addHeaderView(bannerView);

        loadMoreGridViewContainer.setAutoLoadMore(false);
        loadMoreGridViewContainer.useDefaultFooter();
        loadMoreGridView.setAdapter(mAdapter);

        loadMoreGridViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // load more complete

                        mGoodsList.addAll(mGoodsListTest);
                        loadMoreGridViewPtrFrame.refreshComplete();
                        loadMoreGridViewContainer.loadMoreFinish(mGoodsList.isEmpty(), true);
                        mAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });
        loadMoreGridViewPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMoreGridViewPtrFrame.autoRefresh();
            }
        }, 100);
    }

    @OnClick({R.id.imag_left, R.id.btn_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_more:

                break;
        }
    }
}
