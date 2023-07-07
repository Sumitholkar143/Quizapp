package com.example.onlineexam;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SettingList extends AppCompatActivity {

    ListView l;
    ImageView backbtn;
    @Override
    protected void onCreate(Bundle saveinstancestate){
        super.onCreate(saveinstancestate);
        setContentView(R.layout.layout_setting_list);
        l=findViewById(R.id.list);
        backbtn=findViewById(R.id.back);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                window.setStatusBarColor(Color.rgb(33,150,240));//173,216,230));
            }
        }
        getSupportActionBar().setTitle("Setting");
        backbtn.setClickable(true);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingList.super.onBackPressed();
            }
        });
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Name");
        arrayList.add("Gmail Id");
        arrayList.add("PassWord");
        arrayList.add("Log Out");
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arrayList);
        l.setAdapter(arrayAdapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(SettingList.this,chaneSetting.class);
                intent.putExtra("valuechange",((TextView)view).getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

    }
    }

