package com.userinterface.ssuroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;

public class EditActivity2 extends AppCompatActivity {

    RadioGroup category;
    EditText monthPrice;
    EditText stackPrice;
    EditText servicePrice;
    int monthPrice1;
    int stackPrice1;
    int servicePrice1;
    int state;
    HashMap<String,Object> input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit2);


        category = findViewById(R.id.radiogroup2);
        monthPrice = findViewById(R.id.monthPrice);
        stackPrice = findViewById(R.id.stackPrice);
        servicePrice = findViewById(R.id.sevicePrice);

        input=(HashMap<String, Object>) getIntent().getSerializableExtra("data");

        //monthPrice1 = Integer.parseInt(monthPrice.getText().toString());
        //stackPrice1 = Integer.parseInt(stackPrice.getText().toString());
        //servicePrice1 = Integer.parseInt(servicePrice.getText().toString());
    }


    public void clickbtn (View view){
        String rentStr=monthPrice.getText().toString();
        String depositStr=stackPrice.getText().toString();
        String adminStr=servicePrice.getText().toString();
        if(rentStr.isEmpty()||depositStr.isEmpty()||adminStr.isEmpty()){
            Toast.makeText(getApplicationContext(),"모든 값을 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton btn=findViewById(category.getCheckedRadioButtonId());
        input.put("tradeType",btn.getText().toString());
        input.put("rentCost",Integer.parseInt(rentStr));
        input.put("depositCost",Integer.parseInt(depositStr));
        input.put("adminCost",Integer.parseInt(adminStr));
        Intent intent = new Intent(getApplicationContext(), EditActivity3.class);
        intent.putExtra("data",input);
        startActivity(intent);
    }

    /*
    RadioGroup.OnCheckedChangeListener radioGroupClickListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (i == R.id.monthpay) {                // 첫 번째 버튼이 선택 되었을 때
                state = 1;
            } else if (i == R.id.overpay) {      // 두 번째 버튼이 선택 되었을 때
                state = 2;
            } else {
                state = 3;
            }
            //추가 코드 작성 필요
        }
    };
     */
}