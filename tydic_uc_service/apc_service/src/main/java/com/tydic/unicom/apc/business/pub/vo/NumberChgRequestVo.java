package com.tydic.unicom.apc.business.pub.vo;

import java.io.Serializable;
import java.util.List;

import com.tydic.unicom.service.ecaop.vo.BaseMsg;
import com.tydic.unicom.service.ecaop.vo.BaseRequestVo;
import com.tydic.unicom.service.ecaop.vo.Para;

/**
 * 
 * <p></p>
 * @author liuyazong 2014年11月5日 下午6:23:27
 * @ClassName NumberChgReq
 * @Description TODO 号码状态变更请求参数
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年11月5日
 * @modify by reason:{方法名}:{原因}
 */
public class NumberChgRequestVo extends BaseRequestVo{
	
	/** 
    * @Fields serialVersionUID : TODO
    */ 
    private static final long serialVersionUID = 6289071605008848437L;
	public Msg msg;
	private String tele_type="";//YUN-172
	private String device_number="";//YUN-172
	public static class Msg extends BaseMsg{
		
		/** 
        * @Fields serialVersionUID : TODO
        */ 
        private static final long serialVersionUID = -949141064301520550L;
		private String serType;//受理类型	1：后付费	2：预付费
		private List<ResourcesInfo> resourcesInfo;//资源信息
		private List<Para> para;
		
		private String is4g; //上海自助终端预占也加个字段
		
		public static class ResourcesInfo implements Serializable{
			
			/** 
            * @Fields serialVersionUID : TODO
            */ 
            private static final long serialVersionUID = 4578723596575652575L;
			private String resourcesType;////资源类型		01：USIM卡		02：手机号码		03：移动电话		04：上网卡		05：上网本
			private String resourcesCode;//资源唯一标识
			private String occupiedFlag;//预占标识		0：不预占		1：预占
			private String occupiedTime;//yyyymmddhh24miss，预占标识为1时此字段必填
			
			//新的预占接口加的字段
			private String keyChangeTag; //0:不修改；1：修改
			private String proKeyMode;   //资源预占关键字类型：0：随机码；1：客户标识（或登录名）；2：订单标识；3: 淘宝序列号
			private String proKey;       //资源预占关键字
			private String snChangeTag;  //0：号码不变更；1：号码变更
			
			
			
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
			
	        public String getOccupiedFlag() {
	        	return occupiedFlag;
	        }
			
	        public void setOccupiedFlag(String occupiedFlag) {
	        	this.occupiedFlag = occupiedFlag;
	        }
			
	        public String getOccupiedTime() {
	        	return occupiedTime;
	        }
			
	        public void setOccupiedTime(String occupiedTime) {
	        	this.occupiedTime = occupiedTime;
	        }

			@Override
            public String toString() {
	            return "ResourcesInfo [resourcesType=" + resourcesType + ", resourcesCode=" + resourcesCode + ", occupiedFlag="
	                    + occupiedFlag + ", occupiedTime=" + occupiedTime + "]";
            }

			public String getKeyChangeTag() {
				return keyChangeTag;
			}

			public void setKeyChangeTag(String keyChangeTag) {
				this.keyChangeTag = keyChangeTag;
			}

			public String getProKeyMode() {
				return proKeyMode;
			}

			public void setProKeyMode(String proKeyMode) {
				this.proKeyMode = proKeyMode;
			}

			public String getProKey() {
				return proKey;
			}

			public void setProKey(String proKey) {
				this.proKey = proKey;
			}

			public String getSnChangeTag() {
				return snChangeTag;
			}

			public void setSnChangeTag(String snChangeTag) {
				this.snChangeTag = snChangeTag;
			}
	        
			
		}
		
	    public String getSerType() {
	    	return serType;
	    }

		
	    public void setSerType(String serType) {
	    	this.serType = serType;
	    }

		
	    public List<ResourcesInfo> getResourcesInfo() {
	    	return resourcesInfo;
	    }

		
	    public void setResourcesInfo(List<ResourcesInfo> resourcesInfo) {
	    	this.resourcesInfo = resourcesInfo;
	    }
		
        public List<Para> getPara() {
        	return para;
        }
		
        public void setPara(List<Para> para) {
        	this.para = para;
        }


		public String getIs4g() {
			return is4g;
		}


		public void setIs4g(String is4g) {
			this.is4g = is4g;
		}


		@Override
        public String toString() {
	        return "Msg [serType=" + serType + ", resourcesInfo=" + resourcesInfo + ", para=" + para + "]";
        }
	
	}
	
    public Msg getMsg() {
	    return msg;
    }
    
    public void setMsg(Msg msg) {
	    this.msg = msg;
    }

	@Override
    public String toString() {
	    return "NumberChgRequestVo [msg=" + msg + "]";
    }

	public String getTele_type() {
		return tele_type;
	}

	public void setTele_type(String tele_type) {
		this.tele_type = tele_type;
	}

	public String getDevice_number() {
		return device_number;
	}

	public void setDevice_number(String device_number) {
		this.device_number = device_number;
	}
    
}
