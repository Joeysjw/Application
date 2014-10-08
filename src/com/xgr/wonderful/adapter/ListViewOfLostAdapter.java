/**
 * Project Name: CumtLife
 * Package Name: com.xgr.wonderful.adapter
 * File Name: ListViewOfLostAdapter.java
 * 
 * Copyright 2014 Garea Microsys, Inc. All Rights Reserved.
 * 
 * Date: 2014-9-5
 * @author joey
 *
 */
package com.xgr.wonderful.adapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.GetListener;

import com.cumtLife.Const.MyConst;
import com.cumtLife.bean.Lost;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.xgr.wonderful.MyApplication;
import com.xgr.wonderful.R;
import com.xgr.wonderful.adapter.AIContentAdapter.ViewHolder;
import com.xgr.wonderful.entity.QiangYu;
import com.xgr.wonderful.entity.User;
import com.xgr.wonderful.sns.TencentShare;
import com.xgr.wonderful.sns.TencentShareEntity;
import com.xgr.wonderful.ui.CommentActivity;
import com.xgr.wonderful.ui.CommentOfLostActivity;
import com.xgr.wonderful.ui.RegisterAndLoginActivity;
import com.xgr.wonderful.utils.ActivityUtil;
import com.xgr.wonderful.utils.GetLostPicUriUtil;
import com.xgr.wonderful.utils.LogUtils;
import com.xgr.wonderful.utils.GetLostPicUriUtil.IGetPicListSuccess;
import com.xgr.wonderful.view.MyGridView;

public class ListViewOfLostAdapter extends BaseContentAdapter<Lost> {
	private Context context;
	
	private ArrayList<String> picPath = new ArrayList<String>();

	public ListViewOfLostAdapter(Context context, List<Lost> list) {
		super(context, list);
		this.context = context;
	}

	@Override
	public View getConvertView(final int position, View convertView,
			ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.lost_listview_item, null);
			viewHolder.userName = (TextView) convertView
					.findViewById(R.id.lost_user_name);
			viewHolder.userLogo = (ImageView) convertView 
					.findViewById(R.id.lost_user_logo);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.lost_item_date);
			viewHolder.contentText = (TextView) convertView
					.findViewById(R.id.lost_content_text);
			viewHolder.content_image = (MyGridView) convertView
					.findViewById(R.id.lost_content_image);
			viewHolder.phone = (TextView) convertView
					.findViewById(R.id.tv_phone);
			viewHolder.share = (TextView) convertView
					.findViewById(R.id.lost_item_action_share);
			viewHolder.comment = (TextView) convertView
					.findViewById(R.id.lost_item_action_comment);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final Lost entity = dataList.get(position);
		LogUtils.i("user", entity.toString());
		User user = entity.getAuthor();
		if (user == null) {
			LogUtils.i("user", "USER IS NULL");
		}
		if (user.getAvatar() == null) {
			LogUtils.i("user", "USER avatar IS NULL");
		}
		String avatarUrl = null;
		if (user.getAvatar() != null) {
			avatarUrl = user.getAvatar().getFileUrl();
		}
		ImageLoader.getInstance().displayImage(
				avatarUrl,
				viewHolder.userLogo,
				MyApplication.getInstance().getOptions(
						R.drawable.user_icon_default_main),
				new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// TODO Auto-generated method stub
						super.onLoadingComplete(imageUri, view, loadedImage);
					}

				});

		viewHolder.userName.setText(entity.getAuthor().getUsername());
		viewHolder.date.setText(entity.getDate());

		viewHolder.contentText.setText(entity.getDescribe());
		GetLostPicUriUtil getLostPicUriUtil = new GetLostPicUriUtil(mContext, entity);		
		getLostPicUriUtil.getPicUriList();
		getLostPicUriUtil.setIGetPicListSuccessListener(new IGetPicListSuccess() {
			
			@Override
			public void getPicPath(ArrayList<String> list) {		
				if(list==null || list.size()<1){
					viewHolder.content_image.setVisibility(View.GONE);
				}else{
					viewHolder.content_image.setVisibility(View.VISIBLE);
					viewHolder.content_image
							.setAdapter(new LostGridViewAdapter(context, list));
					viewHolder.content_image.setTag(entity.getObjectId());
				}
				
			}
		});
		
		
//		picPath = getLostPicUriUtil.getPicUriList();
//		if(picPath==null || picPath.size()<1){
//			viewHolder.content_image.setVisibility(View.GONE);
//		}else{
////			Log.w("I am in else...", "joey");
//			viewHolder.content_image.setVisibility(View.VISIBLE);
//			viewHolder.content_image
//					.setAdapter(new LostGridViewAdapter(context, picPath));
//			viewHolder.content_image.setTag(entity.getObjectId());
//		}
		
		
		
