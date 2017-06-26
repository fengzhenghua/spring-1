package com.tydic.unicom.uoc.service.mod.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.OrdModAttrCheckRulePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModAttrCheckRuleServ;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModAttrCheckRuleServDu;
import com.tydic.unicom.uoc.service.mod.vo.OrdModAttrCheckRuleVo;

@Service("OrdModAttrCheckRuleServDu")
public class OrdModAttrCheckRuleServDuImpl implements OrdModAttrCheckRuleServDu{

	@Autowired
	private OrdModAttrCheckRuleServ ordModAttrCheckRuleServ;

	@Override
	public boolean create(OrdModAttrCheckRuleVo ordModAttrCheckRuleVo) throws Exception{
		OrdModAttrCheckRulePo ordPo = new OrdModAttrCheckRulePo();
		BeanUtils.copyProperties(ordModAttrCheckRuleVo, ordPo);
		boolean res = ordModAttrCheckRuleServ.create(ordPo);
		return res;
	}

	@Override
	public boolean update(OrdModAttrCheckRuleVo ordModAttrCheckRuleVo) throws Exception{
		OrdModAttrCheckRulePo ordPo = new OrdModAttrCheckRulePo();
		BeanUtils.copyProperties(ordModAttrCheckRuleVo, ordPo);
		boolean res = ordModAttrCheckRuleServ.update(ordPo);
		return res;
	}

	@Override
	public boolean delete(OrdModAttrCheckRuleVo ordModAttrCheckRuleVo) throws Exception{
		OrdModAttrCheckRulePo ordPo = new OrdModAttrCheckRulePo();
		BeanUtils.copyProperties(ordModAttrCheckRuleVo, ordPo);
		boolean res = ordModAttrCheckRuleServ.delete(ordPo);
		return res;
	}



	@Override
	public int queryOrdModAttrCheckRuleListCount(
			OrdModAttrCheckRuleVo ordModAttrCheckRuleVo) throws Exception {
		OrdModAttrCheckRulePo ordPo = new OrdModAttrCheckRulePo();
		BeanUtils.copyProperties(ordModAttrCheckRuleVo, ordPo);
		int res=ordModAttrCheckRuleServ.queryOrdModAttrCheckRuleListCount(ordPo);
		return res;
	}

	@Override
	public List<OrdModAttrCheckRuleVo> queryOrdModAttrCheckRuleList(
			OrdModAttrCheckRuleVo ordModAttrCheckRuleVo, int pageNo,
			int pageSize) throws Exception {
		OrdModAttrCheckRulePo ordPo = new OrdModAttrCheckRulePo();
		BeanUtils.copyProperties(ordModAttrCheckRuleVo, ordPo);
		List<OrdModAttrCheckRuleVo> listVo= new ArrayList<OrdModAttrCheckRuleVo>();		 
		List<OrdModAttrCheckRulePo> listPo = ordModAttrCheckRuleServ.queryOrdModAttrCheckRuleList(ordPo, pageNo, pageSize);
		if(listPo != null && listPo.size() > 0){
			for(OrdModAttrCheckRulePo po : listPo){
				OrdModAttrCheckRuleVo vo = new OrdModAttrCheckRuleVo();
				BeanUtils.copyProperties(po, vo);
				listVo.add(vo);
			}
			return listVo;
		}else{
			return null;
		}		
		
	}

	@Override
	public List<OrdModAttrCheckRuleVo> queryOrdModAttrCheckRuleAll() throws Exception {
		List<OrdModAttrCheckRulePo> list = ordModAttrCheckRuleServ.queryOrdModAttrCheckRuleAll();
		if(list != null && list.size()>0){
			List<OrdModAttrCheckRuleVo> listOut = new ArrayList<OrdModAttrCheckRuleVo>();
			for(int i=0;i<list.size();i++){
				OrdModAttrCheckRuleVo ordModAttrCheckRuleVo = new OrdModAttrCheckRuleVo();
				BeanUtils.copyProperties(list.get(i), ordModAttrCheckRuleVo);
				listOut.add(ordModAttrCheckRuleVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

}
