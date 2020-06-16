package com.example.graduateproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {
    String userid;
    String patient_id;
    ServiceApi service;
    TextView feedbackView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ImageButton timesettingBtn = (ImageButton) findViewById(R.id.timesettigBtn);
        ImageButton patientimgBtn = (ImageButton) findViewById(R.id.patientimgBtn);
        ImageButton patientlocBtn = (ImageButton) findViewById(R.id.patientlocBtn);
        ImageButton logoutBtn = (ImageButton) findViewById(R.id.logoutBtn);
        ImageButton mappigBtn = (ImageButton) findViewById(R.id.mappingbtn);
        feedbackView = (TextView) findViewById(R.id.feedbackView);
        service = RetrofitClient.getClient().create(ServiceApi.class);
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        patient_id = intent.getStringExtra("patient_id");
        System.out.println("아이디"+userid);
        if(patient_id != "") {
            System.out.println(patient_id+"!!!");
            getFeed(new BoardGetData(patient_id));
        }
        logoutBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Toast.makeText(MenuActivity.this, "로그아웃 되셨습니다", Toast.LENGTH_LONG).show();
                Intent logoutIntent = new Intent(MenuActivity.this, MainActivity.class);
                MenuActivity.this.startActivity(logoutIntent);
            }
        });

        timesettingBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent timesettingIntent = new Intent(MenuActivity.this, TimeSettingActivity.class);
                timesettingIntent.putExtra("userid", userid);
                timesettingIntent.putExtra("patient_id",patient_id);
                System.out.println("메인에서보낼아이디"+patient_id+userid);
                MenuActivity.this.startActivity(timesettingIntent);
            }
        });

        patientimgBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent patientimgIntent = new Intent(MenuActivity.this, PatientImgActivity.class);
                patientimgIntent.putExtra("userid", userid);
                patientimgIntent.putExtra("patient_id",patient_id);
                System.out.println("메인에서보낼아이디"+patient_id+userid);
                MenuActivity.this.startActivity(patientimgIntent);
            }
        });

        patientlocBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent patientlocIntent = new Intent(MenuActivity.this, PatientLocationActivity.class);
                patientlocIntent.putExtra("userid", userid);
                patientlocIntent.putExtra("patient_id",patient_id);
                System.out.println("메인에서보낼아이디"+patient_id+userid);
                MenuActivity.this.startActivity(patientlocIntent);
            }
        });

        mappigBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent mappingIntent = new Intent(MenuActivity.this, MappingActivity.class);
                mappingIntent.putExtra("userid", userid);
                mappingIntent.putExtra("patient_id",patient_id);
                System.out.println("메인에서보낼아이디"+patient_id+userid);
                MenuActivity.this.startActivity(mappingIntent);
            }
        });
    }
    public void getFeed(BoardGetData data) {
        service.userBoardGet(data).enqueue(new Callback<BoardGetResponse>() {
            @Override
            public void onResponse(Call<BoardGetResponse> call, Response<BoardGetResponse> response) {
                BoardGetResponse result = response.body();
                Toast.makeText(MenuActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                feedbackView.setText(result.getContents());
            }
            @Override
            public void onFailure(Call<BoardGetResponse> call, Throwable t) {
                Toast.makeText(MenuActivity.this, "이름 check 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("이름 check 에러 발생", t.getMessage());
            }
        });
    }
}
