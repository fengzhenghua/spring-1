package com.tydic.unicom.ugc.base.dataBase.interfaces;

import java.util.List;

import com.tydic.unicom.ugc.base.dataBase.po.GoodsDefinePo;
import com.tydic.unicom.ugc.base.dataBase.po.GoodsInfo;

public interface GoodsDefineServ {

	public List<GoodsDefinePo> queryInfoGoodsDefineByRoleId(GoodsInfo po) throws Exception;

	public List<GoodsDefinePo> queryGoodsDefinePoByPo(GoodsDefinePo po) throws Exception;

	public boolean addGoodsDefine(GoodsDefinePo po) throws Exception;

	public boolean deleteGoodsDefine(GoodsDefinePo po) throws Exception;

	public boolean updateGoodsDefine(GoodsDefinePo po) throws Exception;

	public List<GoodsDefinePo> queryGoodsDefineByPage(GoodsDefinePo po, int pageNo, int pageSize) throws Exception;

	public int queryGoodsDefineCount(GoodsDefinePo po) throws Exception;
}
