package com.android.english.learn;

import com.android.english.learn.utils.SharePrefsHelper;

import android.app.Application;

/**
 *
 * 作用：Application类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class LearnApp extends Application {
	private static LearnApp sInstance = null;//单例
	private SharePrefsHelper mSharePrefsHelper;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		sInstance = this;
		initializeAsyncTask();
		mSharePrefsHelper = new SharePrefsHelper(this);
	}

	public static LearnApp getInstance() {
		return sInstance;
	}

	// 初始化异步任务
	private void initializeAsyncTask() {
		try {
			Class.forName("android.os.AsyncTask");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException();
		}
	}

	public SharePrefsHelper getSharePrefsHelper() {
		return mSharePrefsHelper;
	}

}
