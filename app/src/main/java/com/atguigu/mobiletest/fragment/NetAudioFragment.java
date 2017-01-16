package com.atguigu.mobiletest.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atguigu.mobiletest.Constant;
import com.atguigu.mobiletest.R;
import com.atguigu.mobiletest.ShowImageAndGifActivity;
import com.atguigu.mobiletest.adapter.NetAudioFragmentAdapter;
import com.atguigu.mobiletest.base.BaseFragment;
import com.atguigu.mobiletest.bean.NetAudioBean;
import com.atguigu.mobiletest.util.CacheUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

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
    private List<NetAudioBean.ListBean> datas;
    private NetAudioFragmentAdapter myAdapter;

    /**
     * 初始化视图的方法
     * @return
     */
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_net_audio, null);
        ButterKnife.bind(this, view);
        //设置点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                NetAudioBean.ListBean listEntity = datas.get(position);
                if(listEntity !=null ){
                    //3.传递视频列表
                    Intent intent = new Intent(mContext,ShowImageAndGifActivity.class);
                    if(listEntity.getType().equals("gif")){
                        String url = listEntity.getGif().getImages().get(0);
                        intent.putExtra("url",url);
                        mContext.startActivity(intent);
                    }else if(listEntity.getType().equals("image")){
                        String url = listEntity.getImage().getBig().get(0);
                        intent.putExtra("url",url);
                        mContext.startActivity(intent);
                    }
                }


            }
        });
        return view;
    }

    /**
     * 初始化数据的方法
     */
    @Override
    public void initData() {

//        textView.setText("网络音频");
        //先从缓存中读取，如果联网成功后会在网络上读取，覆盖本地的数据
        String saveJson = CacheUtils.getString(mContext, Constant.NET_AUDIO_URL);
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }

        getDataFromNet();
        super.initData();
    }

    /**
     * 从网络上获取数据 用xutil进行网络请求
     */
    private void getDataFromNet() {
        RequestParams reques = new RequestParams(Constant.NET_AUDIO_URL);
        x.http().get(reques, new Callback.CommonCallback<String>() {
            /**
             * 请求成功的时候调用
             * @param result
             */
            @Override
            public void onSuccess(String result) {
                Log.e("TAG","result = "+result);
                //进行缓存
                CacheUtils.putString(mContext,Constant.NET_AUDIO_URL,result);

                processData(result);

            }

            /**
             * 请求错误的时候
             * @param ex
             * @param isOnCallback
             */
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 解析json数据
     * @param result
     */
    private void processData(String result) {
        NetAudioBean netAudioBean = paraseJons(result);
        LogUtil.e(netAudioBean.getList().get(0).getText()+"-----------");

        datas = netAudioBean.getList();

        if(datas != null && datas.size() >0){
            //有视频
            tvNomedia.setVisibility(View.GONE);
            //设置适配器
            myAdapter = new NetAudioFragmentAdapter(mContext,datas);
            listview.setAdapter(myAdapter);
        } else{
            //没有视频
            tvNomedia.setVisibility(View.VISIBLE);
        }

        progressbar.setVisibility(View.GONE);

    }

    /**
     * json解析数据
     * @param json 用Gson进行解析将json解析 封装到bean类中
     * @return
     */
    private NetAudioBean paraseJons(String json) {
        return new Gson().fromJson(json,NetAudioBean.class);
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
