package com.example.concerts_booking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPass extends AppCompatActivity {
    TextView Emailsend;
    Button sendnewpass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        Emailsend=findViewById(R.id.resetpass);
        sendnewpass=findViewById(R.id.send_email_btn);
        mAuth=FirebaseAuth.getInstance();

        sendnewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = Emailsend.getText().toString();

                if (TextUtils.isEmpty(useremail)){
                    Toast.makeText(ForgetPass.this, "Please Enter your Email", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ForgetPass.this, "Please Check your Email account ", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ForgetPass.this , MainActivity.class);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(ForgetPass.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();

                            }
                        }


                    });
                }
            }
        });

    }
}
