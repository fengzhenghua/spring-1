package com.tydic.unicom.uoc.service.order.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderExtPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderExtServ;
import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.OrdModAttrDefine;
import com.tydic.unicom.service.cache.service.redis.po.OrdModParaFieldRelation;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.service.mod.vo.OrdModAttrDefineVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderExtServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderExtVo;
import com.tydic.unicom.webUtil.UocMessage;
@Service("InfoSaleOrderExtServDu")
public class InfoSaleOrderExtServDuImpl implements InfoSaleOrderExtServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderExtServDuImpl.class);

	@Autowired
	private InfoSaleOrderExtServ infoSaleOrderExtServ;
	@Autowired
	private RedisServiceServ redisServiceServ;


	@Override
	public boolean deleteInfoSaleOrderExtBySaleOrderNo(
			InfoSaleOrderExtVo vo) throws Exception {
		logger.info("----------------deleteInfoSaleOrderExtBySaleOrderNo-----------------------");
		InfoSaleOrderExtPo infoSaleOrderExtPo =new InfoSaleOrderExtPo();
		BeanUtils.copyProperties(vo,infoSaleOrderExtPo);

		infoSaleOrderExtServ.deleteInfoSaleOrderExtBySaleOrderNo(infoSaleOrderExtPo);

		logger.info("----------------deleteInfoSaleOrderExtBySaleOrderNo---------end--------------");
		return true;
	}

	@Override
	public InfoSaleOrderExtVo getInfoSaleOrderExtBySaleOrderNo(
			InfoSaleOrderExtVo vo) throws Exception {
		InfoSaleOrderExtPo infoSaleOrderExtPo =new InfoSaleOrderExtPo();
		BeanUtils.copyProperties(vo,infoSaleOrderExtPo);		
		InfoSaleOrderExtPo ePo = infoSaleOrderExtServ.getInfoSaleOrderExtBySaleOrderNo(infoSaleOrderExtPo);			
		if(ePo != null){
			InfoSaleOrderExtVo ordVo = new InfoSaleOrderExtVo();
			BeanUtils.copyProperties(ePo, ordVo);
			return ordVo;
		}else{			
			return null;
		}
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getAttrDesc(InfoSaleOrderExtVo vo,String tableName)
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
}
