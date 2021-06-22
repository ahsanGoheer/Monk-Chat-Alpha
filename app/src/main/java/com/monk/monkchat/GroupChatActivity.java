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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

    private String currentUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        currentUserName = "NA";

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

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Users").child(FirebaseAuth.getInstance().getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUserName = dataSnapshot.child("userName").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR", databaseError.getMessage()); //Don't ignore errors!
            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
        Log.d("GOT THIS DATA - ", currentUserName);

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

                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("userName").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Toast.makeText(getApplicationContext(), snapshot.getValue().toString(), Toast.LENGTH_LONG).show();
                        currentUserName = snapshot.getValue().toString();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


//                String userId = FirebaseDatabase.getInstance().getReference("Users").child().toString();

                //String currentUser = FirebaseDatabase.getInstance().getReference("Users/"+FirebaseAuth.getInstance().getUid()+"/userName").child(FirebaseAuth.getInstance().getUid()).child("userName").getKey();
//                String currentUser = FirebaseDatabase.getInstance().getReference("Users/"+FirebaseAuth.getInstance().getUid()+"/userName").toString();
                //DataSnapshot snapshot= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).getRef().get();


                //Toast.makeText(getApplicationContext(), currentUserName, Toast.LENGTH_LONG).show();

                final String message =enteredGroupMessage.getText().toString();
                final MessageModel groupMessageModel=new MessageModel(senderId,currentUserName + " - " + message);
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