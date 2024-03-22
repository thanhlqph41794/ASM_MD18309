package com.example.asm_md18309;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class resignter extends AppCompatActivity {
    private EditText emailedit,passedit;
    private Button btn_resister;
    private FirebaseAuth mAuth;
    ImageButton btnback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resignter);
        btnback = findViewById(R.id.btn_back_res);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(resignter.this,login.class);
                startActivity(i);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        emailedit = findViewById(R.id.Email_resis);
        passedit = findViewById(R.id.Pass_resis);
        btn_resister = findViewById(R.id.btnresis);
        btn_resister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resister();
            }
        });
    }

    private void resister() {
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
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(resignter.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Log.w(TAG,"createUserWithEmail:failure",task.getException());
                    Toast.makeText(resignter.this, "Tạo tài khoản không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
