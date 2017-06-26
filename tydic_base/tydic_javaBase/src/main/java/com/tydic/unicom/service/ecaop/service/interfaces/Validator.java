package com.tydic.unicom.service.ecaop.service.interfaces;

import java.io.Serializable;


/**
 * 
 * <p></p>
 * @author liuyazong 2014年11月10日 下午2:31:41
 * @ClassName MsgValidator
 * @Description TODO 对请求参数中msg进行非空校验
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年11月10日
 * @modify by reason:{方法名}:{原因}
 */
public interface Validator extends Serializable{
	/** 
    * @Fields serialVersionUID : TODO
    */ 

	/**
	 * 
	 * @author liuyazong 2014年11月10日 上午11:16:45
	 * @Method: validateMsg 
	 * @Description: TODO  仅对操作员信息进行校验,子类重写此方法[并调用super.validateMsg(msg)]对具体的业务数据进行校验
	 * @param msg
	 * @throws Exception 
	 * @throws
	 */
	public Boolean validate(Object object) throws Exception;
	/*{
		if(msg==null){
			throw new Exception("参数[msg]为空");
		}else{
			String operatorId = msg.getOperatorId();
			if(operatorId==null||operatorId.equals("")){
				throw new Exception("参数[操作员ID]为空");
			}
			String province = msg.getProvince();
			if(province==null||province.equals("")){
				throw new Exception("参数[省份]为空");
			}
			String city = msg.getCity();
			if(city==null||city.equals("")){
				throw new Exception("参数[地市]为空");
			}
			String district = msg.getDistrict();
			if(district==null||district.equals("")){
				throw new Exception("参数[区县]为空");
			}
			String channelId = msg.getChannelId();
			if(channelId==null||channelId.equals("")){
				throw new Exception("参数[渠道编码]为空");
			}
			String channelType = msg.getChannelType();
			if(channelType==null||channelType.equals("")){
				throw new Exception("参数[渠道类型]为空");
			}
		}
	}*/
}
