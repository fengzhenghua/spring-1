package com.tydic.unicom.uoc.service.backup.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoDeliverOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoOfrOrderFeeHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoOfrOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoOfrOrderInvoiceHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoOfrOrderPayHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoOrderCancelHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoPayOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderAttrHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderDistrDetailHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderDistributionHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderEditHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderExtHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderFeeHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderOfrMapHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderPersionCertHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderPersionHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderServMapHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderActivityHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderAgreementHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderAttrHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderCollectionHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderDeveloperHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderExtHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderFeeHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderFixHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderGoodNbrHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderGuarantorHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderM165HisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderPackageHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderPayHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderPersionHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderProdMapHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderSimCardHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderTerminalHisPo;
import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskAssignDepartHisPo;
import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskAssignOperHisPo;
import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskAssignRecordHisPo;
import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskDealRecordHisPo;
import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskInstHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoDeliverOrderHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoOfrOrderFeeHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoOfrOrderHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoOfrOrderInvoiceHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoOfrOrderPayHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoOrderCancelHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoPayOrderHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderAttrHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderDistrDetailHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderDistributionHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderEditHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderExtHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderFeeHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderOfrMapHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderPersionCertHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderPersionHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderServMapHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderActivityHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderAgreementHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderAttrHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderCollectionHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderDeveloperHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderExtHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderFeeHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderFixHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderGoodNbrHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderGuarantorHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderM165HisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderPackageHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderPayHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderPersionHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderProdMapHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderSimCardHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderTerminalHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ProcInstTaskAssignDepartHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ProcInstTaskAssignOperHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ProcInstTaskAssignRecordHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ProcInstTaskDealRecordHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ProcInstTaskInstHisServ;
import com.tydic.unicom.uoc.base.uocinst.po.InfoDeliverOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderFeePo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderInvoicePo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderPayPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoOrderCancelPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoPayOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderAttrPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderDistrDetailPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderDistributionPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderEditPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderExtPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderFeePo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderOfrMapPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPersionCertPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPersionPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderServMapPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderActivityPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderAgreementPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderAttrPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderCollectionPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderDeveloperPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderExtPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderFeePo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderFixPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderGoodNbrPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderGuarantorPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderM165Po;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPackagePo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPayPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPersionPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderProdMapPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderSimCardPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderTerminalPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskAssignDepartPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskAssignOperPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskAssignRecordPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskDealRecordPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskInstPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoDeliverOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOfrOrderFeeServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOfrOrderInvoiceServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOfrOrderPayServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOfrOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOrderCancelServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoPayOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderAttrServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderDistrDetailServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderDistributionServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderEditServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderExtServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderFeeServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderOfrMapServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderPersionCertServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderPersionServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderServMapServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderActivityServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderAgreementServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderAttrServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderCollectionServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderDeveloperServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderExtServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderFeeServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderFixServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderGoodNbrServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderGuarantorServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderM165Serv;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderPackageServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderPayServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderPersionServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderProdMapServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderSimCardServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderTerminalServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskAssignDepartServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskAssignOperServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskAssignRecordServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskDealRecordServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskInstServ;
import com.tydic.unicom.uoc.service.backup.interfaces.BackupDataServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("BackupDataServDu")
public class BackupDataServDuImpl implements BackupDataServDu{

	Logger logger = Logger.getLogger(BackupDataServDuImpl.class);

	@Autowired
	private InfoSaleOrderServ infoSaleOrderServ;

	@Autowired
	private InfoSaleOrderHisServ infoSaleOrderHisServ;

	@Autowired
	private InfoSaleOrderFeeServ infoSaleOrderFeeServ;

	@Autowired
	private InfoSaleOrderFeeHisServ infoSaleOrderFeeHisServ;

	@Autowired
	private InfoSaleOrderAttrServ infoSaleOrderAttrServ;

	@Autowired
	private InfoSaleOrderAttrHisServ infoSaleOrderAttrHisServ;

	@Autowired
	private InfoSaleOrderPersionServ infoSaleOrderPersionServ;

	@Autowired
	private InfoSaleOrderPersionHisServ infoSaleOrderPersionHisServ;

	@Autowired
	private InfoSaleOrderDistributionServ infoSaleOrderDistributionServ;

	@Autowired
	private InfoSaleOrderDistributionHisServ infoSaleOrderDistributionHisServ;

	@Autowired
	private InfoSaleOrderDistrDetailServ infoSaleOrderDistrDetailServ;

	@Autowired
	private InfoSaleOrderDistrDetailHisServ infoSaleOrderDistrDetailHisServ;

	@Autowired
	private InfoSaleOrderOfrMapServ infoSaleOrderOfrMapServ;

	@Autowired
	private InfoSaleOrderOfrMapHisServ infoSaleOrderOfrMapHisServ;

	@Autowired
	private InfoSaleOrderServMapServ infoSaleOrderServMapServ;

	@Autowired
	private InfoSaleOrderServMapHisServ infoSaleOrderServMapHisServ;

	@Autowired
	private InfoOfrOrderServ infoOfrOrderServ;

	@Autowired
	private InfoOfrOrderHisServ infoOfrOrderHisServ;

	@Autowired
	private InfoOfrOrderInvoiceServ infoOfrOrderInvoiceServ;

	@Autowired
	private InfoOfrOrderInvoiceHisServ infoOfrOrderInvoiceHisServ;

	@Autowired
	private InfoOfrOrderPayServ infoOfrOrderPayServ;

	@Autowired
	private InfoOfrOrderPayHisServ infoOfrOrderPayHisServ;

	@Autowired
	private InfoOfrOrderFeeServ infoOfrOrderFeeServ;

	@Autowired
	private InfoOfrOrderFeeHisServ infoOfrOrderFeeHisServ;

	@Autowired
	private InfoServiceOrderServ infoServiceOrderServ;

	@Autowired
	private InfoServiceOrderHisServ infoServiceOrderHisServ;

	@Autowired
	private InfoServiceOrderPayHisServ infoServiceOrderPayHisServ;

	@Autowired
	private InfoServiceOrderPayServ infoServiceOrderPayServ;

	@Autowired
	private InfoServiceOrderFeeServ infoServiceOrderFeeServ;

	@Autowired
	private InfoServiceOrderFeeHisServ infoServiceOrderFeeHisServ;

	@Autowired
	private InfoServiceOrderAttrServ infoServiceOrderAttrServ;

	@Autowired
	private InfoServiceOrderAttrHisServ infoServiceOrderAttrHisServ;

	@Autowired
	private InfoServiceOrderProdMapServ infoServiceOrderProdMapServ;

	@Autowired
	private InfoServiceOrderProdMapHisServ infoServiceOrderProdMapHisServ;

	@Autowired
	private InfoServiceOrderTerminalServ infoServiceOrderTerminalServ;

	@Autowired
	private InfoServiceOrderTerminalHisServ infoServiceOrderTerminalHisServ;

	@Autowired
	private InfoServiceOrderActivityServ infoServiceOrderActivityServ;

	@Autowired
	private InfoServiceOrderActivityHisServ infoServiceOrderActivityHisServ;

	@Autowired
	private InfoServiceOrderAgreementServ infoServiceOrderAgreementServ;

	@Autowired
	private InfoServiceOrderAgreementHisServ infoServiceOrderAgreementHisServ;

	@Autowired
	private InfoServiceOrderPackageServ infoServiceOrderPackageServ;

	@Autowired
	private InfoServiceOrderPackageHisServ infoServiceOrderPackageHisServ;

	@Autowired
	private InfoServiceOrderSimCardServ infoServiceOrderSimCardServ;

	@Autowired
	private InfoServiceOrderSimCardHisServ infoServiceOrderSimCardHisServ;

	@Autowired
	private InfoServiceOrderFixServ infoServiceOrderFixServ;

	@Autowired
	private InfoServiceOrderFixHisServ infoServiceOrderFixHisServ;

	@Autowired
	private InfoServiceOrderM165Serv infoServiceOrderM165Serv;

	@Autowired
	private InfoServiceOrderM165HisServ infoServiceOrderM165HisServ;

	@Autowired
	private InfoServiceOrderGoodNbrServ infoServiceOrderGoodNbrServ;

	@Autowired
	private InfoServiceOrderGoodNbrHisServ infoServiceOrderGoodNbrHisServ;

	@Autowired
	private InfoServiceOrderCollectionServ infoServiceOrderCollectionServ;

	@Autowired
	private InfoServiceOrderCollectionHisServ infoServiceOrderCollectionHisServ;

	@Autowired
	private InfoServiceOrderGuarantorServ infoServiceOrderGuarantorServ;

	@Autowired
	private InfoServiceOrderGuarantorHisServ infoServiceOrderGuarantorHisServ;

	@Autowired
	private InfoServiceOrderDeveloperServ infoServiceOrderDeveloperServ;

	@Autowired
	private InfoServiceOrderDeveloperHisServ infoServiceOrderDeveloperHisServ;

	@Autowired
	private InfoServiceOrderPersionServ infoServiceOrderPersionServ;

	@Autowired
	private InfoServiceOrderPersionHisServ infoServiceOrderPersionHisServ;

	@Autowired
	private InfoSaleOrderEditServ infoSaleOrderEditServ;

	@Autowired
	private InfoSaleOrderEditHisServ infoSaleOrderEditHisServ;

	@Autowired
	private InfoPayOrderServ infoPayOrderServ;

	@Autowired
	private InfoPayOrderHisServ infoPayOrderHisServ;

	@Autowired
	private InfoDeliverOrderServ infoDeliverOrderServ;

	@Autowired
	private InfoDeliverOrderHisServ infoDeliverOrderHisServ;

	@Autowired
	private ProcInstTaskInstServ procInstTaskInstServ;

	@Autowired
	private ProcInstTaskAssignDepartServ procInstTaskAssignDepartServ;

	@Autowired
	private ProcInstTaskAssignOperServ procInstTaskAssignOperServ;

	@Autowired
	private ProcInstTaskAssignRecordServ procInstTaskAssignRecordServ;

	@Autowired
	private ProcInstTaskInstHisServ procInstTaskInstHisServ;

	@Autowired
	private ProcInstTaskDealRecordServ procInstTaskDealRecordServ;

	@Autowired
	private ProcInstTaskDealRecordHisServ procInstTaskDealRecordHisServ;

	@Autowired
	private ProcInstTaskAssignDepartHisServ procInstTaskAssignDepartHisServ;

	@Autowired
	private ProcInstTaskAssignOperHisServ procInstTaskAssignOperHisServ;

	@Autowired
	private ProcInstTaskAssignRecordHisServ procInstTaskAssignRecordHisServ;

	@Autowired
	private InfoSaleOrderExtServ infoSaleOrderExtServ;

	@Autowired
	private InfoServiceOrderExtServ infoServiceOrderExtServ;

	@Autowired
	private InfoSaleOrderExtHisServ infoSaleOrderExtHisServ;

	@Autowired
	private InfoServiceOrderExtHisServ infoServiceOrderExtHisServ;

	@Autowired
	private InfoSaleOrderPersionCertServ infoSaleOrderPersionCertServ;

	@Autowired
	private InfoSaleOrderPersionCertHisServ infoSaleOrderPersionCertHisServ;

	@Autowired
	private InfoOrderCancelServ infoOrderCancelServ ;
	
	@Autowired
	private InfoOrderCancelHisServ infoOrderCancelHisServ;
	
