package com.tydic.unicom.uoc.business.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.service.interfaces.ServiceOrderExprotServDu;
import com.tydic.unicom.uoc.business.order.service.vo.InfoServiceOrderAuidtVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderPersionServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderPersionVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年4月14日 上午10:45:45
 * @ClassName ServiceOrderExprotServDuImpl
 * @Description 服务订单报表服务
 * @version V1.0
 */
public class ServiceOrderExprotServDuImpl implements ServiceOrderExprotServDu{

	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServ;
	
	@Autowired
	private InfoSaleOrderPersionServDu infoSaleOrderPersionServDu;
	@Override
	public List<InfoServiceOrderAuidtVo> getServiceOrderAudit(ParaVo paraVo) throws Exception {
		
		List<InfoServiceOrderAuidtVo> auditOrderList = new ArrayList<InfoServiceOrderAuidtVo>();
		InfoServiceOrderVo vo = new InfoServiceOrderVo();
		
		vo.setProvince_code("83");
		vo.setArea_code("831");
		vo.setAccept_oper_no(paraVo.getAccept_no());
		List<InfoServiceOrderVo> serviceOrderList =  infoServiceOrderServ.queryInfoServiceOrderByVo(vo);
		if(serviceOrderList == null ||serviceOrderList.size() == 0 ){
			return auditOrderList;
		}
		for(InfoServiceOrderVo orderVo:serviceOrderList){
			InfoServiceOrderAuidtVo auditVo = new InfoServiceOrderAuidtVo();
			BeanUtils.copyProperties(auditVo, orderVo);
			auditVo.setAccept_date(orderVo.getCreate_time().substring(0,10));
			auditVo.setAccept_time(orderVo.getCreate_time().substring(11, orderVo.getCreate_time().length()));
			//后期优化为一个关联查询，暂时使用多查询 
			InfoSaleOrderPersionVo persionVo = getSalseOrderPerson(orderVo.getSale_order_no());
			if(persionVo !=null){
				auditVo.setCust_name(persionVo.getCust_name());
				auditVo.setCust_address(persionVo.getCert_address());
				auditVo.setConect_number(persionVo.getCust_phone());
				auditVo.setCert_id(persionVo.getCert_no());
			}
			auditVo.setAutid_status(getServiceOrderAutidStatus(orderVo.getServ_order_no()));
			auditOrderList.add(auditVo);
		}
		return auditOrderList;
	}
	
	private InfoSaleOrderPersionVo getSalseOrderPerson(String sale_order_no) throws Exception{
		InfoSaleOrderPersionVo persionVo = new InfoSaleOrderPersionVo();
		persionVo.setSale_order_no(sale_order_no);
		return infoSaleOrderPersionServDu.getInfoSaleOrderPersionBySaleOrderNo(persionVo);
	}
	
	private String getServiceOrderAutidStatus(String order_no ){
		
		return "待处理";
	}
}
