package com.android.english.learn.model;

/**
 *
 * 作用：学习单词记录模型
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class LearingWordData extends BaseData {

	/**
	 *
	 */
	private static final long serialVersionUID = 2985352692053321615L;
	private long stime;
	private String word;

	public void setStime(long stime) {
		this.stime = stime;
	}

	public long getStime() {
		return stime;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}
}
