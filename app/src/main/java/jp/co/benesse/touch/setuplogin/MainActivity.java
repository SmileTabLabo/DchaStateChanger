package jp.co.benesse.touch.setuplogin;

import android.app.*;
import android.os.*;
import android.provider.Settings.System;
import android.content.Intent;
import android.net.Uri;
public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   //文字表示
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		//dcha 3 変更
		System.putInt(getContentResolver(), "dcha_state", 3);
		System.putInt(getContentResolver(), "hide_navigation_bar", 0);
		//開発者向けオプションを開く
		Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
		startActivity(intent);
		
		//3秒後にdcha 0に変更
		new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					//dcha 0 変更
					System.putInt(getContentResolver(), "dcha_state", 0);
					
				}
			}, 3000);
		
    }
}
