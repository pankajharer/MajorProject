package com.example.majorproject1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Prediction extends AppCompatActivity {
    private Spinner timingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);


        Button checkStatusButton = findViewById(R.id.button);
        checkStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStatus();
            }
        });

        // Initialize and set up the timing spinner
        timingSpinner = findViewById(R.id.spinnerTiming);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.timing_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timingSpinner.setAdapter(adapter);

        // Set up a listener to capture the selected item from the spinner
        timingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item (if needed)
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }

    public void checkStatus() {
        EditText glucoseLevelEditText = findViewById(R.id.editTextGlucoseLevel);

        double glucoseLevel = Double.parseDouble(glucoseLevelEditText.getText().toString());
        String timing = timingSpinner.getSelectedItem().toString();

        // Logic to determine the status based on glucose level and timing
        String status = determineStatus(glucoseLevel, timing);

        // Display the result in a dialog box
        showStatusDialog(status, timing);
    }

    private String determineStatus(double glucoseLevel, String timing) {

        if (timing.equals("Fasting")) {
            if (glucoseLevel >= 70 && glucoseLevel <= 100) {
                return "Normal";
            } else if (glucoseLevel >= 100 && glucoseLevel <= 125) {
                return "Prediabetic";
            } else if (glucoseLevel >= 126) {
                return "Diabetic";
            }
        } else if (timing.equals("Postprandial")) {
            if (glucoseLevel < 140) {
                return "Normal";
            } else if (glucoseLevel >= 140 && glucoseLevel <= 199) {
                return "Prediabetic";
            } else if (glucoseLevel >= 200) {
                return "Diabetic";
            }
        } else if (timing.equals("Preprandial")) {
            if (glucoseLevel >= 80 && glucoseLevel <= 130) {
                return "Normal";
            }
            else {
                return "Diabetic";
            }
        } else if (timing.equals("Bedtime")) {
            if (glucoseLevel >= 100 && glucoseLevel <= 140) {
                return "Normal";
            }
            else
            {
                return "Diabetic";
            }

        } else if (timing.equals("Random")) {
            if (glucoseLevel < 150) {
                return "Normal";
            }
            else
            {
                return "Diabetic";
            }
        }

        return "Status Not Determined";
    }

    private void showStatusDialog(String status, String timing) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Blood Glucose Status");
        builder.setMessage("Timing: " + timing + "\nStatus: " + status);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Do something when OK button is clicked
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}