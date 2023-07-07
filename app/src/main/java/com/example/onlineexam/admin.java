package com.example.onlineexam;

import android.os.Bundle;

import com.example.onlineexam.dbConnection.DBConnection;
import com.example.onlineexam.params.Params;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.onlineexam.databinding.ActivityAdminBinding;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class admin extends AppCompatActivity {

String oa,ob,oc,od,ro,q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
setContentView(R.layout.activity_admin);
        StrictMode.ThreadPolicy a=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        ((Button)findViewById(R.id.submit1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oa= ((TextView)findViewById(R.id.oa)).getText().toString();
                ob= ((TextView)findViewById(R.id.ob)).getText().toString();
                oc= ((TextView)findViewById(R.id.oc)).getText().toString();
                od= ((TextView)findViewById(R.id.od)).getText().toString();
                ro= ((TextView)findViewById(R.id.ro)).getText().toString();
                q= ((TextView)findViewById(R.id.question1)).getText().toString();
                Connection connection= DBConnection.getConnection();
                try {
                    Statement st=connection.createStatement();
                    String s ="('"+q+"','"+oa+"','"+ob +"','"+oc+"','"+od+"','"+ro+"')";
                    String sql="insert into "+ Params.MAQPDB +"(question,optiona,optionb,optionc,optiond,rightop) values "+s;
                    st.executeUpdate(sql);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
        });


    }


}