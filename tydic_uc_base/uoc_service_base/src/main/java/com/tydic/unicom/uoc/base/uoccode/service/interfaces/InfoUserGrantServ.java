package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.InfoUserGrantPo;

public interface InfoUserGrantServ {

	/**
	 * 向用户赠款表新增记录
	 * @param infoUserGrantPo
	 * @return
	 * @throws Exception
	 */
	public boolean create(InfoUserGrantPo infoUserGrantPo) throws Exception;
	
	/**
	 * 更新用户赠款表记录
	 * @param infoUserGrantPo
	 * @return
	 * @throws Exception
	 */
	public boolean update(InfoUserGrantPo infoUserGrantPo) throws Exception;
	
	/**
	 * 查询用户赠款表记录
	 * @param infoUserGrantPo
	 * @return
	 * @throws Exception
	 */
	public List<InfoUserGrantPo> queryInfoUserGrantList(InfoUserGrantPo infoUserGrantPo) throws Exception;
	
	/**
	 * 查询需要赠款的记录
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<InfoUserGrantPo> queryNeedGrantUser() throws Exception;
}
