package com.xgr.wonderful.ui;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cumtLife.bean.Lost;
import com.xgr.wonderful.R;



/**
 * 导航栏
 * 
 * @author jiawei.shi
 * 
 */
public class NaviFragment extends Fragment implements OnClickListener
         {

    private static final int HOMEFRAGMENT = 0;
    private static final int SETTINGSFRAGMENT = 1;
    private static final int FEEDBACKFRAGMENT = 2;
    private static final int INTROFRAGMENT = 3;
    private static final int ABOUTFRAGMENT = 4;
    private static final int DINNERFRAGMENT = 5;

    private MainActivity mActivity;
    private TextView navi_home;
    private TextView navi_settings;
    private TextView navi_my_focus;
    private TextView navi_find_things;
    private TextView navi_about;
    
    private ImageView iv_edit;
    private TextView tv_titleBar;
    
    private Context context;

    Mainfragment mMainFMainfragment;
    SettingsFragment mSettingsfragment;
    AboutFragment mAboutFragment;
    FavFragment mFavFragment;
    LostThingsFragment mFindLostThingsFragment;
    

    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View rootView;// 缓存Fragment view

    /**
     * 显示左边导航栏fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_navi, null);
        }

        fragmentManager = getFragmentManager();

        init();

        return rootView;

    }

    @Override
    public void onAttach(Activity activity) {
        mActivity = (MainActivity) activity;
        super.onAttach(activity);
    }

    /**
     * 初始化，设置点击事件
     */
    private void init() {
        navi_home = (TextView) rootView.findViewById(R.id.tv_navi_main);
        navi_my_focus = (TextView) rootView.findViewById(R.id.tv_navi_my_focus);
        navi_settings = (TextView) rootView.findViewById(R.id.tv_navi_settings);
        navi_find_things = (TextView) rootView.findViewById(R.id.tv_navi_find_things);
        navi_about = (TextView) rootView.findViewById(R.id.tv_navi_about);
        
        tv_titleBar = (TextView) getActivity().findViewById(R.id.topbar_title);
        iv_edit = (ImageView) getActivity().findViewById(R.id.topbar_menu_right);
        
        context = getActivity();

        navi_home.setSelected(true);// 默认选中菜单
        navi_my_focus.setSelected(false);
        navi_settings.setSelected(false);
        navi_find_things.setSelected(false);
        navi_about.setSelected(false);

        OnTabSelected(HOMEFRAGMENT);

        navi_home.setOnClickListener(this);
        navi_my_focus.setOnClickListener(this);
        navi_settings.setOnClickListener(this);
        navi_find_things.setOnClickListener(this);
        navi_about.setOnClickListener(this);
    }

    /**
     * 点击导航栏切换 同时更改标题
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.tv_navi_main:

            navi_home.setSelected(true);// 菜单设置为被选中状态，其余设置为非选中状态
            navi_my_focus.setSelected(false);
            navi_settings.setSelected(false);
            navi_find_things.setSelected(false);
            navi_about.setSelected(false);
            tv_titleBar.setText(R.string.home);
            iv_edit.setVisibility(View.VISIBLE);
            OnTabSelected(HOMEFRAGMENT);
            break;
        case R.id.tv_navi_my_focus:

            navi_home.setSelected(false);
            navi_my_focus.setSelected(true);
            navi_settings.setSelected(false);
            navi_find_things.setSelected(false);
            navi_about.setSelected(false);
            tv_titleBar.setText(R.string.my_focus);
            iv_edit.setVisibility(View.INVISIBLE);
            OnTabSelected(FEEDBACKFRAGMENT);
            break;
        case R.id.tv_navi_settings:// 

            navi_home.setSelected(false);
            navi_my_focus.setSelected(false);
            navi_settings.setSelected(true);
            navi_find_things.setSelected(false);
            navi_about.setSelected(false);
            tv_titleBar.setText(R.string.setting);
            iv_edit.setVisibility(View.INVISIBLE);
            OnTabSelected(SETTINGSFRAGMENT);
            break;
        case R.id.tv_navi_find_things:

            navi_home.setSelected(false);
            navi_my_focus.setSelected(false);
            navi_settings.setSelected(false);
            navi_find_things.setSelected(true);
            navi_about.setSelected(false);
            tv_titleBar.setText(R.string.find_lost_things);
            iv_edit.setVisibility(View.VISIBLE);
            OnTabSelected(INTROFRAGMENT);
            break;
        case R.id.tv_navi_about:
        	 navi_home.setSelected(false);
        	 navi_my_focus.setSelected(false);
             navi_settings.setSelected(false);
             navi_find_things.setSelected(false);
             navi_about.setSelected(true);
             tv_titleBar.setText(R.string.about_app);
             iv_edit.setVisibility(View.INVISIBLE);
             OnTabSelected(ABOUTFRAGMENT);
        	break;
        }
        mActivity.getSlidingMenu().toggle();
    }
    
    //选中导航中对应的tab选项
    private void OnTabSelected(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        
        switch (index) {
        case HOMEFRAGMENT:
        	hideFragments(transaction);
          if (null == mMainFMainfragment) {
              mMainFMainfragment = new Mainfragment();
              transaction.add(R.id.center, mMainFMainfragment);
          } else {
              transaction.show(mMainFMainfragment);
          }
            break;
        case SETTINGSFRAGMENT: 

        	hideFragments(transaction);
        	if (null == mSettingsfragment) {
        		mSettingsfragment = new SettingsFragment();
        		
                transaction.add(R.id.center, mSettingsfragment);
            } else {
            	mSettingsfragment.initPersonalInfo(context);
                transaction.show(mSettingsfragment);
            }
            break;
        case FEEDBACKFRAGMENT:

				hideFragments(transaction);
	        	if(null == mFavFragment){
	        		mFavFragment = new FavFragment();
	        		transaction.add(R.id.center, mFavFragment);
	        	}else{
	        		transaction.show(mFavFragment);
	        	}
//			} else {
				// 缓存用户对象为空时， 可打开用户注册界面
//				Toast.makeText(mActivity, "请先登录。",
//						Toast.LENGTH_SHORT).show();
//				Intent intent = new Intent();
//				intent.setClass(mActivity, RegisterAndLoginActivity.class);
//				startActivity(intent);
//			}
            break;
        case INTROFRAGMENT:
//        	Intent intent = new Intent(mActivity, FindLostThingsActivity.class);
//        	startActivity(intent);
        	hideFragments(transaction);
        	if(null== mFindLostThingsFragment){
        		mFindLostThingsFragment = new LostThingsFragment();
        		transaction.add(R.id.center, mFindLostThingsFragment);
        	}else{
        		transaction.show(mFindLostThingsFragment);
        	}


//        	OffersManager.getInstance(mActivity).showOffersWall();
            break;
        case ABOUTFRAGMENT:
        	hideFragments(transaction);
        	if(null == mAboutFragment){
        		mAboutFragment = new AboutFragment();
        		transaction.add(R.id.center, mAboutFragment);
        	}else{
        		transaction.show(mAboutFragment);
        	}
        	break;
        }
        transaction.commit();
    }

    /**
     * 将所有fragment都置为隐藏状态
     * 
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
    	if(mMainFMainfragment!=null){
    		transaction.hide(mMainFMainfragment);
    	}
    	if(mSettingsfragment!=null){
    		transaction.hide(mSettingsfragment);
    	}
    	if(mAboutFragment!=null){
    		transaction.hide(mAboutFragment);
    	}
    	if(mFavFragment!=null){
    		transaction.hide(mFavFragment);
    	}
    	if(mFindLostThingsFragment!=null){
    		transaction.hide(mFindLostThingsFragment);
    	}
    }

}

