package com.example.majorproject1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, confirmPasswordEditText,phoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.pass);
        confirmPasswordEditText = findViewById(R.id.confirm_pass);
        phoneEditText= findViewById(R.id.phone);


        Button signUpButton = findViewById(R.id.signup);
        Button login =findViewById(R.id.login);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SignUp.this,MainActivity.class);
                startActivity(intent);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }


    private void signUp() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String phoneSet = phoneEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phoneSet.isEmpty()) {
            showToast("Please fill in all fields");
        } else if (!isValidPhoneNumber(phoneSet)) {
            showToast("Invalid phone number");
        } else if (!password.equals(confirmPassword)) {
            showToast("Passwords do not match");
        } else {
            showToast("Sign up successful!");
            ConnectionHelper con = new ConnectionHelper(this);
            con.addUser(username, password, phoneSet);

        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.length() >= 10;
    }
}
