package com.example.concerts_booking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth m_auth ;
    EditText UserEmail , UserPass , UserRepass;
    Button Signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        m_auth=FirebaseAuth.getInstance();
        UserEmail = findViewById(R.id.userEmail);
        UserPass= findViewById(R.id.Password);
        UserRepass = findViewById(R.id.REPassword);
        Signup=findViewById(R.id.Signup_btn_signup_page);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserPass.getText().toString().equals(UserRepass.getText().toString())){
                    signup(UserEmail.getText().toString() , UserPass.getText().toString());
                }  else {
                    Toast.makeText(SignupActivity.this, "The Password does not match", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void signup (String Email , String pass){
        m_auth.createUserWithEmailAndPassword(Email,pass ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "The User Created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this , MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(SignupActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
