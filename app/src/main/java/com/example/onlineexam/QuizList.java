package com.example.onlineexam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.onlineexam.adapter.CustomAdapter;
import com.example.onlineexam.dbConnection.DBConnection;
import com.example.onlineexam.params.Params;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import android.os.Bundle;

public class QuizList extends AppCompatActivity {
    int count=0;
    ImageView back;
    String QPDTNAME,QPDBNAME;
    ArrayList<String> score=new ArrayList<>();
    ArrayList<String> accu=new ArrayList<>();
    ArrayList<String> test=new ArrayList<>();
    ArrayList<Integer> bool=new ArrayList<>();
    String id_value;

    int maxid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quiz_list);
        getSupportActionBar().hide();
        Intent intent=getIntent();
        back=findViewById(R.id.back3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizList.super.onBackPressed();
            }
        });
        switch(intent.getIntExtra("subseq",1)){
            /*
            For Change Tables
            */
            case 1:
                QPDBNAME= Params.MAQPDB;
                QPDTNAME=Params.MAQPDT;
                break;
            case 2:
                QPDBNAME=Params.GKQPDB;
                QPDTNAME=Params.GKQPDT;
                break;
            case 3:
                QPDBNAME=Params.SCQPDB;
                QPDTNAME=Params.SCQPDT;

        }
        SharedPreferences sharedPreferences=getSharedPreferences("MyPrefs",0);
        id_value=String.valueOf(sharedPreferences.getInt("uniqeID",0));
        ListView l = findViewById(R.id.listview);

        try {
            Connection c = DBConnection.getConnection();
            Statement st = c.createStatement();//First Statement for QuestionDB
            Statement stt = c.createStatement();//Second Statement For Student Details
            ResultSet r = DBConnection.getResultSet(st,"select MAX(id) from "+QPDBNAME);//For Select Total Number of Questions
            ResultSet sd =DBConnection.getResultSet(stt,"select * from " +QPDTNAME + " where id_value='"+id_value+"'");//Fetch data of student through unique ID
            r.next();
            sd.next();
            maxid = r.getInt(1) + 1;
            int count = 1;//count for


            for (int i = 1; i <= (maxid); i += 10) {//loop for create 10 questions Set
                test.add("Test " + (count++));
            }


            for (int i = 1; i <= count; i++) {

                if (sd.getString("isgiventTest" + i).equals("false")) {
                    bool.add(i-1);//add this test Clickable
                }

                if (sd.getString("test" + i + "Score") == null) {
                    score.add("");
                    accu.add("");
                    continue;
                }

                score.add("Score : " + sd.getString("test" + i + "Score"));
                accu.add("Accurasy : " + String.valueOf(Integer.parseInt(sd.getString("test" + i + "Score")) * 10) + "%");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(score);
        System.out.println(test);
        CustomAdapter arrayAdapter = new CustomAdapter(this,QuizList.this, test, score, accu, new HashSet<Integer>(bool),id_value,intent.getIntExtra("subseq",1));

        System.out.println(arrayAdapter);
        l.setAdapter(arrayAdapter);
    }

}
