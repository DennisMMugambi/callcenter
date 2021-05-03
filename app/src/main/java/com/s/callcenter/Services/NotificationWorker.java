package com.s.callcenter.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s.callcenter.R;

public class NotificationWorker extends Worker {
    private static final String WORK_RESULT = "work_result";
    private static final String ACTIVE_REQUEST_NOTIFICATION_ID = "77717";
    private static final String ACTIVE_REQUEST_NOTIFICATION_CHANNEL_ID = "20640";
    DatabaseReference activeRequestRef;
    //long numChildren = 0;
   // long numNewChildren;
    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        activeRequestRef = FirebaseDatabase.getInstance().getReference("active_requests");

         activeRequestRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               // Log.d("query run", "query has run");
             //   numNewChildren = snapshot.getChildrenCount();
               // if(numNewChildren != numChildren) {
                    showNotification();
               // }
                //numChildren = numNewChildren;

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //numNewChildren = 0;
                //numChildren = -1;
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       /* activeRequestRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    showNotification();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
       //showNotification();
      // Toast.makeText(getApplicationContext(), "notification works", Toast.LENGTH_LONG).show();
        Data outputData = new Data.Builder().putString(WORK_RESULT, "Jobs Finished").build();
        return Result.success(outputData);
    }

    private void showNotification(){
        NotificationManager notificationManager = (NotificationManager)
                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel mChannel = new NotificationChannel(ACTIVE_REQUEST_NOTIFICATION_CHANNEL_ID,
                    "active_requests_channel", NotificationManager.IMPORTANCE_HIGH);
            mChannel.setDescription("requests");
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            mChannel.enableVibration(true);

            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder builder= new NotificationCompat.Builder(getApplicationContext(), ACTIVE_REQUEST_NOTIFICATION_CHANNEL_ID);
        builder.setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.ic_person_pin)
                .setLargeIcon(largeIcon(getApplicationContext()))
                .setContentTitle(getApplicationContext().getString(R.string.new_active_request))
                .setContentText(getApplicationContext().getString(R.string.click_to_view_requests))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(getApplicationContext().getString(
                        R.string.click_to_view_requests
                )));
        // COMPLETED (9) Create a notification channel for Android O devices
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    ACTIVE_REQUEST_NOTIFICATION_CHANNEL_ID,
                    getApplicationContext().getString(R.string.active_request_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            mChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            mChannel.enableVibration(true);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),ACTIVE_REQUEST_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_person_pin)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setLargeIcon(largeIcon(getApplicationContext()))
                .setContentTitle(getApplicationContext().getString(R.string.new_active_request))
                .setContentText(getApplicationContext().getString(R.string.click_to_view_requests))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        getApplicationContext().getString(R.string.click_to_view_request)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
//                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);
        */


        // COMPLETED (11) If the build version is greater than or equal to JELLY_BEAN and less than OREO,
        // set the notification's priority to PRIORITY_HIGH.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        // COMPLETED (12) Trigger the notification by calling notify on the NotificationManager.
        // Pass in a unique ID of your choosing for the notification and notificationBuilder.build()
        notificationManager.notify(Integer.parseInt(ACTIVE_REQUEST_NOTIFICATION_ID), builder.build());
    }

    private static Bitmap largeIcon(Context context) {
        // COMPLETED (5) Get a Resources object from the context.
        Resources res = context.getResources();
        // COMPLETED (6) Create and return a bitmap using BitmapFactory.decodeResource, passing in the
        // resources object and R.drawable.ic_local_drink_black_24px
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_person_pin);
        return largeIcon;
    }
}
