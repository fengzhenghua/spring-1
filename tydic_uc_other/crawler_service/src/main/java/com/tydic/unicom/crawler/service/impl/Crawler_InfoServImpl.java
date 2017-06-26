package com.tydic.unicom.crawler.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.tydic.unicom.crawler.common.SysUtil;
import com.tydic.unicom.crawler.dao.interfaces.Crawler_InfoDao;
import com.tydic.unicom.crawler.dao.po.Crawler_InfoPo;
import com.tydic.unicom.crawler.service.interfaces.Crawler_InfoServ;

@Service()
public class Crawler_InfoServImpl implements Crawler_InfoServ{
	private static Logger logger = Logger.getLogger(Crawler_InfoServImpl.class);

	
	@Autowired
	Crawler_InfoDao cs;// = new CrawlerSerImpl();

	@Override
	public boolean create(Crawler_InfoPo po) throws Exception {
		try {
			Crawler_InfoPo c = cs.get(po);
			if(c ==null){
				cs.create(po);
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public boolean delete(Crawler_InfoPo po) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Crawler_InfoPo po) throws Exception {
		try {
			return cs.update(po);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Crawler_InfoPo get(Crawler_InfoPo po) throws Exception {
		try {
			Crawler_InfoPo cp = cs.get(po);
			return cp;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Crawler_InfoPo> likequery(Crawler_InfoPo po) throws Exception {
		try {
			String sqlwhere  = "";
			String sql = "";
			logger.debug(" is tre : "+(po==null || (SysUtil.getStr(po.getOrder_status()).isEmpty() && SysUtil.getStr(po.getCi_uuid()).isEmpty())) );
			if((po==null || (SysUtil.getStr(po.getOrder_status()).isEmpty() && SysUtil.getStr(po.getCi_uuid()).isEmpty())) ){
				return new ArrayList();
			}else{
				
				logger.debug("likequery par: "+po.convertToMap());
				
				sql = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT * FROM crawler_info where 1=1 ";
				
				if(!SysUtil.getStr(po.getOrder_status()).isEmpty()){
					sql = sql+ " and (order_status = '"+ po.getOrder_status() +"')";
				}
				if(po.getAdmin()==0){
					sql =sql + " and (CRAWMU_UUID = '"+ SysUtil.getStr(po.getCrawmu_uuid()) +"')";
				}
				
				if(!SysUtil.getStr(po.getCi_uuid()).isEmpty()){
					String likestr = po.getCi_uuid();
					sqlwhere = sqlwhere+ " ORDER_ID like '%"+ likestr +"%'";
					sqlwhere = sqlwhere+ " or SALE_ORDER_NO like '%"+ likestr +"%'";
					sqlwhere = sqlwhere+ " or SERV_ORDER_NO_LIST like '%"+ likestr +"%'";
					sqlwhere = sqlwhere+ " or LOGISTICS_NO like '%"+ likestr +"%'";
					sqlwhere = sqlwhere+ " or PRODUCT_ID like '%"+ likestr +"%'";
					sqlwhere = sqlwhere+ " or SERIAL_NUMBER like '%"+ likestr +"%'";
					sqlwhere = sqlwhere+ " or CUSTOMER_NAME like '%"+ likestr +"%'";
					sqlwhere = sqlwhere+ " or CBSSNUM like '%"+ likestr +"%'";
					sqlwhere = sqlwhere+ " or USIM like '%"+ likestr +"%'";
					sqlwhere = sqlwhere+ " or CERT_NUM like '%"+ likestr +"%'";
					sqlwhere = "and (" + sqlwhere +")";
				}
				
				sql = sql +sqlwhere+ " ORDER BY ORDER_CREATETIME desc,ORDER_STATUS ASC) A WHERE ROWNUM < 1*100 ) WHERE RN >=(1-1)*100";
				logger.debug("sql "+sql);
				po.setSql(sql);
				List<Crawler_InfoPo> cp = cs.execsql(po);
				return cp;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Override
	public List<Crawler_InfoPo> query(Crawler_InfoPo po) throws Exception {
		try {
			String sqlwhere  = "";
			if(!SysUtil.getStr(po.getCi_uuid()).isEmpty()){
				sqlwhere = sqlwhere+ " and ci_uuid = '"+ po.getCi_uuid() +"'";
			}
			if(!SysUtil.getStr(po.getOrder_id()).isEmpty()){
				sqlwhere = sqlwhere+ " and order_id = '"+ po.getOrder_id() +"'";
			}
			if(!SysUtil.getStr(po.getOrder_malltype()).isEmpty()){
				sqlwhere = sqlwhere + " and Order_malltype = '"+ po.getOrder_malltype() +"'";
			}
			if(!SysUtil.getStr(po.getOrder_status()).isEmpty()){
				sqlwhere =sqlwhere + " and ORDER_STATUS = '"+ po.getOrder_status() +"'";
			}
			if(!SysUtil.getStr(po.getCrawmu_uuid()).isEmpty()){
				sqlwhere =sqlwhere + " and CRAWMU_UUID = '"+ po.getCrawmu_uuid() +"'";
			}
			
			if(!SysUtil.getStr(po.getJsessionid()).isEmpty()){
				sqlwhere =sqlwhere + " and jsessionid = '"+ po.getJsessionid() +"'";
			}
			
			if(!SysUtil.getStr(po.getSale_order_no()).isEmpty()){
				sqlwhere =sqlwhere + " and sale_order_no = '"+ po.getSale_order_no() +"'";
			}
			
			if(!SysUtil.getStr(po.getServ_order_no_list()).isEmpty()){
				sqlwhere =sqlwhere + " and serv_order_no_list = '"+ po.getServ_order_no_list() +"'";
			}
			po.setSql(sqlwhere);
			List<Crawler_InfoPo> cp = cs.query(po);
			return cp;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	
	

	
	public void addOrderinfo(Crawler_InfoPo cpp) throws Exception {
		try {
			Crawler_InfoPo c = cs.get(cpp);
			if(c ==null){
				cs.create(cpp);
			}else{
				System.out.println(c.getOrder_info());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}		
	}
	
	public List<Crawler_InfoPo> querylist(Crawler_InfoPo cpp) throws Exception {
		try {
			String sqlwhere  = "";
			if(!SysUtil.getStr(cpp.getOrder_id()).isEmpty()){
				sqlwhere = sqlwhere+ " and order_id = '"+ cpp.getOrder_id() +"'";
			}
			if(!SysUtil.getStr(cpp.getOrder_malltype()).isEmpty()){
				sqlwhere = sqlwhere + " and Order_malltype = '"+ cpp.getOrder_malltype() +"'";
			}
			if(!SysUtil.getStr(cpp.getOrder_status()).isEmpty()){
				sqlwhere =sqlwhere + " and ORDER_STATUS = '"+ cpp.getOrder_status() +"'";
			}
			cpp.setSql(sqlwhere);
			List<Crawler_InfoPo> cp = cs.query(cpp);
			return cp;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
//	@Override
//	public List<CoderegionPo> query_coderegion(CoderegionPo cpp) throws Exception {
//		try {
//			List l = cs.query_coderegion(cpp);
//			return l;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//	}
	
	
	public static void main(String[] args) {
//		String json_main = "{\"ordernum\":\"5651892673\",\"orderid\":\"8117032823571525\",\"姓名\":\"尹梦婷\",\"电话\":\"15223460919\",\"推广渠道\":\"自然订单\",\"context\":{\"0\":{\"商品\":\"微博V卡\"},\"1\":{\"号码\":\"17623105615(重庆)\",\"套餐\":\"微博V卡\"},\"2\":{\"姓名\":\"尹梦婷\",\"证件\":\"500227199810170029(18位身份证)\",\"地址\":\"重庆市璧山县璧城街道黄葛村1组128号\"},\"3\":{}},\"费用\":{\"商品金额总计\":\"0.00\",\"减免费用\":\"0.00\",\"运费\":\"0.00\",\"共需支付\":\"0.00\"},\"other\":{\"支付方式\":\"在线支付(全额支付)\",\"收货地址\":\"重庆，重庆市，璧山县，重庆市璧山区上海城A栋\",\"配送方式\":\"快递0元不限时间送货\",\"期望物流\":\"不限物流\"}}";

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
		// SysPar.ppvo = (ProParamVo) context.getBean("proParamVo");
		Crawler_InfoServImpl t = (Crawler_InfoServImpl) context.getBean("crwlerSerServDu");
		try {
			Crawler_InfoPo cpp = new Crawler_InfoPo();
			cpp.setOrder_status("0");
			try {
				List<Crawler_InfoPo> list = t.querylist(cpp);
				for(Crawler_InfoPo crainfo:list){
					System.out.println(crainfo.getOrder_info());
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Crawler_InfoPo queryCrawlerInfoByOrderNo(
			Crawler_InfoPo Crawler_InfoPo) throws Exception {
		return cs.queryCrawlerInfoByOrderNo(Crawler_InfoPo);
	}
	

	@Override
	public List<Crawler_InfoPo> getCountStatus(
			Crawler_InfoPo crawler_InfoPo) throws Exception {
		return cs.getCountStatus(crawler_InfoPo);
	}
	
	
	
}
