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
import android.widget.TextView;

import com.android.benben.safe.R;
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
 * Created by LiYuanxiong on 2016/7/26 15:51.
 * Desribe:
 */
public class SplashActivityTest extends BaseActivity {
    private static final int UPDATE_VERSION = 1;//更新
    private static final int ENTER_HOME = 2;//进入主界面
    private static final int URL_ERROR = 3;//url异常
    private static final int IO_ERROR = 4;//io异常
    private static final int JSON_ERROR = 5;//json异常
    private static final String TAG = "lyx";
    @InjectView(R.id.tv_version_name)
    TextView mName;
    private int mVersionCode;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_VERSION:
                    /*弹出对话框 提示用户是否更新*/
                    showUpdateDialog();
                    break;
                case ENTER_HOME:
                    /*直接进入主界面*/
                    enterHome();
                    break;
                case URL_ERROR:
                    /*提示然后进入主界面*/
                    ToastUrl.show(getApplicationContext(),"URL");
                    enterHome();
                    break;
                case IO_ERROR:
                    ToastUrl.show(getApplicationContext(),"IO");
                    enterHome();
                    break;
                case JSON_ERROR:
                    ToastUrl.show(getApplicationContext(),"JSON");
                    enterHome();
                    break;
            }
        }
    };
    private String versionDes;
    private String downloadUrl;

    /*进入主界面*/
    private void enterHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /*弹出对话框 提示用户更新*/
    private void showUpdateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("版本更新");
        builder.setMessage(versionDes);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*下载apk*/
                downloadApk();
            }
        });
        builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*取消对话框 然后直接进入主界面*/
                enterHome();

            }
        });
        /*监听break返回键*/
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
                dialog.dismiss();
            }
        });

        builder.show();
    }

    /*下载apk*/
    private void downloadApk() {
        /*1.先判断sd卡是否可用*/
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            /*2.获取sd卡的路径*/
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                    File.separator + "safe.apk";
            /*3.发送请求 获取apk并且放置到指定路径*/
            HttpUtils httpUtils = new HttpUtils();
            /*4.发送请求，传递参数（下载地址，下载应用放置的位置*/
            httpUtils.download(downloadUrl, path, new RequestCallBack<File>() {
                /*下载成功*/
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    File file = responseInfo.result;
                    /*提示安装*/
                    installApk(file);
                }
                /*下载失败*/
                @Override
                public void onFailure(HttpException e, String s) {
                    Log.i(TAG, "下载失败");
                }

                @Override
                public void onStart() {
                    Log.i(TAG, "刚开始下载");
                    super.onStart();
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    Log.i(TAG, "下载中");
                    super.onLoading(total, current, isUploading);
                }
            });


        }
    }

    /*提示安装apk*/
    private void installApk(File file) {
        /*系统应用界面*/
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        /*文件作为数据源*/
        intent.setData(Uri.fromFile(file));
        /*设置安装的类型*/
        intent.setType("application/vnd.android.package-archive");
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);
        /*获取数据*/
        initData();
    }

    private void initData() {
        /*1.获取版本名称*/
        mName.setText("版本名称" + getVersionName());
        /*2.获取本地版本号检查是否有更新（用本地的版本号与服务器的版本号做对比）*/
        mVersionCode = getVersionCode();
        /*3.获取服务器的版本号*/
        checkVersion();

    }

    /**
     * 检查服务端的版本号
     */
    private void checkVersion() {
        /*由于是联网的耗时操作所有要放在子线程中进行*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*发送请求 获取数据*/
                Message message = Message.obtain();
                long startTime = System.currentTimeMillis();//获取链接开始的时间
                try {
                    /*1.封装url地址*/
                    URL url = null;
                    url = new URL("http://192.168.10.103:8888/benben.json");
                    /*2.开启一个链接*/
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    /*3.设置常见的请求参数*/
                    connection.setConnectTimeout(2000);//请求超时
                    connection.setReadTimeout(4000);//读取超时
                    connection.setRequestMethod("GET");//请求方式（默认为GET请求）

                    /*4.获取响应码并做判断*/
                    if (connection.getResponseCode() == 200) {
                        /*5.以流的形式，将数据获取下来*/
                        InputStream is = connection.getInputStream();
                        /*6.将流转换成字符串（工具类对其进行了封装）*/
                        String json = StreamUtil.streamToString(is);
                        /*7.json解析*/
                        JSONObject object = new JSONObject(json);
                        String versionName = object.getString("versionName");
                         versionDes = object.getString("versionDes");
                        String versionCode = object.getString("versionCode");
                         downloadUrl = object.getString("downloadUrl");
                        /*8.对比版本号*/
                        if (mVersionCode < Integer.parseInt(versionCode)) {
                            message.what = UPDATE_VERSION;
                        }else {
                            message.what = ENTER_HOME;
                        }
                    }
                } catch (MalformedURLException e) {
                    /*url异常*/
                    message.what = URL_ERROR;
                    e.printStackTrace();
                }   catch (IOException e) {
                        /*输入输出异常*/
                    message.what = IO_ERROR;
                    e.printStackTrace();
                } catch (JSONException e) {
                    /*json解析异常*/
                    message.what = JSON_ERROR;
                    e.printStackTrace();
                }finally {
                    long endTime = System.currentTimeMillis();//获取网络链接结束时的时间
                    long time = endTime - startTime;
                    if (time < 4000) {
                        try {
                            Thread.sleep(4000 - time);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendMessage(message);
                }
            }
        }).start();

    }

    /**
     * 获取版本名称 从清单文件中获取
     * @return 应用版本名称 返回null 代表有异常
     */
    public String getVersionName() {
        /*实例化包的管理者*/
        PackageManager pm = getPackageManager();
        /*2.从包的管理者对象中获取包名的基本信息 传0表示获取所有的信息*/
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本地的版本号
     * @return 非0代表成功
     */
    public int getVersionCode() {
        /*1.实例化包的管理者*/
        PackageManager pm = getPackageManager();
        /*2.从包的管理者对象中获取包名的基本信息，传0表示或所有基本信息*/
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
