package com.example.graduateproject;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graduateproject.R;
import com.example.graduateproject.RetrofitClient;
import com.example.graduateproject.ServiceApi;
import com.example.graduateproject.JoinData;
import com.example.graduateproject.JoinResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private ProgressBar mProgressView;
    private ServiceApi service;
    EditText idText;
    EditText passwordText;
    Button registerBtn;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idText = (EditText) findViewById(R.id.idText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        registerBtn = (Button) findViewById(R.id.RegisterBtn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        mProgressView = (ProgressBar) findViewById(R.id.join_progress);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        registerBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                attemptLogin();
            }
        });
    }
    private void attemptLogin() {
        idText.setError(null);
        passwordText.setError(null);

        String id = idText.getText().toString();
        String password = passwordText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            passwordText.setError("비밀번호를 입력해주세요.");
            focusView = idText;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            passwordText.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = passwordText;
            cancel = true;
        }
        // 이메일의 유효성 검사
        if (id.isEmpty()) {
            idText.setError("아이디를 입력해주세요.");
            focusView = idText;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            startLogin(new LoginData(id, password));
            showProgress(true);
        }
    }

    private void startLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);
                if (result.getCode() == 200) {
                Intent loginIntent = new Intent(MainActivity.this, MenuActivity.class);
                loginIntent.putExtra("userid", result.getUserId());
                System.out.println("보낼아이디"+result.getUserId());
                MainActivity.this.startActivity(loginIntent);}
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
                showProgress(false);
            }
        });
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }
    private void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
