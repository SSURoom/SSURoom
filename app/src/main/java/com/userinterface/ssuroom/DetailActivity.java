package com.userinterface.ssuroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.userinterface.ssuroom.adapter.CommentAdapter;
import com.userinterface.ssuroom.adapter.OptionsAdapter;
import com.userinterface.ssuroom.model.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    private static class TIME_MAXIMUM {
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }


    ListView commentList;
    CommentAdapter commentAdapter;
    String id;//postId
    InputMethodManager imm;

    Adapter adapter;
    ViewPager viewPager;

    private String calculateTime(long regTime) {
        long curTime = System.currentTimeMillis();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_detail);

        viewPager = (ViewPager) findViewById(R.id.view);
        adapter = new Adapter(this);
        viewPager.setAdapter(adapter);


        id = getIntent().getStringExtra("id");

        TextView address = findViewById(R.id.text1);
        TextView cost = findViewById(R.id.text2);
        TextView adminCost = findViewById(R.id.text3);
        Button isTrading = findViewById(R.id.button1);
        TextView roomType = findViewById(R.id.text9);
        TextView area = findViewById(R.id.text10);
        TextView floor = findViewById(R.id.text11);
        TextView review = findViewById(R.id.review);
        GridView option = findViewById(R.id.options);
        TextView phoneTv = findViewById(R.id.phonenumber);
        TextView timeTv = findViewById(R.id.text4);
        commentList = findViewById(R.id.commentList);
        commentAdapter = new CommentAdapter();
        commentList.setAdapter(commentAdapter);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        String pNum = phoneTv.getText().toString();
        Button callBt = findViewById(R.id.calling);
        callBt.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + pNum));
            //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(String.valueOf(phoneTv)));
            startActivity(intent);
        });


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("reviews").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("final_log","DB 게시글 정보 가져오기");
                        Map<String, Object> data = document.getData();
                        address.setText((String) data.get("address"));
                        String costString = data.get("tradeType") + " " + data.get("depositCost") + "/" + data.get("rentCost");
                        cost.setText(costString);
                        adminCost.setText("관리비 " + data.get("adminCost") + "만원");
                        isTrading.setText("" + data.get("isTrading"));
                        roomType.setText("" + data.get("roomType"));
                        area.setText("" + data.get("area") + "평");
                        floor.setText("" + data.get("floor") + "층");
                        review.setText("" + data.get("review"));
                        phoneTv.setText("" + data.get("phoneNum"));
                        timeTv.setText(calculateTime((long) data.get("createdAt")));

                        ArrayList<String> optionData = (ArrayList<String>) data.get("option");
                        OptionsAdapter optionsAdapter = new OptionsAdapter(getApplicationContext(), R.layout.option_item, optionData);
                        option.setAdapter(optionsAdapter);

                        ArrayList<String> fileName = (ArrayList<String>) data.get("imgs");
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        byte[][] imgData=new byte[3][];
                        storageRef.child("image").child(fileName.get(0))
                                .getBytes(10*1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        Log.d("final_log","1번째 사진 다운로드");
                                        imgData[0]=bytes;
                                        storageRef.child("image").child(fileName.get(1))
                                                .getBytes(10*1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                    @Override
                                                    public void onSuccess(byte[] bytes) {
                                                        Log.d("final_log","2번째 사진 다운로드");
                                                        imgData[1]=bytes;
                                                        storageRef.child("image").child(fileName.get(2))
                                                                .getBytes(10*1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                                    @Override
                                                                    public void onSuccess(byte[] bytes) {
                                                                        Log.d("final_log","3번째 사진 다운로드");
                                                                        imgData[2]=bytes;
                                                                        adapter = new Adapter(getApplicationContext(),imgData);
                                                                        viewPager.setAdapter(adapter);
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
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
                commentData.put("name", user.getDisplayName());


                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("comments").document()
                        .set(commentData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                commentAdapter.clear();

                                loadComments(1);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "댓글 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        loadComments(0);

    }


    private void loadComments(int n) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("comments")
                .whereEqualTo("postId", id)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                Log.d("firebase_comment", data.toString());
                                commentAdapter.addItem(new Comment((String) data.get("name"), (String) data.get("content"), (long) data.get("createdAt")));
                                if (n == 1) {
                                    //Toast.makeText(getApplicationContext(),"댓글을 등록했습니다.",Toast.LENGTH_SHORT).show();
                                    EditText commentEt = findViewById(R.id.commentContent);
                                    commentEt.setText("");

                                    imm.hideSoftInputFromWindow(commentEt.getWindowToken(), 0);
                                }
                            }
                            setListViewHeightBasedOnChildren(commentList);
                            Log.d("firebase_comment", "" + commentAdapter.getCount());
                        } else {
                            Log.d("serverLoad", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}