package com.atguigu.mobiletest.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atguigu.mobiletest.Constant;
import com.atguigu.mobiletest.R;
import com.atguigu.mobiletest.adapter.RecyclerViewAdpater;
import com.atguigu.mobiletest.base.BaseFragment;
import com.atguigu.mobiletest.bean.NetAudioBean;
import com.atguigu.mobiletest.util.CacheUtils;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
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

public class RecyclerViewFragment extends BaseFragment {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;
    @Bind(R.id.tv_nomedia)
    TextView tvNomedia;
    @Bind(R.id.refresh)
    MaterialRefreshLayout refreshLayout;
    private RecyclerViewAdpater myAdapter;
    private List<NetAudioBean.ListBean> datas;

    /**
     * 初始化视图的方法
     * @return
     */
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_recyclerview, null);
        ButterKnife.bind(this, view);
        refreshLayout.setMaterialRefreshListener(new MyMaterialRefreshListener());
        return view;
    }
    private boolean isLoadMore = false;
    class MyMaterialRefreshListener extends MaterialRefreshListener {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
//            Toast.makeText(mContext,"下拉刷新",Toast.LENGTH_SHORT).show();
            //下拉刷新的时候需要重新从网络中获取数据
            isLoadMore = false;
            getDataFromNet();
        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);
//            Toast.makeText(mContext,"上拉刷新",Toast.LENGTH_SHORT).show();
            //上拉同样需要获取数据
            isLoadMore = true;
            getDataFromNet();
        }
    }

    /**
     * 初始化数据的方法
     */
    @Override
    public void initData() {
        super.initData();
        String saveJson = CacheUtils.getString(mContext, Constant.NET_AUDIO_URL);
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }

        getDataFromNet();
//        textView.setText("网络视频");
    }
    @Override
    public void onRefrshData() {
        super.onRefrshData();
//        textView.setText("网络视频刷新");
//        Log.e("TAG","onHiddenChanged。。"+this.toString());
    }

    private void getDataFromNet() {
        RequestParams reques = new RequestParams(Constant.NET_AUDIO_URL);
        x.http().get(reques, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                CacheUtils.putString(mContext,Constant.NET_AUDIO_URL,result);
                LogUtil.e("onSuccess==" + result);
                processData(result);
                if (!isLoadMore) {
                    //在请求数据成功后刷新就完成
                    refreshLayout.finishRefresh();//下拉刷新完成（下拉圈会隐藏）
                }else {

                    refreshLayout.finishRefreshLoadMore();//上拉刷新完成（上拉圈会消失）
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("onError==" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("onCancelled==" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished==");
            }
        });

    }

    /**
     * 解析json数据
     * @param result
     */
    private void processData(String result) {
        if (!isLoadMore) {
            NetAudioBean netAudioBean = paraseJons(result);
            LogUtil.e(netAudioBean.getList().get(0).getText()+"-----------");

            datas = netAudioBean.getList();

            if(datas != null && datas.size() >0){
                //有视频
                tvNomedia.setVisibility(View.GONE);
                //设置适配器
                myAdapter = new RecyclerViewAdpater(mContext,datas);
                recyclerview.setAdapter(myAdapter);

                //添加布局管理器
                recyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
            } else{
                //没有视频
                tvNomedia.setVisibility(View.VISIBLE);
            }

            progressbar.setVisibility(View.GONE);
        }else {
            //加载更多
            datas.addAll(paraseJons(result).getList());//上拉刷新就是
            //适配器要刷新
            myAdapter.notifyDataSetChanged();//会重新执行getCotent--->getView
        }


    }

    /**
     * json解析数据
     * @param json
     * @return
     */
    private NetAudioBean paraseJons(String json) {
        return new Gson().fromJson(json,NetAudioBean.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
