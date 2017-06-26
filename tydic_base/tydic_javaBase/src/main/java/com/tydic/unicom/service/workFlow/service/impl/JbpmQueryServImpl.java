package com.tydic.unicom.service.workFlow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.service.workFlow.po.Jbpm4Dual;
import com.tydic.unicom.service.workFlow.po.Jbpm4HistActinst;
import com.tydic.unicom.service.workFlow.po.ProgressList;
import com.tydic.unicom.service.workFlow.po.VdsTabShardding;
import com.tydic.unicom.service.workFlow.service.interfaces.JbpmQueryServ;

public class JbpmQueryServImpl implements JbpmQueryServ {
	private static Logger logger = Logger.getLogger(com.tydic.unicom.service.workFlow.service.impl.JbpmQueryServImpl.class);
	
	/**
	 * 
	 * @author dell 2014-8-7 上午10:32:04
	 * @Method: getProgressList 
	 * @Description: TODO 获取工单已完成和正在进行的状态动作（单独从jbpm4_hist_actinst表获取）
	 * @param procInstanceId 流程实例ID
	 * @return List<ProgressList> 主要返回当前步骤的订单状态，当前和已完成状态标识、状态起始时间等
	 * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmQueryServ#getProgressList(java.lang.String)
	 */
	@Override
	public List<ProgressList> getProgressList(String procInstanceId) {
		logger.info("从jbpm4_hist_actinst获取数据 ...   begin");
		Map<String,Object> tmp = new HashMap<String,Object>();
		tmp.put("execution_", procInstanceId);
		Condition con = Condition.build("queryHistActinstByExecution").filter(tmp);
		//从物理数据库查询jbpm4_hist_actinst数据
		List<Jbpm4HistActinst>	jbpm4HistActinstList = (List<Jbpm4HistActinst>)S.get(Jbpm4HistActinst.class).query(con);
		
		List<ProgressList> progressLists = new ArrayList<ProgressList>();
		//对象拷贝
		for(Jbpm4HistActinst jbpm4HistActinst:jbpm4HistActinstList){
			ProgressList progressList = new ProgressList();
			/*progressList.setProc_ins_id(jbpm4HistActinst.getProc_ins_id());
			progressList.setStart_date(jbpm4HistActinst.getStart_date());
			progressList.setEnd_date(jbpm4HistActinst.getEnd_date());
			progressList.setOrder_status(jbpm4HistActinst.getOrder_status());
			progressList.setNow_proc_flag(jbpm4HistActinst.getNow_proc_flag());*/
			org.springframework.beans.BeanUtils.copyProperties(jbpm4HistActinst,progressList);
			progressLists.add(progressList);
		}
		
		return progressLists;
	}

	/**
	 * 
	 * @author yangwen 2014-8-28 下午4:46:26
	 * @Method: getDataSourceId 
	 * @Description: TODO 获取具体定位的数据源ID
	 * @param jbpmKey 如：WF_ORDER 订单流程定义KEY,WF_APPROVE 实名认证流程定义KEY 等
	 * @param keyId 各个流程定义的关键ID,如订单流程则为orderId,如客户实名认证流程则为custId等
	 * @return dataSourceId 返回对应的数据源ID
	 * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmQueryServ#getDataSourceId(java.lang.String)
	 */
	@Override
    public String getDataSourceId(String jbpmKey,String keyId) {
        String dataSourceId = "";
        Condition con = null;
		Map<String,Object> tmp = new HashMap<String,Object>();
		logger.info("getDataSourceId in ...");
		//依据流程定义key获取各个流程定义的关键ID名称，替换表达式时使用
		tmp.clear();
		tmp.put("jbpm_name", jbpmKey);
		con = Condition.build("queryJbpm4DualByJbpmKey").filter(tmp);
		Jbpm4Dual jbpm4Dual = (Jbpm4Dual)S.get(Jbpm4Dual.class).query(con).get(0);
		//从vds_tab_shardding表获取分库信息
		tmp.clear();
		tmp.put("table_name", "jbpm4_dual."+jbpm4Dual.getKey_id_name());
		con = Condition.build("querySharddingByTabName").filter(tmp);
		List<VdsTabShardding> vdsTabSharddingList = (List<VdsTabShardding>)S.get(VdsTabShardding.class).query(con);
		logger.info("getDataSourceId jbpmKey = "+jbpmKey);
		if(jbpm4Dual!=null&&vdsTabSharddingList!=null){
			for(VdsTabShardding vts:vdsTabSharddingList){
				logger.info("getDataSourceId vts.getAccept_constraint() = "+vts.getAccept_constraint());
				logger.info("getDataSourceId jbpm4Dual.getKey_id_name() = "+jbpm4Dual.getKey_id_name());
				logger.info("getDataSourceId keyId = "+keyId);
				//用传入的流程定义关键ID替换表达式中的关键ID名称
				String sqlWhere = vts.getAccept_constraint().replace(jbpm4Dual.getKey_id_name(), keyId);
				logger.info("getDataSourceId sqlWhere = "+sqlWhere);
				//依据替换后的表达式判断其真假，以此来选择相应数据源
				tmp.clear();
				tmp.put("jbpm_name", jbpmKey);
				tmp.put("sql_where", sqlWhere);
				con = Condition.build("queryJbpm4DualForSql").filter(tmp);
				List<Jbpm4Dual> jbpm4DualList = (List<Jbpm4Dual>)S.get(Jbpm4Dual.class).query(con);
				if(jbpm4DualList!=null){
					if("1".equals(jbpm4DualList.get(0).getResult_flag())){
						dataSourceId = vts.getPartition_id();
						logger.info("getDataSourceId dataSourceId = "+dataSourceId);
						break;
					}
				}
			}
		}
	    return dataSourceId;
    }
	
}
