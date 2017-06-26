package com.tydic.unicom.uoc.business.common.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.service.cache.service.redis.po.CodeList;
import com.tydic.unicom.uoc.base.uochis.po.InfoGztHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoGztHisServ;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskDealRecordPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskInstPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskDealRecordServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskInstServ;
import com.tydic.unicom.uoc.business.common.interfaces.CheckInfoServDu;
import com.tydic.unicom.uoc.business.common.vo.InfoIDCardVo;
import com.tydic.unicom.uoc.business.common.vo.LivingCheckVo;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.service.interfaces.ArtificialTaskServDu;
import com.tydic.unicom.uoc.business.order.service.vo.ArtificialTaskVo;
import com.tydic.unicom.uoc.pub.common.service.po.ActivemqSendPo;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcessCirculationServDu;
import com.tydic.unicom.uoc.service.code.interfaces.CodeListServDu;
import com.tydic.unicom.uoc.service.common.impl.OperServDuImpl;
import com.tydic.unicom.uoc.service.common.impl.ToolSpring;
import com.tydic.unicom.uoc.service.common.impl.UocTool;
import com.tydic.unicom.uoc.service.common.interfaces.AbilityPlatformServDu;
import com.tydic.unicom.uoc.service.common.interfaces.ActivemqSendServDu;
import com.tydic.unicom.uoc.service.common.interfaces.FileServiceServDu;
import com.tydic.unicom.uoc.service.common.interfaces.GetIdServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.uoc.service.constants.Constant;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModFunctionServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderPersionCertHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderPersionCertHisVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoDeliverOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderPersionCertServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderExtServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoDeliverOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderPersionCertVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderExtVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.uoc.service.task.interfaces.CheckArtificialTaskServDu;
import com.tydic.unicom.util.DateUtil;
import com.tydic.unicom.util.EncodeUtil;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

import net.sf.json.JSONObject;

public class CheckInfoServDuImpl implements CheckInfoServDu {
	
