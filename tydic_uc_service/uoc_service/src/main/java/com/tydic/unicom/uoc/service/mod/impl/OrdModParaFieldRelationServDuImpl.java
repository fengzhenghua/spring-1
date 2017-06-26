package com.tydic.unicom.uoc.service.mod.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.OrdModParaFieldRelationPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModParaFieldRelationServ;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModParaFieldRelationServDu;
import com.tydic.unicom.uoc.service.mod.vo.OrdModParaFieldRelationVo;
@Service("OrdModParaFieldRelationServDu")
public class OrdModParaFieldRelationServDuImpl implements  OrdModParaFieldRelationServDu{

	@Autowired
	private OrdModParaFieldRelationServ ordModParaFieldRelationServ;

	@Override
	public boolean create(OrdModParaFieldRelationVo ordModParaFieldRelationVo)  throws Exception{
		OrdModParaFieldRelationPo ordPo = new OrdModParaFieldRelationPo();
		BeanUtils.copyProperties(ordModParaFieldRelationVo, ordPo);
		boolean res = ordModParaFieldRelationServ.create(ordPo);
		return res;
	}

	@Override
	public boolean update(OrdModParaFieldRelationVo ordModParaFieldRelationVo)  throws Exception{
		OrdModParaFieldRelationPo ordPo = new OrdModParaFieldRelationPo();
		BeanUtils.copyProperties(ordModParaFieldRelationVo, ordPo);
		boolean res = ordModParaFieldRelationServ.update(ordPo);
		return res;
	}

	@Override
	public boolean delete(OrdModParaFieldRelationVo ordModParaFieldRelationVo)  throws Exception{
		OrdModParaFieldRelationPo ordPo = new OrdModParaFieldRelationPo();
		BeanUtils.copyProperties(ordModParaFieldRelationVo, ordPo);
		boolean res = ordModParaFieldRelationServ.delete(ordPo);
		return res;
	}

	@Override
	public int queryOrdModParaFieldRelationListCount(
			OrdModParaFieldRelationVo ordModParaFieldRelationVo)
					throws Exception {
		OrdModParaFieldRelationPo ordPo = new OrdModParaFieldRelationPo();
		BeanUtils.copyProperties(ordModParaFieldRelationVo, ordPo);
		int res=ordModParaFieldRelationServ.queryOrdModParaFieldRelationListCount(ordPo);
		return res;

	}


	@Override
	public List<OrdModParaFieldRelationVo> queryOrdModParaFieldRelationList(
			OrdModParaFieldRelationVo ordModParaFieldRelationVo, int pageNo,
			int pageSize) throws Exception {
		OrdModParaFieldRelationPo ordPo = new OrdModParaFieldRelationPo();
		BeanUtils.copyProperties(ordModParaFieldRelationVo, ordPo);
		List<OrdModParaFieldRelationVo> listVo = new ArrayList<OrdModParaFieldRelationVo>();
		List<OrdModParaFieldRelationPo> listPo =ordModParaFieldRelationServ.queryOrdModParaFieldRelationList(ordPo, pageNo, pageSize);
		if(listPo != null && listPo.size()>0){
			for(OrdModParaFieldRelationPo po : listPo){
				OrdModParaFieldRelationVo ordVo = new OrdModParaFieldRelationVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}

	@Override
	public List<OrdModParaFieldRelationVo> queryOrdModParaFieldRelationAll() throws Exception {
		List<OrdModParaFieldRelationPo> list = ordModParaFieldRelationServ.queryOrdModParaFieldRelationAll();
		if(list != null && list.size()>0){
			List<OrdModParaFieldRelationVo> listOut = new ArrayList<OrdModParaFieldRelationVo>();
			for(int i=0;i<list.size();i++){
				OrdModParaFieldRelationVo ordModParaFieldRelationVo = new OrdModParaFieldRelationVo();
				BeanUtils.copyProperties(list.get(i), ordModParaFieldRelationVo);
				listOut.add(ordModParaFieldRelationVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}
}
