/**
 * Project Name: CumtLife
 * Package Name: com.xgr.wonderful.utils
 * File Name: GetPicUriUtil.java
 * 
 * Copyright 2014 Garea Microsys, Inc. All Rights Reserved.
 * 
 * Date: 2014-9-27
 * @author joey
 *
 */
package com.xgr.wonderful.utils;

import java.util.ArrayList;

import com.cumtLife.bean.Lost;

import android.content.Context;
import android.util.Log;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.GetListener;

public class GetLostPicUriUtil {

	public interface IGetPicListSuccess {
		public void getPicPath(ArrayList<String> list);
	}

	private BmobObject entity;
	private Context mConetxt;

	private IGetPicListSuccess iGetPicListSuccess;

	private ArrayList<BmobFile> picList = new ArrayList<BmobFile>();

	public GetLostPicUriUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetLostPicUriUtil(Context mContext, BmobObject entity) {
		super();
		this.mConetxt = mContext;
		this.entity = entity;
	}

	public void setIGetPicListSuccessListener(
			IGetPicListSuccess iGetPicListSuccess) {
		this.iGetPicListSuccess = iGetPicListSuccess;
	}

	public void getPicUriList() {
		BmobQuery<Lost> query = new BmobQuery<Lost>();
		query.include("picForLost");
		query.getObject(mConetxt, entity.getObjectId(),
				new GetListener<Lost>() {

					@Override
					public void onSuccess(Lost lost) {
						// TODO Auto-generated method stub
						if (lost == null) {
							return;
						} else {
							// picList = new ArrayList<BmobFile>();
							if (lost.getPicForLost() != null) {
								picList.clear();
								if (lost.getPicForLost().getPic1() != null) {
									picList.add(lost.getPicForLost().getPic1());
								}
								if (lost.getPicForLost().getPic2() != null) {
									picList.add(lost.getPicForLost().getPic2());
								}
								if (lost.getPicForLost().getPic3() != null) {
									picList.add(lost.getPicForLost().getPic3());
								}
								if (lost.getPicForLost().getPic4() != null) {
									picList.add(lost.getPicForLost().getPic4());
								}
								if (lost.getPicForLost().getPic5() != null) {
									picList.add(lost.getPicForLost().getPic5());
								}
								if (lost.getPicForLost().getPic6() != null) {
									picList.add(lost.getPicForLost().getPic6());
								}
								if (lost.getPicForLost().getPic6() != null) {
									picList.add(lost.getPicForLost().getPic7());
								}
								if (lost.getPicForLost().getPic6() != null) {
									picList.add(lost.getPicForLost().getPic8());
								}
//								iGetPicListSuccess
//										.getPicPath(getPicUri(picList));
							}

						}
						iGetPicListSuccess
						.getPicPath(getPicUri(picList));
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub

					}
				});
	}

	private ArrayList<String> getPicUri(ArrayList<BmobFile> list) {
		ArrayList<String> picUri = new ArrayList<String>();
		picUri.clear();
		for (BmobFile p : list) {
			picUri.add(p.getFileUrl());
		}
		return picUri;
	}

}
