package com.lyf.bookreader.view.refreshload;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lyf.bookreader.R;


@SuppressLint("NewApi")
public class CommonProgressBar extends RelativeLayout {
    public Context mContext = null;
    public LayoutInflater mInflater;
    public Drawable mLoadingAnimation;
    public ImageView mLoadingView;

    public CommonProgressBar(Context paramContext) {
        super(paramContext);
        init(paramContext);
    }

    public CommonProgressBar(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init(paramContext);
    }

    public CommonProgressBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init(paramContext);
    }

    private void refreshAnim() {
        try {
            if ((this.mLoadingAnimation != null) && (this.mLoadingView != null)) {
                if (this.mLoadingView.isShown()) {
                    startAnimation(this.mLoadingAnimation);
                    return;
                }
                stopAnimation(this.mLoadingAnimation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setLoadingAnim(Drawable paramDrawable) {
        if ((paramDrawable instanceof AnimationDrawable))
            if (Build.VERSION.SDK_INT < 16)
                this.mLoadingView.setBackgroundDrawable(this.mLoadingAnimation);
        if (Build.VERSION.SDK_INT == 16)
            this.mLoadingView.setLayerType(1, null);
        this.mLoadingView.setImageDrawable(this.mLoadingAnimation);
        this.mLoadingView.setAlpha(0);
    }

    private void startLoadingAnim(Drawable paramDrawable) {
        if ((paramDrawable instanceof KeyframesDrawable))
            ((KeyframesDrawable) paramDrawable).startAnimation();
    }

    private void stopLoadingAnim(Drawable paramDrawable) {
        if ((paramDrawable instanceof KeyframesDrawable))
            ((KeyframesDrawable) this.mLoadingAnimation).stopAnimation();
    }

    public void init(Context paramContext) {
        this.mContext = paramContext;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mInflater.inflate(R.layout.common_loading, this);
        this.mLoadingView = ((ImageView) findViewById(R.id.img));
        if (this.mLoadingAnimation != null)
            stopAnimation(this.mLoadingAnimation);
        this.mLoadingAnimation = Tool.get(this.mContext, "loading_animation_from_bottom");
        setLoadingAnim(this.mLoadingAnimation);
        startLoadingAnim(this.mLoadingAnimation);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        try {
            if ((this.mLoadingAnimation != null) && (this.mLoadingView != null))
                stopAnimation(this.mLoadingAnimation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopAnimation(Drawable paramDrawable) {
        if (paramDrawable == null) {
            return;
        }
        if ((paramDrawable instanceof KeyframesDrawable)) {
            ((KeyframesDrawable) paramDrawable).stopAnimation();
            return;
        }
    }

    public void startAnimation(Drawable paramDrawable) {
        if (paramDrawable == null) {
            return;
        }
        if ((paramDrawable instanceof KeyframesDrawable)) {
            ((KeyframesDrawable) paramDrawable).startAnimation();
            return;
        }
    }

    protected void onVisibilityChanged(View paramView, int paramInt) {
        super.onVisibilityChanged(paramView, paramInt);
        refreshAnim();
    }

    protected void onWindowVisibilityChanged(int paramInt) {
        super.onWindowVisibilityChanged(paramInt);
        refreshAnim();
    }

    public final void startProgress() {
        String str = "loading_animation_from_bottom";
        if (this.mLoadingAnimation != null)
            stopAnimation(this.mLoadingAnimation);
        this.mLoadingAnimation = Tool.get(this.mContext, str);
        setLoadingAnim(this.mLoadingAnimation);
        startLoadingAnim(this.mLoadingAnimation);
        mLoadingView.setVisibility(View.VISIBLE);
    }

    public void setVisibility(int paramInt) {
        super.setVisibility(paramInt);
        if ((paramInt == View.VISIBLE) && (this.mLoadingView != null) && (this.mLoadingView.isShown()))
            startAnimation(this.mLoadingAnimation);
    }

    public void stopAnim() {
        this.mLoadingView.clearAnimation();
        stopAnimation(this.mLoadingAnimation);
        mLoadingView.setVisibility(View.GONE);
    }
}