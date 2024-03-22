package com.example.asm_md18309;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    private EditText emailedit,passedit;
    private Button btn_login,btn_resister;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();
        emailedit = findViewById(R.id.email_login);
        passedit = findViewById(R.id.pass_login);
        btn_login = findViewById(R.id.btnlogin);
        btn_resister = findViewById(R.id.btnresis_lg);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        btn_resister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resister();
            }
        });
    }

    private void resister() {
        Intent i = new Intent(login.this,resignter.class);
        startActivity(i);
    }

    private void login() {
        String email,pass;
        email = emailedit.getText().toString();
        pass = passedit.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Bạn chưa nhập Email",Toast.LENGTH_SHORT).show();
            return;
        }if (TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Bạn chưa nhập mật khẩu",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(login.this, sanpham.class);
                    startActivity(intent);
                }else {
                    Log.w(TAG,"signInWithEmail:failure",task.getException());
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
