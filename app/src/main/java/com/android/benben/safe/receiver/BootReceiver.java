package com.android.benben.safe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.benben.safe.ui.activity.ConstantValue;
import com.android.benben.safe.utils.SpUtil;

/**
 * Created by LiYuanxiong on 2016/8/10 15:07.
 * Desribe:
 */
public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        /*一旦监听到开机广播，就要进行sim卡的序列号的比对*/
        Log.i(TAG, "开机了 ");

        /*1获取本地存储的sim卡序列号码*/
         String spNumber=SpUtil.getString(context, ConstantValue.OPEN_BOUND_SIM, "");
        /*2获取当前的sim卡序列号码*/
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
       String ctNumber= tm.getSimSerialNumber()+"123";
        /*3对两个sim卡的序列号做对比*/
        if (!spNumber.equals(ctNumber)) {
            /*4如果序列号不一致就发送短信*/
            SmsManager sm = SmsManager.getDefault();
            String receiveNumber=SpUtil.getString(context, ConstantValue.PHONE_NUMBER, "");
            sm.sendTextMessage(receiveNumber, null, "卡发生了改变", null, null);
        }


    }
}
