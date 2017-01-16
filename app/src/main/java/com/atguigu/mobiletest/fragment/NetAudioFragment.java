package com.atguigu.mobiletest.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.mobiletest.base.BaseFragment;

/**
 * Created by 万里洋 on 2017/1/6.
 */

public class NetAudioFragment extends BaseFragment {
    private TextView textView;

    /**
     * 初始化视图的方法
     * @return
     */
    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setTextColor(Color.GRAY);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        return textView;
    }

    /**
     * 初始化数据的方法
     */
    @Override
    public void initData() {
        super.initData();
        textView.setText("网络音频");
    }
    @Override
    public void onRefrshData() {
        super.onRefrshData();
        textView.setText("网络音频刷新");
//        Log.e("TAG","onHiddenChanged。。"+this.toString());
    }
}
