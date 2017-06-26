package com.tydic.unicom.uac.business.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uac.base.database.interfaces.InfoMenuServ;
import com.tydic.unicom.uac.base.database.po.InfoAuthorityPo;
import com.tydic.unicom.uac.base.database.po.InfoMenuPo;
import com.tydic.unicom.uac.base.database.po.InfoOperPo;
import com.tydic.unicom.uac.business.common.interfaces.InfoMenuServDu;
import com.tydic.unicom.uac.business.common.vo.InfoAuthorityVo;
import com.tydic.unicom.uac.business.common.vo.InfoMenuVo;
import com.tydic.unicom.uac.business.common.vo.InfoOperVo;

public class InfoMenuServDuImpl implements InfoMenuServDu{

	@Autowired
	private InfoMenuServ infoMenuServ;
	
	@Override
	public List<InfoMenuVo> queryMenuByInfoOper(InfoOperVo infoOperVo,InfoAuthorityVo infoAuthorityVo) throws Exception {
		InfoOperPo infoOperPo=new InfoOperPo();
		BeanUtils.copyProperties(infoOperVo, infoOperPo);
		InfoAuthorityPo infoAuthorityPo=new InfoAuthorityPo();
		BeanUtils.copyProperties(infoAuthorityVo, infoAuthorityPo);
		List<InfoMenuPo> list = infoMenuServ.queryMenuByInfoOper(infoOperPo, infoAuthorityPo);
		if(list != null && list.size()>0){
			List<InfoMenuVo> listOut = new ArrayList<InfoMenuVo>() ;
			for(int i=0;i<list.size();i++){
				InfoMenuVo infoMenuVoTemp = new InfoMenuVo();
				BeanUtils.copyProperties(list.get(i), infoMenuVoTemp);
				listOut.add(infoMenuVoTemp);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

}
