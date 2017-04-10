package com.app.joyfulkitchen.util;


import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.joyfulkitchen.activity.R;


public class MyTitleInfoLayout extends LinearLayout {
    /**
     * 返回
     */
    private ImageView titleBack;
    public MyTitleInfoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.my_info_title, this);
        titleBack = (ImageView) findViewById(R.id.title_back);

        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });



    }
}