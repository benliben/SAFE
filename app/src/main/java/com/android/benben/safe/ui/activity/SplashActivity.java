package com.android.benben.safe.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.benben.safe.R;
import com.android.benben.safe.utils.StreamUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LiYuanxiong on 2016/7/25 14:22.
 * Desribe:
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = "lyx";
    private static final int UPDATE_VERSION = 1;//更新
    private static final int ENTER_HOME = 2;//进入主界面
    @InjectView(R.id.tv_version_name)
    TextView mVersionName;
    @InjectView(R.id.pb_time)
    ProgressBar mTime;
    private Object versionName;
    private int versionCode;
    private int mVersionCode;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "handleMessage: ");
            Log.i(TAG, "handleMessage: "+msg.what);
            switch (msg.what) {
                case UPDATE_VERSION:
                    Log.i(TAG, "handleMessage: adsfjaksjfksajfksajflksajflkajfjalkf");
                    break;
                case ENTER_HOME:
                    Log.i(TAG, "=========++++++");
                    /*进入应用程序*/
                    enterHome();
                    break;
            }

        }


    };

    /*进入应用程序的主界面*/
    private void enterHome() {
        Log.i(TAG, "--------------");
        startActivity(new Intent(this,MainActivity.class));
        /*在开启一个新的界面后 将导航页面关闭*/
        finish();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);
        initData();
    }

    /**
     * 获取数据方法
     */
    private void initData() {
        /*1.获取版本名称*/
        mVersionName.setText("版本名称:" + getVersionName());
        /*2.检查是否有更新（用现有的版本号与服务器做对比） 如果是有更新就提示用户更新(member)*/
        mVersionCode = getVersionCode();
        /*3.获取服务器的版本号（客户端发请求，服务端给相应（json，xml））*/
        //http://www.oxxx.com/SAFE.json?key=value  返回200请求成功，流的方式将数据读取下来
        /**
         * json中内容包括
         * 更新版本的版本名称
         * 新版本的描述信息
         * 服务端版本号
         * 新版本apk下载地址
         */
        checkVersion();
    }

    /**
     * 检查版本号
     */
    private void checkVersion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*发送请求获取数据*/
                Message message = Message.obtain();
                long startTime = System.currentTimeMillis();
                try {
                   /*1.封装url地址*/
                    URL url = new URL("http://www.baidu.com");
                    /*************************************/
                    /*仅限于模拟器访问电脑的tomcat*/
//                    URL url = new URL("http://http://10.0.2.2:8888/benben.json");
                    /************************************/


                /*2.开启一个链接*/
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    /*3.设置常见的请求参数（请求头）*/
                    connection.setConnectTimeout(3000);//请求超时
                    connection.setReadTimeout(4000);//读取超时
                    connection.setRequestMethod("GET");//默认是GET请求
                    /*4.获取响应码*/
                    if (connection.getResponseCode() == 200) {
                        /*5.以流的形式，将数据获取下来*/
                        InputStream is = connection.getInputStream();
                        /*6将流转换成字符串（工具类封装）*/
                        String json = StreamUtil.streamToString(is);
                        Log.i("lyx", "run: " + json);

                        /*7.json解析*/
                        try {

                            JSONObject object = new JSONObject(json);

//                            String versionName = object.getString("versionName");
//                            String versionDes = object.getString("versionDes");
//                            String versionCode = object.getString("versionCode");
//                            String downloadurl = object.getString("downloadUrl");

                            /*8.比对版本号（服务器版本号大于本地版本号，提示用户更新）*/
//                            if (mVersionCode < Integer.parseInt(versionCode)) {
                                /*8.1提示用户更新，弹出对话框，消息机制*/
//                                message.what = UPDATE_VERSION;
//                            } else {
//                                /*8.2直接进入程序界面*/
                                message.what = ENTER_HOME;
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    Log.i(TAG, "run: " + e);
                    e.printStackTrace();
                } finally {
                    /*指定睡眠时间  请求网络的时长超过4秒则不做处理*/
                    /*请求网络的时长小于4秒 强制让其睡眠4秒*/

                    long endTime = System.currentTimeMillis();
                    long time = endTime - startTime;
                    if (time < 4000) {
                        try {
                            Log.i(TAG, "0000000000");
                            Thread.sleep(4000 - time);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.i(TAG, "run: dfasdfadfasfa");
                    mHandler.sendMessage(message);
                }
            }
        }).start();

    }

    /**
     * 获取版本名称：清单文件中
     *
     * @return 应用版本名称 返回null 代表有异常
     */
    public String getVersionName() {
      /*1.包的管理者*/
        PackageManager pm = getPackageManager();
        /*2.从包的管理者对象中，获取指定包名的基本信息.传0代表获取基本信息*/
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回版本号
     *
     * @return 非0代表为成功
     */
    public int getVersionCode() {
  /*1.包的管理者*/
        PackageManager pm = getPackageManager();
        /*2.从包的管理者对象中，获取指定包名的基本信息.传0代表获取基本信息*/
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
