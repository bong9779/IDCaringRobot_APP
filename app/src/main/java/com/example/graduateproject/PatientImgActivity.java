package com.example.graduateproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PatientImgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_img);

        Button returnBtn = (Button) findViewById(R.id.returnBtn);
        Button logoutBtn = (Button) findViewById(R.id.logoutBtn);

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
                PatientImgActivity.this.startActivity(ReturnIntent);
            }
        });
    }
}

