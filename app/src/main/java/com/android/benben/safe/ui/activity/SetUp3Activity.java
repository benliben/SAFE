package com.android.benben.safe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.benben.safe.R;
import com.android.benben.safe.utils.SpUtil;
import com.android.benben.safe.utils.ToastUrl;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LiYuanxiong on 2016/8/8 13:54.
 * Desribe:
 */
public class SetUp3Activity extends BaseActivity {
    @InjectView(R.id.et_phone_number)
    EditText mPhoneNumber;
    @InjectView(R.id.bt_phone)
    Button mPhone;
    String phoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_setup_3);
        ButterKnife.inject(this);
        initUI();
    }

    private void initUI() {
        String number = SpUtil.getString(this, ConstantValue.PHONE_NUMBER, "");
        if (TextUtils.isEmpty(number)) {
            mPhoneNumber.setText(number);
        }

    }

    public void nextPage3(View view) {
        phoneNumber = mPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            startActivity(new Intent(this, SetUp4Activity.class));
            finish();
        }else {
            ToastUrl.show(this,"请输入接收警告短信的电话号码");
        }


    }

    public void lastPage3(View view) {
        startActivity(new Intent(this, SetUp2Activity.class));
        finish();

    }

    @OnClick(R.id.bt_phone)
    public void onClick() {
        Intent intent = new Intent(this, ContactListActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }
}
