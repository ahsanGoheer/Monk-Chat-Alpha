package com.monk.monkchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Button signInBtn= null;
    EditText userAddressBox=null;
    EditText userPasswordBox=null;
    String userMail="admin@gmail.com";
    String password="admin";
    TextView signUpText=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        signInBtn=(Button) findViewById(R.id.signupBtn);
        userAddressBox=(EditText) findViewById(R.id.userID);
        userPasswordBox=(EditText)findViewById(R.id.userPass);
        signUpText=(TextView)findViewById(R.id.signUpTV);
        signInBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                if((userAddressBox.getText().toString().equals(userMail)) && (userPasswordBox.getText().toString().equals(password)))
                {
                    Toast.makeText(getApplicationContext(),"Signed in!",Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        Toast.makeText(getApplicationContext(),"Incorrect Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i=new Intent(Login.this,SignUp.class);
                startActivity(i);
                finish();
                return false;
            }
        });

    }


}