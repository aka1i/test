package com.example.wechat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.wechat.Utils.OnlineUtils;

public class LoginActivity extends AppCompatActivity {
    Button mLoginButton;
    EditText mUserName;
    EditText mPassword;
    String mUserNameText;
    String mPasswordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    void init(){
        mLoginButton = findViewById(R.id.login);
        mUserName = findViewById(R.id.login_username);
        mPassword = findViewById(R.id.login_password);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserNameText = mUserName.getText().toString();
                mPasswordText = mPassword.getText().toString();
                login(mUserNameText,mPasswordText);
            }
        });
    }

    private void login(String username,String password){
        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    LoginActivity.this.finish();
                    OnlineUtils.synchronizeToNet();
                    OnlineUtils.synchronizeFromNet();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "登入失败（可能账户名或密码错误）", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext,LoginActivity.class);
        return intent;
    }
}
