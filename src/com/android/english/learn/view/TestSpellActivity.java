package com.android.english.learn.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.english.learn.R;
import com.android.english.learn.base.BaseActivity;
import com.android.english.learn.model.WordData;
import com.android.english.learn.utils.Loger;

/**
 *
 * 作用：测试 拼写 视图类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */

public class TestSpellActivity extends BaseActivity {
	private String tip;//提示
	private String word;//单词

	private List<WordData> mWordData;//单词模型列表

	private searchWordTask searchTask;//搜索单词

	private TextToSpeech speak = null;// tts发音
	private TextView page_learn_tip;
	private ImageView page_learn_speak;
	private EditText page_learn_spell;
	private TextView test_word_next_tv;
	private TextView page_learn_confirm;
	private TextView page_learn_answer;
	private TextView page_learn_show;
	private TextView compare_spell_tv;
	private TextView compare_word_tv;
	private RelativeLayout test_compare_rl;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		initView();
		initData();
		setListener();
	}

	private void initView() {
		page_learn_tip = (TextView) findViewById(R.id.page_learn_tip);
		page_learn_speak = (ImageView) findViewById(R.id.page_learn_speak);
		page_learn_spell = (EditText) findViewById(R.id.page_learn_spell);
		page_learn_confirm = (TextView) findViewById(R.id.page_learn_confirm);
		page_learn_answer = (TextView) findViewById(R.id.page_learn_answer);
		page_learn_show = (TextView) findViewById(R.id.page_learn_show);
		test_compare_rl = (RelativeLayout) findViewById(R.id.test_compare_rl);
		compare_spell_tv = (TextView) findViewById(R.id.compare_spell_tv);
		compare_word_tv = (TextView) findViewById(R.id.compare_word_tv);
		test_word_next_tv=(TextView) findViewById(R.id.test_word_next_tv);
	}

	private void initData() {
		mWordData = new ArrayList<WordData>();
		mWordData.addAll(MainAllWordsActivity.mWordData);

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
						 speak.speak("",
						 TextToSpeech.QUEUE_FLUSH, null);
					}
				}
			}
		});
		searchTask = new searchWordTask();
		searchTask.execute();
	}

	Handler mClearTextHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				page_learn_spell.setEnabled(true);
				page_learn_spell.setText("");
				break;
			case 2:

				break;
			default:
				break;
			}
		};
	};

	private void initAndSetData() {
		test_compare_rl.setVisibility(View.INVISIBLE);
		page_learn_show.setVisibility(View.VISIBLE);
		page_learn_spell.setText("");
	}

	private void setListener() {
		test_word_next_tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				searchTask = new searchWordTask();
				searchTask.execute();
			}
		});
		page_learn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String spell_str = page_learn_spell.getText().toString().trim();
				if (!spell_str.equals("")) {

					compare_spell_tv.setText(spell_str);
					int spell_size = spell_str.length();//自己写的单词数目
					int word_size = word.length();//本书单词的数量
					String str_word = word;

					if (spell_size > word_size) {
						for (int i = 0;i < spell_size - word_size;i++) {
							str_word += " ";
						}
					}
					SpannableString ss = new SpannableString(str_word);
					int same_size = spell_size > word_size ? word_size : spell_size;

					for (int i = 0;i < same_size;i++) {
						boolean b = (spell_str.charAt(i) == str_word.charAt(i));
						ss.setSpan(new ForegroundColorSpan(!b ? getResources().getColor(R.color.real_red)
								: getResources().getColor(R.color.common_green)), i, i + 1,
								Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					}
					if (spell_size > word_size) {

						ss.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.real_red)),
								same_size, spell_size, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					} else if (spell_size < word_size) {
						ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.real_red)),
								same_size, word_size, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					}
					compare_word_tv.setText(ss);
					test_compare_rl.setVisibility(View.VISIBLE);
					compare_word_tv.setVisibility(View.VISIBLE);
					page_learn_spell.setEnabled(false);
					mClearTextHandler.sendEmptyMessageDelayed(1, 5000);
				}
			}
		});

		page_learn_speak.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				speak.speak(word, TextToSpeech.QUEUE_FLUSH, null);
			}
		});
		page_learn_show.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				page_learn_show.setVisibility(View.GONE);
			}
		});

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

	private class searchWordTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			int size = mWordData.size();
			int mNextData = (int) (Math.floor((Math.random() - 0.0000001) * size));
			word = mWordData.get(mNextData).getWord();
			tip = mWordData.get(mNextData).getBase();
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
				speak.speak(word, TextToSpeech.QUEUE_FLUSH, null);
				page_learn_tip.setText(tip);
				page_learn_answer.setText(word);
				initAndSetData();
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
		setContentView(R.layout.test_select_layout);
	}

	@Override
	public String getBaseTitle() {
		// TODO Auto-generated method stub
		return getResources().getString(R.string.base_spell_selected);
	}

}
