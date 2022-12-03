package com.userinterface.ssuroom.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.userinterface.ssuroom.DetailActivity;
import com.userinterface.ssuroom.R;
import com.userinterface.ssuroom.model.ReviewItem;

import java.util.ArrayList;

public class GridListAdapter extends BaseAdapter {
    ArrayList<ReviewItem> items = new ArrayList<ReviewItem>();
    Context context;
    FirebaseFirestore db;
    AppCompatActivity mainActivity;

    public GridListAdapter(AppCompatActivity activity) {
        db = FirebaseFirestore.getInstance();
        mainActivity = activity;
    }

    public void addItem(ReviewItem item) {
        items.add(item);
        this.notifyDataSetChanged();
    }

    public void clearItem() {
        items.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        ReviewItem item = items.get(i);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, viewGroup, false);
        }

        TextView itemTitle = view.findViewById(R.id.itemTitle);
        String content = item.getTradeType() + " " + item.getDepositCost() + " / " + item.getRentCost() + "\n" +
                item.getArea() + "평, " + item.getFloor() + "층\n" +
                item.getAddress();
        itemTitle.setText(content);

        RatingBar itemStar = view.findViewById(R.id.itemStar);
        itemStar.setRating((float) item.getStar());

        Button trading = view.findViewById(R.id.tradingButton);
        trading.setText(item.getIsTrading());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        CheckBox heart = view.findViewById(R.id.reviewHeart);
        heart.setChecked(item.isHeart());
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                item.setHeart(checked);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("reviews").document(item.getId())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        ArrayList<String> fans = (ArrayList<String>) document.getData().get("fans");
                                        if (fans.contains(user.getUid()))
                                            fans.remove(user.getUid());
                                        else
                                            fans.add(user.getUid());
                                        db.collection("reviews").document(item.getId())
                                                .update("fans", fans)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {

                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Log.d("firebase_heart", "DocumentSnapshot data: " + document.getData());

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d("firebase_heart", "get failed");

                                                    }
                                                });
                                    } else {
                                        Log.d("firebase_heart", "No such document");
                                    }
                                } else {
                                    Log.d("firebase_heart", "get failed with ", task.getException());
                                }
                            }
                        });
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, DetailActivity.class);
                intent.putExtra("id", item.getId());
                mainActivity.startActivity(intent);
            }
        });

        return view;
    }
}
