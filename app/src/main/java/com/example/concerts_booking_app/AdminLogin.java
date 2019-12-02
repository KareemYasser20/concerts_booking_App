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

public class AdminLogin extends AppCompatActivity {

    EditText Adminemail , Adminpass;
    Button Adminlogin;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        Adminemail=findViewById(R.id.AdminEmail);
        Adminpass=findViewById(R.id.AdminPassword);
        Adminlogin=findViewById(R.id.Admin_login_btn);

        Adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Adminemail.getText().toString().equals("admin@gmail.com")||Adminemail.getText().toString().equals("Admin@gmail.com")) {
                    SignInUser(Adminemail.getText().toString() , Adminpass.getText().toString());
                }

            }
        });



    }

    private void SignInUser(String email , String password){
        auth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent AddParty = new Intent(AdminLogin.this , AddParty.class);
                    startActivity(AddParty);
                }
                else {
                    Toast.makeText(AdminLogin.this, "SignIn Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

