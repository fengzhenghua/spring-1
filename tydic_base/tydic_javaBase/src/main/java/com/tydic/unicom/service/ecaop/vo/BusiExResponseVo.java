/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年11月6日 下午2:17:17
 * @Title: BusiExResponseRo.java
 * @Package com.tydic.unicom.service.ecaop.ro
 * @Description: TODO
 */
package com.tydic.unicom.service.ecaop.vo;


/**
 * <p></p>
 * @author yangfei 2014年11月6日 下午2:17:17
 * @ClassName BusiExResponseRo
 * @Description TODO 业务异常 HTTP返回码=560
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年11月6日
 * @modify by reason:{方法名}:{原因}
 */
public class BusiExResponseVo extends BaseResponseVo{
	/** 
    * @Fields serialVersionUID : TODO
    */ 
    private static final long serialVersionUID = 3103954388109521935L;
	/**
	 * 应答编码
	 */
	private String code;
	/**
	 * 返回描述
	 */
	private String detail;
	
    public String getCode() {
    	return code;
    }
	
    public void setCode(String code) {
    	this.code = code;
    }
	
    public String getDetail() {
    	return detail;
    }
	
    public void setDetail(String detail) {
    	this.detail = detail;
    }
	
}
