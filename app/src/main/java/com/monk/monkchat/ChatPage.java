package com.monk.monkchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatPage extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth auth;

    CircleImageView profileImageOnChatPage;
    ImageView backFromChatIV, voiceCallIV, newMessageSendIV;
    TextView userNameOnChatTV;
    EditText newMessageToChatET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        backFromChatIV = findViewById(R.id.backFromChatIV);
        voiceCallIV = findViewById(R.id.voiceCallIV);
        newMessageSendIV = findViewById(R.id.newMessageSendIV);
        userNameOnChatTV = findViewById(R.id.userNameOnChatTV);
        newMessageToChatET = findViewById(R.id.newMessageToChatET);

        profileImageOnChatPage = findViewById(R.id.profileImageOnChatPage);

        String senderId = auth.getUid();

        Intent intent =  getIntent();
        String recieverId = intent.getStringExtra("userId");
        String recieverProfilePic = intent.getStringExtra("profilePic");
        String recieverUserName = intent.getStringExtra("userName");

        userNameOnChatTV.setText(recieverUserName);

        Picasso.get().load(recieverProfilePic).placeholder(R.drawable.avatar).into(profileImageOnChatPage);

        backFromChatIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ChatPage.this, MainView.class);
                startActivity(intent1);
            }
        });
    }
}