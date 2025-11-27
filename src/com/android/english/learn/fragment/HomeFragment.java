package com.android.english.learn.fragment;

import java.util.Calendar;
import java.util.Date;

import com.android.english.learn.R;
import com.android.english.learn.commu.LearnConstants;
import com.android.english.learn.datasource.DataSource;
import com.android.english.learn.model.WordBookData;
import com.android.english.learn.utils.Loger;
import com.android.english.learn.view.MainActivity;
import com.android.english.learn.view.MainActivity.changeHome;
import com.android.english.learn.view.MainAllWordsActivity;
import com.android.english.learn.view.ViewWordActivity;
import com.android.english.learn.view.WordBookLibrayActivity;
import com.android.english.learn.widget.DrawPieChartView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 * 作用：主界面的主页界面
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class HomeFragment extends Fragment {
	private TextView mPageMainStartTv;//开始学习
	// private ImageView mPageMainMenuIv;
	private TextView page_main_title;//标题
	private ImageView page_main_change;//查看词汇表
	private TextView page_main_all_words;//查看测试单词

	private TextView home_study_review_tv;//今天已经学习
	private TextView home_study_today_new_words_tv;//今天目标
	private TextView home_study_complete_tv;//完成数
	private TextView home_study_remain;//剩余

	private DrawPieChartView mDrawChartView;//圆饼图

	private long cid;//词汇表ID
	private String title;//标题
	private DataSource ds;//DB的引用

	// private getWordBookTask getTask;
	WordBookData wb;//词汇表模型
	private long ctime;//时间戳
	private int totalNum;//所有数
	private int haveLearnNum;//已学习数
	private int todayLearnNum;//今日学习数

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.home_fragment_layout, container, false);
		initView(view);
		initData();
		setListener();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				title = wb.getTitle();
				cid = wb.getCid();
				page_main_title.setText(title);
				home_study_review_tv.setText(String.valueOf(todayLearnNum));
				home_study_complete_tv.setText(String.valueOf(haveLearnNum) + "/"
						+ String.valueOf(totalNum));
				int day=Math.round(((ctime-(new Date()).getTime()) / (1000 * 60 * 60 * 24)));
				if (day<0) {
					home_study_today_new_words_tv.setText(String.valueOf(0));

					home_study_remain.setText(String.valueOf(0));
				} else {
					home_study_today_new_words_tv.setText(String.valueOf((int) (totalNum / (day+1))));

					home_study_remain.setText(String.valueOf(day));
				}
				mDrawChartView.setMax(totalNum);
				mDrawChartView.setProgress(haveLearnNum);
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new Thread() {
			public void run() {
				doRefreshData();
			};
		}.start();
	}

	private void doRefreshData() {
		wb = ds.getWordBookPositionData();
		cid = wb.getCid();
		ctime = ds.getPlanData((int) wb.getCid());
		totalNum = ds.getDB().getSpecialCidCount((int) cid);

		haveLearnNum = ds.getDB().getLearningRecordWordNum(cid);
		todayLearnNum = ds.getTodayWordDataNum((int) cid);
		if (ctime == -1) {
			mHandler.sendEmptyMessage(1);
		} else {
			mHandler.sendEmptyMessage(1);
		}
	}

	private void initView(View view) {
		Loger.d("PML home is:");
		mPageMainStartTv = (TextView) view.findViewById(R.id.page_main_start);
		// mPageMainMenuIv = (ImageView) view.findViewById(R.id.page_main_menu);
		page_main_title = (TextView) view.findViewById(R.id.page_main_title);
		page_main_change = (ImageView) view.findViewById(R.id.page_main_change);
		page_main_all_words = (TextView) view.findViewById(R.id.page_main_all_words);
		mDrawChartView = (DrawPieChartView) view.findViewById(R.id.home_picchar_view);

		home_study_review_tv = (TextView) view.findViewById(R.id.home_study_review_tv);
		home_study_today_new_words_tv = (TextView) view
				.findViewById(R.id.home_study_today_new_words_tv);
		home_study_complete_tv = (TextView) view.findViewById(R.id.home_study_complete_tv);
		home_study_remain = (TextView) view.findViewById(R.id.home_study_remain);
	}

	private void initData() {
		ds = new DataSource(getActivity());
		MainActivity ma = (MainActivity) getActivity();
		ma.setHome(new resultHome());

		// filldata();
	}

	private class resultHome implements changeHome {

		@Override
		public void setHomeActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			if (resultCode == Activity.RESULT_OK) {
				if (requestCode == MainActivity.REQUEST_HOME_CODE) {
					Loger.d("The result code is:" + data.getLongExtra("cid", 1));
					cid = data.getLongExtra("cid", 1);
					title = data.getStringExtra("title");
					fillResultData();
				}
			}
		}
	}

	// private void filldata() {
	// getTask = new getWordBookTask();
	// getTask.execute();
	// }

	private void fillResultData() {
		if (page_main_title != null) {
			Loger.d("f11111111111111111");
			page_main_title.setText(title);
		} else {
			Loger.d("fdsssssssssss");
		}
	}

	public void setHomeActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == MainActivity.REQUEST_HOME_CODE) {
				Loger.d("The result code is:" + data.getLongExtra("cid", 1));
				cid = data.getLongExtra("cid", 1);
				title = data.getStringExtra("title");

				fillResultData();
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		Loger.d("PML" + requestCode + ";resultcode is:" + resultCode);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == MainActivity.REQUEST_HOME_CODE) {
				Loger.d("The result code is:" + data.getLongExtra("cid", 1));
				cid = data.getLongExtra("cid", 1);
				title = data.getStringExtra("title");
				fillResultData();
			}
		}
	}

	private void setListener() {
		page_main_change.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(getActivity(), WordBookLibrayActivity.class);
				it.putExtra("cid", cid);
				getActivity().startActivityForResult(it, MainActivity.REQUEST_HOME_CODE);

				getActivity().overridePendingTransition(R.anim.new_dync_in_from_right,
						R.anim.new_dync_no_animation);
			}
		});
		mPageMainStartTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(getActivity(), ViewWordActivity.class);
				it.putExtra("type", LearnConstants.LEARN_WORD);
				it.putExtra("insert", true);
				it.putExtra("cid", cid);
				startActivity(it);
				getActivity().overridePendingTransition(R.anim.new_dync_in_from_right,
						R.anim.new_dync_no_animation);
			}
		});
		page_main_all_words.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(getActivity(), MainAllWordsActivity.class);
				it.putExtra("cid", cid);
				it.putExtra("title", title);
				startActivity(it);
				getActivity().overridePendingTransition(R.anim.new_dync_in_from_right,
						R.anim.new_dync_no_animation);
			}
		});
		mDrawChartView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar c = Calendar.getInstance();
				if (ctime > (new Date()).getTime()) {
					Loger.d("the ctime is:"+ctime);
					c.setTime(new Date(ctime));
				}
				new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						final Date date = new Date(year-1900, monthOfYear, dayOfMonth);


						Loger.d("The year is:"+year+";"+monthOfYear+1+";"+dayOfMonth);
						Loger.d("The date is:"+date.getTime());
						new Thread() {
							public void run() {
								long selectedTime = date.getTime();
								Loger.d("The selected is:"+ (new Date()).getTime());
								if (selectedTime < (new Date()).getTime()) {
									MainActivity main=(MainActivity) getActivity();
//									Toast.makeText(getActivity(),getResources().getString(R.string.selected_time_low),
//											Toast.LENGTH_SHORT).show();
									main.showMsg(R.string.selected_time_low);
								} else {
									ds.setPlanData((int) cid, date.getTime());
									doRefreshData();
								}
							};
						}.start();
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) // 传入天数)
				).show();
				;
			}
		});
	}
	// class getWordBookTask extends AsyncTask<Void, Void, Void> {
	//
	// @Override
	// protected Void doInBackground(Void... params) {
	// // TODO Auto-generated method stub
	// if (ds == null) {
	// ds = new DataSource(getActivity());
	// }
	// wb = ds.getWordBookPositionData();
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(Void result) {
	// // TODO Auto-generated method stub
	// super.onPostExecute(result);
	// mHandler.sendEmptyMessage(1);
	// }
	//
	// }

}
