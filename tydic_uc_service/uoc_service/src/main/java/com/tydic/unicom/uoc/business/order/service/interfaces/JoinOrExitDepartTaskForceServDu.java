package com.tydic.unicom.uoc.business.order.service.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface JoinOrExitDepartTaskForceServDu {
	
	/**
	 *  UOC0079	加入/退出部门任务组
	 */
	public UocMessage createJoinOrExitDepartTaskForce(String jsession_id,String oper_type,String quit_type)throws Exception;

}
