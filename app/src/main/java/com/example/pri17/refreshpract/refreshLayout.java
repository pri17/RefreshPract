package com.example.pri17.refreshpract;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by pri17 on 2016/12/22.
 */

public class refreshLayout extends PtrFrameLayout {
    public refreshHeader header;

    public refreshLayout(Context context){
        super(context);
        initView();
    }
    public refreshLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        initView();
    }
    public refreshLayout(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        initView();
    }

    private void initView(){
        header = new refreshHeader(getContext());
        setHeaderView(header);
        addPtrUIHandler(header);
    }
}
