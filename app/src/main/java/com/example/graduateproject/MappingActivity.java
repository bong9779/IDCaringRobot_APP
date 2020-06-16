package com.example.graduateproject;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MappingActivity extends AppCompatActivity {
    int i=0;
    Handler handler = new Handler();
    private Socket socket;
    String userid;
    String patient_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping);
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        patient_id = intent.getStringExtra("patient_id");
        ImageButton logoutBtn = (ImageButton) findViewById(R.id.logoutBtn);
        final ImageButton mappingstartbtn = (ImageButton) findViewById(R.id.mappingstartbtn); // 매핑시작
        ImageButton returnbtn = (ImageButton) findViewById(R.id.returnBtn);

        ImageButton mapping_image = (ImageButton) findViewById(R.id.mapping_image); //현재 mapping 상황 확인
        ImageButton front = (ImageButton) findViewById(R.id.front);
        ImageButton back = (ImageButton) findViewById(R.id.back);
        ImageButton right = (ImageButton) findViewById(R.id.right);
        ImageButton left = (ImageButton) findViewById(R.id.left);

        try {
            socket = IO.socket("http://172.16.108.178:3030"); // 내 서버 ip 넣기, localhost와 같은 공공ip는 입력불가능.
            socket.on(Socket.EVENT_CONNECT, (Object... objects) -> {
                socket.emit("joinRoom_app", "room2");
                socket.on("mappingsave_complete", imagesave_mapping);
            });
            socket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logoutBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Toast.makeText(MappingActivity.this, "로그아웃 되셨습니다", Toast.LENGTH_LONG).show();
                Intent logoutIntent = new Intent(MappingActivity.this, MainActivity.class);
                MappingActivity.this.startActivity(logoutIntent);
            }
        });
        returnbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent ReturnIntent = new Intent(MappingActivity.this, MenuActivity.class);
                ReturnIntent.putExtra("userid", userid);
                ReturnIntent.putExtra("patient_id",patient_id);
                MappingActivity.this.startActivity(ReturnIntent);
            }
        });

        mappingstartbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                i++;

                if(i%2!=0){
                    mappingstartbtn.setImageResource(R.drawable.mappingstop);
                }else{
                    mappingstartbtn.setImageResource(R.drawable.mappingstart);
                }

                socket.emit("mappingstart","mappingstart");
            }
        });

        mapping_image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                socket.emit("imagesave_mapping","imagesave_mapping");
            }
        });

        front.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                socket.emit("front","front");
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                socket.emit("back","back");
            }
        });
        right.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                socket.emit("right","right");
            }
        });
        left.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                socket.emit("left","left");
            }
        });



    }

    protected Emitter.Listener imagesave_mapping = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        final ImageView iv = (ImageView)findViewById(R.id.imageview_mapping);
                        URL url = new URL("https://mymapimagesave.s3.ap-northeast-2.amazonaws.com/mapping/mapping");
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