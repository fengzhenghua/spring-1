package com.tydic.unicom.apc.business.pub.vo;

import java.io.Serializable;
import java.util.List;

import com.tydic.unicom.service.ecaop.annotation.EcaopField;
import com.tydic.unicom.service.ecaop.annotation.EcaopField.EcaopFieldType;
import com.tydic.unicom.service.ecaop.vo.BaseResponseVo;
import com.tydic.unicom.service.ecaop.vo.Para;

/**
 * 
 * <p></p>
 * @author liuyazong 2014年11月5日 下午6:24:12
 * @ClassName NumberChgRsp
 * @Description TODO  号码状态变更返回数据
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年11月5日
 * @modify by reason:{方法名}:{原因}
 */
public class NumberChgResponseVo extends BaseResponseVo{
	
	/** 
    * @Fields serialVersionUID : TODO
    */ 
    private static final long serialVersionUID = 7288113329686579215L;
	@EcaopField(type=EcaopFieldType.LIST,subType=EcaopFieldType.OBJECT,className="com.tydic.unicom.apc.business.pub.vo.NumberChgResponseVo$ResourcesRsp")
	private List<ResourcesRsp> resourcesRsp;//资源信息
	@EcaopField(type=EcaopFieldType.LIST,subType=EcaopFieldType.OBJECT,className="com.tydic.unicom.service.ecaop.vo.Para")
	private List<Para> para;
	
	
	
	
	public static class ResourcesRsp implements Serializable{
		/** 
        * @Fields serialVersionUID : TODO
        */ 
        private static final long serialVersionUID = 2424484403572988385L;
		@EcaopField(type=EcaopFieldType.STRING)
		private String resourcesType;//资源类型：
									//		01：USIM卡
									//		02：手机号码
									//		03：移动电话；
									//		04：上网卡；
									//		05：上网本
		@EcaopField(type=EcaopFieldType.STRING)
		private String resourcesCode;//资源唯一标识
		@EcaopField(type=EcaopFieldType.STRING)
		private String rscStateCode;//资源变更结果编码：
									//		0000 资源可用
									//		0001 资源已被占
									//		0002 无此资源信息
									//		0003 资源不可售
									//		0004 资源状态是非可用
									//		0005 资源归属渠道错误
									//		0006 资源空闲，不能释放
									//		0007 资源已售，不能释放
									//		9999 其它失败原因
		@EcaopField(type=EcaopFieldType.STRING)
		private String rscStateDesc;//资源状态描述
		@EcaopField(type=EcaopFieldType.STRING)
		private String salePrice;//销售价格
		@EcaopField(type=EcaopFieldType.STRING)
		private String reservaPrice;//预存话费金额
		@EcaopField(type=EcaopFieldType.LIST,subType=EcaopFieldType.STRING)
		private List<String> productIds;//资源可选产品ID
		
		@EcaopField(type=EcaopFieldType.STRING)
		private String numId;//号码
		
        public String getResourcesType() {
        	return resourcesType;
        }
		
        public void setResourcesType(String resourcesType) {
        	this.resourcesType = resourcesType;
        }
		
        public String getResourcesCode() {
        	return resourcesCode;
        }
		
        public void setResourcesCode(String resourcesCode) {
        	this.resourcesCode = resourcesCode;
        }
		
        public String getRscStateCode() {
        	return rscStateCode;
        }
		
        public void setRscStateCode(String rscStateCode) {
        	this.rscStateCode = rscStateCode;
        }
		
        public String getRscStateDesc() {
        	return rscStateDesc;
        }
		
        public void setRscStateDesc(String rscStateDesc) {
        	this.rscStateDesc = rscStateDesc;
        }
		
        public String getSalePrice() {
        	return salePrice;
        }
		
        public void setSalePrice(String salePrice) {
        	this.salePrice = salePrice;
        }
		
        public String getReservaPrice() {
        	return reservaPrice;
        }
		
        public void setReservaPrice(String reservaPrice) {
        	this.reservaPrice = reservaPrice;
        }
		
        public List<String> getProductIds() {
        	return productIds;
        }
		
        public void setProductIds(List<String> productIds) {
        	this.productIds = productIds;
        }

		public String getNumId() {
			return numId;
		}

		public void setNumId(String numId) {
			this.numId = numId;
		}

		@Override
        public String toString() {
	        return "ResourcesRsp [resourcesType=" + resourcesType + ", resourcesCode=" + resourcesCode + ", rscStateCode="
	                + rscStateCode + ", rscStateDesc=" + rscStateDesc + ", salePrice=" + salePrice + ", reservaPrice="
	                + reservaPrice + ", productIds=" + productIds + "]";
        }
        
	}
	
	
    public List<ResourcesRsp> getResourcesRsp() {
    	return resourcesRsp;
    }
	
    public void setResourcesRsp(List<ResourcesRsp> resourcesRsp) {
    	this.resourcesRsp = resourcesRsp;
    }
    
    
    public List<Para> getPara() {
	    return para;
    }
    
    
    public void setPara(List<Para> para) {
	    this.para = para;
    }

	@Override
    public String toString() {
	    return "NumberChgResponseVo [resourcesRsp=" + resourcesRsp + ", para=" + para + "]";
    }

}
