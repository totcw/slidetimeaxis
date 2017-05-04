package com.lyf.bookreader.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by gan lin on 2017/4/22.
 * 热门词汇和本地搜索记录的item
 */

public class SearchKeyView extends AppCompatTextView {
    public SearchKeyView(Context context) {
        this(context,null);
    }

    public SearchKeyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
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

    public void init(String history) {
        setText(history);
    }

}
