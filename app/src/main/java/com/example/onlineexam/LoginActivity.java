//loginactivity
package com.example.onlineexam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.DrawableWrapper;
import android.os.Build;
import android.os.Bundle;

import com.example.onlineexam.dbConnection.DBConnection;
import com.example.onlineexam.params.Params;
import androidx.appcompat.app.AppCompatActivity;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import  com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements TextWatcher {
    TextView t;
    EditText iuser,ipass;
    TextView msg;
    TextInputLayout username_inputLayout,password_inputLayout;
    Button lbtn,sinup_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("islogin",false)){
            startActivity(new Intent(LoginActivity.this,MainActivity3.class));
            finish();
        }
        username_inputLayout=findViewById(R.id.username);
        System.out.println(username_inputLayout.getBoxStrokeColor());
        username_inputLayout.setHelperTextEnabled(true);
        password_inputLayout=findViewById(R.id.password);
        sinup_btn=findViewById(R.id.sinup_btn);
        lbtn=findViewById(R.id.submit);
        msg=findViewById(R.id.msg);
        iuser=username_inputLayout.getEditText();
        ipass=password_inputLayout.getEditText();
        iuser.addTextChangedListener(this);
        msg.setText("");
        ResultSet  rs=null;
        StrictMode.ThreadPolicy a=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        Intent ad=new Intent(LoginActivity.this,admin.class);
        Intent bv=new Intent(LoginActivity.this,MainActivity3.class);

        sinup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,MainActivity2.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        lbtn.setOnClickListener(new View.OnClickListener() {
            String isadmin;
            @Override
            public void onClick(View view) {
                msg.setText("");
                String sql = "SELECT * FROM " + Params.Stu_DB_NAME + " WHERE username=? and pass=?";
                try {
                    Connection c = DBConnection.getConnection1(LoginActivity.this);
                    if (c == null) {
                        Toast.makeText(LoginActivity.this, "Something went a wrong ", Toast.LENGTH_LONG).show();
                        return;
                    }
                    PreparedStatement ps = c.prepareStatement(sql);
                    ps.setString(1, iuser.getText().toString());
                    ps.setString(2, ipass.getText().toString());
                   ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        String isadmin = rs.getString("isADMIN");
                        if (isadmin.equals("true")) {
                            startActivity(ad);
                            finish();
                            return;
                        }
                        Toast.makeText(LoginActivity.this, "Log In Successful", Toast.LENGTH_LONG);
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", rs.getString(1));
                        editor.putInt("uniqeID", rs.getInt(4));
                        editor.putString("username",rs.getString(6));
                        editor.putString("number",rs.getString(7));
                        editor.putBoolean("islogin", true);
                        editor.apply();
                        c.close();
                        startActivity(bv);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();

                    } else {
                        msg.setText("Uername Or PassWord incorrect...");
                        msg.setTextColor(Color.RED);
                    }
                    ipass.setText("");
                    iuser.setText("");

                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

    });
    }
     @Override
     public void onBackPressed(){
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
             finishAffinity();
         }
     }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // This method is called before the text is changed
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // This method is called when the text is being changed
    }

    @Override
    public void afterTextChanged(Editable s) {
        // This method is called after the text has been changed
        replaceNewLineCharacters(s);
    }

    private void replaceNewLineCharacters(Editable s) {
        String text = s.toString();
        String replacedText = text.replace("\n", ""); // Replace newline characters with blank characters
        if (!text.equals(replacedText)) {
            s.replace(0, s.length(), replacedText); // Update the text in the EditText
        }
}
}