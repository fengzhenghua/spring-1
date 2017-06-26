package com.tydic.unicom.uoc.service.mod.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.OrdModCheckRulePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModCheckRuleServ;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModCheckRuleServDu;
import com.tydic.unicom.uoc.service.mod.vo.OrdModCheckRuleVo;

@Service("OrdModCheckRuleServDu")
public class OrdModCheckRuleServDuImpl implements OrdModCheckRuleServDu{

	@Autowired
	private OrdModCheckRuleServ ordModCheckRuleServ;

	@Override
	public boolean create(OrdModCheckRuleVo ordModCheckRuleVo) throws Exception {
		OrdModCheckRulePo ordPo = new OrdModCheckRulePo();
		BeanUtils.copyProperties(ordModCheckRuleVo, ordPo);
		boolean res = ordModCheckRuleServ.create(ordPo);
		return res;
	}

	@Override
	public boolean update(OrdModCheckRuleVo ordModCheckRuleVo) throws Exception{
		OrdModCheckRulePo ordPo = new OrdModCheckRulePo();
		BeanUtils.copyProperties(ordModCheckRuleVo, ordPo);
		boolean res = ordModCheckRuleServ.update(ordPo);
		return res;
	}

	@Override
	public boolean delete(OrdModCheckRuleVo ordModCheckRuleVo) throws Exception{
		OrdModCheckRulePo ordPo = new OrdModCheckRulePo();
		BeanUtils.copyProperties(ordModCheckRuleVo, ordPo);
		boolean res = ordModCheckRuleServ.delete(ordPo);
		return res;
	}


	@Override
	public int queryOrdModCheckRuleListCount(OrdModCheckRuleVo ordModCheckRuleVo)
			throws Exception {
		OrdModCheckRulePo ordPo = new OrdModCheckRulePo();
		BeanUtils.copyProperties(ordModCheckRuleVo, ordPo);
		int res=ordModCheckRuleServ.queryOrdModCheckRuleListCount(ordPo);
		return res;
	}

	@Override
	public List<OrdModCheckRuleVo> queryOrdModCheckRuleList(
			OrdModCheckRuleVo ordModCheckRuleVo, int pageNo, int pageSize)
			throws Exception {
		OrdModCheckRulePo ordPo = new OrdModCheckRulePo();
		BeanUtils.copyProperties(ordModCheckRuleVo, ordPo);
		List<OrdModCheckRuleVo> listVo = new ArrayList<OrdModCheckRuleVo>();
		List<OrdModCheckRulePo> listPo = ordModCheckRuleServ.queryOrdModCheckRuleList(ordPo, pageNo, pageSize);
		if(listPo != null && listPo.size()>0){
			for(OrdModCheckRulePo po : listPo){
				OrdModCheckRuleVo ordVo = new OrdModCheckRuleVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}

	@Override
	public List<OrdModCheckRuleVo> queryOrdModCheckRuleAll() throws Exception {
		List<OrdModCheckRulePo> list = ordModCheckRuleServ.queryOrdModCheckRuleAll();
		if(list != null && list.size()>0){
			List<OrdModCheckRuleVo> listOut = new ArrayList<OrdModCheckRuleVo>();
			for(int i=0;i<list.size();i++){
				OrdModCheckRuleVo ordModCheckRuleVo = new OrdModCheckRuleVo();
				BeanUtils.copyProperties(list.get(i), ordModCheckRuleVo);
				listOut.add(ordModCheckRuleVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

	
}
