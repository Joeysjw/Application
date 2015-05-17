package com.bmob.lostfound;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.bmob.v3.update.BmobUpdateAgent;

import com.cumtLife.Const.MyConst;
import com.cumtLife.bean.Lost;
import com.xgr.wonderful.R;
import com.xgr.wonderful.entity.PicForLost;
import com.xgr.wonderful.entity.User;
import com.xgr.wonderful.ui.PhotoViewerActivity;
import com.xgr.wonderful.ui.SelectedPicsViewerActivity;
import com.xgr.wonderful.utils.BitmapUtil;
import com.xgr.wonderful.utils.OpenCamerAndAlbumHelper;
import com.xgr.wonderful.utils.ShowAlbumDialog;
import com.xgr.wonderful.utils.ToastFactory;


/**
 * 
 * @ClassName: AddLostThingsActivity
 * @Description: TODO
 * @author jiawei.shi
 * @date 2014-8-18 
 */
public class AddFindLostActivity extends BaseActivity implements OnClickListener ,OnItemClickListener{

	private EditText edit_title, edit_phone, edit_describe;
	private Button btn_back, btn_true;
	
	private LinearLayout btn_camer;
	private LinearLayout btn_openpic;
	
	private GridView addPic;
	private com.xgr.wonderful.adapter.GridImageAdapter gridImageAdapter;
	private ArrayList<String> dataList = new ArrayList<String>();
	private ArrayList<String> selectedPicList = new ArrayList<String>();

	private String dateTime;

	private String from = "";
	
	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.find_lost_add_activity);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_true = (Button) findViewById(R.id.btn_true);
		edit_phone = (EditText) findViewById(R.id.et_phone);
		edit_describe = (EditText) findViewById(R.id.edit_describe);
		edit_title = (EditText) findViewById(R.id.edit_title);
		btn_camer = (LinearLayout) findViewById(R.id.take_layout);
		btn_openpic = (LinearLayout) findViewById(R.id.open_layout);
		
		
		addPic = (GridView) findViewById(R.id.addPic);
