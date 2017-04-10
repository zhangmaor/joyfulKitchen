package com.app.joyfulkitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.joyfulkitchen.activity.menuchild.Newest;
import com.app.joyfulkitchen.model.Title;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class FragmentMenu extends Fragment {

	private View view;

	private Button menu_btn;//导航栏点击搜索按钮

	private ViewPager viewPager; // android-support-v4中的滑动组件
	private List<ImageView> imageViews; // 滑动的图片集合
	private String[] titles; // 图片标题
	private int[] imageResId; // 图片ID
	private List<View> dots; // 图片标题正文的那些点

	private TextView tv_title;
	private int currentItem = 0; // 当前图片的索引号

	private ScheduledExecutorService scheduledExecutorService;

	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		}

		;
	};

	//****************用list模拟时间轴，***************************************
	private TextView title, text;  //标题   和 介绍详情
	private ListView lv;//fra_menu011.xml
	private BaseAdapter adapter;//适配器
	private List<Title> titleList = new ArrayList<Title>();//保持数据

	//*********************************************************************
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fra_menu, container, false);


		/*******************************    导航栏点击搜索，跳转最新推荐   *****************************/
		menu_btn = (Button)view.findViewById(R.id.menu_btn);
		menu_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(getActivity(),new Newest().getClass());
				//getActivity().startActivity(intent);
				Intent intent = new Intent(getActivity(),new Newest().getClass());
				getActivity().startActivity(intent);

			}
		});


		//*********************    找到LIstview 模拟时间轴  **************************
		lv = (ListView) view.findViewById(R.id.display_listview);
		//模拟数据库
		for (int i = 0; i < 30; i++) {
			Title tl = new Title();
			tl.setTitle("口味鸡" + i);
			tl.setText("很好吃的哦，用了" + i + "只鸡，经过九九八十一天炼成");
			titleList.add(tl);
		}

		//设置适配器
		adapter = new BaseAdapter() {
			@Override
			public int getCount() {
				return titleList.size();
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
				LayoutInflater inflater = LayoutInflater.from(getActivity());
				View view;
				if (convertView == null) {
					view = inflater.inflate(R.layout.menu_display_describe, null);
				} else {
					view = convertView;
					Log.i("info", "有缓存，不需要重新生成" + position);
				}
				title = (TextView) view.findViewById(R.id.describe_text_Title);
				text = (TextView) view.findViewById(R.id.describe_text_introduce);

				title.setText(titleList.get(position).getTitle());
				text.setText(titleList.get(position).getText());
				return view;
			}
		};
		lv.setAdapter(adapter);


		//****************************************************************

		imageResId = new int[]{R.mipmap.muent_meat01, R.mipmap.muent_meat, R.mipmap.muent_meat01, R.mipmap.muent_meat, R.mipmap.muent_meat01};
		titles = new String[imageResId.length];
		titles[0] = "巩俐不低俗，我就不能低俗";
		titles[1] = "扑树又回来啦！再唱经典老歌引万人大合唱";
		titles[2] = "揭秘北京电影如何升级";
		titles[3] = "乐视网TV版大派送";
		titles[4] = "热血屌丝的反杀";

		imageViews = new ArrayList<ImageView>();

		// 初始化图片资源
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(this.getContext());
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}


		dots = new ArrayList<View>();
		dots.add(view.findViewById(R.id.v_dot0));
		dots.add(view.findViewById(R.id.v_dot1));
		dots.add(view.findViewById(R.id.v_dot2));
		dots.add(view.findViewById(R.id.v_dot3));
		dots.add(view.findViewById(R.id.v_dot4));

		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_title.setText(titles[0]);//

		viewPager = (ViewPager) view.findViewById(R.id.vp);
		viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.setOnPageChangeListener(new MyPageChangeListener());

		return view ;
	}
	@Override
	public void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	public void onStop() {
		// 当Activity不可见的时候停止切换
		scheduledExecutorService.shutdown();
		super.onStop();
	}

	/**
	 * 换行切换任务
	 *
	 * @author Administrator
	 *
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}

	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 *
	 * @author Administrator
	 *
	 */
	private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			tv_title.setText(titles[position]);
			dots.get(oldPosition).setBackgroundResource(R.drawable.menu_dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.menu_dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 填充ViewPager页面的适配器
	 *
	 * @author Administrator
	 *
	 */
	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageResId.length;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(imageViews.get(arg1));
			return imageViews.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}
}
