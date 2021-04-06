package com.monk.monkchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        Thread splash_thread= new Thread()
        {
            public void run()
            {
                try
                {

                sleep(3000);


                }
                catch(Exception exception)
                {
                    exception.printStackTrace();
                }
                finally {
                    Intent i = new Intent(MainActivity.this,Login.class);
                    startActivity(i);
                }



            }




        };
        splash_thread.start();*/


       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent i = new Intent(MainActivity.this,Login.class);
               startActivity(i);
               finish();
           }
       },5*1000);
    }
}