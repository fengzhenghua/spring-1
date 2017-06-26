package com.tydic.unicom.apc.base.pub.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.apc.base.pub.interfaces.ApDefineServ;
import com.tydic.unicom.apc.base.pub.po.ApDefinePo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("ApDefineServ")
public class ApDefineServImpl extends BaseServImpl<ApDefinePo> implements ApDefineServ {
	
	Logger logger = LoggerFactory.getLogger(ApDefineServImpl.class);

	@Override
	public ApDefinePo getApDefinePo(ApDefinePo po) throws Exception {

		return get(ApDefinePo.class,po);
	}

	@Override
	public boolean addApDefinePo(ApDefinePo po) throws Exception {
		try{
			create(ApDefinePo.class,po);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean deleteApDefinePo(ApDefinePo po) throws Exception {
		try{
			remove(ApDefinePo.class,po);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean updateApDefinePo(ApDefinePo po) throws Exception {
		try{
			update(ApDefinePo.class,po);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public List<ApDefinePo> queryApDefinePageByPo(ApDefinePo po, int pageNo, int pageSize) throws Exception {
		if (logger.isInfoEnabled()) {
			logger.info("INFO [获取触点信息]==========>请求参数：",po.toString());
		}
		int number = (pageNo - 1) * pageSize;
		List<ApDefinePo> list = S.get(ApDefinePo.class).page(Condition.build("queryApDefinePageByPo").filter(po.convertThis2Map()), number,
				pageSize);
		if (logger.isInfoEnabled()) {
			logger.info("INFO [获取触点信息]==========>响应参数：共返回{}条记录。",list.size());
		}
		return list != null && list.size() > 0 ? list : null;
	}

	@Override
	public int queryApDefineCount(ApDefinePo po) throws Exception {
		Condition con = Condition.build("queryApDefineByPo").filter(po.convertThis2Map());
		List<ApDefinePo> list = query(ApDefinePo.class, con);
		return list != null && list.size() > 0 ? list.size() : 0;
	}

	@Override
	public List<ApDefinePo> queryApDefineByApIdOrApName(ApDefinePo po)
			throws Exception {
		Condition con = Condition.build("queryApDefineByApIdOrApName").filter(po.convertThis2Map());
		List<ApDefinePo> list = query(ApDefinePo.class, con);
		return list != null && list.size() > 0 ? list : null;
	}

	@Override
	public List<ApDefinePo> queryApDefineAll(ApDefinePo po) throws Exception {
		Condition con = Condition.build("queryApDefineAll").filter(po.convertThis2Map());
		List<ApDefinePo> list = query(ApDefinePo.class, con);
		return list != null && list.size() > 0 ? list : null;
	}

	@Override
	public List<ApDefinePo> queryEffectiveApDefinePage(ApDefinePo po,int pageNo, int pageSize) throws Exception {
		
		if (logger.isInfoEnabled()) {
			logger.info("INFO [获取触点信息]==========>请求参数：{}",po.toString());
		}
		
		int number = (pageNo - 1) * pageSize;
		List<ApDefinePo> list = S.get(ApDefinePo.class).page(Condition.build("queryEffectiveApDefinePage").filter(po.convertThis2Map()), number,pageSize);
		
		if (logger.isInfoEnabled()) {
			logger.info("INFO [获取触点信息]==========>响应参数：共返回{}条记录。",list.size());
		}
		
		return list != null && list.size() > 0 ? list : null;
	}

	@Override
	public List<ApDefinePo> queryInvalidApDefinePage(ApDefinePo po, int pageNo,int pageSize) throws Exception {
		int number = (pageNo - 1) * pageSize;
		List<ApDefinePo> list = S.get(ApDefinePo.class).page(Condition.build("queryInvalidApDefinePage").filter(po.convertThis2Map()), number,pageSize);
		return list != null && list.size() > 0 ? list : null;
	}

	@Override
	public int queryEffectiveApDefineCount(ApDefinePo po) throws Exception {
		Condition con = Condition.build("queryEffectiveApDefineCount").filter(po.convertThis2Map());
		List<ApDefinePo> list = query(ApDefinePo.class, con);
		return list != null && list.size() > 0 ? list.size() : 0;
	}

	@Override
	public int queryInvalidApDefineCount(ApDefinePo po) throws Exception {
		Condition con = Condition.build("queryInvalidApDefineCount").filter(po.convertThis2Map());
		List<ApDefinePo> list = query(ApDefinePo.class, con);
		return list != null && list.size() > 0 ? list.size() : 0;
	}

	@Override
	public List<ApDefinePo> queryApDefineByPo(ApDefinePo po) throws Exception {
		Condition con = Condition.build("queryApDefineByPo").filter(po.convertThis2Map());
		List<ApDefinePo> list = query(ApDefinePo.class, con);
		return list != null && list.size() > 0 ? list : null;
	}

}
