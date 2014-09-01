package com.special.BuidingSite;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class PatrolApp extends Application {

	private List<Activity> mList = new LinkedList<Activity>(); 
    private static PatrolApp instance; 
    
    public static PatrolApp getInstance()
    {
    	return instance;
    }
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(this.getClass().getSimpleName(),"PatrolApp.onCreate()");
		instance = this;
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
    /*private void resetCookieStr()
	{
		Log.i(this.getClass().getSimpleName(),"PatrolApp.resetCookieStr()");
		SharedPreferences sp = getSharedPreferences("cfrt", 0);
		Editor editor = sp.edit();
		editor.putString("cookieStr", "");
		editor.commit();
	}*/
    private void setCurrentUserStr(String currentUser)
	{
		SharedPreferences sp = getSharedPreferences("cfrt", 0);
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
    
    public void backLogin()
    {
    	Log.i(this.getClass().getSimpleName(),"PatrolApp.backLogin()");
    	for (Activity activity : mList) { 
            if (activity != null) 
                activity.finish(); 
        }
    	setCurrentUserStr(null);
    	startActivity(new Intent(this,Login.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    //SysApplication.getInstance().addActivity(this); 
    public void exit() { 
    	Log.i(this.getClass().getSimpleName(),"PatrolApp.exit()");
        try { 
            for (Activity activity : mList) { 
                if (activity != null) 
                    activity.finish(); 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally {
        	setCurrentUserStr(null);
        } 
    }
    
    public void onLowMemory() { 
    	Log.i(this.getClass().getSimpleName(),"PatrolApp.onLowMemory()");
        super.onLowMemory();
        System.gc(); 
    }
    
	@Override
	public void onTerminate()
	{
		super.onTerminate();
		Log.i(this.getClass().getSimpleName(),"PatrolApp.onTerminate()");
	}
}