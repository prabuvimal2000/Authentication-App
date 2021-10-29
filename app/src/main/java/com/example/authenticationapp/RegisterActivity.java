package com.example.authenticationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity {
    EditText fullName, emailAddress, mobileNumber, password;
    Button registerButton;
    FirebaseAuth fireBaseAuth;
    TextView alreadyUser;
    ProgressBar progressBar;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullName = findViewById(R.id.fullName);
        emailAddress = findViewById(R.id.email);
        mobileNumber = findViewById(R.id.mobilenumber);
        password = findViewById(R.id.password);
        alreadyUser = findViewById(R.id.user);
        progressBar = findViewById(R.id.progressBar);
        registerButton = findViewById(R.id.registerButton);
        fireBaseAuth = FirebaseAuth.getInstance();

        /*if (fireBaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
            finish();
        }*/

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailAddress.getText().toString().trim();
                String passwordCheck = password.getText().toString().trim();
                String userName = fullName.getText().toString().trim();
                String mobileNum = mobileNumber.getText().toString().trim();
                userRegistration(email,passwordCheck,userName,mobileNum);
            }

            private void userRegistration(String email, String passwordCheck, String userName, String mobileNum)
            {
                if(TextUtils.isEmpty(userName))
                {
                    fullName.setError("Name is required");
                    return;
                }
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
                if(TextUtils.isEmpty(mobileNum))
                {
                    mobileNumber.setError("Mobile Number is required");
                    return;
                }

                if(mobileNum.length()!=10)
                {
                    mobileNumber.setError("Please enter the valid mobileNumber");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fireBaseAuth.createUserWithEmailAndPassword(email,passwordCheck).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        FirebaseUser firebaseUser = fireBaseAuth.getCurrentUser();
                        UserDetails userData = new UserDetails(userName,email,mobileNum,passwordCheck);
                        rootNode= FirebaseDatabase.getInstance();
                        reference= rootNode.getReference("UserDetails");
                        reference.child(firebaseUser.getUid()).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(RegisterActivity.this,"User Created Successfully",Toast.LENGTH_LONG).show();
                                    //FirebaseUser firebaseUser= fireBase.getCurrentUser();
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                }
                                else
                                {
                                    Toast.makeText(RegisterActivity.this,"Error"+task.getException(),Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                });
            }
        });
        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }
}
                // userRegistration(email,passwordCheck,userName,mobileNum);
              /*  rootNode= FirebaseDatabase.getInstance();
                reference= rootNode.getReference("UserDetails");
                UserDetails userData = new UserDetails(userName,email,mobileNum,passwordCheck);
                FirebaseUser firebaseUser = null;
                reference.child(firebaseUser.getUid()).setValue(userData);

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
                //user details store in firebase
                fireBase.createUserWithEmailAndPassword(email,passwordCheck).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(RegisterActivity.this,"User Created",Toast.LENGTH_LONG).show();
                            //FirebaseUser firebaseUser= fireBase.getCurrentUser();

                            startActivity(new Intent(getApplicationContext(),UserProfileActivity.class));
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this,"Error"+task.getException(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });*/
