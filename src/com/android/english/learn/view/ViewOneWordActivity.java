package com.android.english.learn.view;

import java.util.Locale;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.english.learn.R;
import com.android.english.learn.base.BaseActivity;
import com.android.english.learn.datasource.DataSource;
import com.android.english.learn.model.WordData;
import com.android.english.learn.utils.Loger;

/**
 *
 * 作用：查看单个单词
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class ViewOneWordActivity extends BaseActivity {
	private TextView page_learn_word;
	private TextView page_learn_phonetic;
	private TextView page_learn_base;
	private ImageView page_learn_speak;
	private TextView page_learn_sentence_english;
	private TextView page_learn_sentence_chinese;
	private ImageView page_learn_sentence_speak;
	private TextView page_learn_funny_note_text;
	private TextView page_learn_next ;

	private TextToSpeech speak = null;// tts发音

	private getWordTask task;//获取单词

	private DataSource ds;

	private boolean mInsertDB;// 代表是否插入数据库

	private String word;//单词
	private WordData mWordData;//单词模型

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		initView();
		initData();
		setListener();
	}

	private void initView() {
		page_learn_word = (TextView) findViewById(R.id.page_learn_word);
		page_learn_phonetic = (TextView) findViewById(R.id.page_learn_phonetic);
		page_learn_base = (TextView) findViewById(R.id.page_learn_base);
		page_learn_speak = (ImageView) findViewById(R.id.page_learn_speak);
		page_learn_sentence_english = (TextView) findViewById(R.id.page_learn_sentence_english);
		page_learn_sentence_chinese = (TextView) findViewById(R.id.page_learn_sentence_chinese);
		page_learn_sentence_speak = (ImageView) findViewById(R.id.page_learn_sentence_speak);
		page_learn_funny_note_text = (TextView) findViewById(R.id.page_learn_funny_note_text);
		page_learn_next = (TextView) findViewById(R.id.page_learn_next);
	}

	private void initData() {
		ds = new DataSource(this);
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
		mInsertDB = getIntent().getBooleanExtra("insert", true);
		word = getIntent().getStringExtra("word");
		speak.speak(word, TextToSpeech.QUEUE_FLUSH, null);
		task = new getWordTask();
		task.execute();
	}

	private void setListener() {
		page_learn_speak.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (word != null && !word.equals("")) {
					speak.speak(word, TextToSpeech.QUEUE_FLUSH, null);
				}
			}
		});
		page_learn_sentence_speak.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mWordData.getEnglish_sentance() != null
						&& !mWordData.getEnglish_sentance().equals("")) {
					speak.speak(mWordData.getEnglish_sentance(), TextToSpeech.QUEUE_FLUSH, null);
				}
			}
		});
		page_learn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ViewOneWordActivity.this.finish();
				overridePendingTransition(R.anim.new_dync_no_animation, R.anim.new_dync_out_to_right);
			}
		});
	}

	class getWordTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			mWordData = ds.getDB().getWord(word);
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
				fillData();
				break;

			default:
				break;
			}
		};
	};

	private void fillData() {
		if (word != null && !word.equals("")) {
			speak.speak(word, TextToSpeech.QUEUE_FLUSH, null);
		}
		page_learn_word.setText(mWordData.getWord());
		page_learn_phonetic.setText(mWordData.getHelptxt());
		page_learn_base.setText(mWordData.getBase());
		page_learn_funny_note_text.setText(mWordData.getPhonetic());
		page_learn_sentence_english.setText(mWordData.getEnglish_sentance());
		page_learn_sentence_chinese.setText(mWordData.getChinese_sentance());
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

	@Override
	public void setActivityContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.view_one_word_layout);
	}

	@Override
	public String getBaseTitle() {
		// TODO Auto-generated method stub
		return null;
	}

}
