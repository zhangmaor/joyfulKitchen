package com.app.joyfulkitchen.activity.mychild;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.app.joyfulkitchen.activity.R;

public class MySettingActivity extends Activity implements OnClickListener  {

    /**
     * 返回
     */
    private ImageView title_back;

    /**
     * 个人资料
     */
    private LinearLayout profile_linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity_setting);
        title_back = (ImageView) findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        profile_linearLayout = (LinearLayout) findViewById(R.id.profile_linearLayout);
        profile_linearLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                this.finish();
                break;
            case R.id.profile_linearLayout:
                Intent intent = new Intent(this,MyInformationActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
