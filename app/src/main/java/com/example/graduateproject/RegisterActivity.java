package com.example.graduateproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.graduateproject.R;
import com.example.graduateproject.RetrofitClient;
import com.example.graduateproject.ServiceApi;
import com.example.graduateproject.JoinData;
import com.example.graduateproject.JoinResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.socket.client.IO;
import io.socket.client.Socket;
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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private ProgressBar mProgressView;
    private ServiceApi service;
    EditText idText;
    EditText passwordText;
    EditText textName;
    EditText textPatientID;
    EditText emailText;
    EditText phoneText;
    EditText macText;
    ImageButton registerBtn;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        idText = (EditText) findViewById(R.id.idText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        textName = (EditText) findViewById(R.id.textName);
        textPatientID = (EditText) findViewById(R.id.textPatientID);
        emailText = (EditText) findViewById(R.id.emailText);
        registerBtn = (ImageButton) findViewById(R.id.RegisterBtn);
        phoneText = (EditText) findViewById(R.id.phoneText);
        macText = (EditText) findViewById(R.id.macText);
        mProgressView = (ProgressBar) findViewById(R.id.join_progress);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        registerBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                attemptJoin();
            }
        });
    }
    private void attemptJoin() {
        textName.setError(null);
        passwordText.setError(null);
        idText.setError(null);
        textPatientID.setError(null);
        emailText.setError(null);
        phoneText.setError(null);
        macText.setError(null);
        String name = textName.getText().toString();
        String id = idText.getText().toString();
        String password = passwordText.getText().toString();
        String patientid = textPatientID.getText().toString();
        String email = emailText.getText().toString();
        String phone = phoneText.getText().toString();
        String mac = macText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            passwordText.setError("비밀번호를 입력해주세요.");
            focusView = passwordText;
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

        // 이름의 유효성 검사
        if (name.isEmpty()) {
            textName.setError("이름을 입력해주세요.");
            focusView = textName;
            cancel = true;
        }

        // 환자번호 유효성 검사
        if (patientid.isEmpty()) {
            textPatientID.setError("환자번호를 입력해주세요.");
            focusView = textPatientID;
            cancel = true;
        }

        // 이메일 유효성 검사
        if (email.isEmpty()) {
            emailText.setError("이메일을 입력해주세요.");
            focusView = emailText;
            cancel = true;
        }

        if (phone.isEmpty()) {
            phoneText.setError("번호를 입력해주세요.");
            focusView = phoneText;
            cancel = true;
        }

        if (mac.isEmpty()) {
            macText.setError("맥주소를 입력해주세요.");
            focusView = macText;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            System.out.println("아이디"+id+"이름"+name+"비번"+password+"환자번호"+patientid+"이메일"+email);
            startJoin(new JoinData(name, id, password, patientid, email, phone, mac));
            showProgress(true);
        }

    }

    private void startJoin(JoinData data) {
        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(RegisterActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);
                if (result.getCode() == 200) {
                    Intent loginIntent = new Intent(RegisterActivity.this, MainActivity.class);
                    RegisterActivity.this.startActivity(loginIntent);
                    finish();
                }

            }
            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
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

