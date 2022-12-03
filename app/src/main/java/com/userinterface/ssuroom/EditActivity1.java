package com.userinterface.ssuroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.userinterface.ssuroom.fragment.SearchDialogFragment;

import java.util.HashMap;

public class EditActivity1 extends AppCompatActivity {

    EditText address;
    EditText phoneNumber;
    Button nextPage;
    RadioGroup isTrade;
    HashMap<String,Object> input;
    SearchDialogFragment searchDialogFragment;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit1);

        nextPage = findViewById(R.id.bt1);
        address = findViewById(R.id.arr);
        phoneNumber = findViewById(R.id.pNumber);
        isTrade = findViewById(R.id.radiogroup1);

        input=new HashMap<>();
        searchDialogFragment=new SearchDialogFragment();
        manager=getSupportFragmentManager();

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!searchDialogFragment.isVisible()){
                    searchDialogFragment.show(manager,null);
                }
            }
        });
    }

    public void clickbtn(View view) {
        String addStr=address.getText().toString();
        String phoneNumStr=phoneNumber.getText().toString();
        if(addStr.isEmpty()||phoneNumStr.isEmpty()){
            Toast.makeText(getApplicationContext(),"모든 값을 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton btn=findViewById(isTrade.getCheckedRadioButtonId());
        input.put("address",addStr);
        input.put("isTrading",btn.getText().toString());
        input.put("phoneNum",phoneNumber.getText().toString());

        Intent intent = new Intent(getApplicationContext(), EditActivity2.class);
        intent.putExtra("data",input);
        startActivity(intent);
    }
}