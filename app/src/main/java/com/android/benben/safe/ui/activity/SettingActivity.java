package com.android.benben.safe.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.android.benben.safe.R;
import com.android.benben.safe.utils.SpUtil;
import com.android.benben.safe.view.SettingItemView;

/**
 * Created by LiYuanxiong on 2016/8/1 15:34.
 * Desribe:
 */
public class SettingActivity extends BaseActivity {

    private static final String TAG = "lyx";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initUpdate();
        initLocation();
    }



    private void initUpdate() {

        final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);
        /*获取已有的开关状态，用作显示*/
        boolean open_update = SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE, false);
        Log.i(TAG, "==============="+open_update);
        /*是否被选择，根据上次存储的结果去做决定*/
        siv_update.setCheck(open_update);
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*获取当前的状态*/
                boolean isCheck = siv_update.isCheck();
                /*将原有的状态取反*/
                siv_update.setCheck(!isCheck);
                /*将取反后的状态存储到相应的sp中*/
                SpUtil.putBoolean(getApplicationContext(),ConstantValue.OPEN_UPDATE,!isCheck);
                Log.i(TAG, "===============");
            }
        });
    }

    private void initLocation() {
        final SettingItemView siv_location = (SettingItemView) findViewById(R.id.siv_location);
        boolean open_location = SpUtil.getBoolean(this, ConstantValue.OPEN_LOCATION, false);
        /*是否被选择*/
        siv_location.setCheck(open_location);
        siv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*获取当前的状态*/
                boolean isCheck = siv_location.isCheck();
                /*将原有的状态取反*/
                siv_location.setCheck(!isCheck);
                /*将取反后的状态存储到相应的sp中*/
                SpUtil.putBoolean(getApplicationContext(),ConstantValue.OPEN_LOCATION,!isCheck);
            }
        });
    }


}
