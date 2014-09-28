package com.xgr.wonderful.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class PicForLost extends BmobObject {
	
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	public PicForLost() {
		super();
	}
	public PicForLost(BmobFile pic1) {
		super();
		this.pic1 = pic1;
	}
	
	
	public PicForLost(BmobFile pic1, BmobFile pic2) {
		super();
		this.pic1 = pic1;
		this.pic2 = pic2;
	}
	
	
	public PicForLost(BmobFile pic1, BmobFile pic2, BmobFile pic3) {
		super();
		this.pic1 = pic1;
		this.pic2 = pic2;
		this.pic3 = pic3;
	}
	
	
	public PicForLost(BmobFile pic1, BmobFile pic2, BmobFile pic3, BmobFile pic4) {
		super();
		this.pic1 = pic1;
		this.pic2 = pic2;
		this.pic3 = pic3;
		this.pic4 = pic4;
	}
	
	
	public PicForLost(BmobFile pic1, BmobFile pic2, BmobFile pic3,
			BmobFile pic4, BmobFile pic5) {
		super();
		this.pic1 = pic1;
		this.pic2 = pic2;
		this.pic3 = pic3;
		this.pic4 = pic4;
		this.pic5 = pic5;
	}
	
	
	
	public PicForLost(BmobFile pic1, BmobFile pic2, BmobFile pic3,
			BmobFile pic4, BmobFile pic5, BmobFile pic6) {
		super();
		this.pic1 = pic1;
		this.pic2 = pic2;
		this.pic3 = pic3;
		this.pic4 = pic4;
		this.pic5 = pic5;
		this.pic6 = pic6;
	}
	
	
	
	public PicForLost(BmobFile pic1, BmobFile pic2, BmobFile pic3,
			BmobFile pic4, BmobFile pic5, BmobFile pic6, BmobFile pic7) {
		super();
		this.pic1 = pic1;
		this.pic2 = pic2;
		this.pic3 = pic3;
		this.pic4 = pic4;
		this.pic5 = pic5;
		this.pic6 = pic6;
		this.pic7 = pic7;
	}
	public PicForLost(BmobFile pic1, BmobFile pic2, BmobFile pic3,
			BmobFile pic4, BmobFile pic5, BmobFile pic6, BmobFile pic7,
			BmobFile pic8) {
		super();
		this.pic1 = pic1;
		this.pic2 = pic2;
		this.pic3 = pic3;
		this.pic4 = pic4;
		this.pic5 = pic5;
		this.pic6 = pic6;
		this.pic7 = pic7;
		this.pic8 = pic8;
	}


	private BmobFile pic1;
	private BmobFile pic2;
	private BmobFile pic3;
	private BmobFile pic4;
	private BmobFile pic5;
	private BmobFile pic6;
	private BmobFile pic7;
	private BmobFile pic8;
	public BmobFile getPic1() {
		return pic1;
	}
	public void setPic1(BmobFile pic1) {
		this.pic1 = pic1;
	}
	public BmobFile getPic2() {
		return pic2;
	}
	public void setPic2(BmobFile pic2) {
		this.pic2 = pic2;
	}
	public BmobFile getPic3() {
		return pic3;
	}
	public void setPic3(BmobFile pic3) {
		this.pic3 = pic3;
	}
	public BmobFile getPic4() {
		return pic4;
	}
	public void setPic4(BmobFile pic4) {
		this.pic4 = pic4;
	}
	public BmobFile getPic5() {
		return pic5;
	}
	public void setPic5(BmobFile pic5) {
		this.pic5 = pic5;
	}
	public BmobFile getPic6() {
		return pic6;
	}
	public void setPic6(BmobFile pic6) {
		this.pic6 = pic6;
	}
	public BmobFile getPic7() {
		return pic7;
	}
	public void setPic7(BmobFile pic7) {
		this.pic7 = pic7;
	}
	public BmobFile getPic8() {
		return pic8;
	}
	public void setPic8(BmobFile pic8) {
		this.pic8 = pic8;
	}

}
