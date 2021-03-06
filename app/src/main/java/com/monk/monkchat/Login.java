package com.monk.monkchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class Login extends AppCompatActivity {

    Button signInBtn= null;
    EditText userAddressBox=null;
    EditText userPasswordBox=null;
    TextView signUpText=null;
    FirebaseAuth LoginAuth;
    ProgressDialog signInProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        LoginAuth=FirebaseAuth.getInstance();
        signInProgress=new ProgressDialog(Login.this);
        signInProgress.setTitle("Signing In");
        signInProgress.setMessage("Signing in to your account!");
        signInBtn=(Button) findViewById(R.id.signupBtn);
        userAddressBox=(EditText) findViewById(R.id.userID);
        userPasswordBox=(EditText)findViewById(R.id.userPass);
        signUpText=(TextView)findViewById(R.id.signUpTV);
        signInBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                signInProgress.show();
                if((!userAddressBox.getText().toString().equals("")) && (!userPasswordBox.getText().toString().equals("")))
                {
                    LoginAuth.signInWithEmailAndPassword(userAddressBox.getText().toString(),userPasswordBox.getText().toString()).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            signInProgress.dismiss();
                            if(task.isSuccessful())
                            {
                                Log.d("Login","Login was successful!");
                                Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Login.this,MainView.class);
                                startActivity(i);
                                finish();
                            }
                            else
                            {
                                Log.d("Login",task.getException().getMessage());
                                Toast.makeText(getApplicationContext(),"Login wasn't successful!",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
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

        if(LoginAuth.getCurrentUser()!=null)
        {
            Intent redirectIntent= new Intent(Login.this,MainView.class);
            startActivity(redirectIntent);
            finish();
        }

    }


}