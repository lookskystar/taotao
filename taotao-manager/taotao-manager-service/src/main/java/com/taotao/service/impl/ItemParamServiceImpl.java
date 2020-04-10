package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.uitl.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;

/**
 * 商品规格参数模板
 * @author Administrator
 */
@Service
public class ItemParamServiceImpl implements ItemParamService{
	@Autowired
	private TbItemParamMapper itemParamMapper;
	
	/**
	 * 根据cid得到商品规格参数
	 */
	@Override
	public TaotaoResult getItemParamByCid(long cid) {
		TbItemParamExample example=new TbItemParamExample();
		Criteria criteria=example.createCriteria(); 
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list=
				//itemParamMapper.selectByExample(example);
				itemParamMapper.selectByExampleWithBLOBs(example);
		//判断是否查询到结果
		if(list!=null&&list.size()>0){
			return TaotaoResult.ok(list.get(0));
		}
		return TaotaoResult.ok();
	}
	
	/**
	 * 添加商品规格参数
	 */
	@Override
	public TaotaoResult insertItemParam(TbItemParam itemParam) {
		//不全pojo
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		//插入规格参数模板表
		itemParamMapper.insert(itemParam);
		return TaotaoResult.ok();
	}

}
