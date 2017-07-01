package com.lyf.bookreader.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/7/1
 * 功能说明：
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class SlideBar extends View {
    private int mTouchSlop;
    Paint mPaint;
    private float mDownY;
    private OnTouchingChangedListener mOnTouchingChangedListener;

    public SlideBar(Context context) {
        this(context, null);
    }

    public SlideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(MeasureSpec.getSize(widthMeasureSpec), widthMeasureSpec);
        int height = getMySize(MeasureSpec.getSize(heightMeasureSpec), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, 100,  100, mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float downX = event.getX();
                mDownY = event.getY();
                if (downX < 100 && downX > 0) {
                    if (mDownY < Math.abs(getScrollY()) + 100 && mDownY > Math.abs(getScrollY())) {

                    } else {
                        return false;
                    }
                } else {
                    return false;
                }

                break;
            case MotionEvent.ACTION_MOVE:

                float diffY = event.getY() - mDownY;
                //判断界限
                if (getScrollY() - diffY > 0) {
                    diffY = getScrollY();
                }
                if (getScrollY() - diffY < -(getMeasuredHeight()-100)) {
                    diffY = getScrollY() + getMeasuredHeight()-100;
                }
                int scrollHeight = getScrollY() - (int) diffY;

                float i = Math.abs(scrollHeight)*1.0f / (getMeasuredHeight()-100);

                System.out.println("scrollHeight:"+scrollHeight);
                System.out.println("getMeasuredHeight:"+getMeasuredHeight());
                System.out.println("i:"+i);
                if (mOnTouchingChangedListener != null) {

                    mOnTouchingChangedListener.onTouchingChanged(0);
                }

                //滑动
                scrollBy(0, -(int) diffY);
                mDownY = event.getY();

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    public interface OnTouchingChangedListener {
        void onTouchingChanged(int position);
    }
}
