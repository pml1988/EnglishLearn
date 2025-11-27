package com.android.english.learn.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.android.english.learn.LearnApp;
import com.android.english.learn.R;
import com.android.english.learn.base.BaseActivity;
import com.android.english.learn.base.BaseMainActivity;
import com.android.english.learn.datasource.DataSource;
import com.android.english.learn.model.ComparedWordData;
import com.android.english.learn.model.ExampleData;
import com.android.english.learn.model.WordBookData;
import com.android.english.learn.model.WordData;
import com.android.english.learn.view.ViewWordActivity.JudgeWordIsNewTask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

/**
 *
 * 作用：程序加载 视图类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class SplashActivity extends BaseMainActivity {
	private ProgressDialog mLoadingProgressDialog;
	private final String DATABASE_FILENAME = "learn_english.db";
	private final String DATABASE_PATH = android.os.Environment.getExternalStorageDirectory()
			.toString() + "/dictionary";
	private SQLiteDatabase mDb;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.splash_screen);
		if (LearnApp.getInstance().getSharePrefsHelper().getIsLoadingWord()) {
			Jump();
		} else {
			mLoadingProgressDialog = new ProgressDialog(this);
			mLoadingProgressDialog.setTitle(R.string.loading_words);
			mLoadingProgressDialog.setMessage(getResources().getString(R.string.loading_words));
			mHandler.sendEmptyMessage(0);
			mThread.start();
		}

	}
	/**
	 * 操作从raw中填充数据到数据库中
	 */
	Thread mThread = new Thread() {
		public void run() {
			mDb = openDatabase();
			insertword();
			insertwordbook();
			insertWordExampleData();
			insertComparedData();
			DataSource ds=new DataSource(SplashActivity.this);
			ds.updateWordbookPositioin(1, 1);
			printData();
			mHandler.sendEmptyMessage(1);
		};
	};

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				mLoadingProgressDialog.show();
				break;
			case 1:
				mLoadingProgressDialog.dismiss();
				LearnApp.getInstance().getSharePrefsHelper().setIsLoadingWord(true);
				Jump();
				break;
			case 2:
				Jump1();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 插入单词
	 */
	private void insertword() {
		List<WordData> WordList = new ArrayList<WordData>();
		Cursor cursor = mDb.rawQuery("select word,phonetic,base,helptxt from word", null);
		int num = cursor.getCount();
		cursor.moveToFirst();
		for (int i = 0;i < num;i++) {
			WordData wd = new WordData();
			wd.setWord(cursor.getString(0));
			wd.setPhonetic(cursor.getString(1));
			wd.setBase(cursor.getString(2));
			wd.setHelptxt(cursor.getString(3));
			cursor.moveToNext();
			WordList.add(wd);
		}
		DataSource ds = new DataSource(this);
		ds.InsertWordData(WordList);
	}

	/**
	 * 插入词汇表
	 */
	private void insertwordbook() {
		DataSource ds = new DataSource(this);
		Cursor cursor = mDb.rawQuery("select title,cid,position from wordbook", null);
		int num = cursor.getCount();
		cursor.moveToFirst();
		List<WordBookData> data = new ArrayList<WordBookData>();
		for (int i = 0;i < num;i++) {
			WordBookData subData = new WordBookData();
			subData.setTitle(cursor.getString(0));
			subData.setCid(cursor.getLong(1));
			subData.setPosition(cursor.getInt(2));
			cursor.moveToNext();
			data.add(subData);
		}
		ds.InsertWordBookData(data);
	}

	/**
	 * 插入例句
	 */
	private void insertWordExampleData() {
		List<ExampleData> exampleList = new ArrayList<ExampleData>();
		Cursor cursor = mDb.rawQuery("select word,english,chinese from wordexample", null);
		int num = cursor.getCount();
		cursor.moveToFirst();
		for (int i = 0;i < num;i++) {
			ExampleData data = new ExampleData();
			data.setWord(cursor.getString(0));
			data.setEnglish(cursor.getString(1));
			data.setChinese(cursor.getString(2));
			exampleList.add(data);
			cursor.moveToNext();
		}
		DataSource ds = new DataSource(this);
		ds.InsertWordExampleData(exampleList);
	}

	/**
	 * 插入字符串单词
	 */
	private void insertComparedData() {
		List<ComparedWordData> wordList = new ArrayList<ComparedWordData>();
		Cursor cursor = mDb.rawQuery("select word,cid from compareword", null);
		int num = cursor.getCount();
		cursor.moveToFirst();
		for (int i = 0;i < num;i++) {
			ComparedWordData data = new ComparedWordData();
			data.setWord(cursor.getString(0));
			data.setCid(cursor.getLong(1));
			wordList.add(data);
			cursor.moveToNext();
		}
		DataSource ds = new DataSource(this);
		ds.InsertComparedWordData(wordList);
	}

	/**
	 * 获取词汇表信息
	 */
	private void Jump() {

		getPositionTask judgetTask=new getPositionTask();
		judgetTask.execute();
	}

	/**
	 * 跳转到主页面
	 */
	private void Jump1() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				finish();
				Intent it=new Intent(SplashActivity.this, MainActivity.class);
				it.putExtra("main", true);
				startActivity(it);
				overridePendingTransition(R.anim.new_dync_in_from_right,
						R.anim.new_dync_no_animation);
			}
		},500);
	}

	/**
	 *
	 * 作用：获取词汇表信息
	 * @author 顾美琴
	 * @Email:1132654532@qq.com
	 * @version 创建时间：2014年04月
	 */
	class getPositionTask extends AsyncTask<Void, Void, Void> {
		private long cid;
		private String title;
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			DataSource ds=new DataSource(SplashActivity.this);
			WordBookData wb = ds.getWordBookPositionData();
			title = wb.getTitle();
			cid = wb.getCid();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mHandler.sendEmptyMessage(2);
		}
	}

	public void printData() {
		DataSource ds = new DataSource(this);
		Log.d("PML", ds.getWordData("hello").getPhonetic());
	}

	/**
	 * 将raw数据复制到手机上，再打开数据库
	 * @return
	 */
	private SQLiteDatabase openDatabase() {
		try {
			String path = "";
			if (!HasState()) {
				path = "/data/data/com.android.english.learn/databases";
			} else {
				path = DATABASE_PATH;
			}
			String databaseFilename = path + "/" + DATABASE_FILENAME;
			File dir = new File(path);
			if (!dir.exists()) dir.mkdirs();
			File file = new File(databaseFilename);
			if (!file.exists()) {
				file.createNewFile();
				InputStream is = getResources().openRawResource(R.raw.learn_english);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				Log.d("PML", "count is:");
				fos.close();
				is.close();
			}
			SQLiteDatabase database = SQLiteDatabase.openDatabase(databaseFilename, null,
					SQLiteDatabase.OPEN_READWRITE);
			return database;
		} catch (Exception e) {
			Log.d("PML", e.getMessage());
		}
		return null;
	}

	/**
	 * 判断手机卡状态
	 * @return
	 */
	private boolean HasState() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
}
