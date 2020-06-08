package com.example.graduateproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button timesettingBtn = (Button) findViewById(R.id.timesettigBtn);
        Button patientimgBtn = (Button) findViewById(R.id.patientimgBtn);
        Button patientlocBtn = (Button) findViewById(R.id.patientlocBtn);
        Button logoutBtn = (Button) findViewById(R.id.logoutBtn);
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        System.out.println("아이디"+userid);
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
                System.out.println("메인에서보낼아이디"+userid);
                MenuActivity.this.startActivity(timesettingIntent);
            }
        });

        patientimgBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent patientimgIntent = new Intent(MenuActivity.this, PatientImgActivity.class);
                MenuActivity.this.startActivity(patientimgIntent);
            }
        });

        patientlocBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent patientlocIntent = new Intent(MenuActivity.this, PatientLocationActivity.class);
                MenuActivity.this.startActivity(patientlocIntent);
            }
        });
    }
}
