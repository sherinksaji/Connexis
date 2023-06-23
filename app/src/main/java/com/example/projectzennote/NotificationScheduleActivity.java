package com.example.projectzennote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class NotificationScheduleActivity extends AppCompatActivity {
    final Calendar selectedDate = Calendar.getInstance();
    int dayOfMonth;
    int month;
    int year;

    PendingIntent pending_intent;
    AlarmManager alarm_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_schedule);

        dayOfMonth = getIntent().getIntExtra("dayOfMonth", 0);
        month = getIntent().getIntExtra("month", 0);
        year = getIntent().getIntExtra("year", 0);
        selectedDate.set(Calendar.YEAR, year);
        selectedDate.set(Calendar.MONTH,
                month);
        selectedDate.set(Calendar.DAY_OF_MONTH,
                dayOfMonth);
        selectedDate.set(Calendar.HOUR_OF_DAY, 7);
        selectedDate.set(Calendar.MINUTE, 0);
        selectedDate.set(Calendar.SECOND, 0);
        selectedDate.set(Calendar.MILLISECOND, 0);


        String sendingFormatedTime = DateFormat.getDateTimeInstance().format(selectedDate.getTimeInMillis());
        Log.i("sendingTime", sendingFormatedTime);// correct time is logged!!


        notification_channel();
        pending_intent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(this, broadcast_receiver.class), PendingIntent.FLAG_IMMUTABLE);
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);



        set_notification_alarm(60*1000);

        // selectedDate.getTimeInMillis() - System.currentTimeMillis()

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(NotificationScheduleActivity.this,HomeActivity.class));
    }

    public void set_notification_alarm(long interval) {
        long triggerAtMillis = System.currentTimeMillis() + interval;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarm_manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pending_intent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarm_manager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pending_intent);
        } else {
            alarm_manager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pending_intent);
        }
    }
    public void cancel_notification_alarm() {
        alarm_manager.cancel(pending_intent);
    }
    private void notification_channel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Reminder1";
            String description = "Reminder Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Notification", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

//
//        // Create a unique notification channel ID
//        String channelId = "project_zennote";
//
//        // Build the notification
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
//                .setContentTitle("ZenNote")
//                .setContentText("A letter from your past self awaits you")
//                .setSmallIcon(R.drawable.app_icon)
//                .setPriority(NotificationCompat.PRIORITY_HIGH) // Set higher priority
//                .setDefaults(NotificationCompat.DEFAULT_ALL) // Include default sound, vibration, etc.
//                .setAutoCancel(true);
//
//        // Create a notification manager
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//        // Check if the device is running Android Oreo (API 26) or higher
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // Create a notification channel for Android Oreo and higher
//            NotificationChannel channel = new NotificationChannel(channelId, "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        // Create a unique request code for the pending intent
//        int requestCode = generateRequestCode();
//
//        // Create a pending intent to launch AddNoteActivity
//        Intent intent = new Intent(this, AddNoteActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Set the pending intent on the notification builder
//        builder.setContentIntent(pendingIntent);
//
//        // Schedule the notification for the sendingTime
//        scheduleNotification(noteModel.getSendingTime(), requestCode, builder.build());
//    }
//
//    private void scheduleNotification(long sendingTime, int requestCode, Notification notification) {
//        // Create a pending intent to be triggered at the sendingTime
//        Intent intent = new Intent(this, AddNoteActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Get the AlarmManager system service
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//
//
//        // Schedule the alarm clock using the AlarmManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, sendingTime, pendingIntent);
//            Log.i("build version","went through");
//        } else {
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, sendingTime, pendingIntent);
//        }
//
//        // Display a toast or log a message indicating that the notification has been scheduled
//        String sendingFormatedTime = DateFormat.getDateTimeInstance().format(sendingTime);
//        Log.i("sendingTime",sendingFormatedTime);
//        Toast.makeText(this, "Notification scheduled for " + sendingTime, Toast.LENGTH_SHORT).show();
//    }
//    private int generateRequestCode() {
//        // Generate a unique request code using a random number or any other method
//        Random random = new Random();
//        return random.nextInt(1000000);
//    }
//
//    private int generateNotificationId() {
//        // Generate a unique notification ID using a random number or any other method
//        Random random = new Random();
//        return random.nextInt(1000000);
//    }

