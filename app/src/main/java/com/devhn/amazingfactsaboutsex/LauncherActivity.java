package com.devhn.amazingfactsaboutsex;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class LauncherActivity extends Activity {

    private static final String TAG = LauncherActivity.class.getSimpleName();
    private Tracker mTracker;
    private RatingDialog mRatingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laucher);
        // Obtain the shared Tracker instance.
        AppController application = (AppController) getApplication();
        mTracker = application.getDefaultTracker();
        saveOpenAppTime();
        mRatingDialog = new RatingDialog();
        mRatingDialog.onCreate(this);
    }

    private void saveOpenAppTime() {
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = mPref.edit();
        edit.putLong(Constants.TIME_OPEN_APP, System.currentTimeMillis());
        edit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName(TAG);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void gotoFactsBtnClick(View v) {
        Intent intent = new Intent(getApplicationContext(), FactActivity.class);
        startActivity(intent);
    }

    public void gotoPositionBtnClick(View v) {
        Intent intent = new Intent(getApplicationContext(), PositionActivity.class);
        startActivity(intent);
    }

    public void gotoDownloadJokeBtnClick(View v) {
        gotoJokeMe();
    }

    public void feedBackClick(View v) {
        gotoStore();
    }

    @Override
    public void onBackPressed() {

        if (mRatingDialog.shouldShowAwesomePopup()) {
            mRatingDialog.showAwesomePopup();
        }else {
            super.onBackPressed();
        }
    }

    void gotoJokeMe() {
        final String appPackageName = "app.jokeme"; // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));

        }
        sendEvent();
    }

    void gotoStore() {
        final String appPackageName = getPackageName();// from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));

        }
        sendEvent();
    }

    private void sendEvent() {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("GotoJokeMe")
                .setAction("GotoJokeMe")
                .build());
    }
}
