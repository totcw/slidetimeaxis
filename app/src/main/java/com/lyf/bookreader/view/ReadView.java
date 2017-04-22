package com.lyf.bookreader.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.lyf.bookreader.utils.ScreenUtils;

/**
 * 阅读控件
 * Created by Administrator on 2017/4/18.
 */

public class ReadView extends View {
    protected int mScreenWidth;
    protected int mScreenHeight;

    protected Bitmap mCurPageBitmap, mNextPageBitmap, mPrePageBitmap;
    protected Canvas mCurrentPageCanvas, mNextPageCanvas, mPrePageCanvas;

    private Context mContext;

    /**
     * 正文字体大小
     */
    private int mFontSize;
    /**
     * 标题字体大小
     */
    private int mTitleSize;
    /**
     * 每页行数
     */
    private int mPageLineCount;

    /**
     * 每行字数
     */
    private int mLineCount;
    /**
     * 行间距
     **/
    private int mLineSpace;

    /**
     * 正文距离屏幕左边的距离
     */
    private int paddingLeft = ScreenUtils.dpToPxInt(10);
    /**
     * 正文距离屏幕右边的距离
     */
    private int paddingright = ScreenUtils.dpToPxInt(3);

    /**
     * 正文距离屏幕底部的距离
     */
    private int paddingbottom = ScreenUtils.dpToPxInt(10);


    /**
     * 页首,页尾的位置
     */
    private int mEndPos = 0, mBeginPos = 0;
    /**
     * 当前的章节
     */
    private int currentChapter;
    /**
     * 正文的画笔
     */
    private Paint mPaint;

    /**
     * 标题的画笔
     */
    private Paint mTitlePaint;

    /**
     * 手指按下的 坐标
     */
    protected float actiondownX, actiondownY;
    /**
     * 是否是点击的屏幕中间
     */
    private boolean center;


    private LoadPageListener mLoadPageListener;

    private String str = "";//正文内容
    private String title; //章节名
    private int total;//书本总的章节数
    private boolean isLodaing;//是否正在加载数据,用来判断没加载完,不重复发起请求

    public ReadView(Context context) {

        this(context, null);
    }

    public ReadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScreenWidth = ScreenUtils.getScreenWidth();
        mScreenHeight = ScreenUtils.getScreenHeight() - ScreenUtils.getStatusBarHeight(context);

