package com.android.benben.safe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.benben.safe.R;

/**
 * Created by LiYuanxiong on 2016/8/1 15:55.
 * Desribe:抽取布局文件的管理类
 */
public class SettingItemView extends RelativeLayout {
    private static final String TAG = "lyx";
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.android.benben.safe";
    private final CheckBox cd_box;
    private final TextView tv_des, tv_title;
    private String destitle,desoff,deson;

    public SettingItemView(Context context) {
        this(context, null);
    }
    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*将设置界面的一个条目转换成view对象,直接添加当前settingItemView对应的View中*/
        View.inflate(context, R.layout.setting_item_view, this);

        /*自定义组合控件的描述*/
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_des = (TextView) findViewById(R.id.tv_des);
        cd_box = (CheckBox) findViewById(R.id.cb_box);

        /*获取自定义以及原生属性的操作，写在此处，AttributeSet attrs对象中获取*/
        initAttrs(attrs);

        /*获取布局文件中定义的字符串，赋值给标题*/
        tv_title.setText(destitle);

    }

    /**
     * @param attrs 构造方法中维护好的属性集合
     *              返回属性集合中定义属性值
     */
    private void initAttrs(AttributeSet attrs) {
        destitle = attrs.getAttributeValue(NAMESPACE, "destitle");
        desoff = attrs.getAttributeValue(NAMESPACE, "desoff");
         deson = attrs.getAttributeValue(NAMESPACE, "deson");
    }

    /**
     * 判断是否开启的方法
     *
     * @return 返回当前settingItemView是否选中状态 true开启
     */
    public boolean isCheck() {
        /*由checkBox的选中结果，决定当前条目是否开启*/
        return cd_box.isChecked();
    }

    /**
     * @param isCheck 是否作为开启的变量，由点击过程中去做传递
     */
    public void setCheck(boolean isCheck) {
        /*当前条目在选择过程中，cb_box选中状态也跟随变化*/
        cd_box.setChecked(isCheck);
        if (isCheck) {
            tv_des.setText(deson);
        } else {
            tv_des.setText(desoff);
        }

    }
}
