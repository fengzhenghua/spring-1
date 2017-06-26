/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年11月6日 下午3:17:29
 * @Title: Para.java
 * @Package com.tydic.unicom.service.ecaop.ro
 * @Description: TODO
 */
package com.tydic.unicom.service.ecaop.vo;

import java.io.Serializable;

import com.tydic.unicom.service.ecaop.annotation.EcaopField;
import com.tydic.unicom.service.ecaop.annotation.EcaopField.EcaopFieldType;


/**
 * <p></p>
 * @author yangfei 2014年11月6日 下午3:17:29
 * @ClassName Para
 * @Description TODO 预留字段
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年11月6日
 * @modify by reason:{方法名}:{原因}
 */
public class Para implements Serializable {
	/** 
    * @Fields serialVersionUID : TODO
    */ 
    private static final long serialVersionUID = 5911982584666850111L;
	@EcaopField(type=EcaopFieldType.STRING)
	private String paraId;
	@EcaopField(type=EcaopFieldType.STRING)
	private String paraValue;
	
    public String getParaId() {
    	return paraId;
    }
	
    public void setParaId(String paraId) {
    	this.paraId = paraId;
    }
	
    public String getParaValue() {
    	return paraValue;
    }
	
    public void setParaValue(String paraValue) {
    	this.paraValue = paraValue;
    }

	@Override
    public String toString() {
	    return "Para [paraId=" + paraId + ", paraValue=" + paraValue + "]";
    }
	
}
