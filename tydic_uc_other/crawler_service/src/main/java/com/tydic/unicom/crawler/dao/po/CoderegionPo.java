package com.tydic.unicom.crawler.dao.po;

/**
 * 区县
 * @author xx
 */
public class CoderegionPo extends BasePo{
	private static final long serialVersionUID = 1L;
	private String region_id;
	private String region_code;
	private String region_name;
	private String upper_region_id;
	private String region_level;
	
	public String getRegion_id() {
		return region_id;
	}
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	public String getRegion_code() {
		return region_code;
	}
	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	public String getUpper_region_id() {
		return upper_region_id;
	}
	public void setUpper_region_id(String upper_region_id) {
		this.upper_region_id = upper_region_id;
	}
	public String getRegion_level() {
		return region_level;
	}
	public void setRegion_level(String region_level) {
		this.region_level = region_level;
	}
}
