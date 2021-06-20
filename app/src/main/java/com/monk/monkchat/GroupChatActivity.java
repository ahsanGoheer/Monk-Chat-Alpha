package com.monk.monkchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.monk.monkchat.Adapters.ChatAdapter;
import com.monk.monkchat.Models.MessageModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {
    ImageView backBtnGroup,groupChatSendBtn;
    RecyclerView groupMessageRV;
    TextView userNameChatGroup;
    EditText enteredGroupMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        getSupportActionBar().hide();
        groupChatSendBtn=(ImageView)findViewById(R.id.newMessageSendGroupIV);
        userNameChatGroup=(TextView)findViewById(R.id.userNameOnChatGroupTV);
        groupMessageRV=(RecyclerView)findViewById(R.id.messagesOnChatGroupRV);
        backBtnGroup=(ImageView)findViewById(R.id.backFromChatGroupIV);
        enteredGroupMessage=(EditText)findViewById(R.id.newMessageToChatGroupET);
        backBtnGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GroupChatActivity.this,MainView.class);
                startActivity(i);
            }
        });

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final ArrayList<MessageModel> groupMessageModels=new ArrayList<MessageModel>();
        final String senderId= FirebaseAuth.getInstance().getUid();
        userNameChatGroup.setText("My Friends");
        final ChatAdapter groupChatAdapter=new ChatAdapter(groupMessageModels,this);
        groupMessageRV.setAdapter(groupChatAdapter);

        LinearLayoutManager linerLayoutGroupChat= new LinearLayoutManager(this);
        groupMessageRV.setLayoutManager(linerLayoutGroupChat);
        database.getReference()
                .child("Group Chat")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        groupMessageModels.clear();
                        for(DataSnapshot dataSnapshot:snapshot.getChildren())
                        {
                            MessageModel myMessageModel=dataSnapshot.getValue(MessageModel.class);
                            groupMessageModels.add(myMessageModel);
                        }
                        groupChatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
        groupChatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message =enteredGroupMessage.getText().toString();
                final MessageModel groupMessageModel=new MessageModel(senderId,message);
                groupMessageModel.setTimestamp(new Date().getTime());
                enteredGroupMessage.setText("");

                database.getReference().child("Group Chat")
                        .push()
                        .setValue(groupMessageModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("Group Chat","Group Message Sent!");
                            }
                        });

            }
        });
    }
}