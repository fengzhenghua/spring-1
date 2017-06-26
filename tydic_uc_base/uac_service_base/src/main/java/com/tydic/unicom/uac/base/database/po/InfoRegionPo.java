package com.tydic.unicom.uac.base.database.po;

import com.tydic.unicom.vdsBase.annotation.filter;
import com.tydic.unicom.vdsBase.po.BasePo;

/**
 * ZB_INFO_REGION-地区信息PO
 * @author ZhangCheng
 * @date 2017-04-12
 * @version V1.0
 */
public class InfoRegionPo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	/** 地区标识 */
	@filter
	private String region_id;
	/** 地区名称 */
	@filter
	private String region_name;
	/** 父级地区编码 */
	@filter
	private String parent_region_id;
	/** 地区级别 */
	@filter
	private String region_level;
	
	@Override
	public String toString() {
		return "InfoRegionVo [region_id=" + region_id + ", region_name=" + region_name + ", parent_region_id="
				+ parent_region_id + ", region_level=" + region_level + "]";
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
