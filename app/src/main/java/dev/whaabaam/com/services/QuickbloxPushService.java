package dev.whaabaam.com.services;

import android.app.Notification;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppPreferences;
import dev.whaabaam.com.app.MyApplication;

public class QuickbloxPushService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage != null) {
            if (AppPreferences.getInstance(getApplicationContext())
                    .getBoolean(AppPreferences.PREF_KEYS.IS_LOGGED_IN)) {
                if (remoteMessage.getData() != null) {
                    if (remoteMessage.getData().containsKey("message"))
                        createNotification(remoteMessage.getData().get("message"), MyApplication.APP_NAME);
                    else
                        createNotification(remoteMessage.getData().get("body"), remoteMessage.getData().get("title"));
                }
            }
        }
    }

    public void createNotification(String body, String title) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                .setSmallIcon(R.drawable.logo_small)
                .setAutoCancel(true)
                .setContentText(body)
                .setContentTitle(title)
                .setDefaults(Notification.DEFAULT_ALL)
                .setVibrate((new long[]{0, 1000, 500, 1000}));
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(3245, builder.build());
    }
}
