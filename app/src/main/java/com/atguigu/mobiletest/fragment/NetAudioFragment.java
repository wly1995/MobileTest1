package com.atguigu.mobiletest.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atguigu.mobiletest.Constant;
import com.atguigu.mobiletest.R;
import com.atguigu.mobiletest.base.BaseFragment;
import com.atguigu.mobiletest.util.CacheUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 万里洋 on 2017/1/6.
 */

public class NetAudioFragment extends BaseFragment {
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;
    @Bind(R.id.tv_nomedia)
    TextView tvNomedia;
    /**
     * 初始化视图的方法
     * @return
     */
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_net_audio, null);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 初始化数据的方法
     */
    @Override
    public void initData() {
        super.initData();
//        textView.setText("网络音频");
        String saveJson = CacheUtils.getString(mContext, Constant.NET_AUDIO_URL);
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }

        getDataFromNet();
    }

    /**
     * 从网络上获取数据
     */
    private void getDataFromNet() {

    }

    /**
     * 解析json数据
     * @param saveJson
     */
    private void processData(String saveJson) {

    }

    @Override
    public void onRefrshData() {
        super.onRefrshData();
//        textView.setText("网络音频刷新");
//        Log.e("TAG","onHiddenChanged。。"+this.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
