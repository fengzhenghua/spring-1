package com.tydic.unicom.uoc.test.interfaces;

import java.util.Map;

import com.tydic.unicom.webUtil.UocMessage;

public interface YHTestBaseServiceServDu{

	/**
	 * 
	 * @author yuhao 2016年11月25日 下午4:05:49
	 * @Method: testAll 
	 * @Description:  测试个人开发的全部base服务接口
	 * @param 无
	 * @return Message对象，（注：type：成功/失败标志（success/error），content：成功/失败描述信息；arg：成功时返回的数据）
	 */
	public Map<String,Object> testAll(String fileUrl) throws Exception;
	
	/**
	 * 
	 * @author yuhao 2016年11月25日 下午4:05:49
	 * @Method: testProcessHistoryServDu 
	 * @Description:  测试获取流程实例历史信息服务（本服务会对act_hi_procinst,act_hi_taskinst进行查询）
	 * @param processInstanceId（流程实例Id）,orderNo（订单号）
	 * @return Message对象，（注：type：成功/失败标志（success/error），content：成功/失败描述信息；arg：成功时返回的数据）
	 */
	public UocMessage testProcessHistoryServDu();
	
	/**
	 * 
	 * @author yuhao 2016年11月29日 下午4:05:49
	 * @Method: testProcessDefinitionServDu 
	 * @Description:  测试获取流程定义信息服务（本服务会对act_re_procdef进行查询）
	 * @param 无需参数
	 * @return Message对象，（注：type：成功/失败标志（success/error），content：成功/失败描述信息；arg：成功时返回的数据）
	 */
	public UocMessage testProcessDefinitionServDu();
	
	
	public UocMessage testGetIdServDu(String serv_order_no, String order_type,String jsession_id);
	
	public UocMessage testException() throws Exception;
}
