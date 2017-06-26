package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.GetServOrderNoPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoServiceOrderServ")
public class InfoServiceOrderServImpl extends BaseServImpl<InfoServiceOrderPo> implements InfoServiceOrderServ{


	@Override
	public boolean create(InfoServiceOrderPo infoServiceOrderPo) {
		create(InfoServiceOrderPo.class,infoServiceOrderPo);
		return true;
	}

	@Override
	public boolean update(InfoServiceOrderPo infoServiceOrderPo) {
		Map<String, String> strMap =new HashMap<String,String>();
		try {
			strMap = StrUtil.splitStringFromOrderNo(infoServiceOrderPo.getServ_order_no());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		infoServiceOrderPo.setPart_month(strMap.get("part_month"));
		infoServiceOrderPo.setArea_code(strMap.get("area_code"));
		update(InfoServiceOrderPo.class,infoServiceOrderPo);
		return true;
	}

	@Override
	public boolean delete(InfoServiceOrderPo infoServiceOrderPo) {
		Map<String, String> strMap =new HashMap<String,String>();
		try {
			strMap = StrUtil.splitStringFromOrderNo(infoServiceOrderPo.getServ_order_no());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		infoServiceOrderPo.setPart_month(strMap.get("part_month"));
		infoServiceOrderPo.setArea_code(strMap.get("area_code"));
		remove(InfoServiceOrderPo.class,infoServiceOrderPo);
		return true;
	}

	@Override
	public List<InfoServiceOrderPo> queryInfoServiceOrderByPo(
			InfoServiceOrderPo po) {
		Condition condition = Condition.build("queryInfoServiceOrderByPo").filter(po.convertThis2Map());
		List<InfoServiceOrderPo> list =query(InfoServiceOrderPo.class,condition);
		return (list!=null&&list.size()>0)?list:null;
	}

	@Override
	public InfoServiceOrderPo getInfoServiceOrderByServOrderNo(
			InfoServiceOrderPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getServ_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		return get(InfoServiceOrderPo.class,po);
	}

	@Override
	public List<InfoServiceOrderPo> queryInfoServiceOrderByOrderNo(
			InfoServiceOrderPo po) throws Exception {
		String order_no="";
		if(po.getSale_order_no()!=null){
			order_no=po.getSale_order_no();
		}else if(po.getServ_order_no()!=null){
			order_no=po.getServ_order_no();
		}else if(po.getOfr_order_no()!=null){
			order_no=po.getOfr_order_no();
		}

		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(order_no);
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition condition = Condition.build("queryInfoServiceOrderByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderPo> list =query(InfoServiceOrderPo.class,condition);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public List<InfoServiceOrderPo> queryInfoServiceOrderByOfrOrderNo(
			InfoServiceOrderPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOfr_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition condition = Condition.build("queryInfoServiceOrderByOfrOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderPo> list =query(InfoServiceOrderPo.class,condition);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}
	@Override
	public List<InfoServiceOrderPo> queryInfoServiceOrderBySaleOrderNo(
			InfoServiceOrderPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition condition = Condition.build("queryInfoServiceOrderBySaleOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderPo> list =query(InfoServiceOrderPo.class,condition);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public boolean deleteInfoServiceOrderBySaleOrderNo(InfoServiceOrderPo po) throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition condition = Condition.build("deleteInfoServiceOrderBySaleOrderNo").filter(po.convertThis2Map());
		int i=S.get(InfoServiceOrderPo.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<InfoServiceOrderPo> queryInfoServiceOrderAndSim(
GetServOrderNoPo po) throws Exception {
		// TODO Auto-generated method stub
		/*
		Map<String,Object> strMap=new HashMap<String,Object>();
		strMap.put("area_code", po.getArea_code());
		strMap.put("province_code", po.getProvince_code());
		strMap.put("sim_id", po.getSim_id());
		strMap.put("acc_nbr",po.getAcc_nbr());
		*/
		Condition con = Condition.build("queryInfoServiceOrderAndSim").filter(po.convertThis2Map());
		List<InfoServiceOrderPo> list= query(InfoServiceOrderPo.class,con);
		return (list==null||list.isEmpty())?null:list;
	}

}

