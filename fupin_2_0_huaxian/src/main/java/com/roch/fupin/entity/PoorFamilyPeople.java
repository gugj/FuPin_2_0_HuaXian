package com.roch.fupin.entity;

/**
 * 贫困户的家庭成员信息，含有家庭成员的全部字段
 * 
 * @author wf
 */
public class PoorFamilyPeople extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private String id;//   ID
	private String householderid;//   户主ID
	private String nationid;//   民族ID,字典表
	private int sex;//   性别
	private String familyrelationid;//   与户主关系ID,字典表
	private String personname;//   成员姓名
	private String culturelevelid;//   文化程度ID,字典表
	private String inschoolstatusid;//   在校生状况ID,字典表
	private String birthday;//   出生日期
	private String idno;//   身份证号
	private int ifxnh;//   是否参加新型农村合作医疗（城乡居民基本医疗保险）
	private int ifxnbx;//   是否参加新型农村社会养老保险（城乡居民社会养老保险）
	private int ifjbbx;//   是否参加城镇职工基本养老保险
	private String ifdbbx;//   是否参加大病保险
	private String pp_poorreason;//贫困人致贫原因
	private String pp_helpplan;//贫困人帮扶措施
	private String ifwanttrained;//是否愿意培训
	private String personinfo;//   个人情况
	private String remark;//   备注
	private String householdername;
	//特殊参数
	private String housesafe;//	房屋安全
	private String watertrouble;// 水质问题
	private String watersafe;// 饮水安全
	//对应上面的id
	private String sexName; 
	private String ifxnhname;
	private String ifxnbxname;
	private String ifjbbxname;
	private String ifdbbxname;
	
	private String pp_health;//健康状况
	private String pp_deformityno;//残疾证号 
	private String pp_spiritno;//精神病证件号
	private String pp_retardedno;//智障证件号 
	private String pp_outcontacttime;//失联时间  
	private String ifdeathtime;  //死亡时间
	private String onsoldiername;//是否现役军人
	private String martydependentsname;//是否军烈属
	private String ifoutcontactname;//是否失联
	private String ifecologyname;//是否是生态环境引起
	private String ifdeathname;//是否死亡
	private String pp_marriagemovetime;//婚迁时间	
	private String pp_marriagemovename;//婚迁
	private String jkzkname;//健康状况
	
	private String pp_laborskill;//劳动能力
	private String pp_laborskillname;//劳动技能
	private String housecount;//家庭人口数
	private String workstatus;//务工情况
	private String workmonth;//务工时间
	private String bankname;//银行卡名称
	private String bankcardno;//银行卡号
	
	private String nationname;//民族
	private String pp_phone;//联系电话
	private String whname;//文化程度
	private String inschoolname ;//在校生状态
	private String workstatusname ;//务工状态
