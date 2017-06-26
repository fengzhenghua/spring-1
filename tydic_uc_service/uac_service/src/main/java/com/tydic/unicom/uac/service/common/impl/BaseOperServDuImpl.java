package com.tydic.unicom.uac.service.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uac.base.database.interfaces.InfoAgentDevelopersServ;
import com.tydic.unicom.uac.base.database.interfaces.InfoAgentServ;
import com.tydic.unicom.uac.base.database.interfaces.InfoCommendOperServ;
import com.tydic.unicom.uac.base.database.interfaces.InfoDeptServ;
import com.tydic.unicom.uac.base.database.interfaces.InfoMenuServ;
import com.tydic.unicom.uac.base.database.interfaces.InfoOperServ;
import com.tydic.unicom.uac.base.database.interfaces.OperPasswordServ;
import com.tydic.unicom.uac.base.database.interfaces.RuleOperRoleServ;
import com.tydic.unicom.uac.base.database.interfaces.ZBAgentRelationServ;
import com.tydic.unicom.uac.base.database.interfaces.ZBAgentServ;
import com.tydic.unicom.uac.base.database.po.InfoAgentDevelopersPo;
import com.tydic.unicom.uac.base.database.po.InfoAgentPo;
import com.tydic.unicom.uac.base.database.po.InfoAuthorityPo;
import com.tydic.unicom.uac.base.database.po.InfoCommendOperPo;
import com.tydic.unicom.uac.base.database.po.InfoDeptPo;
import com.tydic.unicom.uac.base.database.po.InfoMenuPo;
import com.tydic.unicom.uac.base.database.po.InfoOperPasswordPo;
import com.tydic.unicom.uac.base.database.po.InfoOperPo;
import com.tydic.unicom.uac.base.database.po.RuleOperRolePo;
import com.tydic.unicom.uac.base.database.po.ZBInfoAgentPo;
import com.tydic.unicom.uac.base.database.po.ZBInfoAgentRelationPo;
import com.tydic.unicom.uac.business.common.vo.InfoAgentDevelopersVo;
import com.tydic.unicom.uac.business.common.vo.InfoAgentVo;
import com.tydic.unicom.uac.business.common.vo.InfoAuthorityVo;
import com.tydic.unicom.uac.business.common.vo.InfoCommendOperVo;
import com.tydic.unicom.uac.business.common.vo.InfoDeptVo;
import com.tydic.unicom.uac.business.common.vo.InfoMenuVo;
import com.tydic.unicom.uac.business.common.vo.InfoOperPasswordVo;
import com.tydic.unicom.uac.business.common.vo.InfoOperVo;
import com.tydic.unicom.uac.business.common.vo.RuleOperRoleVo;
import com.tydic.unicom.uac.business.common.vo.UniformInfoOperVo;
import com.tydic.unicom.uac.business.common.vo.ZBInfoAgentRelationVo;
import com.tydic.unicom.uac.business.common.vo.ZBInfoAgentVo;
import com.tydic.unicom.uac.service.common.interfaces.BaseOperServDu;
import com.tydic.unicom.uac.service.interfaces.RedisServiceServDu;
import com.tydic.unicom.uac.service.vo.RedisDataVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 认证中心服务
 *
 * @author ZhangCheng
 * @version V1.0
 * @data 2017-02-28
 *
 */
@Service("BaseOperServDu")
public class BaseOperServDuImpl implements BaseOperServDu {

	private static Logger LOGGER = Logger.getLogger(BaseOperServDuImpl.class);

	/** uac_service redis缓存服务 */
	@Autowired
	private RedisServiceServDu redisServiceServDu;

	/** uac_service_base 工号相关数据服务 */
	@Autowired
	private InfoOperServ infoOperServ;

	/** uac_service_base 发展人相关数据服务 */
	@Autowired
	private InfoCommendOperServ infoCommendOperServ;

	/** uac_service_base 发展人相关数据服务 */
	@Autowired
	private InfoAgentDevelopersServ infoAgentDevelopersServ;

	/** uac_service_base 部门相关数据服务 */
	@Autowired
	private InfoDeptServ infoDeptServ;

	/** uac_service_base 代理相关数据服务 */
	@Autowired
	private InfoAgentServ infoAgentServ;

	/** uac_service_base 代理相关数据服务 */
	@Autowired
	private ZBAgentRelationServ zbAgentRelationServ;

	/** uac_service_base 代理相关数据服务 */
	@Autowired
	private ZBAgentServ zbAgentServ;

	/** uac_service_base 工号密码数据服务 */
	@Autowired
	private OperPasswordServ operPasswordServ;

	/** uac_service_base 菜单数据服务 */
	@Autowired
	private InfoMenuServ infoMenuServ;

