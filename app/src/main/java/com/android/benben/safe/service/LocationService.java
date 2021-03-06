package com.android.benben.safe.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by LiYuanxiong on 2016/8/10 17:07.
 * Desribe:
 */
public class LocationService extends Service {

    private static final String TAG = "LocationService";

    @Override
    public void onCreate() {
        super.onCreate();
        /*获取手机经纬度坐标*/
        /*获取手机经纬的的管理者*/
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        /*2.以最优的方式获取经纬度的坐标*/
        Criteria criteria = new Criteria();
        /*允许花费*/
        criteria.setCostAllowed(true);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);//指定获取经纬度的精确度
        String baseProvider = lm.getBestProvider(criteria, true);//
        /*在一定事件*/

        MyLocationListener listener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(baseProvider, 0, 0, listener);
    }

    class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
             /*经度*/
            double longitude = location.getLongitude();
                /*纬度*/
            double latitude = location.getLatitude();
            /*发送短信*/
//            SmsManager sms = SmsManager.getDefault();
//            sms.sendTextMessage("", null, "", null, null);
            Log.i(TAG, "longitude:" + longitude + "==" + "latitude:" + latitude);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
