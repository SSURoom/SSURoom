package com.userinterface.ssuroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity1 extends AppCompatActivity {

    EditText address;
    EditText phoneNumber;
    Button nextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit1);

        nextPage = findViewById(R.id.bt1);
        address = findViewById(R.id.arr);
        phoneNumber = findViewById(R.id.pNumber);

        //String arr = address.getText().toString();
        //int pNumber = Integer.parseInt(address.getText().toString());

    }

    public void clickbtn(View view) {
        Intent intent = new Intent(getApplicationContext(), EditActivity2.class);
        startActivity(intent);
    }
}