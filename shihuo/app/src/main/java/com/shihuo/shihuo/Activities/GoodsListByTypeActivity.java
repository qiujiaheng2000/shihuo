package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shihuo.shihuo.Adapters.GoodsGrideListAdapter;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.HomeHeaderView;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreGridViewContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.models.HomeModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by cm_qiujiaheng on 2016/12/14.
 * 类别商品列表页面
 */

public class GoodsListByTypeActivity extends BaseActivity {


    public static final String LIST_TYPE = "listType";

    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.btn_msg)
    ImageButton btnMsg;
    @BindView(R.id.btn_more)
    ImageButton btnMore;
    @BindView(R.id.title_bar)
    RelativeLayout titleBar;
    @BindView(R.id.load_more_grid_view)
    GridViewWithHeaderAndFooter loadMoreGridView;
    @BindView(R.id.load_more_grid_view_container)
    LoadMoreGridViewContainer loadMoreGridViewContainer;
    @BindView(R.id.load_more_grid_view_ptr_frame)
    PtrClassicFrameLayout loadMoreGridViewPtrFrame;

    //记录当前列表的分类
    private int mFlag;

    //点击的商品类型
    private GoodsTypeModel mGoodsTypeModel;

    private GoodsGrideListAdapter mAdapter;
    private List<HomeModel> mList = new ArrayList<>();

    public static void start(Context context, GoodsTypeModel flag) {
        Intent intent = new Intent(context, GoodsListByTypeActivity.class);
        intent.putExtra(LIST_TYPE, flag);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_goodsbytype_list);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        mGoodsTypeModel = getIntent().getParcelableExtra(LIST_TYPE);
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(String.format(getResources().getString(R.string.goodslist_by_type), "类型"));

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


            }
        });

        loadMoreGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("grid-view", String.format("onItemClick: %s %s", position, id));
            }
        });
        mAdapter = new GoodsGrideListAdapter(this, mList);
        HomeHeaderView homeHeaderView = new HomeHeaderView(this);
        loadMoreGridView.addHeaderView(homeHeaderView);

        loadMoreGridViewContainer.setAutoLoadMore(false);
        loadMoreGridViewContainer.useDefaultFooter();
        loadMoreGridView.setAdapter(mAdapter);

        loadMoreGridViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {


            }
        });
    }

    @OnClick({R.id.imag_left, R.id.btn_more, R.id.title_bar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_more:
                break;
            case R.id.title_bar:
                break;
        }
    }
}
