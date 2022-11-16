package com.userinterface.ssuroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditActivity5 extends AppCompatActivity {

    Button bt1;
    Button bt2;
    Button bt3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit5);

        bt1 = findViewById(R.id.camera1);
        bt2 = findViewById(R.id.camera2);
        bt3 = findViewById(R.id.camera3);

    }
    //넘어가는 액티비티 다시 설정하기
    public void clickbtn(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void cameraopen(View view) {
        //클릭시 카메라 앱 오픈 구현하기.
    }
}