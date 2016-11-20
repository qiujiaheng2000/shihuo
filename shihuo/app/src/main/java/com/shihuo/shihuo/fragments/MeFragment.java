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
import com.shihuo.shihuo.Activities.FeedbackActivity;
import com.shihuo.shihuo.Activities.LoginActivity;
import com.shihuo.shihuo.Activities.MyAddressListActivity;
import com.shihuo.shihuo.Activities.MyOrdersListActivity;
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

    @BindView(R.id.imag_left)
    ImageView leftbtn;
    @BindView(R.id.title)
    TextView title;
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
    @BindView(R.id.thirdbtn)
    Button thirdbtn;
    @BindView(R.id.order_icon)
    ImageView orderIcon;
    @BindView(R.id.order_item)
    TextView orderItem;
    @BindView(R.id.imageView_arrow_order)
    ImageView imageViewArrowOrder;
    @BindView(R.id.add_icon)
    ImageView addIcon;
    @BindView(R.id.add_item)
    TextView addItem;
    @BindView(R.id.imageView_arrow_add)
    ImageView imageViewArrowAdd;
    @BindView(R.id.recommend_icon)
    ImageView recommendIcon;
    @BindView(R.id.recommend_item)
    TextView recommendItem;
    @BindView(R.id.imageView_arrow_recommend)
    ImageView imageViewArrowRecommend;
    @BindView(R.id.enter_icon)
    ImageView enterIcon;
    @BindView(R.id.enter_item)
    TextView enterItem;
    @BindView(R.id.imageView_arrow_enter)
    ImageView imageViewArrowEnter;
    @BindView(R.id.service_icon)
    ImageView serviceIcon;
    @BindView(R.id.service_item)
    TextView serviceItem;
    @BindView(R.id.imageView_arrow_service)
    ImageView imageViewArrowService;
    @BindView(R.id.qa_icon)
    ImageView qaIcon;
    @BindView(R.id.qa_item)
    TextView qaItem;
    @BindView(R.id.imageView_arrow_qa)
    ImageView imageViewArrowQa;
    @BindView(R.id.about_icon)
    ImageView aboutIcon;
    @BindView(R.id.about_item)
    TextView aboutItem;
    @BindView(R.id.imageView_arrow_about)
    ImageView imageViewArrowAbout;
    @BindView(R.id.feedback_icon)
    ImageView feedbackIcon;
    @BindView(R.id.feedback_item)
    TextView feedbackItem;
    @BindView(R.id.imageView_arrow_feedback)
    ImageView imageViewArrowFeedback;
    @BindView(R.id.login_icon)
    ImageView loginIcon;
    @BindView(R.id.login_item)
    TextView loginItem;
    @BindView(R.id.imageView_arrow_login)
    ImageView imageViewArrowLogin;
    @BindView(R.id.layout_login)
    RelativeLayout layoutLogin;
    @BindView(R.id.txBtn)
    TextView txBtn;

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
        txBtn.setText(R.string.setting);
        txBtn.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.fav_goods, R.id.fav_shops, R.id.fav_videos, R.id.fav_services, R.id.layout_order, R.id.layout_add, R.id.layout_recommend, R.id.layout_enter, R.id.layout_service, R.id.layout_qa, R.id.layout_abuot, R.id.layout_feedback,R.id.layout_login,R.id.txBtn})
    public void onClick(View view) {
        switch (view.getId()) {
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
                MyOrdersListActivity.startMyOrdersListActivity(getContext());
                break;
            case R.id.layout_add:
                MyAddressListActivity.startMyAddressListActivity(getContext());
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
                FeedbackActivity.stardFeedbackActivity(getContext());
                break;
            case R.id.layout_login:
                LoginActivity.startLoginActivity(getContext());
                break;
            case R.id.txBtn://设置按钮
                SettingActivity.startSettingActivity(getActivity());
                break;
        }
    }
}
