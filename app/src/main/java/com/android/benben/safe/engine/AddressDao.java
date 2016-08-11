package com.android.benben.safe.engine;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by LiYuanxiong on 2016/8/11 17:33.
 * Desribe:
 */
public class AddressDao {
    /*1.指定访问数据库的路径*/
    public static String path = "data/data/com.android.benben.safe/files/address.db";


    /**
     * 传递一个电话号码，开启数据库的链接，进行访问，返会一个归属地
     * @param phone 查询的电话号码
     */
    public static void getAddress(String phone) {
        /*只取电话号码的前七位数*/
       phone= phone.substring(0, 7);
        /**
         * 2.开启数据库链接
         * 路径
         * 游标
         * 读写的模式（现在是只读的状态）
         */
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        /*3.数据库的查询*/
//      c  db.query("datea1",new String[]{"outkey"},"id=?",new String[]{phone},null,null,null);
    }
}
