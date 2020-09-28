package com.example.dropdart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class signup extends AppCompatActivity {
    private EditText EmailId, Password;
    private Button btnSignUp;
    private TextView SignIn;
    ProgressDialog progressDialog;

    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Create Account");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        mFirebaseAuth = FirebaseAuth.getInstance();
        EmailId = (EditText)findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        btnSignUp  = (Button) findViewById(R.id.signup);
        SignIn = (TextView) findViewById(R.id.SignIn);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");
        setOnClickListeners();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void setOnClickListeners() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = EmailId.getText().toString();
                String password = Password.getText().toString();
                if (email.isEmpty()){
                    EmailId.setError("Please enter Email Address");
                    EmailId.setFocusable(true);
                }
                else if (password.isEmpty()){
                    Password.setError("please enter a valid Password");
                    Password.setFocusable(true);
                }
                else if (password.isEmpty() && email.isEmpty()){
                    Toast.makeText(signup.this, "Fields are Empty", Toast.LENGTH_SHORT).show();
                }
                else if (!password.isEmpty() && !email.isEmpty()){
                    progressDialog.show();
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                progressDialog.dismiss();
                                FirebaseUser User = mFirebaseAuth.getCurrentUser();

                                String email = User.getEmail();
                                String uid = User.getUid();
                                HashMap<Object, String> hashMap = new HashMap<>();
                                hashMap.put("email", email);
                                hashMap.put("uid", uid);
                                hashMap.put("name", "");
                                hashMap.put("phone", "");
                                hashMap.put("image", "");

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("Users");
                                reference.child(uid).setValue("hashMap");

                                Toast.makeText(signup.this, "signup unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                progressDialog.dismiss();
                                //Redirect to Home activity
                                startActivity(new Intent(signup.this, DashboardActivity.class));
                                finish();
                            }
                        }
                    });
                }else{
                    progressDialog.dismiss();
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