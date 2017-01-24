
package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shihuo.shihuo.R;

/**
 * Created by lishuai on 16/12/26.
 * 搜索结果的店铺的 "更多"列表
 */

public class SearchStoreMoreActivity extends BaseActivity {

    private static final String KEYWORD = "keyWord";

    private String mKeyWord;

    public static void start(Context context, String keyWord) {
        Intent intent = new Intent(context, SearchStoreMoreActivity.class);
        intent.putExtra(KEYWORD, keyWord);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_store_more);
        mKeyWord = getIntent().getStringExtra(KEYWORD);

        initViews();
    }

    @Override
    public void initViews() {

    }
}
