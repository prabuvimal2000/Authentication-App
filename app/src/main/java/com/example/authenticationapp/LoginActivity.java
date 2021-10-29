package com.example.authenticationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText emailAddress,password;
    Button loginButton;
    TextView newUser,forgetPasswordLink;
    ProgressBar progressBar;
    FirebaseAuth fireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailAddress= findViewById(R.id.userEmail);
        password=  findViewById(R.id.userPassword);
        loginButton= findViewById(R.id.loginClick);
        newUser= findViewById(R.id.newUser);
        forgetPasswordLink=findViewById(R.id.forgetPassword);
        progressBar = findViewById(R.id.progressBar);
        fireBase = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email= emailAddress.getText().toString().trim();
                String passwordCheck= password.getText().toString().trim();
               // String mobileNumber = phoneNumber.getText().toString().trim();
                if(TextUtils.isEmpty(email))
                {
                    emailAddress.setError("Email is required ");
                    return;
                }

                if(TextUtils.isEmpty(passwordCheck))
                {
                    password.setError("Password is required");
                    return;
                }
              /*  if(TextUtils.isEmpty(mobileNumber)  || mobileNumber.length()<10)
                {
                    password.setError("Please enter the valid mobile number");
                    return;
                }*/
                if(TextUtils.isEmpty(email))
                {
                    emailAddress.setError("Email is required ");
                    return;
                }

                if(TextUtils.isEmpty(passwordCheck))
                {
                    password.setError("Password is required");
                    return;
                }

                if(passwordCheck.length()<6)
                {
                    password.setError("Password must be greater then 6 letters");
                    return;
                }
               // Query checkUser = FirebaseDatabase.getInstance().getReference("UserDetails").orderByChild("mobileNumber").equalTo(mobileNumber);
                progressBar.setVisibility(View.VISIBLE);
                fireBase.signInWithEmailAndPassword(email,passwordCheck).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //FirebaseUser userDetails = fireBase.getCurrentUser();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this,"Login successfully done",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),UserProfileActivity.class));
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"Error"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

                /*checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            String userPassword=snapshot.child(mobileNumber).child("password").getValue(String.class);
                            if(userPassword.equals(passwordCheck))
                            {

                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
            }
        });
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        forgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail= new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter your email to reset password link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetMail.getText().toString();
                        fireBase.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(LoginActivity.this,"Reset password link send succesfully",Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this,"Error! Unable to send reset link",Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialog.create().show();

            }
        });
    }
}