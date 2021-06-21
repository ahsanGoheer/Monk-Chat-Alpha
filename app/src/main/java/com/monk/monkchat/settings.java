package com.monk.monkchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.monk.monkchat.Models.Users;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class settings extends AppCompatActivity {

    ImageView backArrowSettings,addImage;
    CircleImageView profilePicture;
    FirebaseStorage firebaseStorage;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    EditText userNameSettings, AboutMeSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        firebaseStorage=FirebaseStorage.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();



        getSupportActionBar().hide();
        backArrowSettings=(ImageView)findViewById(R.id.backArrowSettings);
        addImage=(ImageView)findViewById(R.id.addProfilePicture);
        profilePicture=(CircleImageView)findViewById(R.id.profileImageSettings);


        firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Users myUser= snapshot.getValue(Users.class);
                Picasso.get().load(myUser.getProfilePic()).placeholder(R.drawable.user).into(profilePicture);


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });








        backArrowSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(settings.this,MainView.class);
                startActivity(i);
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent contentIntent=new Intent();
                contentIntent.setAction(Intent.ACTION_GET_CONTENT);
                contentIntent.setType("image/*");
                startActivityForResult(contentIntent,2);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {

        if(data.getData()!=null)
        {
            Uri imageUri=data.getData();
            profilePicture.setImageURI(imageUri);
            final StorageReference imageStorageReference=firebaseStorage.getReference().child("profile_pictures").child(firebaseAuth.getUid());
            imageStorageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("Profile Picture","Picture Added Successfully!");
                    Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                    imageStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("profilePic").setValue(uri.toString());
                        }
                    });

                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}