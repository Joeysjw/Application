package com.xgr.wonderful.proxy;

import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.ResetPasswordListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


import com.umeng.fb.model.UserReply;
import com.xgr.wonderful.entity.User;
import com.xgr.wonderful.utils.Constant;
import com.xgr.wonderful.utils.LogUtils;

import android.content.Context;
import android.util.Log;

public class UserProxy {

	public static final String TAG = "UserProxy";
	
	private Context mContext;
	
	public UserProxy(Context context){
		this.mContext = context;
	}
	
	public void signUp(String userName,String password,String email){
//		UserInfo userInfo = new UserInfo();
//		userInfo.setName(userName);
//		userInfo.setPassword(password);
//		userInfo.setEmail(email);
//		userInfo.save(mContext, new SaveListener() {
//			
//			@Override
//			public void onSuccess() {
//				if(signUpLister != null){
//					signUpLister.onSignUpSuccess();
//				}else{
//					LogUtils.i(TAG,"signup listener is null,you must set one!");
//				}
//				
//			}
//			
//			@Override
//			public void onFailure(int arg0, String arg1) {
//				if(signUpLister != null){
//					signUpLister.onSignUpFailure(arg1);
//				}else{
//					LogUtils.i(TAG,"signup listener is null,you must set one!");
//				}
//				
//			}
//		});
		
		
		User user = new User();
		user.setUsername(userName);
		user.setPassword(password);
		user.setEmail(email);
		user.setSex(Constant.SEX_FEMALE);
		user.setSignature("这个家伙很懒，什么也不说。。。");
		user.signUp(mContext, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				if(signUpLister != null){
					signUpLister.onSignUpSuccess();
				}else{
					LogUtils.i(TAG,"signup listener is null,you must set one!");
				}
			}

			@Override
			public void onFailure(int arg0, String msg) {
				// TODO Auto-generated method stub
				if(signUpLister != null){
					signUpLister.onSignUpFailure(msg);
				}else{
					LogUtils.i(TAG,"signup listener is null,you must set one!");
				}
			}
		});
	}
	
	public interface ISignUpListener{
		void onSignUpSuccess();
		void onSignUpFailure(String msg);
	}
	private ISignUpListener signUpLister;
	public void setOnSignUpListener(ISignUpListener signUpLister){
		this.signUpLister = signUpLister;
	}
	
	
	public User getCurrentUser(){
		User user = BmobUser.getCurrentUser(mContext, User.class);
		if(user != null){
			LogUtils.i(TAG,"本地用户信息" + user.getObjectId() + "-"
					+ user.getUsername() + "-"
					+ user.getSessionToken() + "-"
					+ user.getCreatedAt() + "-"
					+ user.getUpdatedAt() + "-"
					+ user.getSignature() + "-"
					+ user.getSex());
			return user;
		}else{
			LogUtils.i(TAG,"本地用户为null,请登录。");
		}
		return null;
	}
	
	public void login(final String userName,final String password){
		final BmobUser user = new BmobUser();
		user.setUsername(userName);
		user.setPassword(password);
		user.login(mContext, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				if(loginListener != null){
					loginListener.onLoginSuccess();
				}
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				if(loginListener != null){
					loginListener.onLoginFailure(arg1);
				}
			}
		});
		
		
		
//		BmobQuery<UserInfo> userLogin = new BmobQuery<UserInfo>();
//		userLogin.addWhereEqualTo("name", userName).addWhereEqualTo("password", password);
//		userLogin.findObjects(mContext, new FindListener<UserInfo>() {			
//			@Override
//			public void onSuccess(List<UserInfo> arg0) {
//				Log.w("arg0 is", ""+arg0.size());
//				if(arg0.size()>0){
//					if(loginListener != null){
//						Log.w("LoginSuccess", "LoginSuccess");
//						loginListener.onLoginSuccess();
//					}else{
//						Log.w("LoginFailed", "LoginFailed");
//						LogUtils.i(TAG, "login listener is null,you must set one!");
//					}
//					
//				}else{
//					if(loginListener != null){
//						loginListener.onLoginFailure("用户名或密码错误！");
//					}
//				}			
//			}
//			
//			@Override
//			public void onError(int arg0, String arg1) {
//				if(loginListener != null){
//					loginListener.onLoginFailure(arg1);
//				}else{
//					LogUtils.i(TAG, "login listener is null,you must set one!");
//				}
//			}
//		});
		
	}
	
	public interface ILoginListener{
		void onLoginSuccess();
		void onLoginFailure(String msg);
	}
	private ILoginListener loginListener;
	public void setOnLoginListener(ILoginListener loginListener){
		this.loginListener  = loginListener;
	}
	
	public void logout(){
		BmobUser.logOut(mContext);
		LogUtils.i(TAG, "logout result:"+(null == getCurrentUser()));
	}
	
	public void update(String... args){
		User user = getCurrentUser();
		user.setUsername(args[0]);
		user.setEmail(args[1]);
		user.setPassword(args[2]);
		user.setSex(args[3]);
		user.setSignature(args[4]);
		//...
		user.update(mContext, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				if(updateListener != null){
					updateListener.onUpdateSuccess();
				}else{
					LogUtils.i(TAG,"update listener is null,you must set one!");
				}
			}

			@Override
			public void onFailure(int arg0, String msg) {
				// TODO Auto-generated method stub
				if(updateListener != null){
					updateListener.onUpdateFailure(msg);
				}else{
					LogUtils.i(TAG,"update listener is null,you must set one!");
				}
			}
		});
	}
	
	public interface IUpdateListener{
		void onUpdateSuccess();
		void onUpdateFailure(String msg);
	}
	private IUpdateListener updateListener;
	public void setOnUpdateListener(IUpdateListener updateListener){
		this.updateListener = updateListener;
	}
	
	public void resetPassword(String email){
		BmobUser.resetPassword(mContext, email, new ResetPasswordListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				if(resetPasswordListener != null){
					resetPasswordListener.onResetSuccess();
				}else{
					LogUtils.i(TAG,"reset listener is null,you must set one!");
				}
			}

			@Override
			public void onFailure(int arg0, String msg) {
				// TODO Auto-generated method stub
				if(resetPasswordListener != null){
					resetPasswordListener.onResetFailure(msg);
				}else{
					LogUtils.i(TAG,"reset listener is null,you must set one!");
				}
			}
		});
	}
	public interface IResetPasswordListener{
		void onResetSuccess();
		void onResetFailure(String msg);
	}
	private IResetPasswordListener resetPasswordListener;
	public void setOnResetPasswordListener(IResetPasswordListener resetPasswordListener){
		this.resetPasswordListener = resetPasswordListener;
	}

}
