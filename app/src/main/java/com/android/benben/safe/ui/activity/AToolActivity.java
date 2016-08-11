package com.android.benben.safe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.benben.safe.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LiYuanxiong on 2016/8/11 16:06.
 * Desribe:
 */
public class AToolActivity extends BaseActivity {
    private static final String TAG = "AToolActivity";
    @InjectView(R.id.tv_query_phone_address)
    TextView mQueryPhoneAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_atool);
        ButterKnife.inject(this);
    }




    @OnClick(R.id.tv_query_phone_address)
    public void onClick() {
        startActivity(new Intent(this, QueryAddressActivity.class));
    }
}
