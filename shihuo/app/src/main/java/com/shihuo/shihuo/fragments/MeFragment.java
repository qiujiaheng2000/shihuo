package com.shihuo.shihuo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shihuo.shihuo.Activities.FavGoodsListActivity;
import com.shihuo.shihuo.Activities.FavServiceListActivity;
import com.shihuo.shihuo.Activities.FavShopsListActivity;
import com.shihuo.shihuo.Activities.FavVideoListActivity;
import com.shihuo.shihuo.Activities.SettingActivity;
import com.shihuo.shihuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jiahengqiu on 2016/10/23.
 * 我的
 */
public class MeFragment extends BaseFragment {

    @BindView(R.id.leftbtn)
    Button leftbtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rightbtn)
    Button rightbtn;
    @BindView(R.id.user_icon)
    ImageView userIcon;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.me_header_bg)
    RelativeLayout meHeaderBg;
    @BindView(R.id.fav_goods)
    LinearLayout favGoods;
    @BindView(R.id.fav_shops)
    LinearLayout favShops;
    @BindView(R.id.fav_videos)
    LinearLayout favVideos;
    @BindView(R.id.fav_services)
    LinearLayout favServices;

    @BindView(R.id.layout_order)
    RelativeLayout layoutOrder;

    @BindView(R.id.layout_add)
    RelativeLayout layoutAdd;

    @BindView(R.id.layout_recommend)
    RelativeLayout layoutRecommend;

    @BindView(R.id.layout_enter)
    RelativeLayout layoutEnter;

    @BindView(R.id.layout_service)
    RelativeLayout layoutService;

    @BindView(R.id.layout_qa)
    RelativeLayout layoutQa;

    @BindView(R.id.layout_abuot)
    RelativeLayout layoutAbuot;

    @BindView(R.id.layout_feedback)
    RelativeLayout layoutFeedback;

    public static MeFragment newInstance() {
        MeFragment frament = new MeFragment();
        Bundle bundle = new Bundle();
        frament.setArguments(bundle);
        return frament;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.me_activity, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        title.setText(R.string.tab_me);
        rightbtn.setText(R.string.setting);
        rightbtn.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.rightbtn, R.id.fav_goods, R.id.fav_shops, R.id.fav_videos, R.id.fav_services, R.id.layout_order, R.id.layout_add, R.id.layout_recommend, R.id.layout_enter, R.id.layout_service, R.id.layout_qa, R.id.layout_abuot, R.id.layout_feedback})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rightbtn:
                SettingActivity.startSettingActivity(getActivity());
                break;
            case R.id.fav_goods:
                FavGoodsListActivity.startFavGoodsListActivity(getContext());
                break;
            case R.id.fav_shops:
                FavShopsListActivity.startFavShopsListActivity(getContext());
                break;
            case R.id.fav_videos:
                FavVideoListActivity.startFavVideoListActivity(getContext());
                break;
            case R.id.fav_services:
                FavServiceListActivity.startFavServiceListActivity(getContext());
                break;
            case R.id.layout_order:
                break;
            case R.id.layout_add:
                break;
            case R.id.layout_recommend:
                break;
            case R.id.layout_enter:
                break;
            case R.id.layout_service:
                break;
            case R.id.layout_qa:
                break;
            case R.id.layout_abuot:
                break;
            case R.id.layout_feedback:
                break;
        }
    }
}
