package com.tydic.unicom.uac.business.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uac.base.database.interfaces.InfoAgentDevelopersServ;
import com.tydic.unicom.uac.base.database.po.InfoAgentDevelopersPo;
import com.tydic.unicom.uac.business.common.interfaces.InfoAgentDevelopersServDu;
import com.tydic.unicom.uac.business.common.vo.InfoAgentDevelopersVo;

public class InfoAgentDevelopersServDuImpl implements InfoAgentDevelopersServDu {
	
	private static final Logger logger = LoggerFactory.getLogger(InfoAgentDevelopersServDuImpl.class);
	
	@Autowired
	private InfoAgentDevelopersServ infoAgentDevelopersServ;
	
	@Override
	public InfoAgentDevelopersVo queryInfoAgentDevelopersByDevCode(
			InfoAgentDevelopersVo infoAgentDevelopersVo) throws Exception{
		
		InfoAgentDevelopersPo infoAgentDevelopers = new InfoAgentDevelopersPo();
		BeanUtils.copyProperties(infoAgentDevelopersVo, infoAgentDevelopers);
		infoAgentDevelopers =infoAgentDevelopersServ.queryInfoAgentDevelopersByDevCode(infoAgentDevelopers);
		BeanUtils.copyProperties(infoAgentDevelopers, infoAgentDevelopersVo);
		return infoAgentDevelopersVo;
	}

	@Override
	public InfoAgentDevelopersVo queryInfoAgentDevelopersByOperNo(
			InfoAgentDevelopersVo infoAgentDevelopersVo) throws Exception{
				
		InfoAgentDevelopersPo infoAgentDevelopers = new InfoAgentDevelopersPo();
		BeanUtils.copyProperties(infoAgentDevelopersVo, infoAgentDevelopers);
		infoAgentDevelopers =infoAgentDevelopersServ.queryInfoAgentDevelopersByOperNo(infoAgentDevelopers);
		BeanUtils.copyProperties(infoAgentDevelopers, infoAgentDevelopersVo);
		return infoAgentDevelopersVo;
			
	}

	@Override
	public List<InfoAgentDevelopersVo> queryInfoAgentDevelopers(
			InfoAgentDevelopersVo infoAgentDevelopersVo) throws Exception {
		
		if(logger.isInfoEnabled()){
			logger.info("UAC0004==========>请求参数：{},{},{},{}",infoAgentDevelopersVo.getDev_code(),
				infoAgentDevelopersVo.getDev_name(),infoAgentDevelopersVo.getChnl_code(),infoAgentDevelopersVo.getRegion());
		}
		
		InfoAgentDevelopersPo infoAgentDevelopers = new InfoAgentDevelopersPo();
		BeanUtils.copyProperties(infoAgentDevelopersVo, infoAgentDevelopers);
		List<InfoAgentDevelopersPo> list =infoAgentDevelopersServ.queryInfoAgentDevelopersByCodeOrName(infoAgentDevelopers);
		List<InfoAgentDevelopersVo> listVo =new ArrayList<InfoAgentDevelopersVo>();
		if(list !=null && list.size()>0){
			for(InfoAgentDevelopersPo po:list){
				InfoAgentDevelopersVo vo =new InfoAgentDevelopersVo();
				BeanUtils.copyProperties(po, vo);
				listVo.add(vo);
			}
		}
		
		if(logger.isInfoEnabled()){
			logger.info("[UAC0004]==========>响应参数：共返回{}条记录。",listVo.size());
		}
		
		return listVo;
	}

}
