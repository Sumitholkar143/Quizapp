package com.example.onlineexam;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class FlashActivity extends AppCompatActivity {
    ImageView imageView;
    TextView intro,sp_intro;
    private static final int SPLASH_SCREEN_TIME_OUT = 4000; // After completion of 2000 ms, the next activity will get started.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        imageView=findViewById(R.id.imageView3);
        intro=findViewById(R.id.inro);
        sp_intro=findViewById(R.id.splashitro);
        getSupportActionBar().hide();
        // This method is used so that your splash activity can cover the entire screen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                 // this will bind your MainActivity.class file with activity_main.
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splashanima);
                Animation animation1=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.introanim);
        imageView.startAnimation(animation);
        intro.startAnimation(animation1);
        sp_intro.startAnimation(animation1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent is used to switch from one activity to another.
                Intent i = new Intent(FlashActivity.this, LoginActivity.class);
                startActivity(i); // invoke the SecondActivity.
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish(); // the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}
