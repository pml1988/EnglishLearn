package com.android.english.learn.model;

/**
 *
 * 作用：单词学习记录模型
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class UserWordData extends BaseData{

	/**
	 *
	 */
	private static final long serialVersionUID = -3562216807021693491L;
	private int times;//总次数
	private long stime;//时间
	private int error_times;//错误次数
	private int degree;//分数
	private String word;//单词

	public void setTimes(int times) {
		this.times = times;
	}
	public int getTimes() {
		return times;
	}
	public void setStime(long stime) {
		this.stime = stime;
	}
	public long getStime() {
		return stime;
	}
	public void setError_times(int error_times) {
		this.error_times = error_times;
	}
	public int getError_times() {
		return error_times;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	public int getDegree() {
		return degree;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getWord() {
		return word;
	}

}
