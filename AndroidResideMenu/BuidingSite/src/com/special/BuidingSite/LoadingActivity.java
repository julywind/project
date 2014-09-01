package com.special.BuidingSite;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import com.special.BuidingSite.base.BaseActivity;
import com.special.BuidingSite.common.BroadCastAction;

public class LoadingActivity extends BaseActivity {
	private class LoginBroadcastReceiver extends BroadcastReceiver {
		public LoginBroadcastReceiver() {
			super();
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			System.out
					.println("LoadingActivity.LoginBroadcastReceiver.onReceive()  "+intent.getAction());
			
			if (intent.getAction().equals(Login.LOGIN_SUCCESS)) {
				LoadingActivity.this.login(true);
			}
			if (intent.getAction().equals(BroadCastAction.MSG_HEART_BEAT)) {
				LoadingActivity.this.login(true);
			} else if (intent.getAction().equals(Login.LOGIN_FAILED)) {
				LoadingActivity.this.login(false);
			} else if (intent.getAction().equals(Login.PROCESS_OVER)) {
				LoadingActivity.this.over();
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
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Log.i(this.getClass().getSimpleName(),"Login.onKeyDown()  set stopLogin=true");
			if (Login.getInstance() != null) {
				Login.getInstance().stopLogin = true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);

		IntentFilter filter = new IntentFilter();
		filter.addAction(Login.LOGIN_SUCCESS);
		filter.addAction(Login.LOGIN_FAILED);
		filter.addAction(Login.PROCESS_OVER);
		filter.addAction(BroadCastAction.MSG_HEART_BEAT);

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

	public void login(Boolean result) {
		if (!result) {
			finish();
			return;
		}

		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
		finish();
		Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT)
				.show();
	}

	public void over() {
		this.finish();
	}
}