package com.userinterface.ssuroom.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.userinterface.ssuroom.R;

import java.util.ArrayList;

public class OptionsAdapter extends ArrayAdapter<String> {
    Context context;
    int resId;
    ArrayList<String> data;

    public OptionsAdapter(@NonNull Context context, int resource, ArrayList<String> data) {
        super(context, resource);
        this.context = context;
        this.resId = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resId, null);
        }

        final String str = data.get(position);
        Button btn = convertView.findViewById(R.id.optionName);
        btn.setText(str);


        return convertView;
    }
}
