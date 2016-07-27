package com.android.benben.safe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by LiYuanxiong on 2016/7/27 10:39.
 * Desribe:能获取焦点的自定义的TextView
 */
public class FocusTestView extends TextView {
    /**
     * 使用在通过java代码创建控件
     */
    public FocusTestView(Context context) {
        super(context);
    }

    /**
     * 由系统调用
     *
     * @param context 上下文环境
     * @param attrs   属性集合
     */
    public FocusTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 由系统调用
     *
     * @param context      上下文构造环境
     * @param attrs        属性
     * @param defStyleAttr 布局文件中定义样式的构造方法
     */
    public FocusTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重写获取焦点的方法 由系统调用， 调用的时候默认就获取焦点
     */
    @Override
    public boolean isFocused() {
        return true;
    }
}
