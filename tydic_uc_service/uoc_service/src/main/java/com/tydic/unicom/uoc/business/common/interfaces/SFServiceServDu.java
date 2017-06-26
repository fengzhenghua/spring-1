package com.tydic.unicom.uoc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface SFServiceServDu {

	/**获取顺丰物流信息*/
	public UocMessage getLogisticsRouterInfo(String jsession_id,String order_no,String order_no_type,String query_type) throws Exception;

	/**顺丰发货*/
	public UocMessage sendlogisticsInfo(String jsession_id, String serv_order_no, String d_contact, String d_tel, String d_address,
			String j_tel, String j_contact, String j_address, String name, String remark, String flow_type, String deal_code,
			String deal_desc, String deal_system_no, String cod_account, String cod_charge, String insure_charge,String need_return_tracking_no) throws Exception;

	/**顺丰发货(无流程)*/
	public UocMessage sendLogisticsInfoNoProcess(String jsession_id,String serv_order_no,String d_contact,String d_tel,String d_address, String j_tel, String j_contact, String j_address, String name, String remark, String cod_account,
			String cod_charge, String insure_charge,String need_return_tracking_no) throws Exception;
}
