package com.tydic.unicom.uoc.service.common.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("propertiesParamVo")
public class PropertiesParamVo {
	// 25天自动撤单固定工号
	@Value("${cancel.oper_no}")
	private String cancel_oper_no;
	// 消息队列地域
	@Value("${activemq.areaCodeList}")
	private String activemqAreaCodeList;
	// 备份地域
	@Value("${backup.areaCodeList}")
	private String backupAreaCodeList;
	// 是否执行定时发送消息队列
	@Value("${activemq.send}")
	private String activemqSend;
	// 是否执行定时备份
	@Value("${timing.backup}")
	private String timingBackup;
	// es服务器地址
	@Value("${es.serviceAddress}")
	private String esServiceAddress;
	// es IndexName表名
	@Value("${es.indexName}")
	private String indexName;
	// 本地临时文件存储路径
	@Value("${fileService.localTempFilePath}")
	private String localTempFilePath;
	// 文件服务器地址
	@Value("${fileService.fileServicePath}")
	private String fileServicePath;
	// 启动流程url
	@Value("${activiti.startProcess_0}")
	private String startProcess_0;
	@Value("${activiti.startProcess_1}")
	private String startProcess_1;
	// 查询当前任务
	@Value("${activiti.findPersonalTask_0}")
	private String findPersonalTask_0;
	@Value("${activiti.findPersonalTask_1}")
	private String findPersonalTask_1;
	// 提交当前任务
	@Value("${activiti.completePersonalTask_0}")
	private String completePersonalTask_0;
	@Value("${activiti.completePersonalTask_1}")
	private String completePersonalTask_1;
	// 撤销流程
	@Value("${activiti.revokeProcess_0}")
	private String revokeProcess_0;
	@Value("${activiti.revokeProcess_1}")
	private String revokeProcess_1;
	// 校验流程
	@Value("${activiti.checkProcess_0}")
	private String checkProcess_0;
	@Value("${activiti.checkProcess_1}")
	private String checkProcess_1;
	// 提交流程
	@Value("${activiti.completeProcess_0}")
	private String completeProcess_0;
	@Value("${activiti.completeProcess_1}")
	private String completeProcess_1;
	// 查询流程定义
	@Value("${activiti.findProcessDefinition}")
	private String findProcessDefinition;
	// 查询流程历史信息
	@Value("${activiti.findProcessHistoryInfo_0}")
	private String findProcessHistoryInfo_0;
	@Value("${activiti.findProcessHistoryInfo_1}")
	private String findProcessHistoryInfo_1;

	@Value("${abilityPlatForm.local_url}")
	private String localUrl;

	@Value("${abilityPlatForm.aop_url}")
	private String aopUrl;

	@Value("${abilityPlatForm.wo_url}")
	private String woUrl;

	@Value("${abilityPlatForm.new_appkey}")
	private String newAppkey;

	@Value("${abilityPlatForm.secret}")
	private String secret;

	@Value("${abilityPlatForm.local_token}")
	private String localToken;

	@Value("${abilityPlatForm.aop_token}")
	private String aopToken;

	@Value("${abilityPlatForm.wo_token}")
	private String woToken;

	@Value("${abilityPlatForm.v}")
	private String v;

	@Value("${abilityPlatForm.USS_ACTION}")
	private String ussAction;

	@Value("${abilityPlatForm.USS_USL_AJAX_HN}")
	private String ussUslAjaxHn;

	@Value("${abilityPlatForm.sf_url}")
	private String sfUrl;

	@Value("${abilityPlatForm.hn_aop_url}")
	private String hnAopUrl;

	@Value("${abilityPlatForm.hd_url}")
	private String hdUrl;

	@Value("${abilityPlatForm.hd_token}")
	private String hdToken;

	@Value("${abilityPlatForm.hd_appkey}")
	private String hdAppKey;

	@Value("${abilityPlatForm.df_url}")
	private String dfUrl;

	@Value("${abilityPlatForm.df_token}")
	private String dfToken;

	@Value("${abilityPlatForm.df_appkey}")
	private String dfAppkey;

	@Value("${abilityPlatForm.gzt_url}")
	private String gztUrl;

	@Value("${abilityPlatForm.gzt_token}")
	private String gztToken;

	// 开户证件上传
	@Value("${abilityPlatForm.sendPhoto_url}")
	private String sendPhotoUrl;

	@Value("${abilityPlatForm.sendPhoto_token}")
	private String sendPhotoToken;

	@Value("${abilityPlatForm.localSf_url}")
	private String localSfUrl;

	@Value("${abilityPlatForm.localSf_token}")
	private String localSfToken;

	@Value("${abilityPlatForm.ugc_url}")
	private String ugcUrl;

