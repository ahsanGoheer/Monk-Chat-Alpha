package com.monk.monkchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SignUp extends AppCompatActivity {

    TextView loginText=null;
    Button signUpBtn=null;
    EditText userName,password,confirmPassword,email;
    CheckBox termsBox=null;
    private FirebaseAuth mAuth;
    private FirebaseDatabase dbConn;
    ProgressDialog signUpProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        dbConn=FirebaseDatabase.getInstance();
        loginText=(TextView) findViewById(R.id.loginTextTV);
        signUpBtn=(Button) findViewById(R.id.signupBtn);
        userName=(EditText)findViewById(R.id.usernameET);
        email=(EditText)findViewById(R.id.selectedEmailET);
        password=(EditText)findViewById(R.id.passwordET);
        confirmPassword=(EditText) findViewById(R.id.confirmPasswordET);
        termsBox=(CheckBox)findViewById(R.id.termsConditionsCB);

        signUpProgress=new ProgressDialog(SignUp.this);
        signUpProgress.setTitle("Creating Your Account.");
        signUpProgress.setMessage("Your account is being created!");


        ArrayList<EditText> textFields = new ArrayList<EditText>();
        textFields.add(userName);
        textFields.add(email);
        textFields.add(password);
        textFields.add(confirmPassword);
        backToNormalEvent(textFields);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpProgress.show();
                if(termsBox.isChecked()&&!userName.getText().toString().equals("") && !email.getText().toString().equals("") && !password.getText().toString().equals("") && !confirmPassword.getText().toString().equals(""))
                {

                    if(password.getText().toString().equals(confirmPassword.getText().toString()))
                    {
                        Intent i = new Intent(SignUp.this,Confirmation.class);
                        i.putExtra("Username",userName.getText().toString());
                        i.putExtra("Email",email.getText().toString());
                        i.putExtra("Password",password.getText().toString());


                        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).
                                addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NotNull Task<AuthResult> task) {
                                        signUpProgress.dismiss();
                                        if (task.isSuccessful()) {
                                            Log.d("Sign Up", "Sign Up Successful!");
                                            Toast.makeText(getApplicationContext(),"Sign Up Successful!",Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.d("Sign Up", task.getException().getMessage());
                                            Toast.makeText(getApplicationContext(),"Sign Up Failed!",Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });


/*
                        startActivityForResult(i,2);
*/
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Passwords do not match!",Toast.LENGTH_SHORT).show();
                    }


                }
                else
                {


                    if(!termsBox.isChecked())
                    {
                        termsBox.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    }

                    checkError(textFields);

                    Toast.makeText(getApplicationContext(),"Missing Fields!",Toast.LENGTH_SHORT).show();
                }

            }
        });


        loginText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i =new Intent(SignUp.this,Login.class);
                startActivity(i);
                finish();
                return true;
            }
        });

        termsBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                termsBox.setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
        });



    }//END ON CREATE



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2) {
            String fetchedUserName = data.getStringExtra("Username");
            String fetchedEmail=data.getStringExtra("Email");
            String fetchedPassword=data.getStringExtra("Password");
            userName.setText(fetchedUserName);
            email.setText(fetchedEmail);
            password.setText(fetchedPassword);
            confirmPassword.setText(fetchedPassword);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void checkError(ArrayList<EditText> textFields)
    {
        for(EditText field:textFields)
        {
            if(field.getText().toString().equals(""))
            {
                field.setHintTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        }
    }


    public void backToNormalEvent(ArrayList<EditText> textFields)
    {
        for(EditText field:textFields)
        {
            field.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    field.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
                    return false;
                }
            });

        }
    }
}//END CLASS