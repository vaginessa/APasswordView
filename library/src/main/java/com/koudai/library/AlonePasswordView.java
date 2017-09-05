package com.koudai.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by abc on 2017/9/5.
 * 单独输入框
 */

public class AlonePasswordView extends BasePasswordView{

    private int mMargin;
    private Drawable mItemDrawable;

    public AlonePasswordView(Context context) {
        this(context, null);
    }

    public AlonePasswordView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlonePasswordView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.basePasswordView, defStyleAttr, 0);
        mMargin = (int) ta.getDimension(R.styleable.basePasswordView_BaseItemMargin, dp2px(getContext(), 1));
        mItemDrawable = ta.getDrawable(R.styleable.basePasswordView_BaseItemBackground);
        ta.recycle();

        setItemBackground(mItemDrawable);
        setMargin(mMargin);
    }

    public void setItemBackground(int resId){
        for (TextView textView : mViewArr){
            textView.setBackgroundResource(resId);
        }
    }

    public void setItemBackground(Drawable drawable){
        for (TextView textView : mViewArr){
            textView.setBackgroundDrawable(drawable);
        }
    }

    public void setMargin(int margin){
        margin = dp2px(getContext(), margin);
        for (TextView textView : mViewArr){
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.leftMargin = margin;
        }
    }

}
