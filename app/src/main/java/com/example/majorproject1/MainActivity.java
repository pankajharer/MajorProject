package com.example.majorproject1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.pass);
        Button loginButton = findViewById(R.id.btnlogin);
        TextView signup=findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,SignUp.class);
                startActivity(i);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateCredentials();
            }
        });


    }
    private void validateCredentials() {
        // Get the entered username and password
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        boolean isValidCredentials = false;

        ConnectionHelper dbhelper = new ConnectionHelper(this);
        ArrayList<LoginModel> arruser = dbhelper.fetchUser();


        if (username.isEmpty() || password.isEmpty()) {

            Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
        }

        for(int i=0;i<arruser.size();i++)
        {
            if (username.equals((arruser.get(i).username)) && password.equals((arruser.get(i).password))) {
                isValidCredentials = true;
            }

        }

        if (isValidCredentials) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            String loggedInUsername = username;
            saveUsernameInPreferences(loggedInUsername);
            Intent i1=new Intent(MainActivity.this,UserInfo.class);
            startActivity(i1);
        } else {
            Toast.makeText(this, "Invalid username or password. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveUsernameInPreferences(String username) {
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

}