//		BmobQuery<Lost> query = new BmobQuery<Lost>();		
//		// query.setCachePolicy(CachePolicy.NETWORK_ONLY);
////		query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
//		// Log.w("query.hasCachedResult(context)",
//		// ""+query.hasCachedResult(context));
//		// if(!query.hasCachedResult(context)){
//
//		query.include("picForLost");
//		query.getObject(context, entity.getObjectId(), new GetListener<Lost>() {
//
//			@Override
//			public void onSuccess(Lost lost) {
//				// TODO Auto-generated method stub
//				if (lost == null) {
//					return;
//				} else {
//					// picList = new ArrayList<BmobFile>();
//					if (lost.getPicForLost() != null) {
//						picList.clear();
//						if (lost.getPicForLost().getPic1() != null) {
//							picList.add(lost.getPicForLost().getPic1());
//						}
//						if (lost.getPicForLost().getPic2() != null) {
//							picList.add(lost.getPicForLost().getPic2());
//						}
//						if (lost.getPicForLost().getPic3() != null) {
//							picList.add(lost.getPicForLost().getPic3());
//						}
//						if (lost.getPicForLost().getPic4() != null) {
//							picList.add(lost.getPicForLost().getPic4());
//						}
//						if (lost.getPicForLost().getPic5() != null) {
//							picList.add(lost.getPicForLost().getPic5());
//						}
//						if (lost.getPicForLost().getPic6() != null) {
//							picList.add(lost.getPicForLost().getPic6());
//						}
//						if (lost.getPicForLost().getPic6() != null) {
//							picList.add(lost.getPicForLost().getPic7());
//						}
//						if (lost.getPicForLost().getPic6() != null) {
//							picList.add(lost.getPicForLost().getPic8());
//						}
//						
//						
//						viewHolder.content_image.setVisibility(View.VISIBLE);
//						viewHolder.content_image
//								.setAdapter(new LostGridViewAdapter(context,
//										getPicUri(picList)));
//						viewHolder.content_image.setTag(entity.getObjectId());
//					} else {
//						viewHolder.content_image.setVisibility(View.GONE);
//					}
//
//				}
//			}
//
//			@Override
//			public void onFailure(int arg0, String arg1) {
//				// TODO Auto-generated method stub
//
//			}
//		});
		
		// }else{
		//
		//
		// }
		//

		// viewHolder.share.setOnClickListener(new OnClickListener() {
		//
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// //share to sociaty
		// ActivityUtil.show(mContext, "分享给好友看哦~");
		// final TencentShare tencentShare=new
		// TencentShare(MyApplication.getInstance().getTopActivity(),
		// getQQShareEntity(entity));
		// tencentShare.shareToQQ();
		// }
		// });

		viewHolder.comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 评论
				// MyApplication.getInstance().setCurrentQiangYu(entity);
				if (MyApplication.getInstance().getCurrentUser() == null) {
					ActivityUtil.show(mContext, "请先登录。");
					Intent intent = new Intent();
					intent.setClass(mContext, RegisterAndLoginActivity.class);
					MyApplication.getInstance().getTopActivity()
							.startActivity(intent);
					return;
				}
				Intent intent = new Intent();
				intent.setClass(mContext,
						CommentOfLostActivity.class);
				intent.putExtra(MyConst.DATA, entity);
				intent.putStringArrayListExtra(MyConst.PICLIST, picPath);
				mContext.startActivity(intent);
			}
		});

		viewHolder.phone.setText(entity.getPhone().toString());

		return convertView;
	}
	
	
//	private ArrayList<String> getPicUri(ArrayList<BmobFile> list){
//		ArrayList<String> picUri = new ArrayList<String>();
//		picUri.clear();
//		for(BmobFile p : list){
//			picUri.add(p.getFileUrl());
//		}
//		return picUri;
//	}

	public static class ViewHolder {
		public ImageView userLogo;
		public TextView userName;
		public TextView contentText;
		public TextView phone;
		public MyGridView content_image;

		// public ImageView favMark;
		public TextView date;

		public TextView share;
		public TextView comment;
	}


	// private TencentShareEntity getQQShareEntity(Lost lost) {
	// String title= "这里好多美丽的风景";
	// String comment="来领略最美的风景吧";
	// String img= null;
	// if(qy.getContentfigureurl()!=null){
	// img = qy.getContentfigureurl().getFileUrl();
	// }else{
	// img =
	// "http://www.codenow.cn/appwebsite/website/yyquan/uploads/53af6851d5d72.png";
	// }
	// String summary=qy.getContent();
	//
	// String targetUrl="http://yuanquan.bmob.cn";
	// TencentShareEntity entity=new TencentShareEntity(title, img, targetUrl,
	// summary, comment);
	// return entity;
	// }

}
