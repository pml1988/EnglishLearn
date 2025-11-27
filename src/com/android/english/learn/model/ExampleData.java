package com.android.english.learn.model;

/**
 *
 * 作用：例句模型
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class ExampleData extends BaseData {

	/**
	 *
	 */
	private static final long serialVersionUID = 5089916916290254260L;
	private String word;
	private String chinese;
	private String english;

	public void setWord(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	public String getChinese() {
		return chinese;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public String getEnglish() {
		return english;
	}
}
