package com.userinterface.ssuroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView idTv, pwTv;
    private Button loginBtn,signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        loginBtn = findViewById(R.id.login);
        signupBtn = findViewById(R.id.signup);
        loginBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
        idTv = findViewById(R.id.id);
        pwTv = findViewById(R.id.password);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //로그인 안되있는 경우?
        }
    }

    @Override
    public void onClick(View view) {

        if(view==signupBtn) {
            Intent intent=new Intent(getApplicationContext(),SignupActivity.class);
            startActivity(intent);
            //finish() 뒤로가기 막고싶으면 쓰는듯
        }
        else if(view==loginBtn){
            Log.d("login","start login");
            mAuth.signInWithEmailAndPassword(idTv.getText().toString(), pwTv.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("login", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("login", "signInWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(),"비밀번호 또는 이메일을 확인해주세요.",Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        }
    }
}