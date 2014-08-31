package com.special.ResideMenuDemo.common;



import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.special.ResideMenuDemo.R;
import com.special.ResideMenuDemo.base.BaseActivity;

public class PictureShower extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.info_picutre);
		
		Intent intent = getIntent();
		String filename = intent.getStringExtra("filename");
		
		if(filename==null)
		{
			Integer personId = intent.getIntExtra("personId", -2);
			if(personId!=-2)
			{
				filename = getCfrtPath("/head/"+personId+".jpg");
			}
		}
		File file = new File(filename);
		if(!file.exists() || !file.isFile())
		{
			sendMessage("文件"+filename+"不存在");
			finish();
		}
		ImageView image = (ImageView) findViewById(R.id.image_view);

		Bitmap bm = getThundbPic(filename);
		image.setImageBitmap(bm);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);   //全屏显示
		//Toast.makeText(getApplicationContext(), "孩子！好好背诵！", Toast.LENGTH_LONG).show();
		//overridePendingTransition(R.anim.hyperspace_in, R.anim.hyperspace_out);
   }
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
}