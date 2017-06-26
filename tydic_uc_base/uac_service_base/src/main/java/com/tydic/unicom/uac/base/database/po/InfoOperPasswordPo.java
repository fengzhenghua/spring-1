package com.tydic.unicom.uac.base.database.po;
import com.tydic.unicom.vdsBase.po.BasePo;

/**
 * 
 * <p>
 * </p>
 * @author yangyi 2015年5月18日 
 * @ClassName InfoOperPassword
 * @Description TODO 子工号在不同系统登录对应的密码
 * @version V1.0
 */
public class InfoOperPasswordPo extends BasePo {
	
	private static final long serialVersionUID = 1L;
	
	private String oper_id;
	private String oper_pw;
	private String system;
	
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	public String getOper_pw() {
		return oper_pw;
	}
	public void setOper_pw(String oper_pw) {
		this.oper_pw = oper_pw;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	
}
