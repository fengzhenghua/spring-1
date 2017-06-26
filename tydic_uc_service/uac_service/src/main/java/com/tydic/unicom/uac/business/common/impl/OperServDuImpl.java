package com.tydic.unicom.uac.business.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uac.base.database.interfaces.InfoAgentDevelopersServ;
import com.tydic.unicom.uac.base.database.interfaces.InfoAgentServ;
import com.tydic.unicom.uac.base.database.interfaces.InfoCommendOperServ;
import com.tydic.unicom.uac.base.database.interfaces.InfoDeptServ;
import com.tydic.unicom.uac.base.database.interfaces.InfoMenuServ;
import com.tydic.unicom.uac.base.database.interfaces.InfoOperServ;
import com.tydic.unicom.uac.base.database.interfaces.InfoPagesMobileServ;
import com.tydic.unicom.uac.base.database.interfaces.OperPasswordServ;
import com.tydic.unicom.uac.base.database.interfaces.ZBAgentRelationServ;
import com.tydic.unicom.uac.base.database.interfaces.ZBAgentServ;
import com.tydic.unicom.uac.base.database.po.InfoAgentDevelopersPo;
import com.tydic.unicom.uac.base.database.po.InfoAgentPo;
import com.tydic.unicom.uac.base.database.po.InfoCommendOperPo;
import com.tydic.unicom.uac.base.database.po.InfoDeptPo;
import com.tydic.unicom.uac.base.database.po.InfoMenuPo;
import com.tydic.unicom.uac.base.database.po.InfoOperPasswordPo;
import com.tydic.unicom.uac.base.database.po.InfoOperPo;
import com.tydic.unicom.uac.base.database.po.InfoPagesMobilePo;
import com.tydic.unicom.uac.base.database.po.ZBInfoAgentPo;
import com.tydic.unicom.uac.base.database.po.ZBInfoAgentRelationPo;
import com.tydic.unicom.uac.business.common.interfaces.OperServDu;
import com.tydic.unicom.uac.business.common.vo.InfoAgentDevelopersVo;
import com.tydic.unicom.uac.business.common.vo.InfoAgentVo;
import com.tydic.unicom.uac.business.common.vo.InfoCommendOperVo;
import com.tydic.unicom.uac.business.common.vo.InfoDeptVo;
import com.tydic.unicom.uac.business.common.vo.InfoMenuVo;
import com.tydic.unicom.uac.business.common.vo.InfoOperPasswordVo;
import com.tydic.unicom.uac.business.common.vo.InfoOperVo;
import com.tydic.unicom.uac.business.common.vo.InfoPagesMobileVo;
import com.tydic.unicom.uac.business.common.vo.ZBInfoAgentRelationVo;
import com.tydic.unicom.uac.business.common.vo.ZBInfoAgentVo;
import com.tydic.unicom.uac.service.common.interfaces.BaseOperServDu;
import com.tydic.unicom.uac.service.interfaces.RedisServiceServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class OperServDuImpl implements OperServDu{

	private static Logger logger = Logger.getLogger(OperServDuImpl.class);
	
	@Autowired
	private RedisServiceServDu redisServiceServDu;
	
	@Autowired
	private BaseOperServDu baseOperServDu;
	
	@Autowired
	private InfoOperServ infoOperServ;
	
	@Autowired
	private InfoCommendOperServ infoCommendOperServ;
	
	@Autowired
	private InfoAgentDevelopersServ infoAgentDevelopersServ;
	
	@Autowired
	private InfoDeptServ infoDeptServ;
	
	@Autowired
	private InfoAgentServ infoAgentServ;
	
	@Autowired
	private ZBAgentRelationServ zbAgentRelationServ;
	
	@Autowired
	private ZBAgentServ zbAgentServ;
	
	@Autowired
	private OperPasswordServ operPasswordServ;
	
	@Autowired
	private InfoMenuServ infoMenuServ;
	
	@Autowired
	private InfoPagesMobileServ infoPagesMobileServ;
	
	@Override
	public UocMessage getOperInfoFromRedis(String jsessionId) throws Exception {
		logger.debug("===========获取用户信息(redis)===========");
		UocMessage uocMessage = new UocMessage();		
		uocMessage = redisServiceServDu.queryDataFromRedis(jsessionId);
		return uocMessage;
	}

	@Override
	public InfoOperVo queryInfoOperByLoginCode(String operNo) throws Exception {
		logger.info("===========根据工号获取工号信息=============");
		InfoOperVo resultVo = new InfoOperVo();

		ZBInfoAgentVo zbInfoAgentVo = new ZBInfoAgentVo();
		ZBInfoAgentRelationVo zbInfoAgentRelationVo = new ZBInfoAgentRelationVo();
		InfoAgentVo infoAgentVo = new InfoAgentVo();
		List<InfoOperPasswordVo> infoOperPasswordVos = new  ArrayList<InfoOperPasswordVo>();
		List<InfoMenuVo> infoMenuVos = new  ArrayList<InfoMenuVo>();
		List<InfoPagesMobileVo> infoPagesMobileVos = new  ArrayList<InfoPagesMobileVo>();
		InfoOperPo infoOperPo = new InfoOperPo();
		InfoOperPo resultPo = new InfoOperPo();
		infoOperPo.setOper_no(operNo);
		resultPo = infoOperServ.getInfoOperByLoginCode(infoOperPo);
		
		if(resultPo != null){
			InfoCommendOperPo infoCommendOperPo = new InfoCommendOperPo();
			BeanUtils.copyProperties(resultPo, infoCommendOperPo);
			List<InfoCommendOperPo> infoCommendOperOuts = infoCommendOperServ.queryInfoCommendOperByOperNo(infoCommendOperPo);
			if(infoCommendOperOuts != null && infoCommendOperOuts.size()>0){
				if(infoCommendOperOuts.size()==1){
					InfoCommendOperVo infoCommendOperVo = new InfoCommendOperVo();
					BeanUtils.copyProperties(infoCommendOperOuts.get(0), infoCommendOperVo);
					resultVo.setInfoCommendOperVo(infoCommendOperVo);
					//获得发展人信息
					InfoAgentDevelopersPo InfoAgentDevelopers = new InfoAgentDevelopersPo();
					InfoAgentDevelopers.setDev_code(infoCommendOperOuts.get(0).getCommend_no());
					InfoAgentDevelopersPo developers = infoAgentDevelopersServ.queryInfoAgentDevelopersByDevCode(InfoAgentDevelopers);
					if (developers != null) {
						InfoAgentDevelopersVo infoAgentDevelopersVo = new InfoAgentDevelopersVo();
						BeanUtils.copyProperties(developers, infoAgentDevelopersVo);
						resultVo.setInfoAgentDevelopersVo(infoAgentDevelopersVo);
					}
				}
				else{
					// 获得发展人信息
					InfoAgentDevelopersPo infoAgentDevelopers = new InfoAgentDevelopersPo();
					infoAgentDevelopers.setOper_no(resultPo.getOper_no());
					InfoAgentDevelopersPo developers = infoAgentDevelopersServ.queryInfoAgentDevelopersByOperNo(infoAgentDevelopers);
					if (developers != null) {
						InfoAgentDevelopersVo infoAgentDevelopersVo = new InfoAgentDevelopersVo();
						BeanUtils.copyProperties(developers, infoAgentDevelopersVo);
						resultVo.setInfoAgentDevelopersVo(infoAgentDevelopersVo);
					}
				}
			}
			//部门信息
			InfoDeptPo infoDept = new InfoDeptPo();
			BeanUtils.copyProperties(resultPo, infoDept);
			InfoDeptPo info = infoDeptServ.queryInfoDeptByDeptNo(infoDept);
			if (info != null) {
				InfoDeptVo infoDeptVo = new InfoDeptVo();
				BeanUtils.copyProperties(info, infoDeptVo);
				resultVo.setInfoDeptVo(infoDeptVo);
			}
			InfoAgentPo infoAgent = new InfoAgentPo();
			infoAgent.setDept_no(resultPo.getDept_no());
			List<InfoAgentPo> infoAgents = infoAgentServ.queryInfoAgentByDeptNo(infoAgent);
			if(infoAgents != null && infoAgents.size() > 0){
				BeanUtils.copyProperties(infoAgents.get(0), infoAgentVo);
				ZBInfoAgentRelationPo zbInfoAgentRelation = new ZBInfoAgentRelationPo();
				zbInfoAgentRelation.setAgent_no(infoAgents.get(0).getAgent_no().toString());
				ZBInfoAgentRelationPo zbInfoAgentRelations = zbAgentRelationServ.queryZBInfoAgentRelationByAgentNo(zbInfoAgentRelation);
				if (zbInfoAgentRelations != null){
					BeanUtils.copyProperties(zbInfoAgentRelations, zbInfoAgentRelationVo);
					ZBInfoAgentPo zbInfoAgent = new ZBInfoAgentPo();
					zbInfoAgent.setChnl_code(zbInfoAgentRelations.getChnl_code());
					ZBInfoAgentPo zbInfoAgents = zbAgentServ.queryZBInfoAgentByChnlCode(zbInfoAgent);
					if (zbInfoAgents != null) {
						BeanUtils.copyProperties(zbInfoAgents, zbInfoAgentVo);
					}
				}
			}
			BeanUtils.copyProperties(resultPo, resultVo);
			resultVo.setInfoAgentVo(infoAgentVo);
			resultVo.setZbInfoAgentRelationVo(zbInfoAgentRelationVo);
			resultVo.setZbInfoAgentVo(zbInfoAgentVo);
			
			List<InfoOperPasswordPo> infoOperPasswordOut = operPasswordServ.queryInfoOperPasswordByOperNo(resultPo);
			if (infoOperPasswordOut != null && infoOperPasswordOut.size() > 0){
				for(int i=0;i<infoOperPasswordOut.size();i++){
					InfoOperPasswordVo infoOperPasswordVo = new InfoOperPasswordVo();
					BeanUtils.copyProperties(infoOperPasswordOut.get(i), infoOperPasswordVo);
					infoOperPasswordVos.add(i, infoOperPasswordVo);
				}
				resultVo.setInfoOperPasswordVos(infoOperPasswordVos);
			}
			
			List<InfoMenuPo> infoMenuOut = infoMenuServ.queryMenuByOperId(resultPo);
			if (infoMenuOut != null&& infoMenuOut.size() > 0){
				for(int i=0;i<infoMenuOut.size();i++){
					InfoMenuVo infoMenuVo = new InfoMenuVo();
					BeanUtils.copyProperties(infoMenuOut.get(i), infoMenuVo);
					infoMenuVos.add(i, infoMenuVo);
				}
				resultVo.setInfoMenuVos(infoMenuVos);
			}
			
			List<InfoPagesMobilePo> infoPagesMobileOut = infoPagesMobileServ.queryInfoPagesMobileByOperNo(resultPo);
			if (infoPagesMobileOut != null && infoPagesMobileOut.size() > 0){
				for(int i=0;i<infoPagesMobileOut.size();i++){
					InfoPagesMobileVo infoPagesMobileVo = new InfoPagesMobileVo();
					BeanUtils.copyProperties(infoPagesMobileOut.get(i), infoPagesMobileVo);
					infoPagesMobileVos.add(i, infoPagesMobileVo);
				}
				resultVo.setInfoPagesMobileVos(infoPagesMobileVos);
			}
			return resultVo;
		}
		else{
			return null;
		}
	}
	/**
	 * 验证是否登录
	 */
	@Override
	public UocMessage isLogin(String jsessionId) throws Exception {
		logger.info("=============验证是否登录(uac)=============");
		UocMessage uocMessage = new UocMessage();
		
		UocMessage redisReuslt = redisServiceServDu.queryDataFromRedis(jsessionId);
		if("0000".equals(redisReuslt.getRespCode())){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("已登录");
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("未登录！");
		}
		return uocMessage;
	}

}
