package com.android.benben.safe.ui.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.benben.safe.R;
import com.android.benben.safe.model.PhoneNumberModel;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LiYuanxiong on 2016/8/8 17:19.
 * Desribe: 选着安全号码界面
 */
public class ContactListActivity extends BaseActivity {

    private static final String TAG = "ContactListActivity";
    @InjectView(R.id.ct_rv)
    RecyclerView mContent;
    private ContentListAdapter mAdapter;

    private HashMap<String, String> hashMap;
    private ArrayList<HashMap<String, String>> mList=new ArrayList<HashMap<String, String>>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mContent.setAdapter(mAdapter);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_contact_list);
        ButterKnife.inject(this);
        initData();

        mContent.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ContentListAdapter(this, mList);

        mAdapter.setOnItemClickListener(new ContentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                /*1.获取点中条目的索引指向索引集合的对象*/
                if (mAdapter != null) {
                    HashMap<String, String> hashMap = mList.get(position);
                    String phone = hashMap.get("phone");
                    Log.i(TAG, "phone: "+phone);
                    /*3.此电话号码需要给第三个导航界面使用*/
                    Intent intent = new Intent();
                    intent.putExtra("phone", phone);
                    setResult(0, intent);
                    finish();
                }



            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void initData() {
        /*因为读取联系人，可能是一个耗时操作，so放置到子线程中操作*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                    /*1.获取内容解析器对象*/
                ContentResolver contentResolver = getContentResolver();

                /*2.做查询系统联系人数据库的过程*/
                Cursor cursor = contentResolver.query(
                        Uri.parse("content://com.android.contacts/raw_contacts"),
                        new String[]{"contact_id"}, null, null, null);
                mList.clear();
                /*3.循环游标，直到没有为止*/
                while (cursor.moveToNext()) {
                    String id = cursor.getString(0);
                    /*4.根据用户唯一性id值，查询data表和mimetype表生成的视图，获取data已经mimetype字段*/
                    Cursor indexCursor = contentResolver.query(
                            Uri.parse("content://com.android.contacts/data"),
                            new String[]{"data1", "mimetype"},
                            "raw_contact_id=?", new String[]{id}, null);

                    /*5.循环获取每一个联系人的电话号码及用户名，数据类型*/
                    hashMap = new HashMap<String, String>();
                    while (indexCursor.moveToNext()) {
                        String data = indexCursor.getString(0);
                        String type = indexCursor.getString(1);

                        /*6.区分数据类型*/
                        if (type.equals("vnd.android.cursor.item/phone_v2")) {
                            if (!TextUtils.isEmpty(data)) {
                                    hashMap.put("phone", data);
                            }
                        } else if (type.equals("vnd.android.cursor.item/name")) {
                            if (!TextUtils.isEmpty(data)) {
                                hashMap.put("name", data);
                            }
                        }


                    }
                    indexCursor.close();
                    mList.add(hashMap);
                    Log.i(TAG, "run: "+mList);


                }
                cursor.close();
                /*消息机制 发送一个空的消息，告知主线程可以使用子线程已经填充好的数据集合*/
                handler.sendEmptyMessage(0);


            }
        }).start();

    }


}
