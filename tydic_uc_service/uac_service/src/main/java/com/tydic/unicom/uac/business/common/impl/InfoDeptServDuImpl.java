package com.tydic.unicom.uac.business.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.uac.base.database.interfaces.InfoDeptServ;
import com.tydic.unicom.uac.base.database.po.InfoDeptPo;
import com.tydic.unicom.uac.business.common.interfaces.InfoDeptServDu;
import com.tydic.unicom.uac.business.common.interfaces.OperServDu;
import com.tydic.unicom.uac.business.common.vo.InfoDeptVo;
import com.tydic.unicom.uac.service.common.interfaces.BaseOperServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class InfoDeptServDuImpl implements InfoDeptServDu{

	private static Logger logger = Logger.getLogger(InfoDeptServDuImpl.class);
	
	@Autowired
	private InfoDeptServ infoDeptServ;
	
	@Autowired
	private OperServDu operServDu;
	
	@Autowired
	private BaseOperServDu baseOperServDu;
	
	@Override
	public List<InfoDeptVo> queryInfoDeptByDeptNoAndDeptName(String currDeptNo,String departNo, String departName) throws Exception {
		List<InfoDeptPo> list = infoDeptServ.queryInfoDeptByDeptNoAndDeptName(currDeptNo, departNo, departName);
		if(list != null && list.size()>0){
			List<InfoDeptVo> listOut = new ArrayList<InfoDeptVo>();
			for(int i=0;i<list.size();i++){
				InfoDeptVo infoDeptVo = new InfoDeptVo();
				BeanUtils.copyProperties(list.get(i), infoDeptVo);
				listOut.add(infoDeptVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}
	
	@Override
	public InfoDeptVo queryInfoDeptByOperNo(String operNo) throws Exception {
		InfoDeptPo infoDept = infoDeptServ.queryInfoDeptByOperNo(operNo);
		if(infoDept != null){
			InfoDeptVo infoDeptVo = new InfoDeptVo();
			BeanUtils.copyProperties(infoDept, infoDeptVo);
			return infoDeptVo;
		}
		else{
			return null;
		}	
	}
	
	/**
	 * 获取可选营业厅(UAC0006)
	 * */
	@Override
	public UocMessage getBusinessHallInfo(String jsessionId, String areaId,
			String departNo, String departName) throws Exception {
		
		logger.debug("==========获取可选营业厅（uac_service）===========");
		UocMessage uocMessage = new UocMessage();
		
		//校验必传参数是否为空
		if(StringUtils.isEmpty(jsessionId)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("jsessionId不能为空");
			return uocMessage;
		}
		
		//校验是否登录
		UocMessage loginResult = operServDu.isLogin(jsessionId);
		if(!"0000".equals(loginResult.getRespCode())){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("未登录");
			return uocMessage;
		}
		
		//获取可选营业厅
		InfoDeptPo infoDeptPoQuery = new InfoDeptPo();
		infoDeptPoQuery.setDept_type("05");
		
		if(!StringUtils.isEmpty(areaId)){
			infoDeptPoQuery.setArea_id(areaId);
		}
		if(!StringUtils.isEmpty(departNo)){
			infoDeptPoQuery.setDept_no(departNo);
		}
		if(!StringUtils.isEmpty(departName)){
			infoDeptPoQuery.setDept_name(departName);
		}
		
		List<InfoDeptPo> listResult = infoDeptServ.queryInfoDeptByDeptType(infoDeptPoQuery);
		if(listResult != null && listResult.size()>0){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			List<Map<String,Object>> listOut = new ArrayList<Map<String,Object>>();
			for(int i=0;i<listResult.size();i++){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("dept_no", listResult.get(i).getDept_no());
				map.put("dept_name", listResult.get(i).getDept_name());
				listOut.add(map);
			}
			uocMessage.setContent("获取可选营业厅信息成功");
			uocMessage.addArg("dept_list", listOut);
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("未获取到可选营业厅信息");
		}
		return uocMessage;
	}

	@Override
	public List<InfoDeptVo> queryInfoDepts(String jsession_id,
			String region_id, String depart_no, String depart_name,
			String query_type) throws Exception{
		//校验是否登录
		UocMessage loginResult = baseOperServDu.getOperInfoByJsessionId(jsession_id);
		String curr_dept_no ="";
		if("0000".equals(loginResult.getRespCode())){
			Map<String,Object> oper_info =(Map<String, Object>) loginResult.getArgs().get("oper_info");
			if(oper_info!=null){
				curr_dept_no =(String) oper_info.get("depart_no");
			}
		}
		
		
		InfoDeptPo infoDeptPoQuery = new InfoDeptPo();
		List<InfoDeptPo> list =new ArrayList<InfoDeptPo>();
		if("0".equals(query_type)){
			list = infoDeptServ.queryInfoDeptByDeptNoAndDeptName(curr_dept_no, depart_no, depart_name);
		}else if("1".equals(query_type)){
			infoDeptPoQuery.setLocal_net(region_id);
			infoDeptPoQuery.setDept_no(depart_no);
			infoDeptPoQuery.setDept_name(depart_name);
			list =infoDeptServ.queryInfoDeptByLocalNet(infoDeptPoQuery);
		}
		List<InfoDeptVo> listVo =new ArrayList<InfoDeptVo>();
		if(list !=null && list.size()>0){
			for(InfoDeptPo po :list){
				InfoDeptVo vo =new InfoDeptVo();
				BeanUtils.copyProperties(po, vo);
				listVo.add(vo);
			}
			
		}
		
		
		return listVo;
	}

}
