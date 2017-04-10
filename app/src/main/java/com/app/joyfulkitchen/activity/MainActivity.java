package com.app.joyfulkitchen.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class MainActivity extends FragmentActivity {

    private FragmentManager manager;
    private FragmentTransaction trans;
    private RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();

        trans = manager.beginTransaction();
        FragmentHome one = new FragmentHome();
        trans.add(R.id.frame, one, "frag");
        trans.commit();


        rg = (RadioGroup)findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.r1:
                        trans = manager.beginTransaction();
                        trans.replace(R.id.frame, new FragmentHome(), "frag");
                        //trans.addToBackStack("back");
                        trans.commit();
                        break;
                    case R.id.r2:
                        trans = manager.beginTransaction();
                        trans.replace(R.id.frame, new FragmentMenu(), "frag");
                        //trans.addToBackStack("back");
                        trans.commit();
                        break;
                    case R.id.r3:
                        trans = manager.beginTransaction();
                        trans.replace(R.id.frame, new FragmentThree(), "frag");
                        //trans.addToBackStack("back");
                        trans.commit();
                        break;
                    case R.id.r4:
                        trans = manager.beginTransaction();
                        trans.replace(R.id.frame, new FragmentMy(), "frag");
                        //trans.addToBackStack("back");
                        trans.commit();
                        break;
                }
            }
        });
    }
}
