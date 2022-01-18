package jp.co.benesse.touch.setuplogin;

import android.app.*;
import android.os.*;
import android.provider.Settings.System;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		System.putInt(getContentResolver(), "dcha_state", 3);
		System.putInt(getContentResolver(), "hide_navigation_bar", 0);
		
		//System.putInt(getContentResolver(),System.ACCELEROMETER_ROTATION, 1);
    }
}
