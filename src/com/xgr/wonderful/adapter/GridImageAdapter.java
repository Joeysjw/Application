package com.xgr.wonderful.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.xgr.wonderful.R;
import com.xgr.wonderful.utils.ImageManager2;

public class GridImageAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<String> dataList;
	private DisplayMetrics dm;

	public GridImageAdapter(Context c, ArrayList<String> dataList) {

		mContext = c;
		this.dataList = dataList;
		dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);

	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	private class ViewHolder{
		public ImageView imageView;
	}

	@SuppressLint("NewApi") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.selected_imageview, parent, false);
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.selected_img_view);
			convertView.setTag(viewHolder);
			
//			imageView = new ImageView(mContext);
//			imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, dipToPx(65)));
//			imageView.setAdjustViewBounds(true);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY); 
		} else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		String path;
		if (dataList != null && position<dataList.size() ){
			path = dataList.get(position);
		}else{
			path = "camera_default";
		}			
		
		
		if (path.contains("default")){	
			viewHolder.imageView.setImageResource(R.drawable.camera_default);
		}else {		
            ImageManager2.from(mContext).displayImage(viewHolder.imageView, path,R.drawable.bg_pic_loading,80,80);
		}
		viewHolder.imageView.setTag(path);
		
		return convertView;
	}
	
//	public int dipToPx(int dip) {
//		return (int) (dip * dm.density + 0.5f);
//	}

}
