package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.uitl.IDUtils;
import com.taotao.common.uitl.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;

/**
 * 商品管理service
 * @author Andy
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	//根据商品id得到商品信息
	@Override
	public TbItem getItenById(long itemId) {
		//TbItem item=itemMapper.selectByPrimaryKey(itemId);
		TbItemExample example=new TbItemExample();
		//添加查询条件
		Criteria criteria=example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list=itemMapper.selectByExample(example);
		if(list!=null&&list.size()>0){
			TbItem item=list.get(0);
			return item;
		}
		return null;
	}
	/**
	 * 商品列表查询
	 */
	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		//商品列表查询
		TbItemExample example=new TbItemExample();
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list=itemMapper.selectByExample(example);
		//创建一个返回值对象
		EUDataGridResult result=new EUDataGridResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<TbItem> pageInfo=new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	//添加商品,商品描述，商品规格参数
	@Override
	public TaotaoResult createItem(TbItem item,String desc,String itemParam) 
			throws Exception{
		//item补全
		//生成商品id
		Long itemId=IDUtils.genItemId();
		
//		System.out.println("itemId="+itemId);
//		System.out.println("item="+item.getTitle());
//		System.out.println("desc="+desc);
//		System.out.println("itemParam="+itemParam);
		
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//插入到数据库
		itemMapper.insert(item);
		//添加商品描述信息
		TaotaoResult result=insertItemDesc(itemId,desc);
		if(result.getStatus()!=200){
			//出错抛出异常，给spring处理
			throw new Exception();
		}
		//添加商品规格参数
		result=insertItemParamItem(itemId,itemParam);
		if(result.getStatus()!=200){
			//出错抛出异常，给spring处理
			throw new Exception();
		}
		return TaotaoResult.ok();
	}
	
	//添加商品描述
	private TaotaoResult insertItemDesc(Long itemId,String desc){
		TbItemDesc itemDesc=new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		//插入到数据
		itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
	}
	
	//添加商品规格参数
	private TaotaoResult insertItemParamItem(Long itemId,String itemParam){
		//创建ItemParamItem Pojo
		TbItemParamItem itemParamItem=new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		//插入到数据库
		itemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
	}
}
