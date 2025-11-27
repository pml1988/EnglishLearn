package com.android.english.learn.view;

import com.android.english.learn.R;
import com.android.english.learn.base.BaseActivity;

/**
 *
 * 作用：关于 视图类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class AboutActivity extends BaseActivity{

	@Override
	public void setActivityContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.about_layout);
	}

	@Override
	public String getBaseTitle() {
		// TODO Auto-generated method stub
		return getResources().getString(R.string.about);
	}

}
