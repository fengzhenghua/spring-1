package com.tydic.unicom.uoc.service.mod.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.ProcViewInfo;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.base.uoccode.po.ProcViewInfoPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcViewInfoServ;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcViewInfoServDu;
import com.tydic.unicom.uoc.service.mod.vo.ProcViewInfoVo;
import com.tydic.unicom.webUtil.UocMessage;

@Service("ProcViewInfoServDu")
public class ProcViewInfoServDuImpl implements ProcViewInfoServDu{

	@Autowired
	private ProcViewInfoServ procViewInfoServ;
	@Autowired
	private RedisServiceServ redisServiceServ;
	@SuppressWarnings("unchecked")
	@Override
	public List<ProcViewInfoVo> queryProcViewInfoByProc(ProcViewInfoVo vo) throws Exception {
		List<ProcViewInfo> procViewInfoList =new ArrayList<ProcViewInfo>();
		List<ProcViewInfoVo> listVo = new ArrayList<ProcViewInfoVo>();
		UocMessage msg = redisServiceServ.queryDataFromRedis("proc_view_info");
		if("0000".equals(msg.getRespCode())){
			RedisData redisData=(RedisData) msg.getArgs().get("RedisDataResult");
			Map<String,Object> operMap = redisData.getMap();
			if(operMap.containsKey(vo.getProc_mod_code())){
				procViewInfoList = (List<ProcViewInfo>) operMap.get(vo.getProc_mod_code());
			}else{
				procViewInfoList = (List<ProcViewInfo>) operMap.get("proc_view_info");
			}
			if(procViewInfoList != null&&procViewInfoList.size()>0){
				for(ProcViewInfo source:procViewInfoList){
					if(source.getProc_mod_code().equals(vo.getProc_mod_code())){
						ProcViewInfoVo procViewInfoVo = new ProcViewInfoVo();
						BeanUtils.copyProperties(source, procViewInfoVo);
						listVo.add(procViewInfoVo);
					}
				}
				return listVo;
			}else{
				return getProcViewInfo(vo);
			}
		}else{
			return getProcViewInfo(vo);
		}

	}

	private List<ProcViewInfoVo> getProcViewInfo(ProcViewInfoVo vo) throws Exception {
		ProcViewInfoPo po = new ProcViewInfoPo();
		BeanUtils.copyProperties(vo, po);
		List<ProcViewInfoPo> listPo = procViewInfoServ.queryProcViewInfo(po);
		List<ProcViewInfoVo> listVo = new ArrayList<ProcViewInfoVo>();
		if(listPo != null && listPo.size()>0){
			for(ProcViewInfoPo potemp : listPo){
				ProcViewInfoVo procViewInfoVo = new ProcViewInfoVo();
				BeanUtils.copyProperties(potemp, procViewInfoVo);
				listVo.add(procViewInfoVo);
			}
		}else{
			return null;
		}
		return listVo;
	}
}
