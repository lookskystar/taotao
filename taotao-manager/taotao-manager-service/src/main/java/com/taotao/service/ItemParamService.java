package com.taotao.service;

import com.taotao.common.uitl.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {
	public TaotaoResult getItemParamByCid(long cid);
	public TaotaoResult insertItemParam(TbItemParam itemParam);
}
