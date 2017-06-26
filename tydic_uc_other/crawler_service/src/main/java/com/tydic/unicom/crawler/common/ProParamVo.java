package com.tydic.unicom.crawler.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProParamVo {
	@Value("${malldebufinfo}")
	private String malldebufinfo;
	@Value("${baselink}")
	private String baselink;
	@Value("${getsafetycode}")
	private String getsafetycode;
	@Value("${login}")
	private String login;
	@Value("${mailadd}")
	private String mailadd;
	@Value("${mailaddport}")
	private String mailaddport;
	@Value("${mailuser}")
	private String mailuser;
	@Value("${mailpwd}")
	private String mailpwd;
	@Value("${mailwaittime}")
	private long mailwaittime;
	@Value("${mailintervaltime}")
	private long mailintervaltime;
	@Value("${testwrite:}")
	private String testwrite;
	@Value("${flow}")
	private String flow;
	@Value("${abilityPlatForm.local_url}")
	private String local_url;
	@Value("${abilityPlatForm.new_appkey}")
	private String new_appkey;
	@Value("${abilityPlatForm.secret}")
	private String secret;
	@Value("${abilityPlatForm.local_token}")
	private String local_token;
	@Value("${regionname}")
	private String regionname;
	@Value("${uoccallback}")
	private String uoccallback;
	//是否打开回写商城
	@Value("${opencallback}")
	private boolean opencallback=false;
	
//	@Value("${uoccallback}")
//	private String uoccallback;
	
	
	public String getFlow() {
		return flow;
	}
	public boolean isOpencallback() {
		return opencallback;
	}
	public String getMalldebufinfo() {
		return malldebufinfo;
	}
	public String getBaselink() {
		return baselink;
	}
	public String getGetsafetycode() {
		return getsafetycode;
	}
	public String getLogin() {
		return login;
	}
	public String getMailadd() {
		return mailadd;
	}
	public String getMailaddport() {
		return mailaddport;
	}
	public String getMailuser() {
		return mailuser;
	}
	public String getMailpwd() {
		return mailpwd;
	}
	public long getMailwaittime() {
		return mailwaittime;
	}
	public long getMailintervaltime() {
		return mailintervaltime;
	}
	public String getTestwrite() {
		return testwrite;
	}
	public void setTestwrite(String testwrite) {
		this.testwrite = testwrite;
	}
	public String getLocal_url() {
		return local_url;
	}
	public String getNew_appkey() {
		return new_appkey;
	}
	public String getSecret() {
		return secret;
	}
	public String getLocal_token() {
		return local_token;
	}
	public String getRegionname() {
		return regionname;
	}
	public String getUoccallback() {
		return uoccallback;
	}
	
}
