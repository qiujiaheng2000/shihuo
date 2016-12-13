package com.shihuo.shihuo.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.Views.loadmore.LoadMoreListViewContainer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by cm_qiujiaheng on 2016/12/13.
 * 购物车列表界面
 */

public class ShoppingCarListActivity extends BaseActivity {


    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.txBtnRight)
    TextView txBtnRight;
    @BindView(R.id.checkbox_check_all)
    CheckBox checkboxCheckAll;
    @BindView(R.id.textview_total_price)
    TextView textviewTotalPrice;
    @BindView(R.id.textview_total_num)
    TextView textviewTotalNum;
    @BindView(R.id.rotate_header_list_view)
    ListView rotateHeaderListView;
    @BindView(R.id.load_more_list_view_container)
    LoadMoreListViewContainer loadMoreListViewContainer;
    @BindView(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout rotateHeaderListViewFrame;

    private MyShoppingCarAdapter myShoppingCarAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shoppingcar);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(R.string.title_shoppingcar);
        txBtnRight.setText(R.string.edit);

        rotateHeaderListViewFrame.setLoadingMinTime(1000);
        rotateHeaderListViewFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, rotateHeaderListView, header);
            }
        });

        myShoppingCarAdapter = new MyShoppingCarAdapter();

        loadMoreListViewContainer.setAutoLoadMore(false);
        loadMoreListViewContainer.useDefaultFooter();
        rotateHeaderListView.setAdapter(myShoppingCarAdapter);

        rotateHeaderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {

            }
        });
        rotateHeaderListViewFrame.autoRefresh();
    }

    @OnClick({R.id.imag_left, R.id.txBtnRight, R.id.btn_settlement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.txBtnRight:


                break;
            case R.id.btn_settlement:
                break;
        }
    }


    public class MyShoppingCarAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
