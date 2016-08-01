package com.android.benben.safe.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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

    private List<String> mDatas;
    private HomeAdapter mAdapter;

    private String[] titleStr = {
            "手机防盗","通信卫士","软件管理",
            "进程管理","流量统计","手机杀毒",
            "缓存清理","高级工具","设置中心"};
    private int[]mipmapIds={
            R.mipmap.home_safe,R.mipmap.home_callmsgsafe,R.mipmap.home_apps,
            R.mipmap.home_taskmanager,R.mipmap.home_netmanager,R.mipmap.home_trojan,
            R.mipmap.home_sysoptimize,R.mipmap.home_tools,R.mipmap.home_settings
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        /*初始化数据*/
        initData();
        /*设置管理器*/
        mContent.setLayoutManager(new GridLayoutManager(this, 3));
         /*设置适配器*/
        mContent.setAdapter(mAdapter = new HomeAdapter(this,titleStr,mipmapIds));
           /*设置Item增加，移除动画*/
        mContent.setItemAnimator(new DefaultItemAnimator());
        initClick();
    }

    private void initClick() {
        mAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                }
                ToastUrl.show(getApplicationContext(), "你点击了" + position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                ToastUrl.show(getApplicationContext(), "你长按了" + position);

            }
        });
    }

    private void initData() {

//        for (int i = 0; i < titleStr.length; i++) {
//            mDatas.add("" + i);
//        }
    }


}
