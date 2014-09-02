package com.special.BuidingSite.ui;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import com.special.BuidingSite.net.HttpUtil;
import com.special.BuidingSite.receiver.MessageReceiver;
import com.xiaomi.mipush.sdk.Constants;
import net.sf.json.JSONObject;

public class PhoneApp extends Application {

    // user your appid the key.
    public static final String APP_ID = "2882303761517252451";
    // user your appid the key.
    public static final String APP_KEY = "5751725215451";

	private List<Activity> mList = new LinkedList<Activity>(); 
    private static PhoneApp instance;
    private static MessageReceiver.MessageHandler handler = null;

    // 此TAG在adb logcat中检索自己所需要的信息， 只需在命令行终端输入 adb logcat | grep
    public static final String TAG = "com.special.BuidingSite";
    public static PhoneApp getInstance()
    {
    	return instance;
    }

	@Override
	public void onCreate() {
		super.onCreate();
        Log.i(this.getClass().getSimpleName(),"PhoneApp.onCreate()");
		instance = this;
        if (handler == null)
            handler = new MessageReceiver.MessageHandler(getApplicationContext());
	}
    /** Called when the activity is first created. */
	
	// add Activity  
    public void addActivity(Activity activity) { 
        mList.add(activity); 
    }
    
    public List<Activity> getActivityList() { 
        return mList; 
    }
    /**
     * 重新进入login以后，会重置cookie信息，所以这里不用再重置了
     */
    private void resetCookieStr()
	{
		Log.i(this.getClass().getSimpleName(),"PhoneApp.resetCookieStr()");
		SharedPreferences sp = getSharedPreferences("special", 0);
		Editor editor = sp.edit();
		editor.putString("cookieStr", "");
		editor.commit();
	}
    private void setCurrentUserStr(String currentUser)
	{
		SharedPreferences sp = getSharedPreferences("special", 0);
		Editor editor = sp.edit();
		if(currentUser==null)
		{
			editor.remove("currentUser");
		}else
		{
			editor.putString("currentUser", currentUser);
		}
		editor.commit();
	}


    public static JSONObject getCurrentUser(Context ctx)
    {
        SharedPreferences sp = ctx.getSharedPreferences("special", 0);
        JSONObject obj=null;
        try{
            obj=JSONObject.fromObject(sp.getString("currentUser", "{}"));
        }catch(Exception e)
        {
            //e.printStackTrace();
        }
        return obj;
    }
    public void backLogin()
    {
    	Log.i(this.getClass().getSimpleName(),"PhoneApp.backLogin()");
    	for (Activity activity : mList) { 
            if (activity != null) 
                activity.finish(); 
        }
    	setCurrentUserStr(null);
        HttpUtil.setCookieStr(this, null);
    	startActivity(new Intent(this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    //SysApplication.getInstance().addActivity(this); 
    public void exit() { 
    	Log.i(this.getClass().getSimpleName(), "PhoneApp.exit()");
        try { 
            for (Activity activity : mList) { 
                if (activity != null) 
                    activity.finish(); 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally {
        	setCurrentUserStr(null);
            HttpUtil.setCookieStr(this, null);
            instance = null;
        }
    }
    
    public void onLowMemory() { 
    	Log.i(this.getClass().getSimpleName(),"PhoneApp.onLowMemory()");
        super.onLowMemory();
        System.gc(); 
    }
    
	@Override
	public void onTerminate()
	{
		super.onTerminate();
		Log.i(this.getClass().getSimpleName(),"PhoneApp.onTerminate()");
	}

    public static MessageReceiver.MessageHandler getHandler() {
        return handler;
    }
}