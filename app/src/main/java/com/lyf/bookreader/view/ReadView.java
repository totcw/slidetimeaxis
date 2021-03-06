package com.lyf.bookreader.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.lyf.bookreader.R;
import com.lyf.bookreader.utils.CacheUtils;
import com.lyf.bookreader.utils.Constants;
import com.lyf.bookreader.utils.ScreenUtils;

/**
 * 阅读控件
 * Created by Admin istrator on 2017/4/18.
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

    private Rect rectF;//背景
    private Bitmap mBookPageBg;//背景图片


    private LoadPageListener mLoadPageListener;

    private String str = "";//正文内容
    private String title; //章节名
    private int total;//书本总的章节数
    private boolean isLodaing;//是否正在加载数据,用来判断没加载完,不重复发起请求
    private boolean isNight;//是否是夜间模式
    private StringBuilder mContentBuidler;//缓存当前的内容

    public ReadView(Context context) {

        this(context, null);
    }

    public ReadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScreenWidth = ScreenUtils.getScreenWidth();
        mScreenHeight = ScreenUtils.getScreenHeight() - ScreenUtils.getStatusBarHeight(context);

        //加载背景图片
        rectF = new Rect(0, 0, mScreenWidth, mScreenHeight);
        mBookPageBg = BitmapFactory.decodeResource(getResources(), R.mipmap.theme_leather_bg);

        mCurPageBitmap = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.ARGB_8888);
        mNextPageBitmap = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.ARGB_8888);
        mPrePageBitmap = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.ARGB_8888);
        mCurrentPageCanvas = new Canvas(mCurPageBitmap);
        mNextPageCanvas = new Canvas(mNextPageBitmap);
        mPrePageCanvas = new Canvas(mPrePageBitmap);

        //创建标题画笔
        mTitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint.setAntiAlias(true);
        mTitlePaint.setTypeface(Typeface.DEFAULT_BOLD);//设置黑体
        //创建字体画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);

        mTitleSize = ScreenUtils.dpToPxInt(20);
        mFontSize = CacheUtils.getInt(context, Constants.FONT_SIZE, ScreenUtils.dpToPxInt(18));
        mTitlePaint.setTextSize(mTitleSize);
        reCalculate();

        changeMode(isNight);

        mContentBuidler = new StringBuilder();
    }

    /**
     * 重新设置字体的画笔大小,还有 行距,行数等的计算
     */
    public void reCalculate() {
        mLineSpace = mFontSize / 5 * 4;
        mPageLineCount = (mScreenHeight - paddingbottom - mTitleSize * 3) / (mFontSize + mLineSpace);
        mLineCount = (mScreenWidth - paddingLeft - paddingright) / (mFontSize);
        mPaint.setTextSize(mFontSize);

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
                        Toast.makeText(mContext, "mLoadpageListerner=null", Toast.LENGTH_SHORT).show();
                    }
                }

            }


        } else {
            Toast.makeText(mContext, "str=null", Toast.LENGTH_SHORT).show();
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
     *
     * @param title   章节名
     * @param content 正文
     * @param chapter 当前第几章
     * @param total   总的章节数
     */
    public void drawCurPageBitmap(String title, String content, int chapter, int total) {
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
            if (!isNight) {
                canvas.drawColor(Color.rgb(20, 20, 20));
            } else {
                canvas.drawColor(Color.WHITE);
            }
            //画标题
            canvas.drawText(title, mTitleSize, mTitleSize * 2, mTitlePaint);
            //画正文
            int y = mLineSpace + mFontSize + mTitleSize * 3;
            for (int i = 0; i < mPageLineCount; i++) {
                int x = paddingLeft;
                for (int j = 0; j < mLineCount; j++) {
                    if (mEndPos < content.length() && mEndPos >= 0) {
                        char c = content.charAt(mEndPos);
                        //绘制正文
                        canvas.drawText(String.valueOf(c), x, y, mPaint);
                        if (mContentBuidler != null) {
                            mContentBuidler.append(String.valueOf(c));
                        }
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

    /**
     * 重画当前页面
     *
     * @param canvas
     * @param content
     * @return
     */
    private boolean reDrawContent(Canvas canvas, String content) {
        if (content != null&&canvas!=null&&title!=null) {
            System.out.println("重画2");
            int position = 0;
            //画背景
            if (!isNight) {
                canvas.drawColor(Color.rgb(20, 20, 20));
            } else {
                canvas.drawColor(Color.WHITE);
            }
            //画标题
            canvas.drawText(title, mTitleSize, mTitleSize * 2, mTitlePaint);
            //画正文
            int y = mLineSpace + mFontSize + mTitleSize * 3;
            for (int i = 0; i < mPageLineCount; i++) {
                int x = paddingLeft;

                for (int j = 0; j < mLineCount; j++) {
                    if (position < content.length() && position >= 0) {
                        char c = content.charAt(position);
                        //绘制正文
                        canvas.drawText(String.valueOf(c), x, y, mPaint);

                        //每次加上文字的宽度
                        x += mFontSize;
                        //记录绘制的文字个数
                        position++;
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
                    if (mLoadPageListener != null) {
                        mLoadPageListener.showReadBar();
                    }
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

        /**
         * 显示阅读工具栏
         */
        void showReadBar();

    }

    public void setCurrentChapter(int currentChapter) {
        if (currentChapter > 0 && currentChapter < total) {
            this.currentChapter = currentChapter;
        }

    }

    //切换日/夜模式
    public void changeMode(boolean isNight) {
        this.isNight = isNight;
        if (!isNight) {
            mPaint.setColor(Color.rgb(215, 215, 215));
            mTitlePaint.setColor(Color.rgb(215, 215, 215));
        } else {
            mPaint.setColor(Color.BLACK);
            mTitlePaint.setColor(Color.BLACK);
        }
        reDrawContent();
    }

    public void reDrawContent() {
        if (mContentBuidler != null) {
            reDrawContent(mCurrentPageCanvas, mContentBuidler.toString());
            postInvalidate();
        }
    }


    /**
     * 设置是否加载完成
     *
     * @param lodaing
     */
    public void setLodaing(boolean lodaing) {
        isLodaing = lodaing;
    }

    public int getEndPos() {
        return mEndPos;
    }

    public void setEndPos(int endPos) {
        mEndPos = endPos;
    }

    public int getBeginPos() {
        return mBeginPos;
    }

    public void setBeginPos(int beginPos) {
        mBeginPos = beginPos;
    }

    public int getFontSize() {
        return mFontSize;
    }

    public void setFontSize(int fontSize) {
        mFontSize = fontSize;
        CacheUtils.putInt(mContext,Constants.FONT_SIZE,mFontSize);
    }
}
