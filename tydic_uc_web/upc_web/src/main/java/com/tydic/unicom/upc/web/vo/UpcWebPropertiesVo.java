package com.tydic.unicom.upc.web.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UpcWebPropertiesVo {

	@Value("#{prop.wx_notify_url}")
	private String wx_notify_url;
	
	@Value("#{prop.alipay_notify_url}")
	private String alipay_notify_url;

	public String getWx_notify_url() {
		return wx_notify_url;
	}

	public String getAlipay_notify_url() {
		return alipay_notify_url;
	}
	
	
}
