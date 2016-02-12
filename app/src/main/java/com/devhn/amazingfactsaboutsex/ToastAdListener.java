/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devhn.amazingfactsaboutsex;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

/**
 * An ad listener that toasts all ad events.
 */
public class ToastAdListener extends AdListener {
	private Context mContext;
	private static final String TAG = "Adlistener";
	SharedPreferences mPref;
	Editor editor;
	AdOpenedListener mCallBack;
	public ToastAdListener(Context context, AdOpenedListener callBack) {
		this.mContext = context;
		mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
		editor = mPref.edit();
		mCallBack = callBack;
	}

	@Override
	public void onAdLoaded() {
		// Toast.makeText(mContext, "onAdLoaded()", Toast.LENGTH_SHORT).show();
		Log.i(TAG, "onAdLoaded");		
	}

	@Override
	public void onAdFailedToLoad(int errorCode) {
		String errorReason = "";
		switch (errorCode) {
		case AdRequest.ERROR_CODE_INTERNAL_ERROR:
			errorReason = "Internal error";
			break;
		case AdRequest.ERROR_CODE_INVALID_REQUEST:
			errorReason = "Invalid request";
			break;
		case AdRequest.ERROR_CODE_NETWORK_ERROR:
			errorReason = "Network Error";
			break;
		case AdRequest.ERROR_CODE_NO_FILL:
			errorReason = "No fill";
			break;
		}
		// Toast.makeText(mContext, String.format("onAdFailedToLoad(%s)",
		// errorReason),
		// Toast.LENGTH_SHORT).show();
	}

	/**
	 * Called when an ad opens an overlay that covers the screen
	 */
	@Override
	public void onAdOpened() {
		// Toast.makeText(mContext, "onAdOpened()", Toast.LENGTH_SHORT).show();
		Log.i(TAG, "onAdOpened");
		mCallBack.onAdOpened();
	}

	/**
	 * Called when the user is about to return to the application after clicking
	 * on an ad
	 */
	@Override
	public void onAdClosed() {
		// Toast.makeText(mContext, "onAdClosed()", Toast.LENGTH_SHORT).show();
		Log.i(TAG, "onAdClosed");
	}

	/**
	 * Called when an ad leaves the application (e.g., to go to the browser).
	 */
	@Override
	public void onAdLeftApplication() {
		// Toast.makeText(mContext, "onAdLeftApplication()",
		// Toast.LENGTH_SHORT).show();
		editor.putLong(Constants.PREF_CLICK_AD_OR_NOT_KEY,
				System.currentTimeMillis());
		editor.commit();
		Log.i(TAG, "onAdLeftApplication");
	}

	/**
	 * disable adview or not
	 * 
	 * @return
	 */
	public static boolean isAdDisable(Context context) {
		SharedPreferences mPref = PreferenceManager
				.getDefaultSharedPreferences(context);
		// khoang thoi gian lan cuoi click vao adView
		long lastTimeAdClicked = mPref.getLong(
				Constants.PREF_CLICK_AD_OR_NOT_KEY, 0);
		if ((System.currentTimeMillis() - lastTimeAdClicked) < Constants.TIME_RANGE_DISABLE_AD) {
			return true;
		} else
			return false;
	}
}
