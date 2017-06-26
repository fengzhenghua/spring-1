package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.GetServOrderNoPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderSimCardPo;

public interface InfoServiceOrderSimCardServ {

	//public List<InfoServiceOrderSimCardPo> queryInfoServiceOrderSimCardByOfrOrderNo(InfoServiceOrderSimCardPo po)throws Exception;
	
	/**
	 * 根据订单号查询服务订单SIM卡信息表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderSimCardPo> queryInfoServiceOrderSimCardByOrderNo(InfoServiceOrderSimCardPo po)throws Exception;
	
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean  deleteInfoServiceOrderSimCardBySaleOrderNo(InfoServiceOrderSimCardPo po)throws Exception;
	/**
	 * 查询全部的sim_id
	 */
	public List<InfoServiceOrderSimCardPo> queryInfoServiceOrderSimCardAllSimId(GetServOrderNoPo po)throws Exception;
}
