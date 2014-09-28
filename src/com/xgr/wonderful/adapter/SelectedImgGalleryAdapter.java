package com.xgr.wonderful.adapter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

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

public class SelectedImgGalleryAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<String> selectedDataList;
	private int width;
	private int height;

	
	

	public SelectedImgGalleryAdapter(Context mContext,
			ArrayList<String> selectedDataList) {
		super();
		this.mContext = mContext;
		this.selectedDataList = selectedDataList;
	
	}

	public SelectedImgGalleryAdapter(Context mContext,
			ArrayList<String> selectedDataList, int width, int height) {
		super();
		this.mContext = mContext;
		this.selectedDataList = selectedDataList;
		this.width = width;
		this.height = height;
	}

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
					R.layout.view_img, parent, false);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.iv_seletected_img);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		String path = selectedDataList.get(position);
//		WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
//		int windowWidth = wm.getDefaultDisplay().getHeight();
//		int windowHeight = wm.getDefaultDisplay().getHeight();
		
		try {
			Bitmap bitmap = BitmapUtil.getBitmapByPath(mContext ,path, BitmapUtil.getOptions(path));
			viewHolder.imageView.setImageBitmap(bitmap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		
		return convertView;
	}
	
	

}
