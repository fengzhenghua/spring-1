package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderTerminalPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderTerminalServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoServiceOrderTerminalServ")
public class InfoServiceOrderTerminalServImpl extends BaseServImpl<InfoServiceOrderTerminalPo> implements  InfoServiceOrderTerminalServ{
	
	
	@Override
	public List<InfoServiceOrderTerminalPo> queryInfoServiceOrderTerminalByOrderNo(InfoServiceOrderTerminalPo po)throws Exception{
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
		Condition con = Condition. build("queryInfoServiceOrderTerminalByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderTerminalPo> list = query(InfoServiceOrderTerminalPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	@Override
	public boolean deleteInfoServiceOrderTerminalBySaleOrderNo(InfoServiceOrderTerminalPo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));		
		Condition condition = Condition.build("deleteInfoServiceOrderTerminalBySaleOrderNo").filter(po.convertThis2Map());
		int i=S.get(InfoServiceOrderTerminalPo.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}		
	}

//	@Override
//	public List<InfoServiceOrderTerminalPo> queryInfoServiceOrderTerminalByOfrOrderNo(
//			InfoServiceOrderTerminalPo po) throws Exception {
//		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOfr_order_no());
//		po.setPart_month(strMap.get("part_month"));
//		po.setArea_code(strMap.get("area_code"));
//		Condition con = Condition. build("queryInfoServiceOrderTerminalByOfrOrderNo").filter(po.convertThis2Map());
//		List<InfoServiceOrderTerminalPo> list = query(InfoServiceOrderTerminalPo.class,con);      
//		if(list !=null&&list.size()>0){
//			return list ;
//		}
//		else{
//			return null ;
//		}
//	}

}
