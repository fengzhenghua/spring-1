package com.tydic.unicom.apc.business.common.vo;

import com.tydic.unicom.webUtil.BaseVo;

/**
 * APC0012-获取可选渠道-数据对象
 * @author ZhangCheng
 * @date 2017-04-14
 */
public class InfoAgentVo extends BaseVo{
	
	private static final long serialVersionUID = 1L;
	
	/** 【必选】验证字符串 */
	private String jsession_id;
	/** 【可选】渠道编码 */
	private String chnl_code;
	/** 【可选】渠道名称 */
	private String chnl_name;
	/** 【可选】区域编码 */
	private String region_id;
	
	@Override
	public String toString() {
		return "InfoAgentBo [jsession_id=" + jsession_id + ", chnl_code=" + chnl_code + ", chnl_name=" + chnl_name
				+ ", region_id=" + region_id + "]";
	}
	
	public String getJsession_id() {
		return jsession_id;
	}
	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
	}
	public String getChnl_code() {
		return chnl_code;
	}
	public void setChnl_code(String chnl_code) {
		this.chnl_code = chnl_code;
	}
	public String getChnl_name() {
		return chnl_name;
	}
	public void setChnl_name(String chnl_name) {
		this.chnl_name = chnl_name;
	}
	public String getRegion_id() {
		return region_id;
	}
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
}
