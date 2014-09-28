package com.xgr.wonderful.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cumtLife.Const.MyConst;
import com.xgr.wonderful.R;

public class ShowAlbumDialog {
	
	private Activity mActivity;
	private Context mContext;
	private String dateTime;
	private ArrayList<String> dataList = new ArrayList<String>();
	

	public ShowAlbumDialog() {
		super();
	}



	public ShowAlbumDialog(Activity mActivity, Context mContext,
			String dateTime, ArrayList<String> dataList) {
		super();
		this.mActivity = mActivity;
		this.mContext = mContext;
		this.dateTime = dateTime;
		this.dataList = dataList;
	}



	public void showAlbumOrCamera(){
		final AlertDialog albumDialog = new AlertDialog.Builder(mContext).create();
		albumDialog.setCanceledOnTouchOutside(true);
		View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_usericon, null);
		albumDialog.show();
		albumDialog.setContentView(v);
		albumDialog.getWindow().setGravity(Gravity.CENTER);
		
		
		TextView albumPic = (TextView)v.findViewById(R.id.album_pic);
		TextView cameraPic = (TextView)v.findViewById(R.id.camera_pic);
		albumPic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				albumDialog.dismiss();				
//				getAvataFromAlbum();
				OpenCamerAndAlbumHelper.getAvataFromAlbum(mActivity, dataList);
			}
		});
		cameraPic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				albumDialog.dismiss();
				
				OpenCamerAndAlbumHelper.OpenCamTakePic(mActivity);
			}
		});
	}
	
//	private void getAvataFromAlbum(){
//		Intent intent=new Intent(mActivity, com.xgr.wonderful.ui.AlbumActivity.class);
//		Bundle bundle=new Bundle();
//		intent.putStringArrayListExtra("datalist", getIntentArrayList(dataList));
//		intent.putExtras(bundle);
//		mActivity.startActivityForResult(intent, MyConst.GET_PICS);
//	}
	

//	
//	private void getAvataFromCamera(){		
////		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////		
////		mActivity.startActivityForResult(camera, MyConst.TAKE_PIC);
//		
//		File f = new File("/sdcard/DCIM/Camera/"+dateTime+".jpg");
//		if (f.exists()) {
//			f.delete();
//		}
//		try {
//			f.createNewFile();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		Uri uri = Uri.fromFile(f);
//		Log.e("uri", uri + "");
//		
//		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//		mActivity.startActivityForResult(camera, MyConst.TAKE_PIC);
//		
//	}
//	
//	private ArrayList<String> getIntentArrayList(ArrayList<String> dataList) {
//		ArrayList<String> tDataList = new ArrayList<String>();
//		for (String s : dataList) {			
//			if (!s.contains("default")) {
//				tDataList.add(s);
//			}
//		}
//		return tDataList;
//	}


}
