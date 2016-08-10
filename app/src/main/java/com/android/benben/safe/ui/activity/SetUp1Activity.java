package com.android.benben.safe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.benben.safe.R;

/**
 * Created by Administrator on 2016/8/7.
 */
public class SetUp1Activity extends BaseSetupActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_setup_1);
    }

    @Override
    public void showNextPage() {
        Intent intent = new Intent(this, SetUp2Activity.class);
        startActivity(intent);
        finish();
        /*开启平移动画*/
        overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
    }

    @Override
    public void showPrePage() {

    }


}
