package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.common.po.SaleOrderInPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoSaleOrderServ")
public class InfoSaleOrderServImpl extends BaseServImpl<InfoSaleOrderPo> implements InfoSaleOrderServ {

	Logger logger = Logger.getLogger(InfoSaleOrderServImpl.class);

	@Override
	public InfoSaleOrderPo getInfoSaleOrderPoBySaleOrderNo(InfoSaleOrderPo po)
			throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		return get(InfoSaleOrderPo.class,po);
	}

	@Override
	public boolean createInfoSaleOrderPo(InfoSaleOrderPo po) throws Exception {
		create(InfoSaleOrderPo.class,po);
		return true;
	}

	@Override
	public boolean updateInfoSaleOrderPo(InfoSaleOrderPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		update(InfoSaleOrderPo.class,po);
		return true;
	}

	@Override
	public InfoSaleOrderPo queryInfoSaleOrderPo(InfoSaleOrderPo po)
			throws Exception {
		if (StringUtils.isNotEmpty(po.getSale_order_no())) {
			Map<String, String> strMap = StrUtil.splitStringFromOrderNo(po.getSale_order_no());
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}
		Condition condition = Condition.build("queryInfoSaleOrderPo").filter(po.convertThis2Map());
		List<InfoSaleOrderPo> list = query(InfoSaleOrderPo.class, condition);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}

	@Override
	public boolean deleteInfoSaleOrderPo(InfoSaleOrderPo po) throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		remove(InfoSaleOrderPo.class,po);
		return true;
	}

	@Override
	public List<InfoSaleOrderPo> queryInfoSaleOrderPoList(SaleOrderInPo po,int pageNo,int pageSize)throws Exception{
		Condition condition = Condition.build("queryInfoSaleOrderPoList").filter(po.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<InfoSaleOrderPo> list = S.get(InfoSaleOrderPo. class).page(condition ,number ,pageSize );
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}


	}

	@Override
	public int queryInfoSaleOrderPoListCount(SaleOrderInPo po)throws Exception{

		Condition condition = Condition.build("queryInfoSaleOrder").filter(po.convertThis2Map());
		List<InfoSaleOrderPo> list = query(InfoSaleOrderPo.class, condition);
		return list.size();

	}

	@Override
	public InfoSaleOrderPo queryInfoSaleOrder(SaleOrderInPo po)throws Exception{
		Condition condition = Condition.build("queryInfoSaleOrder").filter(po.convertThis2Map());
		List<InfoSaleOrderPo> list = query(InfoSaleOrderPo.class, condition);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}

	@Override
	public List<InfoSaleOrderPo> queryInfoSaleOrderByOrderState(InfoSaleOrderPo po)
			throws Exception {
		Condition condition = Condition.build("queryInfoSaleOrderByOrderState").filter(po.convertThis2Map());
		List<InfoSaleOrderPo> list = query(InfoSaleOrderPo.class, condition);
		return list;
	}


}
