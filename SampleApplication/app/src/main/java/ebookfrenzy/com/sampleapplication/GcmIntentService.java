package ebookfrenzy.com.sampleapplication;

/**
 * Created by Peru on 14-12-2015.
 */

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GcmIntentService extends IntentService {

    public GcmIntentService() {
        super("GcmIntentService");
    }
    private final static String message1 = "Notification from sample app";
    private final static String message2 = "Request accepted";
    private final static String message3 = "Sorry Request rejected";

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (extras != null && !extras.isEmpty()) {  // has effect of unparcelling Bundle
            // Since we're not using two way messaging, this is all we really to check for
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Logger.getLogger("GCM_RECEIVED").log(Level.INFO, extras.toString());

                showToast(extras.getString("message"));
                showNotification(extras.getString("message"));
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    protected void showToast(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void showNotification(final String message){
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.cast_ic_notification_1)
                        .setContentTitle("Convene notification")
                        .setContentText(message);
// Creates an Intent for the Activity
        Intent myIntent;
        switch(message){
            case message1:
                myIntent =
                        new Intent(Intent.makeMainActivity(new ComponentName(this, NotifyResultActivity.class)));
                break;
            case message2:
                myIntent =
                        new Intent(this, MainActivity.class);
                break;
            case message3:
                myIntent =
                        new Intent(this, MainActivity.class);
                break;
            default:
                myIntent =
                        new Intent(Intent.makeMainActivity(new ComponentName(this, NotifyResultActivity.class)));

        }
/* Sets the Activity to start in a new, empty task */
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
// Creates the PendingIntent
        PendingIntent notifyIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        myIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

// Puts the PendingIntent into the notification builder
        builder.setContentIntent(notifyIntent);
// Notifications are issued by sending them to the
// NotificationManager system service.
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// Builds an anonymous Notification object from the builder, and
// passes it to the NotificationManager
        // Sets an ID for the notification
        int mNotificationId = 001;
        mNotificationManager.notify(mNotificationId, builder.build());
    }
}