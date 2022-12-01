package com.userinterface.ssuroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class EditActivity5 extends AppCompatActivity {

    Button bt1;
    Button bt2;
    Button bt3;
    HashMap<String, Object> input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit5);

        bt1 = findViewById(R.id.camera1);
        bt2 = findViewById(R.id.camera2);
        bt3 = findViewById(R.id.camera3);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        input = (HashMap<String, Object>) getIntent().getSerializableExtra("data");
        input.put("createdAt",System.currentTimeMillis());
        input.put("fans",new ArrayList<String>());
        input.put("uid",user.getUid());
    }

    //넘어가는 액티비티 다시 설정하기
    public void clickbtn(View view) {
        Log.d("inputData", input.toString());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reviews").document()
                .set(input)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"리뷰를 등록했습니다.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Log.d("server", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.w("server", "Error writing document", e);
                    }
                });

    }

    public void cameraopen(View view) {
        //클릭시 카메라 앱 오픈 구현하기.
    }
}