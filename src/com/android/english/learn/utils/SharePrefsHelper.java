package com.android.english.learn.utils;

import com.android.english.learn.LearnApp;
import com.android.english.learn.commu.LearnConstants;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 *
 * 作用：存储SharedPreference类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class SharePrefsHelper {
	private SharedPreferences sp = null;

	public SharePrefsHelper(Context context) {
		this.sp = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public SharePrefsHelper() {
		this.sp = PreferenceManager.getDefaultSharedPreferences(LearnApp.getInstance()
				.getApplicationContext());
	}

	public SharePrefsHelper(SharedPreferences sp) {
		this.sp = sp;
	}

	public int getWordLibrarySwitch() {
		if (this.sp != null) return this.sp.getInt(LearnConstants.WORD_LIBRARY_SWITCH, 0);
		return 0;
	}

	public void setPictureAutoSwitch(int value) {
		if (this.sp != null) this.sp.edit().putInt(LearnConstants.WORD_LIBRARY_SWITCH, value)
				.commit();
	}

	public boolean getIsLoadingWord() {
		if (this.sp != null) return this.sp.getBoolean(LearnConstants.IS_LOAIDNG_WORD, false);
		return false;
	}

	public void setIsLoadingWord(boolean value) {
		if (this.sp != null) this.sp.edit().putBoolean(LearnConstants.IS_LOAIDNG_WORD, value)
				.commit();
	}


	public long getLastOpenAppTime() {
		if (this.sp != null) return this.sp.getLong(LearnConstants.LAST_OPEN_APP_TIME, -1);
		return -1;
	}

	public void setLastOpenAppTime(long value) {
		if (this.sp != null) this.sp.edit().putLong(LearnConstants.LAST_OPEN_APP_TIME, value)
				.commit();
	}
}
