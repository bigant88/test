package com.devhn.amazingfactsaboutsex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.PopupWindow;

import com.startapp.android.publish.Ad;
import com.startapp.android.publish.AdDisplayListener;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends ActionBarActivity {

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	// private DrawerLayout mDrawerLayout;
	// private ListView mDrawerList;
	// private ActionBarDrawerToggle mDrawerToggle;
	private static final String TAG = "MainActivity";
	private SharedPreferences mPref;
	private Editor editor;
	private static float fontSize;
	private int bookmarkedSpine, selectedSpine;
	View backPressedView, layoutView;
	PopupWindow backPressedPopup;
	boolean showRatePopup = true;
	TestFragmentAdapter mAdapter;
	ViewPager mPager;
	PageIndicator mIndicator;
	String[] contentArray;
//    private InterstitialAd interstitial;

    public String[] getContentArray() {
		return contentArray;
	}
	/** StartAppAd object declaration */
	private StartAppAd startAppAd;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StartAppSDK.init(this, "211742685", true);
        startAppAd = new StartAppAd(this);
		setContentView(R.layout.activity_main);
		/** Add Slider **/
//		StartAppAd.showSlider(this);
		contentArray = getResources().getStringArray(R.array.content_array);
		layoutView = findViewById(R.id.drawer_layout);
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

		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setHomeButtonEnabled(false);
		// mDrawerLayout.openDrawer(Gravity.LEFT);
		mAdapter = new TestFragmentAdapter(getSupportFragmentManager(),
				getApplicationContext());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		mIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
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
						if ((numberOfPageSelected) >= 8) {
							displayInterstitial();
						}

					}

					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {
					}

					@Override
					public void onPageScrollStateChanged(int state) {
					}
				});
		loadInterstitialAd();

	}

	int numberOfPageSelected = 0;

	private void loadInterstitialAd() {
//		// Create the interstitial.
//		interstitial = new InterstitialAd(this);
//		interstitial.setAdUnitId(getString(R.string.amazing_interstitial_ad_unit_id));
////
////		// Create ad request.
//		AdRequest adRequest = new AdRequest.Builder().build();
////		// Begin loading your interstitial.
//		interstitial.loadAd(adRequest);

	}

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
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.i(TAG, "onCreateOptionsMenu");
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		// boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.increase_font).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		// if (mDrawerToggle.onOptionsItemSelected(item)) {
		// return true;
		// }
		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.increase_font:
			// create intent to perform web search for this planet
			fontSize += 2;
			editor.putFloat(Constants.PREF_FONT_SIZE, fontSize);
			editor.commit();
			mAdapter.notifyDataSetChanged();
			// define broadcast and send
			sendBroadcast(fontSize);
			//
			return true;

		case R.id.decrease_font:
			fontSize -= 2;
			editor.putFloat(Constants.PREF_FONT_SIZE, fontSize);
			editor.commit();
			// define broadcast and send
			sendBroadcast(fontSize);
			mAdapter.notifyDataSetChanged();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void sendBroadcast(float fontSize) {
		Intent intent = new Intent(Constants.BROADCAST_RECEIVER_KEY);
		// add data
		intent.putExtra(Constants.BROADCAST_MESSAGE_FONT_SIZE,
				String.valueOf(fontSize));
		// intent.setAction("de.vogella.android.mybroadcast");
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

	WebView webview;
	int i = 0;



	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

}