//		dataList.add("camera_default");
		gridImageAdapter=new com.xgr.wonderful.adapter.GridImageAdapter(this, dataList);
		addPic.setAdapter(gridImageAdapter);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(this);
		btn_true.setOnClickListener(this);
		btn_camer.setOnClickListener(this);
		btn_openpic.setOnClickListener(this);
		
		addPic.setOnItemClickListener(this);
		
	}

	@Override
	public void initData() {
//		int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
//		Log.w("maxMemoey is ....", ""+maxMemory); 
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		dataList.clear();
		selectedPicList = intent.getStringArrayListExtra(MyConst.CONFIRM_PATH);
		selectedPicList.add("camera_default");
    	dataList.addAll(selectedPicList);	        	
    	gridImageAdapter.notifyDataSetChanged();
    	super.onNewIntent(intent);
	}
	


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
			
		case R.id.btn_true:
			compressedPicList.clear();
			addByType();
			break;
			
		case R.id.take_layout:
			if(selectedPicList.size()<=8){
				if(OpenCamerAndAlbumHelper.existSDcard()){
					OpenCamerAndAlbumHelper.OpenCamTakePic(this);
				}else{
					ToastFactory.getToast(this, "内存卡不存在！").show();
				}
			}else{
				ToastFactory.getToast(this, "一次只能上传8张图片哦~").show();
			}		
			break;
			
		case R.id.open_layout:
			OpenCamerAndAlbumHelper.getAvataFromAlbum(this, dataList);			
			break;

		}	

	}
	
	
	String title = "";
	String describe = "";
	String phone="";
	
	/**
	  * addByType
	  * @Title: addByType
	  * @throws
	  */
	private void addByType(){
		title = edit_title.getText().toString();
		describe = edit_describe.getText().toString();
		phone = edit_phone.getText().toString();
		
		if(TextUtils.isEmpty(title)){
			ShowToast("标题不能为空");
			return;
		}
		if(TextUtils.isEmpty(describe)){
			ShowToast("描述不能为空");
			return;
		}
		if(TextUtils.isEmpty(phone) ){
			ShowToast("手机号不能为空");
			return;
		}
		if(!TextUtils.isDigitsOnly(phone)){
			ShowToast("手机号输入错误");
			return;
		}
//		if(from.equals("Lost")){
//			addLost();
//		}
		
		if(dataList.size()>0){
			publishLostWithPic(title, phone, describe,dataList);
		}else{
			publishLost(title, phone, describe,null);
		}
	}

	private void addLost(){
		Lost lost = new Lost();
		lost.setDescribe(describe);
		lost.setPhone(phone);
		lost.setTitle(title);
		lost.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ShowToast("发送成功!");
				setResult(RESULT_OK);
				finish();
			}
			
			@Override
			public void onFailure(int code, String arg0) {
				// TODO Auto-generated method stub
				ShowToast("发送失败！:"+arg0);
			}
		});
	}
	
	int picNum ;
	private PicForLost picForLost;
	private Lost lost;
	
	
	private void publishLostWithPic(String title, String phone, String describe,ArrayList<String> picList){
		//获取原图压缩，存入临时目录，返回临时目录中图片的路径
		picNum=picList.size()-1;
		ArrayList<String> compressedPicPathList = new ArrayList<String>();
		for(String p : picList){
			if(!p.contains("camera_default")){
				 Bitmap bitmap = null;
				try {
					bitmap = BitmapUtil.getBitmapByPath(this,p, BitmapUtil.getOptions(p));
				} catch (Exception e) {
					e.printStackTrace();
				}
				 String picUrl = BitmapUtil.saveToSdCard(this, bitmap);
				 picForLost = upLoadFile(new File(picUrl));
				 

			}
			
		 }
		
		
	}
	
	private void publishLost(String title, String phone, String describe, PicForLost picForLost){
		User user =  BmobUser.getCurrentUser(this, User.class);
		Lost lost = new Lost();
		lost.setAuthor(user);
		lost.setTitle(title);
		lost.setPhone(phone);
		lost.setDescribe(describe);
		lost.setLove(0);	
		lost.setShare(0);
		lost.setComment(0);
		lost.setDate(getTime());
		if(picForLost != null){
			lost.setPicForLost(picForLost);
		}
		lost.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ToastFactory.getToast(AddFindLostActivity.this, "发布成功^_^").show();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ToastFactory.getToast(AddFindLostActivity.this, "发布失败，请稍后再试-.-").show();
			}
		});
	}
	
	private String getTime(){
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return date.format(new Date());
	}
	
	ArrayList<BmobFile> compressedPicList = new ArrayList<BmobFile>();
	
	private PicForLost upLoadFile(File file){
		final BmobFile bmobFile = new BmobFile(file);
		bmobFile.upload(this, new UploadFileListener() {
			
			@Override
			public void onSuccess() {
				compressedPicList.add(bmobFile);
//				Log.w("compressedPicList.size()", ""+compressedPicList.size());
//				Log.w("picNum", ""+picNum);
				if(picNum == compressedPicList.size() && compressedPicList.size()==1){
					
					picForLost = new PicForLost(compressedPicList.get(0));
//					picForLost.setUser(BmobUser.getCurrentUser(AddFindLostActivity.this, User.class));
					insertObject(picForLost);
				}
				if(picNum == compressedPicList.size() && compressedPicList.size()==2){
				    picForLost = new PicForLost(compressedPicList.get(0), compressedPicList.get(1));
//					picForLost.setUser(BmobUser.getCurrentUser(AddFindLostActivity.this, User.class));
					insertObject(picForLost);
				}
				if(picNum == compressedPicList.size() && compressedPicList.size()==3){
				    picForLost = new PicForLost(compressedPicList.get(0), compressedPicList.get(1), compressedPicList.get(2));
//					picForLost.setUser(BmobUser.getCurrentUser(AddFindLostActivity.this, User.class));
					insertObject(picForLost);
				}
				if(picNum == compressedPicList.size() && compressedPicList.size()==4){
					picForLost = new PicForLost(compressedPicList.get(0), compressedPicList.get(1), compressedPicList.get(2), compressedPicList.get(3));
//					picForLost.setUser(BmobUser.getCurrentUser(AddFindLostActivity.this, User.class));
					insertObject(picForLost);
				}
				if(picNum == compressedPicList.size() && compressedPicList.size()==5){
					picForLost = new PicForLost(compressedPicList.get(0), compressedPicList.get(1), compressedPicList.get(2), compressedPicList.get(3), compressedPicList.get(4));
//					picForLost.setUser(BmobUser.getCurrentUser(AddFindLostActivity.this, User.class));
					insertObject(picForLost);
				}
				if(picNum == compressedPicList.size() && compressedPicList.size()==6){
					picForLost = new PicForLost(compressedPicList.get(0), compressedPicList.get(1), compressedPicList.get(2), compressedPicList.get(3), compressedPicList.get(4), compressedPicList.get(5));
//					picForLost.setUser(BmobUser.getCurrentUser(AddFindLostActivity.this, User.class));
					insertObject(picForLost);
				}
				if(picNum == compressedPicList.size() && compressedPicList.size()==7){
					picForLost = new PicForLost(compressedPicList.get(0), compressedPicList.get(1), compressedPicList.get(2), compressedPicList.get(3), compressedPicList.get(4), compressedPicList.get(5), compressedPicList.get(6));
//					picForLost.setUser(BmobUser.getCurrentUser(AddFindLostActivity.this, User.class));
					insertObject(picForLost);
				}
				if(picNum == compressedPicList.size() && compressedPicList.size()==8){
					picForLost = new PicForLost(compressedPicList.get(0), compressedPicList.get(1), compressedPicList.get(2), compressedPicList.get(3), compressedPicList.get(4), compressedPicList.get(5), compressedPicList.get(6), compressedPicList.get(7));
//					picForLost.setUser(BmobUser.getCurrentUser(AddFindLostActivity.this, User.class));
					insertObject(picForLost);
				}
				
			}
			
			@Override
			public void onProgress(Integer arg0) {

			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		return picForLost;
	}
	
	private void insertObject(BmobObject obj){
		obj.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
//				ToastFactory.getToast(AddFindLostActivity.this, "图片上传成功^_^").show();
				if(picForLost != null){
					publishLost(title, phone, describe,picForLost);
				}
				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
//				ToastFactory.getToast(AddFindLostActivity.this, "图片上传失败，请稍后再试-.-"+arg1).show();
			}
		});
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			switch (requestCode) {
			case MyConst.TAKE_PIC:	
				if(data == null){return;}
//				String pathTemp = "/sdcard/DCIM/Camera/"+dateTime+".jpg";
				String path = null;
				Uri picUri = data.getData();
				String[] pojo = {MediaStore.Images.Media.DATA};
				Cursor cursor = managedQuery(picUri, pojo, null, null, null);
				if(cursor != null){
					int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
					cursor.moveToFirst();
					path = cursor.getString(columnIndex);
					if(VERSION.SDK_INT<14){
						cursor.close();
					}
				}
				
				Intent intent = new Intent(this, PhotoViewerActivity.class);
				intent.putExtra(MyConst.PIC_PATH_FOR_PHOTO_VIEWR, path);
				intent.putStringArrayListExtra(MyConst.SELECTED_PICS_PATH, selectedPicList);
				startActivity(intent);		
				break;
				
			case MyConst.GET_PICS:	
				dataList.clear();
	        	selectedPicList = data.getStringArrayListExtra(MyConst.SELECTED_PICS_PATH);
	        	selectedPicList.add("camera_default");
	        	dataList.addAll(selectedPicList);	        	
	        	gridImageAdapter.notifyDataSetChanged();
	        	
			default:
				break;
			}
			
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(selectedPicList != null && position == selectedPicList.size()-1){
			if(selectedPicList.size()<=8){
				startAlbumActivity();
			}else{
				ToastFactory.getToast(this, "一次只能上传8张图片哦~").show();
			}	
			
		}else if( dataList.get(0)=="camera_default"){
			startAlbumActivity();
		}else{
			Intent intent = new Intent(this,SelectedPicsViewerActivity.class);
			intent.putExtra(MyConst.POSITION, position);
			intent.putStringArrayListExtra(MyConst.SELECTED_PICS_PATH, selectedPicList);			
			startActivityForResult(intent, MyConst.GET_PICS);
	
		}

	}
	
	
	private void startAlbumActivity(){	
		if(OpenCamerAndAlbumHelper.existSDcard()){
			Date date = new Date(System.currentTimeMillis());
			dateTime = date.getTime() + "";
			ShowAlbumDialog showAlbumDialog = new ShowAlbumDialog(this, this, dateTime, dataList);
			showAlbumDialog.showAlbumOrCamera();
		}else{
			ToastFactory.getToast(this, "内存卡不存在！").show();
		}

	}
	


}
