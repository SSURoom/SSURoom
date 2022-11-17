package com.userinterface.ssuroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fab_main = findViewById(R.id.fab_main);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditActivity1.class);
                startActivity(intent);
            }
        });

        GridView gridView = findViewById(R.id.gridView);
        GridListAdapter adapter = new GridListAdapter();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reviews")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                adapter.addItem(new ReviewItem((String) data.get("tradeType"), (Long) data.get("rentCost"), (Long) data.get("depositCost"), (Long) data.get("area"), (Long) data.get("floor"), (String) data.get("address"), (Double) data.get("star")));
                            }

                            gridView.setAdapter(adapter);
                        } else {
                            Log.d("serverLoad", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}