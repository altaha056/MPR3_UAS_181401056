package com.example.uasaltaha;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    LinearLayout mMenu, mExplore;
    ImageView mBurger;
    CircleImageView mPhotoprofile;
    TextView username,email,phone, resendCode, resendButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    StorageReference storageReference;
    Button editProfile;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        mMenu = findViewById(R.id.menu1);
        mExplore = findViewById(R.id.explore1);
        mBurger = findViewById(R.id.burger1);
        username = findViewById(R.id.username1);
        email = findViewById(R.id.email1);
        phone = findViewById(R.id.phone1);
        resendCode = findViewById(R.id.verify1);
        resendButton = findViewById(R.id.verifybutton);
        mPhotoprofile = findViewById(R.id.profile_image);
        editProfile = findViewById(R.id.editprofile2);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();


        userId = fAuth.getCurrentUser().getUid();
        final FirebaseUser user = fAuth.getCurrentUser();

        if (!user.isEmailVerified()){
            resendCode.setVisibility(View.VISIBLE);
            resendButton.setVisibility(View.VISIBLE);
            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(),"Verification Email has been sent", Toast.LENGTH_SHORT);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag", "onFailure: Email not sent " + e.getMessage());
                        }
                    });
                }
            });
        }

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                phone.setText(documentSnapshot.getString("phone"));
                username.setText(documentSnapshot.getString("username"));
                email.setText(documentSnapshot.getString("email"));
            }
        });


        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Menu.class));
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
            }
        });

        mExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),Explore.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
            }
        });
        mBurger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                Intent i = new Intent(v.getContext(),MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),EditProfile.class);
                i.putExtra("username",username.getText().toString());
                i.putExtra("email", email.getText().toString());
                i.putExtra("phone", phone.getText().toString());
                startActivity(i);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mPhotoprofile);
            }
        });

    }


//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==1000){
//            if(resultCode== Activity.RESULT_OK){
//                Uri imageUri = data.getData();
//                uploadimagetoFirebase(imageUri);
//            }
//        }
//    }
//
//    private void uploadimagetoFirebase(Uri imageUri){
//        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
//        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Picasso.get().load(uri).into(mPhotoprofile);
//                    }
//                });
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(Profile.this,"failed",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}