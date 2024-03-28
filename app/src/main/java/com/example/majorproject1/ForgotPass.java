package com.example.majorproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ForgotPass extends AppCompatActivity {
    private ImageView img;
    private TextView textViewPasswordResetHead, textViewPasswordResetEmail;
    private EditText editTextPasswordReset;
    private Button buttonPasswordReset;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

            img = findViewById(R.id.img);
            textViewPasswordResetHead = findViewById(R.id.textView_password_reset_head);
            textViewPasswordResetEmail = findViewById(R.id.textView_password_reset_email);
            editTextPasswordReset = findViewById(R.id.editText_password_reset);
            buttonPasswordReset = findViewById(R.id.button_password_reset);
            progressBar = findViewById(R.id.progressbar);

            buttonPasswordReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Add your logic here to handle the button click (e.g., initiate password reset)
                }
            });
    }
}