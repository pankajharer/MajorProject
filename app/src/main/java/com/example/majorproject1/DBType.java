package com.example.majorproject1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class DBType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_dbtype);

        // Initialize UI elements
        Spinner diabetesTypeSpinner = findViewById(R.id.diabetesTypeSpinner);
        Button submitBtn = findViewById(R.id.submitBtn);

        // Define diabetes types
        String[] diabetesTypes = {"Type 1 Diabetes", "Type 2 Diabetes", "Gestational Diabetes", "Don't Know"};

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, diabetesTypes);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        diabetesTypeSpinner.setAdapter(adapter);

        // Handle item selection on the spinner
        diabetesTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selection, you can use the selected item
                String selectedDiabetesType = diabetesTypes[position];
                Toast.makeText(getApplicationContext(), "Selected Diabetes Type: " + selectedDiabetesType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        // Handle button click
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DBType.this,HomePage.class);
                startActivity(intent);
            }
        });
    }
}