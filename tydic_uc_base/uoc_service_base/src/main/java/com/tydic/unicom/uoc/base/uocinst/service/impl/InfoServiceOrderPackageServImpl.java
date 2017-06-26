package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPackagePo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderPackageServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoServiceOrderPackageServ")
public class InfoServiceOrderPackageServImpl extends BaseServImpl<InfoServiceOrderPackagePo> implements InfoServiceOrderPackageServ{
	
	@Override
	public List<InfoServiceOrderPackagePo> queryInfoServiceOrderPackageByOrderNo(InfoServiceOrderPackagePo po)throws Exception{
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
		Condition con = Condition. build("queryInfoServiceOrderPackageByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderPackagePo> list = query(InfoServiceOrderPackagePo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	@Override
	public boolean deleteInfoServiceOrderPackageBySaleOrderNo(InfoServiceOrderPackagePo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));		
		Condition condition = Condition.build("deleteInfoServiceOrderPackageBySaleOrderNo").filter(po.convertThis2Map());
		int i=S.get(InfoServiceOrderPackagePo.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}		
	}

//	@Override
//	public List<InfoServiceOrderPackagePo> queryInfoServiceOrderPackageByOfrOrderNo(
//			InfoServiceOrderPackagePo po) throws Exception {
//		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOfr_order_no());
//		po.setPart_month(strMap.get("part_month"));
//		po.setArea_code(strMap.get("area_code"));
//		Condition con = Condition. build("queryInfoServiceOrderPackageByOfrOrderNo").filter(po.convertThis2Map());
//		List<InfoServiceOrderPackagePo> list = query(InfoServiceOrderPackagePo.class,con);      
//		if(list !=null&&list.size()>0){
//			return list ;
//		}
//		else{
//			return null ;
//		}
//	}

}
