package com.example.majorproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ai.client.generativeai.BuildConfig;
import com.google.ai.client.generativeai.GenerativeModel;

import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DietGen extends AppCompatActivity {

    private EditText questionEditText;
    private Button submitButton;
    private TextView responseTextView;
    private ProgressBar spinner;

    private GenerativeModelFutures modelFutures;
    private Executor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_gen);

        submitButton = findViewById(R.id.submitButton);
        responseTextView = findViewById(R.id.responseTextView);
        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        String apiKey = "AIzaSyDDNt2pyJEOUa-5Caxq8lKCfoZlV0UGOOY";
        GenerativeModel model = new GenerativeModel("gemini-pro", apiKey);
        modelFutures = GenerativeModelFutures.from(model);

        String prompt = "You are a skilled Diet Generator with a deep understanding of diabetics and its recovery meal, \n" +
                "The task is to generate a diet plan specifically for a diabetic patient.\n" +
                "The diet plan should focus on optimizing blood sugar levels and promoting overall health.\n" +
                "It specifies the meal components (breakfast, lunch, dinner, and snacks) and asks for details like portion sizes and nutritional values.\n" +
                "It highlights the importance of incorporating whole grains, lean proteins, healthy fats, and low-glycemic index foods to manage blood sugar effectively.\n" +
                "The prompt emphasizes the need for variety and balance in the meal plan to meet nutritional requirements while considering diabetes management.";

        executor = Executors.newCachedThreadPool();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPrompt(prompt);
            }
        });
    }

    private void submitPrompt(String prompt) {
        spinner.setVisibility(View.VISIBLE);
        Content content = new Content.Builder().addText(prompt).build();
        ListenableFuture<GenerateContentResponse> response = modelFutures.generateContent(content);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spinner.setVisibility(View.GONE);
                        responseTextView.setText(result.getText());
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spinner.setVisibility(View.GONE);
                        responseTextView.setText("Error: " + t.getMessage());
                        t.printStackTrace();
                    }
                });
            }
        }, executor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (spinner != null && spinner.isShown()) {
            spinner.setVisibility(View.GONE);
        }

    }
}