package com.userinterface.ssuroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

public class EditActivity4 extends AppCompatActivity {

    RatingBar star;
    EditText review;
    float rating;
    String review1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit4);

        star = findViewById(R.id.star);
        review = findViewById(R.id.review);

        //rating = star.getRating();
        //review1 = review.getText().toString();
    }

    public void clickbtn(View view) {
        Intent intent = new Intent(getApplicationContext(), EditActivity5.class);
        startActivity(intent);
    }
}