package com.devhn.amazingfactsaboutsex;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laucher);
    }

    public void gotoFactsBtnClick(View v){
        Intent intent = new Intent(getApplicationContext(), FactActivity.class);
        startActivity(intent);
    }

    public void gotoPositionBtnClick(View v){
        Intent intent = new Intent(getApplicationContext(), PositionActivity.class);
        startActivity(intent);
    }
}
