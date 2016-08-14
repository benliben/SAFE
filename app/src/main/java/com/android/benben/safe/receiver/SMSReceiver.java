package com.android.benben.safe.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import com.android.benben.safe.R;
import com.android.benben.safe.service.DeviceAdmin;
import com.android.benben.safe.service.LocationService;
import com.android.benben.safe.ui.activity.ConstantValue;
import com.android.benben.safe.utils.SpUtil;
import com.android.benben.safe.utils.ToastUrl;

/**
 * Created by LiYuanxiong on 2016/8/10 15:37.
 * Desribe:
 */
public class SMSReceiver extends BroadcastReceiver {

    private Context mContext;
    private DevicePolicyManager mDPM;
    private ComponentName mDeviceAdminSample;
    @Override
    public void onReceive(Context context, Intent intent) {
        mDeviceAdminSample = new ComponentName(mContext, DeviceAdmin.class);
        mDPM = (DevicePolicyManager) mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);

        /*判断是否开启了防盗保护*/
        boolean open_secuity = SpUtil.getBoolean(context, ConstantValue.OPEN_SECURITY, false);

        if (open_secuity) {
            /*2.获取短信内容*/
            Object[] objects = (Object[]) intent.getExtras().get("pdus");
            for (Object object:objects
                 ) {
                /*获取短信对象*/
               SmsMessage sms= SmsMessage.createFromPdu((byte[]) object);
                /*获取短信对象的基本信息*/
                String originatingAddress = sms.getOriginatingAddress();
                String messageBody = sms.getMessageBody();

                /*6.判断是否包含关键字*/
                if (messageBody.contains("#*alarm*#")) {
                    /*播放音乐 （准备音乐）*/
                    MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
                    player.setLooping(true);
                    player.start();
                }
                if (messageBody.contains("#*location*#")) {
                    /*开启获取位置的服务*/
                    context.startService(new Intent(context, LocationService.class));
                } else if (messageBody.contains("#*lockscrenn*#")) {
                    /*判断是否开启*/
                    if (mDPM.isAdminActive(mDeviceAdminSample)) {
                        mDPM.lockNow();
                        mDPM.resetPassword("123", 0);
                    }else {
                        ToastUrl.show(context,"请先激活");
                    }

                } else if (messageBody.contains("#*wipedate*#")) {
                    if (mDPM.isAdminActive(mDeviceAdminSample)) {
                        mDPM.wipeData(0);//手机数据
////                        mDPM.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//手机sd卡的数据请慎重
                    }else {
                        ToastUrl.show(context,"请先激活");
                    }
                }
            }
        }
    }
}