//	private String ifdbbxname ;//是否参加大病保险
//	private String ifxnhname;//是否参加城乡居民医疗保险
//	private String ifxnbxname;//是否参加城乡居民养老保险
//	private String ifjbbxname ;//是否参加城镇职工养老保险


	public String getPp_laborskillname() {
		return pp_laborskillname;
	}

	public void setPp_laborskillname(String pp_laborskillname) {
		this.pp_laborskillname = pp_laborskillname;
	}

	public String getId() {
	    return this.id;
	}
	public void setId(String id) {
	 this.id=id;
	}
	public String getHouseholderid() {
	    return this.householderid;
	}
	public void setHouseholderid(String householderid) {
	 this.householderid=householderid;
	}
	public String getNationid() {
	    return this.nationid;
	}
	public void setNationid(String nationid) {
	 this.nationid=nationid;
	}
	public int getSex() {
	    return this.sex;
	}
	public void setSex(int sex) {
	 this.sex=sex;
	}
	public String getFamilyrelationid() {
	    return this.familyrelationid;
	}
	public void setFamilyrelationid(String familyrelationid) {
	 this.familyrelationid=familyrelationid;
	}
	public String getPersonname() {
	    return this.personname;
	}
	public void setPersonname(String personname) {
	 this.personname=personname;
	}
	public String getCulturelevelid() {
	    return this.culturelevelid;
	}
	public void setCulturelevelid(String culturelevelid) {
	 this.culturelevelid=culturelevelid;
	}
	public String getInschoolstatusid() {
	    return this.inschoolstatusid;
	}
	public void setInschoolstatusid(String inschoolstatusid) {
	 this.inschoolstatusid=inschoolstatusid;
	}
	public String getBirthday() {
	    return this.birthday;
	}
	public void setBirthday(String birthday) {
	 this.birthday=birthday;
	}
	public String getIdno() {
	    return this.idno;
	}
	public void setIdno(String idno) {
	 this.idno=idno;
	}
	public int getIfxnh() {
	    return this.ifxnh;
	}
	public void setIfxnh(int ifxnh) {
	 this.ifxnh=ifxnh;
	}
	public int getIfxnbx() {
	    return this.ifxnbx;
	}
	public void setIfxnbx(int ifxnbx) {
	 this.ifxnbx=ifxnbx;
	}
	public int getIfjbbx() {
	    return this.ifjbbx;
	}
	public void setIfjbbx(int ifjbbx) {
	 this.ifjbbx=ifjbbx;
	}
	public String getIfdbbx() {
	    return this.ifdbbx;
	}
	public void setIfdbbx(String ifdbbx) {
	 this.ifdbbx=ifdbbx;
	}
	public String getRemark() {
	    return this.remark;
	}
	public void setRemark(String remark) {
	 this.remark=remark;
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
	public String getHouseholdername() {
		return householdername;
	}
	public void setHouseholdername(String householdername) {
		this.householdername = householdername;
	}
	public String getSexName() {
		return sexName;
	}
	public void setSexName(String sexName) {
		this.sexName = sexName;
	}
	public String getIfxnhname() {
		return ifxnhname;
	}
	public void setIfxnhname(String ifxnhname) {
		this.ifxnhname = ifxnhname;
	}
	public String getIfxnbxname() {
		return ifxnbxname;
	}
	public void setIfxnbxname(String ifxnbxname) {
		this.ifxnbxname = ifxnbxname;
	}
	public String getIfjbbxname() {
		return ifjbbxname;
	}
	public void setIfjbbxname(String ifjbbxname) {
		this.ifjbbxname = ifjbbxname;
	}
	public String getIfdbbxname() {
		return ifdbbxname;
	}
	public void setIfdbbxname(String ifdbbxname) {
		this.ifdbbxname = ifdbbxname;
	}
	public String getPp_health() {
		return pp_health;
	}
	public void setPp_health(String pp_health) {
		this.pp_health = pp_health;
	}
	public String getPp_deformityno() {
		return pp_deformityno;
	}
	public void setPp_deformityno(String pp_deformityno) {
		this.pp_deformityno = pp_deformityno;
	}
	public String getPp_spiritno() {
		return pp_spiritno;
	}
	public void setPp_spiritno(String pp_spiritno) {
		this.pp_spiritno = pp_spiritno;
	}
	public String getPp_retardedno() {
		return pp_retardedno;
	}
	public void setPp_retardedno(String pp_retardedno) {
		this.pp_retardedno = pp_retardedno;
	}
	public String getPp_outcontacttime() {
		return pp_outcontacttime;
	}
	public void setPp_outcontacttime(String pp_outcontacttime) {
		this.pp_outcontacttime = pp_outcontacttime;
	}
	public String getIfdeathtime() {
		return ifdeathtime;
	}
	public void setIfdeathtime(String ifdeathtime) {
		this.ifdeathtime = ifdeathtime;
	}
	public String getOnsoldiername() {
		return onsoldiername;
	}
	public void setOnsoldiername(String onsoldiername) {
		this.onsoldiername = onsoldiername;
	}
	public String getMartydependentsname() {
		return martydependentsname;
	}
	public void setMartydependentsname(String martydependentsname) {
		this.martydependentsname = martydependentsname;
	}
	public String getIfoutcontactname() {
		return ifoutcontactname;
	}
	public void setIfoutcontactname(String ifoutcontactname) {
		this.ifoutcontactname = ifoutcontactname;
	}
	public String getIfecologyname() {
		return ifecologyname;
	}
	public void setIfecologyname(String ifecologyname) {
		this.ifecologyname = ifecologyname;
	}
	public String getIfdeathname() {
		return ifdeathname;
	}
	public void setIfdeathname(String ifdeathname) {
		this.ifdeathname = ifdeathname;
	}
	public String getPp_marriagemovetime() {
		return pp_marriagemovetime;
	}
	public void setPp_marriagemovetime(String pp_marriagemovetime) {
		this.pp_marriagemovetime = pp_marriagemovetime;
	}
	public String getPp_marriagemovename() {
		return pp_marriagemovename;
	}
	public void setPp_marriagemovename(String pp_marriagemovename) {
		this.pp_marriagemovename = pp_marriagemovename;
	}
	public String getJkzkname() {
		return jkzkname;
	}
	public void setJkzkname(String jkzkname) {
		this.jkzkname = jkzkname;
	}
	public String getHousecount() {
		return housecount;
	}
	public void setHousecount(String housecount) {
		this.housecount = housecount;
	}
	public String getPp_laborskill() {
		return pp_laborskill;
	}
	public void setPp_laborskill(String pp_laborskill) {
		this.pp_laborskill = pp_laborskill;
	}
	public String getWorkstatus() {
		return workstatus;
	}
	public void setWorkstatus(String workstatus) {
		this.workstatus = workstatus;
	}
	public String getWorkmonth() {
		return workmonth;
	}
	public void setWorkmonth(String workmonth) {
		this.workmonth = workmonth;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBankcardno() {
		return bankcardno;
	}
	public void setBankcardno(String bankcardno) {
		this.bankcardno = bankcardno;
	}
	public String getNationname() {
		return nationname;
	}
	public void setNationname(String nationname) {
		this.nationname = nationname;
	}
	public String getWhname() {
		return whname;
	}
	public void setWhname(String whname) {
		this.whname = whname;
	}
	public String getInschoolname() {
		return inschoolname;
	}
	public void setInschoolname(String inschoolname) {
		this.inschoolname = inschoolname;
	}
	public String getPp_phone() {
		return pp_phone;
	}
	public void setPp_phone(String pp_phone) {
		this.pp_phone = pp_phone;
	}
	public String getWorkstatusname() {
		return workstatusname;
	}
	public void setWorkstatusname(String workstatusname) {
		this.workstatusname = workstatusname;
	}
	
	
}
