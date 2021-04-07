package com.monk.monkchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Confirmation extends AppCompatActivity {

    TextView userName,email,pass,confirmPrompt;
    Button editBtn=null,confirmBtn=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        userName=(TextView) findViewById(R.id.userNameText);
        email=(TextView)findViewById(R.id.emailText);
        pass=(TextView) findViewById(R.id.passwordText);
        confirmPrompt=(TextView)findViewById(R.id.confirmationPrompt);

        Intent currentIntent=getIntent();
        String fetchedUserName=currentIntent.getStringExtra("Username");
        String fetchedEmail=currentIntent.getStringExtra("Email");
        String fetchedPassword=currentIntent.getStringExtra("Password");

        userName.setText(userName.getText().toString()+':'+' '+' '+fetchedUserName);
        email.setText(email.getText().toString()+':'+' '+' '+fetchedEmail);
        pass.setText(pass.getText().toString()+':'+' '+' '+fetchedPassword);
        confirmPrompt.setText(fetchedUserName+confirmPrompt.getText().toString());

        editBtn=(Button) findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeActivity = new Intent(Confirmation.this,SignUp.class);
                changeActivity.putExtra("Username",fetchedUserName);
                changeActivity.putExtra("Email",fetchedEmail);
                changeActivity.putExtra("Password",fetchedPassword);
                setResult(2,changeActivity);
                finish();

            }
        });

        confirmBtn=(Button) findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeActivity2=new Intent(Confirmation.this,Congratulations.class);
                changeActivity2.putExtra("Username",fetchedUserName);
                startActivity(changeActivity2);
                finish();


            }
        });

    }


}