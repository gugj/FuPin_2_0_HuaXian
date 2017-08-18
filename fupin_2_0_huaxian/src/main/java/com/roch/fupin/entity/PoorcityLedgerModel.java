package com.roch.fupin.entity;

/**
 * 贫困人口统计报表实体
 * @author ZhaoDongShao
 * 2016年6月25日 
 */
public class PoorcityLedgerModel extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String adl_cd;// 行政区划代码
	private int populationnumber;// 总人口
	private int poorhousenm;// 贫困户数
	private int poorpeoplenm;// 贫困人数

	private int poorreson_l;// 基础设施
	private int poorreson_b;// 因病致贫人数
	private int poorreson_c;//  因残致贫人数
	private int poorreson_x;// 因学致贫人数
	private int poorreson_z;// 缺资金致贫人数
	private int poorreson_j;// 缺技术致贫人数
	private int tzof_p;// 2014年已脱贫人数
	private int tzoff_p;// 2015年已脱贫人数
	private int tzox_p;// 2016年已脱贫人数
	private int tzos_p;// 2017年已脱贫人数
	private int ytp_p;// 已脱贫人数
	private int wtp_p;//  未脱贫人数
	private int fp_p;//  返贫人数

	private int tzof_f;//2014已脱贫户数
	private int tzoff_f;//2015已脱贫的贫困户数
	private int tzox_f;//2016已脱贫的贫困户数
	private int tzos_f;//2017已脱贫的贫困户数
	private int ytp_f;//已脱贫户数
	private int wtp_f;//未脱贫户数
	private int fp_f;//返贫户数
	private int familyreson_b;// 因病致贫户数
	private int familyreson_c;// 因残致贫户数
	private int familyreson_x;// 因学致贫户数
	private int familyreson_z;// 缺资金致贫户数
	private int familyreson_j;// 缺技术致贫户数

	private String poorpercent;
	private String adl_nm;

	public int getTzof_f() {
		return tzof_f;
	}

	public void setTzof_f(int tzof_f) {
		this.tzof_f = tzof_f;
	}

	public int getTzoff_f() {
		return tzoff_f;
	}

	public void setTzoff_f(int tzoff_f) {
		this.tzoff_f = tzoff_f;
	}

	public int getTzox_f() {
		return tzox_f;
	}

	public void setTzox_f(int tzox_f) {
		this.tzox_f = tzox_f;
	}

	public int getTzos_f() {
		return tzos_f;
	}

	public void setTzos_f(int tzos_f) {
		this.tzos_f = tzos_f;
	}

	public int getYtp_f() {
		return ytp_f;
	}

	public void setYtp_f(int ytp_f) {
		this.ytp_f = ytp_f;
	}

	public int getWtp_f() {
		return wtp_f;
	}

	public void setWtp_f(int wtp_f) {
		this.wtp_f = wtp_f;
	}

	public int getFp_f() {
		return fp_f;
	}

	public void setFp_f(int fp_f) {
		this.fp_f = fp_f;
	}

	public int getFamilyreson_b() {
		return familyreson_b;
	}

	public void setFamilyreson_b(int familyreson_b) {
		this.familyreson_b = familyreson_b;
	}

	public int getFamilyreson_c() {
		return familyreson_c;
	}

	public void setFamilyreson_c(int familyreson_c) {
		this.familyreson_c = familyreson_c;
	}

	public int getFamilyreson_x() {
		return familyreson_x;
	}

	public void setFamilyreson_x(int familyreson_x) {
		this.familyreson_x = familyreson_x;
	}

	public int getFamilyreson_z() {
		return familyreson_z;
	}

	public void setFamilyreson_z(int familyreson_z) {
		this.familyreson_z = familyreson_z;
	}

	public int getFamilyreson_j() {
		return familyreson_j;
	}

	public void setFamilyreson_j(int familyreson_j) {
		this.familyreson_j = familyreson_j;
	}

	public int getTzof_p() {
		return tzof_p;
	}

	public void setTzof_p(int tzof_p) {
		this.tzof_p = tzof_p;
	}

	public int getTzoff_p() {
		return tzoff_p;
	}

	public void setTzoff_p(int tzoff_p) {
		this.tzoff_p = tzoff_p;
	}

	public int getTzox_p() {
		return tzox_p;
	}

	public void setTzox_p(int tzox_p) {
		this.tzox_p = tzox_p;
	}

	public int getTzos_p() {
		return tzos_p;
	}

	public void setTzos_p(int tzos_p) {
		this.tzos_p = tzos_p;
	}

	public int getYtp_p() {
		return ytp_p;
	}

	public void setYtp_p(int ytp_p) {
		this.ytp_p = ytp_p;
	}

	public int getWtp_p() {
		return wtp_p;
	}

	public void setWtp_p(int wtp_p) {
		this.wtp_p = wtp_p;
	}

	public int getFp_p() {
		return fp_p;
	}

	public void setFp_p(int fp_p) {
		this.fp_p = fp_p;
	}

	// 用于查询
	private int length;
	private String condition;

	public int getPopulationnumber() {
		return populationnumber;
	}

	public void setPopulationnumber(int populationnumber) {
		this.populationnumber = populationnumber;
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

	public String getPoorpercent() {
		return poorpercent;
	}

	public void setPoorpercent(String poorpercent) {
		this.poorpercent = poorpercent;
	}

	public int getPoorreson_b() {
		return poorreson_b;
	}

	public void setPoorreson_b(int poorreson_b) {
		this.poorreson_b = poorreson_b;
	}

	public int getPoorreson_c() {
		return poorreson_c;
	}

	public void setPoorreson_c(int poorreson_c) {
		this.poorreson_c = poorreson_c;
	}

	public int getPoorreson_x() {
		return poorreson_x;
	}

	public void setPoorreson_x(int poorreson_x) {
		this.poorreson_x = poorreson_x;
	}

	public int getPoorreson_l() {
		return poorreson_l;
	}

	public void setPoorreson_l(int poorreson_l) {
		this.poorreson_l = poorreson_l;
	}

	public int getPoorreson_z() {
		return poorreson_z;
	}

	public void setPoorreson_z(int poorreson_z) {
		this.poorreson_z = poorreson_z;
	}

	public int getPoorreson_j() {
		return poorreson_j;
	}

	public void setPoorreson_j(int poorreson_j) {
		this.poorreson_j = poorreson_j;
	}

	public String getAdl_cd() {
		return adl_cd;
	}

	public void setAdl_cd(String adl_cd) {
		this.adl_cd = adl_cd;
	}

	public String getAdl_nm() {
		return adl_nm;
	}

	public void setAdl_nm(String adl_nm) {
		this.adl_nm = adl_nm;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
