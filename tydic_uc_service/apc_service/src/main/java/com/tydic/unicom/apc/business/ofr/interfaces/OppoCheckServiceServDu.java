package com.tydic.unicom.apc.business.ofr.interfaces;

import com.tydic.unicom.apc.business.pub.vo.CheckUserInfoVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface OppoCheckServiceServDu {

	/**
	 * （oppo）国政通校验
	 * */
	public UocMessage checkGZT(String certName, String certId,String flag) throws Exception;

	/**
	 * 客户资料校验（一证五户和黑名单）
	 * @return
	 * @throws Exception
	 */
	public UocMessage checkCustInfo(CheckUserInfoVo vo) throws Exception;

	/**
	 * 一证五户，海南，直接http调ess全业务接口
	 */
	public UocMessage queryUserNumber(String oper_no,String certId, String certName) throws Exception;

	/**
	 * 触点 同一身份证开卡数量检验
	 * @param oper_no
	 * @param ap_id
	 * @param cert_no
	 * @return
	 * @throws Exception
	 */
	public UocMessage checkCertNumsForAp(String oper_no, String ap_id, String cert_no) throws Exception;
}
