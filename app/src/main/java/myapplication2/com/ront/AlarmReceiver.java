package myapplication2.com.ront;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

/**
 * Created by user on 7/3/18.
 */

public class AlarmReceiver extends BroadcastReceiver {

    Ringtone ringtone;
    private NotificationManager alarmNotificationManager;
    public String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

    @Override
    public void onReceive(Context context, Intent intent) {
        //this will update the UI with message
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();

        //using service for vibration
        Vibrator v = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
        v.vibrate(10000);

        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);



        String message  = intent.getStringExtra("assignment name");

        alarmNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.drawable.ic_add;
        long when = System.currentTimeMillis();


/*
        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this, NOTIFICATION_CHANNEL_ID).setContentTitle("Alarm").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(message);


        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());*/



    }

}