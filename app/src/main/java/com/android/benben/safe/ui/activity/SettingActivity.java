package com.android.benben.safe.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.benben.safe.R;
import com.android.benben.safe.view.SettingItemView;

/**
 * Created by LiYuanxiong on 2016/8/1 15:34.
 * Desribe:
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initUpdate();
    }

    private void initUpdate() {
        final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*获取当前的状态*/
                boolean isCheck = siv_update.isCheck();
                /*将原有的状态取反*/
                siv_update.setCheck(!isCheck);


            }
        });

    }


}
