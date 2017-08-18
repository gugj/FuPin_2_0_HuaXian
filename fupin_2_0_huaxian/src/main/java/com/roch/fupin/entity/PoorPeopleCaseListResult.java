package com.roch.fupin.entity;

import java.util.List;

/**
 * @author ZhaoDongShao
 * 2016年6月27日
 */
public class PoorPeopleCaseListResult extends BaseResult{


	private static final long serialVersionUID = 1L;

	private List<PoorPeopleCase> jsondata;

	public List<PoorPeopleCase> getJsondata() {
		return jsondata;
	}

	public void setJsondata(List<PoorPeopleCase> jsondata) {
		this.jsondata = jsondata;
	}
}