	/** uac_service_base 菜单数据服务 */
	@Autowired
	private RuleOperRoleServ ruleOperRoleServ;

	@Override
	public UocMessage getOperInfoByJsessionId(String jsessionId) throws Exception {

		LOGGER.debug("[UACBS0001]==========>根据工号或验证字符串获取工号信息<==========");
		LOGGER.info("[UACBS0001]==========>jsessionId：" + jsessionId);

		UocMessage resultMessage = new UocMessage();

		// 从缓存中获取工号根据验证字符串
		try {
			UocMessage uocMessageTemp = redisServiceServDu.queryDataFromRedis(jsessionId);
			if (RespCodeContents.SUCCESS.equals(uocMessageTemp.getRespCode())) {
				RedisDataVo redisDataVo= (RedisDataVo) uocMessageTemp.getArgs().get("RedisData");
				UniformInfoOperVo uniformInfoOperVo = (UniformInfoOperVo) redisDataVo.getObj();
				LOGGER.debug("[UACBS0001]==========>从redis中获取工号根据验证字符串正常");
				jsessionId = uniformInfoOperVo.getOper_no();
				LOGGER.info("[UACBS0001]==========>工号："+ jsessionId);
			}

		} catch (Exception e1) {
			LOGGER.debug("[UACBS0001]==========>从redis中获取工号根据验证字符串-读取缓存异常");
			e1.printStackTrace();
		}

		String redisKey = jsessionId + "1";

		// 先从缓存获取工号信息
		RedisDataVo redisDataVoOne = new RedisDataVo();
		redisDataVoOne.setId(redisKey);
		try {
			UocMessage uocMessageTemp = redisServiceServDu.queryDataFromRedis(redisDataVoOne.getId());
			if (RespCodeContents.SUCCESS.equals(uocMessageTemp.getRespCode())) {
				RedisDataVo redisDataVo= (RedisDataVo) uocMessageTemp.getArgs().get("RedisData");
				resultMessage = (UocMessage) redisDataVo.getObj();
				LOGGER.info("[UACBS0001]==========>根据工号或验证字符串获取工号信息正常");
				return resultMessage;
			}

		} catch (Exception e1) {
			LOGGER.debug("[UACBS0001]==========>从缓存获取工号信息-读取缓存异常");
			e1.printStackTrace();
		}

		// 1、获取工号基本信息根据工号
		InfoOperVo infoOperVo = new InfoOperVo();
		InfoOperPo infoOperPo = new InfoOperPo();
		infoOperPo.setOper_no(jsessionId);
		infoOperPo = infoOperServ.getInfoOperByLoginCode(infoOperPo);
		if (infoOperPo == null) {
			resultMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			resultMessage.setContent("工号或验证字符串不存在，请查正后重新获取");
			return resultMessage;
		}

		// 2、获取发展人编号根据工号
		InfoCommendOperPo infoCommendOperPo = new InfoCommendOperPo();
		BeanUtils.copyProperties(infoOperPo, infoCommendOperPo);
		List<InfoCommendOperPo> infoCommendOperOuts = infoCommendOperServ
				.queryInfoCommendOperByOperNo(infoCommendOperPo);
		if (infoCommendOperOuts != null && infoCommendOperOuts.size() != 0) {
			// 发展人信息不为空

			if (infoCommendOperOuts.size() == 1) {
				// 只有一个发展人信息

				InfoCommendOperVo infoCommendOperVo = new InfoCommendOperVo();
				BeanUtils.copyProperties(infoCommendOperOuts.get(0), infoCommendOperVo);
				infoOperVo.setInfoCommendOperVo(infoCommendOperVo);

				// 获取发展人信息根据发展人工号
				InfoAgentDevelopersPo InfoAgentDevelopers = new InfoAgentDevelopersPo();
				InfoAgentDevelopers.setDev_code(infoCommendOperOuts.get(0).getCommend_no());
				InfoAgentDevelopersPo developers = infoAgentDevelopersServ
						.queryInfoAgentDevelopersByDevCode(InfoAgentDevelopers);
				if (developers != null) {
					InfoAgentDevelopersVo infoAgentDevelopersVo = new InfoAgentDevelopersVo();
					BeanUtils.copyProperties(developers, infoAgentDevelopersVo);
					infoOperVo.setInfoAgentDevelopersVo(infoAgentDevelopersVo);
				}
			} else {
				// 有多个发展人信息

				// 获得多个发展人信息根据工号
				InfoAgentDevelopersPo infoAgentDevelopers = new InfoAgentDevelopersPo();
				infoAgentDevelopers.setOper_no(infoOperPo.getOper_no());
				InfoAgentDevelopersPo developers = infoAgentDevelopersServ
						.queryInfoAgentDevelopersByOperNo(infoAgentDevelopers);
				if (developers != null) {
					InfoAgentDevelopersVo infoAgentDevelopersVo = new InfoAgentDevelopersVo();
					BeanUtils.copyProperties(developers, infoAgentDevelopersVo);
					infoOperVo.setInfoAgentDevelopersVo(infoAgentDevelopersVo);
				}
			}
		}

		// 3、获取部门基本信息根据工号
		InfoDeptPo infoDept = new InfoDeptPo();
		BeanUtils.copyProperties(infoOperPo, infoDept);
		InfoDeptPo info = infoDeptServ.queryInfoDeptByDeptNo(infoDept);
		if (info != null) {
			InfoDeptVo infoDeptVo = new InfoDeptVo();
			BeanUtils.copyProperties(info, infoDeptVo);
			infoOperVo.setInfoDeptVo(infoDeptVo);
		}

		// 4、获取渠道信息根据部门编号
		InfoAgentPo infoAgent = new InfoAgentPo();
		InfoAgentVo infoAgentVo = new InfoAgentVo();// 代理信息
		ZBInfoAgentRelationVo zbInfoAgentRelationVo = new ZBInfoAgentRelationVo();// 渠道信息
		ZBInfoAgentVo zbInfoAgentVo = new ZBInfoAgentVo();// 省份信息
		infoAgent.setDept_no(infoOperPo.getDept_no());
		// 获取代理信息根据部门编号
		List<InfoAgentPo> infoAgents = infoAgentServ.queryInfoAgentByDeptNo(infoAgent);
		if (!infoAgents.isEmpty()) {
			// 代理信息不为空

			BeanUtils.copyProperties(infoAgents.get(0), infoAgentVo);
			ZBInfoAgentRelationPo zbInfoAgentRelation = new ZBInfoAgentRelationPo();
			zbInfoAgentRelation.setAgent_no(infoAgents.get(0).getAgent_no().toString());
			// 获取渠道信息根据代理编号
			ZBInfoAgentRelationPo zbInfoAgentRelations = zbAgentRelationServ
					.queryZBInfoAgentRelationByAgentNo(zbInfoAgentRelation);
			if (zbInfoAgentRelations != null) {
				BeanUtils.copyProperties(zbInfoAgentRelations, zbInfoAgentRelationVo);
				ZBInfoAgentPo zbInfoAgent = new ZBInfoAgentPo();
				zbInfoAgent.setChnl_code(zbInfoAgentRelations.getChnl_code());
				// 获取省份信息根据渠道编号
				ZBInfoAgentPo zbInfoAgents = zbAgentServ.queryZBInfoAgentByChnlCode(zbInfoAgent);
				if (zbInfoAgents != null) {
					BeanUtils.copyProperties(zbInfoAgents, zbInfoAgentVo);
				}
			}
		}
		BeanUtils.copyProperties(infoOperPo, infoOperVo);
		infoOperVo.setInfoAgentVo(infoAgentVo);
		infoOperVo.setZbInfoAgentRelationVo(zbInfoAgentRelationVo);
		infoOperVo.setZbInfoAgentVo(zbInfoAgentVo);

		// 5、获取密码信息根据工号
		List<InfoOperPasswordVo> infoOperPasswordVos = new ArrayList<InfoOperPasswordVo>();// 密码信息
		List<InfoOperPasswordPo> infoOperPasswordOut = operPasswordServ.queryInfoOperPasswordByOperNo(infoOperPo);
		if (infoOperPasswordOut != null && infoOperPasswordOut.size() > 0) {
			for (int i = 0; i < infoOperPasswordOut.size(); i++) {
				InfoOperPasswordVo infoOperPasswordVo = new InfoOperPasswordVo();
				BeanUtils.copyProperties(infoOperPasswordOut.get(i), infoOperPasswordVo);
				infoOperPasswordVos.add(i, infoOperPasswordVo);
			}
		}
		infoOperVo.setInfoOperPasswordVos(infoOperPasswordVos);

		// 6、获取菜单信息根据工号
		List<InfoMenuVo> infoMenuVos = new ArrayList<InfoMenuVo>();// 菜单信息

		InfoAuthorityVo infoAuthorityVo = new InfoAuthorityVo();
		infoAuthorityVo.setAuthority_id("1");
		InfoOperPo infoOperPoThree = new InfoOperPo();
		BeanUtils.copyProperties(infoOperVo, infoOperPoThree);
		InfoAuthorityPo infoAuthorityPo = new InfoAuthorityPo();
		BeanUtils.copyProperties(infoAuthorityVo, infoAuthorityPo);
		List<InfoMenuPo> infoMenuList = infoMenuServ.queryMenuByInfoOper(infoOperPoThree,infoAuthorityPo);
		if (infoMenuList != null && infoMenuList.size() > 0) {
			for (int i = 0; i < infoMenuList.size(); i++) {
				InfoMenuVo infoMenuVo = new InfoMenuVo();
				BeanUtils.copyProperties(infoMenuList.get(i), infoMenuVo);
				infoMenuVos.add(i, infoMenuVo);
			}
		}
		// 过滤菜单
		if (infoMenuVos != null && infoMenuVos.size() > 0) {
			for (int i = 0; i < infoMenuVos.size();) {
				if (!"2".equals(infoMenuVos.get(i).getMenu_ctrl())) {
					infoMenuVos.remove(i);
				} else {
					i++;
				}
			}
		}

		infoOperVo.setInfoMenuVos(infoMenuVos);

		// 7、获取角色编号根据工号
		InfoOperPo infoOperPoTwo = new InfoOperPo();
		BeanUtils.copyProperties(infoOperVo, infoOperPoTwo);
		List<RuleOperRolePo> list = ruleOperRoleServ.queryRuleOperRoleNameByInfoOper(infoOperPoTwo);
		List<RuleOperRoleVo> roleVos = new ArrayList<RuleOperRoleVo>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				RuleOperRoleVo rRuleOperRoleVoTemp = new RuleOperRoleVo();
				BeanUtils.copyProperties(list.get(i), rRuleOperRoleVoTemp);
				roleVos.add(rRuleOperRoleVoTemp);
			}
		}
		String role_id = "";// 当前登录工号所拥有的角色编号
		String role_name="";// 当前登录工号所拥有的角色名称
		if (roleVos != null && roleVos.size() > 0) {
			for (RuleOperRoleVo vo : roleVos) {
				role_id += vo.getRole_id() + ",";
				role_name += vo.getRole_name() +",";
			}
		}
		if (!"".equals(role_id)) {
			role_id = role_id.substring(0, role_id.length() - 1);
			role_name = role_name.substring(0, role_name.length() - 1);
		}
		
		// 获取中台（UOC）  角色ID和角色名称
		String createOperatorId = "UOC";
		List<RuleOperRolePo> listUoc = ruleOperRoleServ.queryRuleOperRoleByCreateOperatorId(createOperatorId);
		String uoc_role_id = "";//中台 角色ID
		String uoc_role_name = "";//中台 角色名称
		if(listUoc != null && listUoc.size()>0){
			for (RuleOperRolePo po : listUoc) {
				uoc_role_id += po.getRole_id() + ",";
				uoc_role_name += po.getRole_name() +",";
			}
		}
		if (!"".equals(uoc_role_id)) {
			uoc_role_id = uoc_role_id.substring(0, uoc_role_id.length() - 1);
		}
		if (!"".equals(uoc_role_name)){
			uoc_role_name = uoc_role_name.substring(0, uoc_role_name.length() - 1);
		}
		
		// 封装返回数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oper_no", infoOperVo.getOper_no());
		map.put("oper_name", infoOperVo.getOper_name());
		map.put("role_id", uoc_role_id);
		map.put("role_name", uoc_role_name);
		map.put("province_code", infoOperVo.getZbInfoAgentVo().getProvince_code());
		map.put("area_code", infoOperVo.getZbInfoAgentVo().getCity_code());
		map.put("depart_no", infoOperVo.getDept_no());
		map.put("depart_name", infoOperVo.getDept_name());
		map.put("channel_id", infoOperVo.getZbInfoAgentVo().getChnl_code());
		map.put("channel_type", infoOperVo.getZbInfoAgentVo().getChnl_kind_id());
		map.put("district", infoOperVo.getZbInfoAgentVo().getManager_area_code());
		resultMessage.setRespCode(RespCodeContents.SUCCESS);
		resultMessage.setContent("获取登录信息正常");
		resultMessage.addArg("oper_info", map);
		resultMessage.addArg("infoMenuList", infoOperVo.getInfoMenuVos());

		// 将工号信息写入缓存
		RedisDataVo redisDataVoTwo = new RedisDataVo();
		redisDataVoTwo.setId(redisKey);
		redisDataVoTwo.setObj(resultMessage);
		try {
			UocMessage redisUocMessageTwo = redisServiceServDu.queryDataFromRedis(redisDataVoTwo.getId());
			if (RespCodeContents.SUCCESS.equals(redisUocMessageTwo.getRespCode())) {
				redisServiceServDu.updateDataToRedis(redisDataVoTwo);
			} else {
				redisServiceServDu.createDataToRedis(redisDataVoTwo);
			}
		} catch (Exception e) {
			LOGGER.debug("[UACBS0001]==========>将工号信息写入缓存-读取缓存异常");
			e.printStackTrace();
		}

		LOGGER.info("[UACBS0001]==========>根据工号或验证字符串获取工号信息正常<==========");

		// 返回工号信息
		return resultMessage;
	}
}
