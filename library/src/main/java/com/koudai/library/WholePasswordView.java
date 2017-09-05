package com.koudai.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by abc on 2017/9/5.
 * 一个整体的密码输入框
 */

public class WholePasswordView extends BasePasswordView{

    private int mLineWidth;
    private int mGridColor;
    private int mLineColor;
    private int mCornerRadius;
    private Drawable mLineDrawable;
    private Drawable mOuterLineDrawable;

    public WholePasswordView(Context context) {
        this(context, null);
    }

    public WholePasswordView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WholePasswordView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.basePasswordView, defStyleAttr, 0);

        mLineWidth = (int) ta.getDimension(R.styleable.basePasswordView_gpvLineWidth, dp2px(getContext(), 1));
        mLineColor = ta.getColor(R.styleable.basePasswordView_gpvLineColor, DEFAULT_LINECOLOR);
        mGridColor = ta.getColor(R.styleable.basePasswordView_gpvGridColor, DEFAULT_GRIDCOLOR);
        mLineDrawable = ta.getDrawable(R.styleable.basePasswordView_gpvLineColor);
        if (mLineDrawable == null) {
            mLineDrawable = new ColorDrawable(mLineColor);
        }
        mCornerRadius = (int) ta.getDimension(R.styleable.basePasswordView_BaseCornerRadius, dp2px(getContext(), 5));
        mOuterLineDrawable = generateBackgroundDrawable();

        ta.recycle();

        setBackground(mOuterLineDrawable);
        setLine();
    }

    public void setBackground(int resId){
        setBackgroundResource(resId);
    }

    public void setLineColor(int lineColor){
        mLineColor = getResources().getColor(lineColor);
        mLineDrawable = new ColorDrawable(mLineColor);
        setLine();
    }

    private void setLine(){
        int index = 1;
        while (index < mPasswordLength) {
            // 添加分割线
            View dividerView = new View(getContext());
            LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(mLineWidth, LinearLayout.LayoutParams.MATCH_PARENT);
            dividerView.setBackgroundDrawable(mLineDrawable);
            addView(dividerView, dividerParams);

            TextView textView = mViewArr[index];
            removeView(textView);
            addView(textView);

            index++;
        }
    }

    private GradientDrawable generateBackgroundDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(mGridColor);
        drawable.setCornerRadius(mCornerRadius); // 设置圆角
        drawable.setStroke(mLineWidth, mLineColor);
        return drawable;
    }

}
