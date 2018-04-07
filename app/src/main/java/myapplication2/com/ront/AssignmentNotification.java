package myapplication2.com.ront;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

/**
 * Created by user on 6/4/18.
 */

public class AssignmentNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Vibrator v= (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        v.vibrate(10000);
    }
}
