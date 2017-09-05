package com.koudai.library;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by abc on 2017/9/5.
 * 密码输入框
 */

public class BasePasswordView extends LinearLayout implements PasswordView{

    public static final int DEFAULT_PASSWORDLENGTH = 6;
    public static final int DEFAULT_TEXTSIZE = 16;
    public static final String DEFAULT_TRANSFORMATION = "●";
    public static final int DEFAULT_LINECOLOR = 0xaa888888;
    public static final int DEFAULT_GRIDCOLOR = 0xffffffff;

    private ColorStateList mTextColor;
    private int mTextSize = DEFAULT_TEXTSIZE;
    private int mPasswordType;

    private OnPasswordChangedListener mListener;
    private PasswordTransformationMethod mTransformationMethod;

    private String mPasswordTransformation = DEFAULT_TRANSFORMATION;

    public int mPasswordLength = DEFAULT_PASSWORDLENGTH; // 默认6位密码

    public String[] mPasswordArr;
    public TextView[] mViewArr;

    private ImeDelBugFixedEditText mInputView;

    public BasePasswordView(Context context) {
        this(context, null);
    }

    public BasePasswordView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasePasswordView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.basePasswordView, defStyleAttr, 0);

        mTextColor = ta.getColorStateList(R.styleable.basePasswordView_gpvTextColor);
        if (mTextColor == null)
            mTextColor = ColorStateList.valueOf(getResources().getColor(android.R.color.primary_text_light));
        int textSize = ta.getDimensionPixelSize(R.styleable.basePasswordView_gpvTextSize, -1);
        if (textSize != -1) {
            this.mTextSize = px2sp(context, textSize);
        }

        mPasswordLength = ta.getInt(R.styleable.basePasswordView_gpvPasswordLength, DEFAULT_PASSWORDLENGTH);
        mPasswordTransformation = ta.getString(R.styleable.basePasswordView_gpvPasswordTransformation);
        if (TextUtils.isEmpty(mPasswordTransformation))
            mPasswordTransformation = DEFAULT_TRANSFORMATION;


        mPasswordType = ta.getInt(R.styleable.basePasswordView_gpvPasswordType, 0);

        ta.recycle();


        mPasswordArr = new String[mPasswordLength];
        mViewArr = new TextView[mPasswordLength];

        mTextColor = ColorStateList.valueOf(getResources().getColor(android.R.color.primary_text_light));

    }

    private void initViews(Context context){
        // 设置方向为水平
        setOrientation(HORIZONTAL);

        mTransformationMethod = new BasePasswordTransformationMethod(mPasswordTransformation);

        // 布局basepasswordview中只有一个EditText，添加到当前视图
        mInputView = new ImeDelBugFixedEditText(context);
        // 隐藏光标
        mInputView.setCursorVisible(false);
        mInputView.setMaxEms(mPasswordLength);
        mInputView.addTextChangedListener(mTextWatcher);
        mInputView.setDelKeyEventListener(onDelKeyEventListener);
        // 监听软键盘-发送按钮
        mInputView.setImeOptions(EditorInfo.IME_ACTION_SEND);
        mInputView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    if(mListener != null){
                        boolean isComplete = getPassword().length() == mPasswordLength;
                        mListener.onClickSend(isComplete, getPassword());
                        return !isComplete;
                    }
                }
                return false;
            }
        });

        setCustomAttr(mInputView);
        mInputView.setBackground(null);
        mInputView.setGravity(Gravity.CENTER);
        LayoutParams inputViewParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
        addView(mInputView, inputViewParams);
        mViewArr[0] = mInputView;

        int index = 1;
        while(index < mPasswordLength){
            // 添加文本
            TextView textView = new TextView(context);
            setCustomAttr(textView);
            textView.setBackground(null);
            textView.setGravity(Gravity.CENTER);
            LayoutParams textViewParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
            addView(textView, textViewParams);

            mViewArr[index] = textView;
            index++;
        }

        setOnClickListener(mOnClickListener);

    }

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            forceInputViewGetFocus();
        }
    };

    private ImeDelBugFixedEditText.OnDelKeyEventListener onDelKeyEventListener = new ImeDelBugFixedEditText.OnDelKeyEventListener() {

        @Override
        public void onDeleteClick() {
            for (int i = mPasswordArr.length - 1; i >= 0; i--) {
                // 找到已输入的最后一个字符，将其置为null
                if (mPasswordArr[i] != null) {
                    mPasswordArr[i] = null;
                    mViewArr[i].setText(null);
                    notifyTextChanged();
                    break;
                } else {
                    mViewArr[i].setText(null);
                }
            }
        }
    };

    private void setCustomAttr(TextView view){
        if(mTextColor != null){
            view.setTextColor(mTextColor);
        }
        view.setTextSize(mTextSize);
        int inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD;
        switch (mPasswordType) {
            case 1:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                break;
            case 2:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
                break;
            case 3:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD;
                break;
        }
        view.setInputType(inputType);
        view.setTransformationMethod(mTransformationMethod);
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s)) {
                return;
            }

            String newStr = s.toString();
            if (newStr.length() == 1) { // 初次输入字符，赋值给索引0
                mPasswordArr[0] = newStr;
                notifyTextChanged();
            }
            // 非首次输入字符
            else if (newStr.length() == 2) {
                String newNum = newStr.substring(1); // 截取第2个字符
                for (int i = 0; i < mPasswordArr.length; i++) {
                    if (mPasswordArr[i] == null) {
                        mPasswordArr[i] = newNum; // 赋值给数组mPasswordArr，mPasswordArr中存储的就是最终的密码
                        mViewArr[i].setText(newNum); // 给TextView设值
                        notifyTextChanged(); // 通知密码发生改变
                        break;
                    }
                }
                // 上述逻辑执行完毕后，把索引0的字符设置给mInputView
                // 这样，之后再输入内容时，newStr.length()始终等于2。mInputView的内容长度始终等于1
                mInputView.removeTextChangedListener(this);
                mInputView.setText(mPasswordArr[0]);
                if (mInputView.getText().length() >= 1) {
                    mInputView.setSelection(1);
                }
                mInputView.addTextChangedListener(this);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void notifyTextChanged(){
        if(mListener == null){
            return;
        }

        String currentPsw = getPassword();
        mListener.onTextChanged(currentPsw);

        if(currentPsw.length() == mPasswordLength){
            mListener.onInputFinish(currentPsw);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putStringArray("passwordArr", mPasswordArr);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mPasswordArr = bundle.getStringArray("passwordArr");
            state = bundle.getParcelable("instanceState");
            mInputView.removeTextChangedListener(mTextWatcher);
            setPassword(getPassword());
            mInputView.addTextChangedListener(mTextWatcher);
        }
        super.onRestoreInstanceState(state);
    }


    /**
     * 打开软键盘
     * */
    private void forceInputViewGetFocus(){
        mInputView.setFocusable(true);
        mInputView.setFocusableInTouchMode(true);
        mInputView.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mInputView, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 打开软键盘
     * */
    public void openSoftKey(){
        forceInputViewGetFocus();
    }

    @Override
    public String getPassword() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<mPasswordLength; i++){
            if(mPasswordArr[i] != null){
                sb.append(mPasswordArr[i]);
            }
        }
        return sb.toString();
    }

    @Override
    public void clearPassword() {
        for (int i=0; i<mPasswordArr.length; i++){
            mPasswordArr[i] = null;
            mViewArr[i].setText(null);
        }
    }

    @Override
    public void setPassword(String password) {
        clearPassword();
        if(TextUtils.isEmpty(password)){
            return;
        }
        char[] pswArr = password.toCharArray();
        for (int i=0; i<pswArr.length; i++){
            if(i < mPasswordArr.length){
                mViewArr[i].setText(mPasswordArr[i]);
            }
        }
    }

    @Override
    public void setPasswordVisibility(boolean visible) {
        for (TextView textView : mViewArr){
            textView.setTransformationMethod(visible ? null : mTransformationMethod);
            if(textView instanceof EditText){
                EditText et = (EditText) textView;
                et.setSelection(et.getText().length());
            }
        }
    }

    @Override
    public void setOnPasswordChangedListener(OnPasswordChangedListener listener) {
        mListener = listener;
    }

    @Override
    public void setPasswordType(PasswordType passwordType) {
        boolean visible = getPasswordVisibility();
        int inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD;
        switch (passwordType) {
            case TEXT:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                break;
            case TEXTVISIBLE:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
                break;
            case TEXTWEB:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD;
                break;
        }
        for (TextView textView : mViewArr){
            textView.setInputType(inputType);
        }
        setPasswordVisibility(visible);
    }

    private boolean getPasswordVisibility(){
        return mViewArr[0].getTransformationMethod() == null;
    }

    public int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }
}
