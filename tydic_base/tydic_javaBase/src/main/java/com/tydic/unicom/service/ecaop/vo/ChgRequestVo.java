package com.tydic.unicom.service.ecaop.vo;


public class ChgRequestVo extends BaseRequestVo{
	/** 
    * @Fields serialVersionUID : TODO
    */ 
    private static final long serialVersionUID = -8051622737737706808L;
	private Msg msg;
	
    public Msg getMsg() {
    	return msg;
    }
	
    public void setMsg(Msg msg) {
    	this.msg = msg;
    }
	public static class Msg{
		private String operatorId;
		private String province;
		private String city;
		private String district;
		private String channelId;
		private String channelType;
		private String serType;
		private ResourcesInfo resourcesInfo;
		private Para para;
		
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
		
        public String getSerType() {
        	return serType;
        }
		
        public void setSerType(String serType) {
        	this.serType = serType;
        }
		
        public ResourcesInfo getResourcesInfo() {
        	return resourcesInfo;
        }
		
        public void setResourcesInfo(ResourcesInfo resourcesInfo) {
        	this.resourcesInfo = resourcesInfo;
        }
		
        public Para getPara() {
        	return para;
        }
		
        public void setPara(Para para) {
        	this.para = para;
        }
		
	}
	public static class ResourcesInfo{
		private String resourcesType;
		private String ResourcesCode;
		private String OccupiedFlag;
		private String occupiedTime;
		
        public String getResourcesType() {
        	return resourcesType;
        }
		
        public void setResourcesType(String resourcesType) {
        	this.resourcesType = resourcesType;
        }
		
        public String getResourcesCode() {
        	return ResourcesCode;
        }
		
        public void setResourcesCode(String resourcesCode) {
        	ResourcesCode = resourcesCode;
        }
		
        public String getOccupiedFlag() {
        	return OccupiedFlag;
        }
		
        public void setOccupiedFlag(String occupiedFlag) {
        	OccupiedFlag = occupiedFlag;
        }
		
        public String getOccupiedTime() {
        	return occupiedTime;
        }
		
        public void setOccupiedTime(String occupiedTime) {
        	this.occupiedTime = occupiedTime;
        }
		
	}
}
