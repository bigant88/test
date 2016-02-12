package com.devhn.amazingfactsaboutsex;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tmobile.books.matmadavinci.database.Book;
import com.tmobile.books.matmadavinci.database.BookDatabase;

class TestFragmentAdapter extends FragmentPagerAdapter {
	protected static final String[] CONTENT = Constants.spines;
	private static ArrayList<Book> chapterContent;
	private int mCount = CONTENT.length;
	private Context mContext;
	SharedPreferences mPref;
	Editor editor;
	float fontSize;
	BookDatabase mBookDatabase;

	public TestFragmentAdapter(FragmentManager fm, Context context) {
		super(fm);
		mContext = context;
		// chapterContent = AppSingleton.getInstance().getListBooks();
		mBookDatabase = BookDatabase.getInstance(context);
		mPref = PreferenceManager.getDefaultSharedPreferences(context);
		editor = mPref.edit();
		fontSize = mPref.getFloat(Constants.PREF_FONT_SIZE, 16);
	}

	@Override
	public Fragment getItem(int position) {
		// Log.i(TAG, "position = "+ position);
		int chapterId = 1 + position % CONTENT.length;
		return TestFragment.newInstance(chapterId, fontSize);
		// return TestFragment.newInstance(
		// chapterContent.get(position % CONTENT.length)
		// .getChapterContent(), fontSize);
	}

	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mContext.getString(R.string.fact) + " " +  TestFragmentAdapter.CONTENT[position % CONTENT.length];
	}

	public void setCount(int count) {
		if (count > 0 && count <= 10) {
			mCount = count;
			notifyDataSetChanged();
		}
	}
}