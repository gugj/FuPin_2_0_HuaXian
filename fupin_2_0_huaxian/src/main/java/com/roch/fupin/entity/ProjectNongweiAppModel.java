package com.roch.fupin.entity;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>ProjectNongweiAppPage<br>
 * <b>作者：</b>nq生成器<br>
 */
public class ProjectNongweiAppModel extends BaseProject {
	
	
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//   ID
	
	private String companytypeidcall;//企业类型
	private String approvalstatusidcall;//审批进度
	
	private List<ProjectNongweiItemAppModel> pnm;//就业信息
	
	public List<ProjectNongweiItemAppModel> getPnm() {
		return pnm;
	}
	public void setPnm(List<ProjectNongweiItemAppModel> pnm) {
		this.pnm = pnm;
	}
	public String getCompanytypeidcall() {
		return companytypeidcall;
	}
	public void setCompanytypeidcall(String companytypeidcall) {
		this.companytypeidcall = companytypeidcall;
	}
	public String getApprovalstatusidcall() {
		return approvalstatusidcall;
	}
	public void setApprovalstatusidcall(String approvalstatusidcall) {
		this.approvalstatusidcall = approvalstatusidcall;
	}
	public String getId() {
	
}