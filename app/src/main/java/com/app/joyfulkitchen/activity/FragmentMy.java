package com.app.joyfulkitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View.OnClickListener;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.joyfulkitchen.activity.mychild.MyInformationActivity;
import com.app.joyfulkitchen.activity.mychild.MyModifyActivity;


public class FragmentMy extends Fragment implements OnClickListener {


	/**
	 * 修改资料
	 */
	private LinearLayout modify_data;

	/**
	 * 个人信息
	 */
	private LinearLayout info;

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.my_fragment, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		info = (LinearLayout) getActivity().findViewById(R.id.info);
		modify_data = (LinearLayout) getActivity().findViewById(R.id.modify_data);

		modify_data.setOnClickListener(this);
		info.setOnClickListener(this);

	}





	@Override
	public void onClick(View v) {

		switch (v.getId()){
			case R.id.modify_data:
				Intent intent = new Intent(getActivity(),MyInformationActivity.class);
				startActivity(intent);
				break;
			case R.id.info:
				Intent intentInfo = new Intent(getActivity(),MyModifyActivity.class);
				startActivity(intentInfo);
			default:
				break;
		}
	}
}
