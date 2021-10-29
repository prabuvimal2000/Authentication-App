package com.example.authenticationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class UserProfileActivity extends AppCompatActivity {

    TextView userName, emailaddress, phoneNumber;
    ImageView userImage, emailImage, contactImage;
    //FirebaseDatabase userData;
    //DatabaseReference userReference, rootRef;
    ProgressBar progressBar;
    String email,userFullName,mobileNumber;
    FirebaseAuth userProfile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userName= findViewById(R.id.userName);
        emailaddress=findViewById(R.id.emailAddress);
        phoneNumber=findViewById(R.id.phoneNumber);
        userImage=findViewById(R.id.userProfile);
        emailImage=findViewById(R.id.emailImage);
        contactImage=findViewById(R.id.contactImage);
        progressBar=findViewById(R.id.progressBar);
        userProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = userProfile.getCurrentUser();

        if(firebaseUser==null)
        {
            Toast.makeText(UserProfileActivity.this,"Something is wrong",Toast.LENGTH_LONG);
        }
        else
        {
            showUserProfile(firebaseUser);
        }

    }

    private void showUserProfile(FirebaseUser firebaseUser)
    {
        String userId= firebaseUser.getUid();
        Log.d("userId",userId);
        DatabaseReference profileReference = FirebaseDatabase.getInstance().getReference("UserDetails");
        progressBar.setVisibility(View.VISIBLE);
        profileReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDetails readUserData = snapshot.getValue(UserDetails.class);
                if (readUserData != null) {
                    progressBar.setVisibility(View.GONE);
                    email = firebaseUser.getEmail();
                    //mobileNumber = readUserData.mobileNumber;
                    userFullName = readUserData.fullName;
                    mobileNumber= readUserData.mobileNumber;

                    userName.setText(userFullName);
                    emailaddress.setText(email);
                    phoneNumber.setText(mobileNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void logout(View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}
    //static final String user="UserDetails";

    //String emailAdress;
    //String email;
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        email = intent.getStringExtra("emailAddress");

        userName= findViewById(R.id.userName);
        emailaddress=findViewById(R.id.emailAddress);
        phoneNumber=findViewById(R.id.phoneNumber);
        userImage=findViewById(R.id.userProfile);
        emailImage=findViewById(R.id.emailImage);
        contactImage=findViewById(R.id.contactImage);
        userData= FirebaseDatabase.getInstance();
        rootRef=FirebaseDatabase.getInstance().getReference();
        userReference= rootRef.child(user);

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()) {
                        if(data.child("emailAddress").getValue().equals(email))
                        {
                        String fullName, email, mobileNumber;
                        fullName = snapshot.child("fullName").getValue().toString();
                        email = snapshot.child("emailAddress").getValue().toString();
                        mobileNumber = snapshot.child("mobileNumber").getValue().toString();
                            fullName = data.child("fullName").getValue(String.class);
                            email = data.child("emailAddress").getValue(String.class);
                            mobileNumber = data.child("mobileNumber").getValue(String.class);
                        emailaddress.setText(email);
                        userName.setText(fullName);
                        phoneNumber.setText(mobileNumber);
                        Toast.makeText(UserProfileActivity.this, "This is userData", Toast.LENGTH_LONG).show();
                    }
                }
            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(UserProfileActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
//            }
        });
        //showAllUserData ();
     /*   userData=FirebaseAuth.getInstance().getCurrentUser();
        userReference= FirebaseDatabase.getInstance().getReference("UserDetails");
        userId= userData.getUid();

        userReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDetails userProfile = snapshot.getValue(UserDetails.class);
                String fullName,email,mobileNum;
                fullName=userProfile.fullName;
                email=userProfile.emailAddress;
                mobileNum=userProfile.mobileNumber;

                userName.setText(fullName);
                emailaddress.setText(email);
                phoneNumber.setText(mobileNum);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    /*
        userData=FirebaseDatabase.getInstance();
        userReference= userData.getReference("UserDetails");

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren())
                {
                    if(data.child("emailAddress").equals(email))
                    {
                        userName.setText(data.child("fullName").getValue(String.class));
                        emailaddress.setText((CharSequence) data.child(email));
                        phoneNumber.setText(data.child("mobileNumber").getValue(String.class));

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }*/

    /*private void showAllUserData() {
    }*/

   /* public void logout(View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}*/