package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;




import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoPayOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderFeePo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderExtServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderFeeServ;
import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderFeeServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoPayOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderFeeVo;

@Service("InfoSaleOrderFeeServDu")
public class InfoSaleOrderFeeServDuImpl implements InfoSaleOrderFeeServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderFeeServDuImpl.class);
	@Autowired
	private InfoSaleOrderFeeServ infoSaleOrderFeeServ;
	@Autowired
	private RedisServiceServ redisServiceServ;
	@Autowired
	private InfoSaleOrderExtServ infoSaleOrderExtServ;
	@Override
	public boolean createInfoSaleOrderFee(InfoSaleOrderFeeVo vo)
			throws Exception {
		logger.info("----------------createInfoSaleOrderFee-----------------------");
		InfoSaleOrderFeePo infoSaleOrderFeePo =new InfoSaleOrderFeePo();
		BeanUtils.copyProperties(vo,infoSaleOrderFeePo);
		
		infoSaleOrderFeeServ.createInfoSaleOrderFee(infoSaleOrderFeePo);

		logger.info("----------------createInfoSaleOrderFee---------end--------------");
		return true;
	}
	@Override
	public List<InfoSaleOrderFeeVo> queryInfoSaleOrderFeeByPayOrder(
			InfoPayOrderVo vo) throws Exception {
		InfoPayOrderPo infoPayOrderPo =new InfoPayOrderPo();
		BeanUtils.copyProperties(vo, infoPayOrderPo);
		List<InfoSaleOrderFeeVo> listVo = new ArrayList<InfoSaleOrderFeeVo>();
		List<InfoSaleOrderFeePo> listPo = infoSaleOrderFeeServ.queryInfoSaleOrderFeeByPayOrder(infoPayOrderPo);
		if(listPo != null && listPo.size() > 0){
			for(InfoSaleOrderFeePo po : listPo){
				InfoSaleOrderFeeVo ordVo = new InfoSaleOrderFeeVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}
		return null;
	}
	@Override
	public InfoSaleOrderFeeVo queryInfoSaleOrderFeeBySaleOrderNo(
			InfoSaleOrderFeeVo vo) throws Exception {
		InfoSaleOrderFeePo infoSaleOrderFeePo =new InfoSaleOrderFeePo();
		BeanUtils.copyProperties(vo, infoSaleOrderFeePo);
		InfoSaleOrderFeeVo ordVo = new InfoSaleOrderFeeVo();
		InfoSaleOrderFeePo po = infoSaleOrderFeeServ.getInfoSaleOrderFeePoBySaleOrderNo(infoSaleOrderFeePo);	
		if(po != null){
			BeanUtils.copyProperties(po, ordVo);
			return ordVo;
		}else{
			return null;
		}
		
	}

}