	@Override
	public UocMessage queryRawData(String order_no,String oper_type)  throws Exception{
		UocMessage message=new UocMessage();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if("".equals(order_no) || order_no==null){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("order_no查询参数为空");
			return message;
		}
		if("".equals(oper_type) || oper_type==null){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("oper_type查询参数为空");
			return message;
		}

		if("100".equals(oper_type)){
			//获取销售订单表数据
			InfoSaleOrderPo po = new InfoSaleOrderPo();
			po.setSale_order_no(order_no);
			InfoSaleOrderPo infoSaleOrderPo=infoSaleOrderServ.getInfoSaleOrderPoBySaleOrderNo(po);
			if(infoSaleOrderPo!=null){
				dataMap.put("info_sale_order", infoSaleOrderPo);
			}

			//获取销售订单费用信息表数据
			InfoSaleOrderFeePo infoSaleOrderFeePo =new InfoSaleOrderFeePo();
			infoSaleOrderFeePo.setSale_order_no(order_no);
			InfoSaleOrderFeePo infoSaleOrderFeePoTemp=infoSaleOrderFeeServ.getInfoSaleOrderFeePoBySaleOrderNo(infoSaleOrderFeePo);
			if(infoSaleOrderFeePoTemp!=null){
				dataMap.put("info_sale_fee", infoSaleOrderFeePoTemp);
			}

			//销售订单修订表
			InfoSaleOrderEditPo infoSaleOrderEditPo = new InfoSaleOrderEditPo();
			infoSaleOrderEditPo.setSale_order_no(order_no);
			List<InfoSaleOrderEditPo> infoSaleOrderEditPoTemp=infoSaleOrderEditServ.queryInfoSaleOrderEditPoBySaleOrderNo(infoSaleOrderEditPo);
			if(infoSaleOrderEditPoTemp!=null && infoSaleOrderEditPoTemp.size()>0){
				dataMap.put("info_sale_edit", infoSaleOrderEditPoTemp);
			}

			//销售订单属性集表
			InfoSaleOrderAttrPo infoSaleOrderAttrPo =new InfoSaleOrderAttrPo();
			infoSaleOrderAttrPo.setSale_order_no(order_no);
			List<InfoSaleOrderAttrPo> infoSaleOrderAttrPoTemp=infoSaleOrderAttrServ.queryInfoSaleOrderAttrBySaleOrderNo(infoSaleOrderAttrPo);
			if(infoSaleOrderAttrPoTemp!=null && infoSaleOrderAttrPoTemp.size()>0){
				dataMap.put("info_sale_attr", infoSaleOrderAttrPoTemp);
			}

			//销售订单个客信息表
			InfoSaleOrderPersionPo infoSaleOrderPersionPo =new InfoSaleOrderPersionPo();
			infoSaleOrderPersionPo.setSale_order_no(order_no);
			InfoSaleOrderPersionPo infoSaleOrderPersionPoTemp=infoSaleOrderPersionServ.getInfoSaleOrderPersionBySaleOrderNo(infoSaleOrderPersionPo);
			if(infoSaleOrderPersionPoTemp!=null){
				dataMap.put("info_sale_person", infoSaleOrderPersionPoTemp);
			}

			//新表info_sale_order_persion_cert
			InfoSaleOrderPersionCertPo infoSaleOrderPersionCertPo =new InfoSaleOrderPersionCertPo();
			infoSaleOrderPersionCertPo.setSale_order_no(order_no);
			InfoSaleOrderPersionCertPo infoSaleOrderPersionCertPoTemp =infoSaleOrderPersionCertServ.getInfoSaleOrderPersionCertBySaleOrderNo(infoSaleOrderPersionCertPo);
			if(infoSaleOrderPersionPoTemp!=null){
				dataMap.put("info_sale_person_cert", infoSaleOrderPersionCertPoTemp);
			}

			//销售订单收件人信息表
			InfoSaleOrderDistributionPo infoSaleOrderDistributionPo =new InfoSaleOrderDistributionPo();
			infoSaleOrderDistributionPo.setSale_order_no(order_no);
			InfoSaleOrderDistributionPo infoSaleOrderDistributionPoTemp=infoSaleOrderDistributionServ.getInfoSaleOrderDistributionBySaleOrderNo(infoSaleOrderDistributionPo);
			if(infoSaleOrderDistributionPoTemp!=null){
				dataMap.put("info_sale_distr", infoSaleOrderDistributionPoTemp);
			}

			//销售订单包裹信息表
			InfoSaleOrderDistrDetailPo infoSaleOrderDistrDetailPo =new InfoSaleOrderDistrDetailPo();
			infoSaleOrderDistrDetailPo.setSale_order_no(order_no);
			List<InfoSaleOrderDistrDetailPo> infoSaleOrderDistrDetailPoList=infoSaleOrderDistrDetailServ.queryInfoSaleOrderDistrDetailBySaleOrderNo(infoSaleOrderDistrDetailPo);
			if(infoSaleOrderDistrDetailPoList!=null && infoSaleOrderDistrDetailPoList.size()>0){
				dataMap.put("info_sale_distr_detail", infoSaleOrderDistrDetailPoList);
			}

			//销售订单商品表
			InfoSaleOrderOfrMapPo infoSaleOrderOfrMapPo =new InfoSaleOrderOfrMapPo();
			infoSaleOrderOfrMapPo.setSale_order_no(order_no);
			List<InfoSaleOrderOfrMapPo> infoSaleOrderOfrMapPoTemp=infoSaleOrderOfrMapServ.queryInfoSaleOrderOfrMapBySaleOrderNo(infoSaleOrderOfrMapPo);

			if(infoSaleOrderOfrMapPoTemp!=null && infoSaleOrderOfrMapPoTemp.size()>0){
				dataMap.put("info_sale_ofr_map", infoSaleOrderOfrMapPoTemp);
			}

			//销售订单业务表
			InfoSaleOrderServMapPo infoSaleOrderServMapPo =new InfoSaleOrderServMapPo();
			infoSaleOrderServMapPo.setSale_order_no(order_no);
			List<InfoSaleOrderServMapPo> infoSaleOrderServMapPoTemp =infoSaleOrderServMapServ.queryInfoSaleOrderServMapBySaleOrderNo(infoSaleOrderServMapPo);

			if(infoSaleOrderServMapPoTemp!=null && infoSaleOrderServMapPoTemp.size()>0){
				dataMap.put("info_sale_serv_map", infoSaleOrderServMapPoTemp);
			}

			//销售订单拓展属性横表
			InfoSaleOrderExtPo infoSaleOrderExtPo = new InfoSaleOrderExtPo();
			infoSaleOrderExtPo.setSale_order_no(order_no);
			InfoSaleOrderExtPo infoSaleOrderExtPoTemp=infoSaleOrderExtServ.getInfoSaleOrderExtBySaleOrderNo(infoSaleOrderExtPo);

			if(infoSaleOrderExtPoTemp!=null){
				dataMap.put("info_sale_ext", infoSaleOrderExtPoTemp);
			}

			//商品订单表
			InfoOfrOrderPo infoOfrOrderPo =new InfoOfrOrderPo();
			infoOfrOrderPo.setSale_order_no(order_no);
			List<InfoOfrOrderPo> infoOfrOrderPoTemp=infoOfrOrderServ.queryInfoOfrOrderBySaleOrderNo(infoOfrOrderPo);

			if(infoOfrOrderPoTemp!=null && infoOfrOrderPoTemp.size()>0){
				dataMap.put("info_ofr_order", infoOfrOrderPoTemp);
			}

			//商品订单一次性费用发票表
			InfoOfrOrderInvoicePo infoOfrOrderInvoicePo =new InfoOfrOrderInvoicePo();
			infoOfrOrderInvoicePo.setSale_order_no(order_no);
			List<InfoOfrOrderInvoicePo> infoOfrOrderInvoicePoTemp = infoOfrOrderInvoiceServ.queryInfoOfrOrderInvoiceByOrderNo(infoOfrOrderInvoicePo);

			if(infoOfrOrderInvoicePoTemp!=null && infoOfrOrderInvoicePoTemp.size()>0){
				dataMap.put("info_ofr_invoice", infoOfrOrderInvoicePoTemp);
			}


			//商品订单收费详情表
			InfoOfrOrderPayPo infoOfrOrderPayPo =new InfoOfrOrderPayPo();
			infoOfrOrderPayPo.setSale_order_no(order_no);
			List<InfoOfrOrderPayPo> infoOfrOrderPayPoTemp = infoOfrOrderPayServ.queryInfoOfrOrderPayByOrderNo(infoOfrOrderPayPo);

			if(infoOfrOrderPayPoTemp!=null && infoOfrOrderPayPoTemp.size()>0){
				dataMap.put("info_ofr_pay", infoOfrOrderPayPoTemp);
			}

			//商品订单费用详情表
			InfoOfrOrderFeePo infoOfrOrderFeePo =new InfoOfrOrderFeePo();
			infoOfrOrderFeePo.setSale_order_no(order_no);
			List<InfoOfrOrderFeePo> infoOfrOrderFeePoTemp = infoOfrOrderFeeServ.queryInfoOfrOrderFeeByOrderNo(infoOfrOrderFeePo);

			if(infoOfrOrderFeePoTemp!=null && infoOfrOrderFeePoTemp.size()>0){
				dataMap.put("info_ofr_fee", infoOfrOrderFeePoTemp);
			}

			//服务订单表
			InfoServiceOrderPo infoServiceOrderPo =new InfoServiceOrderPo();
			infoServiceOrderPo.setSale_order_no(order_no);
			List<InfoServiceOrderPo> infoServiceOrderPoTemp=infoServiceOrderServ.queryInfoServiceOrderBySaleOrderNo(infoServiceOrderPo);

			if(infoServiceOrderPoTemp!=null && infoServiceOrderPoTemp.size()>0){
				dataMap.put("info_service_order", infoServiceOrderPoTemp);
			}

			//服务订单收费详情表
			InfoServiceOrderPayPo infoServiceOrderPayPo =new InfoServiceOrderPayPo();
			infoServiceOrderPayPo.setSale_order_no(order_no);
			List<InfoServiceOrderPayPo> infoServiceOrderPayPoTemp=infoServiceOrderPayServ.queryInfoServiceOrderPayByOrderNo(infoServiceOrderPayPo);

			if(infoServiceOrderPayPoTemp!=null && infoServiceOrderPayPoTemp.size()>0){
				dataMap.put("info_serv_pay", infoServiceOrderPayPoTemp);
			}

			//服务订单费用详情表
			InfoServiceOrderFeePo infoServiceOrderFeePo =new InfoServiceOrderFeePo();
			infoServiceOrderFeePo.setSale_order_no(order_no);
			List<InfoServiceOrderFeePo> infoServiceOrderFeePoTemp=infoServiceOrderFeeServ.queryInfoServiceOrderFeeBySaleOrderNo(infoServiceOrderFeePo);

			if(infoServiceOrderFeePoTemp!=null && infoServiceOrderFeePoTemp.size()>0){
				dataMap.put("info_serv_fee", infoServiceOrderFeePoTemp);
			}

			//服务订单属性表
			InfoServiceOrderAttrPo infoServiceOrderAttrPo =new InfoServiceOrderAttrPo();
			infoServiceOrderAttrPo.setSale_order_no(order_no);
			List<InfoServiceOrderAttrPo> infoServiceOrderAttrPoTemp=infoServiceOrderAttrServ.queryInfoServiceOrderAttrByOrderNo(infoServiceOrderAttrPo);

			if(infoServiceOrderAttrPoTemp!=null && infoServiceOrderAttrPoTemp.size()>0){
				dataMap.put("info_serv_attr", infoServiceOrderAttrPoTemp);
			}

			//服务订单产品列表
			InfoServiceOrderProdMapPo infoServiceOrderProdMapPo =new InfoServiceOrderProdMapPo();
			infoServiceOrderProdMapPo.setSale_order_no(order_no);
			List<InfoServiceOrderProdMapPo> infoServiceOrderProdMapPoTemp=infoServiceOrderProdMapServ.queryInfoServiceOrderProdMapByOrderNo(infoServiceOrderProdMapPo);

			if(infoServiceOrderProdMapPoTemp!=null && infoServiceOrderProdMapPoTemp.size()>0){
				dataMap.put("info_serv_prod_map", infoServiceOrderProdMapPoTemp);
			}

			//服务订单终端表
			InfoServiceOrderTerminalPo infoServiceOrderTerminalPo = new InfoServiceOrderTerminalPo();
			infoServiceOrderTerminalPo.setSale_order_no(order_no);
			List<InfoServiceOrderTerminalPo> infoServiceOrderTerminalPoTemp=infoServiceOrderTerminalServ.queryInfoServiceOrderTerminalByOrderNo(infoServiceOrderTerminalPo);

			if(infoServiceOrderTerminalPoTemp!=null && infoServiceOrderTerminalPoTemp.size()>0){
				dataMap.put("info_serv_terminal", infoServiceOrderTerminalPoTemp);
			}

			//服务订单优惠活动
			InfoServiceOrderActivityPo infoServiceOrderActivityPo = new InfoServiceOrderActivityPo();
			infoServiceOrderActivityPo.setSale_order_no(order_no);
			List<InfoServiceOrderActivityPo> infoServiceOrderActivityPoTemp=infoServiceOrderActivityServ.queryInfoServiceOrderActivityByOrderNo(infoServiceOrderActivityPo);

			if(infoServiceOrderActivityPoTemp!=null && infoServiceOrderActivityPoTemp.size()>0){
				dataMap.put("info_serv_activity", infoServiceOrderActivityPoTemp);
			}


			//服务订单协议表
			InfoServiceOrderAgreementPo infoServiceOrderAgreementPo =new InfoServiceOrderAgreementPo();
			infoServiceOrderAgreementPo.setSale_order_no(order_no);
			List<InfoServiceOrderAgreementPo> infoServiceOrderAgreementPoTemp=infoServiceOrderAgreementServ.queryInfoServiceOrderAgreementByOrderNo(infoServiceOrderAgreementPo);

			if(infoServiceOrderAgreementPoTemp!=null && infoServiceOrderAgreementPoTemp.size()>0){
				dataMap.put("info_serv_agreement", infoServiceOrderAgreementPoTemp);
			}

			//服务订单套餐列表
			InfoServiceOrderPackagePo infoServiceOrderPackagePo =new InfoServiceOrderPackagePo();
			infoServiceOrderPackagePo.setSale_order_no(order_no);
			List<InfoServiceOrderPackagePo> infoServiceOrderPackagePoTemp=infoServiceOrderPackageServ.queryInfoServiceOrderPackageByOrderNo(infoServiceOrderPackagePo);

			if(infoServiceOrderPackagePoTemp!=null && infoServiceOrderPackagePoTemp.size()>0){
				dataMap.put("info_serv_pkg", infoServiceOrderPackagePoTemp);
			}

			//服务订单SIM卡信息表
			InfoServiceOrderSimCardPo infoServiceOrderSimCardPo = new InfoServiceOrderSimCardPo();
			infoServiceOrderSimCardPo.setSale_order_no(order_no);
			List<InfoServiceOrderSimCardPo> infoServiceOrderSimCardPoTemp =infoServiceOrderSimCardServ.queryInfoServiceOrderSimCardByOrderNo(infoServiceOrderSimCardPo);

			if(infoServiceOrderSimCardPoTemp!=null && infoServiceOrderSimCardPoTemp.size()>0){
				dataMap.put("info_serv_sim_card", infoServiceOrderSimCardPoTemp);
			}

			//服务订单固网信息表
			InfoServiceOrderFixPo infoServiceOrderFixPo = new InfoServiceOrderFixPo();
			infoServiceOrderFixPo.setSale_order_no(order_no);
			List<InfoServiceOrderFixPo> infoServiceOrderFixPoTemp=infoServiceOrderFixServ.queryInfoServiceOrderFixByOrderNo(infoServiceOrderFixPo);

			if(infoServiceOrderFixPoTemp!=null && infoServiceOrderFixPoTemp.size()>0){
				dataMap.put("info_serv_fix", infoServiceOrderFixPoTemp);
			}

			//服务订单宽带信息表
			InfoServiceOrderM165Po infoServiceOrderM165Po =new InfoServiceOrderM165Po();
			infoServiceOrderM165Po.setSale_order_no(order_no);
			List<InfoServiceOrderM165Po> infoServiceOrderM165PoTemp=infoServiceOrderM165Serv.queryInfoServiceOrderM165ByOrderNo(infoServiceOrderM165Po);

			if(infoServiceOrderM165PoTemp!=null && infoServiceOrderM165PoTemp.size()>0){
				dataMap.put("info_serv_m165", infoServiceOrderM165PoTemp);
			}

			//服务订单靓号信息表
			InfoServiceOrderGoodNbrPo infoServiceOrderGoodNbrPo =new InfoServiceOrderGoodNbrPo();
			infoServiceOrderGoodNbrPo.setSale_order_no(order_no);
			List<InfoServiceOrderGoodNbrPo> infoServiceOrderGoodNbrPoTemp=infoServiceOrderGoodNbrServ.queryInfoServiceOrderGoodNbrByOrderNo(infoServiceOrderGoodNbrPo);

			if(infoServiceOrderM165PoTemp!=null && infoServiceOrderGoodNbrPoTemp.size()>0){
				dataMap.put("info_serv_good_nbr", infoServiceOrderGoodNbrPoTemp);
			}


			//服务订单银行托收表
			InfoServiceOrderCollectionPo infoServiceOrderCollectionPo = new InfoServiceOrderCollectionPo();
			infoServiceOrderCollectionPo.setSale_order_no(order_no);
			List<InfoServiceOrderCollectionPo> infoServiceOrderCollectionPoTemp=infoServiceOrderCollectionServ.queryInfoServiceOrderCollectionByOrderNo(infoServiceOrderCollectionPo);

			if(infoServiceOrderCollectionPoTemp!=null && infoServiceOrderCollectionPoTemp.size()>0){
				dataMap.put("info_serv_collection", infoServiceOrderCollectionPoTemp);
			}

			//服务订单担保人表
			InfoServiceOrderGuarantorPo infoServiceOrderGuarantorPo = new InfoServiceOrderGuarantorPo();
			infoServiceOrderGuarantorPo.setSale_order_no(order_no);
			List<InfoServiceOrderGuarantorPo> infoServiceOrderGuarantorPoTemp=infoServiceOrderGuarantorServ.queryInfoServiceOrderGuarantorByOrderNo(infoServiceOrderGuarantorPo);

			if(infoServiceOrderGuarantorPoTemp!=null && infoServiceOrderGuarantorPoTemp.size()>0){
				dataMap.put("info_serv_guarantor", infoServiceOrderGuarantorPoTemp);
			}

			//服务订单发展人表
			InfoServiceOrderDeveloperPo infoServiceOrderDeveloperPo = new InfoServiceOrderDeveloperPo();
			infoServiceOrderDeveloperPo.setSale_order_no(order_no);
			List<InfoServiceOrderDeveloperPo> infoServiceOrderDeveloperPoTemp=infoServiceOrderDeveloperServ.queryInfoServiceOrderDeveloperByOrderNo(infoServiceOrderDeveloperPo);

			if(infoServiceOrderDeveloperPoTemp!=null && infoServiceOrderDeveloperPoTemp.size()>0){
				dataMap.put("info_serv_developer", infoServiceOrderDeveloperPoTemp);
			}

			//服务订单个客信息表
			InfoServiceOrderPersionPo infoServiceOrderPersionPo = new InfoServiceOrderPersionPo();
			infoServiceOrderPersionPo.setSale_order_no(order_no);
			List<InfoServiceOrderPersionPo> infoServiceOrderPersionPoTemp=infoServiceOrderPersionServ.queryInfoServiceOrderPersionByOrderNo(infoServiceOrderPersionPo);

			if(infoServiceOrderPersionPoTemp!=null && infoServiceOrderPersionPoTemp.size()>0){
				dataMap.put("info_serv_person", infoServiceOrderPersionPoTemp);
			}

			//服务订单拓展属性横表
			InfoServiceOrderExtPo infoServiceOrderExtPo= new InfoServiceOrderExtPo();
			infoServiceOrderExtPo.setSale_order_no(order_no);
			List<InfoServiceOrderExtPo> infoServiceOrderExtPoTemp=infoServiceOrderExtServ.queryInfoServiceOrderExtByOrderNo(infoServiceOrderExtPo);

			if(infoServiceOrderExtPoTemp!=null && infoServiceOrderExtPoTemp.size()>0){
				dataMap.put("info_serv_ext", infoServiceOrderExtPoTemp);
			}

			//人工任务实例表
			ProcInstTaskInstPo procInstTaskInstPo = new ProcInstTaskInstPo();
			procInstTaskInstPo.setOrder_no(order_no);
			procInstTaskInstPo.setOrder_type("100");
			List<ProcInstTaskInstPo> procInstTaskInstPoTemp = procInstTaskInstServ.queryTaskInstByOrderNo1(procInstTaskInstPo);
			if(procInstTaskInstPoTemp!=null && procInstTaskInstPoTemp.size()>0){
				dataMap.put("task_inst_sale", procInstTaskInstPoTemp);
			}
		
			//人工任务操作记录
			ProcInstTaskDealRecordPo procInstTaskDealRecordPo = new ProcInstTaskDealRecordPo();
			procInstTaskDealRecordPo.setOrder_no(order_no);
			List<ProcInstTaskDealRecordPo> procInstTaskDealRecordPoTemp = procInstTaskDealRecordServ.queryProcInstTaskDealRecordByOrderNo(procInstTaskDealRecordPo);
			if(procInstTaskDealRecordPoTemp!=null && procInstTaskDealRecordPoTemp.size()>0){
				dataMap.put("task_deal_record_sale", procInstTaskDealRecordPoTemp);
			}

			//撤单记录表
			InfoOrderCancelPo infoOrderCancelPo=new InfoOrderCancelPo();
			infoOrderCancelPo.setOrder_no(order_no);
			
			List<InfoOrderCancelPo> infoOrderCancelPoTemp = infoOrderCancelServ.queryInfoOrderCancelList(infoOrderCancelPo);
			if(infoOrderCancelPoTemp!=null && infoOrderCancelPoTemp.size()>0){
				dataMap.put("info_order_cancel_sale", infoOrderCancelPoTemp);
			}
			
			// 处理销售订单下所有服务订单
			if(infoServiceOrderPoTemp!=null){
				String servOrderNo="";
				
				//task_inst
				List<ProcInstTaskInstPo> procInstTaskInstPoTempAll = new ArrayList<ProcInstTaskInstPo>();
				ProcInstTaskInstPo procInstTaskInstPo1 = new ProcInstTaskInstPo();
				List<ProcInstTaskInstPo> procInstTaskInstPoTemp1 = null;
				
				// task_deal_record
				List<ProcInstTaskDealRecordPo> procInstTaskDealRecordPoTempAll = new ArrayList<ProcInstTaskDealRecordPo>();
				ProcInstTaskDealRecordPo procInstTaskDealRecordPo1 = new ProcInstTaskDealRecordPo();
				List<ProcInstTaskDealRecordPo> procInstTaskDealRecordPoTemp1 = null;
				
				//info_order_cancel
				List<InfoOrderCancelPo> infoOrderCancelPoTempAll=new ArrayList<InfoOrderCancelPo>();
				InfoOrderCancelPo infoOrderCancelPo1 = new InfoOrderCancelPo();
				List<InfoOrderCancelPo> infoOrderCancelPoTemp1 = null;
				
				for(InfoServiceOrderPo poTemp:infoServiceOrderPoTemp){
					servOrderNo=poTemp.getServ_order_no();
					
					//task_inst
					procInstTaskInstPo1.setOrder_no(servOrderNo);
					procInstTaskInstPo1.setOrder_type("101");
					procInstTaskInstPoTemp1 = procInstTaskInstServ.queryTaskInstByOrderNo1(procInstTaskInstPo1);
					if(procInstTaskInstPoTemp1!=null && procInstTaskInstPoTemp1.size()>0){
						procInstTaskInstPoTempAll.addAll(procInstTaskInstPoTemp1);
					}
					
					// task_deal_record
					procInstTaskDealRecordPo1.setOrder_no(servOrderNo);
					procInstTaskDealRecordPo1.setOrder_type("101");
					procInstTaskDealRecordPoTemp1 = procInstTaskDealRecordServ.queryProcInstTaskDealRecordByOrderNo(procInstTaskDealRecordPo1);
					if(procInstTaskDealRecordPoTemp1!=null && procInstTaskDealRecordPoTemp1.size()>0){
						procInstTaskDealRecordPoTempAll.addAll(procInstTaskDealRecordPoTemp1);
					}
					
					//info_order_cancel
					infoOrderCancelPo1.setOrder_no(servOrderNo);
					infoOrderCancelPo1.setOrder_type("101");
					infoOrderCancelPoTemp1 = infoOrderCancelServ.queryInfoOrderCancelList(infoOrderCancelPo1);
					if(infoOrderCancelPoTemp1!=null && infoOrderCancelPoTemp1.size()>0){
						infoOrderCancelPoTempAll.addAll(infoOrderCancelPoTemp1);
					}
				}

				if(procInstTaskInstPoTempAll != null && procInstTaskInstPoTempAll.size() > 0){
					dataMap.put("task_inst_serv", procInstTaskInstPoTempAll);
				}

				if(procInstTaskDealRecordPoTempAll != null && procInstTaskDealRecordPoTempAll.size() > 0){
					dataMap.put("task_deal_record_serv", procInstTaskDealRecordPoTempAll);
				}
				
				if(infoOrderCancelPoTempAll != null && infoOrderCancelPoTempAll.size() > 0){
					dataMap.put("info_order_cancel_serv", infoOrderCancelPoTempAll);
				}
			}
			
			/*
			//task_assign_depart
			ProcInstTaskAssignDepartPo procInstTaskAssignDepartPo=new ProcInstTaskAssignDepartPo();
			procInstTaskAssignDepartPo.setOrder_no(order_no);
			ProcInstTaskAssignDepartPo procInstTaskAssignDepartPoTemp=procInstTaskAssignDepartServ.queryProcInstTaskAssignDepartByPo(procInstTaskAssignDepartPo);
			if(procInstTaskAssignDepartPoTemp!=null){
				dataMap.put("task_assign_depart", procInstTaskAssignDepartPoTemp);
			}
			//task_assign_oper
			ProcInstTaskAssignOperPo procInstTaskAssignOperPo=new ProcInstTaskAssignOperPo();
			procInstTaskAssignOperPo.setOrder_no(order_no);
			ProcInstTaskAssignOperPo procInstTaskAssignOperPoTemp=procInstTaskAssignOperServ.queryProcInstTaskAssignOperByPo(procInstTaskAssignOperPo);
			if(procInstTaskAssignOperPoTemp!=null){
				dataMap.put("task_assign_oper", procInstTaskAssignOperPoTemp);
			}
			//task_assign_record
			ProcInstTaskAssignRecordPo procInstTaskAssignRecordPo=new ProcInstTaskAssignRecordPo();
			procInstTaskAssignRecordPo.setOrder_no(order_no);
			List<ProcInstTaskAssignRecordPo> procInstTaskAssignRecordPoTemp=procInstTaskAssignRecordServ.queryProcInstTaskAssignRecordByOrderNo(procInstTaskAssignRecordPo);
			if(procInstTaskAssignRecordPoTemp!=null){
				dataMap.put("task_assign_record", procInstTaskAssignRecordPoTemp);
			}
			*/
			
			// 交付订单表
			InfoDeliverOrderPo infoDeliverOrderPo = new InfoDeliverOrderPo();
			infoDeliverOrderPo.setSale_order_no(order_no);
			List<InfoDeliverOrderPo> infoDeliverOrderPoTemp=infoDeliverOrderServ.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderPo);

			if(infoDeliverOrderPoTemp!=null && infoDeliverOrderPoTemp.size()>0){
				dataMap.put("info_deliver_order", infoDeliverOrderPoTemp);
			}
			
			//支付订单表
			InfoPayOrderPo infoPayOrderPo = new InfoPayOrderPo();
			infoPayOrderPo.setRela_order_no(order_no);
			infoPayOrderPo.setRela_order_type("100");
			InfoPayOrderPo infoPayOrderPoTemp=infoPayOrderServ.queryInfoPayOrderByRelaOrderNo(infoPayOrderPo);

			if(infoPayOrderPoTemp!=null){
				dataMap.put("info_pay_order", infoPayOrderPoTemp);
			}

			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("销售订单备份数据查询成功");
			message.addArg("backData", dataMap);

		}else{
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("无该操作类型");

		}
		return message;

	}


	@SuppressWarnings("unchecked")
	@Override
	public UocMessage insertRawData(Map<String, Object> dataMap,String oper_type)  throws Exception{
		UocMessage message=new UocMessage();
		if(dataMap==null){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("无备份数据");
			return message;
		}
		if("100".equals(oper_type)){
			//备份销售订单表
			InfoSaleOrderPo infoSaleOrderPo=(InfoSaleOrderPo) dataMap.get("info_sale_order");
			if(infoSaleOrderPo!=null){
				InfoSaleOrderHisPo infoSaleOrderHisPo = new InfoSaleOrderHisPo();
				BeanUtils.copyProperties(infoSaleOrderPo,infoSaleOrderHisPo);
				boolean infoSaleOrderFlag=infoSaleOrderHisServ.createInfoSaleOrderHisPo(infoSaleOrderHisPo);
				if(!infoSaleOrderFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("销售订单表备份错误");
					return message;
				}
			}

			//销售订单费用信息表
			InfoSaleOrderFeePo infoSaleOrderFeePoTemp=(InfoSaleOrderFeePo) dataMap.get("info_sale_fee");
			if(infoSaleOrderFeePoTemp!=null){
				InfoSaleOrderFeeHisPo infoSaleOrderFeeHisPo =new InfoSaleOrderFeeHisPo();
				BeanUtils.copyProperties(infoSaleOrderFeePoTemp,infoSaleOrderFeeHisPo);
				boolean infoSaleOrderFeeFlag=infoSaleOrderFeeHisServ.createInfoSaleOrderFeeHisPo(infoSaleOrderFeeHisPo);
				if(!infoSaleOrderFeeFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("销售订单费用信息表备份错误");
					return message;
				}
			}

			//销售订单修订表
			List<InfoSaleOrderEditPo> infoSaleOrderEditPoTemps=(List<InfoSaleOrderEditPo>) dataMap.get("info_sale_edit");
			if(infoSaleOrderEditPoTemps!=null && infoSaleOrderEditPoTemps.size()>0){
				for(InfoSaleOrderEditPo infoSaleOrderEditPoTemp:infoSaleOrderEditPoTemps){
					InfoSaleOrderEditHisPo infoSaleOrderEditHisPo = new InfoSaleOrderEditHisPo();
					BeanUtils.copyProperties(infoSaleOrderEditPoTemp,infoSaleOrderEditHisPo);
					boolean infoSaleOrderEditHisFlag=infoSaleOrderEditHisServ.createInfoSaleOrderEditHisPo(infoSaleOrderEditHisPo);
					if(!infoSaleOrderEditHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("销售订单修订表备份错误");
						return message;
					}
				}
			}

			//销售订单属性集表
			List<InfoSaleOrderAttrPo> infoSaleOrderAttrPoTemp=(List<InfoSaleOrderAttrPo>) dataMap.get("info_sale_attr");
			if(infoSaleOrderAttrPoTemp!=null && infoSaleOrderAttrPoTemp.size()>0){
				for(InfoSaleOrderAttrPo infoSaleOrderAttrPoRes :infoSaleOrderAttrPoTemp){
					InfoSaleOrderAttrHisPo infoSaleOrderAttrHisPo =new InfoSaleOrderAttrHisPo();
					BeanUtils.copyProperties(infoSaleOrderAttrPoRes,infoSaleOrderAttrHisPo);
					boolean infoSaleOrderAttrHisFlag=infoSaleOrderAttrHisServ.createInfoSaleOrderAttrHisPo(infoSaleOrderAttrHisPo);
					if(!infoSaleOrderAttrHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("销售订单属性集表备份错误");
						return message;
					}
				}
			}

			//销售订单个客信息表
			InfoSaleOrderPersionPo infoSaleOrderPersionPoTemp=(InfoSaleOrderPersionPo) dataMap.get("info_sale_person");
			if(infoSaleOrderPersionPoTemp!=null){
				InfoSaleOrderPersionHisPo infoSaleOrderPersionHisPo =new InfoSaleOrderPersionHisPo();
				BeanUtils.copyProperties(infoSaleOrderPersionPoTemp,infoSaleOrderPersionHisPo);
				boolean  infoSaleOrderPersionHisFlag = infoSaleOrderPersionHisServ.createInfoSaleOrderPersionHisPo(infoSaleOrderPersionHisPo);
				if(!infoSaleOrderPersionHisFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("销售订单个客信息表备份错误");
					return message;
				}
			}

			//新表info_sale_order_persion_cert
			InfoSaleOrderPersionCertPo infoSaleOrderPersionCertPoTemp =(InfoSaleOrderPersionCertPo) dataMap.get("info_sale_person_cert");
			if(infoSaleOrderPersionCertPoTemp!=null){
				InfoSaleOrderPersionCertHisPo infoSaleOrderPersionCertHisPo =new InfoSaleOrderPersionCertHisPo();
				BeanUtils.copyProperties(infoSaleOrderPersionCertPoTemp,infoSaleOrderPersionCertHisPo);
				boolean flag=infoSaleOrderPersionCertHisServ.createInfoSaleOrderPersionCertHis(infoSaleOrderPersionCertHisPo);
				if(!flag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("销售订单个客证件信息备份错误");
					return message;
			 }
			}

			//销售订单收件人信息表
			InfoSaleOrderDistributionPo infoSaleOrderDistributionPoTemp=(InfoSaleOrderDistributionPo) dataMap.get("info_sale_distr");
			if(infoSaleOrderDistributionPoTemp!=null){
				InfoSaleOrderDistributionHisPo infoSaleOrderDistributionHisPo =new InfoSaleOrderDistributionHisPo();
				BeanUtils.copyProperties(infoSaleOrderDistributionPoTemp,infoSaleOrderDistributionHisPo);
				boolean infoSaleOrderDistributionHisFlag=infoSaleOrderDistributionHisServ.createInfoSaleOrderDistributionHisPo(infoSaleOrderDistributionHisPo);
				if(!infoSaleOrderDistributionHisFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("销售订单收件人信息表表备份错误");
					return message;
				}
			}

			//销售订单包裹信息表
			List<InfoSaleOrderDistrDetailPo> infoSaleOrderDistrDetailPoList=(List<InfoSaleOrderDistrDetailPo>) dataMap.get("info_sale_distr_detail");
			if(infoSaleOrderDistrDetailPoList!=null && infoSaleOrderDistrDetailPoList.size()>0){
				for(InfoSaleOrderDistrDetailPo infoSaleOrderDistrDetailPoRes :infoSaleOrderDistrDetailPoList){
					InfoSaleOrderDistrDetailHisPo infoSaleOrderDistrDetailHisPo =new InfoSaleOrderDistrDetailHisPo();
					BeanUtils.copyProperties(infoSaleOrderDistrDetailPoRes,infoSaleOrderDistrDetailHisPo);
					boolean infoSaleOrderDistrDetailHisFlag=infoSaleOrderDistrDetailHisServ.createInfoSaleOrderDistrDetailHisPo(infoSaleOrderDistrDetailHisPo);
					if(!infoSaleOrderDistrDetailHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("销售订单包裹信息表备份失败");
						return message;
					}
				}
			}

			//销售订单商品表
			List<InfoSaleOrderOfrMapPo> infoSaleOrderOfrMapPoTemp=(List<InfoSaleOrderOfrMapPo>) dataMap.get("info_sale_ofr_map");
			if(infoSaleOrderOfrMapPoTemp!=null && infoSaleOrderOfrMapPoTemp.size()>0){
				for(InfoSaleOrderOfrMapPo infoSaleOrderOfrMapPoRes :infoSaleOrderOfrMapPoTemp){
					InfoSaleOrderOfrMapHisPo infoSaleOrderOfrMapHisPo =new InfoSaleOrderOfrMapHisPo();
					BeanUtils.copyProperties(infoSaleOrderOfrMapPoRes,infoSaleOrderOfrMapHisPo);
					boolean infoSaleOrderOfrMapHisFlag=infoSaleOrderOfrMapHisServ.createInfoSaleOrderOfrMapHisPo(infoSaleOrderOfrMapHisPo);
					if(!infoSaleOrderOfrMapHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("销售订单商品表备份失败");
						return message;
					}
				}
			}

			//销售订单业务表
			List<InfoSaleOrderServMapPo> infoSaleOrderServMapPoTemp =(List<InfoSaleOrderServMapPo>) dataMap.get("info_sale_serv_map");
			if(infoSaleOrderServMapPoTemp!=null && infoSaleOrderServMapPoTemp.size()>0){
				for(InfoSaleOrderServMapPo infoSaleOrderServMapPoRes :infoSaleOrderServMapPoTemp){
					InfoSaleOrderServMapHisPo infoSaleOrderServMapHisPo =new InfoSaleOrderServMapHisPo();
					BeanUtils.copyProperties(infoSaleOrderServMapPoRes,infoSaleOrderServMapHisPo);
					boolean infoSaleOrderServMapHisFlag=infoSaleOrderServMapHisServ.createInfoSaleOrderServMapHisPo(infoSaleOrderServMapHisPo);
					if(!infoSaleOrderServMapHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("销售订单业务表备份失败");
						return message;
					}
				}
			}

			//销售订单拓展属性横表
			InfoSaleOrderExtPo infoSaleOrderExtPoTemp=(InfoSaleOrderExtPo) dataMap.get("info_sale_ext");
			if(infoSaleOrderExtPoTemp!=null){
				InfoSaleOrderExtHisPo infoSaleOrderExtHisPo = new InfoSaleOrderExtHisPo();
				BeanUtils.copyProperties(infoSaleOrderExtPoTemp,infoSaleOrderExtHisPo);
				boolean infoSaleOrderExtHisFlag=infoSaleOrderExtHisServ.create(infoSaleOrderExtHisPo);
				if(!infoSaleOrderExtHisFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("销售订单拓展属性横表备份错误");
					return message;
				}
			}

			//商品订单表
			List<InfoOfrOrderPo> infoOfrOrderPoTemp=(List<InfoOfrOrderPo>) dataMap.get("info_ofr_order");
			if(infoOfrOrderPoTemp!=null && infoOfrOrderPoTemp.size()>0){
				for(InfoOfrOrderPo infoOfrOrderPoRes :infoOfrOrderPoTemp){
					InfoOfrOrderHisPo infoOfrOrderHisPo =new InfoOfrOrderHisPo();
					BeanUtils.copyProperties(infoOfrOrderPoRes,infoOfrOrderHisPo);
					boolean infoOfrOrderHisFlag=infoOfrOrderHisServ.createInfoOfrOrderHisPo(infoOfrOrderHisPo);
					if(!infoOfrOrderHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("商品订单表备份失败");
						return message;
					}
				}
			}

			//商品订单一次性费用发票表
			List<InfoOfrOrderInvoicePo> infoOfrOrderInvoicePoTemp = (List<InfoOfrOrderInvoicePo>) dataMap.get("info_ofr_invoice");
			if(infoOfrOrderInvoicePoTemp!=null && infoOfrOrderInvoicePoTemp.size()>0){
				for(InfoOfrOrderInvoicePo infoOfrOrderInvoicePoRes : infoOfrOrderInvoicePoTemp){
					InfoOfrOrderInvoiceHisPo infoOfrOrderInvoiceHisPo =new InfoOfrOrderInvoiceHisPo();
					BeanUtils.copyProperties(infoOfrOrderInvoicePoRes,infoOfrOrderInvoiceHisPo);
					boolean infoOfrOrderInvoiceHisFlag=infoOfrOrderInvoiceHisServ.createInfoOfrOrderInvoiceHisPo(infoOfrOrderInvoiceHisPo);
					if(!infoOfrOrderInvoiceHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("商品订单一次性费用发票表备份失败");
						return message;
					}
				}
			}

			//商品订单收费详情表
			List<InfoOfrOrderPayPo> infoOfrOrderPayPoTemp = (List<InfoOfrOrderPayPo>) dataMap.get("info_ofr_pay");
			if(infoOfrOrderPayPoTemp!=null && infoOfrOrderPayPoTemp.size()>0){
				for(InfoOfrOrderPayPo infoOfrOrderPayPoRes : infoOfrOrderPayPoTemp){
					InfoOfrOrderPayHisPo infoOfrOrderPayHisPo =new InfoOfrOrderPayHisPo();
					BeanUtils.copyProperties(infoOfrOrderPayPoRes,infoOfrOrderPayHisPo);
					boolean infoOfrOrderPayHisPoFlag=infoOfrOrderPayHisServ.createInfoOfrOrderPayHisPo(infoOfrOrderPayHisPo);
					if(!infoOfrOrderPayHisPoFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("商品订单收费详情表备份失败");
						return message;
					}
				}
			}

			//商品订单费用详情表
			List<InfoOfrOrderFeePo> infoOfrOrderFeePoTemp = (List<InfoOfrOrderFeePo>) dataMap.get("info_ofr_fee");
			if(infoOfrOrderFeePoTemp!=null && infoOfrOrderFeePoTemp.size()>0){
				for(InfoOfrOrderFeePo infoOfrOrderFeePoRes : infoOfrOrderFeePoTemp){
					InfoOfrOrderFeeHisPo infoOfrOrderFeeHisPo =new InfoOfrOrderFeeHisPo();
					BeanUtils.copyProperties(infoOfrOrderFeePoRes,infoOfrOrderFeeHisPo);
					boolean  infoOfrOrderFeeHisFlag=infoOfrOrderFeeHisServ.createInfoOfrOrderFeeHisPo(infoOfrOrderFeeHisPo);
					if(!infoOfrOrderFeeHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("商品订单费用详情表备份失败");
						return message;
					}
				}
			}

			//服务订单表
			List<InfoServiceOrderPo> infoServiceOrderPoTemp=(List<InfoServiceOrderPo>) dataMap.get("info_service_order");
			if(infoServiceOrderPoTemp!=null && infoServiceOrderPoTemp.size()>0){
				for(InfoServiceOrderPo infoServiceOrderPoRes : infoServiceOrderPoTemp){
					InfoServiceOrderHisPo infoServiceOrderHisPo = new InfoServiceOrderHisPo();
					BeanUtils.copyProperties(infoServiceOrderPoRes,infoServiceOrderHisPo);
					boolean infoServiceOrderHisFlag=infoServiceOrderHisServ.createInfoServiceOrderHisPo(infoServiceOrderHisPo);
					if(!infoServiceOrderHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单表备份失败");
						return message;
					}
				}
			}

			//服务订单收费详情表
			List<InfoServiceOrderPayPo> infoServiceOrderPayPoTemp=(List<InfoServiceOrderPayPo>) dataMap.get("info_serv_pay");
			if(infoServiceOrderPayPoTemp!=null && infoServiceOrderPayPoTemp.size()>0){
				for(InfoServiceOrderPayPo infoServiceOrderPayPoRes : infoServiceOrderPayPoTemp){
					InfoServiceOrderPayHisPo infoServiceOrderPayHisPo =new InfoServiceOrderPayHisPo();
					BeanUtils.copyProperties(infoServiceOrderPayPoRes,infoServiceOrderPayHisPo);
					boolean infoServiceOrderPayHisFlag=infoServiceOrderPayHisServ.createInfoServiceOrderPayHisPo(infoServiceOrderPayHisPo);
					if(!infoServiceOrderPayHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单收费详情表备份失败");
						return message;
					}
				}
			}

			//服务订单费用详情表
			List<InfoServiceOrderFeePo> infoServiceOrderFeePoTemp=(List<InfoServiceOrderFeePo>) dataMap.get("info_serv_fee");
			if(infoServiceOrderFeePoTemp!=null && infoServiceOrderFeePoTemp.size()>0){
				for(InfoServiceOrderFeePo infoServiceOrderFeePoRes : infoServiceOrderFeePoTemp){
					InfoServiceOrderFeeHisPo infoServiceOrderFeeHisPo =new InfoServiceOrderFeeHisPo();
					BeanUtils.copyProperties(infoServiceOrderFeePoRes,infoServiceOrderFeeHisPo);
					boolean infoServiceOrderFeeHisFlag =infoServiceOrderFeeHisServ.createInfoServiceOrderFeeHisPo(infoServiceOrderFeeHisPo);
					if(!infoServiceOrderFeeHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单费用详情表备份失败");
						return message;
					}
				}
			}

			//服务订单属性表
			List<InfoServiceOrderAttrPo> infoServiceOrderAttrPoTemp=(List<InfoServiceOrderAttrPo>) dataMap.get("info_serv_attr");
			if(infoServiceOrderAttrPoTemp!=null && infoServiceOrderAttrPoTemp.size()>0){
				for(InfoServiceOrderAttrPo infoServiceOrderAttrPoRes : infoServiceOrderAttrPoTemp){
					InfoServiceOrderAttrHisPo infoServiceOrderAttrHisPo =new InfoServiceOrderAttrHisPo();
					BeanUtils.copyProperties(infoServiceOrderAttrPoRes,infoServiceOrderAttrHisPo);
					boolean infoServiceOrderAttrHisFlag=infoServiceOrderAttrHisServ.createInfoSaleOrderAttrHisPo(infoServiceOrderAttrHisPo);
					if(!infoServiceOrderAttrHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单属性表备份失败");
						return message;
					}
				}
			}

			//服务订单产品列表
			List<InfoServiceOrderProdMapPo> infoServiceOrderProdMapPoTemp=(List<InfoServiceOrderProdMapPo>) dataMap.get("info_serv_prod_map");
			if(infoServiceOrderProdMapPoTemp!=null && infoServiceOrderProdMapPoTemp.size()>0){
				for(InfoServiceOrderProdMapPo infoServiceOrderProdMapPoRes : infoServiceOrderProdMapPoTemp){
					InfoServiceOrderProdMapHisPo infoServiceOrderProdMapHisPo = new InfoServiceOrderProdMapHisPo();
					BeanUtils.copyProperties(infoServiceOrderProdMapPoRes,infoServiceOrderProdMapHisPo);
					boolean infoServiceOrderProdMapHisFlag= infoServiceOrderProdMapHisServ.createInfoServiceOrderProdMapHisPo(infoServiceOrderProdMapHisPo);
					if(!infoServiceOrderProdMapHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单产品列表备份失败");
						return message;
					}

				}
			}

			//服务订单终端表
			List<InfoServiceOrderTerminalPo> infoServiceOrderTerminalPoTemp=(List<InfoServiceOrderTerminalPo>) dataMap.get("info_serv_terminal");
			if(infoServiceOrderTerminalPoTemp!=null && infoServiceOrderTerminalPoTemp.size()>0){
				for(InfoServiceOrderTerminalPo infoServiceOrderTerminalPoRes :infoServiceOrderTerminalPoTemp){
					InfoServiceOrderTerminalHisPo infoServiceOrderTerminalHisPo =new InfoServiceOrderTerminalHisPo();
					BeanUtils.copyProperties(infoServiceOrderTerminalPoRes,infoServiceOrderTerminalHisPo);
					boolean infoServiceOrderTerminalHisFlag=infoServiceOrderTerminalHisServ.createInfoServiceOrderTerminalHisPo(infoServiceOrderTerminalHisPo);
					if(!infoServiceOrderTerminalHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单终端表备份失败");
						return message;
					}
				}
			}

			//服务订单优惠活动
			List<InfoServiceOrderActivityPo> infoServiceOrderActivityPoTemp=(List<InfoServiceOrderActivityPo>) dataMap.get("info_serv_activity");
			if(infoServiceOrderActivityPoTemp!=null && infoServiceOrderActivityPoTemp.size()>0){
				for(InfoServiceOrderActivityPo infoServiceOrderActivityPoRes : infoServiceOrderActivityPoTemp){
					InfoServiceOrderActivityHisPo infoServiceOrderActivityHisPo =new InfoServiceOrderActivityHisPo();
					BeanUtils.copyProperties(infoServiceOrderActivityPoRes,infoServiceOrderActivityHisPo);
					boolean infoServiceOrderActivityHisFlag=infoServiceOrderActivityHisServ.createInfoServiceOrderActivityHisPo(infoServiceOrderActivityHisPo);
					if(!infoServiceOrderActivityHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单优惠活动备份失败");
						return message;
					}
				}
			}

			//服务订单协议表
			List<InfoServiceOrderAgreementPo> infoServiceOrderAgreementPoTemp= (List<InfoServiceOrderAgreementPo>) dataMap.get("info_serv_agreement");
			if(infoServiceOrderAgreementPoTemp!=null && infoServiceOrderAgreementPoTemp.size()>0){
				for(InfoServiceOrderAgreementPo infoServiceOrderAgreementPoRes : infoServiceOrderAgreementPoTemp){
					InfoServiceOrderAgreementHisPo infoServiceOrderAgreementHisPo =new InfoServiceOrderAgreementHisPo();
					BeanUtils.copyProperties(infoServiceOrderAgreementPoRes,infoServiceOrderAgreementHisPo);
					boolean infoServiceOrderAgreementHisFlag=infoServiceOrderAgreementHisServ.createInfoServiceOrderAgreementHisPo(infoServiceOrderAgreementHisPo);
					if(!infoServiceOrderAgreementHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单协议表备份失败");
						return message;
					}
				}
			}

			//服务订单套餐列表
			List<InfoServiceOrderPackagePo> infoServiceOrderPackagePoTemp=(List<InfoServiceOrderPackagePo>) dataMap.get("info_serv_pkg");
			if(infoServiceOrderPackagePoTemp!=null && infoServiceOrderPackagePoTemp.size()>0){
				for(InfoServiceOrderPackagePo infoServiceOrderPackagePoRes : infoServiceOrderPackagePoTemp){
					InfoServiceOrderPackageHisPo infoServiceOrderPackageHisPo =new InfoServiceOrderPackageHisPo();
					BeanUtils.copyProperties(infoServiceOrderPackagePoRes,infoServiceOrderPackageHisPo);
					boolean infoServiceOrderPackageHisFlag=infoServiceOrderPackageHisServ.createInfoServiceOrderPackageHisPo(infoServiceOrderPackageHisPo);
					if(!infoServiceOrderPackageHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单套餐列表备份失败");
						return message;
					}
				}
			}

			//服务订单SIM卡信息表
			List<InfoServiceOrderSimCardPo> infoServiceOrderSimCardPoTemp =(List<InfoServiceOrderSimCardPo>) dataMap.get("info_serv_sim_card");
			if(infoServiceOrderSimCardPoTemp!=null && infoServiceOrderSimCardPoTemp.size()>0){
				for(InfoServiceOrderSimCardPo infoServiceOrderSimCardPoRes : infoServiceOrderSimCardPoTemp){
					InfoServiceOrderSimCardHisPo infoServiceOrderSimCardHisPo =new InfoServiceOrderSimCardHisPo();
					BeanUtils.copyProperties(infoServiceOrderSimCardPoRes,infoServiceOrderSimCardHisPo);
					boolean infoServiceOrderSimCardHisPoFlag=infoServiceOrderSimCardHisServ.createInfoServiceOrderSimCardHisPo(infoServiceOrderSimCardHisPo);
					if(!infoServiceOrderSimCardHisPoFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单SIM卡信息表备份失败");
						return message;
					}
				}
			}

			//服务订单固网信息表
			List<InfoServiceOrderFixPo> infoServiceOrderFixPoTemp=(List<InfoServiceOrderFixPo>) dataMap.get("info_serv_fix");
			if(infoServiceOrderFixPoTemp!=null && infoServiceOrderFixPoTemp.size()>0){
				for(InfoServiceOrderFixPo infoServiceOrderFixPoRes : infoServiceOrderFixPoTemp){
					InfoServiceOrderFixHisPo infoServiceOrderFixHisPo = new InfoServiceOrderFixHisPo();
					BeanUtils.copyProperties(infoServiceOrderFixPoRes,infoServiceOrderFixHisPo);
					boolean infoServiceOrderFixHisFlag=infoServiceOrderFixHisServ.createInfoServiceOrderFixHisPo(infoServiceOrderFixHisPo);
					if(!infoServiceOrderFixHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单固网信息表备份失败");
						return message;
					}
				}
			}

			//服务订单宽带信息表
			List<InfoServiceOrderM165Po> infoServiceOrderM165PoTemp=(List<InfoServiceOrderM165Po>) dataMap.get("info_serv_m165");
			if(infoServiceOrderM165PoTemp!=null && infoServiceOrderM165PoTemp.size()>0){
				for(InfoServiceOrderM165Po infoServiceOrderM165PoRes : infoServiceOrderM165PoTemp){
					InfoServiceOrderM165HisPo infoServiceOrderM165HisPo =new InfoServiceOrderM165HisPo();
					BeanUtils.copyProperties(infoServiceOrderM165PoRes,infoServiceOrderM165HisPo);
					boolean infoServiceOrderM165HisFlag=infoServiceOrderM165HisServ.createInfoServiceOrderM165HisPo(infoServiceOrderM165HisPo);
					if(!infoServiceOrderM165HisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单宽带信息表备份失败");
						return message;
					}
				}
			}

			//服务订单靓号信息表
			List<InfoServiceOrderGoodNbrPo> infoServiceOrderGoodNbrPoTemp=(List<InfoServiceOrderGoodNbrPo>) dataMap.get("info_serv_good_nbr");
			if(infoServiceOrderGoodNbrPoTemp!=null && infoServiceOrderGoodNbrPoTemp.size()>0){
				for(InfoServiceOrderGoodNbrPo infoServiceOrderGoodNbrPoRes : infoServiceOrderGoodNbrPoTemp){
					InfoServiceOrderGoodNbrHisPo infoServiceOrderGoodNbrHisPo =new InfoServiceOrderGoodNbrHisPo();
					BeanUtils.copyProperties(infoServiceOrderGoodNbrPoRes,infoServiceOrderGoodNbrHisPo);
					boolean infoServiceOrderGoodNbrHisFlag=infoServiceOrderGoodNbrHisServ.createInfoServiceOrderGoodNbrHisPo(infoServiceOrderGoodNbrHisPo);
					if(!infoServiceOrderGoodNbrHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单靓号信息表备份失败");
						return message;
					}
				}
			}

			//服务订单银行托收表
			List<InfoServiceOrderCollectionPo> infoServiceOrderCollectionPoTemp=(List<InfoServiceOrderCollectionPo>) dataMap.get("info_serv_collection");
			if(infoServiceOrderCollectionPoTemp!=null && infoServiceOrderCollectionPoTemp.size()>0){
				for(InfoServiceOrderCollectionPo infoServiceOrderCollectionPoRes : infoServiceOrderCollectionPoTemp){
					InfoServiceOrderCollectionHisPo infoServiceOrderCollectionHisPo = new InfoServiceOrderCollectionHisPo();
					BeanUtils.copyProperties(infoServiceOrderCollectionPoRes,infoServiceOrderCollectionHisPo);
					boolean infoServiceOrderCollectionHisFlag=infoServiceOrderCollectionHisServ.createInfoSaleOrderAttrHisPo(infoServiceOrderCollectionHisPo);
					if(!infoServiceOrderCollectionHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单银行托收表备份失败");
						return message;
					}
				}
			}


			//服务订单担保人表
			List<InfoServiceOrderGuarantorPo> infoServiceOrderGuarantorPoTemp=(List<InfoServiceOrderGuarantorPo>) dataMap.get("info_serv_guarantor");
			if(infoServiceOrderGuarantorPoTemp!=null && infoServiceOrderGuarantorPoTemp.size()>0){
				for(InfoServiceOrderGuarantorPo infoServiceOrderGuarantorPoRes : infoServiceOrderGuarantorPoTemp){
					InfoServiceOrderGuarantorHisPo infoServiceOrderGuarantorHisPo = new InfoServiceOrderGuarantorHisPo();
					BeanUtils.copyProperties(infoServiceOrderGuarantorPoRes,infoServiceOrderGuarantorHisPo);
					boolean infoServiceOrderGuarantorHisFlag=infoServiceOrderGuarantorHisServ.createInfoServiceOrderGuarantorHisPo(infoServiceOrderGuarantorHisPo);
					if(!infoServiceOrderGuarantorHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单担保人表备份失败");
						return message;
					}
				}
			}

			//服务订单发展人表
			List<InfoServiceOrderDeveloperPo> infoServiceOrderDeveloperPoTemp=(List<InfoServiceOrderDeveloperPo>) dataMap.get("info_serv_developer");
			if(infoServiceOrderDeveloperPoTemp!=null && infoServiceOrderDeveloperPoTemp.size()>0){
				for(InfoServiceOrderDeveloperPo infoServiceOrderDeveloperPoRes : infoServiceOrderDeveloperPoTemp){
					InfoServiceOrderDeveloperHisPo infoServiceOrderDeveloperHisPo = new InfoServiceOrderDeveloperHisPo();
					BeanUtils.copyProperties(infoServiceOrderDeveloperPoRes,infoServiceOrderDeveloperHisPo);
					boolean infoServiceOrderDeveloperHisFlag=infoServiceOrderDeveloperHisServ.createInfoServiceOrderDeveloperHisPo(infoServiceOrderDeveloperHisPo);
					if(!infoServiceOrderDeveloperHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单发展人表备份失败");
						return message;
					}
				}
			}

			//服务订单个客信息表
			List<InfoServiceOrderPersionPo> infoServiceOrderPersionPoTemp=(List<InfoServiceOrderPersionPo>) dataMap.get("info_serv_person");
			if(infoServiceOrderPersionPoTemp!=null && infoServiceOrderPersionPoTemp.size()>0){
				for(InfoServiceOrderPersionPo infoServiceOrderPersionPoRes : infoServiceOrderPersionPoTemp){
					InfoServiceOrderPersionHisPo infoServiceOrderPersionHisPo = new InfoServiceOrderPersionHisPo();
					BeanUtils.copyProperties(infoServiceOrderPersionPoRes,infoServiceOrderPersionHisPo);
					boolean infoServiceOrderPersionHisFlag=infoServiceOrderPersionHisServ.createInfoSaleOrderAttrHisPo(infoServiceOrderPersionHisPo);
					if(!infoServiceOrderPersionHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单个客信息表备份失败");
						return message;
					}
				}
			}

			//服务订单拓展属性横表
			List<InfoServiceOrderExtPo> infoServiceOrderExtPoTemp=(List<InfoServiceOrderExtPo>) dataMap.get("info_serv_ext");
			if(infoServiceOrderExtPoTemp!=null && infoServiceOrderExtPoTemp.size()>0){
				for(InfoServiceOrderExtPo infoServiceOrderExtPoRes : infoServiceOrderExtPoTemp){
					InfoServiceOrderExtHisPo infoServiceOrderExtHisPo = new InfoServiceOrderExtHisPo();
					BeanUtils.copyProperties(infoServiceOrderExtPoRes,infoServiceOrderExtHisPo);
					boolean infoServiceOrderExtHisFlag=infoServiceOrderExtHisServ.create(infoServiceOrderExtHisPo);
					if(!infoServiceOrderExtHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("服务订单拓展属性横表备份失败");
						return message;
					}
				}
			}

			//人工任务实例表
			List<ProcInstTaskInstPo> procInstTaskInstPoTemp=(List<ProcInstTaskInstPo>) dataMap.get("task_inst_sale");
			if(procInstTaskInstPoTemp!=null && procInstTaskInstPoTemp.size()>0){
				for(ProcInstTaskInstPo procInstTaskInstPo : procInstTaskInstPoTemp){
					ProcInstTaskInstHisPo procInstTaskInstHisPo = new ProcInstTaskInstHisPo();
					BeanUtils.copyProperties(procInstTaskInstPo,procInstTaskInstHisPo);
					boolean procInstTaskInstHisFlag=procInstTaskInstHisServ.createProcInstTaskInstHisPo(procInstTaskInstHisPo);
					if(!procInstTaskInstHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("人工任务实例表(销售订单）备份失败");
						return message;
					}
				}
			}
			
			
			//人工任务实例表
			List<ProcInstTaskInstPo> procInstTaskInstPoTemp1=(List<ProcInstTaskInstPo>) dataMap.get("task_inst_serv");
			if(procInstTaskInstPoTemp1!=null && procInstTaskInstPoTemp1.size()>0){
				for(ProcInstTaskInstPo procInstTaskInstPo : procInstTaskInstPoTemp1){
					ProcInstTaskInstHisPo procInstTaskInstHisPo = new ProcInstTaskInstHisPo();
					BeanUtils.copyProperties(procInstTaskInstPo,procInstTaskInstHisPo);
					boolean procInstTaskInstHisFlag=procInstTaskInstHisServ.createProcInstTaskInstHisPo(procInstTaskInstHisPo);
					if(!procInstTaskInstHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("人工任务实例表（服务订单）备份失败");
						return message;
					}
				}
			}
			
			//撤单表
			List<InfoOrderCancelPo> infoOrderCancelPoTemp=(List<InfoOrderCancelPo>) dataMap.get("info_order_cancel_sale");
			if(infoOrderCancelPoTemp!=null && infoOrderCancelPoTemp.size()>0){
				for(InfoOrderCancelPo infoOrderCancelPo : infoOrderCancelPoTemp){
					InfoOrderCancelHisPo infoOrderCancelHisPo = new InfoOrderCancelHisPo();
					BeanUtils.copyProperties(infoOrderCancelPo,infoOrderCancelHisPo);
					boolean infoOrderCancelHisFlag=infoOrderCancelHisServ.create(infoOrderCancelHisPo);
					if(!infoOrderCancelHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("撤单表(销售订单)备份失败");
						return message;
					}
				}
			}
			
			
			//撤单表
			List<InfoOrderCancelPo> infoOrderCancelPoTemp1=(List<InfoOrderCancelPo>) dataMap.get("info_order_cancel_serv");
			if(infoOrderCancelPoTemp1!=null && infoOrderCancelPoTemp1.size()>0){
				for(InfoOrderCancelPo infoOrderCancelPo : infoOrderCancelPoTemp1){
					InfoOrderCancelHisPo infoOrderCancelHisPo = new InfoOrderCancelHisPo();
					BeanUtils.copyProperties(infoOrderCancelPo,infoOrderCancelHisPo);
					boolean infoOrderCancelPoHisFlag=infoOrderCancelHisServ.create(infoOrderCancelHisPo);
					if(!infoOrderCancelPoHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("撤单表(服务订单)备份失败");
						return message;
					}
				}
			}
			

			//人工任务操作记录
			List<ProcInstTaskDealRecordPo> procInstTaskDealRecordPoTemp=(List<ProcInstTaskDealRecordPo>) dataMap.get("task_deal_record_sale");
			if(procInstTaskDealRecordPoTemp!=null && procInstTaskDealRecordPoTemp.size()>0){
				for(ProcInstTaskDealRecordPo procInstTaskDealRecordPo : procInstTaskDealRecordPoTemp){
					ProcInstTaskDealRecordHisPo procInstTaskDealRecordHisPo = new ProcInstTaskDealRecordHisPo();
					BeanUtils.copyProperties(procInstTaskDealRecordPo,procInstTaskDealRecordHisPo);
					boolean procInstTaskDealRecordFlag=procInstTaskDealRecordHisServ.createProcInstTaskDealRecordHisPo(procInstTaskDealRecordHisPo);
					if(!procInstTaskDealRecordFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("人工任务操作记录（销售订单）备份失败");
						return message;
					}
				}
			}
			
			//人工任务操作记录
			List<ProcInstTaskDealRecordPo> procInstTaskDealRecordPoTemp1=(List<ProcInstTaskDealRecordPo>) dataMap.get("task_deal_record_serv");
			if(procInstTaskDealRecordPoTemp1!=null && procInstTaskDealRecordPoTemp1.size()>0){
				for(ProcInstTaskDealRecordPo procInstTaskDealRecordPo : procInstTaskDealRecordPoTemp1){
					ProcInstTaskDealRecordHisPo procInstTaskDealRecordHisPo = new ProcInstTaskDealRecordHisPo();
					BeanUtils.copyProperties(procInstTaskDealRecordPo,procInstTaskDealRecordHisPo);
					boolean procInstTaskDealRecordFlag=procInstTaskDealRecordHisServ.createProcInstTaskDealRecordHisPo(procInstTaskDealRecordHisPo);
					if(!procInstTaskDealRecordFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("人工任务操作记录备份失败");
						return message;
					}
				}
			}

			/*
			//task_assign_oper任务工号表
			ProcInstTaskAssignOperPo procInstTaskAssignOperPo=(ProcInstTaskAssignOperPo) dataMap.get("task_assign_oper");
			if(procInstTaskAssignOperPo!=null){
				ProcInstTaskAssignOperHisPo procInstTaskAssignOperHisPo=new ProcInstTaskAssignOperHisPo();
				BeanUtils.copyProperties(procInstTaskAssignOperPo,procInstTaskAssignOperHisPo);
				boolean procInstTaskAssignOperHisFlag=procInstTaskAssignOperHisServ.createProcInstTaskAssignOperHisPo(procInstTaskAssignOperHisPo);
				if(!procInstTaskAssignOperHisFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("任务工号表备份失败");
					return message;
				}
			}
			//task_assign_record日志表
			List<ProcInstTaskAssignRecordPo> ProcInstTaskAssignRecordPoTemp=(List<ProcInstTaskAssignRecordPo>) dataMap.get("task_assign_record");
			if(ProcInstTaskAssignRecordPoTemp!=null){
				for(ProcInstTaskAssignRecordPo procInstTaskAssignRecordPo:ProcInstTaskAssignRecordPoTemp){
					ProcInstTaskAssignRecordHisPo procInstTaskAssignRecordHisPo=new ProcInstTaskAssignRecordHisPo();
					BeanUtils.copyProperties(procInstTaskAssignRecordPo,procInstTaskAssignRecordHisPo);
					boolean procInstTaskAssignRecordHisFlag=procInstTaskAssignRecordHisServ.createProcInstTaskAssignRecordHisPo(procInstTaskAssignRecordHisPo);
					if(!procInstTaskAssignRecordHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("日志表备份失败");
						return message;
					}
				}
			}
			//task_assign_depart任务部门表
			ProcInstTaskAssignDepartPo procInstTaskAssignDepartPo=(ProcInstTaskAssignDepartPo) dataMap.get("task_assign_depart");
			if(procInstTaskAssignDepartPo!=null){
				ProcInstTaskAssignDepartHisPo procInstTaskAssignDepartHisPo=new ProcInstTaskAssignDepartHisPo();
				BeanUtils.copyProperties(procInstTaskAssignDepartPo,procInstTaskAssignDepartHisPo);
				boolean procInstTaskAssignDepartHisFlag=procInstTaskAssignDepartHisServ.createProcInstTaskAssignDepartHisPo(procInstTaskAssignDepartHisPo);
				if(!procInstTaskAssignDepartHisFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("任务部门表备份失败");
					return message;
				}
			}
			*/
			
			//支付订单表
			InfoPayOrderPo infoPayOrderPoTemp=(InfoPayOrderPo) dataMap.get("info_pay_order");
			if(infoPayOrderPoTemp!=null){
				InfoPayOrderHisPo infoPayOrderHisPo = new InfoPayOrderHisPo();
				BeanUtils.copyProperties(infoPayOrderPoTemp,infoPayOrderHisPo);
				boolean infoPayOrderHisFlag=infoPayOrderHisServ.createInfoPayOrderHisPo(infoPayOrderHisPo);
				if(!infoPayOrderHisFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("支付订单表备份失败");
					return message;
				}
			}
			
			//交付订单表
			List<InfoDeliverOrderPo> InfoDeliverOrderPoTemps=(List<InfoDeliverOrderPo>) dataMap.get("info_deliver_order");
			if(InfoDeliverOrderPoTemps!=null){
				InfoDeliverOrderHisPo infoDeliverOrderHisPo = new InfoDeliverOrderHisPo();
				
				for(InfoDeliverOrderPo InfoDeliverOrderPoTemp:InfoDeliverOrderPoTemps){
					BeanUtils.copyProperties(InfoDeliverOrderPoTemp,infoDeliverOrderHisPo);
					boolean infoDeliverOrderHisFlag=infoDeliverOrderHisServ.createInfoDeliverOrderHisPo(infoDeliverOrderHisPo);
					if(!infoDeliverOrderHisFlag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("交付订单表单备份失败");
						return message;
					}
				}
			}
			
			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("销售订单备份成功");
		}else{
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("无此操作类型");
			return message;
		}

		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UocMessage deleteRawData(String order_no,String oper_type,Map<String, Object> dataMap)  throws Exception{
		UocMessage message= new UocMessage();
		if("".equals(order_no) || order_no==null){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("order_no查询参数为空");
			return message;
		}
		if("".equals(oper_type) || oper_type==null){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("oper_type查询参数为空");
			return message;
		}

		if("100".equals(oper_type)){
			//获取销售订单表数据
			InfoSaleOrderPo infoSaleOrderPo=(InfoSaleOrderPo) dataMap.get("info_sale_order");
			if(infoSaleOrderPo!=null){
				InfoSaleOrderPo po = new InfoSaleOrderPo();
				po.setSale_order_no(order_no);
				boolean infoSaleOrderFlag =infoSaleOrderServ.deleteInfoSaleOrderPo(po);
				if(!infoSaleOrderFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除销售订单表数据失败");
					return message;
				}
			}

			//获取销售订单费用信息表数据
			InfoSaleOrderFeePo infoSaleOrderFeePoTemp=(InfoSaleOrderFeePo) dataMap.get("info_sale_fee");
			if(infoSaleOrderFeePoTemp!=null){
				InfoSaleOrderFeePo infoSaleOrderFeePo =new InfoSaleOrderFeePo();
				infoSaleOrderFeePo.setSale_order_no(order_no);
				boolean infoSaleOrderFeePoFlag=infoSaleOrderFeeServ.delete(infoSaleOrderFeePo);
				if(!infoSaleOrderFeePoFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除销售订单费用信息表数据失败");
					return message;
				}
			}

			//销售订单修订表
			List<InfoSaleOrderEditPo> infoSaleOrderEditPoTemps=(List<InfoSaleOrderEditPo>) dataMap.get("info_sale_edit");
			if(infoSaleOrderEditPoTemps!=null && infoSaleOrderEditPoTemps.size()>0){
				InfoSaleOrderEditPo infoSaleOrderEditPo = new InfoSaleOrderEditPo();
				infoSaleOrderEditPo.setSale_order_no(order_no);
				boolean infoSaleOrderEditPoFlag=infoSaleOrderEditServ.delete(infoSaleOrderEditPo);
				if(!infoSaleOrderEditPoFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除销售订单修订表数据失败");
					return message;
				}
			}


			//销售订单属性集表
			List<InfoSaleOrderAttrPo> infoSaleOrderAttrPoTemp=(List<InfoSaleOrderAttrPo>) dataMap.get("info_sale_attr");
			if(infoSaleOrderAttrPoTemp!=null && infoSaleOrderAttrPoTemp.size()>0){
				InfoSaleOrderAttrPo infoSaleOrderAttrPo =new InfoSaleOrderAttrPo();
				infoSaleOrderAttrPo.setSale_order_no(order_no);
				boolean infoSaleOrderAttrPoFlag=infoSaleOrderAttrServ.deleteInfoSaleOrderAttrBySaleOrderNo(infoSaleOrderAttrPo);
				if(!infoSaleOrderAttrPoFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除销售订单属性集表数据失败");
					return message;
				}
			}

			//销售订单个客信息表
			InfoSaleOrderPersionPo infoSaleOrderPersionPoTemp=(InfoSaleOrderPersionPo) dataMap.get("info_sale_person");
			if(infoSaleOrderPersionPoTemp!=null){
				InfoSaleOrderPersionPo infoSaleOrderPersionPo =new InfoSaleOrderPersionPo();
				infoSaleOrderPersionPo.setSale_order_no(order_no);
				boolean infoSaleOrderPersionPoFlag=infoSaleOrderPersionServ.deleteInfoSaleOrderPersionBySaleOrderNo(infoSaleOrderPersionPo);
				if(!infoSaleOrderPersionPoFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除销售订单个客信息表数据失败");
					return message;
				}
			}

			//新表info_sale_order_persion_cert
			InfoSaleOrderPersionCertPo infoSaleOrderPersionCertPoTemp =(InfoSaleOrderPersionCertPo) dataMap.get("info_sale_person_cert");
			if(infoSaleOrderPersionCertPoTemp!=null){
				InfoSaleOrderPersionCertPo infoSaleOrderPersionCertPo =new InfoSaleOrderPersionCertPo();
				infoSaleOrderPersionCertPo.setSale_order_no(order_no);
				boolean flag=infoSaleOrderPersionCertServ.deleteInfoSaleOrderPersionCertBySaleOrderNo(infoSaleOrderPersionCertPo);
				if(!flag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除销售订单个客证件信息表数据失败");
					return message;
			 }
			}
			//销售订单收件人信息表
			InfoSaleOrderDistributionPo infoSaleOrderDistributionPoTemp=(InfoSaleOrderDistributionPo) dataMap.get("info_sale_distr");
			if(infoSaleOrderDistributionPoTemp!=null){
				InfoSaleOrderDistributionPo infoSaleOrderDistributionPo =new InfoSaleOrderDistributionPo();
				infoSaleOrderDistributionPo.setSale_order_no(order_no);
				boolean infoSaleOrderDistributionPoFlag=infoSaleOrderDistributionServ.deleteInfoSaleOrderDistributionBySaleOrderNo(infoSaleOrderDistributionPo);
				if(!infoSaleOrderDistributionPoFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除销售订单收件人信息表数据失败");
					return message;
				}
			}

			//销售订单包裹信息表
			List<InfoSaleOrderDistrDetailPo> infoSaleOrderDistrDetailPoList=(List<InfoSaleOrderDistrDetailPo>) dataMap.get("info_sale_distr_detail");
			if(infoSaleOrderDistrDetailPoList!=null && infoSaleOrderDistrDetailPoList.size()>0){
				InfoSaleOrderDistrDetailPo infoSaleOrderDistrDetailPo =new InfoSaleOrderDistrDetailPo();
				infoSaleOrderDistrDetailPo.setSale_order_no(order_no);
				boolean infoSaleOrderDistrDetailFlag=infoSaleOrderDistrDetailServ.deleteInfoSaleOrderDistrDetailBySaleOrderNo(infoSaleOrderDistrDetailPo);
				if(!infoSaleOrderDistrDetailFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除销售订单包裹信息表数据失败");
					return message;
				}
			}

			//销售订单商品表
			List<InfoSaleOrderOfrMapPo> infoSaleOrderOfrMapPoTempRes=(List<InfoSaleOrderOfrMapPo>) dataMap.get("info_sale_ofr_map");
			if(infoSaleOrderOfrMapPoTempRes!=null && infoSaleOrderOfrMapPoTempRes.size()>0){
				InfoSaleOrderOfrMapPo infoSaleOrderOfrMapPo =new InfoSaleOrderOfrMapPo();
				infoSaleOrderOfrMapPo.setSale_order_no(order_no);
				boolean infoSaleOrderOfrMapPoTemp=infoSaleOrderOfrMapServ.deleteInfoSaleOrderOfrMapBySaleOrderNo(infoSaleOrderOfrMapPo);
				if(!infoSaleOrderOfrMapPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除销售订单商品表数据失败");
					return message;
				}
			}

			//销售订单业务表
			List<InfoSaleOrderServMapPo> infoSaleOrderServMapPoTempRes =(List<InfoSaleOrderServMapPo>) dataMap.get("info_sale_serv_map");
			if(infoSaleOrderServMapPoTempRes!=null && infoSaleOrderServMapPoTempRes.size()>0){
				InfoSaleOrderServMapPo infoSaleOrderServMapPo =new InfoSaleOrderServMapPo();
				infoSaleOrderServMapPo.setSale_order_no(order_no);
				boolean infoSaleOrderServMapPoTemp =infoSaleOrderServMapServ.deleteInfoSaleOrderServMapBySaleOrderNo(infoSaleOrderServMapPo);
				if(!infoSaleOrderServMapPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除销售订单业务表数据失败");
					return message;
				}
			}

			//销售订单拓展属性横表
			InfoSaleOrderExtPo infoSaleOrderExtPoTemp=(InfoSaleOrderExtPo) dataMap.get("info_sale_ext");
			if(infoSaleOrderExtPoTemp!=null){
				InfoSaleOrderExtPo infoSaleOrderExtPo = new InfoSaleOrderExtPo();
				infoSaleOrderExtPo.setSale_order_no(order_no);
				boolean infoSaleOrderExtPoFlag=infoSaleOrderExtServ.deleteInfoSaleOrderExtBySaleOrderNo(infoSaleOrderExtPo);
				if(!infoSaleOrderExtPoFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除销售订单拓展属性横表失败");
					return message;
				}
			}

			//商品订单表
			List<InfoOfrOrderPo> infoOfrOrderPoTempRes=(List<InfoOfrOrderPo>) dataMap.get("info_ofr_order");
			if(infoOfrOrderPoTempRes!=null && infoOfrOrderPoTempRes.size()>0){
				InfoOfrOrderPo infoOfrOrderPo =new InfoOfrOrderPo();
				infoOfrOrderPo.setSale_order_no(order_no);
				boolean infoOfrOrderPoTemp=infoOfrOrderServ.deleteInfoOfrOrderBySaleOrderNo(infoOfrOrderPo);
				if(!infoOfrOrderPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除商品订单表数据失败");
					return message;
				}
			}

			//商品订单一次性费用发票表
			List<InfoOfrOrderInvoicePo> infoOfrOrderInvoicePoTempRes = (List<InfoOfrOrderInvoicePo>) dataMap.get("info_ofr_invoice");
			if(infoOfrOrderInvoicePoTempRes!=null && infoOfrOrderInvoicePoTempRes.size()>0){
				InfoOfrOrderInvoicePo infoOfrOrderInvoicePo =new InfoOfrOrderInvoicePo();
				infoOfrOrderInvoicePo.setSale_order_no(order_no);
				boolean infoOfrOrderInvoicePoTemp = infoOfrOrderInvoiceServ.deleteInfoOfrOrderInvoiceBySaleOrderNo(infoOfrOrderInvoicePo);
				if(!infoOfrOrderInvoicePoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除商品订单一次性费用发票表数据失败");
					return message;
				}
			}


			//商品订单收费详情表
			List<InfoOfrOrderPayPo> infoOfrOrderPayPoTempRes = (List<InfoOfrOrderPayPo>) dataMap.get("info_ofr_pay");
			if(infoOfrOrderPayPoTempRes!=null && infoOfrOrderPayPoTempRes.size()>0){
				InfoOfrOrderPayPo infoOfrOrderPayPo =new InfoOfrOrderPayPo();
				infoOfrOrderPayPo.setSale_order_no(order_no);
				boolean infoOfrOrderPayPoTemp = infoOfrOrderPayServ.deleteInfoOfrOrderPayBySaleOrderNo(infoOfrOrderPayPo);
				if(!infoOfrOrderPayPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除商品订单收费详情表数据失败");
					return message;
				}
			}

			//商品订单费用详情表
			List<InfoOfrOrderFeePo> infoOfrOrderFeePoTempRes = (List<InfoOfrOrderFeePo>) dataMap.get("info_ofr_fee");
			if(infoOfrOrderFeePoTempRes!=null && infoOfrOrderFeePoTempRes.size()>0){
				InfoOfrOrderFeePo infoOfrOrderFeePo =new InfoOfrOrderFeePo();
				infoOfrOrderFeePo.setSale_order_no(order_no);
				boolean infoOfrOrderFeePoTemp = infoOfrOrderFeeServ.deleteInfoOfrOrderFeeBySaleOrderNo(infoOfrOrderFeePo);
				if(!infoOfrOrderFeePoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除商品订单费用详情表数据失败");
					return message;
				}
			}

			//服务订单表
			List<InfoServiceOrderPo> infoServiceOrderPoTempRes=(List<InfoServiceOrderPo>) dataMap.get("info_service_order");
			if(infoServiceOrderPoTempRes!=null && infoServiceOrderPoTempRes.size()>0){
				InfoServiceOrderPo infoServiceOrderPo =new InfoServiceOrderPo();
				infoServiceOrderPo.setSale_order_no(order_no);
				boolean infoServiceOrderPoTemp=infoServiceOrderServ.deleteInfoServiceOrderBySaleOrderNo(infoServiceOrderPo);
				if(!infoServiceOrderPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单表数据失败");
					return message;
				}
			}

			//服务订单收费详情表
			List<InfoServiceOrderPayPo> infoServiceOrderPayPoTempRes=(List<InfoServiceOrderPayPo>) dataMap.get("info_serv_pay");
			if(infoServiceOrderPayPoTempRes!=null && infoServiceOrderPayPoTempRes.size()>0){
				InfoServiceOrderPayPo infoServiceOrderPayPo =new InfoServiceOrderPayPo();
				infoServiceOrderPayPo.setSale_order_no(order_no);
				boolean infoServiceOrderPayPoTemp=infoServiceOrderPayServ.deleteInfoServiceOrderPayBySaleOrderNo(infoServiceOrderPayPo);
				if(!infoServiceOrderPayPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单收费详情表数据失败");
					return message;
				}
			}

			//服务订单费用详情表
			List<InfoServiceOrderFeePo> infoServiceOrderFeePoTempRes=(List<InfoServiceOrderFeePo>) dataMap.get("info_serv_fee");
			if(infoServiceOrderFeePoTempRes!=null && infoServiceOrderFeePoTempRes.size()>0){
				InfoServiceOrderFeePo infoServiceOrderFeePo =new InfoServiceOrderFeePo();
				infoServiceOrderFeePo.setSale_order_no(order_no);
				boolean infoServiceOrderFeePoTemp=infoServiceOrderFeeServ.deleteInfoServiceOrderFeeBySaleOrderNo(infoServiceOrderFeePo);
				if(!infoServiceOrderFeePoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单费用详情表数据失败");
					return message;
				}
			}

			//服务订单属性表
			List<InfoServiceOrderAttrPo> infoServiceOrderAttrPoTempRes=(List<InfoServiceOrderAttrPo>) dataMap.get("info_serv_attr");
			if(infoServiceOrderAttrPoTempRes!=null && infoServiceOrderAttrPoTempRes.size()>0){
				InfoServiceOrderAttrPo infoServiceOrderAttrPo =new InfoServiceOrderAttrPo();
				infoServiceOrderAttrPo.setSale_order_no(order_no);
				boolean infoServiceOrderAttrPoTemp=infoServiceOrderAttrServ.deleteInfoServiceOrderAttrBySaleOrderNo(infoServiceOrderAttrPo);
				if(!infoServiceOrderAttrPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单属性表数据失败");
					return message;
				}
			}

			//服务订单产品列表
			List<InfoServiceOrderProdMapPo> infoServiceOrderProdMapPoTempRes=(List<InfoServiceOrderProdMapPo>) dataMap.get("info_serv_prod_map");
			if(infoServiceOrderProdMapPoTempRes!=null && infoServiceOrderProdMapPoTempRes.size()>0){
				InfoServiceOrderProdMapPo infoServiceOrderProdMapPo =new InfoServiceOrderProdMapPo();
				infoServiceOrderProdMapPo.setSale_order_no(order_no);
				boolean infoServiceOrderProdMapPoTemp=infoServiceOrderProdMapServ.deleteInfoServiceOrderProdMapBySaleOrderNo(infoServiceOrderProdMapPo);
				if(!infoServiceOrderProdMapPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单产品列表数据失败");
					return message;
				}
			}

			//服务订单终端表
			List<InfoServiceOrderTerminalPo> infoServiceOrderTerminalPoTempRes=(List<InfoServiceOrderTerminalPo>) dataMap.get("info_serv_terminal");
			if(infoServiceOrderTerminalPoTempRes!=null && infoServiceOrderTerminalPoTempRes.size()>0){
				InfoServiceOrderTerminalPo infoServiceOrderTerminalPo = new InfoServiceOrderTerminalPo();
				infoServiceOrderTerminalPo.setSale_order_no(order_no);
				boolean infoServiceOrderTerminalPoTemp=infoServiceOrderTerminalServ.deleteInfoServiceOrderTerminalBySaleOrderNo(infoServiceOrderTerminalPo);
				if(!infoServiceOrderTerminalPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单终端表数据失败");
					return message;
				}
			}

			//服务订单优惠活动
			List<InfoServiceOrderActivityPo> infoServiceOrderActivityPoTempRes=(List<InfoServiceOrderActivityPo>) dataMap.get("info_serv_activity");
			if(infoServiceOrderActivityPoTempRes!=null && infoServiceOrderActivityPoTempRes.size()>0){
				InfoServiceOrderActivityPo infoServiceOrderActivityPo = new InfoServiceOrderActivityPo();
				infoServiceOrderActivityPo.setSale_order_no(order_no);
				boolean infoServiceOrderActivityPoTemp=infoServiceOrderActivityServ.deleteInfoServiceOrderActivityBySaleOrderNo(infoServiceOrderActivityPo);
				if(!infoServiceOrderActivityPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单优惠活动数据失败");
					return message;
				}
			}

			//服务订单协议表
			List<InfoServiceOrderAgreementPo> infoServiceOrderAgreementPoTempRes= (List<InfoServiceOrderAgreementPo>) dataMap.get("info_serv_agreement");
			if(infoServiceOrderAgreementPoTempRes!=null && infoServiceOrderAgreementPoTempRes.size()>0){
				InfoServiceOrderAgreementPo infoServiceOrderAgreementPo =new InfoServiceOrderAgreementPo();
				infoServiceOrderAgreementPo.setSale_order_no(order_no);
				boolean infoServiceOrderAgreementPoTemp=infoServiceOrderAgreementServ.deleteInfoServiceOrderAgreementBySaleOrderNo(infoServiceOrderAgreementPo);
				if(!infoServiceOrderAgreementPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单协议表数据失败");
					return message;
				}
			}

			//服务订单套餐列表
			List<InfoServiceOrderPackagePo> infoServiceOrderPackagePoTempRes=(List<InfoServiceOrderPackagePo>) dataMap.get("info_serv_pkg");
			if(infoServiceOrderPackagePoTempRes!=null && infoServiceOrderPackagePoTempRes.size()>0){
				InfoServiceOrderPackagePo infoServiceOrderPackagePo =new InfoServiceOrderPackagePo();
				infoServiceOrderPackagePo.setSale_order_no(order_no);
				boolean infoServiceOrderPackagePoTemp=infoServiceOrderPackageServ.deleteInfoServiceOrderPackageBySaleOrderNo(infoServiceOrderPackagePo);
				if(!infoServiceOrderPackagePoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单套餐列表数据失败");
					return message;
				}
			}

			//服务订单SIM卡信息表
			List<InfoServiceOrderSimCardPo> infoServiceOrderSimCardPoTempRes =(List<InfoServiceOrderSimCardPo>) dataMap.get("info_serv_sim_card");
			if(infoServiceOrderSimCardPoTempRes!=null && infoServiceOrderSimCardPoTempRes.size()>0){
				InfoServiceOrderSimCardPo infoServiceOrderSimCardPo = new InfoServiceOrderSimCardPo();
				infoServiceOrderSimCardPo.setSale_order_no(order_no);
				boolean infoServiceOrderSimCardPoTemp =infoServiceOrderSimCardServ.deleteInfoServiceOrderSimCardBySaleOrderNo(infoServiceOrderSimCardPo);
				if(!infoServiceOrderSimCardPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单SIM卡信息表数据失败");
					return message;
				}
			}

			//服务订单固网信息表
			List<InfoServiceOrderFixPo> infoServiceOrderFixPoTempRes=(List<InfoServiceOrderFixPo>) dataMap.get("info_serv_fix");
			if(infoServiceOrderFixPoTempRes!=null && infoServiceOrderFixPoTempRes.size()>0){
				InfoServiceOrderFixPo infoServiceOrderFixPo = new InfoServiceOrderFixPo();
				infoServiceOrderFixPo.setSale_order_no(order_no);
				boolean infoServiceOrderFixPoTemp=infoServiceOrderFixServ.deleteInfoServiceOrderFixBySaleOrderNo(infoServiceOrderFixPo);
				if(!infoServiceOrderFixPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单固网信息表数据失败");
					return message;
				}
			}

			//服务订单宽带信息表
			List<InfoServiceOrderM165Po> infoServiceOrderM165PoTempRes=(List<InfoServiceOrderM165Po>) dataMap.get("info_serv_m165");
			if(infoServiceOrderM165PoTempRes!=null && infoServiceOrderM165PoTempRes.size()>0){
				InfoServiceOrderM165Po infoServiceOrderM165Po =new InfoServiceOrderM165Po();
				infoServiceOrderM165Po.setSale_order_no(order_no);
				boolean infoServiceOrderM165PoTemp=infoServiceOrderM165Serv.deleteInfoServiceOrderM165BySaleOrderNo(infoServiceOrderM165Po);
				if(!infoServiceOrderM165PoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单宽带信息表数据失败");
					return message;
				}
			}

			//服务订单靓号信息表
			List<InfoServiceOrderGoodNbrPo> infoServiceOrderGoodNbrPoTempRes=(List<InfoServiceOrderGoodNbrPo>) dataMap.get("info_serv_good_nbr");
			if(infoServiceOrderGoodNbrPoTempRes!=null && infoServiceOrderGoodNbrPoTempRes.size()>0){
				InfoServiceOrderGoodNbrPo infoServiceOrderGoodNbrPo =new InfoServiceOrderGoodNbrPo();
				infoServiceOrderGoodNbrPo.setSale_order_no(order_no);
				boolean infoServiceOrderGoodNbrPoTemp=infoServiceOrderGoodNbrServ.deleteInfoServiceOrderGoodNbrBySaleOrderNo(infoServiceOrderGoodNbrPo);
				if(!infoServiceOrderGoodNbrPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单靓号信息表数据失败");
					return message;
				}
			}

			//服务订单银行托收表
			List<InfoServiceOrderCollectionPo> infoServiceOrderCollectionPoTempRes=(List<InfoServiceOrderCollectionPo>) dataMap.get("info_serv_collection");
			if(infoServiceOrderCollectionPoTempRes!=null && infoServiceOrderCollectionPoTempRes.size()>0){
				InfoServiceOrderCollectionPo infoServiceOrderCollectionPo = new InfoServiceOrderCollectionPo();
				infoServiceOrderCollectionPo.setSale_order_no(order_no);
				boolean infoServiceOrderCollectionPoTemp=infoServiceOrderCollectionServ.deleteInfoServiceOrderCollectionBySaleOrderNo(infoServiceOrderCollectionPo);
				if(!infoServiceOrderCollectionPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单银行托收表数据失败");
					return message;
				}
			}

			//服务订单担保人表
			List<InfoServiceOrderGuarantorPo> infoServiceOrderGuarantorPoTempRes=(List<InfoServiceOrderGuarantorPo>) dataMap.get("info_serv_guarantor");
			if(infoServiceOrderGuarantorPoTempRes!=null && infoServiceOrderGuarantorPoTempRes.size()>0){
				InfoServiceOrderGuarantorPo infoServiceOrderGuarantorPo = new InfoServiceOrderGuarantorPo();
				infoServiceOrderGuarantorPo.setSale_order_no(order_no);
				boolean infoServiceOrderGuarantorPoTemp=infoServiceOrderGuarantorServ.deleteInfoServiceOrderGuarantorBySaleOrderNo(infoServiceOrderGuarantorPo);
				if(!infoServiceOrderGuarantorPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单担保人表数据失败");
					return message;
				}
			}

			//服务订单发展人表
			List<InfoServiceOrderDeveloperPo> infoServiceOrderDeveloperPoTempRes=(List<InfoServiceOrderDeveloperPo>) dataMap.get("info_serv_developer");
			if(infoServiceOrderDeveloperPoTempRes!=null && infoServiceOrderDeveloperPoTempRes.size()>0){
				InfoServiceOrderDeveloperPo infoServiceOrderDeveloperPo = new InfoServiceOrderDeveloperPo();
				infoServiceOrderDeveloperPo.setSale_order_no(order_no);
				boolean infoServiceOrderDeveloperPoTemp=infoServiceOrderDeveloperServ.deleteInfoServiceOrderDeveloperBySaleOrderNo(infoServiceOrderDeveloperPo);
				if(!infoServiceOrderDeveloperPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单发展人表数据失败");
					return message;
				}
			}

			//服务订单个客信息表
			List<InfoServiceOrderPersionPo> infoServiceOrderPersionPoTempRes=(List<InfoServiceOrderPersionPo>) dataMap.get("info_serv_person");
			if(infoServiceOrderPersionPoTempRes!=null && infoServiceOrderPersionPoTempRes.size()>0){
				InfoServiceOrderPersionPo infoServiceOrderPersionPo = new InfoServiceOrderPersionPo();
				infoServiceOrderPersionPo.setSale_order_no(order_no);
				boolean infoServiceOrderPersionPoTemp=infoServiceOrderPersionServ.deleteInfoServiceOrderPersionBySaleOrderNo(infoServiceOrderPersionPo);
				if(!infoServiceOrderPersionPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单个客信息表数据失败");
					return message;
				}
			}

			//服务订单拓展属性横表
			List<InfoServiceOrderExtPo> infoServiceOrderExtPoTemp=(List<InfoServiceOrderExtPo>) dataMap.get("info_serv_ext");
			if(infoServiceOrderExtPoTemp!=null && infoServiceOrderExtPoTemp.size()>0){
				InfoServiceOrderExtPo infoServiceOrderExtPo= new InfoServiceOrderExtPo();
				infoServiceOrderExtPo.setSale_order_no(order_no);
				boolean infoServiceOrderExtPoFlag=infoServiceOrderExtServ.deleteInfoServiceOrderExtBySaleOrderNo(infoServiceOrderExtPo);
				if(!infoServiceOrderExtPoFlag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除服务订单拓展属性横表数据失败");
					return message;
				}
			}

			//人工任务实例表
			List<ProcInstTaskInstPo> procInstTaskInstPoTempRes=(List<ProcInstTaskInstPo>) dataMap.get("task_inst_sale");
			if(procInstTaskInstPoTempRes!=null && procInstTaskInstPoTempRes.size()>0){
				ProcInstTaskInstPo procInstTaskInstPo = new ProcInstTaskInstPo();
				procInstTaskInstPo.setOrder_no(order_no);
				procInstTaskInstPo.setOrder_type("100");
				boolean procInstTaskInstPoTemp=procInstTaskInstServ.deleteProcInstTaskInstByOrderNo(procInstTaskInstPo);
				if(!procInstTaskInstPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除人工任务实例表(销售订单)数据失败");
					return message;
				}
			}

			//人工任务操作记录
			List<ProcInstTaskDealRecordPo> procInstTaskDealRecordPoTempRes=(List<ProcInstTaskDealRecordPo>) dataMap.get("task_deal_record_sale");
			if(procInstTaskDealRecordPoTempRes!=null && procInstTaskDealRecordPoTempRes.size()>0){
				ProcInstTaskDealRecordPo procInstTaskDealRecordPo = new ProcInstTaskDealRecordPo();
				procInstTaskDealRecordPo.setOrder_no(order_no);
				boolean procInstTaskDealRecordPoTemp=procInstTaskDealRecordServ.deleteProcInstTaskDealRecordByOrderNo(procInstTaskDealRecordPo);
				if(!procInstTaskDealRecordPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除人工任务操作记录(销售订单)数据失败");
					return message;
				}
			}
			
			//撤单表
			List<InfoOrderCancelPo> InfoOrderCancelPoTempRes=(List<InfoOrderCancelPo>) dataMap.get("info_order_cancel_sale");
			if(InfoOrderCancelPoTempRes!=null && InfoOrderCancelPoTempRes.size()>0){
				InfoOrderCancelPo infoOrderCancelPo = new InfoOrderCancelPo();
				infoOrderCancelPo.setOrder_no(order_no);
				boolean infoOrderCancelPoTemp=infoOrderCancelServ.deleteByOrderNo(infoOrderCancelPo);
				if(!infoOrderCancelPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除撤单(销售订单)表失败");
					return message;
				}
			}
			
			if(infoServiceOrderPoTempRes!=null){
				String servOrderNo="";
				
				List<ProcInstTaskInstPo> procInstTaskInstPos=(List<ProcInstTaskInstPo>) dataMap.get("task_inst_serv");
				List<ProcInstTaskDealRecordPo> procInstTaskDealRecordPos=(List<ProcInstTaskDealRecordPo>) dataMap.get("task_deal_record_serv");
				List<InfoOrderCancelPo> infoOrderCancelPos=(List<InfoOrderCancelPo>) dataMap.get("info_order_cancel_serv");
				
				for(InfoServiceOrderPo poTemp:infoServiceOrderPoTempRes){
					
					servOrderNo=poTemp.getServ_order_no();
					
					// 判断服务订单是否需要删除
					boolean taskInstFlag = false;
					if (procInstTaskInstPos!=null && procInstTaskInstPos.size()>0){
						for (ProcInstTaskInstPo taskInst : procInstTaskInstPos){
							if (taskInst.getOrder_no().equals(servOrderNo)){
								taskInstFlag = true;
								
								break;
							}
						}
					}
					
					// 删除任务实例
					if (taskInstFlag){
						ProcInstTaskInstPo procInstTaskInstPo = new ProcInstTaskInstPo();
						procInstTaskInstPo.setOrder_no(servOrderNo);
						procInstTaskInstPo.setOrder_type("101");
						boolean procInstTaskInstPoTemp=procInstTaskInstServ.deleteProcInstTaskInstByOrderNo(procInstTaskInstPo);
						if(!procInstTaskInstPoTemp){
							message.setRespCode(RespCodeContents.SERVICE_FAIL);
							message.setContent("删除人工任务实例表(服务订单)数据失败");
							return message;
						}
					}
					
					// 判断服务订单是否需要删除
					boolean taskRecordFlag = false;
					if (procInstTaskDealRecordPos!=null && procInstTaskDealRecordPos.size()>0){
						for (ProcInstTaskDealRecordPo taskRecord : procInstTaskDealRecordPos){
							if (taskRecord.getOrder_no().equals(servOrderNo)){
								taskRecordFlag = true;
								
								break;
							}
						}
					}
					
					// 删除任务操作记录
					if(taskRecordFlag){
						ProcInstTaskDealRecordPo procInstTaskDealRecordPo = new ProcInstTaskDealRecordPo();
						procInstTaskDealRecordPo.setOrder_no(servOrderNo);
						boolean procInstTaskDealRecordPoTemp=procInstTaskDealRecordServ.deleteProcInstTaskDealRecordByOrderNo(procInstTaskDealRecordPo);
						if(!procInstTaskDealRecordPoTemp){
							message.setRespCode(RespCodeContents.SERVICE_FAIL);
							message.setContent("删除人工任务操作记录(服务订单)数据失败");
							return message;
						}
					}
					
					// 判断服务订单是否需要删除
					boolean orderCancelFlag = false;
					if (infoOrderCancelPos!=null && infoOrderCancelPos.size()>0){
						for (InfoOrderCancelPo infoOrderCancelPo : infoOrderCancelPos){
							if (infoOrderCancelPo.getOrder_no().equals(servOrderNo)){
								orderCancelFlag = true;
								
								break;
							}
						}
					}
					
					//删除撤单记录
					if(orderCancelFlag){
						InfoOrderCancelPo infoOrderCancelPo = new InfoOrderCancelPo();
						infoOrderCancelPo.setOrder_no(servOrderNo);
						boolean infoOrderCancelPoTemp=infoOrderCancelServ.deleteByOrderNo(infoOrderCancelPo);
						if(!infoOrderCancelPoTemp){
							message.setRespCode(RespCodeContents.SERVICE_FAIL);
							message.setContent("删除撤單表(服务订单)失败");
							return message;
						}
					}
				}
			}
			
			/*
			//task_assign_depart任务部门表
			ProcInstTaskAssignDepartPo procInstTaskAssignDepartPoTempRes=(ProcInstTaskAssignDepartPo) dataMap.get("task_assign_depart");
			if(procInstTaskAssignDepartPoTempRes!=null){
				ProcInstTaskAssignDepartPo procInstTaskAssignDepartPo=new ProcInstTaskAssignDepartPo();
				procInstTaskAssignDepartPo.setOrder_no(order_no);
				boolean procInstTaskAssignDepartPoTemp=procInstTaskAssignDepartServ.delete(procInstTaskAssignDepartPo);
				if(!procInstTaskAssignDepartPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除任务部门表数据失败");
					return message;
				}
			}
			//task_assign_oper任务工号表
			ProcInstTaskAssignOperPo procInstTaskAssignOperPoTempRes=(ProcInstTaskAssignOperPo) dataMap.get("task_assign_oper");
			if(procInstTaskAssignOperPoTempRes!=null){
				ProcInstTaskAssignOperPo procInstTaskAssignOperPo=new ProcInstTaskAssignOperPo();
				procInstTaskAssignOperPo.setOrder_no(order_no);
				boolean procInstTaskAssignOperPoTemp=procInstTaskAssignOperServ.delete(procInstTaskAssignOperPo);
				if(!procInstTaskAssignOperPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除任务工号表数据失败");
					return message;
				}
			}
			//task_assign_record任务日志表
			List<ProcInstTaskAssignRecordPo> procInstTaskAssignRecordPoTempRes=(List<ProcInstTaskAssignRecordPo>) dataMap.get("task_assign_record");
			if(procInstTaskAssignRecordPoTempRes!=null){
				ProcInstTaskAssignRecordPo procInstTaskAssignRecordPo=new ProcInstTaskAssignRecordPo();
				procInstTaskAssignRecordPo.setOrder_no(order_no);
				boolean procInstTaskAssignRecordPoTemp=procInstTaskAssignRecordServ.deleteProcTaskAssignRecordByOrderNo(procInstTaskAssignRecordPo);
				if(!procInstTaskAssignRecordPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除任务日志表数据失败");
					return message;
				}
			}
			*/
			
			//支付订单表
			InfoPayOrderPo infoPayOrderPoTempRes=(InfoPayOrderPo) dataMap.get("info_pay_order");
			if(infoPayOrderPoTempRes!=null){
				InfoPayOrderPo infoPayOrderPo = new InfoPayOrderPo();
				infoPayOrderPo.setPay_order_no(infoPayOrderPoTempRes.getPay_order_no());
				boolean infoPayOrderPoTemp=infoPayOrderServ.deleteInfoPayOrderByPayOrderNo(infoPayOrderPo);
				if(!infoPayOrderPoTemp){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("删除支付订单表数据失败");
					return message;
				}
			}
			
			//交付订单表
			List<InfoDeliverOrderPo> infoDeliverOrderPoTempRess=(List<InfoDeliverOrderPo>) dataMap.get("info_deliver_order");
			if(infoDeliverOrderPoTempRess!=null){
				for(InfoDeliverOrderPo infoDeliverOrderPoTempRes:infoDeliverOrderPoTempRess){
					InfoDeliverOrderPo infoDeliverOrderPo = new InfoDeliverOrderPo();
					infoDeliverOrderPo.setDeliver_order_no(infoDeliverOrderPoTempRes.getDeliver_order_no());
					infoDeliverOrderPo.setSale_order_no(infoDeliverOrderPoTempRes.getSale_order_no());
					boolean infoDeliverOrderPoTemp=infoDeliverOrderServ.deleteInfoDeliverOrderByPayDeliverOrderNo(infoDeliverOrderPo);
					if(!infoDeliverOrderPoTemp){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("删除交付订单表数据失败");
						return message;
					}
				}
			}
			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("备份数据删除成功");

		}else{
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("无该操作类型");
			return message;
		}
		return message;
	}
}
