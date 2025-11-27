package com.android.english.learn.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.english.learn.R;
import com.android.english.learn.base.BaseActivity;
import com.android.english.learn.datasource.DataSource;
import com.android.english.learn.model.LearingWordData;
import com.android.english.learn.model.WordData;

/**
 *
 * 作用：查看单词加载类（作用是加载单词）
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class ViewWordLoadingActivity extends BaseActivity {
	private TextView mStartLearnTv;
	private DataSource ds;
	private List<WordData> mWordData;//单词列表
	private long cid;//词汇表ID

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		initView();
		initData();
		setListener();
	}

	private void initView() {
		mStartLearnTv = (TextView) findViewById(R.id.view_word_bottom_tv);
	}

	private void initData() {
		mWordData = new ArrayList<WordData>();
		cid = getIntent().getIntExtra("cid", 1);

		mStartLearnTv.setVisibility(View.GONE);
	}

	private void setListener() {
		mStartLearnTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent it=new Intent(ViewWordLoadingActivity.this, ViewWordActivity.class);
//				it.put

			}
		});
	}

	class getWordTask extends AsyncTask<Void, Void, Void> {
		private LearingWordData data;

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			mWordData = ds.getDB().getAllWordDataList(cid);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mHandler.sendEmptyMessage(1);
		}

	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if (mWordData != null) {
					mStartLearnTv.setVisibility(View.VISIBLE);
				}else {

				}

				break;

			default:
				break;
			}
		};
	};

	@Override
	public void setActivityContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.view_word_loading);
	}

	@Override
	public String getBaseTitle() {
		// TODO Auto-generated method stub
		return null;
	}

}
