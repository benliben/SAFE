package com.android.benben.safe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.android.benben.safe.R;
import com.android.benben.safe.utils.SpUtil;
import com.android.benben.safe.utils.ToastUrl;

/**
 * Created by LiYuanxiong on 2016/8/8 14:37.
 * Desribe:
 */
public class SetUp4Activity extends BaseSetupActivity {
    private CheckBox cb_box;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_setup_4);
        initUI();
    }

    @Override
    public void showNextPage() {
        boolean open_security = SpUtil.getBoolean(this, ConstantValue.OPEN_SECURITY, false);
        if (open_security) {
            /*已经开启了防盗保护措施*/
            SpUtil.putBoolean(this, ConstantValue.SETUP_OVER, open_security);
            startActivity(new Intent(this, SetupOverActivity.class));
            finish();
            overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);

        } else {
            SpUtil.putBoolean(this, ConstantValue.SETUP_OVER, open_security);
            ToastUrl.show(this, "请开启防盗保护措施");
        }
    }

    @Override
    public void showPrePage() {
        startActivity(new Intent(this, SetUp3Activity.class));
        finish();
        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
    }

    /*初始化界面*/
    private void initUI() {
        cb_box = (CheckBox) findViewById(R.id.setup4_cb_box);
        /*1.是否选中状态的回显*/
        boolean open_security = SpUtil.getBoolean(this, ConstantValue.OPEN_SECURITY, false);
        /*2.根据状态，修改checkBox后续的文字显示*/
        if (open_security) {
            cb_box.setText("安全设置已经开启");
            cb_box.setChecked(open_security);
        } else {
            cb_box.setText("安全设置已经关闭");
            cb_box.setChecked(open_security);
        }

        /*3.点击过程中，监听选中状态的发生改变的过程，*/
        cb_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /*4.切换后的状态的存储*/
                SpUtil.putBoolean(getApplicationContext(), ConstantValue.OPEN_SECURITY, isChecked);
                /*根据开启或关闭状态，去修改显示额文字*/
                if (isChecked) {
                    cb_box.setText("安全设置已经开启");
                    cb_box.isChecked();
                } else {
                    cb_box.setText("安全设置已经关闭");
                }
            }
        });
    }
}
