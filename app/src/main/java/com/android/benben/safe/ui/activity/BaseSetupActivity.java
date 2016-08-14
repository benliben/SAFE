package com.android.benben.safe.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;



/**
 * Created by LiYuanxiong on 2016/8/10 10:06.
 * Desribe:
 */
public abstract class BaseSetupActivity extends AppCompatActivity {

    private GestureDetector gestureDetector;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**创建手势识别器*/
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getRawX() - e2.getRawX() > 100) {
                    /**下一页，由右向左滑动（）*/
                    showNextPage();
                }
                if (e2.getRawX() - e1.getRawX() > 100) {
                    /**上一页，由左向右滑动（）*/
                    showPrePage();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }
    /**抽象方法，定义跳转到下一页的方法*/
    public abstract void showNextPage();
    /**抽象方法，定义跳转到上一页的方法*/
    public abstract void showPrePage();

    /**1.监听当前activity上的触摸事件（按下（1次），滑动（多次），抬起（1次））*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    /**统一处理每个界面的上一页和下一页的按钮*/
    public void nextPage(View view) {
        /**将跳转到下一个界面的代码逻辑，交由子类实现*/
        showNextPage();
    }

    public void prePage(View view) {
        /**将跳转到下一个界面的代码逻辑，交由子类实现*/
        showPrePage();
    }
}
