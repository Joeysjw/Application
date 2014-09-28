package com.xgr.wonderful.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;

import com.bmob.lostfound.AddFindLostActivity;
import com.bmob.lostfound.BaseActivity;
import com.cumtLife.Const.MyConst;
import com.xgr.wonderful.R;
import com.xgr.wonderful.adapter.SelectedImgGalleryAdapter;





public class SelectedPicsViewerActivity extends BaseActivity implements OnClickListener{
	
	private Button btn_back;
	private Button btn_ok;
	private ImageButton rubbish_bin;
	
    private SelectedImgGalleryAdapter selectedImgGalleryAdapter;
	
	private Gallery show_img;
	
	
	private ArrayList<String> selectedPicList;

	@Override
	public void setContentView() {
		setContentView(R.layout.selected_pics_viewer_activity);
		
	}

	@Override
	public void initViews() {
		show_img =   (Gallery) findViewById(R.id.show_img);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		rubbish_bin = (ImageButton) findViewById(R.id.rubbish_bin);
	}

	@Override
	public void initListeners() {
		btn_back.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
		rubbish_bin.setOnClickListener(this);
		
	}

	@Override
	public void initData() {
		int position = getIntent().getIntExtra(MyConst.POSITION, 0);
		selectedPicList = getIntent().getStringArrayListExtra(MyConst.SELECTED_PICS_PATH);	
		selectedPicList.remove("camera_default");
		
		int width =show_img.getWidth();
		int height = show_img.getHeight();
		selectedImgGalleryAdapter = new SelectedImgGalleryAdapter(this, selectedPicList,width,height);
		show_img.setAdapter(selectedImgGalleryAdapter);
		show_img.setSelection(position);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_ok:
			Intent intent = new Intent(this, AddFindLostActivity.class);
			intent.putStringArrayListExtra(MyConst.CONFIRM_PATH, selectedPicList);
			startActivity(intent);
			finish();
			
			break;
		case R.id.rubbish_bin:
			int position = show_img.getSelectedItemPosition();
			selectedPicList.remove(position);
			selectedImgGalleryAdapter.notifyDataSetChanged();
			if(selectedPicList.size()==0){
				Intent intent2 = new Intent(this, AddFindLostActivity.class);
				intent2.putStringArrayListExtra(MyConst.CONFIRM_PATH, selectedPicList);
				startActivity(intent2);
				finish();
			}
			break;
		default:
			break;
		}
	}
	
	

}
