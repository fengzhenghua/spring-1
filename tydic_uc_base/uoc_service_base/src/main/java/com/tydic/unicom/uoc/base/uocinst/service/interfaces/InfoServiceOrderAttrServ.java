package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderAttrPo;

public interface InfoServiceOrderAttrServ {

	/**
	 * 服务订单属性表
	 * @param infoServiceOrderAttrPo
	 * @return
	 */
	public boolean create(InfoServiceOrderAttrPo infoServiceOrderAttrPo);
	
	public boolean update(InfoServiceOrderAttrPo InfoServiceOrderAttrPo);
	
	public List<InfoServiceOrderAttrPo> getInfoServiceOrderAttrPo(String serv_order_no);
	
	public Boolean addInfoServiceOrderAttr(InfoServiceOrderAttrPo infoServiceOrderAttrPo);
	
	//public List<InfoServiceOrderAttrPo> queryInfoServiceOrderAttrByOfrOrderNo(InfoServiceOrderAttrPo infoServiceOrderAttrPo) throws Exception;
	
	/**
	 * 根据订单号查询服务订单属性表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderAttrPo> queryInfoServiceOrderAttrByOrderNo(InfoServiceOrderAttrPo po) throws Exception;
	
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean  deleteInfoServiceOrderAttrBySaleOrderNo(InfoServiceOrderAttrPo po) throws Exception;
}
