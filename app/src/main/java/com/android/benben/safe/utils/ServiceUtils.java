package com.android.benben.safe.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;

/**
 * Created by LiYuanxiong on 2016/8/16 13:55.
 * Desribe:  服务的工具类
 */
public class ServiceUtils {
    /**
     * 判断是否正在运行的服务
     * @param serviceName 要判断的服务名称
     * @param mContext 上下文环境
     * @return true运行   false没有运行
     */

    public static boolean isRunning(Context mContext, String serviceName) {
        /*1.获取activityManager管理者对象，可以获取当前手机正在运行的所有服务*/
        ActivityManager mAM = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        /*2.获取手机在正在运行的服务（多少个服务）*/
        List<ActivityManager.RunningServiceInfo> runningServices = mAM.getRunningServices(100);
        /*3.遍历获取的所有的服务集合，拿到每一个服务的类的名称，和传递进来的类的名称做对比，如果一直，说明服务正在运行*/
        for (ActivityManager.RunningServiceInfo runningServiceInfo:runningServices
             ) {
            /*4.获取每一个正在运行服务的名称*/
            Class<? extends ComponentName> name = runningServiceInfo.service.getClass();
            /*5.如果匹配成功表明正在运行 返回true   如果匹配不成功 返回false 表明没有运行*/
            if (serviceName.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
