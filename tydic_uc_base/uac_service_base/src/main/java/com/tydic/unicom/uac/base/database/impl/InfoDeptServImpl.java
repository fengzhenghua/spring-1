package com.tydic.unicom.uac.base.database.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uac.base.database.interfaces.InfoDeptServ;
import com.tydic.unicom.uac.base.database.po.InfoDeptPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoDeptServ")
public class InfoDeptServImpl extends BaseServImpl<InfoDeptPo> implements InfoDeptServ{

	@Override
	public InfoDeptPo queryInfoDeptByDeptNo(InfoDeptPo infoDept) {
		return get(InfoDeptPo.class, infoDept.getDept_no());
	}

	@Override
	public List<InfoDeptPo> queryInfoDeptByDeptNoAndDeptName(String currDeptNo,String departNo, String departName) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("currDeptNo", currDeptNo);
		map.put("departNo", departNo);
		map.put("departName", departName);
		Condition con = Condition.build("queryInfoDeptByDeptNoAndDeptName").filter(map);
		List<InfoDeptPo> list = query(InfoDeptPo.class,con);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public InfoDeptPo queryInfoDeptByOperNo(String operNo) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("oper_no", operNo);
		Condition con = Condition.build("queryInfoDeptByOperNo").filter(map);
		List<InfoDeptPo> list = query(InfoDeptPo.class,con);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}

	@Override
	public List<InfoDeptPo> queryInfoDeptByDeptType(InfoDeptPo infoDeptPo) throws Exception {
		Condition con = Condition.build("queryInfoDeptByDeptType").filter(infoDeptPo.convertThis2Map());
		List<InfoDeptPo> list = query(InfoDeptPo.class,con);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public List<InfoDeptPo> queryInfoDeptByLocalNet(InfoDeptPo infoDeptPo)
			throws Exception {
		Condition con = Condition.build("queryInfoDeptByLocalNet").filter(infoDeptPo.convertThis2Map());
		List<InfoDeptPo> list = query(InfoDeptPo.class,con);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}
