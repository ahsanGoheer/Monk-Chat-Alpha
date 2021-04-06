package com.monk.monkchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Button signInBtn= null;
    EditText userAddressBox=null;
    EditText userPasswordBox=null;
    String userMail="admin@gmail.com";
    String password="admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        signInBtn=(Button) findViewById(R.id.loginBtn);
        userAddressBox=(EditText) findViewById(R.id.userID);
        userPasswordBox=(EditText)findViewById(R.id.userPass);
        signInBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                if(userAddressBox.getText().toString()==userMail && userPasswordBox.getText().toString()==password)
                {
                    Toast.makeText(getApplicationContext(),"Signed in!",Toast.LENGTH_LONG).show();
                }
                else
                    {
                        Toast.makeText(getApplicationContext(),"Incorrect Credentials!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}