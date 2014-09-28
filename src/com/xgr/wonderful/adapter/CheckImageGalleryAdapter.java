package com.xgr.wonderful.adapter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xgr.wonderful.R;
import com.xgr.wonderful.utils.BitmapUtil;
import com.xgr.wonderful.utils.ImageManager2;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class CheckImageGalleryAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<String> selectedDataList;


	
	

	public CheckImageGalleryAdapter(Context mContext,
			ArrayList<String> selectedDataList) {
		super();
		this.mContext = mContext;
		this.selectedDataList = selectedDataList;
	
	}

//	public CheckImageGalleryAdapter(Context mContext,
//			ArrayList<String> selectedDataList, int width, int height) {
//		super();
//		this.mContext = mContext;
//		this.selectedDataList = selectedDataList;
//	
//	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return selectedDataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return selectedDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private class ViewHolder {
		public ImageView imageView;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		 ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.check_image_view, parent, false);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.iv_viewImage);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		String path = selectedDataList.get(position);
		Log.w("path is ...", ""+path);
		
		ImageLoader.getInstance().displayImage(path, viewHolder.imageView);
		
		
//		try {
//			Bitmap bitmap = BitmapUtil.getBitmapByPath(mContext ,path, BitmapUtil.getOptions(path));
//			viewHolder.imageView.setImageBitmap(bitmap);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
	
		
		return convertView;
	}
	
	

}
