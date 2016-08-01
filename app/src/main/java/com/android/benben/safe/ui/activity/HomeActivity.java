package com.android.benben.safe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;

import com.android.benben.safe.R;
import com.android.benben.safe.ui.adapter.HomeAdapter;
import com.android.benben.safe.utils.ToastUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends AppCompatActivity {

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


}
