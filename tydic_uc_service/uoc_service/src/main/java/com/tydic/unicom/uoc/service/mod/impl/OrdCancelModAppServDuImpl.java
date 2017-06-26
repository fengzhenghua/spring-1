package com.tydic.unicom.uoc.service.mod.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.OrdCancelModAppPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdCancelModAppServ;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdCancelModAppServDu;
import com.tydic.unicom.uoc.service.mod.vo.OrdCancelModAppVo;
@Service("OrdCancelModAppServDu")
public class OrdCancelModAppServDuImpl implements OrdCancelModAppServDu{

	@Autowired
	private OrdCancelModAppServ ordCancelModAppServ;
	@Override
	public OrdCancelModAppVo queryOrdCancelModApp(OrdCancelModAppVo vo)
			throws Exception {
		OrdCancelModAppPo OrdCancelModApp = new OrdCancelModAppPo();
		BeanUtils.copyProperties(vo, OrdCancelModApp);
		OrdCancelModAppPo po = ordCancelModAppServ.queryOrdCancelModApp(OrdCancelModApp);
		if(po !=  null){
			OrdCancelModAppVo ordCancelModAppVo = new OrdCancelModAppVo();
			BeanUtils.copyProperties(po, ordCancelModAppVo);
			return ordCancelModAppVo;
		}else{
			return null;
		}

	}
	
	@Override
	public List<OrdCancelModAppVo> queryOrdCancelModAppByVo(OrdCancelModAppVo ordCancelModAppVo) throws Exception {
		OrdCancelModAppPo OrdCancelModApp = new OrdCancelModAppPo();
		BeanUtils.copyProperties(ordCancelModAppVo, OrdCancelModApp);
		List<OrdCancelModAppPo> list = ordCancelModAppServ.queryOrdCancelModAppByPo(OrdCancelModApp);
		if(list != null && list.size()>0){
			List<OrdCancelModAppVo> listOut = new ArrayList<OrdCancelModAppVo>();
			for(int i=0;i<list.size();i++){
				OrdCancelModAppVo ordCancelModAppVoOut = new OrdCancelModAppVo();
				BeanUtils.copyProperties(list.get(i), ordCancelModAppVoOut);
				listOut.add(ordCancelModAppVoOut);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

}
