package com.tydic.unicom.uoc.service.mod.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.OrdModAttrDefine;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.base.uoccode.po.OrdModAttrDefinePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModAttrDefineServ;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModAttrDefineServDu;
import com.tydic.unicom.uoc.service.mod.vo.OrdModAttrDefineVo;
import com.tydic.unicom.uoc.service.order.impl.InfoSaleOrderServDuImpl;
import com.tydic.unicom.webUtil.UocMessage;
@Service("OrdModAttrDefineServDu")
public class OrdModAttrDefineServDuImpl implements OrdModAttrDefineServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderServDuImpl.class);

	@Autowired
	private OrdModAttrDefineServ ordModAttrDefineServ;
	@Autowired
	private RedisServiceServ redisServiceServ;
	
	@Override
	public List<OrdModAttrDefineVo> queryOrdModAttrDefineByModCode(
			OrdModAttrDefineVo vo) throws Exception {
		logger.info("----------------queryOrdModAttrDefineByModCode-----------------------");
		RedisData redisPo = new RedisData();
		List<OrdModAttrDefineVo> listVo =new ArrayList<OrdModAttrDefineVo>();
		UocMessage res =redisServiceServ.queryDataFromRedis("queryOrdModAttrDefineByModCode");
		if("0000".equals(res.getRespCode())){
			Map<String,Object> redisMap = res.getArgs();
			RedisData redisDataResult = (RedisData) redisMap.get("RedisDataResult");
			Map<String,Object> dataMap = redisDataResult.getMap();
			if(dataMap.containsKey(vo.getMod_code())){
				List<OrdModAttrDefine> redisList = new ArrayList<OrdModAttrDefine>();
				redisList = (List<OrdModAttrDefine>) dataMap.get(vo.getMod_code());
				if(redisList!=null && redisList.size()>0){
					for(OrdModAttrDefine omad:redisList){
						OrdModAttrDefineVo omadVo =new OrdModAttrDefineVo();
						BeanUtils.copyProperties(omad, omadVo);
						listVo.add(omadVo);
					}
				}
				
				return listVo;
			}
			else{
				List<OrdModAttrDefine> redisupdateList = getRedisList(vo.getMod_code());
				if(redisupdateList != null && redisupdateList.size()>0){
					redisPo.setId("queryOrdModAttrDefineByModCode");
					dataMap.put(vo.getMod_code(), redisupdateList);
					redisPo.setMap(redisMap);
					UocMessage mres =redisServiceServ.updateDataToRedis(redisPo);
					if("0000".equals(mres.getRespCode())){
						if(redisupdateList!=null && redisupdateList.size()>0){
							for(OrdModAttrDefine omad:redisupdateList){
								OrdModAttrDefineVo omadVo =new OrdModAttrDefineVo();
								BeanUtils.copyProperties(omad, omadVo);
								listVo.add(omadVo);
							}
						}
						return listVo;
					}else{
						return null;
					}
				}
				else{
					return null;
				}
			}
		}else{
			List<OrdModAttrDefine> redisCreateList = getRedisList(vo.getMod_code());
			redisPo.setId("queryOrdModAttrDefineByModCode");
			Map<String,Object> redisCreateMap = new HashMap<String,Object>();
			redisCreateMap.put(vo.getMod_code(), redisCreateList);
			redisPo.setMap(redisCreateMap);
			UocMessage mres =redisServiceServ.createDataToRedis(redisPo);
			if("0000".equals(mres.getRespCode())){
				if(redisCreateList!=null && redisCreateList.size()>0){
					for(OrdModAttrDefine omad:redisCreateList){
						OrdModAttrDefineVo omadVo =new OrdModAttrDefineVo();
						BeanUtils.copyProperties(omad, omadVo);
						listVo.add(omadVo);
					}
				}
				return listVo;
			}else{
				return null;
			}
		}
	}
	
	private List<OrdModAttrDefine> getRedisList(String Mod_code) throws Exception{
		UocMessage redisRes = new UocMessage();
		redisRes =redisServiceServ.queryDataFromRedis("ord_mod_attr_define");
		if(!"0000".equals(redisRes.getRespCode())){
			return null;
		}
		else{
			RedisData redisPo =(RedisData)redisRes.getArgs().get("RedisDataResult");
			Map<String,Object> map =redisPo.getMap();
			List<OrdModAttrDefine> list =new ArrayList<OrdModAttrDefine>();
			list =(List<OrdModAttrDefine>) map.get("ord_mod_attr_define");
			List<OrdModAttrDefine> resultList = new ArrayList<OrdModAttrDefine>();
			for(OrdModAttrDefine ordModAttrDefine:list){
				if(Mod_code.equals(ordModAttrDefine.getMod_code())){
					resultList.add(ordModAttrDefine);
				}
			}
			return resultList;
		}
		
	}

	@Override
	public boolean create(OrdModAttrDefineVo vo) throws Exception {
		OrdModAttrDefinePo ordModAttrDefinePo =new OrdModAttrDefinePo();
		BeanUtils.copyProperties(vo,ordModAttrDefinePo);
		boolean res = ordModAttrDefineServ.create(ordModAttrDefinePo);
		return res;
	}

	@Override
	public boolean update(OrdModAttrDefineVo vo) throws Exception {
		OrdModAttrDefinePo ordModAttrDefinePo =new OrdModAttrDefinePo();
		BeanUtils.copyProperties(vo,ordModAttrDefinePo);
		boolean res = ordModAttrDefineServ.update(ordModAttrDefinePo);
		return res;
	}

	@Override
	public boolean delete(OrdModAttrDefineVo vo) throws Exception {
		OrdModAttrDefinePo ordModAttrDefinePo =new OrdModAttrDefinePo();
		BeanUtils.copyProperties(vo,ordModAttrDefinePo);
		boolean res = ordModAttrDefineServ.delete(ordModAttrDefinePo);
		return res;
	}


	@Override
	public int queryOrdModAttrDefineListCount(OrdModAttrDefineVo vo)
			throws Exception {
		OrdModAttrDefinePo ordModAttrDefinePo =new OrdModAttrDefinePo();
		BeanUtils.copyProperties(vo,ordModAttrDefinePo);
		int res=ordModAttrDefineServ.queryOrdModAttrDefineListCount(ordModAttrDefinePo);
		return res;
	}

	@Override
	public List<OrdModAttrDefineVo> queryOrdModAttrDefineList(
			OrdModAttrDefineVo vo, int pageNo, int pageSize) throws Exception {
		OrdModAttrDefinePo ordModAttrDefinePo =new OrdModAttrDefinePo();
		BeanUtils.copyProperties(vo,ordModAttrDefinePo);
		
		List<OrdModAttrDefineVo> listVo = new ArrayList<OrdModAttrDefineVo>();
		List<OrdModAttrDefinePo> listPo =ordModAttrDefineServ.queryOrdModAttrDefineList(ordModAttrDefinePo, pageNo, pageSize);
		if(listPo != null && listPo.size() > 0){
			for(OrdModAttrDefinePo po : listPo){
				OrdModAttrDefineVo ordVo = new OrdModAttrDefineVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}

	@Override
	public List<OrdModAttrDefineVo> queryOrdModAttrDefineAll() throws Exception {
		List<OrdModAttrDefinePo> list = ordModAttrDefineServ.queryOrdModAttrDefineAll();
		if(list != null && list.size()>0){
			List<OrdModAttrDefineVo> listOut = new ArrayList<OrdModAttrDefineVo>();
			for(int i=0;i<list.size();i++){
				OrdModAttrDefineVo ordModAttrDefineVo = new OrdModAttrDefineVo();
				BeanUtils.copyProperties(list.get(i), ordModAttrDefineVo);
				listOut.add(ordModAttrDefineVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

	

}
