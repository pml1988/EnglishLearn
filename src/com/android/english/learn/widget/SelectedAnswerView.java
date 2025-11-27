package com.android.english.learn.widget;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import com.android.english.learn.R;
import com.android.english.learn.datasource.DataSource;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 *
 * 作用：选择答案 自定义 类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class SelectedAnswerView extends LinearLayout implements OnCheckedChangeListener,
		android.view.View.OnClickListener {
	private DataSource ds;
	private Vibrator vibrator;
	private List<String> mSelectedList;//显示的列表
	private int right_position;//正确位置
	private String word;//单词

	private int right_num;//正确数目
	private int error_num;//错误数目
	private boolean is_selected;//是否选中

	RadioButton selected_a_rb;
	RadioButton selected_b_rb;
	RadioButton selected_c_rb;
	RadioButton selected_d_rb;

	ImageView a_iv;
	ImageView b_iv;
	ImageView c_iv;
	ImageView d_iv;

	TextView a_tv;
	TextView b_tv;
	TextView c_tv;
	TextView d_tv;

	public SelectedAnswerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.selected_answer_widget_layout, this);
		selected_a_rb = (RadioButton) findViewById(R.id.selected_a_rb);
		selected_b_rb = (RadioButton) findViewById(R.id.selected_b_rb);
		selected_c_rb = (RadioButton) findViewById(R.id.selected_c_rb);
		selected_d_rb = (RadioButton) findViewById(R.id.selected_d_rb);

		a_iv = (ImageView) findViewById(R.id.selected_a_iv);
		b_iv = (ImageView) findViewById(R.id.selected_b_iv);
		c_iv = (ImageView) findViewById(R.id.selected_c_iv);
		d_iv = (ImageView) findViewById(R.id.selected_d_iv);

		a_tv = (TextView) findViewById(R.id.selected_a_tv);
		b_tv = (TextView) findViewById(R.id.selected_b_tv);
		c_tv = (TextView) findViewById(R.id.selected_c_tv);
		d_tv = (TextView) findViewById(R.id.selected_d_tv);

		selected_a_rb.setOnCheckedChangeListener(this);
		selected_b_rb.setOnCheckedChangeListener(this);
		selected_c_rb.setOnCheckedChangeListener(this);
		selected_d_rb.setOnCheckedChangeListener(this);

		a_tv.setOnClickListener(this);
		b_tv.setOnClickListener(this);
		c_tv.setOnClickListener(this);
		d_tv.setOnClickListener(this);

		ds = new DataSource(context);
		vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		init();
	}
	/**
	 * 初始化
	 */
	private void init() {
		mSelectedList = new ArrayList<String>();
	}

	/**
	 * 设置数据
	 * @param data 列表数据
	 * @param position 正确位置
	 * @param word 单词
	 */
	public void setAnswerData(List<String> data, int position, String word) {
		mSelectedList.addAll(data);
		right_position = position;
		this.word = word;

		initData();

		a_tv.setText(data.get(0));
		b_tv.setText(data.get(1));
		c_tv.setText(data.get(2));
		d_tv.setText(data.get(3));
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		is_selected = false;

		a_iv.setVisibility(View.INVISIBLE);
		b_iv.setVisibility(View.INVISIBLE);
		c_iv.setVisibility(View.INVISIBLE);
		d_iv.setVisibility(View.INVISIBLE);

		selected_a_rb.setChecked(false);
		selected_b_rb.setChecked(false);
		selected_d_rb.setChecked(false);
		selected_c_rb.setChecked(false);
	}

	private class insertUserWordTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ds.InsertOrUpdateUserWordData(right_num, error_num, word, (new Date()).getTime());
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
	}

	/**
	 * 是否选中
	 */
	public void setAnswerSelected() {
		if (!is_selected) {
			is_selected = true;
			int currentposition = getCurrentSelectedPosition();
			if (currentposition == right_position) {
				error_num = 0;
				right_num = 1;
			} else {
				long[] pattern = { 100, 400, 100, 400 }; // 停止 开启 停止 开启
				vibrator.vibrate(pattern, -1);
				error_num = 1;
				right_num = 0;
			}
			insertUserWordTask insertTask = new insertUserWordTask();
			insertTask.execute();
			a_iv.setVisibility(View.VISIBLE);
			b_iv.setVisibility(View.VISIBLE);
			c_iv.setVisibility(View.VISIBLE);
			d_iv.setVisibility(View.VISIBLE);
			switch (right_position) {
			case 0:
				a_iv.setImageResource(R.drawable.page_learn_right);
				b_iv.setImageResource(R.drawable.page_learn_last_wrong);
				c_iv.setImageResource(R.drawable.page_learn_last_wrong);
				d_iv.setImageResource(R.drawable.page_learn_last_wrong);
				break;
			case 1:
				a_iv.setImageResource(R.drawable.page_learn_last_wrong);
				b_iv.setImageResource(R.drawable.page_learn_right);
				c_iv.setImageResource(R.drawable.page_learn_last_wrong);
				d_iv.setImageResource(R.drawable.page_learn_last_wrong);
				break;
			case 2:
				a_iv.setImageResource(R.drawable.page_learn_last_wrong);
				b_iv.setImageResource(R.drawable.page_learn_last_wrong);
				c_iv.setImageResource(R.drawable.page_learn_right);
				d_iv.setImageResource(R.drawable.page_learn_last_wrong);
				break;
			case 3:
				a_iv.setImageResource(R.drawable.page_learn_last_wrong);
				b_iv.setImageResource(R.drawable.page_learn_last_wrong);
				c_iv.setImageResource(R.drawable.page_learn_last_wrong);
				d_iv.setImageResource(R.drawable.page_learn_right);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 获取当前位置
	 * @return 位置
	 */
	public int getCurrentSelectedPosition() {
		int current_selected_position = -1;
		if (selected_a_rb.isChecked()) {
			current_selected_position = 0;
		} else if (selected_b_rb.isChecked()) {
			current_selected_position = 1;
		} else if (selected_c_rb.isChecked()) {
			current_selected_position = 2;
		} else if (selected_d_rb.isChecked()) {
			current_selected_position = 3;
		}
		return current_selected_position;
	}

	/**
	 * 选中后的效果变化
	 * @param Checkedposition
	 */
	private void doCheckedChanged(int Checkedposition) {
		switch (Checkedposition) {
		case 1:
			selected_b_rb.setChecked(false);
			selected_d_rb.setChecked(false);
			selected_c_rb.setChecked(false);
			break;
		case 2:

			selected_a_rb.setChecked(false);
			selected_d_rb.setChecked(false);
			selected_c_rb.setChecked(false);
			break;
		case 3:
			selected_a_rb.setChecked(false);
			selected_b_rb.setChecked(false);
			selected_d_rb.setChecked(false);
			break;
		case 4:

			selected_b_rb.setChecked(false);
			selected_c_rb.setChecked(false);
			selected_a_rb.setChecked(false);
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (buttonView == selected_a_rb) {
			if (isChecked) {
				doCheckedChanged(1);
			}
		} else if (buttonView == selected_b_rb) {
			if (isChecked) {
				doCheckedChanged(2);
			}
		} else if (buttonView == selected_c_rb) {
			if (isChecked) {
				doCheckedChanged(3);
			}
		} else {
			if (isChecked) {
				doCheckedChanged(4);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.selected_a_tv:
			selected_a_rb.setChecked(true);
			doCheckedChanged(1);
			break;
		case R.id.selected_b_tv:
			selected_b_rb.setChecked(true);
			doCheckedChanged(2);
			break;
		case R.id.selected_c_tv:
			selected_c_rb.setChecked(true);
			doCheckedChanged(3);
			break;
		case R.id.selected_d_tv:
			selected_d_rb.setChecked(true);
			doCheckedChanged(4);
			break;
		default:
			break;
		}
	}
}
