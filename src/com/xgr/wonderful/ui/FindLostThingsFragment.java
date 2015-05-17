package com.xgr.wonderful.ui;

import static com.xgr.wonderful.R.id.tv_describe;
import static com.xgr.wonderful.R.id.tv_photo;
import static com.xgr.wonderful.R.id.tv_time;
import static com.xgr.wonderful.R.id.tv_title;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.bmob.lostfound.AddFindLostActivity;
import com.bmob.lostfound.adapter.BaseAdapterHelper;
import com.bmob.lostfound.adapter.QuickAdapter;
import com.cumtLife.Const.MyConst;
import com.cumtLife.bean.Found;
import com.cumtLife.bean.Lost;
import com.xgr.wonderful.R;
import com.xgr.wonderful.ui.base.BaseHomeFragment;
import com.xgr.wonderful.utils.LogUtils;

/**
 * 
 * @author joey
 * @date 2014-9-4
 *
 */

public class FindLostThingsFragment extends BaseHomeFragment implements OnClickListener,OnCheckedChangeListener{

	private RelativeLayout layout_action;
//	private LinearLayout layout_all;
	private TextView tv_lost;

	
	private ListView listview;
//	private Button btn_add;

	protected QuickAdapter<Lost> LostAdapter;

//	protected QuickAdapter<Found> FoundAdapter;

	private Button layout_found;
	private Button layout_lost;
	
	private ImageView iv_topbar_menu_right;
	
	
	PopupWindow morePop;

	RelativeLayout progress;
	LinearLayout layout_no;
	TextView tv_no;
	
	
	
	public static FindLostThingsFragment newInstance(){
		FindLostThingsFragment addFindLostFragment = new FindLostThingsFragment();
		return addFindLostFragment;
	}
	
	
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.find_lost_activity;
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
	
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		if(resultCode == Activity.RESULT_OK){
//			if(requestCode == MyConst.GO_LOGIN){
//				
//			}
//		}
//		
//	}

	@Override
	protected void findViews(View view) {
		progress = (RelativeLayout) view.findViewById(R.id.progress);
		layout_no = (LinearLayout) view.findViewById(R.id.layout_no);
		tv_no = (TextView) view.findViewById(R.id.tv_no);
		layout_action = (RelativeLayout) view.findViewById(R.id.layout_action);
//		listview = (ListView) view.findViewById(R.id.list_lost);
		
		iv_topbar_menu_right = (ImageView) getActivity().findViewById(R.id.topbar_menu_right);
		iv_topbar_menu_right.setOnClickListener(this);
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	
	}

	@Override
	protected void setupViews(Bundle bundle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setListener() {

	}
	
	
	@Override
	protected void fetchData() {
//		if (LostAdapter == null) {
//			LostAdapter = new QuickAdapter<Lost>(mContext, R.layout.item_list) {
//				@Override
//				protected void convert(BaseAdapterHelper helper, Lost lost) {
//					helper.setText(tv_title, lost.getTitle())
//							.setText(tv_describe, lost.getDescribe())
//							.setText(tv_time, lost.getCreatedAt())
//							.setText(tv_photo, lost.getPhone());
//				}
//			};
//		}

//		if (FoundAdapter == null) {
//			FoundAdapter = new QuickAdapter<Found>(mContext, R.layout.item_list) {
//				@Override
//				protected void convert(BaseAdapterHelper helper, Found found) {
//					helper.setText(tv_title, found.getTitle())
//							.setText(tv_describe, found.getDescribe())
//							.setText(tv_time, found.getCreatedAt())
//							.setText(tv_photo, found.getPhone());
//				}
//			};
//		}
//		listview.setAdapter(LostAdapter);
//		queryLosts();
		
	}
	
	private void queryLosts() {
		showView();
		BmobQuery<Lost> query = new BmobQuery<Lost>();
		query.order("-createdAt");
		query.findObjects(mContext, new FindListener<Lost>() {

			@Override
			public void onSuccess(List<Lost> losts) {
				// TODO Auto-generated method stub
				LostAdapter.clear();
//				FoundAdapter.clear();
				if (losts == null || losts.size() == 0) {
					showErrorView(0);
					LostAdapter.notifyDataSetChanged();
					return;
				}
				progress.setVisibility(View.GONE);
				LostAdapter.addAll(losts);
				listview.setAdapter(LostAdapter);
			}

			@Override
			public void onError(int code, String arg0) {
				// TODO Auto-generated method stub
				showErrorView(0);
			}
		});
	}
	
	private void showView() {
		listview.setVisibility(View.VISIBLE);
		layout_no.setVisibility(View.GONE);
	}
	
	private void showErrorView(int tag) {
		progress.setVisibility(View.GONE);
		listview.setVisibility(View.GONE);
		layout_no.setVisibility(View.VISIBLE);
		if (tag == 0) {
			tv_no.setText(getResources().getText(R.string.list_no_data_lost));
		} else {
			tv_no.setText(getResources().getText(R.string.list_no_data_found));
		}
	}
	
	

}
