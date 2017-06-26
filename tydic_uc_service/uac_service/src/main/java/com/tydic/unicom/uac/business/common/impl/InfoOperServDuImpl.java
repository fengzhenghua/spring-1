package com.tydic.unicom.uac.business.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uac.base.database.interfaces.InfoOperServ;
import com.tydic.unicom.uac.base.database.po.InfoOperPo;
import com.tydic.unicom.uac.business.common.interfaces.InfoOperServDu;
import com.tydic.unicom.uac.business.common.vo.InfoOperVo;

public class InfoOperServDuImpl implements InfoOperServDu{

	private static Logger logger = Logger.getLogger(InfoOperServDuImpl.class);
	
	@Autowired
	private InfoOperServ infoOperServ;
	@Override
	public List<InfoOperVo> queryInfoOperByOperNoAndOperName(String currDeptNo,String operNo, String operName) throws Exception {
		List<InfoOperPo> list = infoOperServ.queryInfoOperByOperNoAndOperName(currDeptNo, operNo, operName);
		if(list != null && list.size()>0){
			List<InfoOperVo> listOut = new ArrayList<InfoOperVo>();
			for(int i=0;i<list.size();i++){
				InfoOperVo infoOperVo = new InfoOperVo();
				BeanUtils.copyProperties(list.get(i), infoOperVo);
				listOut.add(infoOperVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

}
