package com.userinterface.ssuroom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String id=getIntent().getStringExtra("id");

        TextView address=findViewById(R.id.text1);
        TextView cost=findViewById(R.id.text2);
        TextView adminCost=findViewById(R.id.text3);
        Button isTrading=findViewById(R.id.button1);
        TextView roomType=findViewById(R.id.text9);
        TextView area=findViewById(R.id.text10);
        TextView floor=findViewById(R.id.text11);
        TextView review=findViewById(R.id.review);
        GridView option=findViewById(R.id.options);

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("reviews").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> data=document.getData();
                        address.setText((String)data.get("address"));
                        String costString=data.get("tradeType")+" "+data.get("depositCost")+"/"+data.get("rentCost");
                        cost.setText(costString);
                        adminCost.setText(""+data.get("adminCost"));
                        isTrading.setText(""+data.get("isTrading"));
                        roomType.setText(""+data.get("roomType"));
                        area.setText(""+data.get("area"));
                        floor.setText(""+data.get("floor"));
                        review.setText(""+data.get("review"));

                        ArrayList<String> optionData=(ArrayList<String>)data.get("option");
                        OptionsAdapter optionsAdapter=new OptionsAdapter(getApplicationContext(),R.layout.option_item,optionData);
                        option.setAdapter(optionsAdapter);
                        Log.d("FirebaseDetail", ""+document.getData());
                    } else {
                        Log.d("FirebaseDetail", "the review is not existed");
                    }
                } else {
                    Log.d("FirebaseDetail", "get failed with ", task.getException());
                }
            }
        });
    }
}