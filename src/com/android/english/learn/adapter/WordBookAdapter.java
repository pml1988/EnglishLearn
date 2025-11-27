package com.android.english.learn.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.english.learn.R;
import com.android.english.learn.model.WordBookData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 *
 * 作用：单词词汇表的适配器
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class WordBookAdapter extends BaseAdapter {
	private Context mContext;
	private List<WordBookData> mData;

	public WordBookAdapter(Context context) {
		mContext = context;
		mData = new ArrayList<WordBookData>();
	}

	public void refreshList(List<WordBookData> data) {
		mData.clear();
		mData.addAll(data);
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public WordBookData getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(mContext).inflate(R.layout.word_book_item, null);
		TextView title_tv = (TextView) view.findViewById(R.id.word_book_item_title_tv);
		title_tv.setText(getItem(position).getTitle());
		return view;
	}

}
