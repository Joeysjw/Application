package com.xgr.wonderful.ui;


import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;

import com.bmob.lostfound.BaseActivity;
import com.cumtLife.Const.MyConst;

import com.xgr.wonderful.R;
import com.xgr.wonderful.adapter.CheckImageGalleryAdapter;

public class CheckImageActivity extends BaseActivity {
	
	private Gallery viewImage;
	private ArrayList<String> picUriList = new ArrayList<String>();


	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.check_image);
	}



	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		viewImage = (Gallery) findViewById(R.id.gallery_viewImage);
	}



	@Override
	public void initListeners() {
//		// TODO Auto-generated method stub
		viewImage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				finish();
				
			}
		});
	}



	@Override
	public void initData() {
		// TODO Auto-generated method stub
		picUriList = getIntent().getStringArrayListExtra(MyConst.PIC_PATH_ON_NETWORK);
		int position = getIntent().getIntExtra(MyConst.POSITION, 0);
		viewImage.setAdapter(new CheckImageGalleryAdapter(this, picUriList));
		viewImage.setSelection(position);
	}

	

}
