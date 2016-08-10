package com.android.benben.safe;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.EditText;

import com.android.benben.safe.ui.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.location.LocationManager.*;

/**
 * Created by LiYuanxiong on 2016/8/10 16:12.
 * Desribe:
 */
public class GPSActivity extends Activity {
    private static final String TAG = "GPSActivity";
    @InjectView(R.id.et_set_psd)
    EditText X;
    @InjectView(R.id.et_confirm_psd)
    EditText Y;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_set_psd);
        ButterKnife.inject(this);

        /*1.获取位置管理者对象*/
//        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.i(TAG, "================1=================");


        Log.i(TAG, "================dfadsf=================");
        /**
         * 通过lm获取经纬度坐标
         *  定位方式
         *  最小间隔时间
         *  最小间隔距离
         *  监听事件
         *
         */

//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                /*gps状态发生切换的监听事件*/
//            }
//            @Override
//            public void onProviderEnabled(String provider) {
//
//                /*gps开启的时候的事件监听*/
//            }
//            @Override
//            public void onProviderDisabled(String provider) {
//
//                /*gps关闭的时候的事件监听*/
//            }
//            @Override
//            public void onLocationChanged(Location location) {
//
//                /*经度*/
//                double longitude = location.getLongitude();
//                /*纬度*/
//                double latitude = location.getLatitude();
//
//                Log.i("lyx", "onLocationChanged: " + longitude + "f000000" + latitude);
//                X.setText("经度为" + longitude);
//                Y.setText("纬度为" + latitude);
//            }
//        });

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
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        lm.requestLocationUpdates(GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                //状态发生改变(GPS信号切换过中调动方法)
            }

            @Override
            public void onProviderEnabled(String provider) {
                //Gps开启调用方法
            }

            @Override
            public void onProviderDisabled(String provider) {
                //Gps关闭调用的方法
            }

            @Override
            public void onLocationChanged(Location location) {
                //位置发生改变时调用的方法
                double latitude = location.getLatitude();//获取纬度
                double longitude = location.getLongitude();//经度

                //权限
                X.setText("loaction:" + longitude + "," + latitude);
                Log.i("lyx", "loaction:" + longitude + "," + latitude);
            }
        });
    }
}
