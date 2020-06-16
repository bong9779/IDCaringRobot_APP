package com.example.graduateproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

public class PatientImgActivity extends AppCompatActivity {
    String userid, patient_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_img);
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        patient_id = intent.getStringExtra("patient_id");
        ImageButton returnBtn = (ImageButton) findViewById(R.id.returnBtn);
        ImageButton logoutBtn = (ImageButton) findViewById(R.id.logoutBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(PatientImgActivity.this, "로그아웃 되셨습니다", Toast.LENGTH_LONG).show();
                Intent logoutIntent = new Intent(PatientImgActivity.this, MainActivity.class);
                PatientImgActivity.this.startActivity(logoutIntent);
            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent ReturnIntent = new Intent(PatientImgActivity.this, MenuActivity.class);
                ReturnIntent.putExtra("userid", userid);
                ReturnIntent.putExtra("patient_id",patient_id);
                PatientImgActivity.this.startActivity(ReturnIntent);
            }
        });
    }
}

