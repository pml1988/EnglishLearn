package com.android.english.learn.model;

/**
 *
 * 作用：单词基础信息模型
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class WordData extends BaseData {
	/**
	 *
	 */
	private static final long serialVersionUID = -1625431721963191686L;
	private String word;
	private String phonetic;
	private String base;
	private String helptxt;
	private String english_sentance;
	private String chinese_sentance;

	public void setWord(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}

	public String getPhonetic() {
		return phonetic;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getBase() {
		return base;
	}

	public void setHelptxt(String helptxt) {
		this.helptxt = helptxt;
	}

	public String getHelptxt() {
		return helptxt;
	}

	public void setEnglish_sentance(String english_sentance) {
		this.english_sentance = english_sentance;
	}

	public String getEnglish_sentance() {
		return english_sentance;
	}

	public void setChinese_sentance(String chinese_sentance) {
		this.chinese_sentance = chinese_sentance;
	}

	public String getChinese_sentance() {
		return chinese_sentance;
	}
}
