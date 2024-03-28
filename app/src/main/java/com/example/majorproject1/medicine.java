package com.example.majorproject1;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import java.util.Calendar;
import java.util.Date;


public class medicine extends AppCompatActivity {
        private static final String CHANNEL_ID = "channel1";
        private static final int NOTIFICATION_ID = 121;
        private EditText titleEditText, messageEditText;
        private TimePicker timePicker;
        private DatePicker datePicker;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_medicine);

            createNotificationChannel();

            titleEditText = findViewById(R.id.title);
            messageEditText = findViewById(R.id.message);
            timePicker = findViewById(R.id.timePicker);
            datePicker = findViewById(R.id.datePicker);

            findViewById(R.id.buttonAddMedicine).setOnClickListener(v -> {

                try {
                    if (checkNotificationPermissions(this)) {
                        scheduleNotification();
                        Toast.makeText(this, "Notification Set", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    titleEditText.setText(""+e);
                    // Optionally, display an error message to the user or handle the exception in another way
                }
            });
        }

        @SuppressLint("UnspecifiedImmutableFlag")
        private void scheduleNotification() {
            Intent intent = new Intent(getApplicationContext(), NotificationHelper.class);
            String title = titleEditText.getText().toString();
            String message = messageEditText.getText().toString();
            intent.putExtra("titleExtra", title);
            intent.putExtra("messageExtra", message);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getApplicationContext(),
                    NOTIFICATION_ID,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
            );

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            long time = getTimeInMillis();
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    time,
                    pendingIntent
            );

            showAlert(time, title, message);
        }

        private void showAlert(long time, String title, String message) {
            Date date = new Date(time);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Notification Scheduled")
                    .setMessage("Title: " + title + "\nMessage: " + message +
                            "\nAt: " + android.text.format.DateFormat.getLongDateFormat(getApplicationContext()).format(date) +
                            " " + android.text.format.DateFormat.getTimeFormat(getApplicationContext()).format(date))
                    .setPositiveButton("Okay", (dialog, which) -> {})
                    .show();
        }

        private long getTimeInMillis() {
            int minute = timePicker.getMinute();
            int hour = timePicker.getHour();
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hour, minute);
            return calendar.getTimeInMillis();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        private void createNotificationChannel() {
            CharSequence name = "Notify Channel";
            String description = "A Description of the Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        private boolean checkNotificationPermissions(Context context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                boolean isEnabled = notificationManager.areNotificationsEnabled();
                if (!isEnabled) {
                    Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
                    context.startActivity(intent);
                    return false;
                }
            } else {
                boolean areEnabled = NotificationManagerCompat.from(context).areNotificationsEnabled();
                if (!areEnabled) {
                    Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
                    context.startActivity(intent);
                    return false;
                }
            }
            return true;
        }
}