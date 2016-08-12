package com.android.benben.safe.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lidroid.xutils.db.annotation.Id;

/**
 * Created by LiYuanxiong on 2016/8/11 17:33.
 * Desribe:
 */
public class AddressDao {
    private static final String TAG = "lyx";
    /**
     * 1.指定访问数据库的路径
     */
    public static String path = "data/data/com.android.benben.safe/files/address.db";
    private static String mLocation = "未知号码";


    /**
     * 传递一个电话号码，开启数据库的链接，进行访问，返会一个
     *
     * @param phone 查询的电话号码
     */
    public static String getAddress(String phone) {
        mLocation = "未知号码";
        /*正则表达式，匹配手机号码*/
        String regularExpression = "^1[3-8]\\d{9}";
        /**
         * 2.开启数据库链接
         * 路径
         * 游标工厂
         * 读写的模式（现在是只读的状态）
         */
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        if (phone.matches(regularExpression)) {
            /**只取电话号码的前七位数*/
            phone = phone.substring(0, 7);
            /**3.数据库的查询*/
            Cursor cursor = db.query("data1", new String[]{"outkey"}, "id=?", new String[]{phone}, null, null, null);
            /**4.查到即可*/
            if (cursor.moveToNext()) {
                String outkey = cursor.getString(0);
                Log.i(TAG, "outkey: " + outkey);
                Cursor cursor1 = db.query("data2", new String[]{"location"}, "id=?", new String[]{outkey}, null, null, null);
                if (cursor1.moveToNext()) {
                    mLocation = cursor1.getString(0);
                    Log.i(TAG, "location" + mLocation);
                }
            } else {
                mLocation = "未知号码";
            }

        } else {
            int leength = phone.length();
            switch (leength) {
                case 3://119 110  120   114
                    mLocation = "报警电话";
                    break;
                case 4://119 110  120   114
                    mLocation = "模拟器";
                    break;
                case 5://119 110  120   114
                    mLocation = "服务电话";
                    break;
                case 7://119 110  120   114
                    mLocation = "本地电话";
                    break;
                case 8://119 110  120   114
                    mLocation = "报警电话";
                    break;
                case 11://(3+8)查询data2
                    String area=phone.substring(1, 3);
                    Cursor cursor = db.query("data2", new String[]{"location"}, "area=?", new String[]{area}, null, null, null);
                    if (cursor.moveToNext()) {
                      mLocation=  cursor.getString(0);
                    } else {
                        mLocation = "未知号码";
                    }
                    break;
                case 12://(4+8)
                    String area1=phone.substring(1, 4);
                    Cursor cursor1 = db.query("data2", new String[]{"location"}, "area=?", new String[]{area1}, null, null, null);
                    if (cursor1.moveToNext()) {
                        mLocation=  cursor1.getString(0);
                    } else {
                        mLocation = "未知号码";
                    }
                    break;

            }
        }
return mLocation;
    }
}
