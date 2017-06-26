package com.tydic.unicom.apc.business.pub.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import com.tydic.unicom.apc.base.order.interfaces.InfoOrderAttrServ;
import com.tydic.unicom.apc.base.order.po.InfoOrderAttrPo;
import com.tydic.unicom.apc.business.pub.interfaces.OppoExamineServiceServDu;
import com.tydic.unicom.apc.common.wxpay.WXSignUtil;
import com.tydic.unicom.apc.service.common.interfaces.ApcRedisServiceServDu;
import com.tydic.unicom.apc.service.common.interfaces.FileServiceServDu;
import com.tydic.unicom.apc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.apc.service.order.interfaces.InfoOrderAttrServDu;
import com.tydic.unicom.apc.service.pub.interfaces.OppoDSFaceServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class OppoExamineServiceServDuImpl implements OppoExamineServiceServDu{

	private static Logger logger = Logger.getLogger(OppoExamineServiceServDuImpl.class);
	
	private static Map<String, Object[]> wxJSMap = new HashMap<>();
	
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	
	@Autowired
	private FileServiceServDu fileServiceServDu;
	
	@Autowired
	private OppoDSFaceServDu oppoDSFaceServDu;
	
	@Autowired
	private InfoOrderAttrServ infoOrderAttrServ;
	
	@Autowired
	private InfoOrderAttrServDu infoOrderAttrServDu;
	
	@Autowired
	private ApcRedisServiceServDu apcRedisServiceServDu;
	
	@Override
	public UocMessage oppoLivingExamineSubmit(String order_id, String validate_data, String video,String video_file_name, String video_server_id) throws Exception {
		UocMessage uocMessage = new UocMessage();
			
		String compare_fail_times = "0";
		String idcard_number = "";
		String idcard_name = "";
		
		try{
			String appid = propertiesParamVo.getFaceAppid();
			String bucket = propertiesParamVo.getFaceBucket();	
			
			String path = propertiesParamVo.getOppoPicPath();
			
			//查询订单属性表
			InfoOrderAttrPo infoOrderAttrPo = new InfoOrderAttrPo();
			infoOrderAttrPo.setOrder_id(order_id);
			List<InfoOrderAttrPo> orderAttrList = infoOrderAttrServ.queryInfoOrderAttrByOrderId(infoOrderAttrPo);
			for(int i=0;i<orderAttrList.size();i++){
				if("customer_name".equals(orderAttrList.get(i).getAttr_id())){
					idcard_name = orderAttrList.get(i).getAttr_value();
				}
				else if("id_number".equals(orderAttrList.get(i).getAttr_id())){
					idcard_number = orderAttrList.get(i).getAttr_value();
				}
				else if("compare_fail_times".equals(orderAttrList.get(i).getAttr_id())){
					compare_fail_times = orderAttrList.get(i).getAttr_value();
				}
			}
			
			if(StringUtils.isEmpty(compare_fail_times)){
				compare_fail_times = "0";
			}
			
			//传base64进去
			File sfile=new File(path + "/" + video);
			byte sb[]=FileUtils.readFileToByteArray(sfile);
			String b64str=new String(new Base64().encode(sb));
			Map<String, String> map = oppoDSFaceServDu.oppoLivingExamine(appid, bucket, validate_data, b64str, idcard_number, idcard_name);
			logger.info("活体审核返回map======="+map);
			uocMessage.addArg("video_file_name", video_file_name);
			if(map != null){
				if("00000".equals(map.get("RESP_CODE"))){
					String jsonstr = map.get("jsonstr");
					logger.info("活体审核返回结果字符串======="+jsonstr);
					logger.info("前台传的video_server_id======="+video_server_id);
					logger.info("前台传的video======="+video);
					logger.info("前台传的validate_data======="+validate_data);
					//data.live_status	Int	返回活体检测错误码,非0值为出错
					// data.live_msg	String	返回错误描述
					//data.compare_status	Int	返回人脸对比检测错误码,非0值为出错
				    //data.compare_msg	String	返回错误描述
					//data.sim	Int	相似度
					//data.video_photo	String	视频中的一张sim值最大的图像，base64编码。
					//code	Int	返回状态码
					//message	String	返回错误消息
					JSONObject json = JSONObject.fromObject(jsonstr);
					if(json.getInt("code") == 0){
						JSONObject dataObj = json.getJSONObject("data");
						int live_status = dataObj.getInt("live_status");
						if(live_status == 0){
							int sim = dataObj.getInt("sim");
							//记录info_order_attr
							/*List<InfoOrderAttrPo> attrInfoList = new ArrayList<>();
							
							InfoOrderAttrPo orderAttr = new InfoOrderAttrPo();
							orderAttr.setOrder_id(order_id);
							orderAttr.setAttr_type("200");
							orderAttr.setAttr_id("video_file_name");
							orderAttr.setAttr_value(video_file_name);
							attrInfoList.add(orderAttr);
							
							InfoOrderAttrPo simAttr = new InfoOrderAttrPo();
							simAttr.setOrder_id(order_id);
							simAttr.setAttr_type("200");
							simAttr.setAttr_id("video_live_sim");
							simAttr.setAttr_value(sim+"");
							attrInfoList.add(simAttr);
							
							InfoOrderAttrPo serverAttr = new InfoOrderAttrPo();
							serverAttr.setOrder_id(order_id);
							serverAttr.setAttr_type("200");
							serverAttr.setAttr_id("video_server_id");
							serverAttr.setAttr_value(video_server_id);
							attrInfoList.add(serverAttr);
							
							infoOrderAttrServDu.updateInfoOrderAttrList(attrInfoList);*/
							
							//对比
							int minSim = 60;
							//minSim = Integer.parseInt(propertiesParamVo.getOppoLiveSim());
							minSim = Integer.parseInt(propertiesParamVo.getMin_sim());
							if(sim < minSim){
								uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
								uocMessage.setContent("活体检测失败! 相似度不符合要求，请重新拍摄");
							}
							else{
								/*List<InfoOrderAttrPo> infoOrderAttrUpdateList1 = new ArrayList<>();
								InfoOrderAttrPo orderStatus = new InfoOrderAttrPo();
								orderStatus.setOrder_id(order_id);
								orderStatus.setAttr_type("200");
								orderStatus.setAttr_id("order_status");
								orderStatus.setAttr_value("A70");
								infoOrderAttrUpdateList1.add(orderStatus);
								infoOrderAttrServDu.updateInfoOrderAttrList(infoOrderAttrUpdateList1);*/
								
								
								uocMessage.addArg("video_live_sim", sim+"");
								uocMessage.addArg("order_status", "A70");
								uocMessage.addArg("compare_fail_times", "0");
								
								uocMessage.setRespCode(RespCodeContents.SUCCESS);
								uocMessage.setContent("活体检测成功!");
							}
						}
						else{
							uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
							uocMessage.setContent("活体检测失败!"+getLiveCodeMsg(live_status));
						}
					}
					else{
						uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
						uocMessage.setContent("活体检测失败!"+getLiveCodeMsg(json.getInt("code")));
					}
				}
				else{
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent("活体检测失败!"+map.get("RESP_DESC"));
				}
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("活体检测失败!");
			}
		}catch(Exception e){
			logger.error("活体检测失败!"+e.getMessage(), e);
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("活体检测失败!"+e.getMessage());
			return uocMessage;
		}
		logger.info("活体检测结果======"+uocMessage.getRespCode());
		//校验失败状态都改成A71
		if(!RespCodeContents.SUCCESS.equals(uocMessage.getRespCode())){
			uocMessage.addArg("order_status", "A71");
			uocMessage.addArg("video_live_sim", "0");
			//记录失败次数
			int failTimes = Integer.parseInt(compare_fail_times);
			failTimes ++;
			//把失败次数返回到前台
			logger.info("失败次数--------"+failTimes);
			uocMessage.addArg("compare_fail_times", failTimes+"");
			/*try{
				List<InfoOrderAttrPo> infoOrderAttrUpdateList2 = new ArrayList<>();
				//更新订单状态
				InfoOrderAttrPo orderStatus = new InfoOrderAttrPo();
				orderStatus.setOrder_id(order_id);
				orderStatus.setAttr_type("100");
				orderStatus.setAttr_id("order_status");
				orderStatus.setAttr_value("A71");
				infoOrderAttrUpdateList2.add(orderStatus);
				
				//记录失败次数
				int failTimes = Integer.parseInt(compare_fail_times);
				failTimes ++;
				InfoOrderAttrPo failAttr = new InfoOrderAttrPo();
				failAttr.setOrder_id(order_id);
				failAttr.setAttr_type("200");
				failAttr.setAttr_id("compare_fail_times");
				failAttr.setAttr_value(failTimes + "");
				infoOrderAttrUpdateList2.add(failAttr);
				
				//视频文件
				InfoOrderAttrPo videoFileName = new InfoOrderAttrPo();
				videoFileName.setOrder_id(order_id);
				videoFileName.setAttr_type("200");
				videoFileName.setAttr_id("video_file_name");
				videoFileName.setAttr_value(video);
				infoOrderAttrUpdateList2.add(videoFileName);
				
				//WX server_id地址
				InfoOrderAttrPo videoServiceId = new InfoOrderAttrPo();
				videoServiceId.setOrder_id(order_id);
				videoServiceId.setAttr_type("200");
				videoServiceId.setAttr_id("video_server_id");
				videoServiceId.setAttr_value(video_server_id);
				infoOrderAttrUpdateList2.add(videoServiceId);
				infoOrderAttrServDu.updateInfoOrderAttrList(infoOrderAttrUpdateList2);
				//把失败次数返回到前台
				logger.info("失败次数--------"+failTimes);
				uocMessage.addArg("compare_fail_times", failTimes);
			}catch(Exception e){
				logger.error("更新活体数据A71失败!"+e.getMessage(), e);
				uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				uocMessage.setContent("更新订单属性表失败");
				return uocMessage;
			}*/
			
		}
		return uocMessage;
	}
	
	@Override
	public UocMessage getLipLanguageInfo(String mcht_flag, String url) throws Exception {
		UocMessage uocMessage = new UocMessage();
		
		try{
			String appid = propertiesParamVo.getFaceAppid();
			String bucket = propertiesParamVo.getFaceBucket();
			Map<String, String> map = oppoDSFaceServDu.getLipLanguageInfo(appid, bucket);
			if(map != null){
				if("00000".equals(map.get("RESP_CODE"))){
					String jsonstr = map.get("jsonstr");			
//					 data.validate_data	String	唇语验证字符串
//						code	Int	返回状态码
//						message	String	返回错误消息					
					JSONObject json = JSONObject.fromObject(jsonstr);
					//获取成功保存到attr表
					if(json.getInt("code") == 0){
						JSONObject dataObj = json.getJSONObject("data");
						uocMessage.setRespCode(RespCodeContents.SUCCESS);
						uocMessage.addArg("validate_data", dataObj.getString("validate_data"));
						return uocMessage;	
					}
					else{
						uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
						uocMessage.setContent("获取唇语数据失败!"+json.getString("message"));
					}	
				}
				else{
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent("获取唇语数据失败!"+map.get("RESP_DESC"));
				}
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("获取唇语数据失败!");
			}
		}catch(Exception e){
			logger.error("获取唇语数据失败!"+e.getMessage(), e);
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取唇语数据失败!"+e.getMessage());
			return uocMessage;
		}
		return uocMessage;
	}
	
	@Override
	public UocMessage downloadWXFile(String oper_no, String order_id,String mcht_flag, String server_id,String validate_data,String video_server_id,String flag) throws Exception {
		UocMessage uocMessage = new UocMessage();
		try{
			String downloadUrl = propertiesParamVo.getFileDownLoadUrl();//WXPropertiesUtils.getProperties("FILE_DOWNLOAD_URL");
			//String appid = propertiesParamVo.getAppid();//WXPropertiesUtils.getProperties("appid", mcht_flag);
			//String appsecret = propertiesParamVo.getAppsecret();//WXPropertiesUtils.getProperties("appsecret", mcht_flag);
			String appid="";
			String appsecret="";
			UocMessage redisInfo=apcRedisServiceServDu.queryForApAttr(mcht_flag);
			if(RespCodeContents.SUCCESS.equals(redisInfo.getRespCode())){
				@SuppressWarnings("unchecked")
				Map<String, Object> map=(Map<String, Object>) redisInfo.getArgs().get("apAttrInfo");
				appid = (String) map.get("appid");
				appsecret=(String) map.get("appsecret");
			}else {
				uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				uocMessage.setContent("从缓存中获取微信配置失败！");
				return uocMessage;
			}
			String token = getToken(appid, appsecret);
			
			downloadUrl = downloadUrl.replace("[ACCESS_TOKEN]", token).replace("[MEDIA_ID]", server_id);

			CloseableHttpClient httpclient = HttpClients.createDefault();
			// 创建httpget.    
	        HttpGet httpget = new HttpGet(downloadUrl);  
	        // 执行get请求.    
	        CloseableHttpResponse response = httpclient.execute(httpget);  
	        // 获取响应实体    
	        HttpEntity result = response.getEntity();
	        
	        String contentType = result.getContentType().getValue();
			String suffix = contentType.substring(contentType.lastIndexOf("/")+1);
			//获取存放OPPO图片和视频的路径
			String path = propertiesParamVo.getOppoPicPath();
			File pathFile = new File(path);
			if(!pathFile.exists()){
				pathFile.mkdirs();
			}
			String filename = order_id + UUID.randomUUID().toString().replace("-", "");
			filename = filename + "."+suffix;
			BufferedInputStream bis = new BufferedInputStream(result.getContent());
			FileOutputStream fos = new FileOutputStream(new File(path + "/" + filename));
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
			fos.close();
			bis.close();
			
			response.close();
			httpclient.close();
			logger.info("下载视频结束filename====="+filename);
			//TODO
			logger.info("================上传文件服务器（uoc）开始："+path + "/" + filename);
			UocMessage fileUPdateResult = fileServiceServDu.uploadFileByLocalfilePath(path + "/" + filename);
			if(!"0000".equals(fileUPdateResult.getRespCode())){
				logger.info("================上传文件服务器（uoc）失败："+path + "/" + filename);
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("文件处理失败！！！");
				return uocMessage;
			}
			logger.info("================上传文件服务器（uoc）成功："+path + "/" + filename);
			Map<String,Object> MapDataSourceLookup = fileUPdateResult.getArgs();
			String fileServiceUrlString = (String) MapDataSourceLookup.get("file_local_url");
			String fileLocalName = (String) MapDataSourceLookup.get("file_local_name");
			logger.info("前台所传标志flag===="+flag);
			if("1".equals(flag)){
				logger.info("只上传图片");
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				uocMessage.setContent("文件处理成功");
				uocMessage.addArg("filename", fileServiceUrlString);
				uocMessage.addArg("fileLocalName", fileLocalName);
				return uocMessage;
			}else {
				logger.info("上传视频紧接着活体校验");
				return oppoLivingExamineSubmit(order_id,validate_data,fileLocalName,fileServiceUrlString,video_server_id);
			}
			
//			uocMessage.setRespCode(RespCodeContents.SUCCESS);
//			uocMessage.setContent("文件处理成功");
//			uocMessage.addArg("filename", fileServiceUrlString);
//			uocMessage.addArg("fileLocalName", fileLocalName);
//			uocMessage.addArg("filename", filename);
//			return uocMessage;
		}catch(Exception e){
			logger.error("文件处理出现异常!"+e.getMessage(), e);
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("文件处理出现异常!"+e.getMessage());
			return uocMessage;
		}
	}
	
	@Override
	public UocMessage getWXJSConfig(String url, String mcht_flag) throws Exception {
		UocMessage uocMessage = new UocMessage();
		try{
			//String appid = propertiesParamVo.getAppid();//WXPropertiesUtils.getProperties("appid", mcht_flag);
			//String appsecret = propertiesParamVo.getAppsecret();//WXPropertiesUtils.getProperties("appsecret", mcht_flag);
			String appid="";
			String appsecret="";
			UocMessage redisInfo=apcRedisServiceServDu.queryForApAttr(mcht_flag);
			if(RespCodeContents.SUCCESS.equals(redisInfo.getRespCode())){
				@SuppressWarnings("unchecked")
				Map<String, Object> map=(Map<String, Object>) redisInfo.getArgs().get("apAttrInfo");
				appid = (String) map.get("appid");
				appsecret=(String) map.get("appsecret");
			}else {
				uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				uocMessage.setContent("从缓存中获取微信配置失败！");
				return uocMessage;
			}
			
			String access_token = getToken(appid, appsecret);
			String jsapiTicket = getJsapiTicket(access_token);
			
			Map<String, String> map = WXSignUtil.sign(jsapiTicket, url);
			map.put("appId", appid);
			map.put("access_token", access_token);
			uocMessage.addArg("data", map);
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("获取微信配置数据成功!");
			return uocMessage;
		}catch(Exception e){
			logger.error("获取微信配置数据失败!"+e.getMessage(), e);
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取微信配置数据失败!"+e.getMessage());
			return uocMessage;
		}		
	}

	/**
	 * 内部方法
	 * */
	private String getJsapiTicket(String tokenStr) throws Exception{
		
		//获取jsapi
		boolean jsapi_flag = false;
		if(wxJSMap.containsKey("jsapi")){
			Date d = (Date)wxJSMap.get("jsapi")[1];
			//判断是否超时
			if(d.after(new Date())){
				jsapi_flag = true;
			}
		}
		if(!jsapi_flag){
			String jsapiURL = propertiesParamVo.getJsApiUrl();//getWXPropertiesUtils.getProperties("JSAPI_URL");
			jsapiURL = jsapiURL.replace("[ACCESS_TOKEN]", tokenStr);
    		String result = httpGetString(jsapiURL);
    		JSONObject jsonObj = JSONObject.fromObject(result);
    		
    		
    		String errcode = jsonObj.getString("errcode");
    		if("0".equals(errcode)){
    			String ticket = jsonObj.getString("ticket");
        		int time = jsonObj.getInt("expires_in");
        		
        		Calendar nowTime = Calendar.getInstance();
        		nowTime.add(Calendar.SECOND, time/2);
        		
        		wxJSMap.put("jsapi", new Object[]{ticket, nowTime.getTime()});
    		}
		}		
		return wxJSMap.get("jsapi")[0].toString();
	}

	/**
	 * 内部方法
	 * */
	private String getToken(String appid, String appsecret) throws Exception{
		//获取access_token
		boolean token_flag = false;
		if(wxJSMap.containsKey("access_token")){
			Date d = (Date)wxJSMap.get("access_token")[1];
			//判断是否超时
			if(d.after(new Date())){
				token_flag = true;
			}
		}
		
		if(!token_flag){
    		
    		String tokenURL =  propertiesParamVo.getAccessTokenUrl2();//WXPropertiesUtils.getProperties("ACCESS_TOKEN_URL_2");
    		tokenURL = tokenURL.replace("[APPID]", appid).replace("[SECRET]", appsecret);
        	
    		String result = httpGetString(tokenURL);
    		JSONObject jsonObj = JSONObject.fromObject(result);
    		
    		String token = jsonObj.getString("access_token");
    		int time = jsonObj.getInt("expires_in");
    		
    		Calendar nowTime = Calendar.getInstance();
    		nowTime.add(Calendar.SECOND, time/2);
    		
    		wxJSMap.put("access_token", new Object[]{token, nowTime.getTime()});
		}		
		return wxJSMap.get("access_token")[0].toString();		
	}
	
	/**
	 * 内部方法
	 * */
	private static String httpGetString(String url) throws Exception{
		logger.info("请求地址："+url);	
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = null;
        try {    	
            // 创建httpget.    
            HttpGet httpget = new HttpGet(url);  
            // 执行get请求.    
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                // 获取响应实体    
                result = EntityUtils.toString(response.getEntity());                
            } finally {  
                response.close();  
            }  
        } catch (Exception e) {  
           logger.error("请求异常:"+e.getMessage(), e);
        }  finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }        
        logger.info("请求返回："+result);      
        return result;
	}

	private String getLiveCodeMsg(int code){
		switch(code){
		case 3:
			return "错误的请求";
		case 4:
			return "签名为空";
		case 5:
			return "签名串错误";
		case 6:
			return "签名中的appid/bucket与操作目标不匹配";
		case 9:
			return "签名过期";
		case 10:
			return "appid不存在";
		case 11:
			return "secretid不存在";
		case 12:
			return "appid和secretid不匹配";
		case 13:
			return "重放攻击";
		case 14:
			return "签名校验失败";
		case 15:
			return "操作太频繁，触发频控";
		case 16:
			return "Bucket不存在";
		case 21:
			return "无效参数";
		case 23:
			return "请求包体过大";
		case 107:
			return "鉴权服务不可用";
		case 108:
			return "鉴权服务不可用";
		case 203:
			return "内部错误";
		case -4006:
			return "视频中自拍照特征提取失败";
		case -4007:
			return "视频中自拍照之间对比失败";
		case -4009:
			return "Card照片片提取特征失败";
		case -4010:
			return "自拍照与Card照片相似度计算失败";
		case -4015:
			return "自拍照人脸检测失败";
		case -4016:
			return "自拍照解码失败";
		case -4017:
			return "Card照片人脸检测失败";
		case -4018:
			return "Card照片解码失败";
		case -5001:
			return "视频无效";
		case -5002:
			return "唇语失败";
		case -5005:
			return "自拍照解析照片不足";
		case -5007:
			return "视频没有声音";
		case -5008:
			return "声音识别失败";
		case -5009:
			return "视频人脸检测失败，没有嘴或者脸";
		case -5010:
			return "唇动失败";
		case -5011:
			return "活体检测失败";
		case -5012:
			return "视频中噪声太大";
		case -5013:
			return "视频里的声音太小";
		case -5014:
			return "活体检测level参数无效";
		case -5015:
			return "视频像素太低，最小320X480";
		case -5801:
			return "请求缺少身份证号码或身份证姓名";
		case -5802:
			return "服务器内部错误，服务暂时不可用";
		case -5803:
			return "身份证姓名与身份证号码不一致";
		case -5804:
			return "身份证号码无效";
		case -5805:
			return "用户未输入图像或者url下载失败";
		}
		
		return code+"";
	}

}
