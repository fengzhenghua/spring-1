package com.tydic.unicom.uoc.service.mod.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.OrdModAppPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModAppServ;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModAppServDu;
import com.tydic.unicom.uoc.service.mod.vo.OrdModAppVo;

@Service("OrdModAppServDu")
public class OrdModAppServDuImpl implements OrdModAppServDu{

	@Autowired
	private OrdModAppServ ordModAppServ;

	@Override
	public boolean create(OrdModAppVo ordModAppVo) throws Exception{
		OrdModAppPo appPo = new OrdModAppPo();
		BeanUtils.copyProperties(ordModAppVo, appPo);
		boolean res = ordModAppServ.create(appPo);
	    return res;
	}

	@Override
	public boolean update(OrdModAppVo ordModAppVo) throws Exception{
		OrdModAppPo appPo = new OrdModAppPo();
		BeanUtils.copyProperties(ordModAppVo, appPo);
		boolean res = ordModAppServ.update(appPo);
	    return res;
	}

	@Override
	public boolean delete(OrdModAppVo ordModAppVo) throws Exception{
		OrdModAppPo appPo = new OrdModAppPo();
		BeanUtils.copyProperties(ordModAppVo, appPo);
		boolean res = ordModAppServ.delete(appPo);
	    return res;
	}


	@Override
	public int queryOrdModAppListCount(OrdModAppVo ordModAppVo)
			throws Exception {
		OrdModAppPo appPo = new OrdModAppPo();		
		BeanUtils.copyProperties(ordModAppVo, appPo);
		int res=ordModAppServ.queryOrdModAppListCount(appPo);
		return res;
	}

	@Override
	public List<OrdModAppVo> queryOrdModAppList(OrdModAppVo ordModAppVo,
			int pageNo, int pageSize) throws Exception {
		OrdModAppPo appPo = new OrdModAppPo();		
		BeanUtils.copyProperties(ordModAppVo, appPo);
		List<OrdModAppVo> listVo=new ArrayList<OrdModAppVo>();
		List<OrdModAppPo> listPo= ordModAppServ.queryOrdModAppList(appPo, pageNo, pageSize);
		if(listPo != null && listPo.size() > 0){
			for(OrdModAppPo po : listPo){
				OrdModAppVo ordVo = new OrdModAppVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}

	@Override
	public List<OrdModAppVo> queryOrdModAppAll() throws Exception {
		List<OrdModAppPo> list = ordModAppServ.queryOrdModAppAll();
		if(list != null && list.size()>0){
			List<OrdModAppVo> listOut = new ArrayList<OrdModAppVo>();
			for(int i=0;i<list.size();i++){
				OrdModAppVo ordModAppVo = new OrdModAppVo();
				BeanUtils.copyProperties(list.get(i), ordModAppVo);
				listOut.add(ordModAppVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

}
