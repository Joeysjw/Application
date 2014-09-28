/**
 * Project Name: CumtLife
 * Package Name: com.xgr.wonderful.utils
 * File Name: UpLoadFile.java
 * 
 * Copyright 2014 Garea Microsys, Inc. All Rights Reserved.
 * 
 * Date: 2014-9-2
 * @author joey
 *
 */
package com.xgr.wonderful.utils;

import java.io.File;

import android.content.Context;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;

public class UpLoadFile {
	private static void upLoadFile(Context context, File file){
		BmobFile bmobFile = new BmobFile(file);
		bmobFile.upload(context, new UploadFileListener() {
			
			@Override
			public void onSuccess() {
				
				
			}
			
			@Override
			public void onProgress(Integer arg0) {
				
				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
