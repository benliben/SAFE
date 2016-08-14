package com.android.benben.safe.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.benben.safe.R;
import com.android.benben.safe.engine.AddressDao;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LiYuanxiong on 2016/8/11 16:21.
 * Desribe: 号码查询
 */
public class QueryAddressActivity extends BaseActivity {
    @InjectView(R.id.et_query_number)
    EditText mQueryNumber;
    @InjectView(R.id.bt_query)
    Button mQuery;
    @InjectView(R.id.tv_query_result)
    TextView mQueryResult;

    private String mPhoneNumber;

  private   Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mQueryResult.setText(location);
        }
    };
    private String location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_query_address);
        ButterKnife.inject(this);
        initUI();
        /**/
//        AddressDao.getAddress("18280546800");
        /*实时监听*/
        mQueryNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = mQueryNumber.getText().toString();
                query(phone);

            }
        });

    }

    private void initUI() {


    }

    @OnClick(R.id.bt_query)
    public void onClick() {
        mPhoneNumber = mQueryNumber.getText().toString();
        if (!TextUtils.isEmpty(mPhoneNumber)){
            /*查询*/
            query(mPhoneNumber);
        }else {
            /*抖动来提示用户*/
            Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
            //interpolator 插补器  就是数学里面的一个函数
            /*可以自定义插补器*/
            shake.setInterpolator(new Interpolator() {
                //y=ax+b
                @Override
                public float getInterpolation(float input) {
                    return 0;
                }
            });
            mQueryNumber.startAnimation(shake);

            /*手机震动效果*/
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            /*震动毫秒值*/
            vibrator.vibrate(2000);
            /*规律的震动(震动的规则，次数*/
            vibrator.vibrate(new long[]{2000, 5000, 2000, 5000}, 2);

        }


    }

    /**
     * 耗时操作 获取电话号码的
     * @param mPhoneNumber 查询的电话号码
     */
    private void query(final String mPhoneNumber) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                 location= AddressDao.getAddress(mPhoneNumber);
                /*子线程不能更新ui*/
                handler.sendEmptyMessage(0);
            }
        }).start();
    }
}
