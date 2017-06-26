package com.tydic.unicom.service.workFlow.service.interfaces;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.identity.Group;
import org.jbpm.api.identity.User;
import org.jbpm.api.task.Task;

import com.tydic.unicom.service.workFlow.po.ProgressList;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2014/6/19.
 */
public interface JbpmTemplate {
    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: deployByPath 
	 * @Description: TODO 依据xx.jpbl.xml的文件路径发布流程定义
     * @param classPath  xx.jpbl.xml的文件路径
     * @return deploymentId 发布后的流程定义ID
     * */
    public String deployByPath(String classPath);

    /**
     * 
     * @author dell 2014-8-8 下午2:57:47
     * @Method: getSuspenedProcessDefinitions 
     * @Description: TODO 按降序排列缓慢查询发布流程定义列表
     * @param pdKey 流程定义关键字
     * @return List<ProcessDefinition> 返回发布流程定义列表
     * @throws
     */
    public List<ProcessDefinition> getSuspenedProcessDefinitions(String pdKey);

    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: removeProcessDefinitionByDeployId 
	 * @Description: TODO 按流程定义ID级联删除发布流程定义及其下具体信息
     * @param deploymentId 要删除的流程定义ID
     * */
    public void removeProcessDefinitionByDeployId(String deploymentId);

    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: startProcessByKey 
	 * @Description: TODO 启动流程实例
     * @param pdKey  流程定义key
     * @return Map<String,Object> 流程实例返回信息，目前包括成功标识、状态等
     */
    public Map<String,Object> startProcessByKey(String pdKey);
    
    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: startProcessByKey 
	 * @Description: TODO 启动流程实例(流程定义key+流程实例key,可组合成流程实例ID)
     * @param pdKey  流程定义key
     * @param piKey  可以用订单id作为此字段传入，这样最终生成的流程实例ID为pdKey.piKey的形式
     * @return Map<String,Object> 流程实例返回信息，目前包括成功标识、状态等
     */
    public Map<String,Object> startProcessByKey(String pdKey,String piKey);
    
    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: startProcessByKey 
	 * @Description: TODO 启动流程实例(可设置流程实例中需用到的流程变量)
     * @param pdKey  流程定义key
     * @param variables 流程实例中需用到的流程变量
     * @return Map<String,Object> 流程实例返回信息，目前包括成功标识、状态等
     */
    public Map<String,Object> startProcessByKey(String pdKey,Map<String,Object> variables);
    
    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: startProcessByKey 
	 * @Description: TODO 启动流程实例(流程定义key+流程实例key,可组合成流程实例ID;并可设置流程实例中需用到的流程变量)
     * @param pdKey  流程定义key
     * @param variables 流程实例中需用到的流程变量
     * @param piKey  可以用订单id作为此字段传入，这样最终生成的流程实例ID为pdKey.piKey的形式
     * @return Map<String,Object> 流程实例返回信息，目前包括成功标识、状态等
     */
    public Map<String,Object> startProcessByKey(String pdKey,Map<String,Object> variables,String piKey);

    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: signalProcessById 
	 * @Description: TODO 执行等待的流程实例
     * @param procInstanceId  流程实例ID
     * @return Map<String,Object> 流程实例返回信息，目前包括成功标识、状态等
     */
    public Map<String,Object> signalProcessById(String procInstanceId);
    
    /**
     * @Description: TODO 执行等待的流程实例   （流程实例ID与流转条件参数(String)联合确定流向）
     * @param procInstanceId
     * @param str
     * @return
     */
    public Map<String,Object> signalProcessByIdAndStr(String procInstanceId,String str);
    
    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: signalProcessByIdAndParam 
	 * @Description: TODO 执行等待的流程实例（流程实例ID与流转条件参数联合确定流向）
     * @param procInstanceId  流程实例ID
     * @param parameters 流转条件参数
     * @return Map<String,Object> 流程实例返回信息，目前包括成功标识、状态等
     */
    public Map<String,Object> signalProcessByIdAndParam(String procInstanceId,Map<String, Object> parameters);

    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: endProcessInstance 
	 * @Description: TODO 终结流程实例
     * @param procInstanceId  流程实例ID
     * */
    public void endProcessInstance(String procInstanceId);

    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: removeProcessInstance 
	 * @Description: TODO 删除流程实例
     * @param procInstanceId 流程实例ID
     * */
    public void removeProcessInstance(String procInstanceId);

    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: getProcessInstances 
	 * @Description: TODO 查询已启动的流程实例
     * @param pdKey  流程定义key
     * @param piKey  可以用订单id作为此字段传入，这样最终生成的流程实例ID为pdKey.piKey的形式
     * @return List<ProcessInstance> 流程实例列表
     * */
    public List<ProcessInstance> getProcessInstances(String pdKey,String piKey);

    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: getPersonalTasks 
	 * @Description: TODO 查询个人代办任务
     * @param userName 任务处理人
     * @return List<Task> 代办任务列表
     * */
    public List<Task> getPersonalTasks(String userName);

    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: getTask 
	 * @Description: TODO 依据任务id查询对应任务信息
     * @param taskId 任务ID
     * @return Task 具体任务
     * */
    public Task getTask(String taskId);

    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: getTasks 
	 * @Description: TODO 查询所有代办任务
     * @return List<Task> 代办任务列表
     * */
    public List<Task> getTasks();
    
    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: getTaskIdByExecId 
	 * @Description: TODO 依据流程实例ID查询该流程实例当前代办任务
     * @param procInstanceId 流程实例ID
     * @return String 代办任务ID
     * */
    public String getTaskIdByExecId(String procInstanceId);

    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: completeTask 
	 * @Description: TODO 代办任务完成（区分处理分支动作）
     * @param taskId 代办任务ID
     * @param actName 处理分支动作类型(如：申请、驳回等)
     * @param variables 任务申请内容，已map的形式存储
     * */
    public void completeTask(String taskId, String actName, Map<String, Object> variables);

    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: completeTaskDefault 
	 * @Description: TODO 代办任务完成(默认)
     * @param taskId 代办任务ID
     * */
    public void completeTaskDefault(String taskId);

    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: getVariableForTask 
	 * @Description: TODO 依据任务ID及key获取任务内容变量
     * @param taskId 代办任务ID
     * @param varName 任务内容变量key
     * @return Object 返回任务内容
     * */
    public Object getVariableForTask(String taskId,String varName);

    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: getVariablesForTask 
	 * @Description: TODO 依据任务ID获取返回所有任务内容
     * @param taskId 代办任务ID
     * @return Map<String,Object> 返回任务内容
     * */
    public Map<String, Object> getVariablesForTask(String taskId);

    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: getVariablesForProcess 
	 * @Description: TODO 依据流程实例ID获取返回所有流程实例内容
     * @param procInstanceId 流程实例ID
     * @return Map<String,Object> 返回流程实例内容
     * */
    public Map<String, Object> getVariablesForProcess(String procInstanceId);
    
    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: getTaskUsers 
	 * @Description: TODO 获取流程实例任务处理候选人列表
     * @return List<User> 返回任务处理候选人信息列表
     * */
    public List<User> getTaskUsers();
    
