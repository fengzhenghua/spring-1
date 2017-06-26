package com.tydic.unicom.service.ecaop.vo;

import java.io.Serializable;
import java.util.List;

import com.tydic.unicom.service.ecaop.vo.Para;

/**
 * 
 * <p></p>
 * @author liuyazong 2014年11月10日 上午11:30:58
 * @ClassName BaseMsg msg基类，操作员信息
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年11月10日
 * @modify by reason:{方法名}:{原因}
 */
public class BaseMsg implements Serializable{
	
	/** 
    * @Fields serialVersionUID : TODO
    */ 
    private static final long serialVersionUID = -3722194744543365728L;
	private String operatorId;//操作员ID//WBRA03
	private String province;//省分//59
	private String city;//地市//591
	private String district;//区县//592003
	private String channelId;//渠道编码//59b05el
	private String channelType;//渠道类型:参考附录渠道类型编码//2010200
	private String eModeCode;//渠道标示 广西默认为59
//	private List<Para> para;
	
    public String getOperatorId() {
    	return operatorId;
    }
	
    public void setOperatorId(String operatorId) {
    	this.operatorId = operatorId;
    }
	
    public String getProvince() {
    	return province;
    }
	
    public void setProvince(String province) {
    	this.province = province;
    }
	
    public String getCity() {
    	return city;
    }
	
    public void setCity(String city) {
    	this.city = city;
    }
	
    public String getDistrict() {
    	return district;
    }
	
    public void setDistrict(String district) {
    	this.district = district;
    }
	
    public String getChannelId() {
    	return channelId;
    }
	
    public void setChannelId(String channelId) {
    	this.channelId = channelId;
    }
	
    public String getChannelType() {
    	return channelType;
    }
	
    public void setChannelType(String channelType) {
    	this.channelType = channelType;
    }

//    public List<Para> getPara() {
//        return para;
//    }
//    
//    public void setPara(List<Para> para) {
//        this.para = para;
//    }
	@Override
    public String toString() {
	    return "BaseMsg [operatorId=" + operatorId + ", province=" + province + ", city=" + city + ", district=" + district
	            + ", channelId=" + channelId + ", channelType=" + channelType + "]";
    }

	public String geteModeCode() {
		return eModeCode;
	}

	public void seteModeCode(String eModeCode) {
		this.eModeCode = eModeCode;
	}
	
    
}
