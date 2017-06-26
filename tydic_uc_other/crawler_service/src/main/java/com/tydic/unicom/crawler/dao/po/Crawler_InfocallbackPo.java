package com.tydic.unicom.crawler.dao.po;

public class Crawler_InfocallbackPo extends Crawler_InfoPo {
	private static final long serialVersionUID = 1L;
	
	public static final String CRAWLERTOMALLICC = "S00005";
	public static final String CRAWLERTOMALLLOGISTICS = "S00013";
	
	private String cicb_uuid;
	private String ci_uuid;
	private String cicb_callbackinfo;
	private String modi_time;
	private String tache_code;
	private String cbssnum;
	private String usim;
	private String logistics_no;
	
	
	
	public String getCicb_uuid() {
		return cicb_uuid;
	}
	public void setCicb_uuid(String cicb_uuid) {
		this.cicb_uuid = cicb_uuid;
	}
	public String getCi_uuid() {
		return ci_uuid;
	}
	public void setCi_uuid(String ci_uuid) {
		this.ci_uuid = ci_uuid;
	}
	public String getCicb_callbackinfo() {
		return cicb_callbackinfo;
	}
	public void setCicb_callbackinfo(String cicb_callbackinfo) {
		this.cicb_callbackinfo = cicb_callbackinfo;
	}
	public String getModi_time() {
		return modi_time;
	}
	public void setModi_time(String modi_time) {
		this.modi_time = modi_time;
	}
	public String getTache_code() {
		return tache_code;
	}
	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}
	public String getCbssnum() {
		return cbssnum;
	}
	public void setCbssnum(String cbssnum) {
		this.cbssnum = cbssnum;
	}
	public String getUsim() {
		return usim;
	}
	public void setUsim(String usim) {
		this.usim = usim;
	}
	public String getLogistics_no() {
		return logistics_no;
	}
	public void setLogistics_no(String logistics_no) {
		this.logistics_no = logistics_no;
	}
	
}
