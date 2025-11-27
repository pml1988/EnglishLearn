package com.android.english.learn.view;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.english.learn.LearnApp;
import com.android.english.learn.R;
import com.android.english.learn.adapter.WordBookAdapter;
import com.android.english.learn.base.BaseActivity;
import com.android.english.learn.datasource.DataSource;
import com.android.english.learn.model.WordBookData;

/**
 *
 * 作用：词汇表 视图类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class WordBookLibrayActivity extends BaseActivity {
	private ListView mWordBookListView;
	private List<WordBookData> mWordBookList;
	private WordBookAdapter mWordBookAdapter;
	private getWordBookLibraryTask getLibraryTask;
	private long current_cid;//当前词汇表ID
	private long selected_id;//选中的词汇表ID
	private String title;
	private DataSource ds;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		initView();
		initData();
		setListener();
	}

	private void initView() {
		mWordBookListView = (ListView) findViewById(R.id.page_book_books);
	}

	private void initData() {
		current_cid = getIntent().getLongExtra("cid", 1);
		ds = new DataSource(this);
		mWordBookAdapter = new WordBookAdapter(this);
		mWordBookListView.setAdapter(mWordBookAdapter);
		showLoadingDialog();
		getLibraryTask = new getWordBookLibraryTask();
		getLibraryTask.execute();
	}

	private void setListener() {
		mWordBookListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				selected_id = mWordBookList.get(position).getCid();
				title = mWordBookList.get(position).getTitle();
				if (selected_id == current_cid) {
					setResult(Activity.RESULT_CANCELED);
					// setResult(MainActivity.REQUEST_FAIL_CODE);

					finish();
					overridePendingTransition(R.anim.new_dync_no_animation,
							R.anim.new_dync_out_to_right);
				} else {
					insertWordBookPositionTask insertTask=new insertWordBookPositionTask();
					insertTask.execute();
				}
			}
		});
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				dismissLoadingDialog();
				mWordBookAdapter.refreshList(mWordBookList);
				break;
			case 1:
				Intent it = new Intent(WordBookLibrayActivity.this, MainActivity.class);
				it.putExtra("cid", selected_id);
				it.putExtra("title", title);
				setResult(Activity.RESULT_OK, it);
				finish();
				overridePendingTransition(R.anim.new_dync_no_animation,
						R.anim.new_dync_out_to_right);
				break;
			case 2:

				break;
			default:
				break;
			}
		};
	};

	private class insertWordBookPositionTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			int size = mWordBookList.size();
			for (int i = 0;i < size;i++) {
				long cid = mWordBookList.get(i).getCid();
				if (cid == selected_id) ds.updateWordbookPositioin((int) cid, 1);
				else {
					ds.updateWordbookPositioin((int) cid, 0);
				}
			}

			// ds.updateWordbookPositioin((int) selected_id, 0);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mHandler.sendEmptyMessage(1);
		}
	}

	private class getWordBookLibraryTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			mWordBookList = ds.getWordBookLibraryData();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mHandler.sendEmptyMessage(0);
		}
	}

	@Override
	public void setActivityContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.page_book);
	}

	@Override
	public String getBaseTitle() {
		// TODO Auto-generated method stub
		return getResources().getString(R.string.my_word_library);
	}

}
