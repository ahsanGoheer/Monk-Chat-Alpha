package com.monk.monkchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class Congratulations extends AppCompatActivity {

    TextView congratsMsg=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations);
        getSupportActionBar().hide();
        Intent currentIntent=getIntent();

        String username=currentIntent.getStringExtra("Username");

        congratsMsg=(TextView)findViewById(R.id.congratsText);
        congratsMsg.setText("Congratulations"+' '+username+congratsMsg.getText().toString());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent i = new Intent(Congratulations.this,MainView.class);
               startActivity(i);
               finish();
            }
        }, 2000);
    }



}