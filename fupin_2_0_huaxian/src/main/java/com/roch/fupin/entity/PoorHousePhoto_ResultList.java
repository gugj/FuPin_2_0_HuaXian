/**
 * 
 */
package com.roch.fupin.entity;

import java.util.List;

/**
 * 贫困户和贫困村照片ResultList
 * @author ZhaoDongShao
 *  2016年5月18日
 */
public class PoorHousePhoto_ResultList extends BaseResult {

	private static final long serialVersionUID = 1L;

	// 下面三个暂时没用
	private String maplist;
	private String total;
	private String mapjsondata;

	private List<PoorHousePhoto> jsondata;

	/**
	 * 获取List<PoorHousePhoto>类型的jsondata集合
	 */
	public List<PoorHousePhoto> getJsondata() {
		return jsondata;
	}

	/**
	 * 设置List<PoorHousePhoto>类型的jsondata集合
	 */
	public void setJsondata(List<PoorHousePhoto> jsondata) {
		this.jsondata = jsondata;
	}

	public String getMaplist() {
		return maplist;
	}

	public void setMaplist(String maplist) {
		this.maplist = maplist;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getMapjsondata() {
		return mapjsondata;
	}

	public void setMapjsondata(String mapjsondata) {
		this.mapjsondata = mapjsondata;
	}
}
