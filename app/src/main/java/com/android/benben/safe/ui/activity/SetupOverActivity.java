package com.android.benben.safe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.benben.safe.R;
import com.android.benben.safe.utils.SpUtil;

/**
 * Created by LiYuanxiong on 2016/8/3 18:24.
 * Desribe:
 */
public class SetupOverActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /*密码输入成功，并且四个导航界面设置成功-----》停留在设置完成功能列表界面
       * 如果设置没有成功，-----》跳转到导航界面的第一个界面*/
        boolean setup_over=SpUtil.getBoolean(this, ConstantValue.SETUP_OVER, false);
        if (setup_over) {
            /*密码输入成功，并且四个导航界面设置成功-----》停留在设置完成功能列表界面*/
            setContentView(R.layout.a_setup_1);
        }else {
            /*如果设置没有成功，-----》跳转到导航界面的第一个界面*/
            Intent intent = new Intent(this, SetUpActivity.class);
            startActivity(intent);

            /*开启了一个新的界面关闭本界面*/
            finish();
        }

    }
}
