package com.tydic.unicom.ugc.base.dataBase.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.ugc.base.dataBase.interfaces.SkuDefineServ;
import com.tydic.unicom.ugc.base.dataBase.po.SkuDefinePo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("SkuDefineServ")
public class SkuDefineServImpl extends BaseServImpl<SkuDefinePo> implements SkuDefineServ{
	

	private static Logger logger = Logger.getLogger(SkuDefineServImpl.class);

	@Override
	public SkuDefinePo querySkuDefineBySkuId(SkuDefinePo po) throws Exception{
		logger.info("=========query sku_define========");
		Condition condition = Condition.build("querySkuDefineBySkuId").filter(po.convertThis2Map());
		List<SkuDefinePo> list = query(SkuDefinePo.class, condition);
		if(list != null && list.size()>0){
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public boolean addSkuDefinePo(SkuDefinePo po) throws Exception {
		create(SkuDefinePo.class,po);
		return true;
	}

	@Override
	public boolean deleteSkuDefinePo(SkuDefinePo po) throws Exception {
		remove(SkuDefinePo.class,po);
		return true;
	}

	@Override
	public boolean updateSkuDefinePo(SkuDefinePo po) throws Exception {
		update(SkuDefinePo.class,po);
		return true;
	}
	
	@Override
	public List<SkuDefinePo> querySkuDefineBySkuIdOrSkuName(SkuDefinePo po) throws Exception {
		if(po == null){
			return null;
		}
		logger.info("[获取可选sku]==========>begin<==========");
		logger.info("[获取可选sku]==========>请求参数："+po.toString());
		
		Condition condition = Condition.build("querySkuDefineBySkuIdOrSkuName").filter(po.convertThis2Map());
		List<SkuDefinePo> listSku = query(SkuDefinePo.class, condition);
		if( listSku != null && listSku.size()>0 ){
			logger.info("[获取可选sku]==========>响应参数："+listSku.toString());
			logger.info("[获取可选sku]==========> end <==========");
			return listSku;
		}
		return null;
	}
}
