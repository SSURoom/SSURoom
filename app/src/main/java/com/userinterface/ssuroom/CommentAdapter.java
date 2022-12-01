package com.userinterface.ssuroom;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    private static class TIME_MAXIMUM {
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }

    ArrayList<Comment> comments = new ArrayList<>();

    public void addItem(Comment comment) {
        comments.add(comment);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private String calculateTime(int i) {
        Comment comment = comments.get(i);
        long curTime = System.currentTimeMillis();
        long regTime = comment.getCreatedAt();
        long diffTime = (curTime - regTime) / 1000;

        String msg;

        if (diffTime < TIME_MAXIMUM.SEC) {
            // sec
            msg = diffTime + "초전";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            // min
            System.out.println(diffTime);
            msg = diffTime + "분전";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            // hour
            msg = (diffTime) + "시간전";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            // day
            msg = (diffTime) + "일전";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            // day
            msg = (diffTime) + "달전";
        } else {
            msg = (diffTime) + "년전";
        }
        return msg;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        Comment comment = comments.get(i);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.comment_item, viewGroup, false);
        }

        TextView writerTv = view.findViewById(R.id.commentWriter);
        TextView timeTv = view.findViewById(R.id.commentTime);
        TextView textTv = view.findViewById(R.id.commentText);

        writerTv.setText(comment.getName());
        timeTv.setText(calculateTime(i));
        textTv.setText(comment.getComment());

        return view;
    }
}
