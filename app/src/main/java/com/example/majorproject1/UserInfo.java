package com.example.majorproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UserInfo extends AppCompatActivity {

    private EditText nameEditText, ageEditText, weightEditText;
    private RadioGroup genderRadioGroup;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_user_info);

        nameEditText = findViewById(R.id.name);
        ageEditText = findViewById(R.id.age);
        weightEditText = findViewById(R.id.weight);
        genderRadioGroup = findViewById(R.id.Gender);
        saveButton = findViewById(R.id.saveNameButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveDetails();
            }
        });
    }

    public void saveDetails() {
        String name = nameEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();
        String weight = weightEditText.getText().toString().trim();
        int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
        String gender = selectedGenderRadioButton.getText().toString();

        if (validateInputs(name, age, weight, gender)) {
            ConnectionHelper con = new ConnectionHelper(this);

            // Step 1: Add user and get user ID
            // Retrieve the username from shared preferences
            String savedUsername = getUsernameFromPreferences();

            ConnectionHelper con1 = new ConnectionHelper(this);

            long userId = con.getUserIdByUsername(savedUsername);


            if (userId != -1) {
                // Step 2: If user was added successfully, insert details into user_info table
                boolean success = con.addUserInfo(userId, name, age, weight, gender);

                if (success) {
                    // Display a toast message
                    Toast.makeText(this, "Details saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Display an error message
                    Toast.makeText(this, "Failed to save details", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Display an error message for user insertion failure
                Toast.makeText(this, "Failed to add user", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private String getUsernameFromPreferences() {
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        return preferences.getString("username", ""); // "" is the default value if the key is not found
    }




    public boolean validateInputs(String name, String age, String weight, String gender) {
        if (name.isEmpty() || age.isEmpty() || weight.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        // You can add more specific validation logic if needed
        return true;
    }


}
