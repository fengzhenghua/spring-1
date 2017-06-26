package com.tydic.unicom.uac.business.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uac.base.database.interfaces.InfoRegionServ;
import com.tydic.unicom.uac.base.database.po.InfoRegionPo;
import com.tydic.unicom.uac.business.common.interfaces.InfoRegionServDu;
import com.tydic.unicom.uac.business.common.vo.InfoRegionVo;

/**
 * 服务实现
 * UAC0009-获取可选地区
 * @author ZhangCheng
 * @date 2017-04-12
 * @version V1.0
 */
public class InfoRegionServDuImpl implements InfoRegionServDu {
	
	private static org.slf4j.Logger loger = LoggerFactory.getLogger(InfoRegionServDuImpl.class);
	
	@Autowired
	private InfoRegionServ infoRegionServ;
	
	/**
	 * UAC0009-获取可选地区
	 */
	@Override
	public List<InfoRegionVo> queryInfoRegions(InfoRegionVo infoRegionVo) {
		
		if(loger.isInfoEnabled()){
			loger.info("[UAC0009]==========>请求参数：{}",infoRegionVo.toString());
		}
		  
		List<InfoRegionVo> infoRegionVos= new ArrayList<InfoRegionVo>();
		InfoRegionPo infoRegionPo = new InfoRegionPo();
		
		BeanUtils.copyProperties(infoRegionVo, infoRegionPo);
		List<InfoRegionPo> infoRegionPos = infoRegionServ.queryInfoRegions(infoRegionPo);
		
		if( infoRegionPos!= null ){
			for(InfoRegionPo infoRegionPoTemp : infoRegionPos){
				InfoRegionVo infoRegionVoTemp = new InfoRegionVo();
				BeanUtils.copyProperties(infoRegionPoTemp, infoRegionVoTemp);
				infoRegionVos.add(infoRegionVoTemp);
			}
		}
		
		if(loger.isInfoEnabled()){
			loger.info("[UAC0009]==========>响应参数：共返回{}条记录。", infoRegionVos.size());
		}
		
		return infoRegionVos;
	}
}
