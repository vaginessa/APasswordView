package com.koudai.passwordview.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.koudai.library.AlonePasswordView;
import com.koudai.library.PasswordView;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private AlonePasswordView mAlonePasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        mAlonePasswordView = (AlonePasswordView) findViewById(R.id.pswView1);
        mAlonePasswordView.setPasswordVisibility(true);


        mAlonePasswordView.setOnPasswordChangedListener(new PasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                Log.d(TAG, "onTextChanged()--, psw = " + psw);
            }

            @Override
            public void onInputFinish(String psw) {
                Log.d(TAG, "onInputFinish()--, psw = " + psw);
            }

            @Override
            public void onClickSend(boolean isComplete, String psw) {
                Log.d(TAG, "onClickSend()--, isComplete = " + isComplete + ", psw = " + psw);
            }
        });
    }

}
