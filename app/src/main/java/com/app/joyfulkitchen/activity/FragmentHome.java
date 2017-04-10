package com.app.joyfulkitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.joyfulkitchen.activity.menuchild.MenuCarousel;

import java.util.Timer;
import java.util.TimerTask;

public class FragmentHome extends Fragment {

	/*称转动参数开始*/
	private ImageView needleView;  //指针图片
	private Timer timer;  //时间
	private TextView looktv ;//文本框

	private float degree = 0.0f;  //记录指针旋转

	private float finalWeight = 2500.0f;//最终显示的重量
	private float allWeight = 5000.0f ;

	private float allDegree = 172.0f ;
	private float finaldegree = 0.0f; //记录最终指针的偏转度数
	/*称转动参数结束*/

	/*食物选择*/
	private String food=null;//当前称量的食物
	private TextView changetv ;//显示当前食物tv

	/*顶部搜索框变量*/
	ImageView menu_classfiy;//头部图标
	private LinearLayout home_edt_ly;
	private LinearLayout home_tv_ly;
	private EditText home_search ;//搜索框
	private Button search_btn;//搜索按钮

	private ImageView search_img;//搜索图片


	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.home_fragment, container, false);

		showView();//调用方法，为变量赋值


		menu_classfiy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(getActivity(),MenuCarousel.class);
				startActivity(it);
			}
		});
		/*search_btn点击获取home_search的值*/
		search_btn.setOnClickListener(new HomeClickListener());

		/*home_search_img显示文本框输入值*/
		search_img.setOnClickListener(new HomeClickListener());


		changetv.setText(setChangetv(food));//设置显示


		looktv.setText(finalWeight+"g");//设置显示内容

		finaldegree = finalWeight * allDegree /allWeight ;//设置转动的角度

		// 开始转动
		timer = new Timer();
		// 设置每一秒转动一下
		timer.schedule(new NeedleTask(), 0, 100);

		return view;
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	/***
	 * 为变量赋值
	 */
	public void showView(){

		menu_classfiy = (ImageView) view.findViewById(R.id.home_menu);//头部图标
		home_tv_ly  =(LinearLayout) view.findViewById(R.id.home_tv_ly);//title文本框设置布局
		home_edt_ly =(LinearLayout) view.findViewById(R.id.home_edt_ly);//title文本设置布局
		home_edt_ly.setVisibility(View.GONE);//不可见

		looktv = (TextView) view.findViewById(R.id.home_look_tv);//控件textView显示称量结果
		changetv =(TextView) view.findViewById(R.id.home_change_tv);//控件textView显示当前称量的食物
		home_search = (EditText) view.findViewById(R.id.home_search_edt);//控件EditText搜索当前称量的食物
		search_btn = (Button) view.findViewById(R.id.home_search_btn);//控件Button设置当前称量的食物
		search_img = (ImageView) view.findViewById(R.id.home_search_img);//控件ImageViewsous搜索当前称量的食物
		needleView = (ImageView) view.findViewById(R.id.needle);//指针

	}

	/**
	 * 设置显示当前的食物
	 * */
	public String setChangetv(String food){
		if(food == null || food.equals("")){
			return "你还没选择食物";
		}else {
			return "当前称量的食物是"+food;
		}
	}

	/**
	 * 秤盘转动handler开始
	 */
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {  //设置仪表盘指针转动动画
			//仪表盘最大是172度，这个是自己测出来的
			if (degree >= finaldegree) {
				timer.cancel();
			}
			degree += 2.0f;
			RotateAnimation animation = new RotateAnimation(degree,
					degree, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			animation.setDuration(1000);
			animation.setFillAfter(true);
			needleView.startAnimation(animation);
		}
	};

	private class NeedleTask extends TimerTask {
		@Override
		public void run() {
			handler.sendEmptyMessage(0);
		}
	}
	/**
	 * 秤盘转动handler结束
	 */

	/**
	 * 按钮点击事件
	 */
	class HomeClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.home_search_btn:
					String search_name = home_search.getText().toString();
					food = search_name ;
					changetv.setText(setChangetv(food));//设置显示
					break;
				case R.id.home_search_img:
					home_edt_ly.setVisibility(View.VISIBLE);
					home_tv_ly.setVisibility(View.GONE);
					break;
				default:
					break;
			}
		}
	}

}
