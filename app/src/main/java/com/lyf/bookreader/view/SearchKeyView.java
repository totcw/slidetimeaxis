package com.lyf.bookreader.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.github.lzyzsd.randomcolor.RandomColor;
import com.lyf.bookreader.utils.ScreenUtils;
import com.lyf.bookreader.utils.UiUtils;

import java.util.Random;

/**
 * Created by gan lin on 2017/4/22.
 * 热门词汇和本地搜索记录的item
 */

public class SearchKeyView extends AppCompatTextView {

    public SearchKeyView(Context context) {
        this(context, null);
    }

    public SearchKeyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchKeyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBackgroundShapeColor() {
        GradientDrawable drawable = new GradientDrawable();// 形状-圆角矩形
        drawable.setShape(GradientDrawable.RECTANGLE);// 圆角
        drawable.setCornerRadius(8);
        RandomColor randomColor = new RandomColor();
        drawable.setColor(randomColor.randomColor());
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(8);
        gradientDrawable.setColor(Color.GRAY);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, gradientDrawable);
        stateListDrawable.addState(new int[]{}, drawable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(stateListDrawable);
        } else {
            setBackgroundDrawable(stateListDrawable);
        }
    }

    public void init(String history) {
        setTextColor(Color.parseColor("#fafafa"));
        ViewGroup.MarginLayoutParams params =
                new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 10;
        params.bottomMargin = 10;
        params.topMargin = 10;
        params.rightMargin = 10;
        setPadding(10, 5, 10, 5);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        setLayoutParams(params);
        setBackgroundShapeColor();
        setText(history);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.postTextOnClick(getText().toString());
                }
            }
        });

//        setOnLongClickListener(new OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                showDeleteIcon();
//                return true;
//            }
//
//
//        });
    }

    private void showDeleteIcon() {

    }

    /*点击后将文本回调出去用于显示在搜索框*/
    public interface CallBackOnClick {
        void postTextOnClick(String text);

//        void deleteHistoryOnLongClick();
    }

    CallBackOnClick callBack;

    public void setCallBackOnClick(CallBackOnClick callBack) {
        this.callBack = callBack;
    }


}
