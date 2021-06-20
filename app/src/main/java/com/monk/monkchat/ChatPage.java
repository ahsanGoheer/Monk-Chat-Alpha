package com.monk.monkchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.monk.monkchat.Adapters.ChatAdapter;
import com.monk.monkchat.Models.MessageModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatPage extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth auth;

    CircleImageView profileImageOnChatPage;
    ImageView backFromChatIV, voiceCallIV, newMessageSendIV;
    TextView userNameOnChatTV;
    EditText newMessageToChatET;
    RecyclerView messagesOnChatRV;

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
        messagesOnChatRV = findViewById(R.id.messagesOnChatRV);

        profileImageOnChatPage = findViewById(R.id.profileImageOnChatPage);

        final String senderId = auth.getUid();

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

        final ArrayList<MessageModel> messageModels = new ArrayList<>();

        final ChatAdapter chatAdapter = new ChatAdapter(messageModels, this);
        messagesOnChatRV.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        messagesOnChatRV.setLayoutManager(layoutManager);

        final String senderRoom = senderId + recieverId;
        final String recieverRoom = recieverId + senderId;

        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();
                        for (DataSnapshot snapshot1: snapshot.getChildren()){
                            MessageModel model = snapshot1.getValue(MessageModel.class);
                            messageModels.add(model);
                        }
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        newMessageSendIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMessage = newMessageToChatET.getText().toString();
                final MessageModel messageModel = new MessageModel(senderId, newMessage);
                messageModel.setTimestamp((new Date()).getTime());
                newMessageToChatET.setText("");

                final String senderRoom = senderId + recieverId;
                final String receiverRoom = senderId + recieverId;

                database.getReference().child("chats")
                        .child(senderRoom)
                        .push()
                        .setValue(messageModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                database.getReference().child("chats")
                                        .child(receiverRoom)
                                        .push()
                                        .setValue(messageModel)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            }
                                        });
                            }
                        });
            }
        });
    }
}