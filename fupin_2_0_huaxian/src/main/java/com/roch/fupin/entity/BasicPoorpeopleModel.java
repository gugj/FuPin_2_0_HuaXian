package com.roch.fupin.entity;

/**
 *   年龄和收入、文化程度、制品原因
 * <br>
 * <b>功能：</b>BasicPoorpeoplePage<br>
 * <b>作者：</b>nq生成器<br>
 */
public class BasicPoorpeopleModel extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;// ID
	private String householderid;// 户主ID
	private String nationid;// 民族ID,字典表
	private int sex;// 性别
	private String familyrelationid;// 与户主关系ID,字典表
	private String personname;// 成员姓名
	private String culturelevelid;// 文化程度ID,字典表
	private String inschoolstatusid;// 在校生状况ID,字典表
	private java.util.Date birthday;// 出生日期
	private String idno;// 身份证号
	private int ifxnh;// 是否参加新型农村合作医疗（城乡居民基本医疗保险）
	private int ifxnbx;// 是否参加新型农村社会养老保险（城乡居民社会养老保险）
	private int ifjbbx;// 是否参加城镇职工基本养老保险
	private int ifdbbx;// 是否参加大病保险
	private String pp_poorreason;// 贫困人致贫原因
	private String pp_helpplan;// 贫困人帮扶措施
	private String ifwanttrained;// 是否愿意培训
	private String personinfo;// 个人情况
	private String remark;// 备注

	private String householdername;// 户主姓名
	private int housecount;// 家庭人口

	// 特殊参数
	private String housesafe;// 房屋安全
	private String watertrouble;// 水质问题
	private String watersafe;// 饮水安全

	private String location;
	private String adl_cd;

	private int pp_poorreason_pc;// 致贫原因人数

	private int whcd_pc;// 文化程度人数

	private int income;// 年就业收入


	public String getAdl_cd() {
		return adl_cd;
	}

	public void setAdl_cd(String adl_cd) {
		this.adl_cd = adl_cd;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHouseholderid() {
		return this.householderid;
	}

	public void setHouseholderid(String householderid) {
		this.householderid = householderid;
	}

	public String getNationid() {
		return this.nationid;
	}

	public void setNationid(String nationid) {
		this.nationid = nationid;
	}

	public int getSex() {
		return this.sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getFamilyrelationid() {
		return this.familyrelationid;
	}

	public void setFamilyrelationid(String familyrelationid) {
		this.familyrelationid = familyrelationid;
	}

	public String getPersonname() {
		return this.personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}

	public String getCulturelevelid() {
		return this.culturelevelid;
	}

	public void setCulturelevelid(String culturelevelid) {
		this.culturelevelid = culturelevelid;
	}

	public String getInschoolstatusid() {
		return this.inschoolstatusid;
	}

	public void setInschoolstatusid(String inschoolstatusid) {
		this.inschoolstatusid = inschoolstatusid;
	}

	public java.util.Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public int getIfxnh() {
		return this.ifxnh;
	}

	public void setIfxnh(int ifxnh) {
		this.ifxnh = ifxnh;
	}

	public int getIfxnbx() {
		return this.ifxnbx;
	}

	public void setIfxnbx(int ifxnbx) {
		this.ifxnbx = ifxnbx;
	}

	public int getIfjbbx() {
		return this.ifjbbx;
	}

	public void setIfjbbx(int ifjbbx) {
		this.ifjbbx = ifjbbx;
	}

	public int getIfdbbx() {
		return this.ifdbbx;
	}

	public void setIfdbbx(int ifdbbx) {
		this.ifdbbx = ifdbbx;
	}

	public String getPp_poorreason() {
		return pp_poorreason;
	}

	public void setPp_poorreason(String pp_poorreason) {
		this.pp_poorreason = pp_poorreason;
	}

	public String getPp_helpplan() {
		return pp_helpplan;
	}

	public void setPp_helpplan(String pp_helpplan) {
		this.pp_helpplan = pp_helpplan;
	}

	public String getIfwanttrained() {
		return ifwanttrained;
	}

	public void setIfwanttrained(String ifwanttrained) {
		this.ifwanttrained = ifwanttrained;
	}

	public String getPersoninfo() {
		return personinfo;
	}

	public void setPersoninfo(String personinfo) {
		this.personinfo = personinfo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHouseholdername() {
		return householdername;
	}

	public void setHouseholdername(String householdername) {
		this.householdername = householdername;
	}

	public int getHousecount() {
		return housecount;
	}

	public void setHousecount(int housecount) {
		this.housecount = housecount;
	}

	public String getHousesafe() {
		return housesafe;
	}

	public void setHousesafe(String housesafe) {
		this.housesafe = housesafe;
	}

	public String getWatertrouble() {
		return watertrouble;
	}

	public void setWatertrouble(String watertrouble) {
		this.watertrouble = watertrouble;
	}

	public String getWatersafe() {
		return watersafe;
	}

	public void setWatersafe(String watersafe) {
		this.watersafe = watersafe;
	}

	public int getPp_poorreason_pc() {
		return pp_poorreason_pc;
	}

	public void setPp_poorreason_pc(int pp_poorreason_pc) {
		this.pp_poorreason_pc = pp_poorreason_pc;
	}

	public int getWhcd_pc() {
		return whcd_pc;
	}

	public void setWhcd_pc(int whcd_pc) {
		this.whcd_pc = whcd_pc;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
