package com.lyf.bookreader.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by ganlin on 2017/4/22.
 * 热门词汇和本地搜索记录的item
 */

public class SearchKeyView extends TextView {
    public SearchKeyView(Context context) {
        super(context);
    }

    public SearchKeyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchKeyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBackroundShapeColor(String color) {
//       Random random = new Random();
        GradientDrawable drawable = new GradientDrawable();// 形状-圆角矩形
        drawable.setShape(GradientDrawable.RECTANGLE);// 圆角
        drawable.setCornerRadius(8);
//        int alpha = 255;
//        int red = 55 + random.nextInt(100);
//        int green = 55 + random.nextInt(100);
//        int blue = 55 + random.nextInt(100);// 随机颜色
//        drawable.setColor(Color.argb(alpha, red, green, blue));
        drawable.setColor(Color.parseColor(color));
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
}
