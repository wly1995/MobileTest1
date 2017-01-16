package com.atguigu.mobiletest.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.mobiletest.R;

/**
 * Created by 万里洋 on 2017/1/16.
 */

public class TitleBarView extends LinearLayout implements View.OnClickListener{
    //这个成员变量的意义就是为了吐丝用的。
    private final Context mContext;
    private TextView tv_search;
    private RelativeLayout rl_game;
    private ImageView iv_record;
    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    /**
     * 视图加载完成时回调的方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //对控件进行初始化
        tv_search = (TextView) getChildAt(1);
        rl_game = (RelativeLayout) getChildAt(2);
        iv_record = (ImageView) getChildAt(3);

        //对控件设置监听
        tv_search.setOnClickListener(this);
        rl_game.setOnClickListener(this);
        iv_record.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
               Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
//                mContext.startActivity(new Intent(mContext, SearchActivity.class));
                break;
            case R.id.rl_game:
                Toast.makeText(mContext, "游戏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_record:
                Toast.makeText(mContext, "记录", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
