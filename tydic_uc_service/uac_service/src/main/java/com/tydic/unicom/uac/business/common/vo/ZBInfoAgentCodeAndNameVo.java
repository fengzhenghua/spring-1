package com.tydic.unicom.uac.business.common.vo;

import com.tydic.unicom.webUtil.BaseVo;

/**
 * UAC0008-获取可选渠道
 * 响应对象
 * @author ChangComputer
 * @date 2017-04-13
 */
public class ZBInfoAgentCodeAndNameVo extends BaseVo{
	private static final long serialVersionUID = 1L;
	/** 渠道编码 */
	private String chnl_code;
	/** 渠道名称 */
	private String chnl_name;
	
	@Override
	public String toString() {
		return "ZBInfoAgentCodeAndNameVo [chnl_code=" + chnl_code + ", chnl_name=" + chnl_name + "]";
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
	

}
