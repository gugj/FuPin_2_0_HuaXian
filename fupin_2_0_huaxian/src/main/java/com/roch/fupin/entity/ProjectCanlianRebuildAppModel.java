package com.roch.fupin.entity;

/**
 * 危房改造
 * <br>
 * <b>功能：</b>ProjectCanlianRebuildPage<br>
 * <b>作者：</b>nq生成器<br>
 */
public class ProjectCanlianRebuildAppModel extends BaseProject {
	
	
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//   ID
	private String familytypeidcall;//   家庭类型
	private String address;//   家庭住址
	private String approvestatusidcall;//   申请状态
	private String approvedate;//   申请日期
	private String oldstructcall;//   旧房房屋结构名称
	private String rebuildmodeid;//   改造方式ID,字典表
	private String rebuildmodeidcall;//   改造方式
	private String rebuildstructid;//   房屋结构ID,字典表
	private String rebuildstructidcall;//   房屋结构
	private double rebuildarea;//   改造面积
	private String personname;//   贫困户户主姓名
	
	public String getId() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getApprovestatusid() {
	public String getPersonname() {
		return personname;
	}
	public void setPersonname(String personname) {
		this.personname = personname;
	}
	public String getFamilytypeidcall() {
		return familytypeidcall;
	}
	public void setFamilytypeidcall(String familytypeidcall) {
		this.familytypeidcall = familytypeidcall;
	}
	public String getApprovestatusidcall() {
		return approvestatusidcall;
	}
	public void setApprovestatusidcall(String approvestatusidcall) {
		this.approvestatusidcall = approvestatusidcall;
	}
	public String getRebuildmodeidcall() {
		return rebuildmodeidcall;
	}
	public void setRebuildmodeidcall(String rebuildmodeidcall) {
		this.rebuildmodeidcall = rebuildmodeidcall;
	}
	public String getRebuildstructidcall() {
		return rebuildstructidcall;
	}
	public void setRebuildstructidcall(String rebuildstructidcall) {
		this.rebuildstructidcall = rebuildstructidcall;
	}
	public String getOldstructcall() {
		return oldstructcall;
	}
	public void setOldstructcall(String oldstructcall) {
		this.oldstructcall = oldstructcall;
	}
	
	
}