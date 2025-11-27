package com.android.english.learn.widget;

import com.android.english.learn.R;
import com.android.english.learn.utils.Loger;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 * 作用：圆饼自定义类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class DrawPieChartView extends View {
	private PieChartInterface mPieChartInterface;
	private Paint paint;
	private int roundColor;
	private int roundProgressColor;
	private String text;
	private int textColor;
	private float textSize;
	private float roundWidth;
	private int max;
	private int progress;
	private boolean textIsDisplayable;
	private int style;

	public static final int STROKE = 0;
	public static final int FILL = 1;

	public DrawPieChartView(Context context) {
		this(context, null);
	}

	public DrawPieChartView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public DrawPieChartView(Context context, AttributeSet attrs, int defaultstyle) {
		super(context, attrs, defaultstyle);

		paint = new Paint();
		TypedArray mTypedArray = context
				.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
		roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED);
		roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor,
				Color.GREEN);
		text = mTypedArray.getString(R.styleable.RoundProgressBar_text);
		textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN);
		textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 15);
		roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
		max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
		textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable,
				true);
		style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);

		mTypedArray.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int centre = getWidth() / 2;
		int radius = (int) (centre - roundWidth / 2);
		if(paint==null) {
			Loger.d("The paint is: null");
		}
		paint.setColor(roundColor);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(roundWidth);
		paint.setAntiAlias(true);
		canvas.drawCircle(centre, centre, radius, paint);

		paint.setStrokeWidth(0);
		paint.setColor(textColor);
		paint.setTextSize(textSize);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		// int percent = (int)(((float)progress / (float)max) * 100);
		float textWidth = paint.measureText(text);

		if (textIsDisplayable && style == STROKE) {
			canvas.drawText(text, centre - textWidth / 2, centre + textSize / 2, paint);
		}

		paint.setStrokeWidth(roundWidth);
		paint.setColor(roundProgressColor);
		RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);

		switch (style) {
		case STROKE: {
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawArc(oval, 270, 360 * progress / max, false, paint);
			break;
		}
		case FILL: {
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			if (progress != 0) canvas.drawArc(oval, 270, 360 * progress / max, true, paint);
			break;
		}
		}

	}

	public void setClickData(PieChartInterface clickEvent) {
		mPieChartInterface = clickEvent;
	}

	public synchronized void setProgress(int progress) {
		if (progress < 0) {
			throw new IllegalArgumentException("progress not less than 0");
		}
		if (progress > max) {
			progress = max;
		}
		if (progress <= max) {
			this.progress = progress;
			postInvalidate();
		}
	}

	public synchronized int getMax() {
		return max;
	}

	public synchronized void setMax(int max) {
		if (max < 0) {
			throw new IllegalArgumentException("max not less than 0");
		}
		this.max = max;
	}

	public int getCricleColor() {
		return roundColor;
	}

	public void setCricleColor(int cricleColor) {
		this.roundColor = cricleColor;
	}

	public int getCricleProgressColor() {
		return roundProgressColor;
	}

	public void setCricleProgressColor(int cricleProgressColor) {
		this.roundProgressColor = cricleProgressColor;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public float getRoundWidth() {
		return roundWidth;
	}

	public void setRoundWidth(float roundWidth) {
		this.roundWidth = roundWidth;
	}

	public interface PieChartInterface {
		public void onChooseDate();
	}
	//
	// public class PieView extends View {
	// public float finishAngle;
	// private RectF mBound;
	// private Paint mFinishPaint;
	// private Paint mLeavePaint;
	//
	// public PieView(Context context) {
	// super(context);
	// init();
	// // TODO Auto-generated constructor stub
	// }
	//
	// private void init() {
	// mFinishPaint = new Paint();
	// mFinishPaint.setColor(getResources().getColor(R.color.common_green));
	// mFinishPaint.setStyle(Paint.Style.STROKE);
	//
	// mLeavePaint = new Paint();
	// mLeavePaint.setColor(getResources().getColor(R.color.blue));
	// mLeavePaint.setStyle(Paint.Style.STROKE);
	// }
	//
	// @Override
	// protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	// // TODO Auto-generated method stub
	// mBound = new RectF(0, 0, w, h);
	// }
	//
	// @Override
	// protected void onDraw(Canvas canvas) {
	// // TODO Auto-generated method stub
	// super.onDraw(canvas);
	// canvas.drawArc(mBound, 90.0f, finishAngle, true, mFinishPaint);
	// canvas.drawArc(mBound, 90.0f + finishAngle, 360 - finishAngle, true,
	// mLeavePaint);
	// }
	//
	// public void setFinishAngle(int value) {
	// this.finishAngle = value;
	// this.invalidate();
	// }
	// }
}
