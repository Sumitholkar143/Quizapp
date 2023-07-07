package com.example.onlineexam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity3 extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
   DrawerLayout drawerLayout;
   SharedPreferences sp;
   SharedPreferences.Editor editor;
   Toolbar toolbar;
   Button startbtn;

NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent intent=getIntent();
        getSupportActionBar().setTitle("Dashboard");
        navigationView=findViewById(R.id.navigationview);
        sp=getSharedPreferences("MyPrefs",MODE_PRIVATE);
        ((TextView) (navigationView.getHeaderView(0).findViewById(R.id.stuuser))).setText(sp.getString("username",""));
        ((TextView) (navigationView.getHeaderView(0).findViewById(R.id.stuname))).setText(sp.getString("name",""));
        drawerLayout=findViewById(R.id.drawerlayout);
        toolbar=findViewById(R.id.toolbar);
        startbtn=(Button) ((LinearLayout)findViewById(R.id.mainactivity3childlayout)).getChildAt(2);
        ((TextView)((LinearLayout)findViewById(R.id.mainactivity3childlayout)).getChildAt(0)).setText("Hi "+sp.getString("name",""));
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity3.this,Categery.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });
        toolbar.setTitle("");
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int a=item.getItemId();
                if (a==R.id.navigation_quiz){
                    startActivity(new Intent(MainActivity3.this,Categery.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    return true;
                }else  if (a==R.id.navigation_setting){
                    startActivity(new Intent(MainActivity3.this,SettingList.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    return true;

                }else if (a==R.id.logout){
                    editor = sp.edit();
                    editor.putBoolean("islogin", false);
                    editor.putString("name", "");
                    editor.putInt("uniqeID", 0);
                    editor.apply();
                    Intent intent=new Intent(MainActivity3.this,LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                    return true;
                }
                return false;
            }
        });



    }
}

