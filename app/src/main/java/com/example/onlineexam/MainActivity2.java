//Mainactivity2 register
package com.example.onlineexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineexam.dbConnection.DBConnection;
import com.example.onlineexam.params.Params;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.android.material.textfield.TextInputLayout;
public class MainActivity2 extends AppCompatActivity implements TextWatcher {
    Button b,log_btn;
    EditText name,gmail,pass,number,username;
    TextInputLayout tusername,tname,tnumber,tgmail,tpassword,tcpassword;
    TextView war,war1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        tusername=findViewById(R.id.username_r);
        tname=findViewById(R.id.iname);
        tnumber=findViewById(R.id.mo_num);
        tgmail=findViewById(R.id.gmail_e);
        tpassword=findViewById(R.id.password_r);
        tcpassword=findViewById(R.id.password_r_confirm);
        //find related edittext
        name=tname.getEditText();
        pass=tpassword.getEditText();
        gmail=tgmail.getEditText();
        number=tnumber.getEditText();
        username=tusername.getEditText();
        log_btn=findViewById(R.id.log_btn);
        b=findViewById(R.id.registor);
//        war=findViewById(R.id.war);
       username.addTextChangedListener(this);
       number.addTextChangedListener(this);
       name.addTextChangedListener(this);
        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this,LoginActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                tpassword.setError(null);
                tusername.setError(null);
                tnumber.setError(null);
                tgmail.setError(null);
                tcpassword.setError(null);
                tname.setError(null);
                if (name.getText().toString().equals("")&&name.getText().toString().length()<5)
                {
                    tname.setError("Enter Valid Name");
                    return;
                }

                if (!validateUsername())
                    return;
                if (!validateEmail())
                    return;
                if (!validateNumber())
                    return;
                if (!validatePassword())
                    return;

                StrictMode.ThreadPolicy a=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(a);
                try {
                    Connection c=DBConnection.getConnection();
                    Statement s=c.createStatement();
                    String sql="INSERT INTO "+Params.Stu_DB_NAME+"(name,gmail,pass,username,number) VALUES ('"+name.getText().toString()+"','"+gmail.getText().toString()+"','"+pass.getText().toString()+"','"+username.getText().toString()+"','"+number.getText().toString()+"');";
                    s.executeUpdate(sql);
                    name.setText("");pass.setText(""); gmail.setText("");
                    c.close();
                    Toast.makeText(MainActivity2.this,"Ragistration Susceesfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity2.this,LoginActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
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
    public boolean validateUsername() {
        String username1=username.getText().toString();
        // Define username criteria
        int minLength = 5; // Minimum length of the username
        int maxLength = 20; // Maximum length of the username
        String allowedChars = "a-zA-Z0-9_"; // Allowed characters (alphabets, numbers, and underscore)

        // Validate username length
        if (username1.length()<minLength || username1.length() > maxLength) {
            tusername.setError("Minimum 5 Character needed");
            return false;
        }

        // Validate if username contains only allowed characters
        String regex = "^[a-zA-Z0-9_]+$";
        if (!username1.matches(regex)) {
            tusername.setError("Enter Valid Username");
            return false;
        }

        // All validation passed, username is valid
        return true;
    }

    public boolean validateNumber(){
        if (((number.getText().toString().length())<10)&&(number.getText().toString().length())>10){
        return true;
        }
        tnumber.setError("Enter 10 Digit Phone Number");
        return false;
   }
    public boolean validatePassword() {
        String password=pass.getText().toString();
        // Define password criteria
        int minLength = 8; // Minimum length of the password
        int maxLength = 20; // Maximum length of the password
        if (!password.equals(tcpassword.getEditText().getText().toString())){
            tpassword.setError("Input password not match ");
            tcpassword.setError("Input Password not match");
            return false;
        }
        String specialChars = "!@#$%^&*()-_=+[]{}|;:,.<>?"; // Special characters allowed

        // Validate password length
        if (password.length() < minLength || password.length() > maxLength) {
            tpassword.setError("Minimum 8 or Maximum 20 Character");
            return false;
        }

        // Validate if password contains at least one uppercase letter
        if (!password.matches(".*[A-Z]+.*")) {
            tpassword.setError("Least one Upper case ");
            return false;
        }

        // Validate if password contains at least one lowercase letter
        if (!password.matches(".*[a-z]+.*")) {
            tpassword.setError("Least one Lower case");
            return false;
        }

        // Validate if password contains at least one digit
        if (!password.matches(".*\\d+.*")) {
            tpassword.setError("Least one digit");
            return false;
        }

        // Validate if password contains at least one special character
        if (!password.matches(".*[" + Pattern.quote(specialChars) + "]+.*")) {
            tpassword.setError("Least one Special Character");
            return false;
        }

        // All validation passed, password is valid
        return true;
    }
    public boolean validateEmail() {
        String email=gmail.getText().toString();
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()){
            tgmail.setError("Enter Valid Email");
        }
        return true;
    }
}