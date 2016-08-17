package com.android.benben.safe.ui.activity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.android.benben.safe.R;
import com.android.benben.safe.service.AddressService;
import com.android.benben.safe.utils.ServiceUtils;
import com.android.benben.safe.utils.SpUtil;
import com.android.benben.safe.view.SettingClickView;
import com.android.benben.safe.view.SettingItemView;

/**
 * Created by LiYuanxiong on 2016/8/1 15:34.
 * Desribe设置界面
 */
public class SettingActivity extends BaseActivity {

    private static final String TAG = "lyx";
    private static final String NAMESPACE = "com.android.benben.safe.service.AddressService";
    private String[] mToastStyleDes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initUpdate();
        initLocation();
        initToastStyle();
    }

    private void initToastStyle() {
         SettingClickView scv_toast_style = (SettingClickView) findViewById(R.id.scv_toast_style);
        scv_toast_style.setTitle("设置归属地显示风格");
        /*创建描述文字所在的string数组*/
         mToastStyleDes = new String[]{"透明", "橙色", "蓝色", "灰色", "绿色"};

        /*通过sp获取吐司显示样式的索引值，用于获取描述文字*/
        int toast_styke = SpUtil.getInt(this, ConstantValue.TOAST_STYLE, 0);
        /*获取字符串数组当中的文字显示到控件描述上*/
        scv_toast_style.setDes(mToastStyleDes[toast_styke]);

    }


    /**
     * 更新功能的实现
     */
    private void initUpdate() {
        /*找到控件*/
        final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);
        /*1获取已有的开关状态，用作显示*/
        boolean open_update = SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE, false);
        /*2是否被选择，根据上次存储的结果去做决定*/
        siv_update.setCheck(open_update);
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*获取当前的状态*/
                boolean isCheck = siv_update.isCheck();
                /*将原有的状态取反*/
                siv_update.setCheck(!isCheck);
                /*将取反后的状态存储到相应的sp中*/
                SpUtil.putBoolean(getApplicationContext(), ConstantValue.OPEN_UPDATE, !isCheck);
            }
        });
    }


    /**
     * 是否显示电话号码归属地
     */
    private void initLocation() {
        final SettingItemView siv_location = (SettingItemView) findViewById(R.id.siv_location);
//        boolean open_location = SpUtil.getBoolean(this, ConstantValue.OPEN_LOCATION, false);
        boolean running = ServiceUtils.isRunning(this, NAMESPACE);
        /*是否被选择*/
        siv_location.setCheck(running);
        siv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*获取当前的状态*/
                boolean isCheck = siv_location.isCheck();
                /*将原有的状态取反*/
                siv_location.setCheck(!isCheck);
                /*将取反后的状态存储到相应的sp中*/
                SpUtil.putBoolean(getApplicationContext(), ConstantValue.OPEN_LOCATION, !isCheck);

                if (!isCheck) {
                    /*开启服务*/
                    startService(new Intent(getApplicationContext(), AddressService.class));
                }else {
                    /*关闭服务*/
                    stopService(new Intent(getApplicationContext(), AddressService.class));
                }
            }
        });
    }


}
