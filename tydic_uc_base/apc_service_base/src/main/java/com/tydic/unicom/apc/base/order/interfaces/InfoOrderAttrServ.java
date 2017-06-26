package com.tydic.unicom.apc.base.order.interfaces;

import java.util.List;

import com.tydic.unicom.apc.base.order.po.InfoOrderAttrPo;

public interface InfoOrderAttrServ {

	/**
	 * 向infoOrderAttr插入多条数据
	 * */
	public boolean create(InfoOrderAttrPo infoOrderAttrPo) throws Exception;
	
	/**
	 * 向infoOrderAttr更新数据
	 * */
	public boolean update(InfoOrderAttrPo infoOrderAttrPo) throws Exception;
	
	/**
	 * 根据order_id和attr_id查询数据
	 * */
	public InfoOrderAttrPo queryInfoOrderAttrByAttrIdAndOrderId(InfoOrderAttrPo InfoOrderAttrPo) throws Exception;
	
	/**
	 * 根据attr_id和attr_value查询数据
	 * */
	public InfoOrderAttrPo queryInfoOrderAttrByAttrIdAndAttrValue(InfoOrderAttrPo InfoOrderAttrPo) throws Exception;
	
	/**
	 * 根据order_id查询数据
	 * */
	public List<InfoOrderAttrPo> queryInfoOrderAttrByOrderId(InfoOrderAttrPo InfoOrderAttrPo) throws Exception;
}
