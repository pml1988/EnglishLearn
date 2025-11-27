package com.android.english.learn.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.android.english.learn.R;
import com.android.english.learn.adapter.ViewWordAdapter;
import com.android.english.learn.adapter.ViewWordScoreAdapter;
import com.android.english.learn.datasource.DataSource;
import com.android.english.learn.model.UserWordData;
import com.android.english.learn.model.WordData;
import com.android.english.learn.model.WordScoreData;
import com.android.english.learn.utils.Loger;
import com.android.english.learn.view.MainActivity;
import com.android.english.learn.view.ViewWordScoreActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
/**
 *
 * 作用：主页里面的右侧"学习"
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class PersonFragment extends Fragment {
	private ViewWordScoreAdapter mViewWordAdapter;//列表的适配器
	private List<WordScoreData> mWrongWrodScoreData;//单词分数列表
	private TextView study_des_empty_tv;//列表为空的描述
	private LinearLayout study_data_ll;//学习布局
	private ListView study_listview;//学习列表
	private TextView show_or_hide_corrent_tv;
	private MainActivity mMainActivity;//父Activity
	private DataSource ds;//DB引用
	private List<UserWordData> mAllUserWordData;//所有学习单词
	private List<UserWordData> mWrongUserWordData;//错误学习单词

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.study_layout, container, false);
		initView(view);
		initData();
		setListener();
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mMainActivity == null) {
			mMainActivity = (MainActivity) getActivity();
		}
		mMainActivity.showLoadingDialog();
		if(mAllUserWordData!=null) {
			mAllUserWordData.clear();
		}
		if(mWrongUserWordData!=null) {
			mWrongUserWordData.clear();
		}
		if(mWrongWrodScoreData!=null) {
			mWrongWrodScoreData.clear();
		}
		new Thread() {
			public void run() {
				if (ds == null) {
					ds = new DataSource(getActivity());
				}
				mAllUserWordData = ds.getUserWordListData();
//				Loger.d("the mAll is:" + mAllUserWordData.size());
				if (mAllUserWordData == null || mAllUserWordData.size() == 0) {
					mHandler.sendEmptyMessage(2);
				} else {
					int size = mAllUserWordData.size();
					for (int i = 0;i < size;i++) {
						UserWordData data = mAllUserWordData.get(i);
						if ((data.getTimes() * 10 + 20) != data.getDegree()) {
							mWrongUserWordData.add(data);
						}
					}
					Loger.d("the mWrong is:" + mWrongUserWordData.size());
					if (mWrongUserWordData != null && mWrongUserWordData.size() != 0) {
						mWrongWrodScoreData = ds.getWordScoreData(mWrongUserWordData);
						Collections.sort(mWrongWrodScoreData, new ScoreCompare());
						mHandler.sendEmptyMessage(1);
					} else {
						mHandler.sendEmptyMessage(2);
					}

				}
			};
		}.start();
	}

	private class ScoreCompare implements Comparator {

		@Override
		public int compare(Object lhs, Object rhs) {
			// TODO Auto-generated method stub
			WordScoreData data1 = (WordScoreData) lhs;
			WordScoreData data2 = (WordScoreData) rhs;
			return data1.getScore() > data1.getScore() ? 1 : -1;
		}

	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Loger.d("tehe is:"+mWrongWrodScoreData.size());
				if (mWrongWrodScoreData == null || mWrongWrodScoreData.size() == 0) {
					showEmptyText();
				} else {
					mMainActivity.dismissLoadingDialog();
					mViewWordAdapter.RefreshList(mWrongWrodScoreData);
				}
				break;
			case 2:
				showEmptyText();
				break;
			default:
				break;
			}
		};
	};

	private void showEmptyText() {
		mMainActivity.dismissLoadingDialog();
		study_des_empty_tv.setVisibility(View.VISIBLE);
		study_data_ll.setVisibility(View.GONE);
	}

	private void initView(View view) {
		study_des_empty_tv = (TextView) view.findViewById(R.id.study_des_empty_tv);
		study_data_ll = (LinearLayout) view.findViewById(R.id.study_data_ll);
		study_listview = (ListView) view.findViewById(R.id.study_listview);
		show_or_hide_corrent_tv = (TextView) view.findViewById(R.id.show_or_hide_corrent_tv);
	}

	private void initData() {
		mMainActivity = (MainActivity) getActivity();
		ds = new DataSource(getActivity());
		mViewWordAdapter = new ViewWordScoreAdapter(getActivity());
		study_listview.setAdapter(mViewWordAdapter);
		mAllUserWordData = new ArrayList<UserWordData>();
		mWrongUserWordData = new ArrayList<UserWordData>();
		mWrongWrodScoreData = new ArrayList<WordScoreData>();
	}

	private void setListener() {
		study_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Intent it = new Intent(getActivity(), ViewWordScoreActivity.class);
				it.putExtra("word", mWrongWrodScoreData.get(arg2));
				getActivity().startActivity(it);
			}
		});
	}
}
