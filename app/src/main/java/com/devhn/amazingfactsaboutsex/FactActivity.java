package com.devhn.amazingfactsaboutsex;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.startapp.android.publish.Ad;
import com.startapp.android.publish.AdDisplayListener;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;
import com.viewpagerindicator.CirclePageIndicator;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.InterstitialAd;

public class FactActivity extends ActionBarActivity implements Spinner.OnItemSelectedListener {

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    // private DrawerLayout mDrawerLayout;
    // private ListView mDrawerList;
    // private ActionBarDrawerToggle mDrawerToggle;
    private static final String TAG = "FactActivity";
    private SharedPreferences mPref;
    private Editor editor;
    private static float fontSize;
    private int bookmarkedSpine, selectedSpine;
    private View backPressedView, layoutView, customActionBar;
    PopupWindow backPressedPopup;
    boolean showRatePopup = true;
    FactFragmentAdapter mAdapter;
    ViewPager mPager;
    CirclePageIndicator mIndicator;
    String[] contentArray;
    Spinner spinner;
    private ActionBar actionBar;
    private PositionFragmentAdapter positionFragmentAdapter;
    private Tracker mTracker;
    //    private InterstitialAd interstitial;

    public String[] getContentArray() {
        return contentArray;
    }

    /**
     * StartAppAd object declaration
     */
    private StartAppAd startAppAd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "211742685", true);
        startAppAd = new StartAppAd(this);
        setContentView(R.layout.activity_main);
        AppController application = (AppController) getApplication();
        mTracker = application.getDefaultTracker();
//        customActionBar = getLayoutInflater().inflate(R.layout.custom_action_bar, null);
        /** Add Slider **/
//		StartAppAd.showSlider(this);
        contentArray = getResources().getStringArray(R.array.content_array);
        layoutView = findViewById(R.id.drawer_layout);
//        spinner = (Spinner) customActionBar.findViewById(R.id.spinner);
//        spinner.setOnItemSelectedListener(this);
        backPressedView = getLayoutInflater().inflate(
                R.layout.back_pressed_popup, null);
//		chapterContent = AppSingleton.getInstance().getListBooks();
        // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mPref = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        editor = mPref.edit();
        fontSize = mPref.getFloat(Constants.PREF_FONT_SIZE, 16);
        // Set the adapter for the list view
        // mDrawerList.setAdapter(new ArrayAdapter<String>(this,
        // R.layout.drawer_list_item, Constants.listMenu));
        // Set the list's click listener
        // mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mTitle = mDrawerTitle = getTitle();
        // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setupActionBar();
        // mDrawerLayout.openDrawer(Gravity.LEFT);
        mAdapter = new FactFragmentAdapter(getSupportFragmentManager(),
                getApplicationContext());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mPager.setOffscreenPageLimit(1);
        if (savedInstanceState == null) {
            // lay tu trong intent ra
            bookmarkedSpine = mPref.getInt(Constants.PREF_BOOKMARK_SPINE, 0);
            selectViewPagerIndicator(bookmarkedSpine);
        } else {
            if (getIntent() != null) {
                if (getIntent().getExtras() != null) {
                    int clickedChapter = getIntent().getExtras().getInt(
                            Constants.BOOK_CHAPTER_NUMBER);
                    selectViewPagerIndicator(clickedChapter);
                }
            }
        }
        mIndicator
                .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        selectedSpine = position;
                        numberOfPageSelected++;
                        if ((numberOfPageSelected) >= pageThreshold) {
                            displayInterstitial();
                        }
                        setupTitle(position);
                        sendEvent("" + position);
                    }

                    @Override
                    public void onPageScrolled(int position,
                                               float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });

    }

    private void setupTitle(int position) {
        actionBar.setTitle(getString(R.string.fact) + " " + (position + 1) + "/" + (contentArray.length + 1));
    }

    private void setupActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(false);
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setCustomView(customActionBar);
        actionBar.setDisplayShowTitleEnabled(true);

    }

    int numberOfPageSelected = 0;
    int pageThreshold = 8;

    // Invoke displayInterstitial() when you are ready to display an
    // interstitial.
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
                pageThreshold *= 2;
                Log.i(TAG, "adDisplayed");
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

    @Override
    protected void onPause() {
        // tu dong bookmark
        editor.putInt(Constants.PREF_BOOKMARK_SPINE, selectedSpine);
        editor.commit();
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

    /**
     * chon index cua viewpager
     *
     * @param viewpagerIndex
     */
    private void selectViewPagerIndicator(int viewpagerIndex) {
        mPager.setCurrentItem(viewpagerIndex, true);
        selectedSpine = viewpagerIndex;
        setupTitle(selectedSpine);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        // mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemSelected " + position);
        if (position == 0) {
            mPager.setVisibility(View.VISIBLE);
            mIndicator.setVisibility(View.VISIBLE);
        } else {
            mPager.setVisibility(View.GONE);
            mIndicator.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void sendEvent(String label) {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Fact")
                .setAction("PageSelected")
                .setLabel(label)
                .build());
    }
}
