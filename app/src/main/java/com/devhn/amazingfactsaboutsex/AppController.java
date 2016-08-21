package com.devhn.amazingfactsaboutsex;


import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.Calendar;

public class AppController extends Application {
    private static final String TAG = "AppController";
    private Tracker mTracker;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    public static final int ONE_DAY = 1000 * 60 * 60 * 24;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        initSingletons();
        setAlarm();
    }

    protected void initSingletons() {
        // Initialize the instance of MySingleton
        AppSingleton.initInstance();
    }

    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
    }

    @Override
    public void onTrimMemory(int level) {
        // TODO Auto-generated method stub
        super.onTrimMemory(level);
    }


    private void setAlarm() {
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 15);
        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                ONE_DAY, alarmIntent);
    }

}
