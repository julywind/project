package com.special.BuidingSite;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import com.special.BuidingSite.base.BaseActivity;

public class LoadingActivity2 extends BaseActivity {
	private class LoginBroadcastReceiver extends BroadcastReceiver {
		public LoginBroadcastReceiver() {
			super();
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			System.out
					.println("LoadingActivity2.LoginBroadcastReceiver.onReceive()  "+intent.getAction());
			
			if (intent.getAction().equals(Login.PROCESS_OVER)) {
				LoadingActivity2.this.over();
			}
		}
	}

	private LoginBroadcastReceiver ubreceiver = null;

	@Override
	protected void onDestroy() {
		if (ubreceiver != null)
			unregisterReceiver(ubreceiver);
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading2);

		IntentFilter filter = new IntentFilter();
		filter.addAction(Login.PROCESS_OVER);

		ubreceiver = new LoginBroadcastReceiver();
		registerReceiver(ubreceiver, filter);

		/*
		 * new Handler().postDelayed(new Runnable(){
		 * 
		 * @Override public void run(){
		 * 
		 * Toast.makeText(getApplicationContext(), "登录失败",
		 * Toast.LENGTH_SHORT).show(); LoadingActivity.this.finish(); if(1==1)
		 * return;
		 * 
		 * Intent intent = new Intent (LoadingActivity.this,SDKDemoMain.class);
		 * startActivity(intent); LoadingActivity.this.finish();
		 * Toast.makeText(getApplicationContext(), "登录成功",
		 * Toast.LENGTH_SHORT).show(); } }, 200);
		 */
	}

	public void over() {
		this.finish();
	}
}