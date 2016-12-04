package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.models.MyAddressModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/11/24.
 */

public class NewAddressActivity extends BaseActivity {
    @BindView(R.id.imag_left)
    ImageView imagLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.txBtnRight)
    TextView txBtn;
    @BindView(R.id.edte_consignee)
    EditText edteConsignee;
    @BindView(R.id.edte_phone)
    EditText edtePhone;
    @BindView(R.id.edte_province)
    EditText edteProvince;
    @BindView(R.id.edte_address)
    EditText edteAddress;

    public static final int FLAG_NEW_ADDRESS = 0;//新增
    public static final int FLAG_EDIT_ADDRESS = 1;//编辑
    public static final String KEY_TYPE = "key_type";//当前界面类型 ？
    public static final String KEY_ADDRESS = "key_address";//要修改的地址对象

    private MyAddressModel myAddressModel;//要修改的地址对象

    public static void startNewAddressActivity(Context context, MyAddressModel addressModel, int flag) {
        Intent intent = new Intent(context, NewAddressActivity.class);
        intent.putExtra(KEY_ADDRESS, addressModel);
        intent.putExtra(KEY_TYPE, flag);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_address);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        txBtn.setText(R.string.save);
        imagLeft.setVisibility(View.VISIBLE);
        txBtn.setVisibility(View.VISIBLE);
        if (FLAG_NEW_ADDRESS == getIntent().getIntExtra(KEY_TYPE, FLAG_NEW_ADDRESS)) {
            title.setText(R.string.new_address_title);
        } else {
            title.setText(R.string.edit_address_title);
            myAddressModel = (MyAddressModel) getIntent().getSerializableExtra(KEY_ADDRESS);
            setUIByAddress();
        }
    }

    /**
     * 根据要修改的地址，初始化界面
     */
    private void setUIByAddress() {
        edteConsignee.setText(myAddressModel.addressUser);
        edtePhone.setText(myAddressModel.addressPhone);
        edteProvince.setText(myAddressModel.addressProvince);
        edteAddress.setText(myAddressModel.addressDesc);
    }


    @OnClick({R.id.imag_left, R.id.txBtnRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.txBtnRight:
                save();
                break;
        }
    }

    /**
     * 保存新地址
     * TODO
     */
    private void save() {

    }
}
