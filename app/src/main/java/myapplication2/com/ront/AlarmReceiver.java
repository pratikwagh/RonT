package myapplication2.com.ront;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.os.Vibrator;
import android.provider.Settings;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

/**
 * Created by user on 7/3/18.
 */

public class AlarmReceiver extends BroadcastReceiver {

    Ringtone ringtone;
    @Override
    public void onReceive(Context context, Intent intent) {
        //this will update the UI with message
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();
        Vibrator v = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
        v.vibrate(10000);

        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }

}