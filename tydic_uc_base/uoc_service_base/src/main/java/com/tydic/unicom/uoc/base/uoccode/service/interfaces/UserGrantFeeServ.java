package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.UserGrantFeePo;

public interface UserGrantFeeServ {

	/**
	 * 创建赠款记录
	 * @param userGrantFeePo
	 * @return
	 * @throws Exception
	 */
	public boolean create(UserGrantFeePo userGrantFeePo) throws Exception;
	
	/**
	 * 更新赠款记录
	 * @param userGrantFeePo
	 * @return
	 * @throws Exception
	 */
	public boolean update(UserGrantFeePo userGrantFeePo) throws Exception;
	
	/**
	 * 查询赠款记录
	 * @param userGrantFeePo
	 * @return
	 * @throws Exception
	 */
	public List<UserGrantFeePo> queryUserGrantFeeList(UserGrantFeePo userGrantFeePo) throws Exception;
}
