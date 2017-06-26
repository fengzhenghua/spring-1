package com.tydic.unicom.uac.business.common.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uac.base.database.interfaces.InfoAgentServ;
import com.tydic.unicom.uac.base.database.interfaces.InfoInputOrderServ;
import com.tydic.unicom.uac.base.database.interfaces.InfoOperServ;
import com.tydic.unicom.uac.base.database.po.InfoAgentPo;
import com.tydic.unicom.uac.base.database.po.InfoInputOrderPo;
import com.tydic.unicom.uac.base.database.po.InfoOperPo;
import com.tydic.unicom.uac.business.common.interfaces.InfoInputOrderServDu;
import com.tydic.unicom.uac.business.common.vo.RewardOrderInfoVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class InfoInputOrderServDuImpl implements InfoInputOrderServDu {

	private static Logger logger = LoggerFactory.getLogger(InfoInputOrderServDuImpl.class);

	@Autowired
	private InfoInputOrderServ infoInputOrderServ;
	@Autowired
	private InfoOperServ infoOperServ;
	@Autowired
	private InfoAgentServ infoAgentServ;

	@Override
	public UocMessage createRewardOrderInfo(RewardOrderInfoVo vo) throws Exception {
		UocMessage resultUocMessage = new UocMessage();

		if (logger.isInfoEnabled()) {
			logger.info("[UAC0011]==========>请求参数" + vo.toString());
		}

		String order_id=vo.getOrder_id();
		String oper_id=vo.getOper_id();
		String acc_nbr=vo.getAcc_nbr();

		if (StringUtils.isEmpty(vo.getJsession_id()) || StringUtils.isEmpty(order_id) || StringUtils.isEmpty(oper_id)
				|| StringUtils.isEmpty(acc_nbr)) {
			resultUocMessage.setContent("必传参数为空");
			resultUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			return resultUocMessage;
		}

		//oper_dept_no部门字段根据传入的oper_id从crm_pub.info_oper中取
		InfoOperPo operPo = new InfoOperPo();
		operPo.setOper_no(oper_id);
		List<InfoOperPo> resultOper = infoOperServ.queryInfoOperOperNo(operPo);
		if (resultOper == null) {
			resultUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			resultUocMessage.setContent("未查到工号信息");
			return resultUocMessage;
		}

		//chnl_code根据oper_dept_no部门字段从crm_pub.info_agent中取
		InfoAgentPo agentPo=new InfoAgentPo();
		agentPo.setDept_no(resultOper.get(0).getDept_no());
		List<InfoAgentPo> resultAgent=infoAgentServ.queryInfoAgentByDeptNo(agentPo);
		if (resultAgent == null) {
			resultUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			resultUocMessage.setContent("未查到渠道信息");
			return resultUocMessage;
		}

		InfoInputOrderPo dataPo=new InfoInputOrderPo();
		dataPo.setOrder_id(order_id);
		dataPo.setOper_id(oper_id);
		dataPo.setOrder_number(acc_nbr);
		dataPo.setOper_dept_no(resultOper.get(0).getDept_no());
		dataPo.setChnl_code(resultAgent.get(0).getChnl_code());

		boolean result = infoInputOrderServ.createInfoInputOrder(dataPo);
		if (!result) {
			resultUocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			resultUocMessage.setContent("即时激励数据写入失败");
			return resultUocMessage;
		}

		resultUocMessage.setRespCode(RespCodeContents.SUCCESS);
		resultUocMessage.setContent("即时激励数据写入成功");
		return resultUocMessage;
	}

}
