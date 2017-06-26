package com.tydic.unicom.service.cache.service.impl;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.service.cache.service.interfaces.MemInitServ;
import com.tydic.unicom.service.cache.po.*;

public class MemInitServImpl implements MemInitServ {
		private static Logger logger = Logger.getLogger(com.tydic.unicom.service.cache.service.impl.MemInitServImpl.class);
		
		/**
		 * 初始化code_type
		 * 1.按code_type+type_code的方式存入memcached,一个type_code对应一个List<CodeList>
		 * */
		public String codeTypeInit() {
				String flag = "OK";
				
				//从物理数据库查询code_type数据
				List<CodeType>	codeTypeList = (List<CodeType>)S.get(CodeType.class).query(null);
				
				//加载到memcached内存数据库
				if(codeTypeList == null){
					logger.info("准备加载到memcached中 0  条数据");
				}else{
					logger.info("准备加载到memcached中 "+codeTypeList.size()+" 条数据");
				}
				
				logger.info("将code_type数据加载到memcached中...    begin");
				for(int i=0;i<codeTypeList.size();i++){
						CodeType codeType = (CodeType)codeTypeList.get(i);
						
						if(codeType != null){
							
								//keyId的生成因为切换mysql的原因放在java中进行拼接
								String keyId = "code_type_" + codeType.getType_code();
								codeType.setKey_id(keyId);
							
								logger.info("开始加载第 "+i+" 条 type_code 相关的code_list数据,type_code = "+codeType.getType_code());
								Map<String,Object> tmp = new HashMap<String,Object>();
								tmp.put("type_code", codeType.getType_code());
								Condition con = Condition.build("queryByTypeCode").filter(tmp);
								//从物理数据库查询code_list数据
								List<CodeList>	codeLists = (List<CodeList>)S.get(CodeList.class).query(con);
								if(codeLists == null){
									logger.info("从数据库中查询code_list表,共查询到  0  条数据");
								}else{
									logger.info("从数据库中查询code_list表,共查询到  "+codeLists.size()+" 条数据");
								}
								
								codeType.setCodeList(codeLists);
								
								//对象拷贝
								CodeTypeMem	codeTypeMem = new CodeTypeMem();
								org.springframework.beans.BeanUtils.copyProperties(codeType,codeTypeMem);
								
								//清除内存中重复的keyId
								S.get(CodeTypeMem.class).remove(codeTypeMem.getKey_id());
								//将该条数据写入memcached
								String	tmpKeyId = (String)S.get(CodeTypeMem.class).create(codeTypeMem);
								logger.info("加载第"+i+"条数据成功!     KeyId = "+tmpKeyId);
						}
				}
				logger.info("将code_type数据加载到memcached中...    end");
				
				return flag;
		}
	
		/**
		 * 初始化code_list
		 * 1. 通过type_code查询出对应的code_list数据集合
		 * 2. 按code_list+type_code+code_id的方式存入memcached,一个type_code+code_id对应一个CodeList
		 * */
		public String codeListInit(){
				String flag = "OK";
				String	tmpKeyId = "";
				
				logger.info("将code_list数据加载到memcached中...		begin");
		
				Condition con = Condition.build("queryAll");
				con.filter(new HashMap<String,Object>());
				//从物理数据库查询code_list数据
				List<CodeList>	codeLists = (List<CodeList>)S.get(CodeList.class).query(con);
				if(codeLists == null){
					logger.info("从数据库中查询code_list表,共查询到  0  条数据");
				}else{
					logger.info("从数据库中查询code_list表,共查询到  "+codeLists.size()+" 条数据");
				}
				
				for(int j=0;j<codeLists.size();j++){
						CodeList codeList = (CodeList)codeLists.get(j);
						
						if(codeList != null){
								//keyId的生成因为切换mysql的原因放在java中进行拼接
								String keyId = "code_list_"+codeList.getType_code()+"_"+codeList.getCode_id();
								codeList.setKey_id(keyId);
							
								//对象拷贝
								CodeListMem	codeListMem = new CodeListMem();
								org.springframework.beans.BeanUtils.copyProperties(codeList,codeListMem);
										
								logger.info("准备加载code_list...第 "+j+" 条数据");
								//清除内存中重复的keyId
								S.get(CodeListMem.class).remove(codeListMem.getKey_id());
								//将该条数据写入memcached
								tmpKeyId = (String)S.get(CodeListMem.class).create(codeListMem);
								logger.info("加载第"+j+"条数据成功!     KeyId = "+tmpKeyId);
						}
				}
				logger.info("将code_list数据加载到memcached中...		end");
				return flag;	
		}
		
		/**
		 * 初始化code_list_map
		 * 1. 按code_list_map+type_code+code_id+system的方式存入memcached
		 * */
		public String codeListMapInit(){
			String flag = "OK";
			String	tmpKeyId = "";
			logger.info("将code_list_map数据加载到memcached中...		begin");
			List<CodeListMap> clms = (List<CodeListMap>)S.get(CodeListMap.class).query(null);
			if(clms == null){
				logger.info("从数据库中查询code_list_map表,共查询到  0  条数据");
			}else{
				logger.info("从数据库中查询code_list_map表,共查询到  "+clms.size()+" 条数据");
			}
			
			for(int i=0;i<clms.size();i++){
				CodeListMap clm = clms.get(i);
				
				if(clm != null){
					//keyId的生成因为切换mysql的原因放在java中进行拼接
					String keyId = "code_list_map_"+clm.getType_code()+"_"+clm.getCode_id()+"_"+clm.getSystem();
					clm.setMap_list_id(keyId);
					
					//对象拷贝
					CodeListMapMem	clmm = new CodeListMapMem();
					org.springframework.beans.BeanUtils.copyProperties(clm,clmm);
					
					logger.info("准备加载code_list_map...第 "+i+" 条数据");
					//清除内存中重复的keyId
					S.get(CodeListMem.class).remove(clmm.getMap_list_id());
					//将该条数据写入memcached
					tmpKeyId = (String)S.get(CodeListMapMem.class).create(clmm);
					logger.info("加载第"+i+"条数据成功!     KeyId = "+tmpKeyId);
				}
			}
			
			
			logger.info("将code_list_map数据加载到memcached中...		end");
			return flag;
		}
}
