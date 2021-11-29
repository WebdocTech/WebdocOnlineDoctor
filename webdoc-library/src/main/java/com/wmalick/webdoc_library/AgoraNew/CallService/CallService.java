package com.wmalick.webdoc_library.AgoraNew.CallService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.wmalick.webdoc_library.AgoraNew.AudioCall.VoiceCall;
import com.wmalick.webdoc_library.AgoraNew.VideoCall.VideoCall;
import com.wmalick.webdoc_library.R;


public class CallService extends Service {

    String title, body;
    int notificationID = 4;

    Intent callingIntent;

    public static final String CHANNEL_ID = "Foreground_Calling_Service";
    public static final String CHANNEL_NAME = "Calling Service";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        title = intent.getStringExtra("title");
        body = intent.getStringExtra("body");

        showNotification();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void showNotification() {
        if (title.equalsIgnoreCase("Audio Call")) {
            callingIntent = new Intent(this, VoiceCall.class);
        } else if (title.equalsIgnoreCase("Video Call")) {
            callingIntent = new Intent(this, VideoCall.class);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, callingIntent, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logored)
                .setContentTitle(title)
                .setContentText(body)
                .setOnlyAlertOnce(true)
                .setSound(null)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createChannel(notificationManager);
        notificationManager.notify(notificationID, notificationBuilder.build());
        startForeground(notificationID, notificationBuilder.build());
    }

    public void createChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
        channel.setDescription("Handles calling in the background");
        notificationManager.createNotificationChannel(channel);
    }
}
