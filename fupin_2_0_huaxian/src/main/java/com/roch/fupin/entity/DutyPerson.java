package com.roch.fupin.entity;

/**
 * 
 * <br>
 * <b>功能：</b>HelpdutypersonAppPage<br>
 * <b>作者：</b>nq生成器<br>
 */
public class DutyPerson extends BaseEntity {
	
	
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//   ID
	//贫困户-帮扶责任人关系表字段
	private String map_id;//   ID
	private String householderid;//   户主ID
	private String helpdutypersonid;//   帮扶责任人ID
	private String sexName;//性别

	public String getSexName() {
		return this.sexName;
	}
	public void setSexName(String sexName) {
		this.sexName = sexName;
	}
	public String getId() {
	public String getMap_id() {
		return map_id;
	}
	public void setMap_id(String map_id) {
		this.map_id = map_id;
	}
	public String getHouseholderid() {
		return householderid;
	}
	public void setHouseholderid(String householderid) {
		this.householderid = householderid;
	}
	public String getHelpdutypersonid() {
		return helpdutypersonid;
	}
	public void setHelpdutypersonid(String helpdutypersonid) {
		this.helpdutypersonid = helpdutypersonid;
	}
	
}