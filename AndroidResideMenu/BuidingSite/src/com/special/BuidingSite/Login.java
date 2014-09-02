package com.special.BuidingSite;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.*;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.*;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.special.BuidingSite.base.BaseActivity;
import com.special.BuidingSite.common.BroadCastAction;
import com.special.BuidingSite.net.HttpStatusException;
import com.special.BuidingSite.net.HttpUtil;
import com.xiaomi.mipush.sdk.MiPushClient;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class Login extends BaseActivity/* implements AnyChatBaseEvent*/{
	private EditText mUser; // 帐号编辑框
	private EditText mPassword; // 密码编辑框
	private CheckBox rmWord;

	public static final String LOGIN_SUCCESS = "com.cfrt.patrol.loginSuccess";
	public static final String LOGIN_FAILED = "com.cfrt.patrol.loginFialed";
	public static final String PROCESS_OVER = "com.cfrt.patrol.processOver";
	public static final String Default_Server = "http://123.57.8.66/";
	private PackageInfo info = null;
	private static Login mContext = null;

	private Timer timer;
	//private ConfigEntity configEntity;
	//private AnyChatCoreSDK anychat=null;

	public static Login getInstance() {
		return mContext;
	}

	public void readPackageInfo() {
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block

			if (info == null)
				info = new PackageInfo();
			info.versionName = "Unknown Version";
			info.versionCode = 0;

			e.printStackTrace();
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_wx);
        MiPushClient.unregisterPush(this);

		mContext = this;
		mUser = (EditText) findViewById(R.id.login_user_edit);
		mPassword = (EditText) findViewById(R.id.login_passwd_edit);
		rmWord = (CheckBox) findViewById(R.id.remremberPswd);
		// mWebView = (WebView) findViewById(R.id.login_webview);
		// setServerInfo(null);

		readPackageInfo();

		LoadUserInfo();
		strUrl = getServerAddr();

		initBroadCaseReceiver();
		BaseActivity.resetLoadingPerson(this);
		
		/*configEntity = ConfigService.LoadConfig(this);
		InitialSDK();*/
	}

	private void initBroadCaseReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Login.LOGIN_SUCCESS);
		filter.addAction(Login.LOGIN_FAILED);
		filter.addAction(BroadCastAction.MSG_HEART_BEAT);

		ubreceiver = new HeartBroadcastReceiver();
		registerReceiver(ubreceiver, filter);
	}

	private HeartBroadcastReceiver ubreceiver = null;

	private void LoadUserInfo() {
		SharedPreferences sp = getSharedPreferences("special", 0);
		if (sp.getBoolean("isSaved", false)) {
			String username = sp.getString("name", "");
			String userpassword = sp.getString("password", "");
			if (!("".equals(username) && "".equals(username))) {
				mUser.setText(username);
				mPassword.setText(userpassword);
				rmWord.setChecked(true);
			}
		}

	}

	public String getServerAddr() {
		SharedPreferences sp = getSharedPreferences("special", 0);
		return sp.getString("serverAddr", Default_Server);
	}

	private void setServerInfo(String addr, Boolean sound, Boolean vibrate) {
		SharedPreferences sp = getSharedPreferences("special", 0);
		Editor editor = sp.edit();
		editor.putString("serverAddr", addr);
		editor.putBoolean("viberate", vibrate);
		editor.putBoolean("sound", sound);
		strUrl = addr;
		editor.commit();
	}

	private Boolean getSoundable() {
		SharedPreferences sp = getSharedPreferences("special", 0);
		Boolean viberate = sp.getBoolean("sound", true);
		return viberate;
	}

	private Boolean getViberatenable() {
		SharedPreferences sp = getSharedPreferences("special", 0);
		Boolean viberate = sp.getBoolean("viberate", true);
		return viberate;
	}

	public void setCurrentUser(JSONObject currentUser) {
		if (currentUser == null) {
			setCurrentUserStr(null);
		} else {
			setCurrentUserStr(currentUser.toString());
		}
	}

	private void setCurrentUserStr(String currentUser) {
		SharedPreferences sp = getSharedPreferences("special", 0);
		Editor editor = sp.edit();
		if (currentUser == null) {
			editor.remove("currentUser");
		} else {
			editor.putString("currentUser", currentUser);
		}
		editor.commit();
	}

	private void setCookieStr(String cookieStr) {
		Log.i(this.getClass().getSimpleName(),"Login.setCookieStr()  cookieStr=" + cookieStr);
		SharedPreferences sp = getSharedPreferences("special", 0);
		Editor editor = sp.edit();
		editor.putString("cookieStr", cookieStr);
		editor.commit();
	}

	private void SaveUserInfo() {
		SharedPreferences sp = getSharedPreferences("special", 0);
		Editor editor = sp.edit();

        editor.putString("store_name", mUser.getText().toString());
        editor.putString("store_password", mPassword.getText().toString());

		if (rmWord.isChecked()) {
			editor.putBoolean("isSaved", true);
			editor.putString("name", mUser.getText().toString());
			editor.putString("password", mPassword.getText().toString());
		} else {
			editor.putBoolean("isSave", false);
			editor.putString("name", "");
			editor.putString("password", "");
		}
		editor.commit();
	}

	private String strUrl = null;

	public boolean stopLogin = false;

    private void doLogin()
    {
        showWaitingDialog(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doLoginInternal();
            }
        }).start();
    }
	private void doLoginInternal() {
		//startActivity(new Intent(Login.this, LoadingActivity.class));
        Map<String, String> params = new HashMap<String, String>();
        params.put("userName",mUser.getText().toString());
        params.put("passWord",mPassword.getText().toString());
        boolean succees = false;
        try {
            final String response = HttpUtil.submitPost(this,loginUrl,params).toString();
            JSONObject resultObj = JSONObject.fromObject(response);
            JSONObject data = resultObj.getJSONObject("data");
            sendMessage(data.getString("msg"));
            if(resultObj.getBoolean("result"))
            {
                succees = true;
                setCurrentUser(data.getJSONObject("user"));
                if (shouldInit()) {
                    if(!TextUtils.isEmpty(data.getJSONObject("user").getString("phoneNumber"))) {
                        MiPushClient.registerPush(this, PhoneApp.APP_ID, PhoneApp.APP_KEY);
                    }else
                    {
                        sendMessage(R.string.empty_phone_number_tip);
                    }
                }
                //startActivity(new Intent(this,MenuActivity.class));
            }
        } catch (HttpStatusException e) {
            sendMessage(e.getMessage() + " status:" + e.getStatus());
        } catch (IOException e) {
            if(e instanceof ConnectException)
            {
                sendMessage("连接失败,请检查网络是否通畅");
            }else {
                e.printStackTrace();
            }
        } catch (net.sf.json.JSONException e)
        {
            sendMessage("返回数据格式异常，非Json格式");
        } finally{
            final boolean succ = succees;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(succ)
                    {
                        onLoginSuccess();
                    }else
                    {
                        closeWaitingDialog();
                    }
                }
            });
        }
    }

	String loginUrl = "user/login.json";

    private Dialog waitingDialog;
    //show waiting view
    private void showWaitingDialog(Context mContext){
        final Dialog dialog = new Dialog(mContext, R.style.guideDialog);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.loading, null);
        waitingDialog = dialog;
        dialog.setContentView(view);
        dialog.show();
    }
    private void closeWaitingDialog(){
        if(waitingDialog!=null&&waitingDialog.isShowing())
        {
            waitingDialog.dismiss();
            waitingDialog = null;
        }
    }

	@Override
	protected void onStop() {
		stopLogin = true;
		super.onStop();
	}

    @Override
    public void onDetachedFromWindow() {
        closeWaitingDialog();
        super.onDetachedFromWindow();
    }

    @Override
	protected void onDestroy() {
		stopLogin = true;
        closeWaitingDialog();
		if (ubreceiver != null)
			unregisterReceiver(ubreceiver);
		super.onDestroy();
	}

	//private boolean isLoginingVideo = false;
	private void onLoginSuccess() {
		SaveUserInfo();
        startActivity(new Intent(this,MenuActivity.class));
        finish();
	}

	public void onSettingClicked(View v) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.setting_dialog,
				(ViewGroup) findViewById(R.id.setting_dialog));

		final EditText text = (EditText) layout.findViewById(R.id.server_addr);
		final CheckBox sound = (CheckBox) layout.findViewById(R.id.soundRemind);
		final CheckBox vibrate = (CheckBox) layout
				.findViewById(R.id.vibrateRemind);
		text.setText(getServerAddr());
		
		sound.setChecked(getSoundable());
		vibrate.setChecked(getViberatenable());

		new AlertDialog.Builder(this).setTitle("服务中心地址").setView(layout)
				.setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Toast.makeText(instance, text.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        setServerInfo(text.getText().toString(),
                                sound.isChecked(), vibrate.isChecked());
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", null).show();
	}

	public void login_mainweixin(View v) {

		if ("".equals(mUser.getText().toString())
				|| "".equals(mPassword.getText().toString())) // 判断 帐号和密码
		{
			new AlertDialog.Builder(this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle(R.string.loginError)
					.setMessage(R.string.loginErrorMsg).create().show();
		} else if (!isNetworkAvalidate(this)) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setIcon(
					getResources().getDrawable(R.drawable.login_error_icon))
					.setTitle(R.string.networkError)
					.setMessage(R.string.networkErrorMsg);
			builder.setPositiveButton(R.string.menuSetting,
					new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = null;
							// 判断手机系统的版本 即API大于10 就是3.0或以上版本
							if (android.os.Build.VERSION.SDK_INT > 10) {
								intent = new Intent(
										android.provider.Settings.ACTION_WIRELESS_SETTINGS);
							} else {
								intent = new Intent();
								ComponentName component = new ComponentName(
										"com.android.settings",
										"com.android.settings.WirelessSettings");
								intent.setComponent(component);
								intent.setAction("android.intent.action.VIEW");
							}
							instance.startActivity(intent);

						}
					});
			builder.setNegativeButton(R.string.cancel, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.show();
		} else {
			doLogin();
		}

		// 登录按钮
		/*
		 * Intent intent = new Intent();
		 * intent.setClass(Login.this,Whatsnew.class); startActivity(intent);
		 * Toast.makeText(getApplicationContext(), "登录成功",
		 * Toast.LENGTH_SHORT).show(); this.finish();
		 */
	}

	public void login_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

	public void login_pw(View v) { // 忘记密码按钮
		Uri uri = Uri.parse("http://3g.qq.com");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
		// Intent intent = new Intent();
		// intent.setClass(Login.this,Whatsnew.class);
		// startActivity(intent);
	}

	private class HeartBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(BroadCastAction.MSG_HEART_BEAT)) {
				/*if (isServiceRunning(HeartBeatService.class.getName())) {
					List<Activity> list = PatrolApp.getInstance()
							.getActivityList();
					Activity last = list.get(list.size() - 1);

					if (last instanceof LoadingActivity) {
						sendBroadcast(new Intent(LOGIN_SUCCESS));
						return;
					} else if (last instanceof Login) {
						stopService(new Intent(instance, HeartBeatService.class));
					}
				}*/
			}
		}
	}
	/*
	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		if (!bSuccess) {
			onLoginPatrolSuccess();
			Toast.makeText(this, "连接视频聊天服务器失败", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		// TODO Auto-generated method stub
		Log.i(getClass().getSimpleName(), "OnAnyChatEnterRoomMessage dwRoomId:" + dwRoomId+" dwErrorCode:"+dwErrorCode);
	}

	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
		Log.i(getClass().getSimpleName(), "连接关闭，error：" + dwErrorCode);
	}

	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		isLoginingVideo = false;
		onLoginPatrolSuccess();
		if (dwErrorCode == 0) {
			Log.i(getClass().getSimpleName(),"登录视频服务器成功！");
		} else {
			Log.i(getClass().getSimpleName(),"登录视频服务器失败，错误代码：" + dwErrorCode);
		}
	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
		// TODO Auto-generated method stub
		Log.i(getClass().getSimpleName(), "OnAnyChatOnlineUserMessage dwUserNum:" + dwUserNum+" dwRoomId:"+dwRoomId);
	}

	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
		// TODO Auto-generated method stub
		Log.i(getClass().getSimpleName(), "OnAnyChatUserAtRoomMessage dwUserId:" + dwUserId+" bEnter:"+bEnter);
	}*/
    public boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
