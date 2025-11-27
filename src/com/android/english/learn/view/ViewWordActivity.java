package com.android.english.learn.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.english.learn.R;
import com.android.english.learn.base.BaseActivity;
import com.android.english.learn.commu.LearnConstants;
import com.android.english.learn.datasource.DataSource;
import com.android.english.learn.model.LearingWordData;
import com.android.english.learn.model.WordData;
import com.android.english.learn.utils.Loger;

/**
 *
 * 作用：查看单词类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class ViewWordActivity extends BaseActivity {
	//
	private TextToSpeech speak = null;// tts发音
	// View
	private RelativeLayout common_top_id;
	private TextView page_learn_word;
	private TextView page_learn_phonetic;
	private TextView page_learn_base;
	private ImageView page_learn_speak;
	private TextView page_learn_sentence_english;
	private TextView page_learn_sentence_chinese;
	private ImageView page_learn_sentence_speak;
	private TextView page_learn_funny_note_text;
	private ImageView page_learn_new_word;
	private TextView page_learn_next;

	private LinearLayout view_word_ll;
	private RelativeLayout view_word_loading_rl;
	private TextView mStartLearnTv;
	// private List<CharSequence> mWordList;

	private DataSource ds;
	private InsertLearningRecodeWordTask mInsertTask;

	private getWordTask task;//获取单词列表
	private InsertLearningRecodeWordTask recordTask;//插入单词
	private getOneWordTask oneTask;//获取单词
	private JudgeWordIsNewTask judgeTask;//判断单词是新的

	// private List<WordData> mWordData;
	private List<String> mComparedData;//词汇的单词列表
	private long cid;// 代表单词库的ID
	private int mType;// 代表查看类型：1.表示单个单词查看；2.表示列表单词查看
	private int mOrder;
	private boolean mInsertDB;// 代表是否插入数据库

	private WordData mCurrentData;//当前单词
	private int mNextData;//一个单词的位置

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
		view_word_ll = (LinearLayout) findViewById(R.id.view_word_ll);
		common_top_id = (RelativeLayout) findViewById(R.id.common_top_id);
		page_learn_word = (TextView) findViewById(R.id.page_learn_word);
		page_learn_phonetic = (TextView) findViewById(R.id.page_learn_phonetic);
		page_learn_base = (TextView) findViewById(R.id.page_learn_base);
		page_learn_speak = (ImageView) findViewById(R.id.page_learn_speak);
		page_learn_sentence_english = (TextView) findViewById(R.id.page_learn_sentence_english);
		page_learn_sentence_chinese = (TextView) findViewById(R.id.page_learn_sentence_chinese);
		page_learn_sentence_speak = (ImageView) findViewById(R.id.page_learn_sentence_speak);
		page_learn_funny_note_text = (TextView) findViewById(R.id.page_learn_funny_note_text);
		page_learn_new_word = (ImageView) findViewById(R.id.page_learn_new_word);
		page_learn_next = (TextView) findViewById(R.id.page_learn_next);

		view_word_loading_rl = (RelativeLayout) findViewById(R.id.view_word_loading_rl);
		mStartLearnTv = (TextView) findViewById(R.id.view_word_bottom_tv);
	}

	private void initData() {

		view_word_ll.setVisibility(View.GONE);
		view_word_loading_rl.setVisibility(View.VISIBLE);
		mStartLearnTv.setVisibility(View.GONE);
		ds = new DataSource(this);
		//
		// LearingWordData data = new LearingWordData();
		// data.setStime((new Date()).getTime());
		// data.setWord("hello");
		// mInsertTask = new InsertLearningRecodeWordTask(data);
		// mInsertTask.execute();
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

		// mWordList = new ArrayList<CharSequence>();
		cid = getIntent().getLongExtra("cid", 1);
		// mType = getIntent().getIntExtra("type", LearnConstants.VIEW_WORD);
		// mOrder = getIntent().getIntExtra("order", LearnConstants.SEQ_ORDER);
		mInsertDB = getIntent().getBooleanExtra("insert", true);
		// mWordList = getIntent().getCharSequenceArrayListExtra("words");
		//
		// if (mType == LearnConstants.VIEW_WORD) {
		// common_top_id.setVisibility(View.GONE);
		// page_learn_new_word.setVisibility(View.GONE);
		// } else {
		common_top_id.setVisibility(View.VISIBLE);
		page_learn_new_word.setVisibility(View.VISIBLE);
		// }
		task = new getWordTask();
		task.execute();
	}

	private void setListener() {
		page_learn_sentence_speak.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mCurrentData.getEnglish_sentance() != null
						&& !mCurrentData.getEnglish_sentance().equals("")) {
					speak.speak(mCurrentData.getEnglish_sentance(), TextToSpeech.QUEUE_FLUSH, null);
				}
			}
		});
		page_learn_speak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				speak.speak(mCurrentData.getWord(), TextToSpeech.QUEUE_FLUSH, null);
			}
		});
		mStartLearnTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				view_word_ll.setVisibility(View.VISIBLE);
				view_word_loading_rl.setVisibility(View.GONE);
				oneTask = new getOneWordTask();
				oneTask.execute();
			}
		});
		page_learn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oneTask = new getOneWordTask();
				oneTask.execute();
			}
		});
	}

	class JudgeWordIsNewTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub

			return ds.getDB().getWordIsNew(mCurrentData.getWord());
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Message msg = new Message();
			msg.what = 3;
			msg.obj = result;
			mHandler.sendMessage(msg);
		}
	}

	class InsertLearningRecodeWordTask extends AsyncTask<Void, Void, Void> {
		private LearingWordData data;

		public InsertLearningRecodeWordTask(LearingWordData data) {
			this.data = data;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if (this.data != null) {
				ds.getDB().InsertLearingRecode(data);
			}
			return null;
		}

	}

	class getOneWordTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// mCurrentData = mWordData.get((int) (Math.round(Math.random() *
			// mWordData.size())));

			mCurrentData = ds.getDB().getWord(mComparedData.get(mNextData));

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mNextData = (int) (Math.round(Math.random() * mComparedData.size()));
			mHandler.sendEmptyMessage(2);
		}
	}

	class getWordTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// mWordData = ds.getDB().getAllWordDataList(cid);
			mComparedData = ds.getDB().getCompareWord(cid);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mHandler.sendEmptyMessage(1);
		}

	}

	private void fillData() {

		if (mCurrentData.getWord() != null && !mCurrentData.getWord().equals("")) {
			speak.speak(mCurrentData.getWord(), TextToSpeech.QUEUE_FLUSH, null);
		}
		page_learn_word.setText(mCurrentData.getWord());
		page_learn_phonetic.setText(mCurrentData.getHelptxt());
		page_learn_base.setText(mCurrentData.getBase());
		page_learn_funny_note_text.setText(mCurrentData.getPhonetic());
		page_learn_sentence_english.setText(mCurrentData.getEnglish_sentance());
		page_learn_sentence_chinese.setText(mCurrentData.getChinese_sentance());
		judgeTask = new JudgeWordIsNewTask();
		judgeTask.execute();
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				// dismissLoadingDialog();
				// if (mWordData != null) {
				// mViewWordAdapter.RefreshList(mWordData);
				// }
				// showChangeText();
				mStartLearnTv.setVisibility(View.VISIBLE);
				mNextData = (int) (Math.round(Math.random() * mComparedData.size()));
				break;
			case 2:
				fillData();
				LearingWordData learningdata = new LearingWordData();
				learningdata.setStime((new Date()).getTime());
				learningdata.setWord(mCurrentData.getWord());
				recordTask = new InsertLearningRecodeWordTask(learningdata);
				recordTask.execute();
				break;
			case 3:
				Boolean judge = (Boolean) msg.obj;
				page_learn_new_word.setVisibility(!judge ? View.VISIBLE : View.GONE);
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void setActivityContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.page_learn);
	}

	@Override
	public String getBaseTitle() {
		// TODO Auto-generated method stub
		return getResources().getString(R.string.learn_word);
	}

}
