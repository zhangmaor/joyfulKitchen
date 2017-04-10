package com.app.joyfulkitchen.activity.menuchild;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.joyfulkitchen.activity.R;
import com.app.joyfulkitchen.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/6.
 */
public class MenuInformation extends FragmentActivity {
    private List<Message> messageList1 = new ArrayList<Message>();
    private ListView info_list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_infomation);

        getinitView();

        for (int i = 0; i <5; i++) {
            Message m = new Message();
            m.setMenuName("沙拉");
            m.setImg("R.mipmap.aa");

            messageList1.add(m);//上周
            //定义一个界面与数据的混合体,一个item代表一行记录

        }

        /*设置适配器*/
        info_list.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return messageList1.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = null;
                if(convertView==null){
                    Log.i("info:", "没有缓存，重新生成"+position);
                    LayoutInflater inflater = MenuInformation.this.getLayoutInflater();
                    //因为getView()返回的对象，adapter会自动赋给ListView
                    view = inflater.inflate(R.layout.menu_info_list, null);
                }else{
                    Log.i("info:", "有缓存，不需要重新生成"+position);
                    view = convertView;
                }

                /*食材*/
                Message m = messageList1.get(position);
                TextView name= (TextView) view.findViewById(R.id.menuname);
                name.setText(m.getMenuName());
                /*所需食材数量*/
                TextView count = (TextView) view.findViewById(R.id.count);
                count.setText(m.getMenuName());


                return view;
            }
        });
        info_list.setEnabled(false);
        setListViewHeightBasedOnChildren(info_list);
    }

    /*获取控件*/
    public void getinitView(){
        info_list = (ListView) findViewById(R.id.menu_info_list);
    }

    /*计算listview每个Item   用于自适应高度*/
    public void setListViewHeightBasedOnChildren(ListView listView) {

        // 获取ListView对应的Adapter

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {

            return;

        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目

            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(0, 0); // 计算子项View 的宽高

            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        // listView.getDividerHeight()获取子项间分隔符占用的高度

        // params.height最后得到整个ListView完整显示需要的高度

        listView.setLayoutParams(params);

    }
}
