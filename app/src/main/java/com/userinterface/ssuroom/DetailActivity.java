package com.userinterface.ssuroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String id = getIntent().getStringExtra("id");

        TextView address = findViewById(R.id.text1);
        TextView cost = findViewById(R.id.text2);
        TextView adminCost = findViewById(R.id.text3);
        Button isTrading = findViewById(R.id.button1);
        TextView roomType = findViewById(R.id.text9);
        TextView area = findViewById(R.id.text10);
        TextView floor = findViewById(R.id.text11);
        TextView review = findViewById(R.id.review);
        GridView option = findViewById(R.id.options);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("reviews").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> data = document.getData();
                        address.setText((String) data.get("address"));
                        String costString = data.get("tradeType") + " " + data.get("depositCost") + "/" + data.get("rentCost");
                        cost.setText(costString);
                        adminCost.setText("" + data.get("adminCost"));
                        isTrading.setText("" + data.get("isTrading"));
                        roomType.setText("" + data.get("roomType"));
                        area.setText("" + data.get("area"));
                        floor.setText("" + data.get("floor"));
                        review.setText("" + data.get("review"));

                        ArrayList<String> optionData = (ArrayList<String>) data.get("option");
                        OptionsAdapter optionsAdapter = new OptionsAdapter(getApplicationContext(), R.layout.option_item, optionData);
                        option.setAdapter(optionsAdapter);
                        Log.d("FirebaseDetail", "" + document.getData());
                    } else {
                        Log.d("FirebaseDetail", "the review is not existed");
                    }
                } else {
                    Log.d("FirebaseDetail", "get failed with ", task.getException());
                }
            }
        });



        Button commentBtn = findViewById(R.id.commentBtn);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText commentEt = findViewById(R.id.commentContent);
                String content = commentEt.getText().toString();

                if (content.length() < 1) {
                    Toast.makeText(getApplicationContext(), "댓글 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                HashMap<String, Object> commentData = new HashMap<>();
                commentData.put("uid", user.getUid());
                commentData.put("createdAt", System.currentTimeMillis());
                commentData.put("postId", id);
                commentData.put("content", content);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("comments").document()
                        .set(commentData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),"댓글을 등록에 성공했습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),DetailActivity.class);
                                intent.putExtra("id",id);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"댓글 등록에 실패했습니다.",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}