package com.userinterface.ssuroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class EditActivity3 extends AppCompatActivity {

    RadioGroup structure;
    EditText area;
    EditText floor;
    int area1;
    int floor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit3);
        //토글 버튼, 라디오 버튼 id 해야함
        structure = findViewById(R.id.radiogroup3);
        area = findViewById(R.id.area);
        floor = findViewById(R.id.floor);

        //area1 = Integer.parseInt(area.getText().toString());
        //floor1 = Integer.parseInt(floor.getText().toString());
    }

    public void clickbtn(View view) {
        Intent intent = new Intent(getApplicationContext(), EditActivity4.class);
        startActivity(intent);
    }
}