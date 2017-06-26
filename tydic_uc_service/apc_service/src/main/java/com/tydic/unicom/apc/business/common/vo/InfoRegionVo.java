package com.tydic.unicom.apc.business.common.vo;

import com.tydic.unicom.webUtil.BaseVo;

/**
 * APC0013-获取可选地区-数据对象
 * @author ZhangCheng
 * @date 2017-04-14
 */
public class InfoRegionVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	/** 【必选】验证字符串 */
	private String jsession_id;
	/** 【可选】地区编码 */
	private String region_id;
	/** 【可选】地区名称 */
	private String region_name;
	/** 【可选】父级地区编码 */
	private String parent_region_id;
	/** 【可选】地区级别 */
	private String region_level;
	
	@Override
	public String toString() {
		return "InfoRegionVo [jsession_id=" + jsession_id + ", region_id=" + region_id + ", region_name=" + region_name
				+ ", parent_region_id=" + parent_region_id + ", region_level=" + region_level + "]";
	}
	public String getJsession_id() {
		return jsession_id;
	}
	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
	}
	public String getRegion_id() {
		return region_id;
	}
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	public String getParent_region_id() {
		return parent_region_id;
	}
	public void setParent_region_id(String parent_region_id) {
		this.parent_region_id = parent_region_id;
	}
	public String getRegion_level() {
		return region_level;
	}
	public void setRegion_level(String region_level) {
		this.region_level = region_level;
	}
}
