package com.tydic.unicom.uoc.service.common.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderActivityPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderFeePo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPackagePo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPersionPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderProdMapPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderActivityServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderFeeServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderPackageServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderPersionServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderProdMapServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderServ;
import com.tydic.unicom.uoc.service.common.interfaces.FunctionForOutByOrdModServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;

import net.sf.json.JSONObject;

@Service("FunctionForOutByOrdModServDu")
public class FunctionForOutByOrdModServDuImpl  implements FunctionForOutByOrdModServDu{

	private static Logger logger = LoggerFactory.getLogger(FunctionForOutByOrdModServDuImpl.class);
	
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	
	@Autowired
	private InfoServiceOrderServ infoServiceOrderServ;
	
	@Autowired
	private InfoServiceOrderActivityServ infoServiceOrderActivityServ;
	
	@Autowired
	private InfoServiceOrderPersionServ infoServiceOrderPersionServ;
	
	@Autowired
	private InfoServiceOrderFeeServ infoServiceOrderFeeServ;
	
	@Autowired
	private InfoServiceOrderPackageServ infoServiceOrderPackageServ;
	
	@Autowired
	private InfoServiceOrderProdMapServ infoServiceOrderProdMapServ;
	

	@Override
	public String getParam(String param_json,String paramName){

		String value;
		if(param_json==null ||"".equals(param_json)){
			value="";
		}else{
			JSONObject json = JSONObject.fromObject(param_json);

			if(paramName==null || "".equals(paramName)){
				value="";
			}else{
				value=json.get(paramName).toString();
			}
		}
		return value;

	}

	@Override
	public String getSysdate(String type) {
		String sysDateStr="";
		Date sysDate=new Date();
		SimpleDateFormat sf;
		switch(type){
			case "1":
				sf=new SimpleDateFormat("yyyyMMddHHmmss");
				sysDateStr=sf.format(sysDate);
				break;
			case "2":
				sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sysDateStr=sf.format(sysDate);
				break;
			case "3":
				sf=new SimpleDateFormat("yyyyMMdd");
				sysDateStr=sf.format(sysDate);
				break;
			case "4":
				sf=new SimpleDateFormat("yyyy-MM-dd");
				sysDateStr=sf.format(sysDate);
				break;
			default:
				sysDateStr="传入时间类型错误";
				break;
		}
		return sysDateStr;
	}
	
	@Override
	public  String getServOrderNoByOperCode(String operCode,String order_no) throws Exception{
		
		String servOrderNo="";
		InfoServiceOrderPo po=new InfoServiceOrderPo();
		po.setSale_order_no(order_no);
		po.setOper_code(operCode);

		if(infoServiceOrderServ == null){
			infoServiceOrderServ = (InfoServiceOrderServ) ToolSpring.getBean("InfoServiceOrderServ");
		}
		List<InfoServiceOrderPo> list =infoServiceOrderServ.queryInfoServiceOrderByOrderNo(po);
		if(list!=null){
			servOrderNo=list.get(0).getServ_order_no();
		}	
		return servOrderNo;		
	}
	
	@Override
	public  String getActivityInfo(String orderNo)  throws Exception{
		
		JSONObject data = new JSONObject();
		List<Object> list = new ArrayList<Object>();
		InfoServiceOrderActivityPo po = new InfoServiceOrderActivityPo();
		po.setServ_order_no(orderNo);
		if(infoServiceOrderActivityServ == null){
			infoServiceOrderActivityServ = (InfoServiceOrderActivityServ) ToolSpring.getBean("InfoServiceOrderActivityServ");
		}
		List<InfoServiceOrderActivityPo> poTemp=infoServiceOrderActivityServ.queryInfoServiceOrderActivityByOrderNo(po);
		if(poTemp!=null && poTemp.size()>0){
			data.put("actPlanId", poTemp.get(0).getActivity_id());
			list.add(data);
			return list.toString();
		}else{
			return "";
		}
		
		
	}
	
	
	@Override
	public  String transCertTypeCq (String orderNo)  throws Exception{
		
		/**
		 * CB证件类型
		 * 01	15位身份证
           02	18位身份证
           03	驾驶证
           04	军官证
           05	教师证
           06	学生证
           07	营业执照
           08	护照
           99	其它
		 */
		
		/**
		 * BSS证件类型
		 * 01 18位身份证
		   02 护照
           03 学生证
           04 教师证
           05 军官证
           06 营业执照
           07 驾驶证
           08 武警身份证
           09 户口本
           10 港澳居民来往内地通行证
           11 台湾居民来往大陆通行证
           12 15位身份证
           13 其他
		 */
		
		String bss_cert_type="13";
		InfoServiceOrderPersionPo po= new InfoServiceOrderPersionPo();
		po.setServ_order_no(orderNo);
		if(infoServiceOrderPersionServ == null){
			infoServiceOrderPersionServ = (InfoServiceOrderPersionServ) ToolSpring.getBean("InfoServiceOrderPersionServ");
		}
		
		List<InfoServiceOrderPersionPo> poTemp=infoServiceOrderPersionServ.queryInfoServiceOrderPersionByOrderNo(po);
		if(poTemp!=null && poTemp.size()>0){
			String certType=poTemp.get(0).getCert_type();
			if("02".equals(certType)){
				bss_cert_type="01";
			}else if("01".equals(certType)){
				bss_cert_type="12";
			}else if("03".equals(certType)){
				bss_cert_type="07";
			}else if("04".equals(certType)){
				bss_cert_type="05";
			}else if("05".equals(certType)){
				bss_cert_type="04";
			}else if("06".equals(certType)){
				bss_cert_type="03";
			}else if("07".equals(certType)){
				bss_cert_type="06";
			}else if("08".equals(certType)){
				bss_cert_type="02";
			}else if("99".equals(certType)){
				bss_cert_type="13";
			}else{
				bss_cert_type="13";
			}
		}
		
		return bss_cert_type;
	}

