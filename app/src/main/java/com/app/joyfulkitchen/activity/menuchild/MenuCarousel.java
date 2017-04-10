package com.app.joyfulkitchen.activity.menuchild;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.app.joyfulkitchen.activity.R;

public class MenuCarousel extends Activity {
    private GridView gview,gview01,gview02,gview03,gview04;
    private List<Map<String, Object>> data_list,data_list01;
    private SimpleAdapter sim_adapter,sim_adapter01;
    private TextView text;
    // 图片封装为一个数组
    private int[] icon = { R.mipmap.muent_meat, R.mipmap.muent_meat01, R.mipmap.muent_meat, R.mipmap.muent_meat,
            R.mipmap.muent_meat, R.mipmap.muent_meat, R.mipmap.muent_meat, R.mipmap.muent_meat,
    };
    private String[] iconName = { "通讯录", "日历", "照相机", "时钟", "游戏", "短信", "铃声",
            "设置"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_classify);
        gview = (GridView) findViewById(R.id.gview);
        gview02 = (GridView) findViewById(R.id.gview02);
        gview03 = (GridView) findViewById(R.id.gview03);
        gview04 = (GridView) findViewById(R.id.gview04);



        //可以用了
//        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView text = (TextView) view.findViewById(R.id.text);
//                String str =  text.getText().toString();
//                Toast.makeText(MainActivity.this, ""+str, Toast.LENGTH_SHORT).show();
//            }
//        });



        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();

        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.meun_image01,R.id.menu_text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.menu_centre, from, to);
        // sim_adapter01 = new SimpleAdapter(this, data_list01, R.layout.item, from, to);
        //配置适配器

        gview.setAdapter(sim_adapter);
        gview02.setAdapter(sim_adapter);
        gview03.setAdapter(sim_adapter);
        gview04.setAdapter(sim_adapter);








    }





    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);


        }

        return data_list;
    }

/*    @Override
    public void onMeasureq(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }*/


    /*protected void GridView1_SelectedIndexChanged(object sender, EventArgs e)
    {
        int index = gview.SelectedIndex;//获得行号
        string s1 = GridView1.Rows[index].Cells[0].Text;//第一列数据
        string s2 = GridView1.Rows[index].Cells[1].Text;//第二列数据
        Response.Redirect("b.aspx?s1="+s1+"&s2="+s2);//调用b.aspx
    }*/







}

