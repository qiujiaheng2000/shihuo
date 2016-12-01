
package com.shihuo.shihuo.Views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.util.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 折扣优惠区（包含折扣区类型一、二）
 */
public class DiscountView extends LinearLayout implements View.OnClickListener {

    @BindView(R.id.image1)
    SimpleDraweeView mImage1;

    @BindView(R.id.image2)
    SimpleDraweeView mImage2;

    @BindView(R.id.image3)
    SimpleDraweeView mImage3;

    @BindView(R.id.image4)
    SimpleDraweeView mImage4;

    @BindView(R.id.image5)
    SimpleDraweeView mImage5;

    @BindView(R.id.textView1)
    TextView mTextView1;

    @BindView(R.id.textView2)
    TextView mTextView2;

    @BindView(R.id.textView3)
    TextView mTextView3;

    @BindView(R.id.textView4)
    TextView mTextView4;

    @BindView(R.id.textView5)
    TextView mTextView5;

    @BindView(R.id.view_discount)
    HorizontalDiscountView mDisCountView;

    public DiscountView(Context context) {
        super(context);
        initViews();
    }

    public DiscountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public DiscountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_discount,
                null);
        addView(view);
        ButterKnife.bind(this, view);
        mImage1.setOnClickListener(this);
        mImage2.setOnClickListener(this);
        mImage3.setOnClickListener(this);
        mImage4.setOnClickListener(this);
        mImage5.setOnClickListener(this);
    }

    /**
     * 绑定数据
     * 
     * @param list1 折扣区1
     * @param list2 折扣区2
     */
    public void setData(List<GoodsTypeModel> list1, List<GoodsTypeModel> list2) {
        // 折扣优惠类型1
        if (!list1.isEmpty()) {
            if (list1.size() > 0) {
                commonCode(list1, 0, mTextView1, mImage1);
            }
            if (list1.size() > 1) {
                commonCode(list1, 1, mTextView2, mImage2);
            }
            if (list1.size() > 2) {
                commonCode(list1, 2, mTextView3, mImage3);
            }
            if (list1.size() > 3) {
                commonCode(list1, 3, mTextView4, mImage4);
            }
            if (list1.size() > 4) {
                commonCode(list1, 4, mTextView5, mImage5);
            }
        }
        // 折扣优惠类型2
        if (!list2.isEmpty()) {
            mDisCountView.setData(list2);
        }
    }

    private void commonCode(List<GoodsTypeModel> list1, int index, TextView view, SimpleDraweeView imageView){
        imageView.setImageURI(AppUtils.parse(list1.get(index).discountPicUrl));
        if (!TextUtils.isEmpty(list1.get(index).discountName)) {
            view.setVisibility(View.VISIBLE);
            view.setText(list1.get(index).discountName);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image1:
                AppUtils.showToast(getContext(), "1");
                break;
            case R.id.image2:
                AppUtils.showToast(getContext(), "2");
                break;
            case R.id.image3:
                AppUtils.showToast(getContext(), "3");
                break;
            case R.id.image4:
                AppUtils.showToast(getContext(), "4");
                break;
            case R.id.image5:
                AppUtils.showToast(getContext(), "5");
                break;

        }
    }
}