    /**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: getTaskGroupsByUser 
	 * @Description: TODO 依据任务处理候选人ID获取流程实例任务候选人组信息列表
     * @param userId 用户ID
     * @return List<Group> 返回任务处理候选人组信息列表
     * */
	public List<Group> getTaskGroupsByUser(String userId);
	
	/**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: addTaskUser 
	 * @Description: TODO 添加任务处理候选人信息
     * @param userId 用户ID
     * @param givenName 名字
     * @param familyName 姓氏
     * @return boolean 添加任务处理候选人信息是否成功标识
     * */
	public boolean addTaskUser(String userId, String givenName, String familyName);
	
	/**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: removeTaskUser 
	 * @Description: TODO 删除任务处理候选人信息
     * @param userId 用户ID
     * */
	public void removeTaskUser(String userId);
	
	/**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: getTaskGroups 
	 * @Description: TODO 获取流程实例任务处理候选人组列表
     * @return List<Group> 返回任务分配组信息列表
     * */
	public List<Group> getTaskGroups();
	
	/**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: addTaskGroup 
	 * @Description: TODO 添加任务处理候选人组信息
     * @param groupName 组名
     * @param groupType 组类型
     * @param parentGroupId 父组ID
     * @return boolean 添加任务处理候选人组信息是否成功标识
     * */
	public boolean addTaskGroup(String name, String type, String parentGroupId);
	
	/**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: removeTaskGroup 
	 * @Description: TODO 删除任务处理候选人组信息
     * @param groupId 组ID
     * */
	public void removeTaskGroup(String groupId);
	
	/**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: addTaskMemberShip 
	 * @Description: TODO 添加任务处理候选人组及成员关系信息
     * @param userId 用户ID
     * @param groupId 组ID
     * @param role 组与成员间关系
     * */
	public void addTaskMemberShip(String userId, String groupId, String role);
	
	/**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: removeTaskMemberShip 
	 * @Description: TODO 删除任务处理候选人组及成员关系信息
     * @param userId 用户ID
     * @param groupId 组ID
     * @param role 组与成员间关系
     * */
	public void removeTaskMemberShip(String userId, String groupId, String role);
	
	/**
     * 
     * @author dell 2014-8-6 下午6:29:12
	 * @Method: getProcessStatus 
	 * @Description: TODO 查询获取当前流程实例步骤
     * @param procInstanceId 流程实例ID
     * 
     * @return Map<String,Object> 主要返回当前步骤的订单状态，成功标识、系统时间等
     * */
	public Map<String,Object> getProcessStatus(String procInstanceId);
	
	/**
	 * 
	 * @author dell 2014-8-6 下午6:29:12
	 * @Method: getHistActinst 
	 * @Description: TODO 获取工单已完成和正在进行的状态动作
	 * @param procInstanceId 流程实例ID
	 * @return List<ProgressList> 主要返回当前步骤的订单状态，当前和已完成状态标识、状态起始时间等
	 * @throws
	 */
	public List<ProgressList> getHistActinst(String procInstanceId);
	
	/**
	 * 
	 * @author dell 2014-8-6 下午5:57:37
	 * @Method: takeTaskForOper 
	 * @Description: TODO 任务接收(工单分组后，组中用户接收任务使用)
	 * @param taskId 任务ID
	 * @param operNo 操作员编号
	 * @return
	 * @throws
	 */
	public void takeTaskForOper(String taskId,String operNo);
	
	/**
	 * 
	 * @author yangwen 2014-8-19 上午11:02:19
	 * @Method: getProcInstancesByStatus 
	 * @Description: TODO 根据某一流程定义的ID,与对应状态获得相应流程实例列表
	 * @param pdId 流程定义ID(关键字+版本号的形式)
	 * @param status 具体状态
	 * @return List<ProcessInstance> 返回某一状态的流程实例列表
	 * @throws
	 */
	public List<ProcessInstance> getProcInstancesByStatus(String pdId,String status);
	
}
