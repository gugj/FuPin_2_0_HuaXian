package com.roch.fupin.entity;

import java.util.List;

/**
 * 贫困户、贫困村照片实体类
 */
public class PoorHousePhoto extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String userphone;
	private String shijian;
	private List<PoorPhoto> liam;

	public String getUserphone() {
		return userphone;
	}

	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}

	public String getShijian() {
		return shijian;
	}

	public void setShijian(String shijian) {
		this.shijian = shijian;
	}

	public List<PoorPhoto> getLiam() {
		return liam;
	}

	public void setLiam(List<PoorPhoto> liam) {
		this.liam = liam;
	}
}
