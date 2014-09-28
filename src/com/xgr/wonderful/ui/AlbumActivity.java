package com.xgr.wonderful.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.cumtLife.Const.MyConst;
import com.xgr.wonderful.adapter.AlbumGridViewAdapter;
import com.xgr.wonderful.utils.ImageManager2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.xgr.wonderful.R;


public class AlbumActivity extends Activity implements OnClickListener{
	
	private GridView gridView;
	private TextView tv_add;
	private Button btn_back, btn_true, btn_preview;
	
	
	private ArrayList<String> dataList = new ArrayList<String>();
	private ArrayList<String> selectedDataList ;
	private String cameraDir = "/DCIM/";//相片默认的存放路径
	private ProgressBar progressBar;
	private AlbumGridViewAdapter gridImageAdapter;
//	private LinearLayout selectedImageLayout;
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album);
		init();
		
		initListener();
		
	}

	private void init() {
		
		selectedDataList = getIntent().getStringArrayListExtra("datalist");	
		progressBar = (ProgressBar)findViewById(R.id.progressbar);
		progressBar.setVisibility(View.GONE);
		gridView = (GridView)findViewById(R.id.myGrid);
		tv_add = (TextView) findViewById(R.id.tv_add);
		tv_add.setText(R.string.pick_pics);
		
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_true = (Button) findViewById(R.id.btn_true);
				
		
		gridImageAdapter = new AlbumGridViewAdapter(this, dataList,selectedDataList);
		gridView.setAdapter(gridImageAdapter);
		refreshData();

		btn_preview = (Button)findViewById(R.id.btn_preview);
		btn_preview.setText("预览("+selectedDataList.size()+"/8)");
				
	}



	private void initListener() {
		btn_back.setOnClickListener(this);
		btn_true.setOnClickListener(this);
		btn_preview.setOnClickListener(this);
		
		gridImageAdapter.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {			
			@Override
			public void onItemClick(final ToggleButton toggleButton, int position, final String path,boolean isChecked) {
					
				if(selectedDataList.size()>=8){
					toggleButton.setChecked(false);
					if(!removePath(path)){
						Toast.makeText(AlbumActivity.this, "一次只能选择8张图片", Toast.LENGTH_SHORT).show();
					}
					return;
				}
				
				if(isChecked){						
						selectedDataList.add(path);
						btn_preview.setText("预览("+selectedDataList.size()+"/8)");

				}else{
					removePath(path);
			
				}

			}
		});
		
	}
	
	private boolean removePath(String path){
		if(selectedDataList.contains(path)){
			Log.w("selectedDataList is", ""+selectedDataList.size());
			removeOneData(selectedDataList,path);
			Log.w("selectedDataList is", ""+selectedDataList.size());
			btn_preview.setText("预览("+selectedDataList.size()+"/8)");
			return true;
		}else{
			return false;
		}
			
	}
	
	private void removeOneData(ArrayList<String> arrayList,String s){
		for(int i =0;i<arrayList.size();i++){
			if(arrayList.get(i).equals(s)){
				arrayList.remove(i);
				return;
			}
		}
	}
	
    private void refreshData(){    	
    	new AsyncTask<Void, Void, ArrayList<String>>(){
    		
    		@Override
    		protected void onPreExecute() {
    			progressBar.setVisibility(View.VISIBLE);
    			super.onPreExecute();
    		}

			@Override
			protected ArrayList<String> doInBackground(Void... params) {
				ArrayList<String> tmpList = new ArrayList<String>();
				ArrayList<String> listDirlocal =  listAlldir( new File(cameraDir));
                ArrayList<String> listDiranjuke = new ArrayList<String>();
                listDiranjuke.addAll(listDirlocal);
                
                for (int i = 0; i < listDiranjuke.size(); i++){
                    listAllfile( new File( listDiranjuke.get(i) ),tmpList);
                }
				return tmpList;
			}
			
			protected void onPostExecute(ArrayList<String> tmpList) {
				
				if(AlbumActivity.this==null||AlbumActivity.this.isFinishing()){
					return;
				}
				progressBar.setVisibility(View.GONE);
				dataList.clear();
				dataList.addAll(tmpList);
				gridImageAdapter.notifyDataSetChanged();
				return;
				
			};
    		
    	}.execute();
    	
    }
    
    private ArrayList<String>  listAlldir(File nowDirtemp){
        ArrayList<String> listDir = new ArrayList<String>();
        
        File nowDir = new File(Environment.getExternalStorageDirectory() + nowDirtemp.getPath());
        if(!nowDir.isDirectory()){
            return listDir;
        }
                
        File[] files = nowDir.listFiles();

        for (int i = 0; i < files.length; i++){
            if(files[i].getName().substring(0,1).equals(".")){
               continue; 
            }
            File file = new File(files[i].getPath()); 
            if(file.isDirectory()){
                listDir.add(files[i].getPath());
            }
        }              
        return listDir;
    }
    
    private ArrayList<String>  listAllfile( File baseFile,ArrayList<String> tmpList){
        if(baseFile != null && baseFile.exists()){
            File[] file = baseFile.listFiles();
            for(File f : file){
                if(f.getPath().endsWith(".jpg") || f.getPath().endsWith(".png")){
                    tmpList.add(f.getPath());
                }
            }
        }         
        return tmpList;
    }
    
    @Override
    public void onBackPressed() {
    	finish();

    }
    
    @Override
    public void finish() {
    	super.finish();
//    	ImageManager2.from(AlbumActivity.this).recycle(dataList);
    }
    
    @Override
    protected void onDestroy() {
    	
    	super.onDestroy();
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_true:
			Intent intent = new Intent();
			intent.putStringArrayListExtra(MyConst.SELECTED_PICS_PATH, selectedDataList);
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.btn_preview:
			Intent intent2 = new Intent(this, SelectedPicsViewerActivity.class);
			intent2.putStringArrayListExtra(MyConst.SELECTED_PICS_PATH, selectedDataList);
			startActivity(intent2);
			break;
			
		default:
			break;
		}
		
	}
    

}
