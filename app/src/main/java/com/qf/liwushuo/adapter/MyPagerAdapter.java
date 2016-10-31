package com.qf.liwushuo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

	List<View> list;
	
	public MyPagerAdapter(List<View> list) {
		super();
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(list.get(position));
		return list.get(position);
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(list.get(position));
	}

}
