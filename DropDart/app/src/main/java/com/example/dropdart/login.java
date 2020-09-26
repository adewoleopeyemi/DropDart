package com.example.dropdart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    private EditText emailID, Password;
    private Button btnLogin, btnSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailID = (EditText) findViewById(R.id.LoginEmail);
        Password = (EditText)findViewById(R.id.LoginPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnSignUp = (Button)findViewById(R.id.btnSignup);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null){
                    Toast.makeText(login.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent homepageIntent = new Intent(login.this, HomePage.class);
                    startActivity(homepageIntent);
                }
                else{
                    Toast.makeText(login.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
        setOnClickListeners();
    }
    private void setOnClickListeners(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(login.this, signup.class);
                startActivity(signupIntent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailID.getText().toString();
                String password = Password.getText().toString();
                if (email.isEmpty()){
                    emailID.setError("Please enter Email Address");
                    emailID.requestFocus();
                }
                else if (password.isEmpty()){
                    Password.setError("please enter a valid Password");
                    Password.requestFocus();
                }
                else if (password.isEmpty() && email.isEmpty()){
                    Toast.makeText(login.this, "Fields are Empty", Toast.LENGTH_SHORT).show();
                }
                else if (!password.isEmpty() && !email.isEmpty()){
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(login.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intToHome = new Intent(login.this, HomePage.class);
                                startActivity(intToHome);
                            }
                        }
                    });

                }else{
                    Toast.makeText(login.this, "Error Occured!!, Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}