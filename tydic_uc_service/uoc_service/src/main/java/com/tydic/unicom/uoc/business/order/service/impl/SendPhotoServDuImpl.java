package com.tydic.unicom.uoc.business.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPersionCertPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderSimCardPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderPersionCertServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderSimCardServ;
import com.tydic.unicom.uoc.business.order.service.interfaces.SendPhotoServDu;
import com.tydic.unicom.uoc.service.common.interfaces.AbilityPlatformServDu;
import com.tydic.unicom.uoc.service.common.interfaces.FileServiceServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class SendPhotoServDuImpl implements SendPhotoServDu{
	
	Logger logger = Logger.getLogger(SendPhotoServDuImpl.class);
	
	@Autowired
	private InfoServiceOrderSimCardServ infoServiceOrderSimCardServ;
	
	@Autowired
	private InfoSaleOrderPersionCertServ infoSaleOrderPersionCertServ;
	
	@Autowired
	private FileServiceServDu fileServiceServDu;
	
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	
	@Autowired
	private AbilityPlatformServDu abilityPlatformServDu;
	
	
	
	@Override
	public UocMessage getSendPhoto(String jsession_id,String serv_order_no) throws Exception{
		
		UocMessage message=new UocMessage();
		if(jsession_id==null ||  "".equals(jsession_id)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("jsession_id不能为空");
			return message;
		}
		if(serv_order_no==null ||  "".equals(serv_order_no)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("serv_order_no不能为空");
			return message;
		}
		
		//根据服务订单号，查询info_serv_sim_card表CARD_DATA_PROC_ID字段
		InfoServiceOrderSimCardPo infoServiceOrderSimCardPo = new InfoServiceOrderSimCardPo();
		infoServiceOrderSimCardPo.setServ_order_no(serv_order_no);		
		List<InfoServiceOrderSimCardPo> infoServiceOrderSimCardPoTemp=infoServiceOrderSimCardServ.queryInfoServiceOrderSimCardByOrderNo(infoServiceOrderSimCardPo);
		if(infoServiceOrderSimCardPoTemp == null){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("服务订单SIM卡信息表无数据");
			return message;
		}
		
		String serviceSn=infoServiceOrderSimCardPoTemp.get(0).getCard_data_proc_id();
		if(serviceSn==null ||  "".equals(serviceSn)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("开户照片上传BSS流水不能为空");
			return message;
		}
		//根据销售订单号查询info_sale_person_cert表
		InfoSaleOrderPersionCertPo infoSaleOrderPersionCertPo = new InfoSaleOrderPersionCertPo();
		infoSaleOrderPersionCertPo.setSale_order_no(infoServiceOrderSimCardPoTemp.get(0).getSale_order_no());
		InfoSaleOrderPersionCertPo infoSaleOrderPersionCertPoTemp=infoSaleOrderPersionCertServ.getInfoSaleOrderPersionCertBySaleOrderNo(infoSaleOrderPersionCertPo);
		if(infoSaleOrderPersionCertPoTemp==null){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("服务订单SIM卡信息表无数据");
			return message;
		}
		
		String certFaceUrl=infoSaleOrderPersionCertPoTemp.getCert_face_url();
		String certBackUrl=infoSaleOrderPersionCertPoTemp.getCert_back_url();
		String picPersonUrl=infoSaleOrderPersionCertPoTemp.getPic_person_url();
		String picGuardianUrl=infoSaleOrderPersionCertPoTemp.getPic_guardian_url();
		String photo1="";
		String photo2="";
		String photo3="";
		String photo4="";
		Map<String,Object> map= new HashMap<String,Object>();
		if(certFaceUrl!=null ||  !"".equals(certFaceUrl)){
			UocMessage certFaceUrlMsg=fileServiceServDu.imageCompressEncode(certFaceUrl);
			if(!"0000".equals(certFaceUrlMsg.getRespCode())){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("开户证件上传照片压缩失败");
				return message;
			}
			photo1=certFaceUrlMsg.getArgs().get("file_base64").toString();
		}
		if(certBackUrl!=null ||  !"".equals(certBackUrl)){
			UocMessage certBackUrlMsg=fileServiceServDu.imageCompressEncode(certFaceUrl);
			if(!"0000".equals(certBackUrlMsg.getRespCode())){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("开户证件上传照片压缩失败");
				return message;
			}
			photo2=certBackUrlMsg.getArgs().get("file_base64").toString();
		}
		if(picPersonUrl!=null ||  !"".equals(picPersonUrl)){
			UocMessage picPersonUrlMsg=fileServiceServDu.imageCompressEncode(certFaceUrl);
			if(!"0000".equals(picPersonUrlMsg.getRespCode())){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("开户证件上传照片压缩失败");
				return message;
			}
			photo3=picPersonUrlMsg.getArgs().get("file_base64").toString();
		}
		if(picGuardianUrl!=null ||  !"".equals(picGuardianUrl)){
			UocMessage picGuardianUrlMsg=fileServiceServDu.imageCompressEncode(certFaceUrl);
			if(!"0000".equals(picGuardianUrlMsg.getRespCode())){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("开户证件上传照片压缩失败");
				return message;
			}
			photo4=picGuardianUrlMsg.getArgs().get("file_base64").toString();
		}
		map.put("SERVICE_NAME", "SendPhoto");
		map.put("Service_sn", serviceSn);
		if(!"".equals(photo1)){
			map.put("Photo1", photo1);
		}
		if(!"".equals(photo2)){
			map.put("Photo2", photo2);
		}
		if(!"".equals(photo3)){
			map.put("Photo3", photo3);
		}
		if(!"".equals(photo4)){
			map.put("Photo4", photo4);
		}
		
		String json_info=jsonToBeanServDu.mapToJson(map);
		//调用BSS servernet
		UocMessage msg=abilityPlatformServDu.CallAbilityPlatform(json_info, "1000", "", "");
		if(!"0000".equals(msg.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("开户证件上传失败");
			return message;
		}
		String code=msg.getArgs().get("code").toString();
		if(code ==null || "".equals(code) || !"200".equals(code)){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("开户证件上传失败");
			return message;
		}	
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("开户证件上传成功");		
		return message;
		
	}

}
