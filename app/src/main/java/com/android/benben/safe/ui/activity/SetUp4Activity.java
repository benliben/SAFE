package com.android.benben.safe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.benben.safe.R;

/**
 * Created by LiYuanxiong on 2016/8/8 14:37.
 * Desribe:
 */
public class SetUp4Activity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_setup_4);
    }

    public void nextPage4(View view) {
        startActivity(new Intent(this,SetupOverActivity.class));
        finish();

    }

    public void lastPage4(View view) {
        startActivity(new Intent(this, SetUp3Activity.class));
        finish();

    }
}
