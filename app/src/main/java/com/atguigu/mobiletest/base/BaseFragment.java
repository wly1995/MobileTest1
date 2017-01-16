package com.atguigu.mobiletest.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 万里洋 on 2017/1/6.
 * 作用：基类Fragment（提取所有fragment的共同部分）
 */

public abstract  class BaseFragment extends Fragment {

    //上下文得到
    public Context mContext;

    /**
     * 系统在创建BaseFragment时调用的方法
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    /**
     * 继承它的孩子要实现方法
     * @return
     */
    public abstract  View initView();

    /**
     * 当Activty创建成功的时候回调该方法
     * 初始化数据：
     * 联网请求数据
     * 绑定数据
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     *当子类需要：
     * 1.联网请求网络，的时候重写该方法
     * 2.绑定数据
     */
    public void initData() {

    }

    /**
     *
     * @param hidden false：当前类显示
     *               true:当前类隐藏
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("TAG","onHiddenChanged。。"+this.toString()+",hidden=="+hidden);
        if(!hidden){//这个时候hidden为false的时候才会进来，这时候碎片显示，需要刷新数据
            onRefrshData();
        }

    }



    /**
     * 当子类要刷新数据的时候重写该方法
     */
    public void onRefrshData() {

    }
}
