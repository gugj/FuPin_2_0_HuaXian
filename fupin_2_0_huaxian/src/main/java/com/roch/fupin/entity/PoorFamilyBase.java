package com.roch.fupin.entity;

import java.util.List;

/**
 * 贫困户的全部的基本信息，包括户ID <br>
 * <b>功能：</b>BasicPoorfamilyPage<br>
 * <b>作者：</b>nq生成器<br>
 */
public class PoorFamilyBase extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private String householderid;// 户主ID
	private String povertystatusid;// 脱贫属性ID，字典表，新识别贫困户，未脱贫，已脱贫，预脱贫，返贫，注销
	private String helpplan;// 扶贫计划
	private String poorreason;// 致贫原因
	private double wholeincome;// 总收入（前3项总和）
	private String officeinsurance;// 医疗社保保险
	private String educationinfo;// 教育情况
	private String housesafe;// 住房安全
	private int housecount;// 家庭人口
	private String phone;// 联系电话
	private String housearea;// 住房面积
	private String iselectricity;// 是否通生活用电
	private String watertrouble;// 饮水是否困难
	private String watersafe;// 饮水是否安全
	private String picturePath;// 照片
	private String tpname; // 脱贫情况

	public String pkhsxname;// 贫困户属性
	private String ifooperative;// 是否参与农民专业合作社
	private String productionpower;// 是否通生产用电
	public String housesecurityname;// 房屋安全属性
	private String ifagreetomove;// 是否同意搬迁
	public String housingtypename;// 房屋类型
	private String ifnoroom;// 是否无房

	private double dibao;//
	private double yanglao;//
	private double xinnonghe;//
	private double huanlin;//
	private double shengtailin;//
	private double jiaoyu;//
	private double liangshi;//
	private double yangzhi;//
	private double guopin;//
	private double shumu;//
	private double jiuye;//
	private double linggong;//
	private double juanzeng;//
	private double weiwen;//
	private double qita;//
	// 贫困户-帮扶责任人关系表字段

	private String map_id;// ID
	private String map_householderid;// 户主ID
	private String helpdutypersonid;// 帮扶责任人ID
	// 贫困人表字段
	private String id;// ID
	private String adl_cd; // 行政层级代码
	//
	private String p_householderid;// 户主ID
	private String nationid;// 民族ID,字典表
	private int sex;// 性别
	private String familyrelationid;// 与户主关系ID,字典表
	private String personname;// 成员姓名
	private String culturelevelid;// 文化程度ID,字典表
	private String inschoolstatusid;// 在校生状况ID,字典表
	private String birthday;// 出生日期
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

	private String orders;
	private Integer page;
	private Integer rows;
	private String householdername;// 关联查询出户主姓名

	// 就业情况和收入
	private int ifhasjob;
	private String job_householderid;
	private String sexName;// 性别
	private String havejob;// 有无家人就业

	private String job_poorpeopleid;
	private double total_income;// 总收入

	private int intpoor;// 參加培训
	private String ifpeixun;// 是否培训
	// 筛选条件
	private String income_start;
	private String income_end;

	private String location;// 地址
	private String groupname;// 组名称

	private List<DutyPerson> dutyPerson;
	private int jyrs;
	
	private String tp_year;// 脱贫年度-------------------
	private String familyinfo;// 脱贫年度-------------------
	private String poorfamilypropert;// 贫困户属性-------------------
	private String year_income_perp;// 人均纯收入-------------------

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

	public String getPovertystatusid() {
		return povertystatusid;
	}

	public void setPovertystatusid(String povertystatusid) {
		this.povertystatusid = povertystatusid;
	}

	public String getHelpplan() {
		return helpplan;
	}

	public void setHelpplan(String helpplan) {
		this.helpplan = helpplan;
	}

	public String getPoorreason() {
		return poorreason;
	}

	public void setPoorreason(String poorreason) {
		this.poorreason = poorreason;
	}

	public double getWholeincome() {
		return wholeincome;
	}

	public void setWholeincome(double wholeincome) {
		this.wholeincome = wholeincome;
	}

	public String getOfficeinsurance() {
		return officeinsurance;
	}

	public void setOfficeinsurance(String officeinsurance) {
		this.officeinsurance = officeinsurance;
	}

	public String getEducationinfo() {
		return educationinfo;
	}

	public void setEducationinfo(String educationinfo) {
		this.educationinfo = educationinfo;
	}

	public String getHousesafe() {
		return housesafe;
	}

	public void setHousesafe(String housesafe) {
		this.housesafe = housesafe;
	}

	public int getHousecount() {
		return housecount;
	}

	public void setHousecount(int housecount) {
		this.housecount = housecount;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHousearea() {
		return housearea;
	}

	public void setHousearea(String housearea) {
		this.housearea = housearea;
	}

	public String getIselectricity() {
		return iselectricity;
	}

	public void setIselectricity(String iselectricity) {
		this.iselectricity = iselectricity;
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

	public String getPersonname() {
		return personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getJob_householderid() {
		return job_householderid;
	}

	public void setJob_householderid(String job_householderid) {
		this.job_householderid = job_householderid;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public double getDibao() {
		return dibao;
	}

	public void setDibao(double dibao) {
		this.dibao = dibao;
	}

	public double getYanglao() {
		return yanglao;
	}

	public void setYanglao(double yanglao) {
		this.yanglao = yanglao;
	}

	public double getXinnonghe() {
		return xinnonghe;
	}

	public void setXinnonghe(double xinnonghe) {
		this.xinnonghe = xinnonghe;
	}

	public double getHuanlin() {
		return huanlin;
	}

	public void setHuanlin(double huanlin) {
		this.huanlin = huanlin;
	}

	public double getShengtailin() {
		return shengtailin;
	}

	public void setShengtailin(double shengtailin) {
		this.shengtailin = shengtailin;
	}

	public double getJiaoyu() {
		return jiaoyu;
	}

	public void setJiaoyu(double jiaoyu) {
		this.jiaoyu = jiaoyu;
	}

	public double getLiangshi() {
		return liangshi;
	}

	public void setLiangshi(double liangshi) {
		this.liangshi = liangshi;
	}

	public double getYangzhi() {
		return yangzhi;
	}

	public void setYangzhi(double yangzhi) {
		this.yangzhi = yangzhi;
	}

	public double getGuopin() {
		return guopin;
	}

	public void setGuopin(double guopin) {
		this.guopin = guopin;
	}

	public double getShumu() {
		return shumu;
	}

	public void setShumu(double shumu) {
		this.shumu = shumu;
	}

	public double getJiuye() {
		return jiuye;
	}

	public void setJiuye(double jiuye) {
		this.jiuye = jiuye;
	}

	public double getLinggong() {
		return linggong;
	}

	public void setLinggong(double linggong) {
		this.linggong = linggong;
	}

	public double getJuanzeng() {
		return juanzeng;
	}

	public void setJuanzeng(double juanzeng) {
		this.juanzeng = juanzeng;
	}

	public double getWeiwen() {
		return weiwen;
	}

	public void setWeiwen(double weiwen) {
		this.weiwen = weiwen;
	}

	public double getQita() {
		return qita;
	}

	public void setQita(double qita) {
		this.qita = qita;
	}

	public String getJob_poorpeopleid() {
		return job_poorpeopleid;
	}

	public void setJob_poorpeopleid(String job_poorpeopleid) {
		this.job_poorpeopleid = job_poorpeopleid;
	}

	public double getTotal_income() {
		return total_income;
	}

	public void setTotal_income(double total_income) {
		this.total_income = total_income;
	}

	public String getIncome_start() {
		return income_start;
	}

	public void setIncome_start(String income_start) {
		this.income_start = income_start;
	}

	public String getIncome_end() {
		return income_end;
	}

	public void setIncome_end(String income_end) {
		this.income_end = income_end;
	}

	public int getJyrs() {
		return jyrs;
	}

	public void setJyrs(int jyrs) {
		this.jyrs = jyrs;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getHouseholdername() {
		return householdername;
	}

	public void setHouseholdername(String householdername) {
		this.householdername = householdername;
	}

	public int getIfhasjob() {
		return ifhasjob;
	}

	public void setIfhasjob(int ifhasjob) {
		this.ifhasjob = ifhasjob;
	}

	public int getIfxnh() {
		return ifxnh;
	}

	public void setIfxnh(int ifxnh) {
		this.ifxnh = ifxnh;
	}

	public int getIfxnbx() {
		return ifxnbx;
	}

	public void setIfxnbx(int ifxnbx) {
		this.ifxnbx = ifxnbx;
	}

	public int getIfjbbx() {
		return ifjbbx;
	}

	public void setIfjbbx(int ifjbbx) {
		this.ifjbbx = ifjbbx;
	}

	public int getIfdbbx() {
		return ifdbbx;
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
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMap_id() {
		return map_id;
	}

	public void setMap_id(String map_id) {
		this.map_id = map_id;
	}

	public String getMap_householderid() {
		return map_householderid;
	}

	public void setMap_householderid(String map_householderid) {
		this.map_householderid = map_householderid;
	}

	public String getHelpdutypersonid() {
		return helpdutypersonid;
	}

	public void setHelpdutypersonid(String helpdutypersonid) {
		this.helpdutypersonid = helpdutypersonid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getP_householderid() {
		return p_householderid;
	}

	public void setP_householderid(String p_householderid) {
		this.p_householderid = p_householderid;
	}

	public String getNationid() {
		return nationid;
	}

	public void setNationid(String nationid) {
		this.nationid = nationid;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getFamilyrelationid() {
		return familyrelationid;
	}

	public void setFamilyrelationid(String familyrelationid) {
		this.familyrelationid = familyrelationid;
	}

	public String getCulturelevelid() {
		return culturelevelid;
	}

	public void setCulturelevelid(String culturelevelid) {
		this.culturelevelid = culturelevelid;
	}

	public String getInschoolstatusid() {
		return inschoolstatusid;
	}

	public void setInschoolstatusid(String inschoolstatusid) {
		this.inschoolstatusid = inschoolstatusid;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getHavejob() {
		return havejob;
	}

	public void setHavejob(String havejob) {
		this.havejob = havejob;
	}

	public List<DutyPerson> getDutyPerson() {
		return dutyPerson;
	}

	public void setDutyPerson(List<DutyPerson> dutyPerson) {
		this.dutyPerson = dutyPerson;
	}

	public int getIntpoor() {
		return intpoor;
	}

	public void setIntpoor(int intpoor) {
		this.intpoor = intpoor;
	}

	public String getIfpeixun() {
		return ifpeixun;
	}

	public void setIfpeixun(String ifpeixun) {
		this.ifpeixun = ifpeixun;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getTpname() {
		return tpname;
	}

	public void setTpname(String tpname) {
		this.tpname = tpname;
	}

	public String getAdl_cd() {
		return adl_cd;
	}

	public void setAdl_cd(String adl_cd) {
		this.adl_cd = adl_cd;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getIfooperative() {
		return ifooperative;
	}

	public void setIfooperative(String ifooperative) {
		this.ifooperative = ifooperative;
	}

	public String getProductionpower() {
		return productionpower;
	}

	public void setProductionpower(String productionpower) {
		this.productionpower = productionpower;
	}

	public String getIfagreetomove() {
		return ifagreetomove;
	}

	public void setIfagreetomove(String ifagreetomove) {
		this.ifagreetomove = ifagreetomove;
	}

	public String getIfnoroom() {
		return ifnoroom;
	}

	public void setIfnoroom(String ifnoroom) {
		this.ifnoroom = ifnoroom;
	}

	public String getTp_year() {
		return tp_year;
	}

	public void setTp_year(String tp_year) {
		this.tp_year = tp_year;
	}

	public String getPoorfamilypropert() {
		return poorfamilypropert;
	}

	public void setPoorfamilypropert(String poorfamilypropert) {
		this.poorfamilypropert = poorfamilypropert;
	}

	public String getYear_income_perp() {
		return year_income_perp;
	}

	public void setYear_income_perp(String year_income_perp) {
		this.year_income_perp = year_income_perp;
	}
}
