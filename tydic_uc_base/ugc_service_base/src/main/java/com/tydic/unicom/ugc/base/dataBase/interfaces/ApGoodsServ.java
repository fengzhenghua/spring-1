package com.tydic.unicom.ugc.base.dataBase.interfaces;

import java.util.List;

import com.tydic.unicom.ugc.base.dataBase.po.ApGoodsPo;

public interface ApGoodsServ {

	public List<ApGoodsPo> queryApGoodsByPo(ApGoodsPo apGoodsPo) throws Exception;
	
	public boolean addApGoods(ApGoodsPo apGoodsPo) throws Exception;
	
	public boolean deleteApGoods(ApGoodsPo apGoodsPo) throws Exception;
	
	public boolean updateApGoods(ApGoodsPo apGoodsPo) throws Exception;

}
