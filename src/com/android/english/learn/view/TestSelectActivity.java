package com.android.english.learn.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.english.learn.R;
import com.android.english.learn.base.BaseActivity;
import com.android.english.learn.model.UserWordData;
import com.android.english.learn.model.WordData;
import com.android.english.learn.utils.Loger;
import com.android.english.learn.widget.SelectedAnswerView;

/**
 *
 * 作用： 测试选择 视图类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class TestSelectActivity extends BaseActivity {
	private TextToSpeech speak = null;// tts发音
	private TextView page_learn_tip;
	private ImageView page_learn_speak;
	// private EditText page_learn_spell;
	// private TextView page_learn_confirm;
	private SelectedAnswerView selected_view;
	private TextView test_word_confirm_tv;
	private TextView test_word_next_tv;

	private RelativeLayout test_select_ll;
	private int selected_type;//选择类型
	private int selected_position1;//选择位置
	private List<Integer> mSelectedList = new ArrayList<Integer>();//选择列表

	List<String> mAnswerList;//答案字符串列表
	private List<WordData> mWordData;//单词列表
	private String tip;//提示
	private String word;//单词

	searchWordTask searchTask;//查找单词

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		initView();
		initData();
		setListener();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (speak != null) {
			speak.stop();
			speak.shutdown();
		}
	}

	private void initView() {
		test_select_ll = (RelativeLayout) findViewById(R.id.test_select_ll);
		page_learn_tip = (TextView) findViewById(R.id.page_learn_tip);
		page_learn_speak = (ImageView) findViewById(R.id.page_learn_speak);
		selected_view = (SelectedAnswerView) findViewById(R.id.selected_view);
		test_word_confirm_tv = (TextView) findViewById(R.id.test_word_confirm_tv);
		test_word_next_tv = (TextView) findViewById(R.id.test_word_next_tv);
	}

	private void initData() {
		mAnswerList = new ArrayList<String>();
		mWordData = new ArrayList<WordData>();
		mWordData.addAll(MainAllWordsActivity.mWordData);
		selected_type = getIntent().getIntExtra("selected_type", 0);
		if (selected_type == 1) {
			page_learn_speak.setVisibility(View.GONE);
		} else {
			speak = new TextToSpeech(this, new OnInitListener() {

				@Override
				public void onInit(int status) {
					// TODO Auto-generated method stub
					if (status == TextToSpeech.SUCCESS) {
						int result = speak.setLanguage(Locale.ENGLISH);// 设置只能朗读英文
						if (result == TextToSpeech.LANG_MISSING_DATA
								|| result == TextToSpeech.LANG_NOT_SUPPORTED) {// 要是结果没值，就在后台打印出来
							Loger.e("lanageTag not use");
						} else {// 模拟机在启动时朗读下面的英文
							// speak.speak("Hello World,Hello Android",
							// TextToSpeech.QUEUE_FLUSH, null);
						}
					}
				}
			});

			page_learn_speak.setVisibility(View.VISIBLE);
		}
		searchTask = new searchWordTask();
		searchTask.execute();
	}

	private void setListener() {
		test_word_confirm_tv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int selectedPosition = selected_view.getCurrentSelectedPosition();
				if (selectedPosition == -1) {
					showMsg(R.string.selected_position);
				} else {
					selected_view.setAnswerSelected();
				}
			}
		});
		test_word_next_tv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchTask = new searchWordTask();
				searchTask.execute();
			}
		});
		page_learn_speak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (selected_type == 0) {
					speak.speak(tip, TextToSpeech.QUEUE_FLUSH, null);
				}
			}
		});
	}

	private class searchWordTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			mSelectedList.clear();
			int i = 0;
			int size = mWordData.size();
			while (i < 4) {
				int mNextData = (int) (Math.floor((Math.random() - 0.0000001) * size));
				if (!mSelectedList.contains(mNextData)) {
					mSelectedList.add(mNextData);
					i++;
				}
			}
			selected_position1 = (int) (Math.floor((Math.random() - 0.0000001) * 4));
			word = mWordData.get(mSelectedList.get(selected_position1)).getWord();
			mAnswerList.clear();
			switch (selected_type) {
			case 1:
				for (int j = 0;j < 4;j++) {
					mAnswerList.add(mWordData.get(mSelectedList.get(j)).getWord());
				}
				tip = mWordData.get(mSelectedList.get(selected_position1)).getBase();
				break;
			case 0:
				for (int j = 0;j < 4;j++) {
					mAnswerList.add(mWordData.get(mSelectedList.get(j)).getBase());
				}
				tip = word;
				break;
			default:
				break;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mHandler.sendEmptyMessage(0);
		}

	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				if (selected_type == 0) {
					speak.speak(tip, TextToSpeech.QUEUE_FLUSH, null);
				}
				test_select_ll.setClickable(true);
				page_learn_tip.setText(tip);
				selected_view.setAnswerData(mAnswerList, selected_position1, word);
				break;
			case 1:
				break;
			case 2:

				break;
			default:
				break;
			}
		};
	};

	@Override
	public void setActivityContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.test_select_layout_1);
	}

	@Override
	public String getBaseTitle() {
		// TODO Auto-generated method stub
		selected_type = getIntent().getIntExtra("selected_type", 0);
		if (selected_type == 0) {
			return getResources().getString(R.string.word_selected);
		} else {
			return getResources().getString(R.string.base_selected);
		}
	}
}
