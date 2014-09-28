package com.xgr.wonderful.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.bmob.lostfound.AddFindLostActivity;
import com.bmob.lostfound.BaseActivity;
import com.cumtLife.Const.MyConst;
import com.xgr.wonderful.R;
import com.xgr.wonderful.utils.BitmapUtil;

public class PhotoViewerActivity extends BaseActivity implements OnClickListener{
	private ImageView iv_show_img;
	private Button btn_back;
	private Button btn_ok;
	private ImageButton rubbish_bin;
	
	private String path;
	private ArrayList<String> selectedPicList;

	@Override
	public void setContentView() {
		setContentView(R.layout.photo_viewer_activity);
		
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		iv_show_img = (ImageView) findViewById(R.id.iv_show_img);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		rubbish_bin = (ImageButton) findViewById(R.id.rubbish_bin);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
		rubbish_bin.setOnClickListener(this);
	}

	@Override
	public void initData() {
		selectedPicList = getIntent().getStringArrayListExtra(MyConst.SELECTED_PICS_PATH);	
		selectedPicList.remove("camera_default");
		path = getIntent().getStringExtra(MyConst.PIC_PATH_FOR_PHOTO_VIEWR);
		Bitmap bitmap =  BitmapUtil.compressImageFromFile(this, path);
		iv_show_img.setImageBitmap(bitmap);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:			
			Intent intent2 = new Intent(this, AddFindLostActivity.class);
			intent2.putStringArrayListExtra(MyConst.CONFIRM_PATH, selectedPicList);
			selectedPicList.add(path);
			startActivity(intent2);
			finish();
			break;
		case R.id.btn_back:
			finish();
			break;
		case R.id.rubbish_bin:
			finish();
			break;
		default:
			break;
		}
		
	}

}
