package com.tydic.unicom.uoc.service.activiti.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.ProcModApp;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.base.uoccode.po.ProcModAppPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcModAppServ;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcModAppServDu;
import com.tydic.unicom.uoc.service.activiti.vo.ProcModAppVo;
import com.tydic.unicom.webUtil.UocMessage;

@Service("ProcModAppServDu")
public class ProcModAppServDuImpl implements ProcModAppServDu{

	Logger logger = Logger.getLogger(ProcModAppServDuImpl.class);
	
	@Autowired
	private ProcModAppServ procModAppServ;
	
	@Autowired
	private RedisServiceServ redisServiceServ;
	
	@Override
	public ProcModAppVo queryProcModAppByProvinceCodeAndAreaCodeAndOperCodeFromRedis(ProcModAppVo procModAppVo) throws Exception {
		String key = procModAppVo.getProvince_code()+"_"+procModAppVo.getArea_code()+"_"+procModAppVo.getOper_code();
		String keyDefine = procModAppVo.getProvince_code()+"_*_"+procModAppVo.getOper_code();
		UocMessage queryRedisMsg = redisServiceServ.queryDataFromRedis("proc_mod_app");
		if(!"0000".equals(queryRedisMsg.getRespCode())){
			logger.info("=================查询redis错误================");
			return null;
		}
		else{
			Map<String,Object> redismap = queryRedisMsg.getArgs();
			RedisData redisDataResult = (RedisData) redismap.get("RedisDataResult");
			Map<String,Object> dataMap = redisDataResult.getMap();
			if(!dataMap.containsKey(key)){
				logger.info("=================查询redis，不存在area_code对应的数据,取默认区域对应的数据================");
				if(!dataMap.containsKey(keyDefine)){
					logger.info("=================查询redis，不存在默认area_code对应的数据================");
					return null;
				}
				else{
					ProcModApp procModApp = (ProcModApp) dataMap.get(keyDefine);
					ProcModAppVo procModAppVoDefine = new ProcModAppVo();
					BeanUtils.copyProperties(procModApp, procModAppVoDefine);
					return procModAppVoDefine;
				}
			}
			else{
				ProcModApp procModApp = (ProcModApp) dataMap.get(keyDefine);
				ProcModAppVo procModAppVoOut = new ProcModAppVo();
				BeanUtils.copyProperties(procModApp, procModAppVoOut);
				return procModAppVoOut;
			}
		}
	}
	
	@Override
	public ProcModAppVo queryProcModAppByOperCode(ProcModAppVo vo)
			throws Exception {
		ProcModApp procModApp = new ProcModApp();
		ProcModAppVo procModAppVo = new ProcModAppVo();
		UocMessage msg = redisServiceServ.queryDataFromRedis("queryProcModAppByOperCode");
		if("0000".equals(msg.getRespCode())){
			RedisData redisData=(RedisData) msg.getArgs().get("RedisDataResult");
			Map<String,Object> operMap = redisData.getMap();
			procModApp =   (ProcModApp) operMap.get(vo.getOper_code());
			if(procModApp != null){
				BeanUtils.copyProperties(procModApp, procModAppVo);
				return procModAppVo;
			}else{
				return getProcModApp(vo);
			}
		}
		else{
			return getProcModApp(vo);
		}	
	}


	@Override
	public boolean create(ProcModAppVo procModAppVo) throws Exception {
		ProcModAppPo procModAppPo =  new ProcModAppPo();
		BeanUtils.copyProperties(procModAppVo, procModAppPo);
		boolean res = procModAppServ.create(procModAppPo);
		return res;
	}

	@Override
	public boolean update(ProcModAppVo procModAppVo) throws Exception {
		ProcModAppPo procModAppPo =  new ProcModAppPo();
		BeanUtils.copyProperties(procModAppVo, procModAppPo);
		boolean res = procModAppServ.update(procModAppPo);
		return res;
	}

	@Override
	public boolean delete(ProcModAppVo procModAppVo) throws Exception {
		ProcModAppPo procModAppPo =  new ProcModAppPo();
		BeanUtils.copyProperties(procModAppVo, procModAppPo);
		boolean res = procModAppServ.delete(procModAppPo);
		return res;
	}

	@Override
	public List<ProcModAppVo> queryProcModAppAll() throws Exception {
		List<ProcModAppPo> list = procModAppServ.queryProcModAppAll();
		if(list != null && list.size()>0){
			List<ProcModAppVo> listOut = new ArrayList<ProcModAppVo>();
			for(int i=0;i<list.size();i++){
				ProcModAppVo procModAppVo = new ProcModAppVo();
				BeanUtils.copyProperties(list.get(i), procModAppVo);
				listOut.add(procModAppVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

	@Override
	public List<ProcModAppVo> queryProcModAppList(ProcModAppVo vo, int pageNo,
			int pageSize) throws Exception {
		List<ProcModAppVo> listVo = new ArrayList<ProcModAppVo>();
		ProcModAppPo procModAppPo =  new ProcModAppPo();
		BeanUtils.copyProperties(vo, procModAppPo);
		List<ProcModAppPo> listPo = procModAppServ.queryProcModAppList(procModAppPo, pageNo, pageSize);
		if(listPo != null && listPo.size()>0){
			for(ProcModAppPo po:listPo){
				ProcModAppVo voTemp = new ProcModAppVo();
				BeanUtils.copyProperties(po, voTemp);
				listVo.add(voTemp);
			}
			return listVo;
		}else{
			return null;
		}
		
	}

	@Override
	public int queryProcModAppListCount(ProcModAppVo vo)
			throws Exception {		
		ProcModAppPo procModAppPo =  new ProcModAppPo();
		BeanUtils.copyProperties(vo, procModAppPo);
		int res = procModAppServ.queryProcModAppListCount(procModAppPo);
		return res;
	}
	
	private ProcModAppVo getProcModApp(ProcModAppVo vo) throws Exception{
		ProcModAppPo po = new ProcModAppPo();		
		BeanUtils.copyProperties(vo, po);
		po = procModAppServ.queryProcModAppByProvinceCodeAndAreaCodeAndOperCode(po);
		if(po != null){
			ProcModAppVo procModAppVo = new ProcModAppVo();
			BeanUtils.copyProperties(po, procModAppVo);
			return procModAppVo;
		}else{
			return null;
		}
	}
}
