package com.monk.monkchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.monk.monkchat.Adapters.FragmentsAdapter;


public class MainView extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    private FirebaseAuth logAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        logAuth=FirebaseAuth.getInstance();
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.application_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.settings:
                break;
            case R.id.logout:
                logAuth.signOut();
                Toast.makeText(getApplicationContext(),"Signed Out!",Toast.LENGTH_SHORT).show();
                Intent logOutIntent=new Intent(MainView.this,Login.class);
                startActivity(logOutIntent);
                break;
            default:
                Log.d("Item Select","There was an error!");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}