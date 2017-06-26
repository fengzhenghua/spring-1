package com.tydic.unicom.apc.business.pub.vo;

import java.io.Serializable;
import java.util.List;

import com.tydic.unicom.service.ecaop.vo.BaseMsg;
import com.tydic.unicom.service.ecaop.vo.BaseRequestVo;
import com.tydic.unicom.service.ecaop.vo.Para;

/**
 * <p></p>
 * @author liuyazong 2014年11月5日 下午5:28:48
 * @ClassName NumberQryReq  
 * @Description TODO 号码查询请求参数
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年11月5日
 * @modify by reason:{方法名}:{原因}
 */
public class NumberQryRequestVo extends BaseRequestVo {
	
	/** 
    * @Fields serialVersionUID : TODO
    */ 
    private static final long serialVersionUID = -5983851688661684403L;
	private Msg msg;
	
	public static class Msg extends BaseMsg{
		
		/** 
        * @Fields serialVersionUID : TODO
        */ 
        private static final long serialVersionUID = -6848737493172324162L;
		private String serType;//受理类型	1：后付费	2：预付费
		private String busType;//业务类型，不传默认都为手机类	1：手机类	2：上网卡类
		private String groupFlag;//是否集团号码标记，不传按员工权限查询号码数据	1：公众	2：集团	3：按员工权限
		private String is3G;//0-2G	1-3G	不传的话，都查询
		private String qryCbss;//0-不查cbss号码状态，1-查，不传默认为0
		private List<QueryPara> queryParas;//选号参数
		
		private List<Para> para;
		
		public static class QueryPara implements Serializable{
			
			/** 
            * @Fields serialVersionUID : TODO
            */ 
            private static final long serialVersionUID = 5504345514178274872L;
			private String queryType;//选号条件	01：随机	02：号段	03：号码关键字	04：靓号等级	05：预付费产品编码
			private String queryPara;//选号参数，当选号条件为04时，选号参数为固定值	0：一类靓号；1：二类靓号；2：三类靓号；3：四类靓号；4：五类靓号；5：普号，一类靓号预存最高，普号最低。
			
			
	        public String getQueryType() {
		        return queryType;
	        }
	        
	        public void setQueryType(String queryType) {
		        this.queryType = queryType;
	        }
	        
	        public String getQueryPara() {
		        return queryPara;
	        }
	        public void setQueryPara(String queryPara) {
		        this.queryPara = queryPara;
	        }

			@Override
            public String toString() {
	            return "QueryPara [queryType=" + queryType + ", queryPara=" + queryPara + "]";
            }
	        
		}
		
		public String getSerType() {
	    	return serType;
	    }
		
	    public void setSerType(String serType) {
	    	this.serType = serType;
	    }
		
	    public String getBusType() {
	    	return busType;
	    }
		
	    public void setBusType(String busType) {
	    	this.busType = busType;
	    }
		
	    public String getGroupFlag() {
	    	return groupFlag;
	    }
		
	    public void setGroupFlag(String groupFlag) {
	    	this.groupFlag = groupFlag;
	    }
		
	    public String getIs3G() {
	    	return is3G;
	    }
		
	    public void setIs3G(String is3g) {
	    	is3G = is3g;
	    }
		
	    public String getQryCbss() {
			return qryCbss;
		}

		public void setQryCbss(String qryCbss) {
			this.qryCbss = qryCbss;
		}

		public List<QueryPara> getQueryParas() {
	    	return queryParas;
	    }
		
	    public void setQueryParas(List<QueryPara> queryParas) {
	    	this.queryParas = queryParas;
	    }

		
        public List<Para> getPara() {
        	return para;
        }

		
        public void setPara(List<Para> para) {
        	this.para = para;
        }

		@Override
        public String toString() {
	        return "Msg [serType=" + serType + ", busType=" + busType + ", groupFlag=" + groupFlag + ", is3G=" + is3G
	                + ", queryParas=" + queryParas + ", para=" + para + "]";
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
	    return "NumberQryRequestVo [msg=" + msg + "]";
    }
	
}
