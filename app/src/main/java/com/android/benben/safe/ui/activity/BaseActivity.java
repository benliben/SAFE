package com.android.benben.safe.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by LiYuanxiong on 2016/7/25 14:22.
 * Desribe:
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("BaseActivity", "当前所在的页面为"+getClass().getCanonicalName());
    }



}
