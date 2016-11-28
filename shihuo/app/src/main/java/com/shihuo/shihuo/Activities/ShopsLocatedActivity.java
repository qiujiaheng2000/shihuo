package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shihuo.shihuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/11/27.
 * 店铺入驻界面
 */

public class ShopsLocatedActivity extends BaseActivity {


    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit_phone_number)
    EditText editPhoneNumber;
    @BindView(R.id.edittext_verifycode)
    EditText edittextVerifycode;
    @BindView(R.id.btn_get_verfiy_code)
    TextView btnGetVerfiyCode;
    @BindView(R.id.edittext_shop_name)
    EditText edittextShopName;
    @BindView(R.id.edittext_shop_desc)
    EditText edittextShopDesc;
    @BindView(R.id.shop_type_spinner)
    AppCompatSpinner shopTypeSpinner;
    @BindView(R.id.shop_area_spinner)
    AppCompatSpinner shopAreaSpinner;
    @BindView(R.id.shop_add_spinner)
    AppCompatSpinner shopAddSpinner;
    @BindView(R.id.image_shop_logo)
    ImageView imageShopLogo;
    @BindView(R.id.layout_shop_logo)
    RelativeLayout layoutShopLogo;
    @BindView(R.id.image_idcard_positive)
    ImageView imageIdcardPositive;
    @BindView(R.id.layout_idcard_positive)
    RelativeLayout layoutIdcardPositive;
    @BindView(R.id.image_idcard_reverse)
    ImageView imageIdcardReverse;
    @BindView(R.id.layout_idcard_reverse)
    RelativeLayout layoutIdcardReverse;
    @BindView(R.id.image_idcard_hand)
    ImageView imageIdcardHand;
    @BindView(R.id.layout_idcard_hand)
    RelativeLayout layoutIdcardHand;
    @BindView(R.id.bank_spinner)
    AppCompatSpinner bankSpinner;
    @BindView(R.id.edittext_bank_card)
    EditText edittextBankCard;
    @BindView(R.id.edittext_bank_card_again)
    EditText edittextBankCardAgain;
    @BindView(R.id.checkbox_notice)
    CheckBox checkboxNotice;
    @BindView(R.id.btn_shoplocatd_protocol)
    TextView btnShoplocatdProtocol;

    public static void startShopsLocatedActivity(Context context) {
        Intent intent = new Intent(context, ShopsLocatedActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shoplocated);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        imagLeft.setVisibility(View.VISIBLE);
        title.setText(R.string.shihuo_shop);
    }

    @OnClick({R.id.imag_left, R.id.layout_shop_logo, R.id.layout_idcard_positive, R.id.layout_idcard_reverse, R.id.layout_idcard_hand, R.id.btn_shoplocatd_protocol, R.id.btn_shoplocated_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.layout_shop_logo://商铺logo
                break;
            case R.id.layout_idcard_positive://身份证（证明）
                break;
            case R.id.layout_idcard_reverse://身份证（反面）
                break;
            case R.id.layout_idcard_hand://手持身份证
                break;
            case R.id.btn_shoplocatd_protocol://同意协议
                break;
            case R.id.btn_shoplocated_commit://提交审核
                break;
        }
    }
}
