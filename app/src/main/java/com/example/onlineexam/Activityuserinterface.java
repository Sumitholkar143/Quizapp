//userinterface
package com.example.onlineexam;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import com.example.onlineexam.dbConnection.DBConnection;
import com.example.onlineexam.params.Params;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.example.onlineexam.databinding.ActivityUserinterfaceBinding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Activityuserinterface extends AppCompatActivity implements View.OnClickListener {

    String id;
    static  int questionnumber;
    int totalscore=0;

     TextView question;
     Button opa,opb,opc,opd;
    static Connection ca;
    static ResultSet rs;
     static int count=0;
    static String ans;
    String QPDBNAME,QPDBDT;
    ImageView backbtn;
       TextView tt;

    int reciveQuestionid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getSupportActionBar().hide();
        }
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                window.setStatusBarColor(Color.rgb(33,150,240));//173,216,230));
            }
        }
        tt=findViewById(R.id.war3);
        backbtn=findViewById(R.id.back_pressed);
        question=findViewById(R.id.question);
        opa=findViewById(R.id.optiona);
        opb=findViewById(R.id.optionb);
        opc=findViewById(R.id.optionc);
        opd=findViewById(R.id.optiond);
        opb.setOnClickListener(this);
        opa.setOnClickListener(this);
        opc.setOnClickListener(this);
        opd.setOnClickListener(this);
        Intent recive=getIntent();
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activityuserinterface.super.onBackPressed();
            }
        });
        switch (recive.getIntExtra("subseq",1)){
            case 1:
                QPDBNAME=Params.MAQPDB;
                QPDBDT=Params.MAQPDT;
                break;
            case 2:
                QPDBNAME=Params.GKQPDB;
                QPDBDT=Params.GKQPDT;
                break;
            case 3:
                QPDBNAME=Params.SCQPDB;
                QPDBDT=Params.SCQPDT;
        }
        reciveQuestionid=recive.getIntExtra("vaalueofid",10);
        System.out.println(reciveQuestionid+"question id");
        count=0;
        questionnumber=0;
        System.out.println(recive.getStringExtra("id_values"));
        id=(recive.getStringExtra(("id_values")));

        StrictMode.ThreadPolicy a=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        try {
            ca= DBConnection.getConnection();
            String sql;
            if (reciveQuestionid==1)
            sql="select * from "+ QPDBNAME +" where id<"+(reciveQuestionid*10+1);
            else
                sql="select * from "+QPDBNAME+" where id<"+(reciveQuestionid*10+1)+" and id>"+((reciveQuestionid-1)*10);
            PreparedStatement ps = ca.prepareStatement(sql);
            rs=ps.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }
        setContent();
    }
    void setContent(){
        try {
            if (rs.next()) {
                questionnumber++;
                ans = rs.getString("rightop");
                question.setText(questionnumber+". "+rs.getString("question"));
                    opa.setText(rs.getString( 2));
                    opb.setText(rs.getString(3));
                    opc.setText(rs.getString(4));
                    opd.setText(rs.getString(5));
            }
            else{
                tt.setText("Your Score is "+totalscore+" \n your accuracy is "+((int)(count*100)/questionnumber)+"%");
                ca.close();
                opa.setClickable(false);
                opb.setClickable(false);
                opc.setClickable(false);
                opd.setClickable(false);
                Connection c=DBConnection.getConnection();
                Statement s=c.createStatement();
                s.executeUpdate("update "+QPDBDT+" set isgiventTest"+reciveQuestionid+"='true',test" +reciveQuestionid+"Score="+totalscore+" where id_value="+id+"");
                ca.close();

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
 public void deley(int time){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContent();
                opa.setTextColor(Color.BLACK);
                opb.setTextColor(Color.BLACK);
                opc.setTextColor(Color.BLACK);
                opd.setTextColor(Color.BLACK);
            }
        },time);
 }
@Override
public  void  onBackPressed(){
        startActivity(new Intent(this, MainActivity3.class));
}
    @Override
    public void onClick(View v) {

        Button temp=((Button)findViewById(v.getId()));
        if (temp.getText().equals(ans)){
            count++;
            totalscore+=10;
            temp.setTextColor(Color.GREEN);
            deley(1000);

        }
        else {
            temp.setTextColor(Color.RED);
            totalscore-=5;
           deley(1000);
        }

    }
}