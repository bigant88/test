package com.devhn.amazingfactsaboutsex;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.startapp.android.publish.Ad;
import com.startapp.android.publish.AdDisplayListener;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

public class PositionActivity extends ActionBarActivity {

    private static final String TAG = PositionActivity.class.getSimpleName();
    private ViewPager mPositionPager;
    private PositionFragmentAdapter positionFragmentAdapter;
    private ActionBar actionBar;
    private StartAppAd startAppAd;
    int numberOfPageSelected = 0;
    private int pageThresHold = 6;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        AppController application = (AppController) getApplication();
        mTracker = application.getDefaultTracker();
        StartAppSDK.init(this, "211742685", true);
        startAppAd = new StartAppAd(this);
        setupActionBar();
        mPositionPager = (ViewPager) findViewById(R.id.position_pager);
        positionFragmentAdapter = new PositionFragmentAdapter(getSupportFragmentManager(), getApplicationContext());
        mPositionPager.setAdapter(positionFragmentAdapter);
        setOnPageChange();
        setupTitle(0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        startAppAd.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAppAd.onResume();
        mTracker.setScreenName(TAG);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onBackPressed() {
        startAppAd.onBackPressed();
        super.onBackPressed();
    }

    private void setupActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    public void displayInterstitial() {
        startAppAd.showAd(new AdDisplayListener() {

            /**
             * Callback when Ad has been hidden
             *
             * @param ad
             */
            @Override
            public void adHidden(Ad ad) {

            }

            /**
             * Callback when ad has been displayed
             *
             * @param ad
             */
            @Override
            public void adDisplayed(Ad ad) {
                numberOfPageSelected = 0;
//                pageThresHold *= 2;
            }

            /**
             * Callback when ad has been clicked
             *
             * @param arg0
             */
            @Override
            public void adClicked(Ad arg0) {

            }

            @Override
            public void adNotDisplayed(Ad ad) {

            }
        });
    }

    private void setOnPageChange() {
        mPositionPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                numberOfPageSelected++;
                if ((numberOfPageSelected) >= pageThresHold) {
                    displayInterstitial();
                }
                setupTitle(position);
                sendEvent(""+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupTitle(int position) {
        actionBar.setTitle(getString(R.string.position) + " " + (position + 1) + "/" + (Constants.position_spines.length ));
    }

    private void sendEvent(String label){
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Position")
                .setAction("PageSelected")
                .setLabel(label)
                .build());
    }
}
