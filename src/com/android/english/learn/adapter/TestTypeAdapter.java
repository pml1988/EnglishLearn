package com.android.english.learn.adapter;

import com.android.english.learn.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 *
 * 作用：测试类型的适配器
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class TestTypeAdapter extends BaseAdapter{
	private Context mContext;
	private String[] typeArray;
	public TestTypeAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext=context;
		typeArray=mContext.getResources().getStringArray(R.array.test_type_array);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return typeArray.length;
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return typeArray[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=LayoutInflater.from(mContext).inflate(R.layout.type_select_item, null);
		TextView tv=(TextView) view.findViewById(R.id.type_select_tv);
		tv.setText(getItem(position));
		return view;
	}

}
