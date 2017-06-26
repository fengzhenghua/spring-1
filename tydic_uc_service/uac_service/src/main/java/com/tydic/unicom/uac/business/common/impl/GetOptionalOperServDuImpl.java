package com.tydic.unicom.uac.business.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.uac.base.database.interfaces.InfoCommendOperServ;
import com.tydic.unicom.uac.base.database.po.InfoCommendOperPo;
import com.tydic.unicom.uac.business.common.interfaces.GetOptionalOperServDu;
import com.tydic.unicom.uac.business.common.interfaces.InfoOperServDu;
import com.tydic.unicom.uac.business.common.interfaces.OperServDu;
import com.tydic.unicom.uac.business.common.vo.InfoOperVo;
import com.tydic.unicom.uac.business.common.vo.RewardOperInfoVo;
import com.tydic.unicom.uac.service.common.interfaces.BaseOperServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class GetOptionalOperServDuImpl implements GetOptionalOperServDu {

	private static Logger LOGGER = Logger.getLogger(InfoBaseOperServDuImpl.class);

	@Autowired
	private OperServDu operServDu;

	@Autowired
	private BaseOperServDu baseOperServDu;

	@Autowired
	private InfoOperServDu infoOperServDu;

	@Autowired
	private InfoCommendOperServ infoCommendOperServ;

	@SuppressWarnings("unchecked")
	@Override
	public UocMessage getOptionalOperInfoString(String jsessionId, String operNo, String operName) throws Exception {

		LOGGER.info("INFO [UAC0003]==========>获取可选工号开始<==========");
		LOGGER.info("INFO [UAC0003]==========>请求参数：jsessionId：" + jsessionId+",operNo："+operNo+",operName："+operName);

		UocMessage resultUocMessage = new UocMessage();

		// 1、参数校验
		if(StringUtils.isEmpty(jsessionId)){
			resultUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			resultUocMessage.setContent("请求参数中jsession_id不能为空");
			return resultUocMessage;
		}

		// 判断是否登录
		if( !(RespCodeContents.SUCCESS.equals(operServDu.isLogin(jsessionId).getRespCode())) ){
			// 未登录
			resultUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			resultUocMessage.setContent("未登录");
			return resultUocMessage;
		}

		// 2、调UACBS0001-获取登录信息接口，获取工号
		UocMessage uocMessageTemp = new UocMessage();
		uocMessageTemp = baseOperServDu.getOperInfoByJsessionId(jsessionId);
		if( !RespCodeContents.SUCCESS.equals(uocMessageTemp.getRespCode())){
			return uocMessageTemp;
		}

		// 3、获取当前部门编号
		Map<String,Object> operInfo = (Map<String, Object>) uocMessageTemp.getArgs().get("oper_info");
		if(StringUtils.isEmpty(operInfo.get("depart_no").toString())){
			resultUocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			resultUocMessage.setContent("获取当前部门编号失败");
			return resultUocMessage;
		}

		// 4、获取当前部门以及下级部门的所有工号
		List<InfoOperVo> list = infoOperServDu.queryInfoOperByOperNoAndOperName(operInfo.get("depart_no").toString(), operNo, operName);
		if(list != null && list.size()>0){
			resultUocMessage.setRespCode(RespCodeContents.SUCCESS);
			resultUocMessage.setContent("获取可选工号成功");

			// 5、封装响应参数
			List<Map<String,String>> operList = new ArrayList<Map<String,String>>();//工号列表
			for(InfoOperVo info : list){
				Map<String,String> operInfoMap = new HashMap<String,String>();
				operInfoMap.put("oper_no", info.getOper_no());//工号编号
				operInfoMap.put("oper_name", info.getOper_name());//工号名称
				operList.add(operInfoMap);
			}
			resultUocMessage.addArg("oper_list", operList);
		}
		else{
			resultUocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			resultUocMessage.setContent("没有查询到数据");
		}

		LOGGER.info("INFO [UAC0003]==========>响应参数：" + resultUocMessage.getContent() == null ? "" : resultUocMessage.getContent()
				.toString());
		LOGGER.info("INFO [UAC0003]==========>获取可选工号结束<==========");

		// 返回结果
		return resultUocMessage;
	}

	@Override
	public UocMessage getOptionalRewardOperInfo(String jsession_id, String developer_no) throws Exception {
		LOGGER.info("INFO [UAC0003]==========>获取可选工号开始<==========");
		LOGGER.info("INFO [UAC0003]==========>请求参数：jsession_id：" + jsession_id + ",developer_no：" + developer_no);

		UocMessage resultUocMessage = new UocMessage();
		List<RewardOperInfoVo> resultList = new ArrayList<RewardOperInfoVo>();

		// 参数校验
		if (StringUtils.isEmpty(jsession_id) || StringUtils.isEmpty(developer_no)) {
			resultUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			resultUocMessage.setContent("必传参数为空");
			return resultUocMessage;
		}

		InfoCommendOperPo paramPo = new InfoCommendOperPo();
		paramPo.setCommend_no(developer_no);
		List<InfoCommendOperPo> commendOperList = infoCommendOperServ.queryInfoCommendOperByDevelopNo(paramPo);
		if (commendOperList != null && commendOperList.size() > 0) {
			for (InfoCommendOperPo resultPo : commendOperList) {
				RewardOperInfoVo rewardVo = new RewardOperInfoVo();
				rewardVo.setReward_oper_no(resultPo.getOper_no());
				rewardVo.setReward_oper_name(resultPo.getOper_name());
				resultList.add(rewardVo);
			}
		}

		resultUocMessage.addArg("reward_oper_list", resultList);
		resultUocMessage.setRespCode(RespCodeContents.SUCCESS);
		resultUocMessage.setContent("获取可选即时激励工号成功");
		return resultUocMessage;
	}

}