	/**
	 * 
	 * @author heguoyong 2017年5月11日 下午12:33:43
	 * @Method: getFeeInfo 
	 * @Description: 出库函数info_serv_fee中的费用数据拼装json串
	 */
	@Override
	public String getFeeInfo(String orderNo) throws Exception{
		logger.info("进入getFeeInfo出库函数:"+orderNo);
		List<Object> list = new ArrayList<Object>();
		InfoServiceOrderFeePo po = new InfoServiceOrderFeePo();
		po.setServ_order_no(orderNo);
		if(infoServiceOrderFeeServ == null){
			infoServiceOrderFeeServ = (InfoServiceOrderFeeServ) ToolSpring.getBean("InfoServiceOrderFeeServ");
		}
		List<InfoServiceOrderFeePo> poTemps=infoServiceOrderFeeServ.queryInfoServiceOrderFeeByServOrderNo(po);
		if(poTemps!=null && poTemps.size()>0){
			JSONObject data = null;
			for(InfoServiceOrderFeePo poTemp:poTemps ){
				data = new JSONObject();
				data.put("feeId", poTemp.getFee_item_code());
				data.put("feeCategory", poTemp.getFee_item_type());
				data.put("feeDes", poTemp.getFee_item_name());
				data.put("origFee", poTemp.getTotal_fee());
				data.put("reliefFee", poTemp.getDiscount_fee());
				data.put("reliefResult", poTemp.getDiscount_comments());
				data.put("realFee", poTemp.getPayed_fee());
				list.add(data);
			}
			logger.info("getFeeInfo出库函数返回数据:"+list.toString());
			return list.toString();
		}else{
			return "";
		}
	}
	
	
	/**
	 * 
	 * @author yangxiaofeng 2017年5月11日 下午12:33:43
	 * @Method: getProductInfo 
	 * @Description: 获取产品和附件包信息
	 */
	@Override
	public String getProductInfo(String orderNo) throws Exception{
		logger.info("进入getProductInfo出库函数:"+orderNo);
		List<Object> list = new ArrayList<Object>();
		JSONObject data = new JSONObject();
		List<Object> list1 = new ArrayList<Object>();
		JSONObject data1 = null;
		//获取产品附加包信息
		InfoServiceOrderPackagePo infoServiceOrderPackagePo = new InfoServiceOrderPackagePo();
		infoServiceOrderPackagePo.setServ_order_no(orderNo);
		if(infoServiceOrderPackageServ == null){
			infoServiceOrderPackageServ = (InfoServiceOrderPackageServ) ToolSpring.getBean("InfoServiceOrderPackageServ");
		}
		List<InfoServiceOrderPackagePo> pkgList=infoServiceOrderPackageServ.queryInfoServiceOrderPackageByOrderNo(infoServiceOrderPackagePo);
		if(pkgList!=null && pkgList.size()>0){
			for(InfoServiceOrderPackagePo po:pkgList){
				data1= new JSONObject();
				data1.put("packageId", po.getPack_code());
				data1.put("elementId", po.getExt_field_1());
				data1.put("elementType", po.getExt_field_2());
				list1.add(data1);
			}
			if(list1!=null && list1.size()>0){
				data.put("packageElement", list1);
			}
		}
		
		//获取产品信息
		InfoServiceOrderProdMapPo infoServiceOrderProdMapPo = new InfoServiceOrderProdMapPo();
		infoServiceOrderProdMapPo.setServ_order_no(orderNo);
		if(infoServiceOrderProdMapServ == null){
			infoServiceOrderProdMapServ = (InfoServiceOrderProdMapServ) ToolSpring.getBean("InfoServiceOrderProdMapServ");
		}
		
		List<InfoServiceOrderProdMapPo> prodList=infoServiceOrderProdMapServ.queryInfoServiceOrderProdMapByOrderNo(infoServiceOrderProdMapPo);
		if(prodList!=null && prodList.size()>0){
			data.put("productId", prodList.get(0).getProd_code());
			data.put("productMode", "1");
			list.add(data);
		}		
		return list.toString();
	}
}
