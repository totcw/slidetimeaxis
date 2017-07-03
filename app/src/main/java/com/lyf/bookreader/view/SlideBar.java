package com.lyf.bookreader.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.lyf.bookreader.R;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/7/1
 * 功能说明： 控制recycleview滑动的控件
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class SlideBar extends View {
    private int mSlideBarHeight =100;//滑动控件的高度
    Paint mPaint;
    private float mDownY;
    private OnTouchingChangedListener mOnTouchingChangedListener;
    private int size; //容器的大小
    private int mWidth;//滑动控件的宽度

    public SlideBar(Context context) {
        this(context, null);
    }

    public SlideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);
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

        Bitmap bitmap =BitmapFactory.decodeResource(getResources(), R.mipmap.title_scroll_n);
        int height = bitmap.getHeight();
        mWidth = bitmap.getWidth();
        mSlideBarHeight = height > mWidth ? height : mWidth;
        canvas.drawBitmap(bitmap,getMeasuredWidth()- mWidth,0,mPaint);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float downX = event.getX();
                mDownY = event.getY();
                //如果按下的位置不是拖拽图片就不响应事件
                if (downX > getMeasuredWidth() || downX < getMeasuredWidth()- mWidth|| mDownY > Math.abs(getScrollY()) + mSlideBarHeight
                        || mDownY < Math.abs(getScrollY())) {
                    return false;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                float diffY = move(event);
                //滑动
                scrollBy(0, -(int) diffY);
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                move(event);
                break;
            case MotionEvent.ACTION_OUTSIDE:
                move(event);
                break;
        }
        return true;
    }

    private float move(MotionEvent event) {
        float diffY = event.getY() - mDownY;
        //判断界限
        if (getScrollY() - diffY > 0) {
            diffY = getScrollY();
        }
        if (getScrollY() - diffY < -(getMeasuredHeight()-mSlideBarHeight)) {
            diffY = getScrollY() + getMeasuredHeight()-mSlideBarHeight;
        }
        //已经滑动的距离
        int scrollHeight = getScrollY() - (int) diffY;
        //求出滑动的距离占View的高度的百分比
        float progress = Math.abs(scrollHeight)*1.0f / (getMeasuredHeight()-mSlideBarHeight);
        //使得返回的position的范围在0~size之间
        if (progress >= 0 && progress <= 1) {
            int position = (int) Math.ceil(progress * size);
            if (mOnTouchingChangedListener != null) {
                mOnTouchingChangedListener.onTouchingChanged(position);
            }
        }

        return diffY;
    }

    public interface OnTouchingChangedListener {
        void onTouchingChanged(int position);
    }

    public void setOnTouchingChangedListener(OnTouchingChangedListener onTouchingChangedListener) {
        mOnTouchingChangedListener = onTouchingChangedListener;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
