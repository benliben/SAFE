package com.android.benben.safe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.benben.safe.R;

/**
 * Created by LiYuanxiong on 2016/8/16 15:19.
 * Desribe: 抽取的点击文件的类
 */
public class SettingClickView extends RelativeLayout{

    private final TextView tv_des, tv_title;

    public SettingClickView(Context context) {
        this(context, null);
    }
    public SettingClickView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingClickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*将设置界面的一个条目转换成view对象,直接添加当前settingItemView对应的View中*/
        View.inflate(context, R.layout.setting_click_view, this);

        /*自定义组合控件的描述*/
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_des = (TextView) findViewById(R.id.tv_des);
    }

    /**
     * 设置标题的内容
     * @param title
     */
    public void setTitle(String title) {
        tv_title.setText(title);
    }

    /**
     * 设置描述的内容
     * @param des
     */
    public void setDes(String des) {
        tv_des.setText(des);
    }
}
