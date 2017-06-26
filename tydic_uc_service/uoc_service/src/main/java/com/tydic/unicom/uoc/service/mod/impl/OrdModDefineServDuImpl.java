package com.tydic.unicom.uoc.service.mod.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.OrdModDefinePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModDefineServ;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModDefineServDu;
import com.tydic.unicom.uoc.service.mod.vo.OrdModDefineVo;
@Service("OrdModDefineServDu")
public class OrdModDefineServDuImpl implements OrdModDefineServDu{

	@Autowired
	private OrdModDefineServ ordModDefineServ;

	@Override
	public boolean create(OrdModDefineVo ordModDefineVo) throws Exception{
		OrdModDefinePo ordPo = new OrdModDefinePo();
		BeanUtils.copyProperties(ordModDefineVo, ordPo);
		boolean res = ordModDefineServ.create(ordPo);
		return res;
	}

	@Override
	public boolean update(OrdModDefineVo ordModDefineVo) throws Exception{
		OrdModDefinePo ordPo = new OrdModDefinePo();
		BeanUtils.copyProperties(ordModDefineVo, ordPo);
		boolean res = ordModDefineServ.update(ordPo);
		return res;
	}

	@Override
	public boolean delete(OrdModDefineVo ordModDefineVo) throws Exception{
		OrdModDefinePo ordPo = new OrdModDefinePo();
		BeanUtils.copyProperties(ordModDefineVo, ordPo);
		boolean res = ordModDefineServ.delete(ordPo);
		return res;

	}


	@Override
	public int queryOrdModDefineListCount(OrdModDefineVo ordModDefineVo)
			throws Exception {
		OrdModDefinePo ordPo = new OrdModDefinePo();
		BeanUtils.copyProperties(ordModDefineVo, ordPo);
		int res = ordModDefineServ.queryOrdModDefineListCount(ordPo);
		return res;
	}

	@Override
	public List<OrdModDefineVo> queryOrdModDefineList(
			OrdModDefineVo ordModDefineVo, int pageNo, int pageSize)
			throws Exception {
		
		OrdModDefinePo ordPo = new OrdModDefinePo();
		BeanUtils.copyProperties(ordModDefineVo, ordPo);
		List<OrdModDefineVo> listVo = new ArrayList<OrdModDefineVo>();
		List<OrdModDefinePo> listPo =ordModDefineServ.queryOrdModDefineList(ordPo, pageNo, pageSize);
		if(listPo != null && listPo.size()>0){
			for(OrdModDefinePo po : listPo){
				OrdModDefineVo ordVo = new OrdModDefineVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}

	@Override
	public List<OrdModDefineVo> queryOrdModDefineAll() throws Exception {
		List<OrdModDefinePo> list = ordModDefineServ.queryOrdModDefineAll();
		if(list != null && list.size()>0){
			List<OrdModDefineVo> listOut = new ArrayList<OrdModDefineVo>();
			for(int i=0;i<list.size();i++){
				OrdModDefineVo ordModDefineVo = new OrdModDefineVo();
				BeanUtils.copyProperties(list.get(i), ordModDefineVo);
				listOut.add(ordModDefineVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}
}
