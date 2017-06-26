package com.tydic.unicom.upc.service.code.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.service.sequence.service.interfaces.SequenceServ;
import com.tydic.unicom.upc.base.database.code.interfaces.InfoBusiServ;
import com.tydic.unicom.upc.base.database.po.code.InfoBusiPo;
import com.tydic.unicom.upc.service.code.interfaces.InfoBusiServDu;
import com.tydic.unicom.upc.vo.code.InfoBusiVo;

public class InfoBusiServDuImpl implements InfoBusiServDu {

	private static final Logger logger = Logger.getLogger(InfoBusiServDuImpl.class);
	
	@Autowired
	private InfoBusiServ infoBusiServ;
	
	@Autowired
	private SequenceServ sequenceServ;
	
	@Override
	public InfoBusiVo queryByBusiId(InfoBusiVo infoBusiVo) throws Exception {
		
		//logger.info("序列号："+sequenceServ.genSequence("info_pay_para_attr"));
		
		InfoBusiPo infoBusiPo = new InfoBusiPo();
		BeanUtils.copyProperties(infoBusiVo, infoBusiPo);
		InfoBusiPo po = infoBusiServ.queryByBusiId(infoBusiPo);
		if(po != null){
			InfoBusiVo vo = new InfoBusiVo();
			BeanUtils.copyProperties(po, vo);
			return vo;
		}
		else{
			return null;
		}
	}

	@Override
	public List<InfoBusiVo> queryAllInfoBusi() throws Exception {
		List<InfoBusiPo> poList = infoBusiServ.queryAllInfoBusi();
		
		if(poList != null && poList.size() > 0){
			
			List<InfoBusiVo> voList = new ArrayList<>();
			
			for(InfoBusiPo po : poList){
				InfoBusiVo vo = new InfoBusiVo();
				BeanUtils.copyProperties(po, vo);
				
				voList.add(vo);
			}
			
			return voList;
		}
		
		return null;
	}

}
