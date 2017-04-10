package com.app.joyfulkitchen.util;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.joyfulkitchen.activity.mychild.MySettingActivity;
import com.app.joyfulkitchen.activity.R;

public class MyTitleLayout extends LinearLayout {
    /**
     * 返回
     */
    private ImageView setting;
    public MyTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.my_title, this);

        setting = (ImageView) findViewById(R.id.setting);


        setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((Activity)getContext(), MySettingActivity.class);
                ((Activity)getContext()).startActivity(intent);
            }
        });

    }
}