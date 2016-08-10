package com.android.benben.safe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
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
 * Desribe:设置安全号码界面
 */
public class SetUp3Activity extends BaseSetupActivity {
    private static final String TAG = "lyx";
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

    @Override
    public void showNextPage() {
        phoneNumber = mPhoneNumber.getText().toString();
        if (!TextUtils.isEmpty(phoneNumber)) {
            SpUtil.putString(this, ConstantValue.PHONE_NUMBER, phoneNumber);
            startActivity(new Intent(this, SetUp4Activity.class));
            finish();
            overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);

        } else {
            ToastUrl.show(this, "请输入接收警告短信的电话号码");
        }

    }

    @Override
    public void showPrePage() {
        startActivity(new Intent(this, SetUp2Activity.class));
        finish();

        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);

    }

    private void initUI() {
        String number = SpUtil.getString(this, ConstantValue.PHONE_NUMBER, "");
        if (!TextUtils.isEmpty(number)) {
            mPhoneNumber.setText(number);
        }

    }


    @OnClick(R.id.bt_phone)
    public void onClick() {
        Intent intent = new Intent(this, ContactListActivity.class);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            /*1.返回到当前界面的时候，接收结果的方法*/
            String phone = data.getStringExtra("phone");
            /*2.将特殊字符过滤掉(中划线转换成空字符串)*/
            phone = phone.replace("-", "").replace(" ", "").trim();
            mPhoneNumber.setText(phone);

            /*3.存储联系人到sp中*/
            SpUtil.putString(this, ConstantValue.PHONE_NUMBER, phone);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
