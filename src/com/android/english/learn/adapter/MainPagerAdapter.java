package com.android.english.learn.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 *
 * 作用：首页面左右滑动的工具类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
	private final Context mContext;
	private final ArrayList<TabInfo> mTabs;

	static final class TabInfo {

		private final Class<?> mClss;
		private final Bundle mArgs;

		TabInfo(Class<?> aClass, Bundle args) {
			mClss = aClass;
			mArgs = args;
		}
	}

	public MainPagerAdapter(FragmentActivity activity) {
		super(activity.getSupportFragmentManager());
		// TODO Auto-generated constructor stub
		mTabs = new ArrayList<TabInfo>();
		mContext = activity;
	}

	@Override
	public int getCount() {
		return mTabs.size();
	}

	@Override
	public Fragment getItem(int position) {
		TabInfo info = mTabs.get(position);
		return Fragment.instantiate(mContext, info.mClss.getName(), info.mArgs);
	}

	public void addTab(Class<?> clss, Bundle args) {
		TabInfo info = new TabInfo(clss, args);
		mTabs.add(info);
		notifyDataSetChanged();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
	}
}
