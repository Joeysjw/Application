package com.xgr.wonderful.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.sax.StartElementListener;
import android.util.Log;

import com.cumtLife.Const.MyConst;

public class OpenCamerAndAlbumHelper {
	
	public static boolean existSDcard(){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
		
	}
	
	public static void getAvataFromAlbum(Activity mActivity, ArrayList<String> dataList){
		Intent intent=new Intent(mActivity, com.xgr.wonderful.ui.AlbumActivity.class);
		Bundle bundle=new Bundle();
		intent.putStringArrayListExtra("datalist", getIntentArrayList(dataList));
		intent.putExtras(bundle);
		mActivity.startActivityForResult(intent, MyConst.GET_PICS);
	}
	
	public static void OpenCamTakePic(Activity mActivity){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		ContentValues values = new ContentValues();
		Uri picUri = mActivity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, picUri);
		mActivity.startActivityForResult(intent, MyConst.TAKE_PIC);
		
		
		
		}
	
	public static void getAvataFromCamera(String dateTime,Activity mActivity){		

		
		File f = new File("/sdcard/DCIM/Camera/"+dateTime+".jpg");
		if (f.exists()) {
			f.delete();
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Uri uri = Uri.fromFile(f);
		Log.e("uri", uri + "");
		
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		mActivity.startActivityForResult(camera, MyConst.TAKE_PIC);
		
	}
	
	
	private static ArrayList<String> getIntentArrayList(ArrayList<String> dataList) {
		ArrayList<String> tDataList = new ArrayList<String>();
		for (String s : dataList) {			
			if (!s.contains("default")) {
				tDataList.add(s);
			}
		}
		return tDataList;
	}

}
