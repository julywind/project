package com.special.ResideMenuDemo.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.special.ResideMenuDemo.R;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class BaseFragment extends Fragment {
	protected Context instance = null;
	
	public static JSONObject getCurrentUser(Context ctx)
	{
		SharedPreferences sp = ctx.getSharedPreferences("cfrt", 0);
		JSONObject obj=null;
		try{
			obj=JSONObject.fromObject(sp.getString("currentUser", "{}"));
		}catch(Exception e)
		{
			//e.printStackTrace();
		}
		return obj;
	}

	/*
     * 判断网络连接是否已开
     * 2012-08-20
     *true 已打开  false 未打开
     * */
    public static boolean isNetworkAvalidate(Context context){
        boolean bisConnFlag=false;
        ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if(network!=null){
            bisConnFlag=conManager.getActiveNetworkInfo().isAvailable();
        }
        return bisConnFlag;
    }

	//删除通知
	protected void clearNotification(){
        // 启动后删除之前我们定义的通知   
        NotificationManager notificationManager = (NotificationManager) getActivity()
                .getSystemService(Activity.NOTIFICATION_SERVICE);
        notificationManager.cancel(100);
    }
    
	protected Handler mHandler = new Handler() {
		public void handleMessage(Message message) {
			Bundle b = message.getData();
            if(b.containsKey("content")) {
                Toast.makeText(instance, b.getString("content"), Toast.LENGTH_SHORT)
                        .show();
            }
		}
	};
	
	public static String getCfrtPath(String filename)
	{
		String fileName = sanitizePath(filename);
		File file = new File(fileName);
		File path = file.getParentFile();
		path.mkdirs();// 创建文件夹
		return fileName;
	}
	
	private static String sanitizePath(String path)
	{
		if (!path.startsWith("/"))
		{
			path = "/" + path;
		}
		
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/cfrt/patrol" + path;
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        instance = inflater.getContext();
        Intent intent = getActivity().getIntent();
        if(intent.hasExtra("fromNotify")&&intent.getBooleanExtra("fromNotify",false))
        {
            clearNotification();
        }
        //PatrolApp.getInstance().addActivity(this);

        /*if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            //当前不可用
            Toast.makeText(getActivity(), "系统检测到您尚未安装存储卡，部分功能可能无法正常运行，请尽快安装", Toast.LENGTH_LONG).show();
        }*/
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void onCreate() {
		// TODO Auto-generated method stub
		instance = getActivity();
		Intent intent = getActivity().getIntent();
		if(intent.hasExtra("fromNotify")&&intent.getBooleanExtra("fromNotify",false))
		{
			clearNotification();
		}
		
		//PatrolApp.getInstance().addActivity(this);
		
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			//当前不可用
			Toast.makeText(getActivity(), "系统检测到您尚未安装存储卡，部分功能可能无法正常运行，请尽快安装", Toast.LENGTH_LONG).show();
		}

	}
	public void sendMessage(String str)
 	{
 		Message msg = new Message();
 		Bundle b = new Bundle();
 		b.putString("content", str);
		msg.setData(b);
		mHandler.sendMessage(msg);
 	}
	
	protected void sendMessage(int strId)
 	{
		sendMessage(instance.getString(strId));
 	}
	
	public static Bitmap getThundbPic(String imageFilePath,int width,int height)
	{
		BitmapFactory.Options op = new BitmapFactory.Options();
		// op.inSampleSize = 8;
		op.inJustDecodeBounds = true;
		op.inSampleSize=1;
		// 获取屏幕的宽和高
		int dw = width;
		int dh = height;
		
		Bitmap bigPic = BitmapFactory.decodeFile(imageFilePath,op);
		
		System.out.println("[baseChat]bigPic0:"+bigPic);
		int wRatio = (int) Math.ceil(op.outWidth / (float) dw); // 计算宽度比例
		int hRatio = (int) Math.ceil(op.outHeight / (float) dh); // 计算高度比例
		Log.v("Width Ratio:", wRatio + "");
		Log.v("Height Ratio:", hRatio + "");
		/**
		 * 接下来，我们就需要判断是否需要缩放以及到底对宽还是高进行缩放。 如果高和宽不是全都超出了屏幕，那么无需缩放。
		 * 如果高和宽都超出了屏幕大小，则如何选择缩放呢》 这需要判断wRatio和hRatio的大小
		 * 大的一个将被缩放，因为缩放大的时，小的应该自动进行同比率缩放。 缩放使用的还是inSampleSize变量
		 */
		if (wRatio > 1 && hRatio > 1) {
			if (wRatio > hRatio) {
				op.inSampleSize = wRatio;
			} else {
				op.inSampleSize = hRatio;
			}
		}
		op.inJustDecodeBounds = false;
		// 注意这里，一定要设置为false，因为上面我们将其设置为true来获取图片尺寸了
		Bitmap smallPic = BitmapFactory.decodeFile(imageFilePath,op);
		return smallPic;
	}
	
	
	public Bitmap getThundbPic(String imageFilePath)
	{
		// 首先取得屏幕对象
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		// 获取屏幕的宽和高
		int dw = display.getWidth();
		int dh = display.getHeight();
		
		return getThundbPic(imageFilePath,dw,dh);
	}
	/**
	 * 判断服务是否运行
	 * 
	 * @return
	 */
	protected boolean isServiceRunning(String serviceName) {
		ActivityManager myManager = (ActivityManager) getActivity().getApplicationContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
				.getRunningServices(100);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).service.getClassName().toString()
					.equals(serviceName)) {
				return true;
			}
		}
		return false;
	}
	
    private Dialog waitingDialog;
    //show waiting view
    protected void showWaitingDialog(Context mContext){
        final Dialog dialog = new Dialog(mContext, R.style.guideDialog);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.loading, null);
        waitingDialog = dialog;
        dialog.setContentView(view);
        dialog.show();
    }

    protected void closeWaitingDialog(){
        if(waitingDialog!=null&&waitingDialog.isShowing())
        {
            waitingDialog.dismiss();
            waitingDialog = null;
        }
    }
}
