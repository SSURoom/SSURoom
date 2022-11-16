package com.userinterface.ssuroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;

public class EditActivity3 extends AppCompatActivity {

    RadioGroup structure;
    EditText area;
    EditText floor;
    int area1;
    int floor1;
    HashMap<String, Object> input;
    ViewGroup optionView;
    ToggleButton[] options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit3);
        //토글 버튼, 라디오 버튼 id 해야함
        structure = findViewById(R.id.radiogroup3);
        area = findViewById(R.id.area);
        floor = findViewById(R.id.floor);
        optionView = findViewById(R.id.optionView);
        options = new ToggleButton[optionView.getChildCount()];
        for (int i = 0; i < optionView.getChildCount(); i++)
            options[i] = (ToggleButton) optionView.getChildAt(i);

        input = (HashMap<String, Object>) getIntent().getSerializableExtra("data");

        //area1 = Integer.parseInt(area.getText().toString());
        //floor1 = Integer.parseInt(floor.getText().toString());
    }

    public void clickbtn(View view) {
        String areaStr = area.getText().toString();
        String floorStr = floor.getText().toString();
        if (areaStr.isEmpty() || floorStr.isEmpty()) {
            Toast.makeText(getApplicationContext(), "모든 값을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton btn = findViewById(structure.getCheckedRadioButtonId());
        input.put("roomType", btn.getText().toString());
        input.put("area", Integer.parseInt(areaStr));
        input.put("floor", Integer.parseInt(floorStr));

        ArrayList<String> selectOptions = new ArrayList<>();
        for (int i = 0; i < optionView.getChildCount(); i++) {
            if (options[i].isChecked())
                selectOptions.add(options[i].getText().toString());
        }
        input.put("option", selectOptions);

        Intent intent = new Intent(getApplicationContext(), EditActivity4.class);
        intent.putExtra("data", input);
        startActivity(intent);
    }
}