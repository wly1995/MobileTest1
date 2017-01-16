package com.atguigu.mobiletest;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.atguigu.mobiletest.base.BaseFragment;
import com.atguigu.mobiletest.fragment.LocalAudioFragment;
import com.atguigu.mobiletest.fragment.LocalVideoFragment;
import com.atguigu.mobiletest.fragment.NetAudioFragment;
import com.atguigu.mobiletest.fragment.RecyclerViewFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rg_main;
    //放碎片的集合
    private ArrayList<BaseFragment> fragments;
    /**
     * 记录的碎片的索引
     */
    private int position;
    /**
     * 记录缓存的碎片
     */
    private Fragment tempfragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        isGrantExternalRW(this);
        //初始化碎片
        initFragment();

        //设置RadioGroup的监听
        initListener();
    }

    private void initListener() {
        //设置RadioGroup选中状态变化的监听
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("TAG","1111111");
                Log.e("TAG","onCheckedChanged");
                switch (checkedId) {
                    case R.id.rb_local_video:
                        position = 0;
                        break;
                    case R.id.rb_local_audio:
                        position = 1;
                        break;
                    case R.id.rb_net_audio:
                        position = 2;
                        break;
                    case R.id.rb_net_video:
                        position = 3;
                        break;
                }
                //得到当前的碎片
                Fragment currFragment = fragments.get(position);
                //切换碎片的方法
                switchFragment(currFragment);
            }
        });

        //默认选中本地视频
        rg_main.check(R.id.rb_local_video);//onCheckedChanged
    }
    private void switchFragment(Fragment currFragment) {
        if (tempfragment != currFragment) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (currFragment != null) {
                //是否添加过
                if (!currFragment.isAdded()) {
                    //先添加
                    ft.add(R.id.fl_mainc_content,currFragment);
                    //把之前缓存的隐藏
                    if (tempfragment != null) {
                        ft.hide(tempfragment);
                    }
                } else {
                    //把之前缓存的隐藏
                    if (tempfragment != null) {
                        ft.hide(tempfragment);
                    }
                    //添加了就直接显示
                    ft.show(currFragment);
                }
                //提交事务
                ft.commit();
            }
        }
        tempfragment = currFragment;
        Log.e("TAG",tempfragment.toString()+currFragment.toString());
    }
    /**
     * 往集合中放碎片
     */
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new LocalVideoFragment());//本地视频
        fragments.add(new LocalAudioFragment());//本地音乐
        fragments.add(new NetAudioFragment());//网络音乐
        fragments.add(new RecyclerViewFragment());//网络视频
    }
    /**
     * 解决安卓6.0以上版本不能读取外部存储权限的问题
     * @param activity
     * @return
     */
    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

            return false;
        }

        return true;
    }
    private boolean isExit = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==KeyEvent.KEYCODE_BACK){
            if(position !=0){//先按一次退到首页
                //选中首页
                rg_main.check(R.id.rb_local_video);
                return true;//true代表消费这个事件
            }else if(!isExit){
                isExit = true;
                Toast.makeText(this, "再按一次推出", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //如果在这个两秒钟过了的话 isExit 又变为false，即又可以进来else if
                        //如果在两秒钟之内按下返回键的话，isExit = true//则不能再次进入else if
                        isExit = false;
                    }
                }, 2000);
                return true;//true代表消费这个事件
            }
        }
        return super.onKeyDown(keyCode, event);//这个时候返回的是系统的
    }
}
