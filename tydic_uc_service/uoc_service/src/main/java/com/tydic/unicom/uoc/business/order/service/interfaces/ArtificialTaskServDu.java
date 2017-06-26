package com.tydic.unicom.uoc.business.order.service.interfaces;

import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.service.vo.ArtificialTaskVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface ArtificialTaskServDu {

	/**
	 * uoc0015 人工任务处理
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UocMessage createHandingArtificialTask(ArtificialTaskVo vo) throws Exception;

	/**
	 * UOC0014  人工任务列表获取
	 * @param vo
	 * @return
	 * @throws Exception
	 */

	public UocMessage getArtificialTaskList(ArtificialTaskVo vo) throws Exception;

	/**
	 * UOC0034 任务分配
	 * @param jsession_id	  登陆验证串
     * @param order_no		  订单编号
	 * @param oper_type	  操作类型：100部门共享任务锁定,200部门共享任务解锁,300分配任务给目标工号,400 分配任务给目标部门
	 * @param target_oper	 oper_type =300时必填
	 * @param target_depart oper_type =400时必填
	 * @return
	 * @throws Exception
	 */
	public UocMessage createTaskAssignment(ParaVo paraVo) throws Exception;

	/**
	 * UOC0036 获取任务详情
	 * @param jsession_id
	 * @param order_no
	 * @return
	 * @throws Exception
	 */
	public UocMessage getTaskDetailInfo(String jsession_id,String order_no) throws Exception;


	/**
	 * UOC0032 根据type_code查询业务（operCode）、产品 （prodCode）
	 * @param jsession_id
	 * @param type_code
	 * @return
	 * @throws Exception
	 */
	public UocMessage getTaskQueryInfo(String jsession_id,String type_code) throws Exception;

	/**
	 * UOC0046 获取可选任务包
	 * @param jsession_id
	 * @return
	 * @throws Exception
	 */
	public UocMessage getOptionalTaskPkgList(ParaVo paraVo) throws Exception;

	/**
	 * UOC0047 领取任务包
	 * @param jsession_id
	 * @param pkg_id
	 * @param pkg_num
	 * @return
	 * @throws Exception
	 */
	public UocMessage getTaskPackage(String jsession_id, String pkg_id, String pkg_num) throws Exception;

	/**
	 * UOC0055 任务环节统计
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UocMessage getTaskGroupByTacheCode(ArtificialTaskVo vo) throws Exception;
}
