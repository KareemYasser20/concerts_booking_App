package com.example.concerts_booking_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class HomePage extends AppCompatActivity {
    EditText useremail, userpass;
    Button login , googlelogin , phonelogin ;
    LoginButton facebooklogin;
    TextView forgetpass , signup;
    static final int Google_Sigin=123;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    GoogleSignInClient mgoogleSignInClient;
   private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        login = findViewById(R.id.login_btn);
        googlelogin =findViewById(R.id.google_login_btn);
        phonelogin=findViewById(R.id.phonelogin_btn);
        facebooklogin=findViewById(R.id.FBlogin_btn);
        forgetpass=findViewById(R.id.Forget_pass);
        signup=findViewById(R.id.Signup_homepage);
        useremail=findViewById(R.id.userEmail);
        userpass=findViewById(R.id.Password);
        callbackManager = CallbackManager.Factory.create();
        facebooklogin.setReadPermissions(Arrays.asList("email"));

        facebooklogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent =new Intent(HomePage.this , ConcertPlace.class);
                startActivity(intent);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

















        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mgoogleSignInClient = GoogleSignIn.getClient(this , googleSignInOptions);
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forget = new Intent(HomePage.this , ForgetPass.class);
                startActivity(forget);
            }
        });
        googlelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sigingoogle();
            }
        });

        phonelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this , PhoneSigIn.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInUser(useremail.getText().toString() , userpass.getText().toString());


            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this , SignupActivity.class);
                startActivity(intent);
            }
        });

    }
    private void SignInUser(String email , String password){
        auth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent partyplace = new Intent(HomePage.this , ConcertPlace.class);
                    startActivity(partyplace);
                }
                else {
                    Toast.makeText(HomePage.this, "SignIn Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sigingoogle (){
        Intent googleintent = mgoogleSignInClient.getSignInIntent();
        startActivityForResult(googleintent , Google_Sigin );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode ,data);
        if (requestCode == Google_Sigin){
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);


            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null ) FirebaseAuthWithGoogle(account);
                if (task.isSuccessful()){
                    Intent intent = new Intent(HomePage.this , ConcertPlace.class);
                    startActivity(intent);
                }
            }catch (ApiException e){
                e.printStackTrace();

            }
        }
    }

    //google
    private void FirebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG" , " Fire base Auth With Google: " + account.getId() );
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential).addOnCompleteListener(this , new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("TAG", " Sig in Success");
                    FirebaseUser user = auth.getCurrentUser();

                } else {

                    Log.w("TAG", "sig in failed" + task.getException());
                    Toast.makeText(HomePage.this, "SigIn Failed", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }



}