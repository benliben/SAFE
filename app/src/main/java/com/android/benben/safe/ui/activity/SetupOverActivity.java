package com.android.benben.safe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.benben.safe.R;
import com.android.benben.safe.utils.SpUtil;

import butterknife.InjectView;

/**
 * Created by LiYuanxiong on 2016/8/3 18:24.
 * Desribe:
 */
public class SetupOverActivity extends BaseActivity {
    private static final String TAG = "SetupOverActivity";
    TextView mPhoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean setup_over = SpUtil.getBoolean(this, ConstantValue.SETUP_OVER, false);
        if (setup_over) {
            /**密码输入成功，并且四个导航界面设置成功-----》停留在设置完成功能列表界面*/
            setContentView(R.layout.a_setup);
        } else {
            /**如果设置没有成功，-----》跳转到导航界面的第一个界面*/
            Intent intent = new Intent(this, SetUp1Activity.class);
            startActivity(intent);
            finish();
        }
        /**跟新试图*/
        initUi();

    }

    private void initUi() {
        mPhoneNumber = (TextView) findViewById(R.id.tv_phone_number);
        String phoneNumber = SpUtil.getString(this, ConstantValue.PHONE_NUMBER, "");
        Log.i(TAG, "initUi: " + phoneNumber);
        mPhoneNumber.setText(phoneNumber);

    }

    public void toSetup1(View view) {
        startActivity(new Intent(this, SetUp1Activity.class));
    }
}
