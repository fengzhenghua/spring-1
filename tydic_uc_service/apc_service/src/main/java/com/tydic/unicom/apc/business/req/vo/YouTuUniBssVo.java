package com.tydic.unicom.apc.business.req.vo;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("UNI_BSS")
public class YouTuUniBssVo implements Serializable {

	private static final long serialVersionUID = 1492102125358583787L;

	@XStreamAlias("UNI_HEAD")
    private UniHeadVo UNI_HEAD;

    @XStreamAlias("UNI_BODY")
    private YouTuUniBodyVo UNI_BODY;

	public UniHeadVo getUNI_HEAD() {
		return UNI_HEAD;
	}

	public void setUNI_HEAD(UniHeadVo uNI_HEAD) {
		UNI_HEAD = uNI_HEAD;
	}

	public YouTuUniBodyVo getUNI_BODY() {
		return UNI_BODY;
	}

	public void setUNI_BODY(YouTuUniBodyVo uNI_BODY) {
		UNI_BODY = uNI_BODY;
	}
    
    
}
