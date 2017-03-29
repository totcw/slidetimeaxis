package com.lyf.slidetimeaxis;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;



/**
 * 自定义seekbar可当做时间轴
 *
 * @author xingliuhua
 *
 */
public class XLHStepView extends View {
	private int mStepCount;// 总的步数
	private int mCurrentStep;// 目前的步数
	private int mBarColor;// 背景条的颜色
	private int mStepSelectedColor;// 节点的颜色
	private int mStepNormalColor;// 普通节点的颜色
	private int mProgressColor;// 已完成进度的颜色
	private float mStepRadius;// 节点的半径
	private float mBarHeight;// 进度条的高度
	private boolean mCanDrag;// 是否支持拖拽
	private float mProgress;// 当前进度，拖拽时进度要跟随手指
	private OnStepChangedListener mStepChangedListener;// 监听器
	private Paint mPaint;

	public XLHStepView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public XLHStepView(Context context) {
		super(context);
	}

	public XLHStepView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.xlhStep);
		mBarColor = typedArray.getColor(R.styleable.xlhStep_barColor,
				Color.BLUE);
		mStepSelectedColor = typedArray.getColor(
				R.styleable.xlhStep_stepSelectedColor, Color.RED);
		mStepNormalColor = typedArray.getColor(
				R.styleable.xlhStep_stepNormalColor, Color.BLUE);
		mProgressColor = typedArray.getColor(R.styleable.xlhStep_progressColor,
				Color.RED);
		mStepCount = typedArray.getInt(R.styleable.xlhStep_stepCount, 3);
		mBarHeight = typedArray.getDimension(R.styleable.xlhStep_barHeight,
				TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15,
						context.getResources().getDisplayMetrics()));
		mCurrentStep = typedArray.getInt(R.styleable.xlhStep_currentStep, 0);
		mCanDrag = typedArray.getBoolean(R.styleable.xlhStep_canDrag, true);
		mStepRadius = typedArray.getDimension(R.styleable.xlhStep_stepRadius,
				TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15,
						context.getResources().getDisplayMetrics()));
		mProgress = (mCurrentStep-1) / (float) (mStepCount-1);
		if (mProgress < 0) {
			mProgress = 0;
		} else if (mProgress > 1) {
			mProgress = 1;
		}
		typedArray.recycle();
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawBarBg(canvas);
		drawProgress(canvas);
		drawSteps(canvas);
		drawCurrentStep(canvas);
	}

	/**
	 * 画当前移动的图标
	 * @param canvas
     */
	private void drawCurrentStep(Canvas canvas) {
		canvas.save();
		float barWidth = getMeasuredWidth() - mStepRadius * 2
				- getPaddingLeft() - getPaddingRight();// 背景条的总宽度
		float pivotX=mStepRadius + barWidth * mProgress;
		mPaint.setColor(mStepSelectedColor);
		//这个圆可以换成自己需要的图片
		RectF oval = new RectF(pivotX - mStepRadius, getPaddingTop(),
				pivotX + mStepRadius, getPaddingTop() + mStepRadius * 2);
		canvas.drawOval(oval, mPaint);
		canvas.restore();
	}

	/**
	 * 画节点
	 *
	 * @param canvas
	 *            画布
	 */
	private void drawSteps(Canvas canvas) {
		canvas.save();

		float barWidth = getMeasuredWidth() - mStepRadius * 2
				- getPaddingLeft() - getPaddingRight();// 背景条的总宽度
		for (int i = 0; i < mStepCount; i++) {
			int pivotX = (int) (mStepRadius + getPaddingLeft() + i * barWidth
					/ (mStepCount - 1));// 当前节点圆的中心点X坐标
			// 下面开始画圆
			mPaint.setColor(mStepNormalColor);
			RectF oval = new RectF(pivotX - mStepRadius, getPaddingTop(),
					pivotX + mStepRadius, getPaddingTop() + mStepRadius * 2);
			canvas.drawOval(oval, mPaint);
		}
		canvas.restore();
	}

	/**
	 * 画背景条
	 *
	 * @param canvas
	 *            画布
	 */
	private void drawBarBg(Canvas canvas) {
		canvas.save();
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(mBarColor);
		RectF r = new RectF(getPaddingLeft() + mStepRadius, getPaddingTop()
				+ mStepRadius - mBarHeight / 2, getMeasuredWidth()
				- getPaddingRight() - mStepRadius, getPaddingTop()
				+ mStepRadius + mBarHeight / 2);
		canvas.drawRect(r, paint);
		canvas.restore();
	}

	/**
	 * 画进度
	 *
	 * @param canvas
	 *            画布
	 */
	private void drawProgress(Canvas canvas) {
		canvas.save();
		float barWidth = getMeasuredWidth() - mStepRadius * 2
				- getPaddingLeft() - getPaddingRight();// 背景条的总宽度
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(mProgressColor);
		RectF r = new RectF(getPaddingLeft() + mStepRadius, getPaddingTop()
				+ mStepRadius - mBarHeight / 2, getPaddingLeft() + mStepRadius
				+ barWidth * (mProgress), getPaddingTop() + mStepRadius
				+ mBarHeight / 2);
		canvas.drawRect(r, paint);
		canvas.restore();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		if (widthMode == MeasureSpec.AT_MOST) {// 相当于 wrap_content
			widthSize = (int) (mStepRadius * 2 * mStepCount + getPaddingLeft() + getPaddingRight());
		}
		if (heightMode == MeasureSpec.AT_MOST) {
			heightSize = (int) mStepRadius * 2 + getPaddingBottom()
					+ getPaddingTop();
		}
		setMeasuredDimension(widthSize, heightSize);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mCanDrag) {// 支持拖动
			float x = event.getX();
			//防止快速滑动出屏幕时候 不执行up的逻辑
			if (event.getAction() != MotionEvent.ACTION_UP) {
				if (x < getPaddingLeft() + mStepRadius
						|| x > getMeasuredWidth() - getPaddingRight() - mStepRadius) {
					return true;
				}
			}

			x = x - getPaddingLeft() - mStepRadius;
			float barWidth = getMeasuredWidth() - mStepRadius * 2
					- getPaddingLeft() - getPaddingRight();// 背景条的总宽度
			mProgress = x / barWidth;
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					System.out.println("up");
					// 判断离哪个节点更近
					int temp = mCurrentStep;
					float minDiatance = Math.abs(mProgress);
					for (int i = 0; i < mStepCount; i++) {
						if (minDiatance >= (Math.abs(mProgress - i
								/ (float) (mStepCount - 1)))) {
							minDiatance = Math.abs(mProgress - i
									/ (float) (mStepCount - 1));
							temp = i;
						}
					}
					// 如果目前的离得最近的节点与旧的节点步数不一致，通知监听器
					if (temp != mCurrentStep) {
						mCurrentStep = temp;
						if (mStepChangedListener != null) {
							mStepChangedListener.onStepChanged(mCurrentStep);
						}
					}
					mProgress = mCurrentStep / (float) (mStepCount - 1);
					break;
			}
			invalidate();
			return true;
		} else {// 不支持拖动
			return false;
		}
	}

	public int getStepCount() {
		return mStepCount;
	}

	public void setStepCount(int mStepCount) {
		this.mStepCount = mStepCount;
		mProgress = mCurrentStep / (float) (mStepCount - 1);
		update();
	}

	public int getCurrentStep() {
		return mCurrentStep;
	}

	public void setCurrentStep(int mCurrentStep) {
		this.mCurrentStep = mCurrentStep;
		mProgress = mCurrentStep / (float) (mStepCount - 1);
		update();
	}

	public void update() {
		if (Looper.myLooper() == Looper.getMainLooper()) {
			invalidate();
		} else {
			postInvalidate();
		}

	}

	public int getBarColor() {
		return mBarColor;
	}

	public void setBarColor(int mBarColor) {
		this.mBarColor = mBarColor;
		update();
	}

	public int getStepSelectedColor() {
		return mStepSelectedColor;
	}

	public void setStepSelectedColor(int mStepSelectedColor) {
		this.mStepSelectedColor = mStepSelectedColor;
		update();
	}

	public int getStepNormalColor() {
		return mStepNormalColor;
	}

	public void setStepNormalColor(int mStepNormalColor) {
		this.mStepNormalColor = mStepNormalColor;
		update();
	}

	public int getProgressColor() {
		return mProgressColor;
	}

	public void setProgressColor(int mProgressColor) {
		this.mProgressColor = mProgressColor;
		update();
	}

	public float getStepRadius() {
		return mStepRadius;
	}

	public void setStepRadius(float mStepRadius) {
		this.mStepRadius = mStepRadius;
		update();
	}

	public float getBarHeight() {
		return mBarHeight;
	}

	public void setBarHeight(float mBarHeight) {
		this.mBarHeight = mBarHeight;
		update();
	}

	public boolean isCanDrag() {
		return mCanDrag;
	}

	public void setCanDrag(boolean mCanDrag) {
		this.mCanDrag = mCanDrag;
	}

	public OnStepChangedListener getStepChangedListener() {
		return mStepChangedListener;
	}

	public void setStepChangedListener(
			OnStepChangedListener mStepChangedListener) {
		this.mStepChangedListener = mStepChangedListener;
	}

	public static interface OnStepChangedListener {
		void onStepChanged(int currentStep);
	}

}
