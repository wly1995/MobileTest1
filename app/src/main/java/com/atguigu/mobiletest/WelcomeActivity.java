package com.atguigu.mobiletest;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class WelcomeActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        /**
         * 既是说，这个开启的runnable会在这个handler所依附线程中运行
         */
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                launchActivity();
            }
        }, 2000);
    }

    private void launchActivity() {
        //跳转页面
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        //关闭当前页面
        finish();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        launchActivity();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在销毁activity之前先清除所有的消息
        handler.removeCallbacksAndMessages(null);
    }
}
