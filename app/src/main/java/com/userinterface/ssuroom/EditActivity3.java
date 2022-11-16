package com.userinterface.ssuroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;

public class EditActivity3 extends AppCompatActivity {

    RadioGroup structure;
    EditText area;
    EditText floor;
    int area1;
    int floor1;
    HashMap<String,Object> input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit3);
        //토글 버튼, 라디오 버튼 id 해야함
        structure = findViewById(R.id.radiogroup3);
        area = findViewById(R.id.area);
        floor = findViewById(R.id.floor);

        input=(HashMap<String, Object>) getIntent().getSerializableExtra("data");

        //area1 = Integer.parseInt(area.getText().toString());
        //floor1 = Integer.parseInt(floor.getText().toString());
    }

    public void clickbtn(View view) {
        RadioButton btn=findViewById(structure.getCheckedRadioButtonId());
        input.put("roomType",btn.getText().toString());
        input.put("area",Integer.parseInt(area.getText().toString()));
        input.put("floor",Integer.parseInt(floor.getText().toString()));
        Intent intent = new Intent(getApplicationContext(), EditActivity4.class);
        intent.putExtra("data",input);
        startActivity(intent);
    }
}