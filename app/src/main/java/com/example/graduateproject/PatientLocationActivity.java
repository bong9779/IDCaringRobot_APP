package com.example.graduateproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class PatientLocationActivity extends AppCompatActivity {

    Handler handler = new Handler();
    private Socket socket;
    String userid, patient_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_location);
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        patient_id = intent.getStringExtra("patient_id");
        ImageButton returnBtn = (ImageButton) findViewById(R.id.returnBtn);
        ImageButton logoutBtn = (ImageButton) findViewById(R.id.logoutBtn);
        ImageButton locationBtn = (ImageButton) findViewById(R.id.locationBtn);

        try {
            socket = IO.socket("http://172.16.108.178:3030"); // 내 서버 ip 넣기, localhost와 같은 공공ip는 입력불가능.
            socket.on(Socket.EVENT_CONNECT, (Object... objects) -> {
                socket.emit("joinRoom_app", "room2");
            });
            socket.on("imageview", imagesave);
            socket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logoutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(PatientLocationActivity.this, "로그아웃 되셨습니다", Toast.LENGTH_LONG).show();
                Intent logoutIntent = new Intent(PatientLocationActivity.this, MainActivity.class);
                PatientLocationActivity.this.startActivity(logoutIntent);
            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent ReturnIntent = new Intent(PatientLocationActivity.this, MenuActivity.class);
                ReturnIntent.putExtra("userid", userid);
                ReturnIntent.putExtra("patient_id",patient_id);
                PatientLocationActivity.this.startActivity(ReturnIntent);
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                socket.emit("imagesave_app", "1");
            }
        });

    }

    protected Emitter.Listener imagesave = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        final ImageView iv = (ImageView)findViewById(R.id.imageview);
                        URL url = new URL("https://mymapimagesave.s3.ap-northeast-2.amazonaws.com/robotlocate/robotlocate");
                        InputStream is = url.openStream();
                        final Bitmap bm = BitmapFactory.decodeStream(is);
                        handler.post(new Runnable() {

                            @Override
                            public void run() {  // 화면에 그려줄 작업
                                iv.setImageBitmap(bm);
                            }
                        });
                        iv.setImageBitmap(bm); //비트맵 객체로 보여주기
                    } catch(Exception e){ }
                }
            });

            t.start();
        }
    };
}
