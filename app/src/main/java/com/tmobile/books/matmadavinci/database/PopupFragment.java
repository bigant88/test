package com.tmobile.books.matmadavinci.database;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.devhn.amazingfactsaboutsex.R;

public class PopupFragment extends DialogFragment {
	private static final String TAG = "PopupFragment";
	private Button yesButton;
	private TextView title, content;
	protected OnClickListener yesListener;
	protected String titleString, contentString, yesLabel;

	public static PopupFragment newInstance(String title, String message,
			String yesLabel, OnClickListener yesListener
		) {
		PopupFragment f = new PopupFragment();
		f.titleString = title;
		f.contentString = message;
		f.yesLabel = yesLabel;
		f.yesListener = yesListener;
		f.setCancelable(true);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		View v = initControl(inflater, container);
		initEvent();
		// getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		return v;
	}

	/**
	 * init controller
	 * 
	 * @param: n/a
	 * @return: n/a
	 * @throws: n/a
	 */
	private View initControl(LayoutInflater inflater, ViewGroup container) {
		View v = inflater.inflate(R.layout.popup_fragment_one_button,
				container, false);
		title = (TextView) v.findViewById(R.id.popup_fragment_one_button_title);
		content = (TextView) v
				.findViewById(R.id.popup_fragment_one_button_content);
		yesButton = (Button) v
				.findViewById(R.id.popup_fragment_one_button_yes_button);
		return v;

	}

	/**
	 * init event for controller
	 * 
	 * @param: n/a
	 * @return: n/a
	 * @throws: n/a
	 */
	private void initEvent() {
		try {
			if (title != null) {
				title.setText(titleString);
			}
			if (content != null) {
				content.setText(contentString);
			}
			if (yesButton != null) {
				yesButton.setText(yesLabel);
				yesButton.setOnClickListener(yesListener);
			}			
		} catch (Exception e) {
			Log.e(TAG, "initEvent", e);
		}
	}
}