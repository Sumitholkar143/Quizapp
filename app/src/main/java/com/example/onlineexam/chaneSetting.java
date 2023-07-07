package com.example.onlineexam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.example.onlineexam.dbConnection.DBConnection;
import com.example.onlineexam.params.Params;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.Connection;
import java.sql.Statement;
public class chaneSetting extends AppCompatActivity {
EditText inpass,invalue;
    TextInputLayout textInputLayout;
TextView setvalue,war1;
ImageView back;
Button b;
SharedPreferences sp;
SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp=getSharedPreferences("MyPrefs",0);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                window.setStatusBarColor(Color.rgb(33,150,240));//173,216,230));
            }
        }
        sharedPreferences=getSharedPreferences("MyPrefs",MODE_PRIVATE);
        setContentView(R.layout.activity_chane_setting);
        war1=findViewById(R.id.war1);
        back=findViewById(R.id.back2);
        back.setClickable(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chaneSetting.super.onBackPressed();
            }
        });

       textInputLayout= ((TextInputLayout)findViewById(R.id.inputValues));
        inpass=((TextInputLayout)findViewById(R.id.changeInputPassword)).getEditText();
        invalue=((TextInputLayout)findViewById(R.id.inputValues)).getEditText();
        Intent intent=getIntent();
        text=intent.getStringExtra("valuechange");
       textInputLayout.setHint(text);
        if (text.equals("PassWord")){
            textInputLayout.setHint("New "+text);
            textInputLayout.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
            ((TextInputLayout)findViewById(R.id.changeInputPassword)).setHint("Old "+text);
            textInputLayout.setEndIconActivated(true);
            invalue.setInputType(EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD);

        }
        if (text.equals("Log Out")) {
          logOUT();
        }


        b=findViewById(R.id.update);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                war1.setText("");

                System.out.println(text+"222222222222222222222222222222222222");
                changeApply(text);
                inpass.setText("");
                invalue.setText("");
                System.out.println("after method");
            }
        });

    }
     void changeApply(String s) {
         System.out.println("1"+inpass.getText().toString()+"2");
         if (inpass.getText().toString().equals("") || invalue.getText().toString().equals("")) {
             war1.setText("Please Enter all Fiels");
             war1.setTextColor(Color.rgb(200, 0, 0));
             return;
         }
         if (s.equals("PassWord")){
             s = "pass";
         ;
         }
         else if (s.equals("Gmail id"))
             s = "gmail";
         else
             s = "name";

         try {
             Connection c = DBConnection.getConnection();
             if (c==null){
                 Toast.makeText(chaneSetting.this,"Error to connect host",Toast.LENGTH_LONG).show();
             }
             Statement st1 = c.createStatement();
             Statement st2 = c.createStatement();
             Statement st3 = c.createStatement();
             System.out.println("statment");
             if (!(DBConnection.getResultSet(st1,"select * from " + Params.Stu_DB_NAME + " where pass='" + inpass.getText().toString() + "' and id_value="+sp.getInt("uniqeID",0))).next()) {
                 war1.setText("Please Enter Correct Password");
                 war1.setTextColor(Color.rgb(200, 0, 0));
                 return;
             }

             if (s.equals("pass")) {
                 String sql1 = "select pass from " + Params.Stu_DB_NAME + " where pass='" + invalue.getText().toString() + "'";
                 if ((DBConnection.getResultSet(st2,sql1).next())){
                     war1.setText("Password Already Exist");
                     war1.setTextColor(Color.rgb(250, 0, 0));
                     invalue.setText("");
                     return;
                 }
             }

                 System.out.println("before query");
                 String sql = "UPDATE " + Params.Stu_DB_NAME + " SET " + s + " = '" + invalue.getText().toString() + "' WHERE pass='" + inpass.getText().toString() + "' and id_value="+sharedPreferences.getInt("uniqeID",0);
                 Thread thread=new Thread(new Runnable() {
                     @Override
                     public void run() {
                            try {
                            st3.executeUpdate(sql);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                     }
                 });
                thread.start();
                try {
                    thread.join();
                }catch (Exception e){
                    e.printStackTrace();
                }
                 Toast.makeText(this,s+" Changed Succesfully ",Toast.LENGTH_LONG);
                 logOUT();
             }catch(Exception e){
                 e.printStackTrace();
             System.out.println(e);
             }
         }
         public void logOUT(){
             editor = sp.edit();
             editor.putBoolean("islogin", false);
             editor.putString("name", "");
             editor.putInt("uniqeID", 0);
             editor.apply();
             Intent intent=new Intent(chaneSetting.this,LoginActivity.class);
             startActivity(intent);
             overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
             finish();
         }

    }








