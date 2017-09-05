package com.koudai.library;

/**
 * Created by abc on 2017/9/5.
 */

public interface PasswordView {

    String getPassword();

    void clearPassword();

    void setPassword(String password);

    void setPasswordVisibility(boolean visible);

    void setOnPasswordChangedListener(OnPasswordChangedListener listener);

    void setPasswordType(PasswordType passwordType);

    interface OnPasswordChangedListener{

        void onTextChanged(String psw);

        void onInputFinish(String psw);

        void onClickSend(boolean isComplete, String psw);

    }

    enum PasswordType {
        NUMBER, TEXT, TEXTVISIBLE, TEXTWEB;
    }


}
