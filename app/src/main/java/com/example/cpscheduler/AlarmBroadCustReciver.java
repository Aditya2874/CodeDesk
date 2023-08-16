package com.example.cpscheduler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmBroadCustReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"noti")
                .setContentTitle("CONTEST REMINDER").setSmallIcon(R.drawable.profile).setContentText("Reminder for Contest")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(200,builder.build());
        Log.d("reciever","Recieved");
        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
    }
}
