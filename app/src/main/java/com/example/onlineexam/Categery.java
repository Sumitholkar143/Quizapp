package com.example.onlineexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class Categery extends AppCompatActivity implements View.OnClickListener{
    Button gk,ma,gs,gh;
    ImageView back;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categery);
        sharedPreferences=getSharedPreferences("MyPrefs",MODE_PRIVATE);
         getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                window.setStatusBarColor(Color.rgb(33,150,240));//173,216,230));
            }
        }
        gk=findViewById(R.id.gkcategery);
        ma=findViewById(R.id.mathscategery);
        gs=findViewById(R.id.sciencecategery);
        gh=findViewById(R.id.historycategery);
        back=findViewById(R.id.back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                Categery.super.onBackPressed();
            }
        });
        gk.setOnClickListener(this);
        ma.setOnClickListener(this);
        gh.setOnClickListener(this);
        gs.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int a=v.getId();
        Intent intent=new Intent(Categery.this,QuizList.class);

        if (a==R.id.gkcategery){
           intent.putExtra("subseq",2);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

        }else if (a==R.id.mathscategery){
            intent.putExtra("subseq",1);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);


        }else if(a==R.id.sciencecategery){
            intent.putExtra("subseq",3);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

        }else if(a==R.id.historycategery){

        }

    }
    @Override
    public void onBackPressed(){
        overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
        Categery.super.onBackPressed();

    }



}