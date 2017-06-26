package com.tydic.unicom.ugc.base.dataBase.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.ugc.base.dataBase.interfaces.ApGoodsServ;
import com.tydic.unicom.ugc.base.dataBase.po.ApGoodsPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("ApGoodsServ")
public class ApGoodsServImpl extends BaseServImpl<ApGoodsPo> implements ApGoodsServ {

	private static Logger logger = Logger.getLogger(ApGoodsServImpl.class);

	@Override
	public List<ApGoodsPo> queryApGoodsByPo(ApGoodsPo apGoodsPo) throws Exception {
		logger.info("=========query ap_goods========");
		try {
			Condition condition = Condition.build("queryApGoodsByPo").filter(apGoodsPo.convertThis2Map());
			List<ApGoodsPo> list = query(ApGoodsPo.class, condition);
			if (list != null && list.size() > 0) {
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean addApGoods(ApGoodsPo apGoodsPo) throws Exception {
		try{
			create(ApGoodsPo.class,apGoodsPo);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteApGoods(ApGoodsPo apGoodsPo) throws Exception {
		try{
			remove(ApGoodsPo.class,apGoodsPo);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateApGoods(ApGoodsPo apGoodsPo) throws Exception {
		try{
			update(ApGoodsPo.class,apGoodsPo);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
