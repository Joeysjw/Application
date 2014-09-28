package com.bmob.lostfound;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.Toast;
import cn.bmob.v3.Bmob;



/**
 * @ClassName: BaseActivity
 * @Description: TODO
 * @author jiawei.shi
 * @date 2014-8-18
 */
public abstract class BaseActivity extends Activity {

	protected int mScreenWidth;
	protected int mScreenHeight;
	
	public static final String TAG = "bmob";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//获取当前屏幕高度
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
		
		setContentView();
		initViews();
		initListeners();
		initData();
	}
	/**
	 * 设置布局UI
	 */
	public abstract void setContentView();

	/**
	 * 初始化布局
	 */
	public abstract void initViews();

	/**
	 * 设置监听器
	 */
	public abstract void initListeners();
	
	/** 初始化布局文件中的控件
	  * initData
	  */
	public abstract void initData();
	Toast mToast;

	public void ShowToast(String text) {
		if (!TextUtils.isEmpty(text)) {
			if (mToast == null) {
				mToast = Toast.makeText(getApplicationContext(), text,
						Toast.LENGTH_SHORT);
			} else {
				mToast.setText(text);
			}
			mToast.show();
		}
	}
	
	/** 获取当前状态的高度
	  * getStateBar
	  * @Title: getStateBar
	  * @throws
	  */
	public  int getStateBar(){
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		return statusBarHeight;
	}
	
	public static int dip2px(Context context,float dipValue){
		float scale=context.getResources().getDisplayMetrics().density;		
		return (int) (scale*dipValue+0.5f);		
	}
	
}
