package com.android.english.learn.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.english.learn.R;
import com.android.english.learn.model.WordData;
import com.android.english.learn.model.WordScoreData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 *
 * 作用：查看单词列表的简单适配器
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class ViewWordScoreAdapter extends BaseAdapter{
	private Context mContext;
	private List<WordScoreData> mList;//单词分数模型列表

	public ViewWordScoreAdapter(Context context) {
		mContext = context;
		mList = new ArrayList<WordScoreData>();
	}

	public void RefreshList(List<WordScoreData> data) {
		mList.clear();
		mList.addAll(data);
		this.notifyDataSetChanged();
	}

//	public void RefreshList(List<WordScoreData> data) {
//
//	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public WordData getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(mContext).inflate(R.layout.page_words_0, null);
		TextView wordTv = (TextView) view.findViewById(R.id.page_words_word);
		TextView baseTv = (TextView) view.findViewById(R.id.page_words_base);
		WordData wd = getItem(position);
		wordTv.setText(wd.getWord());
		baseTv.setText(wd.getBase());
		return view;
	}

}
