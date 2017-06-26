/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年11月6日 下午2:58:21
 * @Title: ChgRo.java
 * @Package com.tydic.unicom.service.ecaop.ro
 * @Description: TODO
 */
package com.tydic.unicom.service.ecaop.vo;

import com.tydic.unicom.service.ecaop.annotation.EcaopField;
import com.tydic.unicom.service.ecaop.annotation.EcaopField.EcaopFieldType;


/**
 * <p></p>
 * @author yangfei 2014年11月6日 下午2:58:21
 * @ClassName ChgResponseRo
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年11月6日
 * @modify by reason:{方法名}:{原因}
 */
public class ChgResponseVo extends BaseResponseVo{
	/** 
    * @Fields serialVersionUID : TODO
    */ 
    private static final long serialVersionUID = -650345421033860824L;
	@EcaopField(type=EcaopFieldType.OBJECT,className="com.tydic.unicom.service.ecaop.ro.ChgResponseRo$ResourcesRsp")
	private ResourcesRsp resourcesRsp;
	@EcaopField(type=EcaopFieldType.OBJECT)
	private Para para;
	/**
	 * 
	 * <p></p>
	 * @author yangfei 2014年11月6日 下午3:42:12
	 * @ClassName ResourcesRsp
	 * @Description TODO
	 * @version V1.0   
	 * @modificationHistory=========================逻辑或功能性重大变更记录
	 * @modify by user: {修改人} 2014年11月6日
	 * @modify by reason:{方法名}:{原因}
	 */
	public static class ResourcesRsp{
		@EcaopField(type=EcaopFieldType.STRING)
		private String resourcesType;
		@EcaopField(type=EcaopFieldType.STRING)
		private String resourcesCode;
		@EcaopField(type=EcaopFieldType.STRING)
		private String rscStateCode;
		@EcaopField(type=EcaopFieldType.STRING)
		private String rscStateDesc;
		@EcaopField(type=EcaopFieldType.STRING)
		private String salePrice;
		@EcaopField(type=EcaopFieldType.STRING)
		private String ReservaPrice;
		@EcaopField(type=EcaopFieldType.STRING)
		private String ProductId;
		
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
        	return ReservaPrice;
        }
		
        public void setReservaPrice(String reservaPrice) {
        	ReservaPrice = reservaPrice;
        }
		
        public String getProductId() {
        	return ProductId;
        }
		
        public void setProductId(String productId) {
        	ProductId = productId;
        }
		
	}
	
    public ResourcesRsp getResourcesRsp() {
    	return resourcesRsp;
    }
    
    public void setResourcesRsp(ResourcesRsp resourcesRsp) {
    	this.resourcesRsp = resourcesRsp;
    }
	
    public Para getPara() {
    	return para;
    }
	
    public void setPara(Para para) {
    	this.para = para;
    }

}
