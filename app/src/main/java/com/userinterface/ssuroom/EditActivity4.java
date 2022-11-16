package com.userinterface.ssuroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.HashMap;

public class EditActivity4 extends AppCompatActivity {

    RatingBar star;
    EditText review;
    float rating;
    String review1;
    HashMap<String, Object> input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit4);

        star = findViewById(R.id.star);
        review = findViewById(R.id.review);

        input = (HashMap<String, Object>) getIntent().getSerializableExtra("data");

        //rating = star.getRating();
        //review1 = review.getText().toString();
    }

    public void clickbtn(View view) {
        String reviewStr = review.getText().toString();
        if (reviewStr.isEmpty()) {
            Toast.makeText(getApplicationContext(), "모든 값을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        input.put("review", reviewStr);
        input.put("star", star.getRating());
        Intent intent = new Intent(getApplicationContext(), EditActivity5.class);
        intent.putExtra("data", input);
        startActivity(intent);
    }
}