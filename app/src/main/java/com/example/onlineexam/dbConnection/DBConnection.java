package com.example.onlineexam.dbConnection;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.onlineexam.params.Params;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection extends Thread {
    Connection c=null;
     String s;
     ResultSet rs;
      Statement st;
     String query;
    @Override
    public  void run(){
        if (s=="con"){
        try{
            Class.forName(Params.DRIVER_CLASS);
              c= DriverManager.getConnection(Params.CONNECTION_STRING,Params.uname,Params.pass);
           }catch (Exception e){
            e.printStackTrace();
        }
    }
        if (s=="rs"){
            try {
                rs=st.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection1(Context context) {
        Connection c=null;
        try {
            Class.forName(Params.DRIVER_CLASS);
             c= DriverManager.getConnection(Params.CONNECTION_STRING,Params.uname,Params.pass);

        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return  c;


    }

    public static Connection getConnection() {
        DBConnection d= new DBConnection();
      d.s="con";
        d.start();
        try {
            d.join();
        }catch (Exception e){
            e.printStackTrace();
        }
        return d.c;
    }

   public static ResultSet getResultSet(Statement s, String query){
       DBConnection d=new DBConnection();
       d.st=s;
       d.s="rs";
       d.query=query;
       d.start();
       try {
           d.join();
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       return d.rs;
   }


}
