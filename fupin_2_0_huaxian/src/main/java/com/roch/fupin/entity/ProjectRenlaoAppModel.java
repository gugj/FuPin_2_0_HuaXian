package com.roch.fupin.entity;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>ProjectRenlaoAppPage<br>
 * <b>作者：</b>nq生成器<br>
 */
public class ProjectRenlaoAppModel extends BaseProject {
	
	
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//   ID
	private String traintypeidcall;//   培训类别名称
	private String traindate;//   培训时间
	private String trainmodeidcall;//   培训方式名称
	private String personname;//   培训负责人
	private List<ProjectRenlaoItemAppModel> pam;//人劳分类

	
		return pam;
	}
	public void setPam(List<ProjectRenlaoItemAppModel> pam) {
		this.pam = pam;
	}
	public String getId() {
	public String getTrainmodeidcall() {
		return trainmodeidcall;
	}
	public void setTrainmodeidcall(String trainmodeidcall) {
		this.trainmodeidcall = trainmodeidcall;
	}
	public String getTraintypeidcall() {
		return traintypeidcall;
	}
	public void setTraintypeidcall(String traintypeidcall) {
		this.traintypeidcall = traintypeidcall;
	}
}