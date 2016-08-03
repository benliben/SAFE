package com.android.benben.safe.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by LiYuanxiong on 2016/8/3 18:24.
 * Desribe:
 */
public class TestActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("测试界面");
        setContentView(textView);
    }
}
