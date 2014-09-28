/**
 * Project Name: CumtLife
 * 
 * Date: 2014-9-5
 * @author joey
 *
 */
package com.xgr.wonderful.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.bmob.v3.datatype.BmobFile;

import com.cumtLife.Const.MyConst;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import com.xgr.wonderful.R;
import com.xgr.wonderful.ui.CheckImageActivity;


public class LostGridViewAdapter extends BaseAdapter{
	private ArrayList<String> picList;
	private Context context;
	
	
	
	public LostGridViewAdapter() {
		super();
	}

	public LostGridViewAdapter(Context context, ArrayList<String> picList){
		this.picList = picList;
		this.context = context;
	}

	@Override
	public int getCount() {
		Log.w("picList.size is ", ""+picList.size());
		return picList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return picList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private class ViewHolder{
		public ImageView imageView;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.lost_gridview_imageview, parent, false);
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.lost_selected_img_view);
			convertView.setTag(viewHolder);
		} else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		final String bmobFile = picList.get(position);
		viewHolder.imageView.setTag(bmobFile);
		viewHolder.imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, CheckImageActivity.class);				
				intent.putStringArrayListExtra(MyConst.PIC_PATH_ON_NETWORK, picList);
				intent.putExtra(MyConst.POSITION, position);
				context.startActivity(intent);
				
			}
		});
		DisplayImageOptions options = new DisplayImageOptions.Builder()  
         .showStubImage(R.drawable.bg_pic_loading)  
         .showImageForEmptyUri(R.drawable.bg_pic_loading)  
         .showImageOnFail(R.drawable.bg_pic_loading)
         .resetViewBeforeLoading(true)
         .cacheInMemory(true)  
         .cacheOnDisc(true)
         .imageScaleType(ImageScaleType.EXACTLY)
         .bitmapConfig(Bitmap.Config.RGB_565)     //设置图片的解码类型  
         .build(); 
		if(bmobFile.equals(viewHolder.imageView.getTag())){
			ImageLoader.getInstance().displayImage(bmobFile==null?"":bmobFile, viewHolder.imageView, options);
		}
		
		return 	convertView;
	}
	
	
//	private ArrayList<String> getUri(ArrayList<BmobFile> list){
//		ArrayList<String> picUriList = new ArrayList<String>();
//		for(BmobFile uri : list){
//			picUriList.add(uri.getFileUrl());	
//		}		
//		return picUriList;
//	}
	

}
