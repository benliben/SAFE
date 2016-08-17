package com.android.benben.safe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LiYuanxiong on 2016/8/1 17:21.
 * Desribe:
 */
public class SpUtil {
    private static SharedPreferences sp;

    /**
     * 写
     * @param context 上下文
     * @param key 存储节点的名称
     * @param value 存储节点的boolean
     */
    public static void putBoolean(Context context, String key, boolean value) {

        if (sp ==null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     *读
     * @param context 上下文环境
     * @param key 储存节点名称
     * @param defValue 没有此节点的默认值
     * @return  默认值或者此节点读取到的结果
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }







    /**
     * 写
     * @param context 上下文
     * @param key 存储节点的名称
     * @param value 存储节点的boolean
     */
    public static void putString(Context context, String key, String value) {

        if (sp ==null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    /**
     *读
     * @param context 上下文环境
     * @param key 储存节点名称
     * @param defValue 没有此节点的默认值
     * @return  默认值或者此节点读取到的结果
     */
    public static String getString(Context context, String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    /**
     * 写
     * @param context 上下文
     * @param key 存储节点的名称
     * @param value 存储节点的boolean
     */
    public static void putInt(Context context, String key, int value) {

        if (sp ==null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }

    /**
     *读
     * @param context 上下文环境
     * @param key 储存节点名称
     * @param defValue 没有此节点的默认值
     * @return  默认值或者此节点读取到的结果
     */
    public static int getInt(Context context, String key, int defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    /**
     *从sp中移除指定节点
     * @param context 上下文
     * @param key 需要移节点的名称
     */
    public static void remove(Context context, String key) {
        if (sp ==null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).commit();
    }
}
