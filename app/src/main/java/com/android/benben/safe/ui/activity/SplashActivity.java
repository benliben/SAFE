package com.android.benben.safe.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.benben.safe.R;
import com.android.benben.safe.utils.SpUtil;
import com.android.benben.safe.utils.StreamUtil;
import com.android.benben.safe.utils.ToastUrl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LiYuanxiong on 2016/7/25 14:22.
 * Desribe:
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = "lyx";
    private static final int UPDATE_VERSION = 100;//更新
    private static final int ENTER_HOME = 200;//进入主界面
    private static final int URL_ERROR = 300;//URL出错
    private static final int IO_ERROR = 400;//IO数据读取出错
    private static final int JSON_ERROR = 500;//URL出错
    @InjectView(R.id.tv_version_name)
    TextView mVersionName;
    @InjectView(R.id.rl_root)
    RelativeLayout mRoot;

    private int mVersionCode;
    private String mVersionDes;
    private String mDownloadUrl;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "handleMessage: " + msg.what);
            switch (msg.what) {
                case UPDATE_VERSION:
                    /* 弹出对话框 提示用户更新*/
                    showUpdateDialog();
                    break;
                case ENTER_HOME:
                    /*进入应用程序*/
                    enterHome();
                    break;
                case URL_ERROR:
                    ToastUrl.show(getApplicationContext(), "url异常");
                    enterHome();
                    break;
                case IO_ERROR:
                    ToastUrl.show(getApplicationContext(), "读取异常");
                    enterHome();
                    break;
                case JSON_ERROR:
                    ToastUrl.show(getApplicationContext(), "JSON解析异常");
                    enterHome();
                    break;
            }
        }
    };

    /**
     * 创建布局
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);
        /*获取数据*/
        initData();
        /*初始化动画*/
        initAnimation();
    }

    /*添加淡入的动画效果*/
    private void initAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(3000);
        mRoot.startAnimation(animation);

    }


    /**
     * 弹出对话框 提示用户更新
     */
    private void showUpdateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);//
        builder.setTitle("版本更新");//
        builder.setMessage(mVersionDes);//
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*下载apk */
                downloadApk();
            }
        });
        builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*取消对话框*/
                enterHome();
            }
        });
        /*点击取消的事件监听*/
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                /*即使用户点击取消也可以主界面*/
                enterHome();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * apk下载链接地址  放置apk的所在路径
     */
    private void downloadApk() {
        /*1.判断sd卡是否可用*/
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            /*2.获取sd路径*/
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                    File.separator + "mobilesafe74.apk";
            /*3.发送请求，获取apk 并且放置到指定路径*/
            HttpUtils httpUtils = new HttpUtils();
            /*4.发送请求，传递参数（下载地址，下载引用放置的位置，）*/
            httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
                /**
                 * 下载成功
                 * @param responseInfo
                 */
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    /*下载过后的放置在sd卡的位置*/
                    Log.i(TAG, "下载成功");
                    File file = responseInfo.result;
                    /*提示安装*/
                    installApk(file);
                }

                /**
                 * 下载失败
                 * @param e
                 * @param s
                 */
                @Override
                public void onFailure(HttpException e, String s) {
                    Log.i(TAG, "下载失败");
                }

                /**
                 * 刚刚开始下载的方法
                 */
                @Override
                public void onStart() {
                    Log.i(TAG, "刚刚开始下载");
                    super.onStart();
                }

                /**
                 *下载过程中的方法
                 * @param total 总数
                 * @param current 当前的
                 * @param isUploading 是否正在下载
                 */
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    Log.i(TAG, "下载过程中");
                    super.onLoading(total, current, isUploading);
                }
            });
        }
    }


    /**
     * 安装对应的apk
     *
     * @param file 安装的文件
     */
    private void installApk(File file) {
        /*系统应用界面*/
//        Intent intent = new Intent("android.intent.action.VIEW");
//        intent.addCategory("android.intent.category.DEFAULT");
        /*文件作为数据源*/
//        intent.setData(Uri.fromFile(file));
        /*设置安装的类型*/
//        intent.setType("application/vnd.android.package-archive");
//        startActivityForResult(intent, 0);

        //系统应用界面,源码,安装apk入口
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        //文件作为数据源
        //设置安装的类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivityForResult(intent, 0);
    }

    /**
     * 开启一个activity后 返回结果调用的方法
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 进入应用程序的主界面
     */
    private void enterHome() {
        startActivity(new Intent(this, HomeActivity.class));
        /*在开启一个新的界面后 将导航页面关闭*/
        finish();
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
        if (SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE, false)) {
            checkVersion();
        }else {
            /*消息机制*/
//            mHandler.sendMessageDelayed(,4000);
            /*在发送消息4秒后Enter_home状态码指向的消息*/
            mHandler.sendEmptyMessageDelayed(ENTER_HOME, 4000);
        }

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
//                try {
                   /*1.封装url地址*/
                URL url = null;
                try {
                    url = new URL("http://192.168.10.103:8888/benben.json");
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
                        /*7.json解析*/
                        try {
                            JSONObject object = new JSONObject(json);
                            String versionName = object.getString("versionName");
                            mVersionDes = object.getString("versionDes");
                            String versionCode = object.getString("versionCode");
                            mDownloadUrl = object.getString("downloadUrl");
                            /*8.比对版本号（服务器版本号大于本地版本号，提示用户更新）*/
                            if (mVersionCode < Integer.parseInt(versionCode)) {
                                /*8.1提示用户更新，弹出对话框，消息机制*/
                                message.what = UPDATE_VERSION;
                            } else {
                                /*8.2直接进入程序界面*/
                                message.what = ENTER_HOME;
                            }
                        } catch (JSONException e) {
                            /*json错误*/
                            message.what = JSON_ERROR;
                            e.printStackTrace();
                        }
                    }
                } catch (MalformedURLException e) {
                    /*url不正确*/
                    message.what = URL_ERROR;
                    e.printStackTrace();
                } catch (IOException e) {
                    message.what = IO_ERROR;
                    e.printStackTrace();
                } finally {
                    /*指定睡眠时间  请求网络的时长超过4秒则不做处理*/
                    /*请求网络的时长小于4秒 强制让其睡眠4秒*/
                    long endTime = System.currentTimeMillis();
                    long time = endTime - startTime;
                    if (time < 4000) {
                        try {
                            Thread.sleep(4000 - time);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
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
