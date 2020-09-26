package com.example.dropdart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class signup extends AppCompatActivity {
    private EditText EmailId, Password;
    private Button btnSignUp;
    private TextView SignIn;
    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth = FirebaseAuth.getInstance();
        EmailId = (EditText)findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        btnSignUp  = (Button) findViewById(R.id.signup);
        SignIn = (TextView) findViewById(R.id.SignIn);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EmailId.getText().toString();
                String password = Password.getText().toString();
                if (email.isEmpty()){
                    EmailId.setError("Please enter Email Address");
                    EmailId.requestFocus();
                }
                else if (password.isEmpty()){
                    Password.setError("please enter a valid Password");
                    Password.requestFocus();
                }
                else if (password.isEmpty() && email.isEmpty()){
                    Toast.makeText(signup.this, "Fields are Empty", Toast.LENGTH_SHORT).show();
                }
                else if (!password.isEmpty() && !email.isEmpty()){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(signup.this, "signup unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //Redirect to Home activity
                                startActivity(new Intent(signup.this, HomePage.class));
                            }
                        }
                    });
                }else{
                    Toast.makeText(signup.this, "Error Occured!!, Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this, login.class));
            }
        });
    }

}