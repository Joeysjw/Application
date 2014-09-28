package com.cumtLife.bean;

import java.util.Date;

import com.xgr.wonderful.entity.PicForLost;
import com.xgr.wonderful.entity.User;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/** 
  * @ClassName: Lost
  * @Description: TODO
  * @author jiawie.shi
  * @date 2014-8-13 
  */
public class Lost extends BmobObject{

	private String title;//标题
	private String describe;//详细信息
	private String phone;//手机
	
	private User author;
	private PicForLost picForLost;
	private int love;
	private int share;
	private int comment;
	private boolean myFav;//收藏
	private String date;
	
	private BmobFile photoOfthigs;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
//	public BmobFile getPhotoOfthigs() {
//		return photoOfthigs;
//	}
//	public void setPhotoOfthigs(BmobFile photoOfthigs) {
//		this.photoOfthigs = photoOfthigs;
//	}
	
	
	private BmobRelation relation;

	public BmobRelation getRelation() {
		return relation;
	}
	public void setRelation(BmobRelation relation) {
		this.relation = relation;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}

	public int getLove() {
		return love;
	}
	public void setLove(int love) {
		this.love = love;
	}

	public int getShare() {
		return share;
	}
	public void setShare(int share) {
		this.share = share;
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}
	public boolean getMyFav() {
		return myFav;
	}
	public void setMyFav(boolean myFav) {
		this.myFav = myFav;
	}
	public PicForLost getPicForLost() {
		return picForLost;
	}
	public void setPicForLost(PicForLost picForLost) {
		this.picForLost = picForLost;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	
	
	

	

	
}
