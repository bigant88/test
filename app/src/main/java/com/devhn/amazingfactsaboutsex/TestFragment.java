package com.devhn.amazingfactsaboutsex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmobile.books.matmadavinci.database.Book;
import com.tmobile.books.matmadavinci.database.BookDatabase;

public final class TestFragment extends Fragment  {
	private static final String KEY_CONTENT = "TestFragment:Content";
	public static final String ARG_CHAPTER_NUMBER = "chapter_number";
	public static final String TAG = "TestFragment";
	static final int[] backgrounds = { R.drawable.zero, R.drawable.one,
			R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five,
			R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine };
	private TextView textView;
	int chapterIndex, chapterId;
//	AdView adViewFooter;
	private String mContent = "Loading";
	float fontSize;
	boolean disableAd = false; // co vo hieu hoa quang cao khong
	View scrollView;
	BookDatabase mBookDatabase;
	FactActivity mainActivity;
	String[] contentArray;
	ImageView imageView;
	// handler for received Intents for the "my-event" event
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Extract data included in the Intent
			String message = intent
					.getStringExtra(Constants.BROADCAST_MESSAGE_FONT_SIZE);
			textView.setTextSize(Float.valueOf(message));
			Log.d(TAG, "Got message: " + message);
		}
	};

    public static TestFragment newInstance(int chapId, float s) {
		TestFragment fragment = new TestFragment();
		fragment.chapterId = chapId;
		fragment.fontSize = s;
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
		mBookDatabase = BookDatabase.getInstance(getActivity());
		mainActivity = (FactActivity) getActivity();
		contentArray = mainActivity.getResources().getStringArray(
				R.array.content_array);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_chapter, container,
				false);
		textView = (TextView) rootView.findViewById(R.id.content);
		scrollView = rootView.findViewById(R.id.scrollView1);
		imageView = (ImageView) rootView.findViewById(R.id.image);
		textView.setTextSize(fontSize);
		textView.setText(contentArray[chapterId - 1]);
		imageView.setBackgroundResource(backgrounds[(chapterId % 10)]);
		Log.i(TAG, "content = " + contentArray[chapterId - 1]);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		disableAd = ToastAdListener.isAdDisable(getActivity());
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
				mMessageReceiver,
				new IntentFilter(Constants.BROADCAST_RECEIVER_KEY));

	}

	public void changeFontSize(int size) {
		textView.setTextSize(size);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
//		adViewFooter.destroy();
	}

	@Override
	public void onPause() {
		super.onPause();
		// Unregister since the activity is not visible
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
				mMessageReceiver);
//		adViewFooter.pause();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}
}
