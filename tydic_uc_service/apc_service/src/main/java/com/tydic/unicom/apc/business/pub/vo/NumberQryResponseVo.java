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
 * @author liuyazong 2014年11月5日 下午5:52:25
 * @ClassName NumberQryRsp
 * @Description TODO  号码查询返回数据
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年11月5日
 * @modify by reason:{方法名}:{原因}
 */
public class NumberQryResponseVo extends BaseResponseVo {
	
	/** 
    * @Fields serialVersionUID : TODO
    */ 
    private static final long serialVersionUID = 8049317397925897104L;

	@EcaopField(type=EcaopFieldType.LIST,subType=EcaopFieldType.OBJECT,className="com.tydic.unicom.apc.business.pub.vo.NumberQryResponseVo$NumInfo")
	private List<NumInfo> numInfo;//号码列表（最多返回200条记录）
	
	@EcaopField(type=EcaopFieldType.LIST,subType=EcaopFieldType.OBJECT,className="com.tydic.unicom.service.ecaop.vo.Para")
	private List<Para> para;
	
	public static class NumInfo implements Serializable{
		
		/** 
        * @Fields serialVersionUID : TODO
        */ 
        private static final long serialVersionUID = -4591724871202484867L;
        @EcaopField(type=EcaopFieldType.STRING)
        private String numId; //号码
        @EcaopField(type=EcaopFieldType.STRING)
		private String numMemo; //号码说明
        @EcaopField(type=EcaopFieldType.STRING)
		private String numPreFee; //靓号预存单位分
        @EcaopField(type=EcaopFieldType.STRING)
		private String advancePay; //靓号预存单位分
        @EcaopField(type=EcaopFieldType.STRING)
		private String numLevel; //套餐档、等级金额
        @EcaopField(type=EcaopFieldType.STRING)
        private String lowCostPro;//月最低消费
        @EcaopField(type=EcaopFieldType.STRING)
		private String numTime; //在网时长，单位：月
        @EcaopField(type=EcaopFieldType.STRING)
		private String classId; //号码等级
        @EcaopField(type=EcaopFieldType.STRING)
		private String timeDurPro; //协议时长，值为00000时表示无期限
        private String numID;
		
        public String getNumID() {
			return numID;
		}

		public void setNumID(String numID) {
			this.numID = numID;
		}

		public String getNumId() {
			if( numId==null ){
				numId = getNumID();
			}
        	return numId;
        }
		
        public void setNumId(String numId) {
        	this.numId = numId;
        }
		
        public String getNumMemo() {
        	return numMemo;
        }
		
        public void setNumMemo(String numMemo) {
        	this.numMemo = numMemo;
        }
		
        public String getNumPreFee() {
        	return numPreFee;
        }
		
        public void setNumPreFee(String numPreFee) {
        	this.numPreFee = numPreFee;
        }
		
        public String getNumLevel() {
        	return numLevel;
        }
		
        public void setNumLevel(String numLevel) {
        	this.numLevel = numLevel;
        }
		
        public String getNumTime() {
        	return numTime;
        }
		
        public void setNumTime(String numTime) {
        	this.numTime = numTime;
        }

		public String getTimeDurPro() {
			return timeDurPro;
		}

		public void setTimeDurPro(String timeDurPro) {
			this.timeDurPro = timeDurPro;
		}

		@Override
		public String toString() {
			return "NumInfo [numId=" + numId + ", numMemo=" + numMemo
					+ ", numPreFee=" + numPreFee + ", advancePay=" + advancePay
					+ ", numLevel=" + numLevel + ", lowCostPro=" + lowCostPro
					+ ", numTime=" + numTime + ", classId=" + classId
					+ ", timeDurPro=" + timeDurPro + ", numID=" + numID
					+ ", toString()=" + super.toString() + "]";
		}

		public String getAdvancePay() {
			return advancePay;
		}

		public void setAdvancePay(String advancePay) {
			this.advancePay = advancePay;
		}

		public String getLowCostPro() {
			return lowCostPro;
		}

		public void setLowCostPro(String lowCostPro) {
			this.lowCostPro = lowCostPro;
		}

		public String getClassId() {
			return classId;
		}

		public void setClassId(String classId) {
			this.classId = classId;
		}
        
	}

	
    public List<NumInfo> getNumInfo() {
    	return numInfo;
    }

	
    public void setNumInfo(List<NumInfo> numInfo) {
    	this.numInfo = numInfo;
    }


	
    public List<Para> getPara() {
    	return para;
    }


	
    public void setPara(List<Para> para) {
    	this.para = para;
    }


	@Override
    public String toString() {
	    return "NumberQryResponseVo [numInfo=" + numInfo + ", para=" + para + "]";
    }
    
}
