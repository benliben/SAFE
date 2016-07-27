package com.android.benben.safe.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;

import com.android.benben.safe.R;
import com.android.benben.safe.ui.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends AppCompatActivity {

    @InjectView(R.id.rv_content)
    RecyclerView mContent;

    private List<String> mDatas;
    private HomeAdapter mAdapter;

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
        mContent.setAdapter(mAdapter = new HomeAdapter(this,mDatas));
           /*设置Item增加，移除动画*/
        mContent.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            mDatas.add("" + i);
        }
    }
}
