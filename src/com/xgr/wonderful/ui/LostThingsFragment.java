/**
 * Project Name: CumtLife
 * Package Name: com.xgr.wonderful.ui
 * File Name: LostThingsFragment.java
 * 
 * Copyright 2014 Garea Microsys, Inc. All Rights Reserved.
 * 
 * Date: 2014-9-4
 * @author joey
 *
 */
package com.xgr.wonderful.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;

import com.bmob.lostfound.AddFindLostActivity;
import com.cumtLife.Const.MyConst;
import com.xgr.wonderful.R;
import com.xgr.wonderful.adapter.LostContentAdapter;
import com.xgr.wonderful.ui.base.BaseFragment;
import com.xgr.wonderful.utils.LogUtils;

public class LostThingsFragment extends BaseFragment implements OnPageChangeListener,OnClickListener{
	
	private View contentView;
	private ViewPager viewpager_lost;
	private ImageView iv_topbar_menu_right;
	
	public static BaseFragment newInstance(){
		BaseFragment baseFragment = new LostThingsFragment();
		return baseFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.fragment_lost, null);
		iv_topbar_menu_right = (ImageView) getActivity().findViewById(R.id.topbar_menu_right);
		viewpager_lost = (ViewPager) contentView.findViewById(R.id.viewpager_lost);
		LostContentAdapter losContentAdapter = new LostContentAdapter(getActivity().getSupportFragmentManager());	
		viewpager_lost.setAdapter(losContentAdapter);
		viewpager_lost.setOnPageChangeListener(this);
		viewpager_lost.setOffscreenPageLimit(4);
		iv_topbar_menu_right.setOnClickListener(this);
		return contentView;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == iv_topbar_menu_right){
			  BmobUser currentUser = BmobUser.getCurrentUser(mContext);
	             if (currentUser != null) {
	                 // 允许用户使用应用,即有了用户的唯一标识符，可以作为发布内容的字段
	                 String name = currentUser.getUsername();
	                 String email = currentUser.getEmail();
	                 LogUtils.i(TAG,"username:"+name+",email:"+email);
	                 Intent intent = new Intent();
	                 intent.setClass(mContext, AddFindLostActivity.class);
	                 Log.w("I am here", "111111");
	                 startActivity(intent);
	             } else {
	                 // 缓存用户对象为空时， 可打开用户注册界面…
	                 Toast.makeText(mContext, "请先登录。",
	                         Toast.LENGTH_SHORT).show();
//	               redictToActivity(mContext, RegisterAndLoginActivity.class, null);
	                 Intent intent = new Intent();
	                 intent.setClass(mContext, RegisterAndLoginActivity.class);
	                 startActivityForResult(intent, MyConst.GO_LOGIN);
	             }
		}
  
	}
	
	

}
