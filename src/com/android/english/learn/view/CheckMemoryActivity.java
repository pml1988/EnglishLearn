package com.android.english.learn.view;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;

import com.android.english.learn.R;
import com.android.english.learn.base.BaseActivity;
import com.android.english.learn.utils.StringHelper;

/**
 *
 * 作用：记忆  视图类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class CheckMemoryActivity extends BaseActivity{
	private TextView desc_tv;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		initView();
		initData();
	}

	private void initView() {
		desc_tv=(TextView) findViewById(R.id.memory_desc_tv);
	}

	private void initData() {
		String str = "";
		try {
			str = StringHelper.readTextFile(this.getAssets().open("memory.txt",
					AssetManager.ACCESS_BUFFER));
		} catch (Exception e) {
			str = "";
		}
		desc_tv.setText(str);
	}
	@Override
	public void setActivityContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.check_memory_layout);
	}

	@Override
	public String getBaseTitle() {
		// TODO Auto-generated method stub
		return getResources().getString(R.string.memory);
	}

}
