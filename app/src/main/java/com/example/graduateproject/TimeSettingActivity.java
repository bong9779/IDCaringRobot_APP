package com.example.graduateproject;

import androidx.appcompat.app.AppCompatActivity;
import com.example.graduateproject.R;
import com.example.graduateproject.RetrofitClient;
import com.example.graduateproject.ServiceApi;
import com.example.graduateproject.TimeData;
import com.example.graduateproject.TimeResponse;
import com.example.graduateproject.TimeGetResponse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeSettingActivity extends AppCompatActivity {
    Button returnBtn;
    Button saveBtn;
    Button delBtn;
    Button logoutBtn;
    EditText mednameText;
    EditText medtimeText;
    TextView invisiblebtn;
    private ServiceApi service;
    String[] medNameApp;
    String[] medTimeApp;
    String userid;
    ListView timenameText;

    int code=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_setting);
        returnBtn = (Button) findViewById(R.id.returnBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        mednameText = (EditText)findViewById(R.id.mednameText);
        medtimeText = (EditText)findViewById(R.id.medtimeText);
        timenameText = (ListView) findViewById(R.id.timenameText);
        delBtn = (Button) findViewById(R.id.delBtn);
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        service = RetrofitClient.getClient().create(ServiceApi.class);
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        System.out.println("받은 아이디"+userid);
        GetTimeSetting(new TimeGetData(userid));

        saveBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                timeSetting();
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Toast.makeText(TimeSettingActivity.this, "로그아웃 되셨습니다", Toast.LENGTH_LONG).show();
                Intent logoutIntent = new Intent(TimeSettingActivity.this, MainActivity.class);
                TimeSettingActivity.this.startActivity(logoutIntent);
            }
        });
        returnBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent ReturnIntent = new Intent(TimeSettingActivity.this, MenuActivity.class);
                ReturnIntent.putExtra("userid", userid);
                TimeSettingActivity.this.startActivity(ReturnIntent);
            }
        });
    }
    private void timeSetting() {
        mednameText.setError(null);
        medtimeText.setError(null);
        String mname = mednameText.getText().toString();
        String mtime = medtimeText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 약이름의 유효성 검사
        if (mname.isEmpty()) {
            mednameText.setError("약 이름을 입력해주세요.");
            focusView = mednameText;
            cancel = true;
        }

        // 약주기의 유효성 검사
        if (mtime.isEmpty()) {
            medtimeText.setError("약 주기를 입력해주세요.");
            focusView = medtimeText;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            System.out.println("약이름"+mname+"약주기"+mtime);
            CheckTimeSetting(new MednameData(mname,mtime,userid));

        }

    }

    private void StartTimeSetting(TimeData data) {
        service.userTime(data).enqueue(new Callback<TimeResponse>() {
            @Override
            public void onResponse(Call<TimeResponse> call, Response<TimeResponse> response) {
                TimeResponse result = response.body();
                Toast.makeText(TimeSettingActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                GetTimeSetting(new TimeGetData(userid));

            }
            @Override
            public void onFailure(Call<TimeResponse> call, Throwable t) {
                Toast.makeText(TimeSettingActivity.this, "등록 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("등록 에러 발생", t.getMessage());
            }
        });
    }
    private void CheckTimeSetting(MednameData data) {
        service.userTime(data).enqueue(new Callback<MednameResponse>() {
            @Override
            public void onResponse(Call<MednameResponse> call, Response<MednameResponse> response) {
                MednameResponse result = response.body();
                Toast.makeText(TimeSettingActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                StartTimeSetting(new TimeData(result.getMedName(),result.getMedTime(),userid));
            }
            @Override
            public void onFailure(Call<MednameResponse> call, Throwable t) {
                Toast.makeText(TimeSettingActivity.this, "이름 check 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("이름 check 에러 발생", t.getMessage());
            }
        });
    }
    private void Delmedtable(DelmedData data) {
        service.userTime(data).enqueue(new Callback<DelmedResponse>() {
            @Override
            public void onResponse(Call<DelmedResponse> call, Response<DelmedResponse> response) {
                DelmedResponse result = response.body();
                Toast.makeText(TimeSettingActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("삭제");
            }
            @Override
            public void onFailure(Call<DelmedResponse> call, Throwable t) {
                Toast.makeText(TimeSettingActivity.this, "이름 check 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("삭제 에러 발생", t.getMessage());
            }
        });
    }


    private void GetTimeSetting(TimeGetData data) {
        service.userGetTime(data).enqueue(new Callback<TimeGetResponse>() {
            @Override
            public void onResponse(Call<TimeGetResponse> call, Response<TimeGetResponse> response) {
                final List<String> data = new ArrayList<>();
                final ArrayAdapter<String> adp = new ArrayAdapter<>( getApplicationContext(),android.R.layout.simple_list_item_single_choice,data);
                timenameText.setAdapter(adp);
                TimeGetResponse result = response.body();
                for(int i=0; i<result.getLength();i++){
                medNameApp = result.medNameApp();
                String medname = medNameApp[i];
                medTimeApp = result.medTimeApp();
                String medtime = medTimeApp[i];
                code =result.getCode();
                System.out.println(medtime);
                System.out.println(medname);
                data.add("약 이름: "+medname+"  약 주기: "+medtime);}
                delBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        int pos = timenameText.getCheckedItemPosition();
                        if(pos != ListView.INVALID_POSITION){
                            data.remove(pos);
                            System.out.println("pos값"+pos+userid);
                            timenameText.clearChoices();
                            adp.notifyDataSetChanged();
                            Delmedtable(new DelmedData(pos, userid));
                        }
                    }
                });

            }
            @Override
            public void onFailure(Call<TimeGetResponse> call, Throwable t) {
                Toast.makeText(TimeSettingActivity.this, "찾기 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("찾기 에러 발생", t.getMessage());
            }
        });

    }

}
