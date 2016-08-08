package com.android.benben.safe.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.android.benben.safe.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LiYuanxiong on 2016/8/8 17:19.
 * Desribe:
 */
public class ContactListActivity extends BaseActivity {

    @InjectView(R.id.ct_rv)
    RecyclerView mContent;
    private ContentListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_contact_list);
        ButterKnife.inject(this);
    }


}
