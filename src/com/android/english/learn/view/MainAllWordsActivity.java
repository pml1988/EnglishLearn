package com.android.english.learn.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.english.learn.R;
import com.android.english.learn.adapter.TestTypeAdapter;
import com.android.english.learn.adapter.ViewWordAdapter;
import com.android.english.learn.base.BaseActivity;
import com.android.english.learn.datasource.DataSource;
import com.android.english.learn.model.WordData;

/**
 *
 * 作用：词汇表 查看类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class MainAllWordsActivity extends BaseActivity {
	private TextView page_words_all_text;
	private TextView page_words_review_text;
	private EditText page_words_input;
	private ListView page_words_words_list;
	private TextView page_test;
	private TextView page_learn_next;

	private ViewWordAdapter mViewWordAdapter;

	private int cid;//词汇表ID
	private String title;
	public static List<WordData> mWordData;
	private int mCurrentPosition;//当前位置
	private DataSource ds;

	private getWordTask task;//获取单词任务
	AlertDialog.Builder builder;
	Dialog mDialog;//对话框

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		initView();
		initData();
		setListener();
	}

	private void initView() {
		page_words_all_text = (TextView) findViewById(R.id.page_words_all_text);
		page_words_review_text = (TextView) findViewById(R.id.page_words_review_text);
		page_words_input = (EditText) findViewById(R.id.page_words_input);
		page_words_words_list = (ListView) findViewById(R.id.page_words_words_list);
		page_test = (TextView) findViewById(R.id.page_test);
		page_learn_next = (TextView) findViewById(R.id.page_learn_next);
	}

	private void initData() {
		mWordData = new ArrayList<WordData>();
		mViewWordAdapter = new ViewWordAdapter(this);
		page_words_words_list.setAdapter(mViewWordAdapter);
		ds = new DataSource(this);
		mCurrentPosition = 1;

		builder = new AlertDialog.Builder(this);

		builder.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle(R.string.select_test_type)
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				})
				.setSingleChoiceItems((new TestTypeAdapter(this)), 0,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Intent it = new Intent();
								switch (which) {
								case 0:
									it.setClass(MainAllWordsActivity.this, TestSelectActivity.class);
									it.putExtra("selected_type", 0);
									break;
								case 1:
									it.setClass(MainAllWordsActivity.this, TestSelectActivity.class);
									it.putExtra("selected_type", 1);
									break;
								case 2:
									it.setClass(MainAllWordsActivity.this, TestSpellActivity.class);
									break;
								default:
									break;
								}
								startActivity(it);
								mDialog.dismiss();
							}
						});
		mDialog = builder.create();
	}

	private void setListener() {
		page_words_words_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent it = new Intent(MainAllWordsActivity.this, ViewOneWordActivity.class);
				it.putExtra("insert", false);
				it.putExtra("word", mWordData.get(position).getWord());
				startActivity(it);
			}
		});
		page_words_all_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isDialogLoading()) {
					mCurrentPosition = 1;
					showChangeClickText();
					showLoadingDialog();
					task = new getWordTask();
					task.execute();
				}
			}
		});
		page_words_review_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isDialogLoading()) {
					mCurrentPosition = 2;
					showChangeClickText();
					showLoadingDialog();
					task = new getWordTask();
					task.execute();
				}
			}
		});

		page_test.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDialog.show();
			}
		});

		page_words_input.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				String selection = s.toString();
				// String sql = "select chinese from t_words where english=?";
				//
				int num = mWordData.size();
				for (int i = 0;i < num;i++) {
					if (mWordData.get(i).getWord().toLowerCase(Locale.US).startsWith(selection)) {
						page_words_words_list.setSelection(i);
						break;
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	private List<WordData> getWordList(int position) {
		List<WordData> data = null;
		if (position == 1) {
			data = ds.getDB().getAllWordDataList(cid);
		} else {
			data = ds.getDB().getLearningRecordWordData(cid);
		}
		return data;
	}

	private void showChangeText() {
		if (mCurrentPosition == 1) {
			page_words_all_text.setText(String.format(
					getResources().getString(R.string.page_words_all_num), mWordData == null ? 0
							: mWordData.size()));
			page_words_all_text.setTextColor(getResources().getColor(R.color.white));

			page_words_review_text.setText(R.string.page_words_review);
			page_words_review_text.setTextColor(getResources().getColor(
					R.color.page_main_title_font));
		} else {
			page_words_review_text.setText(String.format(
					getResources().getString(R.string.page_words_review_num), mWordData == null ? 0
							: mWordData.size()));
			page_words_review_text.setTextColor(getResources().getColor(R.color.white));

			page_words_all_text.setText(R.string.page_words_all);
			page_words_all_text.setTextColor(getResources().getColor(R.color.page_main_title_font));
		}
	}

	private void showChangeClickText() {
		if (mCurrentPosition == 1) {
			page_words_all_text.setText(R.string.page_words_all);
			page_words_all_text.setTextColor(getResources().getColor(R.color.white));

			page_words_review_text.setText(R.string.page_words_review);
			page_words_review_text.setTextColor(getResources().getColor(
					R.color.page_main_title_font));
		} else {
			page_words_review_text.setText(R.string.page_words_review);
			page_words_review_text.setTextColor(getResources().getColor(R.color.white));

			page_words_all_text.setText(R.string.page_words_all);
			page_words_all_text.setTextColor(getResources().getColor(R.color.page_main_title_font));
		}
	}

	// Thread tr = new Thread() {
	// public void run() {
	// try {
	// if (!Thread.currentThread().isInterrupted()) {
	// mWordData = getWordList(mCurrentPosition);
	// mHandler.sendEmptyMessage(1);
	// }
	// } catch (Exception e) {
	// Thread.currentThread().interrupt();
	// }
	// };
	// };

	class getWordTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			mWordData = getWordList(mCurrentPosition);
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
				dismissLoadingDialog();
				if (mWordData != null) {
					mViewWordAdapter.RefreshList(mWordData);
					page_words_words_list.setSelection(0);
				}
				showChangeText();

				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mWordData.size() == 0) {
			showLoadingDialog();
			task = new getWordTask();
			task.execute();
		}

	}

	@Override
	public void setActivityContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.page_words);
	}

	@Override
	public String getBaseTitle() {
		// TODO Auto-generated method stub
		cid = (int) getIntent().getLongExtra("cid", 1);
		title = getIntent().getStringExtra("title");
		return title;
	}

}
