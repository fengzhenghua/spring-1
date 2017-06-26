package com.tydic.unicom.upc.service.capacity.impl;

import com.tydic.unicom.upc.service.capacity.AbstractServiceClient;
import com.tydic.unicom.upc.service.capacity.CapacityConfigs;

public class QuerySubsBaseInfoClient extends AbstractServiceClient{
	
	@Override
	public String getGrand() {
		//return serviceName;
		return CapacityConfigs.GRAND;
	}

	@Override
	public String getToken() {
		return CapacityConfigs.TOKEN_QuerySubsBaseInfo;
	}

}
