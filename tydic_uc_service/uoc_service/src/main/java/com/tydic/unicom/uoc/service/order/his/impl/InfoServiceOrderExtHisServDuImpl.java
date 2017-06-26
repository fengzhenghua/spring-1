package com.tydic.unicom.uoc.service.order.his.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderExtHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderExtHisServ;
import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.OrdModAttrDefine;
import com.tydic.unicom.service.cache.service.redis.po.OrdModParaFieldRelation;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.service.mod.vo.OrdModAttrDefineVo;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderExtHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderExtHisVo;
import com.tydic.unicom.webUtil.UocMessage;
@Service("InfoServiceOrderExtHisServDu")
public class InfoServiceOrderExtHisServDuImpl implements InfoServiceOrderExtHisServDu{

	@Autowired
	private InfoServiceOrderExtHisServ infoServiceOrderExtHisServ;
	@Autowired
	private RedisServiceServ redisServiceServ;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getAttrDescHis(InfoServiceOrderExtHisVo vo,String tableName)
			throws Exception {

		Map<String,Object> map = new HashMap<String,Object>();			
		if(vo != null){			
			UocMessage msg =  redisServiceServ.queryDataFromRedis("ord_para_field_relation");			
			List<OrdModParaFieldRelation> ordModParaFieldRelationList = new ArrayList<OrdModParaFieldRelation>();
			if(msg != null){
				if("0000".equals(msg.getRespCode())){
					RedisData redisData=(RedisData) msg.getArgs().get("RedisDataResult" );
					Map<String,Object> ordModParaFieldRelationMap= redisData.getMap(); 
					String table_name = tableName;
					if(vo .getExt_field_1() != null && !"".equals(vo .getExt_field_1())){                                                      
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_1")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_1");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_1", ordModAttrDefineVoList);
									}
								}
							} 
						}						   
					}
					if(vo .getExt_field_2() != null && !"".equals(vo .getExt_field_2())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_2")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_2");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_2", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}
					if(vo .getExt_field_3() != null && !"".equals(vo .getExt_field_3())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_3")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_3");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_3", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}
					if(vo .getExt_field_4() != null && !"".equals(vo .getExt_field_4())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_4")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_4");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_4", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}
					if(vo .getExt_field_5() != null && !"".equals(vo .getExt_field_5())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_5")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_5");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_5", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}
					if(vo .getExt_field_6() != null && !"".equals(vo .getExt_field_6())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_6")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_6");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_6", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}
					if(vo .getExt_field_7() != null && !"".equals(vo .getExt_field_7())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_7")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_7");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_7", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}
					if(vo .getExt_field_8() != null && !"".equals(vo .getExt_field_8())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_8")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_8");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_8", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}
					if(vo .getExt_field_9() != null && !"".equals(vo .getExt_field_9())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_9")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_9");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_9", ordModAttrDefineVoList);
									}
								}
							} 
						}                   	 
					}
					if(vo .getExt_field_10() != null && !"".equals(vo .getExt_field_10())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_10")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_10");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_10", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}
					if(vo .getExt_field_11() != null && !"".equals(vo .getExt_field_11())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_11")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_11");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_11", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}
					if(vo .getExt_field_12() != null && !"".equals(vo .getExt_field_12())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_12")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_12");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_12", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}	
					if(vo .getExt_field_13() != null && !"".equals(vo .getExt_field_13())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_13")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_13");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_13", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}		
					if(vo .getExt_field_14() != null && !"".equals(vo .getExt_field_14())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_14")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_14");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_14", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}
					if(vo .getExt_field_15() != null && !"".equals(vo .getExt_field_15())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_16")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_16");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_16", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}	
					if(vo .getExt_field_16() != null && !"".equals(vo .getExt_field_16())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_17")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_17");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_17", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}	
					if(vo .getExt_field_17() != null && !"".equals(vo .getExt_field_17())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_18")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_18");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_18", ordModAttrDefineVoList);
									}
								}
							} 
						}                   	 
					}	
					if(vo .getExt_field_18() != null && !"".equals(vo .getExt_field_18())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_18")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_18");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_18", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}	
					if(vo .getExt_field_19() != null && !"".equals(vo .getExt_field_19())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_19")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_19");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_19", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}	
					if(vo .getExt_field_20() != null && !"".equals(vo .getExt_field_20())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_20")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_20");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_20", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}	
					if(vo .getExt_field_21() != null && !"".equals(vo .getExt_field_21())){                                                      
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_21")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_21");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_21", ordModAttrDefineVoList);
									}
								}
							} 
						}    
					}	
					if(vo .getExt_field_22() != null && !"".equals(vo .getExt_field_22())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_22")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_22");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_22", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}	
					if(vo .getExt_field_23() != null && !"".equals(vo .getExt_field_23())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_23")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_23");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_23", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}	
					if(vo .getExt_field_24() != null && !"".equals(vo .getExt_field_24())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_24")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_24");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_24", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}	
					if(vo .getExt_field_25() != null && !"".equals(vo .getExt_field_25())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_25")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_25");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_25", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}	
					if(vo .getExt_field_26() != null && !"".equals(vo .getExt_field_26())){                    	                          
						if(ordModParaFieldRelationMap.containsKey(table_name+"_"+"ext_field_26")){
							ordModParaFieldRelationList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_26");
							OrdModParaFieldRelation ord = ordModParaFieldRelationList.get(0);
							if(ord != null){
								if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
									List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
									if(ordModAttrDefineVoList != null ){
										map.put("ext_field_26", ordModAttrDefineVoList);
									}
								}
							} 
						}                    	 
					}	
					if(vo .getExt_field_27() != null && !"".equals(vo .getExt_field_27())){                    	                          
						OrdModAttrDefine ord = (OrdModAttrDefine) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_27");
						if(ord != null){
							if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
								List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
								if(ordModAttrDefineVoList != null ){
									map.put("ext_field_27", ordModAttrDefineVoList);
								}

							}
						}                    	 
					}	
					if(vo .getExt_field_28() != null && !"".equals(vo .getExt_field_28())){                    	                          
						OrdModParaFieldRelation ord = (OrdModParaFieldRelation) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_28");
						if(ord != null){
							if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
								List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
								if(ordModAttrDefineVoList != null ){
									map.put("ext_field_28", ordModAttrDefineVoList);
								}

							}
						}                    	 
					}
					if(vo .getExt_field_29() != null && !"".equals(vo .getExt_field_29())){                    	                          
						OrdModParaFieldRelation ord = (OrdModParaFieldRelation) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_29");
						if(ord != null){
							if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
								List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
								if(ordModAttrDefineVoList != null ){
									map.put("ext_field_29", ordModAttrDefineVoList);
								}

							}
						}                    	 
					}	
					if(vo .getExt_field_30() != null && !"".equals(vo .getExt_field_30())){                    	                          
						OrdModParaFieldRelation ord = (OrdModParaFieldRelation) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_30");
						if(ord != null){
							if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
								List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
								if(ordModAttrDefineVoList != null ){
									map.put("ext_field_30", ordModAttrDefineVoList);
								}

							}
						}                    	 
					}	
					if(vo .getExt_field_31() != null && !"".equals(vo .getExt_field_31())){                    	                          
						OrdModParaFieldRelation ord = (OrdModParaFieldRelation) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_31");
						if(ord != null){
							if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
								List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
								if(ordModAttrDefineVoList != null ){
									map.put("ext_field_31", ordModAttrDefineVoList);
								}

							}
						}                    	 
					}	
					if(vo .getExt_field_32() != null && !"".equals(vo .getExt_field_32())){                    	                          
						OrdModParaFieldRelation ord = (OrdModParaFieldRelation) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_32");
						if(ord != null){
							if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
								List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
								if(ordModAttrDefineVoList != null ){
									map.put("ext_field_32", ordModAttrDefineVoList);
								}

							}
						}                    	 
					}	
					if(vo .getExt_field_33() != null && !"".equals(vo .getExt_field_33())){                    	                          
						OrdModParaFieldRelation ord = (OrdModParaFieldRelation) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_33");
						if(ord != null){
							if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
								List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
								if(ordModAttrDefineVoList != null ){
									map.put("ext_field_33", ordModAttrDefineVoList);
								}

							}
						}                    	 
					}	
					if(vo .getExt_field_34() != null && !"".equals(vo .getExt_field_34())){                    	                          
						OrdModParaFieldRelation ord = (OrdModParaFieldRelation) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_34");
						if(ord != null){
							if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
								List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
								if(ordModAttrDefineVoList != null ){
									map.put("ext_field_34", ordModAttrDefineVoList);
								}

							}
						}                    	 
					}	
					if(vo .getExt_field_35() != null && !"".equals(vo .getExt_field_35())){                    	                          
						OrdModParaFieldRelation ord = (OrdModParaFieldRelation) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_35");
						if(ord != null){
							if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
								List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
								if(ordModAttrDefineVoList != null ){
									map.put("ext_field_35", ordModAttrDefineVoList);
								}

							}
						}                    	 
					}	
					if(vo .getExt_field_36() != null && !"".equals(vo .getExt_field_36())){                    	                          
						OrdModParaFieldRelation ord = (OrdModParaFieldRelation) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_36");
						if(ord != null){
							if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
								List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
								if(ordModAttrDefineVoList != null ){
									map.put("ext_field_36", ordModAttrDefineVoList);
								}

							}
						}                    	 
					}	
					if(vo .getExt_field_37() != null && !"".equals(vo .getExt_field_37())){                    	                          
						OrdModParaFieldRelation ord = (OrdModParaFieldRelation) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_37");
						if(ord != null){
							if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
								List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
								if(ordModAttrDefineVoList != null ){
									map.put("ext_field_37", ordModAttrDefineVoList);
								}

							}
						}                    	 
					}	
					if(vo .getExt_field_38() != null && !"".equals(vo .getExt_field_38())){                    	                          
						OrdModParaFieldRelation ord = (OrdModParaFieldRelation) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_38");
						if(ord != null){
							if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
								List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
								if(ordModAttrDefineVoList != null ){
									map.put("ext_field_38", ordModAttrDefineVoList);
								}

							}
						}                    	 
					}	
					if(vo .getExt_field_39() != null && !"".equals(vo .getExt_field_39())){                    	                          
						OrdModParaFieldRelation ord = (OrdModParaFieldRelation) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_39");
						if(ord != null){
							if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
								List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
								if(ordModAttrDefineVoList != null ){
									map.put("ext_field_39", ordModAttrDefineVoList);
								}

							}
						}                    	 
					}	
					if(vo .getExt_field_40() != null && !"".equals(vo .getExt_field_40())){                    	                          
						OrdModParaFieldRelation ord = (OrdModParaFieldRelation) ordModParaFieldRelationMap.get(table_name+"_"+"ext_field_40");
						if(ord != null){
							if(ord.getAttr_code() != null && !"".equals(ord.getAttr_code())){
								List<OrdModAttrDefineVo> ordModAttrDefineVoList= getAttrDescTemp(ord.getAttr_code());
								if(ordModAttrDefineVoList != null ){
									map.put("ext_field_40", ordModAttrDefineVoList);
								}

							}
						}                    	 
					}
					return map;
				}else{
					return null;
				} 
			}else{
				return null;
			}
		}else{
			return null;
		}

	
	}
	
	public List<OrdModAttrDefineVo> getAttrDescTemp(String attr_code)throws Exception {
		UocMessage msg =  redisServiceServ.queryDataFromRedis("ord_attr_define");
		if(msg != null){
			if(msg.getRespCode().equals("0000")){
				RedisData redisData=(RedisData) msg.getArgs().get("RedisDataResult");
				Map<String,Object> ordModAttrDefineMap= redisData.getMap();
				if(ordModAttrDefineMap.containsKey(attr_code)){
					@SuppressWarnings("unchecked")
					List<OrdModAttrDefine> ordModAttrDefineList = (List<OrdModAttrDefine>) ordModAttrDefineMap.get(attr_code);
					if(ordModAttrDefineList != null){
						List<OrdModAttrDefineVo> ordModAttrDefineVoList = new ArrayList<OrdModAttrDefineVo>();
						for(OrdModAttrDefine ord :ordModAttrDefineList){
							OrdModAttrDefineVo ordVo = new OrdModAttrDefineVo();
							BeanUtils.copyProperties(ord, ordVo);
							ordModAttrDefineVoList.add(ordVo);
						}
						return ordModAttrDefineVoList;
					}
					
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
		return null;
	}
	
	@Override
	public InfoServiceOrderExtHisVo queryInfoServiceOrderExtByOrderNo(
			InfoServiceOrderExtHisVo vo) throws Exception {
		InfoServiceOrderExtHisPo infoServiceOrderExtHis = new InfoServiceOrderExtHisPo();
		BeanUtils.copyProperties(vo, infoServiceOrderExtHis);
		InfoServiceOrderExtHisPo po = infoServiceOrderExtHisServ.queryInfoServiceOrderExtHisByServOrderNo(infoServiceOrderExtHis);
		if(po != null){

			InfoServiceOrderExtHisVo ordVo = new InfoServiceOrderExtHisVo();
			BeanUtils.copyProperties(po, ordVo);

		}
		return null;
	}



}
