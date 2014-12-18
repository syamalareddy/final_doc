package com.example.gre;

import android.app.Application;
import android.content.SharedPreferences;

public class BaseApplication extends Application {
	
	private SharedPreferences sharedPreferences;
	private static final String SETTINGS_CONFIG = "mastersGuideconfig";
	
	@Override
	public void onCreate() {
		super.onCreate();
		sharedPreferences = getApplicationContext().getSharedPreferences(
				SETTINGS_CONFIG, MODE_PRIVATE);
	}
	
	public void saveLoginIn(boolean isLoggedIn) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean("isLoggedIn", isLoggedIn);
		editor.commit();
	}
	
	public boolean isLoggedIn(){
		return sharedPreferences.getBoolean("isLoggedIn", false);
	}

}