	Logger logger = Logger.getLogger(OperServDuImpl.class);
	@Autowired
	private AbilityPlatformServDu abilityPlatformServDu;
	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServDu;
	@Autowired
	private InfoSaleOrderPersionCertServDu infoSaleOrderPersionCertServDu;
	@Autowired
	private ProcessCirculationServDu processCirculationServDu;
	@Autowired
	private OperServDu operServDu;
	@Autowired
	private CheckArtificialTaskServDu checkArtificialTaskServDu;
	@Autowired
	private ProcInstTaskInstServ procInstTaskInstServ;
	@Autowired
	private ProcInstTaskDealRecordServ procInstTaskDealRecordServ;
	@Autowired
	private GetIdServDu getIdServDu;
	@Autowired
	private FileServiceServDu fileServiceServDu;
	@Autowired
	private InfoDeliverOrderServDu infoDeliverOrderServDu;
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	@Autowired
	private InfoGztHisServ infoGztHisServ;
	@Autowired
	private InfoSaleOrderPersionCertHisServDu infoSaleOrderPersionCertHisServDu;
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	@Autowired
	private CodeListServDu codeListServDu;
	@Autowired
	private ArtificialTaskServDu artificialTaskServDu;
	@Autowired
	private ActivemqSendServDu activemqSendServDu;
	@Autowired
	private InfoServiceOrderExtServDu infoServiceOrderExtServDu;
	@Autowired
	private OrdModFunctionServDu ordModFunctionServDu;
	@Autowired
	private InfoSaleOrderServDu infoSaleOrderServDu;
	private void getBeanDu() {
		if (infoSaleOrderServDu == null) {
			infoSaleOrderServDu = (InfoSaleOrderServDu) ToolSpring.getBean("InfoSaleOrderServDu");
		}
		if (jsonToBeanServDu == null) {
			jsonToBeanServDu = (JsonToBeanServDu)ToolSpring.getBean("JsonToBeanServDu");
		}
		if (ordModFunctionServDu == null) {
			ordModFunctionServDu = (OrdModFunctionServDu)ToolSpring.getBean("OrdModFunctionServDu");
		}
		if (activemqSendServDu == null) {
			activemqSendServDu = (ActivemqSendServDu)ToolSpring.getBean("ActivemqSendServDu");
		}
		if (infoServiceOrderExtServDu == null) {
			infoServiceOrderExtServDu = (InfoServiceOrderExtServDu)ToolSpring.getBean("InfoServiceOrderExtServDu");
		}
		if (abilityPlatformServDu == null) {
			abilityPlatformServDu = (AbilityPlatformServDu)ToolSpring.getBean("AbilityPlatformServDu");
		}
		if (infoServiceOrderServDu == null) {
			infoServiceOrderServDu = (InfoServiceOrderServDu)ToolSpring.getBean("InfoServiceOrderServDu");
		}
		if (infoSaleOrderPersionCertServDu == null) {
			infoSaleOrderPersionCertServDu = (InfoSaleOrderPersionCertServDu)ToolSpring
			        .getBean("InfoSaleOrderPersionCertServDu");
		}
		if (processCirculationServDu == null) {
			processCirculationServDu = (ProcessCirculationServDu)ToolSpring.getBean("ProcessCirculationServDu");
		}
		if (operServDu == null) {
			operServDu = (OperServDu)ToolSpring.getBean("OperServDu");
		}
		if (checkArtificialTaskServDu == null) {
			checkArtificialTaskServDu = (CheckArtificialTaskServDu)ToolSpring.getBean("CheckArtificialTaskServDu");
		}
		if (procInstTaskInstServ == null) {
			procInstTaskInstServ = (ProcInstTaskInstServ)ToolSpring.getBean("ProcInstTaskInstServ");
		}
		if (procInstTaskDealRecordServ == null) {
			procInstTaskDealRecordServ = (ProcInstTaskDealRecordServ)ToolSpring.getBean("ProcInstTaskDealRecordServ");
		}
		if (getIdServDu == null) {
			getIdServDu = (GetIdServDu)ToolSpring.getBean("GetIdServDu");
		}
		if (fileServiceServDu == null) {
			fileServiceServDu = (FileServiceServDu)ToolSpring.getBean("FileServiceServDu");
		}
		if (infoDeliverOrderServDu == null) {
			infoDeliverOrderServDu = (InfoDeliverOrderServDu)ToolSpring.getBean("InfoDeliverOrderServDu");
		}
		if (codeListServDu == null) {
			codeListServDu = (CodeListServDu)ToolSpring.getBean("CodeListServDu");
		}
		if (artificialTaskServDu == null) {
			artificialTaskServDu = (ArtificialTaskServDu)ToolSpring.getBean("ArtificialTaskServDu");
		}
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public UocMessage createGztValid(InfoIDCardVo vo, String path) {
		logger.info("----------国政通接口----------");
		UocMessage message = new UocMessage();
		String jsession_id = vo.getJsession_id();
		String cust_name = vo.getCust_name();
		String cert_no = vo.getCert_no();
		
		if (jsession_id == null || "".equals(jsession_id)) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("jession_id不能为空");
			return message;
		}
		if (cust_name == null || "".equals(cust_name)) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("cust_name不能为空");
			return message;
		}
		if (cert_no == null || "".equals(cert_no)) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("id_number不能为空");
			return message;
		}
		
		UocMessage loginMessage = operServDu.isLogin(vo.getJsession_id());
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("效验登录信息失败");
			return message;
		}
		
		Map<String, Object> oper_info = (Map<String, Object>)loginMessage.getArgs().get("oper_info");
		String province_code = oper_info.get("province_code").toString();
		String area_code = oper_info.get("area_code").toString();
		
		InfoGztHisPo infoGztHisPoTemp = new InfoGztHisPo();
		Map<String, String> map = new HashMap<String, String>();
		// 如果serv_order_no不为空，先从INFO_GZT_H表里查
		if (vo.getServ_order_no() != null && !"".endsWith(vo.getServ_order_no())) {
			InfoGztHisPo infoGztHisPo = new InfoGztHisPo();
			infoGztHisPo.setServ_order_no(vo.getServ_order_no());
			try {
				infoGztHisPoTemp = infoGztHisServ.queryInfoGztHisByServOrderNo(infoGztHisPo);
			} catch (Exception e) {
				logger.info("----------根据服务订单号查询身份证信息异常----------");
				e.printStackTrace();
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("根据服务订单号查询身份证信息异常");
				return message;
			}
			
			if (infoGztHisPoTemp != null) {
				// 国政通接口调用成功后 生成身份证图片
				InfoIDCardVo iDCardInfoResult = new InfoIDCardVo();
				iDCardInfoResult.setCert_no(cert_no);
				iDCardInfoResult.setCust_name(cust_name);
				iDCardInfoResult.setCustom_sex(infoGztHisPoTemp.getSex());
				iDCardInfoResult.setCustom_nation(infoGztHisPoTemp.getNation());
				iDCardInfoResult.setId_addr(infoGztHisPoTemp.getAddr());
				iDCardInfoResult.setId_police(infoGztHisPoTemp.getIssue());
				iDCardInfoResult.setPhoto_code(infoGztHisPoTemp.getPhoto());
				String birth = infoGztHisPoTemp.getBirthday();
				birth = birth.substring(0, 4) + "-" + birth.substring(4, 6) + "-" + birth.substring(6, 8);
				iDCardInfoResult.setCustom_birth(birth);
				String endDate = infoGztHisPoTemp.getExp();
				if (endDate == null || "".equals(endDate)) {
					endDate = "2050-01-01";
				} else {
					endDate = endDate.substring(0, 4) + "-" + endDate.substring(4, 6) + "-" + endDate.substring(6, 8);
				}
				iDCardInfoResult.setEnd_date(endDate);
				String startDate = "2000-01-01";
				iDCardInfoResult.setStart_date(startDate);
				// try{
				// message =getIdPic( path,iDCardInfoResult);
				// if(!"0000".equals(message.getRespCode())){
				// return message;
				// }
				//
				// }catch(Exception e){
				// logger.info("----------获取身份证图片异常----------");
				// e.printStackTrace();
				// return message;
				// }
				
				map.put("sex", infoGztHisPoTemp.getSex());
				map.put("nation", infoGztHisPoTemp.getNation());
				map.put("addr", infoGztHisPoTemp.getAddr());
				map.put("issue", infoGztHisPoTemp.getIssue());
				map.put("photo", infoGztHisPoTemp.getPhoto());
				map.put("birthday", infoGztHisPoTemp.getBirthday());
				map.put("exp", infoGztHisPoTemp.getExp());
				
				message.setRespCode(RespCodeContents.SUCCESS);
				message.setContent("国政通返回成功");
				message.addArg("pic_path", infoGztHisPoTemp.getCert_face_url());
				message.addArg("args", map);
				return message;
			}
		}
		if (vo.getServ_order_no() == null || "".endsWith(vo.getServ_order_no()) || infoGztHisPoTemp == null) {
			
			String json_info = "{\"certName\":\"" + cust_name + "\",\"certId\":\"" + cert_no + "\"}";
			try {
				// interface_type填900
				UocMessage res = abilityPlatformServDu.CallAbilityPlatform(json_info, "900", "", "");
				if (res != null) {
					// 9、如果有调用能力平台接口，则返回的信息挂在args节点下返回
					String code = (String)res.getArgs().get("code");
					logger.info("----------code----------" + code);
					if (code != null && "200".equals(code)) {
						String json = res.getArgs().get("json_info").toString();
						JSONObject resJson = JSONObject.fromObject(json);
						map = (Map)resJson.get("rspmsg");
						if (map == null) {
							logger.info("----------无对应信息----------");
							message.setRespCode(RespCodeContents.PARAM_ERROR);
							message.setContent("无对信息");
							return message;
						}
						logger.info("----------oper_info----------" + map.toString());
						
						// 写入INFO_GZT_H
						InfoGztHisPo infoGztHisPo = null;
						if (vo.getServ_order_no() != null && !"".endsWith(vo.getServ_order_no())) {
							infoGztHisPo = new InfoGztHisPo();
							infoGztHisPo.setServ_order_no(vo.getServ_order_no());
							infoGztHisPo.setProvince_code(province_code);
							infoGztHisPo.setArea_code(area_code);
							infoGztHisPo.setAddr(map.get("addr"));
							infoGztHisPo.setBirthday(map.get("birthday"));
							infoGztHisPo.setCertid(cert_no);
							infoGztHisPo.setCertname(cust_name);
							infoGztHisPo.setNation(map.get("nation"));
							infoGztHisPo.setPhoto(map.get("photo"));
							infoGztHisPo.setSex(map.get("sex"));
							String endDate = map.get("exp");
							if (endDate == null || "".equals(endDate)) {
								endDate = "2050-01-01";
							}
							infoGztHisPo.setExp(endDate);
							String issue = map.get("issue");
							if (issue == null || "".equals(issue)) {
								issue = "公安局";
							}
							infoGztHisPo.setIssue(issue);
							
							// boolean flag=infoGztHisServ.createInfoGztHisPo(infoGztHisPo);
							// if(!flag){
							// logger.info("----------写入INFO_GZT_H表失败----------");
							// message.setRespCode(RespCodeContents.SERVICE_FAIL);
							// message.setContent("写入INFO_GZT_H表失败");
							// return message;
							// }
						}
						// 国政通接口调用成功后 生成身份证图片
						InfoIDCardVo iDCardInfoResult = new InfoIDCardVo();
						iDCardInfoResult.setCert_no(cert_no);
						iDCardInfoResult.setCust_name(cust_name);
						iDCardInfoResult.setCustom_sex(map.get("sex"));
						iDCardInfoResult.setCustom_nation(map.get("nation"));
						iDCardInfoResult.setId_addr(map.get("addr"));
						String issue = map.get("issue");
						if (issue == null || "".equals(issue)) {
							issue = "公安局";
						}
						iDCardInfoResult.setId_police(issue);
						iDCardInfoResult.setPhoto_code(map.get("photo"));
						String birth = map.get("birthday");
						birth = birth.substring(0, 4) + "-" + birth.substring(4, 6) + "-" + birth.substring(6, 8);
						iDCardInfoResult.setCustom_birth(birth);
						String endDate = map.get("exp");
						if (endDate == null || "".equals(endDate)) {
							endDate = "2050-01-01";
						} else {
							endDate = endDate.substring(0, 4) + "-" + endDate.substring(4, 6) + "-" + endDate.substring(6, 8);
						}
						iDCardInfoResult.setEnd_date(endDate);
						String startDate = "2000-01-01";
						/*
						 * if(startDate==null || "".equals(startDate)){ startDate ="2000-01-01"; }else{ startDate
						 * =startDate.substring(0,4)+"-"+startDate.substring(4,6)+"-"+startDate.substring(6,8); }
						 */
						iDCardInfoResult.setStart_date(startDate);
						try {
							message = getIdPic(path, iDCardInfoResult);
							if (!"0000".equals(message.getRespCode())) {
								// return message;
								throw new UocException(message);
							}
							
							if (infoGztHisPo != null) {
								String pic_path = (String)message.getArgs().get("pic_path");
								infoGztHisPo.setCert_face_url(pic_path);
								boolean flag = infoGztHisServ.createInfoGztHisPo(infoGztHisPo);
								if (!flag) {
									logger.info("----------写入INFO_GZT_H表失败----------");
									message.setRespCode(RespCodeContents.SERVICE_FAIL);
									message.setContent("写入INFO_GZT_H表失败");
									throw new UocException(message);
								}
							}
						} catch (Exception e) {
							logger.info("----------获取身份证图片异常----------");
							logger.info(e.getMessage());
							e.printStackTrace();
							// return message;
							throw new UocException(message);
						}
					} else {
						logger.info("----------能力平台调用失败----------");
						message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
						return res;
					}
					
				} else {
					logger.info("----------能力平台调用失败----------");
					message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
					message.setContent("能力平台调用失败");
					return message;
				}
				
			} catch (Exception e) {
				logger.info("----------能力平台调用异常----------");
				e.printStackTrace();
				message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
				message.setContent("能力平台调用异常");
				return message;
			}
			
		}
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("国政通返回成功");
		message.addArg("args", map);
		return message;
	}
	
	// 生产身份证图片
	private UocMessage getIdPic(String path, InfoIDCardVo iDCardInfoResult) throws Exception {
		UocMessage message = new UocMessage();
		if (iDCardInfoResult != null && !"".equals(iDCardInfoResult.getCert_no())) {
			logger.info("身份证号码：" + iDCardInfoResult.getCert_no());
			
			int width = 340;
			int height = 432;
			String name = iDCardInfoResult.getCust_name();
			String gender = null;
			if ("M".equals(iDCardInfoResult.getCustom_sex())) {
				gender = "男";
			} else {
				gender = "女";
			}
			String nation = iDCardInfoResult.getCustom_nation();
			String[] birth = iDCardInfoResult.getCustom_birth().split("-");
			String year = birth[0];
			String month = Integer.valueOf(birth[1]).toString();
			String day = Integer.valueOf(birth[2]).toString();
			String addr = iDCardInfoResult.getId_addr();
			String idNum = iDCardInfoResult.getCert_no();
			String police = iDCardInfoResult.getId_police();
			logger.info(iDCardInfoResult.getStart_date());
			String expDate = iDCardInfoResult.getStart_date().substring(0, 10).replace("-", ".") + "-"
			        + iDCardInfoResult.getEnd_date().substring(0, 10).replace("-", ".");
			// 照片流
			String pic = iDCardInfoResult.getPhoto_code();
			// 拆分地址，换行展示
			String addr1 = "";
			String addr2 = "";
			if (addr.length() > 11) {
				addr1 = addr.substring(0, 11);
				addr2 = addr.substring(11);
			} else {
				addr1 = addr;
			}
			// 初始化
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = (Graphics2D)bi.getGraphics();
			// 身份证
			path = propertiesParamVo.getLocalTempFilePath();
			File bg = new File(path + "/idcard.jpg");
			Image img;
			try {
				// 生成身份证背景
				img = ImageIO.read(bg);
				g2.drawImage(img, 0, 0, null);
				
				// 生成照片
				InputStream input = new ByteArrayInputStream(EncodeUtil.decode(pic));
				BufferedImage temp = ImageIO.read(input);
				if (temp == null) {
					input = new ByteArrayInputStream(UocTool.hexStringToByte(pic));
					temp = ImageIO.read(input);
				}
				// 如果大于标准则压缩
				if (temp.getHeight() > Constant.ID_PIC_HEIGHT) {
					Image image = temp.getScaledInstance(Constant.ID_PIC_WIDTH, Constant.ID_PIC_HEIGHT, Image.SCALE_DEFAULT);
					temp = UocTool.toBufferedImage(image);
				}
				
				int imgHeight = temp.getHeight();// 取得图片的长和宽
				int imgWidth = temp.getWidth();
				
				int c = temp.getRGB(3, 3);
				BufferedImage bi2 = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_4BYTE_ABGR);// 新建一个类型支持透明的BufferedImage
				for (int i = 0; i < imgWidth; ++i)// 把原图片的内容复制到新的图片，同时把背景设为透明
				{
					for (int j = 0; j < imgHeight; ++j) {
						if (temp.getRGB(i, j) == c)
							bi2.setRGB(i, j, c & 0x00ffffff);// 这里把背景设为透明
						else
							bi2.setRGB(i, j, temp.getRGB(i, j));
					}
				}
				
				g2.drawImage(bi2, 208, 37, null);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			g2.setPaint(Color.BLACK);
			
			// 在指定位置生成文字
			g2.drawString(name, 60, 42);
			g2.drawString(gender, 60, 68);
			g2.drawString(nation, 140, 68);
			g2.drawString(year, 60, 93);
			g2.drawString(month, 115, 93);
			g2.drawString(day, 145, 93);
			g2.drawString(addr1, 60, 123);
			g2.drawString(addr2, 60, 140);
			g2.drawString(idNum, 110, 190);
			
			// g2.drawString(police, 482, 165);
			// g2.drawString(expDate, 482, 193);
			g2.drawString(police, 136, 379);
			g2.drawString(expDate, 136, 409);
			// 打印照片
			try {
				File file = new File(path + "/" + iDCardInfoResult.getCert_no() + ".jpg");
				boolean isok = ImageIO.write(bi, "jpg", file);
				if (!isok) {
					message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
					message.setContent("生成图片失败");
					return message;
				} else {
					String localTempFilePath = path + "/" + iDCardInfoResult.getCert_no() + ".jpg";
					UocMessage res = fileServiceServDu.uploadFileByLocalfilePath(localTempFilePath);
					if ("0000".equals(res.getRespCode())) {
						String pic_path = (String)res.getArgs().get("file_local_url");
						
						message.setRespCode(RespCodeContents.SUCCESS);
						message.setContent("上传本地文件到文件服务器成功");
						message.addArg("pic_path", pic_path);
						return message;
					} else {
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("上传本地文件到文件服务器失败");
						return message;
					}
					// String fileServicePath = propertiesParamVo.getFileServicePath();
					// String resultUpload = HttpUtil.httpUploadByPath(fileServicePath, localTempFilePath);
					// if(StringUtil.isEmpty(resultUpload)){
					// message.setRespCode(RespCodeContents.SERVICE_FAIL);
					// message.setContent("上传本地文件到文件服务器失败");
					// return message;
					// }
					// else{
					// logger.info("======================上传本地文件到文件服务器结果："+resultUpload);
					// JSONObject resultUploadJson = JSONObject.fromObject(resultUpload);
					// String respCode = resultUploadJson.get("RESP_CODE").toString();
					// if(respCode == null || (!"0000".equals(respCode))){
					// message.setRespCode(RespCodeContents.SERVICE_FAIL);
					// message.setContent("上传本地文件到文件服务器失败");
					// return message;
					// }
					// else{
					// message.setRespCode(RespCodeContents.SUCCESS);
					// message.setContent("上传本地文件到文件服务器成功");
					// message.addArg("pic_path", resultUploadJson.get("APACHE_FILE_INFO").toString());
					//// logger.info("============删除本地临时文件=============");
					// return message;
					// //result = DeleteLocalTempFile(localSavePath);
					// }
					// }
				}
			} catch (IOException e) {
				e.printStackTrace();
				message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				message.setContent("生成图片异常");
				return message;
			}
			/*
			 * message.setRespCode(RespCodeContents.SUCCESS); message.addArg("pic_path",path +"/"+ iDCardInfoResult.getCert_no()+".jpg");
			 */
			// return message;
			
		} else {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("生成图片失败");
			return message;
		}
	}
	
	/**
	 * 活体自动审核结果通知 UOC0051入参组装
	 * @param json_in
	 * @return
	 * @throws Exception
	 */
	public UocMessage createLivingChecKResultForAbilityPlatform(String json_in) throws Exception {
		
		if (jsonToBeanServDu == null) {
			logger.info("====================jsonToBeanServDu is null============================" + jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu)ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_in);
		LivingCheckVo vo = new LivingCheckVo();
		
		String jsession_id = (String)map.get("jsession_id");
		String serv_order_no = (String)map.get("serv_order_no");
		String compare_rate = (String)map.get("compare_rate");
		String compare_result = (String)map.get("compare_result");
		String note = (String)map.get("note");
		String cert_face_url = (String)map.get("cert_face_url");
		String cert_back_url = (String)map.get("cert_back_url");
		String cert_hand_url = (String)map.get("cert_hand_url");
		String cert_video_url = (String)map.get("cert_video_url");
		String pic_person_url = (String)map.get("pic_person_url");
		String pic_guardian_url = (String)map.get("pic_guardian_url");
		String pic_from_person_url = (String)map.get("pic_from_person_url");
		String pic_from_guardian_url = (String)map.get("pic_from_guardian_url");
		vo.setJsession_id(jsession_id);
		vo.setServ_order_no(serv_order_no);
		vo.setSimil_rate(compare_rate);
		vo.setAudit_result(compare_result);
		vo.setNote(note);
		vo.setCert_back_url(cert_back_url);
		vo.setCert_face_url(cert_face_url);
		vo.setCert_hand_url(cert_hand_url);
		vo.setCert_video_url(cert_video_url);
		vo.setPic_from_person_url(pic_from_person_url);
		vo.setPic_guardian_url(pic_guardian_url);
		vo.setPic_person_url(pic_person_url);
		vo.setPic_from_person_url(pic_from_guardian_url);
		
		UocMessage message = createLivingChecKResult(vo);
		return message;
	}
	
	/**
	 * 活体自动审核结果通知 UOC0051
	 * @param vo
	 * @return
	 */
	@Override
	public UocMessage createLivingChecKResult(LivingCheckVo vo) throws Exception {
		
		logger.info("----------活体自动审核结果通知---UOC0051-------");
		getBeanDu();
		UocMessage message = new UocMessage();
		String jsession_id = vo.getJsession_id();
		String serv_order_no = vo.getServ_order_no();
		String audit_result = vo.getAudit_result();
		String simil_rate = vo.getSimil_rate();
		String cert_video_url = vo.getCert_video_url();
		String cert_face_url = vo.getCert_face_url();
		String cert_back_url = vo.getCert_back_url();
		String cert_hand_url = vo.getCert_hand_url();
		String pic_person_url = vo.getPic_person_url();
		String pic_guardian_url = vo.getPic_guardian_url();
		String pic_from_person_url = vo.getPic_from_person_url();
		String pic_from_guardian_url = vo.getPic_from_guardian_url();
		String note = vo.getNote();
		String deal_code = StringUtils.isNotEmpty(vo.getDeal_code()) ? vo.getDeal_code() : "";
		String deal_desc = StringUtils.isNotEmpty(vo.getDeal_desc()) ? vo.getDeal_desc() : "";
		String deal_system_no = StringUtils.isNotEmpty(vo.getDeal_system_no()) ? vo.getDeal_system_no() : "";
		if (jsession_id == null || "".equals(jsession_id)) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("jession_id不能为空");
			return message;
		}
		if (serv_order_no == null || "".equals(serv_order_no)) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("serv_order_no不能为空");
			return message;
		}
		if (audit_result == null || "".equals(audit_result)) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("audit_result不能为空");
			return message;
		}
		
		/*
		 * 校验jession_id
		 */
		UocMessage res = operServDu.isLogin(jsession_id);
		if (!"0000".equals(res.getRespCode())) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent(res.getContent());
			return message;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> oper_info = (Map<String, Object>)res.getArgs().get("oper_info");
		String oper_no = (String)oper_info.get("oper_no");
		String province_code = (String)oper_info.get("province_code");
		
		// 调用BASE0025进行任务校验
		UocMessage checkMessage = checkArtificialTaskServDu.checkArtificialTaskProcess(serv_order_no, "S00017", oper_info);
		if (!checkMessage.getRespCode().equals(RespCodeContents.SUCCESS)) {
			return checkMessage;
		}
		
		InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
		infoServiceOrderVo.setServ_order_no(serv_order_no);
		infoServiceOrderVo = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);
		if (infoServiceOrderVo == null) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("无关联服务订单");
			return message;
		}
		// 3、
		// 根据入参记录info_sale_person_cert表，注意所有URL地址需要先调用BASE0030转换成本地的url地址后再记录该表
		if (checkUrlOrBase64(cert_face_url)) {
			cert_face_url = changeToLoacalUrl(cert_face_url, "");
		} else {
			cert_face_url = changeToLoacalUrl("", cert_face_url);
		}
		if (checkUrlOrBase64(cert_back_url)) {
			cert_back_url = changeToLoacalUrl(cert_back_url, "");
		} else {
			cert_back_url = changeToLoacalUrl("", cert_back_url);
		}
		if (checkUrlOrBase64(cert_hand_url)) {
			cert_hand_url = changeToLoacalUrl(cert_hand_url, "");
		} else {
			cert_hand_url = changeToLoacalUrl("", cert_hand_url);
		}
		if (checkUrlOrBase64(cert_video_url)) {
			cert_video_url = changeToLoacalUrl(cert_video_url, "");
		} else {
			cert_video_url = changeToLoacalUrl("", cert_video_url);
		}
		if (checkUrlOrBase64(pic_from_guardian_url)) {
			pic_from_guardian_url = changeToLoacalUrl(pic_from_guardian_url, "");
		} else {
			pic_from_guardian_url = changeToLoacalUrl("", pic_from_guardian_url);
		}
		if (checkUrlOrBase64(pic_from_person_url)) {
			pic_from_person_url = changeToLoacalUrl(pic_from_person_url, "");
		} else {
			pic_from_person_url = changeToLoacalUrl("", pic_from_person_url);
		}
		if (checkUrlOrBase64(pic_guardian_url)) {
			pic_guardian_url = changeToLoacalUrl(pic_guardian_url, "");
		} else {
			pic_guardian_url = changeToLoacalUrl("", pic_guardian_url);
		}
		if (checkUrlOrBase64(pic_person_url)) {
			pic_person_url = changeToLoacalUrl(pic_person_url, "");
		} else {
			pic_person_url = changeToLoacalUrl("", pic_person_url);
		}
		
		String sale_order_no = infoServiceOrderVo.getSale_order_no();
		InfoSaleOrderPersionCertVo infoSaleOrderPersionCert = new InfoSaleOrderPersionCertVo();
		infoSaleOrderPersionCert.setSale_order_no(sale_order_no);
		infoSaleOrderPersionCert.setProvince_code(infoServiceOrderVo.getProvince_code());
		infoSaleOrderPersionCert.setPart_month(infoServiceOrderVo.getPart_month());
		infoSaleOrderPersionCert.setArea_code(infoServiceOrderVo.getArea_code());
		infoSaleOrderPersionCert.setCert_face_url(cert_face_url);
		infoSaleOrderPersionCert.setCert_back_url(cert_back_url);
		infoSaleOrderPersionCert.setCert_hand_url(cert_hand_url);
		infoSaleOrderPersionCert.setCert_video_url(cert_video_url);
		infoSaleOrderPersionCert.setSimil_rate(simil_rate);
		infoSaleOrderPersionCert.setAudit_result(audit_result);
		infoSaleOrderPersionCert.setNote(note);
		infoSaleOrderPersionCert.setPic_from_guardian_url(pic_from_guardian_url);
		infoSaleOrderPersionCert.setPic_from_person_url(pic_from_person_url);
		infoSaleOrderPersionCert.setPic_guardian_url(pic_guardian_url);
		infoSaleOrderPersionCert.setPic_person_url(pic_person_url);
		InfoSaleOrderPersionCertVo exsitVo = infoSaleOrderPersionCertServDu
		        .getInfoSaleOrderPersionCertBySaleOrderNo(infoSaleOrderPersionCert);
		
		boolean crate_flag = false;
		if (exsitVo != null) {
			crate_flag = infoSaleOrderPersionCertServDu.updateInfoSaleOrderPersionCert(infoSaleOrderPersionCert);
		} else {
			crate_flag = infoSaleOrderPersionCertServDu.createInfoSaleOrderPersionCert(infoSaleOrderPersionCert);
		}
		
		if (!crate_flag) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("创建用户证件信息表失败");
			throw new UocException(message);
		}
		// 查询交付订单表的状态，如果是待配送则更新交付订单表的状态为交付完成102
		InfoDeliverOrderVo infoDeliverOrderVo = new InfoDeliverOrderVo();
		infoDeliverOrderVo.setSale_order_no(sale_order_no);
		List<InfoDeliverOrderVo> infoDeliverOrderVos = infoDeliverOrderServDu
		        .queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderVo);
		if (infoDeliverOrderVos != null && infoDeliverOrderVos.size() > 0) {
			for (InfoDeliverOrderVo vo1 : infoDeliverOrderVos) {
				if ("100".equals(vo1.getDeliver_state())) {
					vo1.setDeliver_state("102");
					Boolean update_flag = infoDeliverOrderServDu.updateInfoDeliverOrder(vo1);
					if (!update_flag) {
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("更新交付订单状态异常");
						throw new UocException(message);
					}
				}
			}
		}
		
		// 调用BASE0016服务进行服务订单的流程流转，oper_type传101，flow_type传1，action_code根据入参audit_result来定：
		// 1）audit_result=0成功，action_code=S00012
		// 2）audit_result=1失败，action_code=S00010
		Map<String, String> actionCode = new HashMap<String, String>();
		if ("0".equals(audit_result)) {
			actionCode.put("condParam1", "S00012");
		}
		if ("1".equals(audit_result)) {
			actionCode.put("condParam1", "S00010");
		}
		String json_info_ext = "{\"jsession_id\":\"" + jsession_id + "\"}";
		message = processCirculationServDu.processCirculation(serv_order_no, "101", "1", actionCode, json_info_ext);
		if (!"0000".equals(message.getRespCode())) {
			throw new UocException(message);
		}
		
		// 更新人工任务实例表的任务状态为已处理，填写完成时间，还要记录人工任务操作记录表，记录传入的处理动作、处理描述、处理系统编码
		List<ProcInstTaskInstPo> list = procInstTaskInstServ.querytaskInstByOrderNoAndTacheCode(serv_order_no, "S00017");
		ProcInstTaskInstPo po = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(list.get(0), po);
		String date = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
		po.setTask_state("102");
		po.setFinish_time(date);
		logger.info("---------更新人工任务实例表---------order_no=" + sale_order_no);
		boolean flagTem = procInstTaskInstServ.updateByOrderNo(po);
		if (!flagTem) {
			logger.error("更新人工任务实例表失败");
			// 抛出业务异常
			UocMessage uocExceptionMsg = new UocMessage();
			uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocExceptionMsg.setContent("更新人工任务实例表失败");
			throw new UocException(uocExceptionMsg);
		}
		
		ProcInstTaskDealRecordPo procInstTaskDealRecordPo = new ProcInstTaskDealRecordPo();
		String id = getIdServDu.getId("createLogId", province_code, "*", "");
		procInstTaskDealRecordPo.setId(id);
		procInstTaskDealRecordPo.setTask_id(list.get(0).getTask_id());
		procInstTaskDealRecordPo.setProvince_code(list.get(0).getProvince_code());
		procInstTaskDealRecordPo.setArea_code(list.get(0).getArea_code());
		procInstTaskDealRecordPo.setPart_month(list.get(0).getPart_month());
		procInstTaskDealRecordPo.setDeal_time(date);
		procInstTaskDealRecordPo.setDeal_oper_no(oper_no);
		procInstTaskDealRecordPo.setDeal_system_no(deal_system_no);
		procInstTaskDealRecordPo.setDeal_code(deal_code);
		procInstTaskDealRecordPo.setDeal_desc(deal_desc);
		procInstTaskDealRecordPo.setOrder_no(sale_order_no);
		procInstTaskDealRecordPo.setOrder_type(list.get(0).getOrder_type());
		procInstTaskDealRecordPo.setTache_code(list.get(0).getTache_code());
		procInstTaskDealRecordPo.setOper_code(list.get(0).getOper_code());
		procInstTaskDealRecordPo.setCreate_time(list.get(0).getCreate_time());
		procInstTaskDealRecordPo.setProd_code(list.get(0).getProd_code());
		logger.info("---------创建人工任务操作记录---------order_no=" + sale_order_no);
		boolean flag = procInstTaskDealRecordServ.create(procInstTaskDealRecordPo);
		if (!flag) {
			logger.error("创建人工任务操作记录失败");
			// 抛出业务异常
			UocMessage uocExceptionMsg = new UocMessage();
			uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocExceptionMsg.setContent("创建人工任务操作记录失败");
			throw new UocException(uocExceptionMsg);
		}
		
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("活体自动审核结果通知成功 ");
		return message;
	}
	
	@Override
	public UocMessage getCertInfo(ParaVo vo) throws Exception {
		UocMessage message = new UocMessage();
		String sale_order_no = vo.getOrder_no();
		String finish_flag = vo.getFinish_flag();
		if ("1".equals(finish_flag)) {
			InfoSaleOrderPersionCertHisVo infoSaleOrderPersionCertHisVo = new InfoSaleOrderPersionCertHisVo();
			infoSaleOrderPersionCertHisVo.setSale_order_no(sale_order_no);
			infoSaleOrderPersionCertHisVo = infoSaleOrderPersionCertHisServDu
			        .getInfoSaleOrderPersionCertHis(infoSaleOrderPersionCertHisVo);
			if (infoSaleOrderPersionCertHisVo == null) {
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("无关联订单证件信息");
				return message;
			}
			message.setRespCode(RespCodeContents.SUCCESS);
			message.addArg("infoSaleOrderPersionCertVo", infoSaleOrderPersionCertHisVo);
			return message;
		} else {
			InfoSaleOrderPersionCertVo infoSaleOrderPersionCertVo = new InfoSaleOrderPersionCertVo();
			infoSaleOrderPersionCertVo.setSale_order_no(sale_order_no);
			infoSaleOrderPersionCertVo = infoSaleOrderPersionCertServDu
			        .getInfoSaleOrderPersionCertBySaleOrderNo(infoSaleOrderPersionCertVo);
			if (infoSaleOrderPersionCertVo == null) {
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("无关联订单证件信息");
				return message;
			}
			message.setRespCode(RespCodeContents.SUCCESS);
			message.addArg("infoSaleOrderPersionCertVo", infoSaleOrderPersionCertVo);
			return message;
		}
		
	}
	
	/**
	 * 获取工号信息UOC0056
	 * @return
	 */
	@Override
	public UocMessage getOperInfo(String jsession_id) throws Exception {
		UocMessage res = operServDu.isLogin(jsession_id);
		return res;
	}
	
	private String changeToLoacalUrl(String url, String base64) throws Exception {
		if (url != null && !"".equals(url)) {
			UocMessage message = fileServiceServDu.uploadFile(url, "");
			if ("0000".equals(message.getRespCode())) {
				url = (String)message.getArgs().get("file_local_url");
			}
		} else
			if (base64 != null && !"".equals(base64)) {
				UocMessage message = fileServiceServDu.uploadFile("", base64);
				if ("0000".equals(message.getRespCode())) {
					url = (String)message.getArgs().get("file_local_url");
				}
			}
		
		return url;
	}
	
	// private String changeBase64ToLoacalUrl(String base64) throws Exception{
	// String url="";
	// if(base64 !=null && !"".equals(base64)){
	// UocMessage message =fileServiceServDu.uploadFile("",base64);
	// if("0000".equals(message.getRespCode())) {
	// url =(String) message.getArgs().get("file_local_url");
	// }
	// }
	//
	// return url;
	// }
	
	/**
	 * 通过能力平台传入 <br>
	 * 传入：参见 重庆联通中台-订单中心接口规范 6.10 接受规范：订单中心服务 UOC0077 创建一个 人工任务，外部系统通过能力平台访问
	 * @param json_in
	 * @return
	 * @throws Exception
	 * @author xx
	 */
	public UocMessage createNewLivingChecKForAbilityPlatformExternalSystem(String json_in) throws Exception {
		getBeanDu();
		if (jsonToBeanServDu == null) {
			logger.info("====================jsonToBeanServDu is null============================" + jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu)ToolSpring.getBean("JsonToBeanServDu");
		}
		UocMessage message = new UocMessage();
		try {
			JSONObject json = JSONObject.fromObject(json_in);
			logger.info("------json_info------" + json_in);
			String jsession_id = json.get("jsession_id").toString();
			String order_no = json.get("serv_order_no").toString();
			String oper_type = json.get("json_info").toString();
			String flow_flag = json.get("flow_flag").toString();
			String threshold_low = json.get("threshold_low").toString();
			String threshold_high = json.get("threshold_high").toString();
			message = createNewLivingChecK(jsession_id, order_no, oper_type, flow_flag, threshold_low, threshold_high);
		} catch (Throwable e) {
			logger.error("===============abilityPlatformForYunWo异常==============");
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("系统异常");
			return message;
		}
		
		return message;
	}
	
	/**
	 * 106 活体认证请求UOC0077
	 * @return
	 */
	@Override
	public UocMessage createNewLivingChecK(String jsession_id, String serv_order_no, String json_info, String flow_flag,
	        String threshold_low, String threshold_high) throws Exception {
		UocMessage message = new UocMessage();
		
		UocMessage loginMessage = operServDu.isLogin(jsession_id);
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("效验登录信息失败");
			return message;
		}
		
		// code_list表type_code=’face_detect’，作为是否异步处理开关配置 ，异步调用时直接返回verifyresult=处理成功，verifymsg=”后台处理中” verifyscore =-1给前台
		CodeList codeList = new CodeList();
		codeList.setType_code("face_detect");
		List<CodeList> codeLists = codeListServDu.queryCodeListByTypeCodeFromRedis(codeList);
		String flag = "0";
		if (codeLists != null && codeLists.size() > 0) {
			flag = codeLists.get(0).getCode_id();
		}
		Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_info);
		String businessscene = (String)map.get("businessscene");
		
		String verifyscore = "-1";
		String verifymsg = "后台处理中";
		String verifyresult = "处理成功";
		
		if (!"1".equals(flag)) {
			// 同步调用
			logger.info("flag值:" + flag + "开始同步调用国政通人脸识别!");
			return createNewLivingCheckGzt(jsession_id, serv_order_no, json_info, flow_flag, threshold_low, threshold_high);
		} else {
			logger.info("flag值:" + flag + "开始异步调用国政通人脸识别!");
			// 异步调用配置为1，直接通过BASE0033服务发起消息队列调用UOC0083
			ActivemqSendPo activemqSendPo = new ActivemqSendPo();
			activemqSendPo.setService_code("UOC0083");
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("jsession_id", jsession_id);
			jsonObject.put("serv_order_no", serv_order_no);
			jsonObject.put("json_info", json_info);
			jsonObject.put("flow_flag", flow_flag);
			jsonObject.put("threshold_low", threshold_low);
			jsonObject.put("threshold_high", threshold_high);
			activemqSendPo.setJson_in(jsonObject.toString());
			// 发送到队列，用已有监听器处理消息
			activemqSendServDu.sendMessage(activemqSendPo, "OrderFinish");
		}
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("活体认证请求成功");
		message.addArg("businessscene", businessscene);
		message.addArg("verifyscore", verifyscore);
		message.addArg("verifymsg", verifymsg);
		message.addArg("verifyresult", verifyresult);
		return message;
	}
	
	/**
	 * 
	 * @author heguoyong 2017年5月2日 上午11:16:14
	 * @Method: createNewLivingChecGztFacade
	 * @Description: 反射调用接口封装活体认证
	 */
	public UocMessage createNewLivingCheckGztFacade(String jsonInfo) {
		logger.info("进入活体认证反射调用接口!");
		JSONObject jsonObject = JSONObject.fromObject(jsonInfo);
		UocMessage message = new UocMessage();
		try {
			message = createNewLivingCheckGzt(jsonObject.getString("jsession_id"), jsonObject.getString("serv_order_no"),
			        jsonObject.getString("json_info"), jsonObject.getString("flow_flag"), jsonObject.getString("threshold_low"),
			        jsonObject.getString("threshold_high"));
		} catch (Exception e) {
			logger.error("调用活体认证失败" + e.getMessage(), e);
		}
		return message;
	}
	
	/**
	 * 国政通活体认证UOC0083
	 * @return
	 */
	public UocMessage createNewLivingCheckGzt(String jsession_id, String serv_order_no, String json_info, String flow_flag,
	        String threshold_low, String threshold_high) throws Exception {
		logger.info("进入活体认证异步UOC0083!");
		getBeanDu();
		UocMessage message = new UocMessage();
		// BASE0018 调用能力平台服务
		Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_info);
		Map file_entity = (Map)map.get("file_entity");
		String fileinfo = (String)file_entity.get("fileinfo");
		String businessscene = (String)map.get("businessscene");
		
		String verifyscore = "-1";
		String verifymsg = "后台处理中";
		String verifyresult = "处理成功";
		JSONObject jsonObject = new JSONObject();
		InfoServiceOrderExtVo infoServiceOrderExtVo = new InfoServiceOrderExtVo();
		InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
		jsonObject.put("bizkey", "CS-038");
		jsonObject.put("method", "ecaop.trades.query.comm.face.check");
		UocMessage abilityMessage = abilityPlatformServDu.CallAbilityPlatform(dealInfoJson(map), "200", jsonObject.toString(),
		        "");
		if (abilityMessage.getRespCode().equals(RespCodeContents.SUCCESS)) {
			logger.info("能力平台调用返回respCode" + abilityMessage.getRespCode());
			String code = String.valueOf(abilityMessage.getArgs().get("code"));
			String reJson = String.valueOf(abilityMessage.getArgs().get("json_info"));
			Map<String, Object> reMap = jsonToBeanServDu.jsonToMap(reJson);
			if (code.equals("200")) {
				//正常返回报文 photo result similarity
				if (reMap.get("similarity") != null) {
					verifyscore = String.valueOf(reMap.get("similarity"));
					String gztresult = String.valueOf(reMap.get("result"));
					if ("0".equals(gztresult)) {
						verifymsg = "认为同一个人";
					} else {
						verifymsg = "认为不是同一个人";
					}
				}else{
					//返回异常报文code detail
					String checkCode = String.valueOf(reMap.get("code"));
					verifymsg = String.valueOf(reMap.get("detail"));
					//不通过 0001 没有查询到该身份证号码, 0002 姓名核查不一致, 9999 其他错误
					if(checkCode.equals("0001")||checkCode.equals("0002")||code.equals("0004")||checkCode.equals("9999")){
						verifyscore = "0";
						logger.info("aop人脸识别调用返回异常" + checkCode+",错误描述"+verifymsg);
					}
				}
			} else {
				verifymsg = String.valueOf(reMap.get("detail"));
				if(code.equals("0001")||code.equals("0002")||code.equals("0004")||code.equals("9999")){
					verifyscore = "0";
				}
				logger.info("aop人脸识别调用返回异常" + code+",错误描述"+verifymsg);
			}
		} else {		
			//能力平台失败，直接转人工verifyscore为-1
			logger.info("能力平台调用失败" + abilityMessage.getRespCode());
		}
		
		try {
			String url = "";
			try {
				if (checkUrlOrBase64(fileinfo)) {
					url = changeToLoacalUrl(fileinfo, "");
				} else {
					url = changeToLoacalUrl("", fileinfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("base64转换本地url失败");
				return message;
			}
			
			InfoServiceOrderVo infoServiceOrder = new InfoServiceOrderVo();
			infoServiceOrder.setServ_order_no(serv_order_no);
			infoServiceOrder = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrder);
			if (infoServiceOrder == null) {
				message.setRespCode(RespCodeContents.PARAM_ERROR);
				message.setContent("无关联订单号");
				return message;
			}
			
			//获取回调地址,判定回调方法
			infoSaleOrderVo.setSale_order_no(infoServiceOrder.getSale_order_no());
			infoSaleOrderVo = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
			if (infoSaleOrderVo == null) {
				logger.info(">>>>>>>>>>>>>>>无对应的销售订单<<<<<<<<<<<<<<<<<");
				message.setRespCode(RespCodeContents.PARAM_ERROR);
				message.setContent("无对应的销售订单");
				return message;
			}
			
			// 构建扩展对象
			BeanUtils.copyProperties(infoServiceOrder, infoServiceOrderExtVo);
			
			InfoSaleOrderPersionCertVo infoSaleOrderPersionCertVo = new InfoSaleOrderPersionCertVo();
			infoSaleOrderPersionCertVo.setSale_order_no(infoServiceOrder.getSale_order_no());
			infoSaleOrderPersionCertVo = infoSaleOrderPersionCertServDu
			        .getInfoSaleOrderPersionCertBySaleOrderNo(infoSaleOrderPersionCertVo);
			if (infoSaleOrderPersionCertVo != null) {
				infoSaleOrderPersionCertVo.setPic_person_url(url);
				infoSaleOrderPersionCertVo.setSimil_rate(verifyscore);
				boolean b = infoSaleOrderPersionCertServDu.updateInfoSaleOrderPersionCert(infoSaleOrderPersionCertVo);
				if (!b) {
					message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
					message.setContent("活体认证请求修改表失败");
					throw new UocException(message);
				}
			} else {
				infoSaleOrderPersionCertVo = new InfoSaleOrderPersionCertVo();
				infoSaleOrderPersionCertVo.setPic_person_url(url);
				infoSaleOrderPersionCertVo.setSimil_rate(verifyscore);
				infoSaleOrderPersionCertVo.setSale_order_no(infoServiceOrder.getSale_order_no());
				infoSaleOrderPersionCertVo.setProvince_code(infoServiceOrder.getProvince_code());
				infoSaleOrderPersionCertVo.setPart_month(infoServiceOrder.getPart_month());
				infoSaleOrderPersionCertVo.setArea_code(infoServiceOrder.getArea_code());
				boolean b = infoSaleOrderPersionCertServDu.createInfoSaleOrderPersionCert(infoSaleOrderPersionCertVo);
				if (!b) {
					message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
					message.setContent("活体认证请求创建表失败");
					throw new UocException(message);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("活体认证请求数据保存异常");
			return message;
		}
		
		// 判定需要返回的next_step值
		if (Integer.parseInt(verifyscore) <= Integer.parseInt(threshold_low) && !"-1".equals(verifyscore)) {
			infoServiceOrderExtVo.setExt_field_6("0");
		} else
			if ((Integer.parseInt(verifyscore) < Integer.parseInt(threshold_high)
			        && Integer.parseInt(verifyscore) > Integer.parseInt(threshold_low)) || "-1".equals(verifyscore)) {
				infoServiceOrderExtVo.setExt_field_6("2");
			} else
				if (Integer.parseInt(verifyscore) >= Integer.parseInt(threshold_high)) {
					infoServiceOrderExtVo.setExt_field_6("1");
				}
		infoServiceOrderExtServDu.updateInfoServiceOrderExtFileSix(infoServiceOrderExtVo);
		logger.info("订单号:"+infoServiceOrderExtVo.getServ_order_no()+":相似度"+verifyscore+":插入数据到info_serv_ext的ext_file_6完成值为：" + infoServiceOrderExtVo.getExt_field_6());
		
		// 调用BASE0003订单模板获取服务取到出参模板，这里入参order_type填101，query_type也填101
		//TODO 该处理逻辑废弃
		UocMessage uocMessageOrdModAttrOut = ordModFunctionServDu.queryOrdMod(serv_order_no, "101", "101");
		if ("0000".equals(uocMessageOrdModAttrOut.getRespCode())) {
			String mod_code = (String)uocMessageOrdModAttrOut.getArgs().get("mod_code");
			logger.info("调用BASE0003订单模板获取服务取到出参模板" + mod_code);
			if ("0000".equals(uocMessageOrdModAttrOut.getRespCode())) {
				// 如果取到模板，则先调用BASE0023服务拼出param_json，然后根据这个值再加上order_type=101
				UocMessage loginMessage = operServDu.isLogin(jsession_id);
				if ("0000".equals(String.valueOf(loginMessage.getRespCode()))) {
					@SuppressWarnings("unchecked")
					Map<String, Object> oper_info = (Map<String, Object>)loginMessage.getArgs().get("oper_info");
					// BASE0023
					logger.info("调用BASE0023");
					String param_json = operServDu.loginShareParam(oper_info, jsession_id);
					// BASE0006
					logger.info("调用BASE0006");
					UocMessage uocMessageOrdModOut = ordModFunctionServDu.outByOrdMod(serv_order_no, mod_code, "101",
					        param_json);
					if (uocMessageOrdModOut != null) {
						if ("0000".equals(uocMessageOrdModOut.getRespCode())) {
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + uocMessageOrdModOut.getContent()
							        + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							Map<String, Object> argsOrdModOut = uocMessageOrdModOut.getArgs();
							String json_info_out = (String)argsOrdModOut.get("json_info");
							String interface_type = (String)argsOrdModOut.get("interface_type");
							String interface_param_json = (String)argsOrdModOut.get("interface_param_json");
							
							String backUrl = infoSaleOrderVo.getCallback_url();
							logger.info("销售订单号："+infoSaleOrderVo.getSale_order_no()+"获取回调地址:"+backUrl);
							if (("700").equals(interface_type)){
								if (StringUtils.isNotEmpty(backUrl)) {
									Map<String, Object> callUrlJson = jsonToBeanServDu.jsonToMap(backUrl);
									if (callUrlJson != null && callUrlJson.containsKey("SERVICE_NAME")) {
										backUrl = (String) callUrlJson.get("SERVICE_NAME");
										logger.info("处理后的回调地址:"+backUrl);
										interface_type = "703";
									}
								} 
							}
							
							// BASE0018 调用能力平台服务
							logger.info("调用BASE0018");
							UocMessage uocMessageAbilityPlat = abilityPlatformServDu.CallAbilityPlatform(json_info_out,
							        interface_type, interface_param_json, backUrl);
							if (uocMessageAbilityPlat != null) {
								if ("0000".equals(uocMessageAbilityPlat.getRespCode())) {
									String code = (String)uocMessageAbilityPlat.getArgs().get("code");
									if (code != null && code.equals("200")) {
										// 然后再调用BASE0008服务记录能力平台接口日志
										logger.info("调用BASE0008服务" + uocMessageAbilityPlat.getContent()
										        + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
										String argsAbilityPlat = (String)uocMessageAbilityPlat.getArgs().get("json_info");
										message.addArg("abilityPlat", argsAbilityPlat);
									}
								} else {
									message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
									message.setContent(uocMessageAbilityPlat.getContent());
									return message;
								}
							} else {
								message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
								message.setContent("调用能力平台失败");
								return message;
							}
						}
					}
				}
			}
		}
		
		// 如果入参flow_flag传入为1，则可能需要自动调用UOC0015服务，根据传入的阀值以及接口返回的verifyscore走不同的逻辑:
		if ("1".equals(flow_flag)) {
			// 如果verifyscore < threshold_low不调用服务
			logger.info(
			        "当前入参threshold_low:" + threshold_low + "verifyscore:" + verifyscore + "threshold_high:" + threshold_high);
			if (Integer.parseInt(verifyscore) <= Integer.parseInt(threshold_low) && !"-1".equals(verifyscore)) {
				ArtificialTaskVo artificialTaskVo = new ArtificialTaskVo();
				artificialTaskVo.setJsession_id(jsession_id);
				artificialTaskVo.setOrder_no(serv_order_no);
				artificialTaskVo.setOrder_type("101");
				artificialTaskVo.setOper_type("101");
				artificialTaskVo.setTache_code("S00017");
				artificialTaskVo.setDeal_code("S00022");
				artificialTaskVo.setFlow_skip("1");
				artificialTaskVo.setCall_type("1");
				
				UocMessage res = artificialTaskServDu.createHandingArtificialTask(artificialTaskVo);
				if (!"0000".equals(res.getRespCode())) {
					return res;
				}
				message.addArg("next_step", "0");
				// 如果 threshold_low < verifyscore < threshold_high则走转人工审核流程
			} else
				if ((Integer.parseInt(verifyscore) < Integer.parseInt(threshold_high)
				        && Integer.parseInt(verifyscore) > Integer.parseInt(threshold_low)) || "-1".equals(verifyscore)) {
					ArtificialTaskVo artificialTaskVo = new ArtificialTaskVo();
					artificialTaskVo.setJsession_id(jsession_id);
					artificialTaskVo.setOrder_no(serv_order_no);
					artificialTaskVo.setOrder_type("101");
					artificialTaskVo.setOper_type("101");
					artificialTaskVo.setTache_code("S00017");
					artificialTaskVo.setDeal_code("S00020");
					artificialTaskVo.setFlow_skip("1");
					artificialTaskVo.setCall_type("1");
					
					UocMessage res = artificialTaskServDu.createHandingArtificialTask(artificialTaskVo);
					if (!"0000".equals(res.getRespCode())) {
						return res;
					}
					// 消息推送工号
					Map<String, Object> resMap = res.getArgs();
					if (resMap != null && resMap.get("pushMsgOperNo") != null) {
						message.addArg("pushMsgFlag", "1");
						message.addArg("operNo", resMap.get("pushMsgOperNo").toString());
						message.addArg("msgTpye", "S00020");
					}
					message.addArg("next_step", "1");
				} else
					if (Integer.parseInt(verifyscore) >= Integer.parseInt(threshold_high)) {
						ArtificialTaskVo artificialTaskVo = new ArtificialTaskVo();
						artificialTaskVo.setJsession_id(jsession_id);
						artificialTaskVo.setOrder_no(serv_order_no);
						artificialTaskVo.setOrder_type("101");
						artificialTaskVo.setOper_type("101");
						artificialTaskVo.setTache_code("S00017");
						artificialTaskVo.setDeal_code("S00021");
						artificialTaskVo.setFlow_skip("1");
						artificialTaskVo.setCall_type("1");
						
						UocMessage res = artificialTaskServDu.createHandingArtificialTask(artificialTaskVo);
						if (!"0000".equals(res.getRespCode())) {
							return res;
						}
						message.addArg("next_step", "2");
					}
				
		}
		
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("活体认证请求成功");
		message.addArg("businessscene", businessscene);
		message.addArg("verifyscore", verifyscore);
		message.addArg("verifymsg", verifymsg);
		message.addArg("verifyresult", verifyresult);
		
		return message;
	}
	
	private boolean checkUrlOrBase64(String url) {
		if (url == null) {
			return false;
		}
		return url.startsWith("http");
	}
	
	/**
	 * 
	 * @author heguoyong 2017年4月27日 下午6:45:35
	 * @Method: dealInfoJson
	 * @Description: 处理传递的json值
	 */
	private String dealInfoJson(Map<String, Object> map) {
		Map reJsonMap = new HashMap();
		Map file_entity = (Map)map.get("file_entity");
		String fileinfo = String.valueOf(file_entity.get("fileinfo"));
		String businessscene = String.valueOf(map.get("businessscene"));
		// 如果有前缀，去掉data:image/jpg;base64
		String base64Img[] = fileinfo.split(",");
		if (base64Img.length > 1) {
			fileinfo = base64Img[1];
		} else {
			fileinfo = base64Img[0];
		}
		reJsonMap.put("province", map.get("province"));
		reJsonMap.put("city", map.get("city"));
		reJsonMap.put("system", "01");// 01 为国政通;02 为阿里
		reJsonMap.put("scene", "01"); // 01:生活照与公安部照片的对比,02:生活照与身份证照片（人像）对比
		reJsonMap.put("certId", map.get("certid"));
		reJsonMap.put("certName", map.get("certname"));
		// 处理传入的base64串BASE64.NO_WRAP接收模式
		reJsonMap.put("faceImg", fileinfo.replaceAll("[\\s*\t\n\r]", ""));
		if ("02".equals(businessscene)) {
			reJsonMap.put("baseImg", fileinfo); // 二代证照片 base64字符串编码
		}
		JSONObject json = JSONObject.fromObject(reJsonMap);
		return json.toString();
	}
}
