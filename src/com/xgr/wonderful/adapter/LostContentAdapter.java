/**
 * Project Name: CumtLife
 * Package Name: com.xgr.wonderful.adapter
 * File Name: LostContentAdapter.java
 * 
 * Copyright 2014 Garea Microsys, Inc. All Rights Reserved.
 * 
 * Date: 2014-9-4
 * @author joey
 *
 */
package com.xgr.wonderful.adapter;

import com.xgr.wonderful.ui.LostContentFragment;
import com.xgr.wonderful.ui.LostThingsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class LostContentAdapter extends SmartFragmentStatePagerAdapter {
	
	private static int NUM_ITEMS = 1;

	public LostContentAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		if(!(this.getRegisteredFragment(position)==null)){
			return getRegisteredFragment(position);
		}else{
			return  LostContentFragment.newInstance(position);
		}
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return NUM_ITEMS;
	}

}
