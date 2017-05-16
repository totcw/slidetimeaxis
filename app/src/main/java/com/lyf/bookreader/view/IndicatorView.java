package com.lyf.bookreader.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyf.bookreader.R;


/**
 *
 *底部导航
 * @author Administrator
 */
public class IndicatorView extends LinearLayout {

    private ImageView mIvBottm;
    private TextView mTvBottom;
    private int normalColor;
    private int selectColor;
    private int normaliv;
    private int selectiv;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.bottom_layout, this);
      //  bottom_line = findViewById(R.id.bottom_line);
        mIvBottm = (ImageView) findViewById(R.id.iv_bottom);
        mTvBottom = (TextView) findViewById(R.id.tv_bottom);
    }

    /**
     *
     *
     * @param title
     */
    public void setTitle(String title) {
        mTvBottom.setText(title);
    }

    /**
     *
     *
     * @param normaliv
     * @param selectiv
     */
    public void setIvBackground(int normaliv, int selectiv) {
        this.normaliv = normaliv;
        this.selectiv = selectiv;
        //
        mIvBottm.setBackgroundResource(normaliv);

    }

    /**
     *
     *
     * @param normalColor
     * @param selectColor
     */
    public void setLineBackground(int normalColor, int selectColor) {
        this.normalColor = normalColor;
        this.selectColor = selectColor;
        //
     //   bottom_line.setBackgroundColor(normalColor);
        mTvBottom.setTextColor(normalColor);

    }

    /**
     *
     *
     * @param selected
     */
    public void setTabSelected(boolean selected) {
        if (selected) {
          //  bottom_line.setBackgroundColor(selectColor);
          mTvBottom.setTextColor(selectColor);
            mIvBottm.setBackgroundResource(selectiv);
        } else {
          //  bottom_line.setBackgroundColor(normalColor);
            mTvBottom.setTextColor(normalColor);
            mIvBottm.setBackgroundResource(normaliv);
        }
    }

    public ImageView getIvBottm() {
        return mIvBottm;
    }

    public TextView getTvBottom() {
        return mTvBottom;
    }
}
