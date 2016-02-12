package com.tmobile.books.matmadavinci.database;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.devhn.amazingfactsaboutsex.R;

public class PopupHelper {
    private static final String TAG = PopupHelper.class.getSimpleName();
    private static PopupHelper mInstance = null;
    private Context mContext;
    private PopupFragment mPopupFragment;

    public static PopupHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PopupHelper(context);
        }
        return mInstance;
    }

    private PopupHelper(Context context) {
        mContext = context;
    }

    public void hideAllPopup() {
        if (mPopupFragment != null && mPopupFragment.isVisible())
            mPopupFragment.dismiss();       
    }

  

    /**
     * show message
     *
     * @param fragmentManager
     * @param message
     * @param title
     * @param
     * @author dungnh8
     */
    public void showNotification(FragmentManager fragmentManager, String title, String message) {
        // show error popup
        View.OnClickListener closeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mPopupFragment.dismiss();
                } catch (Exception e) {
                    Log.e(TAG, "showError", e);
                }
            }
        };
        String yesLabel = mContext.getString(R.string.close);
        mPopupFragment = PopupFragment.newInstance(title, message, yesLabel,
                 closeListener);
        mPopupFragment.show(fragmentManager, TAG);
    }

    /**
     * dismiss loading
     *
     * @author dungnh8
     */
    public void startLoading(final AnimationDrawable loadAnimation,
                             final ImageView loading) {
        try {
            loading.setVisibility(View.VISIBLE);
            loading.post(new Runnable() {

                @Override
                public void run() {
                    try {
                        loadAnimation.start();
                    } catch (Exception e) {
                        Log.e(TAG, "startLoading", e);
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "startLoading", e);
        }
    }

    /**
     * dismiss loading
     *
     * @author dungnh8
     */
    public static void dismissLoading(AnimationDrawable loadAnimation,
                                      ImageView loading) {
        loadAnimation.stop();
        loading.setVisibility(View.GONE);
    }

  
    public void dismissDialog() {
        if (mPopupFragment != null && mPopupFragment.isVisible())
            mPopupFragment.dismiss();
    }

   
}