	@Value("${abilityPlatForm.ugc_token}")
	private String ugcToken;
	// 自动赠款工号
	@Value("${grant_oper_no}")
	private String grantOperNo;

	@Value("${abilityPlatForm.apc_url}")
	private String apcUrl;

	@Value("${abilityPlatForm.apc_token}")
	private String apcToken;

	@Value("${wxpayRefundFlag}")
	private String wxpayRefundFlag;

	@Value("${abilityPlatForm.sendMessage_url}")
	private String sendMessageUrl;

	@Value("${abilityPlatForm.sendMessage_token}")
	private String sendMessageToken;

	// 短信用户账号密码
	@Value("${sms.appKey}")
	private String smsAppKey;

	public String getCancel_oper_no() {
		return cancel_oper_no;
	}

	public String getWxpayRefundFlag() {
		return wxpayRefundFlag;
	}

	public String getGrantOperNo() {
		return grantOperNo;
	}

	public String getEsServiceAddress() {
		return esServiceAddress;
	}

	public String getStartProcess_0() {
		return startProcess_0;
	}

	public String getFindProcessDefinition() {
		return findProcessDefinition;
	}

	public String getStartProcess_1() {
		return startProcess_1;
	}

	public String getFindPersonalTask_0() {
		return findPersonalTask_0;
	}

	public String getFindPersonalTask_1() {
		return findPersonalTask_1;
	}

	public String getCompletePersonalTask_0() {
		return completePersonalTask_0;
	}

	public String getCompletePersonalTask_1() {
		return completePersonalTask_1;
	}

	public String getRevokeProcess_0() {
		return revokeProcess_0;
	}

	public String getRevokeProcess_1() {
		return revokeProcess_1;
	}

	public String getCheckProcess_0() {
		return checkProcess_0;
	}

	public String getCheckProcess_1() {
		return checkProcess_1;
	}

	public String getCompleteProcess_0() {
		return completeProcess_0;
	}

	public String getCompleteProcess_1() {
		return completeProcess_1;
	}

	public String getFindProcessHistoryInfo_0() {
		return findProcessHistoryInfo_0;
	}

	public String getFindProcessHistoryInfo_1() {
		return findProcessHistoryInfo_1;
	}

	public String getFileServicePath() {
		return fileServicePath;
	}

	public String getLocalUrl() {
		return localUrl;
	}

	public String getAopUrl() {
		return aopUrl;
	}

	public String getWoUrl() {
		return woUrl;
	}

	public String getNewAppkey() {
		return newAppkey;
	}

	public String getSecret() {
		return secret;
	}

	public String getLocalToken() {
		return localToken;
	}

	public String getAopToken() {
		return aopToken;
	}

	public String getWoToken() {
		return woToken;
	}

	public String getV() {
		return v;
	}

	public String getUssAction() {
		return ussAction;
	}

	public String getUssUslAjaxHn() {
		return ussUslAjaxHn;
	}

	public String getSfUrl() {
		return sfUrl;
	}

	public String getLocalTempFilePath() {
		return localTempFilePath;
	}

	public String getHnAopUrl() {
		return hnAopUrl;
	}

	public String getHdUrl() {
		return hdUrl;
	}

	public String getHdToken() {
		return hdToken;
	}

	public String getHdAppKey() {
		return hdAppKey;
	}

	public String getDfUrl() {
		return dfUrl;
	}

	public String getDfToken() {
		return dfToken;
	}

	public String getDfAppkey() {
		return dfAppkey;
	}

	public String getGztUrl() {
		return gztUrl;
	}

	public String getGztToken() {
		return gztToken;
	}

	public String getTimingBackup() {
		return timingBackup;
	}

	public String getSendPhotoUrl() {
		return sendPhotoUrl;
	}

	public String getSendPhotoToken() {
		return sendPhotoToken;
	}

	public String getActivemqSend() {
		return activemqSend;
	}

	public String getActivemqAreaCodeList() {
		return activemqAreaCodeList;
	}

	public String getBackupAreaCodeList() {
		return backupAreaCodeList;
	}

	public String getLocalSfUrl() {
		return localSfUrl;
	}

	public String getLocalSfToken() {
		return localSfToken;
	}

	public String getIndexName() {
		return indexName;
	}

	public String getUgcUrl() {
		return ugcUrl;
	}

	public String getUgcToken() {
		return ugcToken;
	}

	public String getApcUrl() {
		return apcUrl;
	}


	public String getApcToken() {
		return apcToken;
	}

	public String getSendMessageUrl() {
		return sendMessageUrl;
	}

	public String getSendMessageToken() {
		return sendMessageToken;
	}

	public String getSmsAppKey() {
		return smsAppKey;
	}

}
