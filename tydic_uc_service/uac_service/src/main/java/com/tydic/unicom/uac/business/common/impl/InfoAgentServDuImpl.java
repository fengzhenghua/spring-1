package com.tydic.unicom.uac.business.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uac.base.database.interfaces.ZBAgentServ;
import com.tydic.unicom.uac.base.database.po.ZBInfoAgentPo;
import com.tydic.unicom.uac.business.common.interfaces.InfoAgentServDu;
import com.tydic.unicom.uac.business.common.vo.ZBInfoAgentCodeAndNameVo;
import com.tydic.unicom.uac.business.common.vo.ZBInfoAgentVo;

/**
 * 服务实现
 * UAC0008-获取可选渠道
 * @author ZhangCheng
 * @date 2017-04-13
 *
 */
public class InfoAgentServDuImpl implements InfoAgentServDu {
	
	private static final Logger logger = LoggerFactory.getLogger(InfoAgentServDuImpl.class);
	
	@Autowired
	private ZBAgentServ zBAgentServ;

	/**
	 * UAC0008-获取可选渠道
	 */
	@Override
	public List<ZBInfoAgentCodeAndNameVo> queryZbInfoAgents(ZBInfoAgentVo zBInfoAgentVo) {
		
		if(logger.isInfoEnabled()){
			logger.info("[UAC0008]==========>请求参数：{},{},{}",zBInfoAgentVo.getChnl_code(),zBInfoAgentVo.getChnl_name(),zBInfoAgentVo.getManager_area_code());
		}
		
		List<ZBInfoAgentCodeAndNameVo> zBInfoAgentVos = new ArrayList<ZBInfoAgentCodeAndNameVo>();
		ZBInfoAgentPo zBInfoAgentPo = new ZBInfoAgentPo();
		
		BeanUtils.copyProperties(zBInfoAgentVo, zBInfoAgentPo);
		List<ZBInfoAgentPo> zBInfoAgentPos = zBAgentServ.queryZBInfoAgentByChnlCodeOrNameOrRegionId(zBInfoAgentPo);
		
		if( zBInfoAgentPos!= null && zBInfoAgentPos.size()>0 ){
			for(ZBInfoAgentPo zBInfoAgentPoTemp : zBInfoAgentPos){
				ZBInfoAgentCodeAndNameVo zBInfoAgentVoTemp = new ZBInfoAgentCodeAndNameVo();
				BeanUtils.copyProperties(zBInfoAgentPoTemp, zBInfoAgentVoTemp);
				zBInfoAgentVos.add(zBInfoAgentVoTemp);
			}
		}
		
		if(logger.isInfoEnabled()){
			logger.info("[UAC0008]==========>响应参数：共返回{}条记录。",zBInfoAgentVos.size());
		}
		
		return zBInfoAgentVos;
	}
}
