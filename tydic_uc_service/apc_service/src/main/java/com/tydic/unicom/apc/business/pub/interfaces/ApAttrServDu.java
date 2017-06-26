package com.tydic.unicom.apc.business.pub.interfaces;

import java.util.List;

import com.tydic.unicom.apc.business.pub.vo.ApAttrVo;
import com.tydic.unicom.webUtil.BusiMessage;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 触点中心服务<br>
 * APC0014-触点属性维护<br>
 * APC0015-触点属性查询<br>
 * 
 * @author ZhangCheng
 * @date 2017-04-24
 */
public interface ApAttrServDu {
	
	/**
	 * APC0014-触点属性维护
	 */
	BusiMessage<?> saveApAttrInfo(ApAttrVo apAttrVo);
	
	/**
	 * APC0015-触点属性查询
	 */
	BusiMessage<List<ApAttrVo>> getApAttrInfo(ApAttrVo apAttrVo);
	
	/**
	 * 触点属性维护-将触点属性写入Redis
	 */
	BusiMessage<?> saveApAttrToRedis(ApAttrVo apAttrVo);
	
	/**
	 * 根据触点id从缓存取微信配置或者触点属性
	 */
	public UocMessage queryApAttrByRedis(String ap_id);
	
}
