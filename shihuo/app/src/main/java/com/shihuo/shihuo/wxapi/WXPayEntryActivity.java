package com.shihuo.shihuo.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shihuo.shihuo.Activities.MyOrdersListActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.pay.PayHelper;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
    private IWXAPI api;

	@BindView(R.id.layout)
	LinearLayout layout;

	@BindView(R.id.imag_left)
	ImageView imagLeft;

	@BindView(R.id.title)
	TextView title;

	@BindView(R.id.textview)
	TextView textview;

	@BindView(R.id.tv_order)
	TextView tv_order;

	private boolean isPaySuccess;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
		AppUtils.fullScreenColor(this);
		ButterKnife.bind(this);
    	api = WXAPIFactory.createWXAPI(this, PayHelper.WX_APPID);
        api.handleIntent(getIntent(), this);

		imagLeft.setVisibility(View.VISIBLE);
		title.setText("运城识货购物网");
    }

	@OnClick({
			R.id.imag_left, R.id.layout
	})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.imag_left:
				finish();
				break;
			case R.id.layout:
				if(isPaySuccess){
					finish();
					MyOrdersListActivity.start(WXPayEntryActivity.this);
				}
				break;
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d("WXPayEntryActivity", "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(resp.errCode == 0){
				textview.setText("您的订单已支付成功");
				isPaySuccess = true;
				tv_order.setVisibility(View.VISIBLE);
			}else{
				textview.setText("您的订单支付支付失败");
				isPaySuccess = false;
				tv_order.setVisibility(View.GONE);
			}
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();
		}
	}
}