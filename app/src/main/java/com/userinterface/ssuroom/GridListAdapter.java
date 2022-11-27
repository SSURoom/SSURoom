package com.userinterface.ssuroom;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
        String content = item.tradeType + " " + item.depositCost + " / " + item.rentCost + "\n" +
                item.area + "평, " + item.floor + "층\n" +
                item.address;
        itemTitle.setText(content);

        RatingBar itemStar = view.findViewById(R.id.itemStar);
        itemStar.setRating((float) item.star);

        Button trading = view.findViewById(R.id.tradingButton);
        trading.setText(item.getIsTrading());

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
