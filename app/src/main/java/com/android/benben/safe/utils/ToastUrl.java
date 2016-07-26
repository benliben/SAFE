package com.android.benben.safe.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by LiYuanxiong on 2016/7/26 9:39.
 * Desribe:
 */
public class ToastUrl {
    /**
     *
     * @param ctx 上下文环境
     * @param msg 显示的内容
     */
    public static void show(Context ctx, String msg) {
        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
    }
}
