package com.android.benben.safe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.benben.safe.R;

/**
 * Created by LiYuanxiong on 2016/8/8 13:54.
 * Desribe:
 */
public class SetUp3Activity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_setup_3);
    }

    public void nextPage3(View view) {
        startActivity(new Intent(this, SetUp4Activity.class));
        finish();

    }

    public void lastPage3(View view) {
        startActivity(new Intent(this, SetUp2Activity.class));
        finish();

    }
}