        mCurPageBitmap = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.ARGB_8888);
        mNextPageBitmap = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.ARGB_8888);
        mPrePageBitmap = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.ARGB_8888);
        mCurrentPageCanvas = new Canvas(mCurPageBitmap);
        mNextPageCanvas = new Canvas(mNextPageBitmap);
        mPrePageCanvas = new Canvas(mPrePageBitmap);

        mFontSize = ScreenUtils.dpToPxInt(16);
        mTitleSize = ScreenUtils.dpToPxInt(30);
        mLineSpace = mFontSize / 5 * 2;
        mPageLineCount = (mScreenHeight - paddingbottom - mTitleSize) / (mFontSize + mLineSpace);
        mLineCount = (mScreenWidth - paddingLeft - paddingright) / (mFontSize);


        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mFontSize);
        mPaint.setColor(Color.BLACK);

        mTitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint.setTextSize(mTitleSize);
        mTitlePaint.setColor(Color.BLACK);


    }

    /**
     * 加载下一页的数据
     *
     * @param canvas
     */
    private void drawNextPageBitmap(Canvas canvas) {
        if (str != null) {
            if (mEndPos < str.length()) {
                //记录下一页的开始位置
                mBeginPos = mEndPos;
                //画文字
                if (drawContent(canvas, str)) {
                    //先将当前页面的文本拷贝
                    mPrePageCanvas.drawBitmap(mCurPageBitmap, 0, 0, null);
                    //将下一页的文本拷贝到当前页面
                    mCurrentPageCanvas.drawBitmap(mNextPageBitmap, 0, 0, null);
                    postInvalidate();
                }

            } else {
                //加载下一章节
                if (currentChapter >= total) {
                    currentChapter = total;
                    //没有下一章
                    return;
                }
                //加载下一章节的内容
                if (!isLodaing) {

                    if (mLoadPageListener != null) {
                        isLodaing = true;
                        currentChapter++;
                        mLoadPageListener.nextPage(currentChapter);
                    } else {
                        //如果加载到的内容为空,就表示最后一章或者失败
                        Toast.makeText(mContext,"mLoadpageListerner=null",Toast.LENGTH_SHORT).show();
                    }
                }

            }


        }

    }

    /**
     * 绘制下一页
     *
     * @param
     */
    public void drawNextPage(String title, String content) {
        this.title = title;
        this.str = content;
        if (TextUtils.isEmpty(str)) {
            //如果加载到的内容为空,就表示最后一章或者失败
            Toast.makeText(mContext, "没有更多章节了", Toast.LENGTH_SHORT).show();

        } else {
            mBeginPos = mEndPos = 0;
            if (drawContent(mNextPageCanvas, str)) {
                //先将当前页面的文本拷贝
                mPrePageCanvas.drawBitmap(mCurPageBitmap, 0, 0, null);
                //将下一页的文本拷贝到当前页面
                mCurrentPageCanvas.drawBitmap(mNextPageBitmap, 0, 0, null);
                postInvalidate();
            }


        }
    }


    private void drawPrePageBitmap(Canvas canvas) {

        if (mBeginPos > 0) {
            mBeginPos = mEndPos = mBeginPos - (mPageLineCount * mLineCount);
            if (mBeginPos < 0) {
                mBeginPos = mEndPos = 0;
            }
            if (drawContent(canvas, str)) {
                //先将当前页面的文本拷贝
                mNextPageCanvas.drawBitmap(mCurPageBitmap, 0, 0, null);
                //将上一页的文本拷贝到当前页面
                mCurrentPageCanvas.drawBitmap(mPrePageBitmap, 0, 0, null);
                postInvalidate();
            }

        } else {
            //加载上一章节
            if (currentChapter <= 1) {
                currentChapter = 1;
                //没有上一章
                return;
            }
            if (!isLodaing) {

                if (mLoadPageListener != null) {
                    isLodaing = true;
                    currentChapter--;
                    mLoadPageListener.prePage(currentChapter);
                    //drawPrePage(canvas);
                } else {
                    Toast.makeText(mContext, "mLoadPageListener为null", Toast.LENGTH_SHORT).show();
                }
            }

        }

    }

    /**
     * 绘制上一章的最后一页
     *
     * @param
     */
    public void drawPrePage(String title, String content) {
        this.title = title;
        this.str = content;
        if (TextUtils.isEmpty(str)) {
            //如果加载到的内容为空,就表示失败
            Toast.makeText(mContext, "加载前面一页的内容为空", Toast.LENGTH_SHORT).show();
        } else {
            int length = str.length();
            //上一章完整的页数
            int count = length % (mPageLineCount * mLineCount);
            int page = length / (mPageLineCount * mLineCount);
            if (count == 0) {
                //如果count==0,表示页数刚刚好好
                mBeginPos = mEndPos = (page - 1) * (mPageLineCount * mLineCount);
            } else {
                mBeginPos = mEndPos = page * (mPageLineCount * mLineCount);
            }
            if (drawContent(mPrePageCanvas, str)) {
                //先将当前页面的文本拷贝
                mNextPageCanvas.drawBitmap(mCurPageBitmap, 0, 0, null);
                //将上一页的文本拷贝到当前页面
                mCurrentPageCanvas.drawBitmap(mPrePageBitmap, 0, 0, null);
                postInvalidate();
            }

        }


    }

    /**
     * 加载当前页的数据
     * @param title 章节名
     * @param content 正文
     * @param chapter 当前第几章
     * @param total 总的章节数
     */
    public void drawCurPageBitmap(String title, String content, int chapter,int total) {
        this.str = content;
        this.title = title;
        this.total = total;
        this.currentChapter = chapter;
        //画文字
        if (drawContent(mCurrentPageCanvas, str)) {
            postInvalidate();
        }

    }


    private boolean drawContent(Canvas canvas, String content) {
        if (content != null) {
            //画背景
            canvas.drawColor(Color.WHITE);
            //画标题
            canvas.drawText(title, mTitleSize * 2, mTitleSize, mTitlePaint);
            //画正文
            int y = mLineSpace + mFontSize + mTitleSize;
            for (int i = 0; i < mPageLineCount; i++) {
                int x = paddingLeft;
                for (int j = 0; j < mLineCount; j++) {
                    if (mEndPos < content.length() && mEndPos >= 0) {
                        char c = content.charAt(mEndPos);
                        //绘制正文
                        canvas.drawText(String.valueOf(c), x, y, mPaint);
                        //每次加上文字的宽度
                        x += mFontSize;
                        //记录绘制的文字个数
                        mEndPos++;
                    }

                }
                //每次加上文字的高度
                y += mFontSize;
                y += mLineSpace;
            }
            return true;
        }
        return false;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mCurPageBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                actiondownX = (int) event.getX();
                actiondownY = (int) event.getY();

                if (actiondownX >= mScreenWidth / 3 && actiondownX <= mScreenWidth * 2 / 3
                        && actiondownY >= mScreenHeight / 3 && actiondownY <= mScreenHeight * 2 / 3) {
                    center = true;
                } else {
                    center = false;
                    if (actiondownX < mScreenWidth / 2) {// 从左翻
                        drawPrePageBitmap(mPrePageCanvas);

                    } else if (actiondownX >= mScreenWidth / 2) {// 从右翻
                        drawNextPageBitmap(mNextPageCanvas);

                    }
                }
                break;
        }

        return true;
    }


    public void setmLoadPageListener(LoadPageListener mLoadPageListener) {
        this.mLoadPageListener = mLoadPageListener;
    }

    public interface LoadPageListener {

        /**
         * 加载上一章节
         */
        void prePage(int chapter);

        /**
         * 加载下一章节
         *
         * @param chapter
         * @return
         */
        void nextPage(int chapter);

    }

    public void setCurrentChapter(int currentChapter) {
        if (currentChapter >0 && currentChapter < total) {
            this.currentChapter = currentChapter;
        }

    }

    /**
     * 设置是否加载完成
     * @param lodaing
     */
    public void setLodaing(boolean lodaing) {
        isLodaing = lodaing;
    }
}
