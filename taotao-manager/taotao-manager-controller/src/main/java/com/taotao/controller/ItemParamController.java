package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.uitl.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;

/**
 * 商品规格参数模板管理Controller
 * @author Administrator
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public TaotaoResult getItemParamByCid(@PathVariable Long itemCatId){
		TaotaoResult resutl=itemParamService.getItemParamByCid(itemCatId);
		return resutl;
	}
	
	/**
	 * 添加规格参数模板
	 * @param itemCatId
	 * @param paramData
	 * @return
	 */
	@RequestMapping("/save/{itemCatId}")
	@ResponseBody
	public TaotaoResult insertItemParam
	(@PathVariable Long itemCatId,String paramData){
		//创建规格参数模板对象
		TbItemParam itemParam=new TbItemParam();
		//设置cid和json格式规格参数模板字符串到对象
		itemParam.setItemCatId(itemCatId);
		itemParam.setParamData(paramData);
		TaotaoResult taotaoResult=
				itemParamService.insertItemParam(itemParam);
		return taotaoResult;
	}
}
