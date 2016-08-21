package com.devhn.amazingfactsaboutsex;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by thaodv on 8/21/16.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = AlarmReceiver.class.getSimpleName();
    long[] vibrate = {300, 200, 100, 10};

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive " + intent);
        boolean isOpenAppSinceOneday = checkOpenAppInOneDay(context);
        if(isOpenAppSinceOneday) return;
        NotificationCompat.Builder mBuilder = buildNotification(context);
        Intent resultIntent = new Intent(context, LauncherActivity.class);
        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }

    @NonNull
    private NotificationCompat.Builder buildNotification(Context context) {
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pos_logo_rounded);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.pos_logo_rounded)
                        .setLargeIcon(largeIcon)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setVibrate(vibrate).setSound(soundUri)
                        .setContentText(context.getString(R.string.find_position_of_you_today));
        mBuilder.setLights(Color.RED, 300, 1000);
        return mBuilder;
    }

    private boolean checkOpenAppInOneDay(Context context) {
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(context);
        long lastTimeOpenApp = mPref.getLong(Constants.TIME_OPEN_APP, 0);
        long passedTime = System.currentTimeMillis() - lastTimeOpenApp;
        if(passedTime < AppController.ONE_DAY){
            return true;
        }else return false;
    }
}
