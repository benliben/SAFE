package com.android.benben.safe.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.benben.safe.R;
import com.android.benben.safe.utils.SpUtil;
import com.android.benben.safe.utils.ToastUrl;
import com.android.benben.safe.view.SettingItemView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LiYuanxiong on 2016/8/8 11:42.
 * Desribe:
 */
public class SetUp2Activity extends BaseActivity {
    private static final String TAG = "SetUp2Activity";
    @InjectView(R.id.siv_sim_bound)
    SettingItemView mSimBound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_setup_2);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        /*回显，读取已有的绑定状态，*/
        String open_bound_sim = SpUtil.getString(this, ConstantValue.OPEN_BOUND_SIM,"");
        Log.i(TAG, "open_bound_sim: "+open_bound_sim);
        if (TextUtils.isEmpty(open_bound_sim)) {
            mSimBound.setCheck(false);
        } else {
            mSimBound.setCheck(true);
        }
    }

    public void nextPage2(View view) {
        String number = SpUtil.getString(this, ConstantValue.OPEN_BOUND_SIM, "");
        if (TextUtils.isEmpty(number)) {
            startActivity(new Intent(this, SetUp3Activity.class));
            finish();
        }else {
            ToastUrl.show(this,"请绑定SIM卡");
        }

        overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);


    }

    public void lastPage2(View view) {
        startActivity(new Intent(this, SetUp1Activity.class));
        finish();
        overridePendingTransition(R.anim.pre_in_anim,R.anim.pre_out_anim);
    }

    @OnClick(R.id.siv_sim_bound)
    public void onClick() {
        /*获取原有的状态*/
        boolean isCheck = mSimBound.isCheck();
        /*将原有的状态取反，状态给设置当前条目*/
        mSimBound.setCheck(!isCheck);
        /*绑定序列号*/
        if (!isCheck) {
            Log.i(TAG, "===============1=============");
            /*存储序列卡号*/
            TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String number = manager.getSimSerialNumber();
            SpUtil.putString(this,ConstantValue.OPEN_BOUND_SIM,number);

        }else {
            Log.i(TAG, "===============2=============");
            /*将存储的序列卡号的节点 从sp中删除*/
            SpUtil.remove(getApplicationContext(), ConstantValue.OPEN_BOUND_SIM);
        }
    }
}
