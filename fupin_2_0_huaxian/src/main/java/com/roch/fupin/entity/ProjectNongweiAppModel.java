package com.roch.fupin.entity;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>ProjectNongweiAppPage<br>
 * <b>作者：</b>nq生成器<br>
 */
public class ProjectNongweiAppModel extends BaseProject {
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//   ID	private String projectid;//   项目ID	private String companyname;//   企业名称	private String companytypeid;//   企业类型ID,字典表	private String address;//   企业地址	private String loanpurpose;//   贷款用途	private double loanamount;//   贷款金额	private int loanmonth;//   贷款期限(月)	private String approvalstatusid;//   审批进度ID,字典表	private String personname;//   企业负责人	private String personphone;//   负责人联系方式	private String remark;//   备注
	
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
	public String getId() {	    return this.id;	}	public void setId(String id) {	 this.id=id;	}	public String getProjectid() {	    return this.projectid;	}	public void setProjectid(String projectid) {	 this.projectid=projectid;	}	public String getCompanyname() {	    return this.companyname;	}	public void setCompanyname(String companyname) {	 this.companyname=companyname;	}	public String getCompanytypeid() {	    return this.companytypeid;	}	public void setCompanytypeid(String companytypeid) {	 this.companytypeid=companytypeid;	}	public String getAddress() {	    return this.address;	}	public void setAddress(String address) {	 this.address=address;	}	public String getLoanpurpose() {	    return this.loanpurpose;	}	public void setLoanpurpose(String loanpurpose) {	 this.loanpurpose=loanpurpose;	}	public double getLoanamount() {	    return this.loanamount;	}	public void setLoanamount(double loanamount) {	 this.loanamount=loanamount;	}	public int getLoanmonth() {	    return this.loanmonth;	}	public void setLoanmonth(int loanmonth) {	 this.loanmonth=loanmonth;	}	public String getApprovalstatusid() {	    return this.approvalstatusid;	}	public void setApprovalstatusid(String approvalstatusid) {	 this.approvalstatusid=approvalstatusid;	}	public String getPersonname() {	    return this.personname;	}	public void setPersonname(String personname) {	 this.personname=personname;	}	public String getPersonphone() {	    return this.personphone;	}	public void setPersonphone(String personphone) {	 this.personphone=personphone;	}	public String getRemark() {	    return this.remark;	}	public void setRemark(String remark) {	 this.remark=remark;	}
	
}
