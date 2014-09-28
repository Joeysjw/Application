package com.xgr.wonderful.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

/**
 * 打开相机或相册，获取图片帮助类
 * @author jiawei.shi
 *
 */

public class GetPicUtil {
	
	private Fragment fragment;
	private Activity activity;
	
	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public GetPicUtil(Fragment fragment) {
		super();
		this.fragment = fragment;
	}
	
	public void getAvataFromAlbumInFragmnet(){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		this.fragment.startActivityForResult(intent, 2);
	}
	
	public void getAvataFromAlbumInActivity(){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		this.fragment.startActivityForResult(intent, 2);
	}


	

}
