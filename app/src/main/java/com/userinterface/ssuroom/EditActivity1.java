package com.userinterface.ssuroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;

public class EditActivity1 extends AppCompatActivity {

    EditText address;
    EditText phoneNumber;
    Button nextPage;
    RadioGroup isTrade;
    HashMap<String,Object> input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit1);

        nextPage = findViewById(R.id.bt1);
        address = findViewById(R.id.arr);
        phoneNumber = findViewById(R.id.pNumber);
        isTrade = findViewById(R.id.radiogroup1);

        input=new HashMap<>();

    }

    public void clickbtn(View view) {
        RadioButton btn=findViewById(isTrade.getCheckedRadioButtonId());
        input.put("address",address.getText().toString());
        input.put("isTrading",btn.getText().toString());
        input.put("phoneNum",Integer.parseInt(phoneNumber.getText().toString()));
        Intent intent = new Intent(getApplicationContext(), EditActivity2.class);
        intent.putExtra("data",input);
        startActivity(intent);
    }
}