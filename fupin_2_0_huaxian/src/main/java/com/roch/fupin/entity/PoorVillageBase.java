package com.roch.fupin.entity;

import java.util.List;

/**
 * 贫困村基本信息
 * @author ZhaoDongShao
 * 2016年5月24日 
 */
public class PoorVillageBase extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private String id;//   ID
	private String povertystatusid;//   脱贫属性ID，字典表，未脱贫，已脱贫，返贫
	private String personname;//   村负责人姓名
	private String persontitle;//   村负责人职务
	private String personphone;//   村负责人电话
	private int populationnumber;//   总人口数
	private String povertypercent;//   贫困发生率
	private String livelihoodinfo;//   生产生活情况
	private String sanitationinfo;//   卫生计划生育情况
	private String informationize;//   基础设施
	private String cultureinfo;//   公共服务
	private String industryinfo;//   产业发展
	private String adl_cd; //行政层级代码
	private String countryname;//城市名称
	private String cityName;
	private String townname;//乡镇名称
	private String villagename;//村名称
	private String location;
	private String groupname;//组名称
	private String tpName;//脱贫状态
	private String companyName; //帮扶单位
	private List<HelpCompany> dutycompany;//帮扶单位列表
	private String picturePath;//图片
	//新添加字段
	private int poorhousenm;//	贫困户数目
	private int poorpeoplenm;//	贫困人数目
	private String secretaryname;//	村支书名称
	private String secretaryphone;// 村支书电话
