/**
 * 
 */
package com.tydic.unicom.apc.business.req.vo;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * <p></p>
 * @author liuyz 2014年9月19日 上午11:29:47
 * @ClassName Response
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年9月19日
 * @modify by reason:{方法名}:{原因}
 */
@XStreamAlias("RESPONSE")
public class ResponseVo implements Serializable{
	/** 
    * @Fields serialVersionUID : TODO
    */ 
    private static final long serialVersionUID = 5016550957837587537L;
	private String RSP_CODE;
	private String RSP_DESC;
	
	private String RspType;
	private String RspCode;
	private String RspDesc;
	
	private String msg;
	private String code;
	//YUN-778  GX_20160033_变更业务支持过户_郝龙
	private String resp_code;
	private String resp_desc;

	
    public String getRspType() {
	    return RspType;
    }
    
    public void setRspType(String rspType) {
	    RspType = rspType;
    }
	
    public String getRspCode() {
	    return RspCode;
    }
    
    public void setRspCode(String rspCode) {
	    RspCode = rspCode;
    }
    
    public String getRspDesc() {
	    return RspDesc;
    }
    
    public void setRspDesc(String rspDesc) {
	    RspDesc = rspDesc;
    }
    
	public String getRSP_CODE() {
		return RSP_CODE;
	}
	public void setRSP_CODE(String rSP_CODE) {
		RSP_CODE = rSP_CODE;
	}
	public String getRSP_DESC() {
		return RSP_DESC;
	}
	public void setRSP_DESC(String rSP_DESC) {
		RSP_DESC = rSP_DESC;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResp_code() {
		return resp_code;
	}

	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}

	public String getResp_desc() {
		return resp_desc;
	}

	public void setResp_desc(String resp_desc) {
		this.resp_desc = resp_desc;
	}
	
}
