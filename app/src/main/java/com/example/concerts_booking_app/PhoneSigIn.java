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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneSigIn extends AppCompatActivity {

    EditText phone_number , codeEntereded ;
    Button getverificationcode , login_phonephage;
    FirebaseAuth m_auth;
    String Codesent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_sig_in);
        phone_number=findViewById(R.id.phonenumber);
        codeEntereded=findViewById(R.id.Code_Received);
        getverificationcode=findViewById(R.id.verification_Code_btn);
        login_phonephage=findViewById(R.id.login_btn_phone_page);
        m_auth= FirebaseAuth.getInstance();
        getverificationcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sendverificationcode();

            }
        });

        login_phonephage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifiysigincode();

            }
        });

    }
    private void verifiysigincode(){
        String code = codeEntereded.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(Codesent, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        m_auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent =new Intent(PhoneSigIn.this , ConcertPlace.class);
                            startActivity(intent);

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(PhoneSigIn.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                });
    }

    private void Sendverificationcode(){
        String Phone = phone_number.getText().toString();
        if (Phone.isEmpty()){
            Toast.makeText(this, "Enter Phone Number Please", Toast.LENGTH_SHORT).show();
            phone_number.requestFocus();
        }

        if (Phone.length() <11){
            Toast.makeText(this, "Please Enter Valid Phone Number ", Toast.LENGTH_SHORT).show();
            phone_number.requestFocus();
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                Phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Codesent =s;

        }
    };
}
