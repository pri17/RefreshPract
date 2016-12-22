package com.example.pri17.refreshpract;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by pri17 on 2016/12/19.
 */

public class refreshHeader extends FrameLayout implements PtrUIHandler {


    private TextView mRemainder;//提醒文本
    private ImageView expressMan;//快递员图片
    private ImageView packImage;//盒子图片
    private int state;//记录状态
//状态值
    public static final int STATE_RESET = -1;
    public static final int STATE_PREPARE = 0;
    public static final int STATE_BEGIN = 1;
    public static final int STATE_INISH = 2;

    public static final int MARGIN_RIGHT = 100;

//动画
    private AnimationDrawable mAnimation;

    public refreshHeader(Context context){
        super(context);
        initView();
    }

    public refreshHeader(Context contect, AttributeSet attrs){
        super(contect,attrs);
        initView();
    }

    public refreshHeader(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
        initView();
    }
    //初始化View
    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.my_activity,this,false);
        mRemainder = (TextView)view.findViewById(R.id.remain);
        expressMan = (ImageView)view.findViewById(R.id.iv_man);
        packImage = (ImageView)view.findViewById(R.id.iv_package);
        addView(view);
    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {
        state = STATE_RESET;
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        state = STATE_BEGIN;
        //隐藏刚开始两幅图，开始跑步动画
        packImage.setVisibility(View.GONE);
        expressMan.setBackgroundResource(R.drawable.animation_list);//包含所有图片的动画列表
        mAnimation = (AnimationDrawable) expressMan.getBackground();
        if(!mAnimation.isRunning())
            mAnimation.start();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        state = STATE_INISH;
        packImage.setVisibility(View.VISIBLE);
        //停止动画
        if(mAnimation.isRunning())
            mAnimation.stop();
        expressMan.setBackgroundResource(R.drawable.step1);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        //处理提醒字体
        switch (state) {
            case STATE_PREPARE:
                expressMan.setAlpha(ptrIndicator.getCurrentPercent());//设置透明度
                packImage.setAlpha(ptrIndicator.getCurrentPercent());
                FrameLayout.LayoutParams expressmanLayoutParams = (LayoutParams) expressMan.getLayoutParams();//获取子视图
                if (ptrIndicator.getCurrentPercent() <=1){
                    expressMan.setScaleX(ptrIndicator.getCurrentPercent());
                    expressMan.setScaleY(ptrIndicator.getCurrentPercent());
                    packImage.setScaleY(ptrIndicator.getCurrentPercent());
                    packImage.setScaleX(ptrIndicator.getCurrentPercent());
                    int maginRight = (int) (MARGIN_RIGHT*(1- ptrIndicator.getCurrentPercent()));
                    expressmanLayoutParams.setMargins(0,0,MARGIN_RIGHT,0);
                    expressMan.setLayoutParams(expressmanLayoutParams);
                }
                if (ptrIndicator.getCurrentPercent()<1.2){
                    mRemainder.setText("下拉刷新");
                }else{
                    mRemainder.setText("松开刷新");
                }
                break;
            case STATE_INISH:
                mRemainder.setText("加载完成！");
                break;
            case STATE_BEGIN:
                mRemainder.setText("加载中...");
        }
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        state = STATE_PREPARE;
    }

}