//	private String firsecretaryname;// 第一书记名称
//	private String firsecretaryphone;// 第一书记电话
	private String popzh;// 总户说

	private String pkcsxname;//贫困村属性
	private String pkctpname;//贫困村脱贫情况
	private String housecount;//贫困人口数
	private String popssmzr;//少数民族人口数
	private String popfnr;//妇女人口数
	private String popdbr;//低保人口数
	private String popdbh;//低保户数
	private String popwbh;//五保户数
	private String popwbr;//五保人口数
	
	private String popcjr;//残疾人口数
	private String popldr;//劳动力人口数
	private String popdwg;//外出务工人口数
	private String povertystatusname ;//贫困村属性

	private String poorreson_b; // 因病致贫人数
	private String poorreson_c; // 因残致贫人数
	private String poorreson_x; // 因学致贫人数
	private String poorreson_z; // 缺资金致贫人数
	private String poorreson_j; // 缺技术致贫人数
	private String tzof_p; // 2014年已脱贫人数
	private String tzoff_p; // 2015年已脱贫人数
	private String tzox_p; // 2016年已脱贫人数
	private String tzos_p; // 2017年已脱贫人数
	private String ytp_p; // 已脱贫人数
	private String wtp_p; // 未脱贫人数
	private String fp_p; // 返贫人数
	private String villageinfo; // 返贫人数

	private String firsecretaryname;// 第一书记
	private String firsecretaryphone;// 第一书记电话
	private String firsecretaryunit;// 第一书记单位
	private String villagecadresname;// 包村干部名称
	private String villagecadresphone;// 包村干部电话
	private String villagecadresunit;// 包村干部单位

	public String getFirsecretaryunit() {
		return firsecretaryunit;
	}

	public void setFirsecretaryunit(String firsecretaryunit) {
		this.firsecretaryunit = firsecretaryunit;
	}

	public String getVillagecadresname() {
		return villagecadresname;
	}

	public void setVillagecadresname(String villagecadresname) {
		this.villagecadresname = villagecadresname;
	}

	public String getVillagecadresphone() {
		return villagecadresphone;
	}

	public void setVillagecadresphone(String villagecadresphone) {
		this.villagecadresphone = villagecadresphone;
	}

	public String getVillagecadresunit() {
		return villagecadresunit;
	}

	public void setVillagecadresunit(String villagecadresunit) {
		this.villagecadresunit = villagecadresunit;
	}

	public String getVillageinfo() {
		return villageinfo;
	}

	public void setVillageinfo(String villageinfo) {
		this.villageinfo = villageinfo;
	}

	public String getPoorreson_b() {
		return poorreson_b;
	}

	public void setPoorreson_b(String poorreson_b) {
		this.poorreson_b = poorreson_b;
	}

	public String getPoorreson_c() {
		return poorreson_c;
	}

	public void setPoorreson_c(String poorreson_c) {
		this.poorreson_c = poorreson_c;
	}

	public String getPoorreson_x() {
		return poorreson_x;
	}

	public void setPoorreson_x(String poorreson_x) {
		this.poorreson_x = poorreson_x;
	}

	public String getPoorreson_z() {
		return poorreson_z;
	}

	public void setPoorreson_z(String poorreson_z) {
		this.poorreson_z = poorreson_z;
	}

	public String getPoorreson_j() {
		return poorreson_j;
	}

	public void setPoorreson_j(String poorreson_j) {
		this.poorreson_j = poorreson_j;
	}

	public String getTzof_p() {
		return tzof_p;
	}

	public void setTzof_p(String tzof_p) {
		this.tzof_p = tzof_p;
	}

	public String getTzoff_p() {
		return tzoff_p;
	}

	public void setTzoff_p(String tzoff_p) {
		this.tzoff_p = tzoff_p;
	}

	public String getTzox_p() {
		return tzox_p;
	}

	public void setTzox_p(String tzox_p) {
		this.tzox_p = tzox_p;
	}

	public String getTzos_p() {
		return tzos_p;
	}

	public void setTzos_p(String tzos_p) {
		this.tzos_p = tzos_p;
	}

	public String getYtp_p() {
		return ytp_p;
	}

	public void setYtp_p(String ytp_p) {
		this.ytp_p = ytp_p;
	}

	public String getWtp_p() {
		return wtp_p;
	}

	public void setWtp_p(String wtp_p) {
		this.wtp_p = wtp_p;
	}

	public String getFp_p() {
		return fp_p;
	}

	public void setFp_p(String fp_p) {
		this.fp_p = fp_p;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPovertystatusid() {
		return povertystatusid;
	}
	public void setPovertystatusid(String povertystatusid) {
		this.povertystatusid = povertystatusid;
	}
	public String getVillagename() {
		return villagename;
	}
	public void setVillagename(String villagename) {
		this.villagename = villagename;
	}
	public String getPersonname() {
		return personname;
	}
	public void setPersonname(String personname) {
		this.personname = personname;
	}
	public String getPersontitle() {
		return persontitle;
	}
	public void setPersontitle(String persontitle) {
		this.persontitle = persontitle;
	}
	public String getPersonphone() {
		return personphone;
	}
	public void setPersonphone(String personphone) {
		this.personphone = personphone;
	}
	public int getPopulationnumber() {
		return populationnumber;
	}
	public void setPopulationnumber(int populationnumber) {
		this.populationnumber = populationnumber;
	}
	public String getPovertypercent() {
		return povertypercent;
	}
	public void setPovertypercent(String povertypercent) {
		this.povertypercent = povertypercent;
	}
	public String getLivelihoodinfo() {
		return livelihoodinfo;
	}
	public void setLivelihoodinfo(String livelihoodinfo) {
		this.livelihoodinfo = livelihoodinfo;
	}
	public String getSanitationinfo() {
		return sanitationinfo;
	}
	public void setSanitationinfo(String sanitationinfo) {
		this.sanitationinfo = sanitationinfo;
	}
	public String getInformationize() {
		return informationize;
	}
	public void setInformationize(String informationize) {
		this.informationize = informationize;
	}
	public String getCultureinfo() {
		return cultureinfo;
	}
	public void setCultureinfo(String cultureinfo) {
		this.cultureinfo = cultureinfo;
	}
	public String getIndustryinfo() {
		return industryinfo;
	}
	public void setIndustryinfo(String industryinfo) {
		this.industryinfo = industryinfo;
	}
	public int getPoorhousenm() {
		return poorhousenm;
	}
	public void setPoorhousenm(int poorhousenm) {
		this.poorhousenm = poorhousenm;
	}
	public int getPoorpeoplenm() {
		return poorpeoplenm;
	}
	public void setPoorpeoplenm(int poorpeoplenm) {
		this.poorpeoplenm = poorpeoplenm;
	}
	public String getSecretaryname() {
		return secretaryname;
	}
	public void setSecretaryname(String secretaryname) {
		this.secretaryname = secretaryname;
	}
	public String getSecretaryphone() {
		return secretaryphone;
	}
	public void setSecretaryphone(String secretaryphone) {
		this.secretaryphone = secretaryphone;
	}
	public String getFirsecretaryname() {
		return firsecretaryname;
	}
	public void setFirsecretaryname(String firsecretaryname) {
		this.firsecretaryname = firsecretaryname;
	}
	public String getFirsecretaryphone() {
		return firsecretaryphone;
	}
	public void setFirsecretaryphone(String firsecretaryphone) {
		this.firsecretaryphone = firsecretaryphone;
	}
	public String getCountryname() {
		return countryname;
	}
	public void setCountryname(String countryname) {
		this.countryname = countryname;
	}
	public String getTpName() {
		return tpName;
	}
	public void setTpName(String tpName) {
		this.tpName = tpName;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public String getAdl_cd() {
		return adl_cd;
	}
	public void setAdl_cd(String adl_cd) {
		this.adl_cd = adl_cd;
	}
	public String getTownName() {
		return townname;
	}
	public void setTownName(String townName) {
		this.townname = townName;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public List<HelpCompany> getDutycompany() {
		return dutycompany;
	}
	public void setDutycompany(List<HelpCompany> dutycompany) {
		this.dutycompany = dutycompany;
	}
	public String getTownname() {
		return townname;
	}
	public void setTownname(String townname) {
		this.townname = townname;
	}
	public String getPopzh() {
		return popzh;
	}
	public void setPopzh(String popzh) {
		this.popzh = popzh;
	}
	public String getPkcsxname() {
		return pkcsxname;
	}
	public void setPkcsxname(String pkcsxname) {
		this.pkcsxname = pkcsxname;
	}
	public String getPkctpname() {
		return pkctpname;
	}
	public void setPkctpname(String pkctpname) {
		this.pkctpname = pkctpname;
	}
	public String getHousecount() {
		return housecount;
	}
	public void setHousecount(String housecount) {
		this.housecount = housecount;
	}
	public String getPopssmzr() {
		return popssmzr;
	}
	public void setPopssmzr(String popssmzr) {
		this.popssmzr = popssmzr;
	}
	public String getPopfnr() {
		return popfnr;
	}
	public void setPopfnr(String popfnr) {
		this.popfnr = popfnr;
	}
	public String getPopdbr() {
		return popdbr;
	}
	public void setPopdbr(String popdbr) {
		this.popdbr = popdbr;
	}
	public String getPopdbh() {
		return popdbh;
	}
	public void setPopdbh(String popdbh) {
		this.popdbh = popdbh;
	}
	public String getPopwbh() {
		return popwbh;
	}
	public void setPopwbh(String popwbh) {
		this.popwbh = popwbh;
	}
	public String getPopwbr() {
		return popwbr;
	}
	public void setPopwbr(String popwbr) {
		this.popwbr = popwbr;
	}
	public String getPopcjr() {
		return popcjr;
	}
	public void setPopcjr(String popcjr) {
		this.popcjr = popcjr;
	}
	public String getPopldr() {
		return popldr;
	}
	public void setPopldr(String popldr) {
		this.popldr = popldr;
	}
	public String getPopdwg() {
		return popdwg;
	}
	public void setPopdwg(String popdwg) {
		this.popdwg = popdwg;
	}
	public String getPovertystatusname() {
		return povertystatusname;
	}
	public void setPovertystatusname(String povertystatusname) {
		this.povertystatusname = povertystatusname;
	}
}
