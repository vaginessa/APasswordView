package com.koudai.library;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

/**
 * Created by abc on 2017/9/5.
 */

public class BasePasswordTransformationMethod extends PasswordTransformationMethod {

    String mTransformation;

    public BasePasswordTransformationMethod(String transformation){
        mTransformation = transformation;
    }

    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new PasswordCharSequence(source);
    }

    private class PasswordCharSequence implements CharSequence {
        CharSequence mSource;

        public PasswordCharSequence(CharSequence source){
            mSource = source;
        }

        @Override
        public int length() {
            return mSource.length();
        }

        @Override
        public char charAt(int index) {
            return mTransformation.charAt(0);
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return mSource.subSequence(start, end);
        }
    }
}
