
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

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.FavGoodsListActivity;
import com.shihuo.shihuo.Activities.FavServiceListActivity;
import com.shihuo.shihuo.Activities.FavShopsListActivity;
import com.shihuo.shihuo.Activities.FavVideoListActivity;
import com.shihuo.shihuo.Activities.FeedbackActivity;
import com.shihuo.shihuo.Activities.LoginActivity;
import com.shihuo.shihuo.Activities.MyAddressListActivity;
import com.shihuo.shihuo.Activities.MyOrdersListActivity;
import com.shihuo.shihuo.Activities.SettingActivity;
import com.shihuo.shihuo.Activities.ShareDialog;
import com.shihuo.shihuo.Activities.shop.ShopActivity;
import com.shihuo.shihuo.Activities.shop.ShopsLocatedActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.LoginModel;
import com.shihuo.shihuo.models.UserInfoModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.shihuo.shihuo.application.AppShareUitl.getUserInfo;

/**
 * Created by jiahengqiu on 2016/10/23. 我的
 */
public class MeFragment extends BaseFragment {

    @BindView(R.id.imag_left)
    ImageView leftbtn;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.user_icon)
    SimpleDraweeView userIcon;

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

    @BindView(R.id.txBtnRight)
    TextView txBtn;

    private boolean isLogin;

    private LoginModel userModel;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.me_activity, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
//        title.setText(R.string.tab_me);
        title.setText("");
        txBtn.setText(R.string.setting);
        txBtn.setVisibility(View.VISIBLE);

        isLogin = AppShareUitl.isLogin(getContext());
        if (isLogin) {
            userModel = getUserInfo(getContext());
            if (userModel != null && userModel.userInfo != null) {
                userName.setVisibility(View.VISIBLE);
                userName.setText(AppUtils.isEmpty(userModel.userInfo.userName));
                userIcon.setImageURI(AppUtils.parse(userModel.userInfo.avatarPicUrl));
            }
        } else {
            userName.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isLogin = AppShareUitl.isLogin(getContext());
        if (isLogin) {
            request();
        } else {
            enterItem.setText(getContext().getResources().getString(R.string.me_enter_item));
        }
    }

    private void initData() {
        LoginModel model = getUserInfo(getContext());
        if (model != null) {
            int storeType = AppShareUitl.getStoreType(getContext());
            if (storeType == Contants.STORE_TYPE_SUCCESS) {
                enterItem.setText(getContext().getResources()
                        .getString(R.string.shore_type_success));
            } else if (storeType == Contants.STORE_TYPE_CHECK) {
                enterItem.setText(getContext().getResources().getString(R.string.shore_type_check));
            } else {
                enterItem.setText(getContext().getResources().getString(R.string.me_enter_item));
            }
        }
    }

    private void request() {
        String url = NetWorkHelper.API_BASICINFO + "?token=" + AppShareUitl.getToken(getContext());

        try {
            OkHttpUtils.get().url(NetWorkHelper.getApiUrl(url)).build()
                    .execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            if (response.code == ShiHuoResponse.SUCCESS) {
                                UserInfoModel model = UserInfoModel.parseJson(response.data);
                                AppShareUitl.saveStoreType(getActivity(), model.isValid, model.storeId);
                                LoginModel loginModel = AppShareUitl.getUserInfo(getContext());
                                loginModel.isValid = model.isValid;
                                loginModel.storeId = model.storeId;
                                AppShareUitl.saveUserInfo(getActivity(),
                                        LoginModel.parseToJson(loginModel));
                                initData();
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
            R.id.fav_goods, R.id.fav_shops, R.id.fav_videos, R.id.fav_services, R.id.layout_order,
            R.id.layout_add, R.id.layout_recommend,R.id.layout_public_airticle, R.id.layout_enter, R.id.layout_service,
            R.id.layout_qa, R.id.layout_abuot, R.id.layout_feedback, R.id.user_icon, R.id.txBtnRight
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fav_goods://商品收藏
                if (isLogin) {
                    FavGoodsListActivity.start(getContext());
                } else {
                    LoginActivity.start(getContext());
                }
                break;
            case R.id.fav_shops://店铺收藏
                if (isLogin) {
                    FavShopsListActivity.startFavShopsListActivity(getContext());
                } else {
                    LoginActivity.start(getContext());
                }
                break;
            case R.id.fav_videos://视频收藏
                if (isLogin) {
                    FavVideoListActivity.startFavVideoListActivity(getContext());
                } else {
                    LoginActivity.start(getContext());
                }
                break;
            case R.id.fav_services://便民收藏
                if (isLogin) {
                    FavServiceListActivity.startFavServiceListActivity(getContext());
                } else {
                    LoginActivity.start(getContext());
                }
                break;
            case R.id.layout_public_airticle:// 便民收藏
                AppUtils.showToast(getContext(), "跳转一个h5");
                break;
            case R.id.layout_order://我的订单
                if (isLogin) {
                    MyOrdersListActivity.startMyOrdersListActivity(getContext());
                } else {
                    LoginActivity.start(getContext());
                }
                break;
            case R.id.layout_add://我的地址
                if (isLogin) {
                    MyAddressListActivity.startMyAddressListActivity(getContext());
                } else {
                    LoginActivity.start(getContext());
                }
                break;
            case R.id.layout_recommend://推荐
                ShareDialog.start(getContext());
                break;
            case R.id.layout_enter://商家入驻
                if (isLogin) {
                    int storeType = AppShareUitl.getStoreType(getContext());
                    if (storeType == Contants.STORE_TYPE_SUCCESS) {
                        ShopActivity.start(getContext());
                    } else if (storeType == Contants.STORE_TYPE_CHECK) {
                        AppUtils.showToast(
                                getContext(),
                                getContext().getResources().getString(
                                        R.string.toast_shore_type_check));
                    } else {
                        ShopsLocatedActivity.startShopsLocatedActivity(getContext());
                    }
                } else {
                    LoginActivity.start(getContext());
                }
                break;
            case R.id.layout_service://客服电话
                AppUtils.callPhone(getContext(), "0359-6382822");
                break;
            case R.id.layout_qa://常见问题
                break;
            case R.id.layout_abuot://关于
                break;
            case R.id.layout_feedback://反馈
                if (isLogin) {
                    FeedbackActivity.stardFeedbackActivity(getContext());
                } else {
                    LoginActivity.start(getContext());
                }
                break;
            case R.id.user_icon:
                if (isLogin) {
                    AppUtils.showToast(getContext(), "换头像");
                } else {
                    LoginActivity.start(getContext());
                }
                break;
            case R.id.txBtnRight:// 设置按钮
                if (isLogin) {
                    SettingActivity.startSettingActivity(getActivity());
//                    VideoPlayActivity.start(getActivity(), "http://v.youku.com/v_show/id_XMTcxMDE1NzM5Ng==.html");
                } else {
                    LoginActivity.start(getContext());
                }
                break;
        }
    }
}
