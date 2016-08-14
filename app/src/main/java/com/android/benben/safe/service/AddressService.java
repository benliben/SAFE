package com.android.benben.safe.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.android.benben.safe.utils.ToastUrl;

/**
 * Created by Administrator on 2016/8/14.
 * 是否显示电话号码归属地的service
 */
public class AddressService extends Service {

    private TelephonyManager mTM;
    private MyPhoneStateListener mPhoneStateListener;

    @Override
    public void onCreate() {
        /*第一次开启服务以后，就需要去管理吐司的显示*/
        /*电话状态的监听*/
        /*1.电话的管理者*/
        mTM = (TelephonyManager) getSystemService(Context.TELECOM_SERVICE);
        /*2.监听电话状态*/
       mPhoneStateListener= new MyPhoneStateListener();

        super.onCreate();
    }

    private void showToast(String incomingNumber) {
        ToastUrl.show(getApplication(),incomingNumber);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        /*取消电话的监听*/
        if (mTM != null && mPhoneStateListener != null) {
            mTM.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
        }

        super.onDestroy();
    }

    class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    /*空闲状态*/
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    /*接听状态*/
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    showToast(incomingNumber);
                    /*响铃状态*/
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }
}
