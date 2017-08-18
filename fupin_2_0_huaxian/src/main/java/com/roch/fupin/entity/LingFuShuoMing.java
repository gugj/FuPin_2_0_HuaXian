package com.roch.fupin.entity;

/**
 * 另附说明实体类
 * 
 * @author wf
 */
public class LingFuShuoMing extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String familyinfoid;
	private String userid;
	private String householderid;
	private String familyinfo;
	private String creattime;
	private String lastupdatetime;
	private String subCreattime;
	private String subLastupdatetime;
	private String username;

	private String villageinfoid;
	private String villageid;
	private String villageinfo;

	public String getVillageinfoid() {
		return villageinfoid;
	}

	public void setVillageinfoid(String villageinfoid) {
		this.villageinfoid = villageinfoid;
	}

	public String getVillageid() {
		return villageid;
	}

	public void setVillageid(String villageid) {
		this.villageid = villageid;
	}

	public String getVillageinfo() {
		return villageinfo;
	}

	public void setVillageinfo(String villageinfo) {
		this.villageinfo = villageinfo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSubCreattime() {
		return subCreattime;
	}

	public void setSubCreattime(String subCreattime) {
		this.subCreattime = subCreattime;
	}

	public String getSubLastupdatetime() {
		return subLastupdatetime;
	}

	public void setSubLastupdatetime(String subLastupdatetime) {
		this.subLastupdatetime = subLastupdatetime;
	}

	public String getFamilyinfoid() {
		return familyinfoid;
	}

	public void setFamilyinfoid(String familyinfoid) {
		this.familyinfoid = familyinfoid;
	}

	public String getFamilyinfo() {
		return familyinfo;
	}

	public void setFamilyinfo(String familyinfo) {
		this.familyinfo = familyinfo;
	}

	public String getHouseholderid() {
		return householderid;
	}

	public void setHouseholderid(String householderid) {
		this.householderid = householderid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCreattime() {
		return creattime;
	}

	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}

	public String getLastupdatetime() {
		return lastupdatetime;
	}

	public void setLastupdatetime(String lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}
}
