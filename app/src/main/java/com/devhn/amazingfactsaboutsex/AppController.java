package com.devhn.amazingfactsaboutsex;


import android.app.Application;
import android.content.res.Configuration;

public class AppController extends Application {
	private static final String TAG = "AppController";
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initSingletons();
	}
	protected void initSingletons()
	{
		// Initialize the instance of MySingleton
		AppSingleton.initInstance();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	@Override
	public void onTrimMemory(int level) {
		// TODO Auto-generated method stub
		super.onTrimMemory(level);
	}

}
