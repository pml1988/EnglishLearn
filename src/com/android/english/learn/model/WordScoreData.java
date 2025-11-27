package com.android.english.learn.model;

/**
 *
 * 作用：单词分数模型
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class WordScoreData extends WordData {

	/**
	 *
	 */
	private static final long serialVersionUID = 3552837366022702305L;
	private int Score;

	public void setScore(int score) {
		Score = score;
	}

	public int getScore() {
		return Score;
	}

	public void setWordData(WordData data) {
		setWord(data.getWord());
		setBase(data.getBase());
		setChinese_sentance(data.getChinese_sentance());
		setEnglish_sentance(data.getEnglish_sentance());
		setHelptxt(data.getHelptxt());
		setPhonetic(data.getPhonetic());
	}




}
