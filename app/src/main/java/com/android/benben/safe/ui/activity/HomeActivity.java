package com.android.benben.safe.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.benben.safe.R;
import com.android.benben.safe.ui.adapter.HomeAdapter;
import com.android.benben.safe.utils.Md5Util;
import com.android.benben.safe.utils.SpUtil;
import com.android.benben.safe.utils.ToastUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    @InjectView(R.id.rv_content)
    RecyclerView mContent;

    private HomeAdapter mAdapter;

    private String[] titleStr = {
            "手机防盗", "通信卫士", "软件管理",
            "进程管理", "流量统计", "手机杀毒",
            "缓存清理", "高级工具", "设置中心"};
    private int[] mipmapIds = {
            R.mipmap.home_safe, R.mipmap.home_callmsgsafe, R.mipmap.home_apps,
            R.mipmap.home_taskmanager, R.mipmap.home_netmanager, R.mipmap.home_trojan,
            R.mipmap.home_sysoptimize, R.mipmap.home_tools, R.mipmap.home_settings
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        /*设置管理器*/
        mContent.setLayoutManager(new GridLayoutManager(this, 3));//为GridView布局
//        mContent.setLayoutManager(new LinearLayoutManager(this));//为listView布局
         /*设置适配器*/
        mContent.setAdapter(mAdapter = new HomeAdapter(this, titleStr, mipmapIds));
           /*设置Item增加，移除动画*/
        mContent.setItemAnimator(new DefaultItemAnimator());


        /*设置item点击事件*/
        initItemClick();
    }

    /*点击事件*/
    private void initItemClick() {
        mAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        showDialog();
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                        break;

                }
                ToastUrl.show(getApplicationContext(), "你点击了" + position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                }
                ToastUrl.show(getApplicationContext(), "你长按了" + position);

            }
        });
    }

    /**
     * 弹出对话框
     */
    private void showDialog() {
        /*判断本地是否有存储密码(sp 字符串)*/
        String psd = SpUtil.getString(this, ConstantValue.MOBILE_SAFE_PSD, "");
        /*1.没有密码就是第一次创建*/
        if (TextUtils.isEmpty(psd)) {
            showSetPsdDialog();
        } else {
            /*2.有密码就不是第一次创建*/
            showConfirmPsdDialog();
        }
    }

    /**
     * 第一次创建密码对话框
     */
    private void showSetPsdDialog() {
        /*1因为需要自己去展示定义的对话框的展示样式，所以需要调用dialog.setView(view)
        * view是自己编写的xml转换成view对象 */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        final View view = View.inflate(this, R.layout.dialog_set_psd, null);
        /*2让对话框显示一个自己定义的对话框界面效果*/
        dialog.setView(view);
        dialog.show();

        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        /*确定的点击*/
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_set_psd = (EditText) view.findViewById(R.id.et_set_psd);
                EditText et_confirm_psd = (EditText) view.findViewById(R.id.et_confirm_psd);

                String set_psd = et_set_psd.getText().toString();
                String confirm_psd = et_confirm_psd.getText().toString();
                if (!TextUtils.isEmpty(set_psd) && !TextUtils.isEmpty(confirm_psd)) {
                    if (set_psd .equals(confirm_psd) ) {
                        /*进入应用手机防盗模块，开启一个新的界面*/
                        Intent intent = new Intent(HomeActivity.this, TestActivity.class);
                        startActivity(intent);
                        /*跳转到新的界面 隐藏对话框*/
                        dialog.dismiss();
                        SpUtil.putString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PSD, Md5Util.encoder(set_psd));
                    } else {
                        ToastUrl.show(getApplicationContext(), "两次输入不一致，请重新输入");
                    }
                } else {
                    ToastUrl.show(getApplicationContext(), "内容不能为空");
                }
            }
        });


        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 确认密码对话框
     */
    private void showConfirmPsdDialog() {
                /*因为需要自己去展示定义的对话框的展示样式，所以需要调用dialog.setView(view)
        * view是自己编写的xml转换成view对象 */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        final View view = View.inflate(this, R.layout.dialog_confirm_psd, null);
        /*让对话框显示一个自己定义的对话框界面效果*/
        dialog.setView(view);
        dialog.show();

        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);

        /*点击了确定*/
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_confirm_psd = (EditText) view.findViewById(R.id.et_confirm_psd);
                String confirm_psd = et_confirm_psd.getText().toString();
                String set_psd = SpUtil.getString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PSD, "");
                Log.i(TAG, "onClick: "+set_psd);
                if (!TextUtils.isEmpty(confirm_psd)) {
                    /*将存储在sp中的32位的密码获取出来，然后将输入的密码进行加密处理然后在进行对比*/
                    if (set_psd .equals(Md5Util.encoder(confirm_psd)) ) {

                        /*进入应用手机防盗模块，开启一个新的界面*/
                        Intent intent = new Intent(HomeActivity.this, TestActivity.class);
                        startActivity(intent);
                        /*跳转到新的界面 隐藏对话框*/
                        dialog.dismiss();
                    } else {
                        ToastUrl.show(getApplicationContext(), "密码错误，请重新输入");
                    }
                } else {
                    ToastUrl.show(getApplicationContext(), "内容不能为空");
                }
            }
        });

        /*点击了取消*/
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


}
