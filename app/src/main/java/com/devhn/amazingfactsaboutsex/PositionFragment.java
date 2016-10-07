package com.devhn.amazingfactsaboutsex;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.HitBuilders;

/**
 * Created by thaodv on 2/16/16.
 */
public class PositionFragment extends Fragment {
    static final int[] backgrounds = {R.drawable.p1, R.drawable.p2,
            R.drawable.p3, R.drawable.p4,
            R.drawable.p5, R.drawable.p6,
            R.drawable.p7, R.drawable.p8,
            R.drawable.p9, R.drawable.p10,
            R.drawable.p11, R.drawable.p12,
            R.drawable.p13, R.drawable.p14,
            R.drawable.p15, R.drawable.p16,
            R.drawable.p17, R.drawable.p18,
            R.drawable.p19, R.drawable.p20,
            R.drawable.p21, R.drawable.p22,
            R.drawable.p23, R.drawable.p24,
            R.drawable.p25, R.drawable.p26,
            R.drawable.p27, R.drawable.p28,
            R.drawable.p29, R.drawable.p30,
            R.drawable.p31};

    private int positionID;
    AppController appController;
    private AdView mAdViewTop;
    private TextView mTvwPositionName, mTvwPositionBenefits, mTvwPositionTryThis, mTvwDesc;
    private ImageView mImgPositionImage;
    private String[] nameArray, benefitArray, tryThisArray, descArray;
    private View mViewLike, mViewTriedThis;

    public static PositionFragment newInstance(int positionID) {
        PositionFragment fragment = new PositionFragment();
        fragment.positionID = positionID;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appController = (AppController) getActivity().getApplication();
        MobileAds.initialize(appController, "ca-app-pub-6811776882268454~8360256126");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_position, container,
                false);
        mTvwPositionName = (TextView) rootView.findViewById(R.id.position_name);
        mTvwPositionBenefits = (TextView) rootView.findViewById(R.id.position_benefits);
        mTvwPositionTryThis = (TextView) rootView.findViewById(R.id.position_try_this);
        mTvwDesc = (TextView) rootView.findViewById(R.id.position_desc);
        mImgPositionImage = (ImageView) rootView.findViewById(R.id.position_image);
        nameArray = getResources().getStringArray(R.array.position_name_array);
        benefitArray = getResources().getStringArray(R.array.position_benefits_array);
        tryThisArray = getResources().getStringArray(R.array.position_try_this_array);
        descArray = getResources().getStringArray(R.array.position_desc_array);
        mViewTriedThis = rootView.findViewById(R.id.tried_this_layout);
        mViewLike = rootView.findViewById(R.id.like_layout);
        mAdViewTop = (AdView) rootView.findViewById((R.id.adViewTop));
        setupViews();
        setListeners();
        loadAdmobAds();
        return rootView;
    }

    private void loadAdmobAds() {
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("4A0CE67F80E22C84CF49D638ECDC09D5").build();
        mAdViewTop.loadAd(adRequest);
    }

    private void setListeners() {
        mViewTriedThis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appController.getDefaultTracker().send(new HitBuilders.EventBuilder()
                        .setCategory("Position")
                        .setAction("Tried")
                        .setLabel("" + positionID)
                        .build());
            }
        });
        mViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appController.getDefaultTracker().send(new HitBuilders.EventBuilder()
                        .setCategory("Position")
                        .setAction("Like")
                        .setLabel("" + positionID)
                        .build());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdViewTop.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdViewTop.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdViewTop.destroy();
    }

    private void setupViews() {
        mTvwPositionName.setText(nameArray[positionID - 1]);
        mTvwPositionBenefits.setText(benefitArray[positionID - 1]);
        mTvwPositionTryThis.setText(tryThisArray[positionID - 1]);
        mTvwDesc.setText(descArray[positionID - 1]);
        mImgPositionImage.setImageResource(backgrounds[positionID - 1]);
    }
}
