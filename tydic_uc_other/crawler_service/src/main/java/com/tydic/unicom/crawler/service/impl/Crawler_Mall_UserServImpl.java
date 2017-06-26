package com.tydic.unicom.crawler.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.crawler.business.impl.GetInfoServiceBusImpl;
import com.tydic.unicom.crawler.dao.interfaces.Crawler_Mall_UserDao;
import com.tydic.unicom.crawler.dao.po.Crawler_Mall_UserPo;
import com.tydic.unicom.crawler.service.interfaces.Crawler_Mall_UserServ;

@Service
public class Crawler_Mall_UserServImpl implements Crawler_Mall_UserServ{
	private static Logger logger = Logger.getLogger(Crawler_Mall_UserServImpl.class);
	

	@Autowired
	Crawler_Mall_UserDao dao;
	
	@Override
	public boolean create(Crawler_Mall_UserPo po) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Crawler_Mall_UserPo po) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Crawler_Mall_UserPo po) throws Exception {
		return dao.update(po);
	}

	@Override
	public Crawler_Mall_UserPo get(Crawler_Mall_UserPo po) throws Exception {
		return dao.get(po);
	}

	@Override
	public List<Crawler_Mall_UserPo> query(Crawler_Mall_UserPo po) throws Exception {
		try {
			List l = dao.query(po);
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void updatesession(Crawler_Mall_UserPo cmu) throws Exception{
		//select user
		Crawler_Mall_UserPo tmppo;
		try {
			tmppo = get(cmu);
			if(tmppo!=null){
				logger.debug("得到用户数据 " + tmppo.getCrawmu_name());
				tmppo.setCrawmu_basereqcookie(cmu.getCrawmu_basereqcookie());
				tmppo.setCrawmu_rescookie(cmu.getCrawmu_rescookie());
				tmppo.setCrawmu_sessioncookie(cmu.getCrawmu_sessioncookie());
				update(tmppo);
			}else{
				throw new Exception("未找到相应的用户，更新COOKIE失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("未找到相应的用户，更新COOKIE失败！");
		}

	}
	
}

//@Autowired
//Crawler_InfoDao cs;// = new CrawlerSerImpl();
//
//
//@Override
//public void addOrderinfo(Crawler_InfoPo cpp) throws Exception {
//	try {
//		Crawler_InfoPo c = cs.get(cpp);
//		if(c ==null){
//			cs.create(cpp);
//		}else{
//			System.out.println(c.getOrder_info());
//		}
//	} catch (Exception e) {
//		e.printStackTrace();
//		throw e;
//	}		
//}
//
//@Override
//public List<Crawler_InfoPo> querylist(Crawler_InfoPo cpp) throws Exception {
//	try {
//		String sqlwhere  = "";
//		if(!SysUtil.getStr(cpp.getOrder_id()).isEmpty()){
//			sqlwhere = sqlwhere+ " and order_id = '"+ cpp.getOrder_id() +"'";
//		}
//		if(!SysUtil.getStr(cpp.getOrder_malltype()).isEmpty()){
//			sqlwhere = sqlwhere + " and Order_malltype = '"+ cpp.getOrder_malltype() +"'";
//		}
//		if(!SysUtil.getStr(cpp.getOrder_status()).isEmpty()){
//			sqlwhere =sqlwhere + " and ORDER_STATUS = '"+ cpp.getOrder_status() +"'";
//		}
//		cpp.setSql(sqlwhere);
//		List<Crawler_InfoPo> cp = cs.query(cpp);
//		return cp;
//	} catch (Exception e) {
//		e.printStackTrace();
//		throw e;
//	}
//}
//
//@Override
//public Crawler_InfoPo get(Crawler_InfoPo cpp) throws Exception {
//	try {
//		Crawler_InfoPo cp = cs.get(cpp);
//		return cp;
//	} catch (Exception e) {
//		e.printStackTrace();
//		throw e;
//	}
//}
//
//@Override
//public List<CoderegionPo> query_coderegion(CoderegionPo cpp) throws Exception {
//	try {
//		List l = cs.query_coderegion(cpp);
//		return l;
//	} catch (Exception e) {
//		e.printStackTrace();
//		throw e;
//	}
//}
//
//@Override
//public boolean update(Crawler_InfoPo cpp) throws Exception {
//	try {
//		return cs.update(cpp);
//	} catch (Exception e) {
//		e.printStackTrace();
//		throw e;
//	}
//}
//
//
//
//public static void main(String[] args) {
////	String json_main = "{\"ordernum\":\"5651892673\",\"orderid\":\"8117032823571525\",\"姓名\":\"尹梦婷\",\"电话\":\"15223460919\",\"推广渠道\":\"自然订单\",\"context\":{\"0\":{\"商品\":\"微博V卡\"},\"1\":{\"号码\":\"17623105615(重庆)\",\"套餐\":\"微博V卡\"},\"2\":{\"姓名\":\"尹梦婷\",\"证件\":\"500227199810170029(18位身份证)\",\"地址\":\"重庆市璧山县璧城街道黄葛村1组128号\"},\"3\":{}},\"费用\":{\"商品金额总计\":\"0.00\",\"减免费用\":\"0.00\",\"运费\":\"0.00\",\"共需支付\":\"0.00\"},\"other\":{\"支付方式\":\"在线支付(全额支付)\",\"收货地址\":\"重庆，重庆市，璧山县，重庆市璧山区上海城A栋\",\"配送方式\":\"快递0元不限时间送货\",\"期望物流\":\"不限物流\"}}";
//
//	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
//			new String[] { "applicationContext.xml" });
//	// SysPar.ppvo = (ProParamVo) context.getBean("proParamVo");
//	CrwlerSerServDuImpl t = (CrwlerSerServDuImpl) context.getBean("crwlerSerServDu");
//	try {
//		Crawler_InfoPo cpp = new Crawler_InfoPo();
//		cpp.setOrder_status("0");
//		try {
//			List<Crawler_InfoPo> list = t.querylist(cpp);
//			for(Crawler_InfoPo crainfo:list){
//				System.out.println(crainfo.getOrder_info());
//			}
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//}
//
//@Override
//public List<Crawler_SKU_definePo> query_skudefine() throws Exception {
//	try {
//		List l = cs.query_skudefine();
//		return l;
//	} catch (Exception e) {
//		e.printStackTrace();
//		throw e;
//	}
//}