package com.special.BuidingSite.ui.exit;


import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.special.BuidingSite.R;
import com.special.BuidingSite.base.BaseActivity;
import com.special.BuidingSite.ui.PhoneApp;

public class Exit extends BaseActivity {
	private LinearLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit_dialog);
		//dialog=new MyDialog(this);
		layout=(LinearLayout)findViewById(R.id.exit_layout);
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
						Toast.LENGTH_SHORT).show();
			}
		});
		//((TextView)findViewById(R.id.exit_tip)).setText(R.string.exit_tip_end_patrol);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	
	public void exitbutton1(View v) {
    	finish();    	
      }  
	public void exitbutton0(View v) {  
    	
    	Intent oldInt = getIntent();
    	Boolean isLogout = oldInt.getBooleanExtra("logout",false);
    	if(isLogout)
    	{
    		PhoneApp.getInstance().backLogin();
    	}else
    	{
            PhoneApp.getInstance().exit();
    	}
      }  
}
