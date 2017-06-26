package com.tydic.unicom.apc.business.req.vo;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("UNI_BODY")
public class YouTuUniBodyVo implements Serializable {

	private static final long serialVersionUID = -4231674934171629604L;

	private String jsonstr;
	private String buInterf;

	public String getJsonstr() {
		return jsonstr;
	}

	public void setJsonstr(String jsonstr) {
		this.jsonstr = jsonstr;
	}

	public String getBuInterf() {
		return buInterf;
	}

	public void setBuInterf(String buInterf) {
		this.buInterf = buInterf;
	}
	
	
}
