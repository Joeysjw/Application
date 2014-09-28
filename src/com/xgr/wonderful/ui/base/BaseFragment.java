package com.xgr.wonderful.ui.base;


import com.umeng.analytics.MobclickAgent;
import com.xgr.wonderful.utils.Constant;
import com.xgr.wonderful.utils.Sputil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;


/**
 * @author jiawei.shi
 * @date 2014-9-4
 * TODO
 */

public abstract class BaseFragment extends Fragment{
	public static String TAG;
	protected Context mContext;
	protected Sputil sputil;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TAG = this.getClass().getSimpleName();
		mContext = getActivity();
		if(null == sputil){
			sputil = new Sputil(mContext, Constant.PRE_NAME);
		}
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart(TAG);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd(TAG);
	}
}
