package com.android.benben.safe.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.benben.safe.R;
import com.android.benben.safe.engine.AddressDao;
import com.android.benben.safe.utils.ToastUrl;

/**
 * Created by Administrator on 2016/8/14.
 * 是否显示电话号码归属地的service
 */
public class AddressService extends Service {

    private TelephonyManager mTM;
    private MyPhoneStateListener mPhoneStateListener;
    private TextView mContent;
    private String address;
    private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private View mViewToast;
    private WindowManager mWM;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mContent.setText(address);
        }
    };


    @Override
    public void onCreate() {
        /*第一次开启服务以后，就需要去管理吐司的显示*/
        /*电话状态的监听*/
        /*1.电话的管理者*/
        mTM = (TelephonyManager) getSystemService(Context.TELECOM_SERVICE);
        /*2.监听电话状态*/
       mPhoneStateListener= new MyPhoneStateListener();
        /*3.获取窗体对象*/
         mWM = (WindowManager) getSystemService(WINDOW_SERVICE);

        super.onCreate();
    }

    private void showToast(String incomingNumber) {
        ToastUrl.show(getApplication(),incomingNumber);
        Toast.makeText(this, incomingNumber, Toast.LENGTH_LONG).show();

        final WindowManager.LayoutParams params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
//        params.windowAnimations = com.android.internal.R.style.Animation_Toast;
        /*在响铃的时候能够显示吐司 和电话类型一致*/
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.setTitle("Toast");
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE//不能被触摸（默认是能被触摸的
 ;

        /*指定吐司的所在位置*/
        params.gravity = Gravity.LEFT + Gravity.TOP;
        /*t吐司显示效果（吐司布局文件） xml》view  将吐司挂载在windowManager窗体上*/
         mViewToast = View.inflate(this, R.layout.toast_view, null);
         mContent = (TextView) mViewToast.findViewById(R.id.toast_iv);
        /*在窗体上挂载一个view也需要权限*/
        mWM.addView(mViewToast, mParams);

        /*做来电号码的查询*/
        query(incomingNumber);

    }

    private void query(final String incomingNumber) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                 address = AddressDao.getAddress(incomingNumber);

                mHandler.sendEmptyMessage(0);
            }
        }).start();
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
                    /*挂断电话的时候需要移除吐司*/
                    if (mWM != null && mViewToast != null) {
                        mWM.removeView(mViewToast);
                    }
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
