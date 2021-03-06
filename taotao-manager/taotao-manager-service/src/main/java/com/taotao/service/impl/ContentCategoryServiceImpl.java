package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.uitl.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

//内容分类管理
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Autowired
	private TbContentCategoryMapper  contentCategoryMapper;

	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		//根据parentId查询节点列表
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria= example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list=contentCategoryMapper.selectByExample(example);
		
		List<EUTreeNode> resultList=new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			//创建一个节点
			EUTreeNode node=new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		//创建一个pojo
		TbContentCategory contentCategory=new TbContentCategory();
		contentCategory.setName(name);
		
		//以下设置值从tb_content_category表结构中字段的解释来
		contentCategory.setIsParent(false);//该类目是否为父类目，1为true，0为false
		contentCategory.setStatus(1); //状态。可选值:1(正常),2(删除)
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//添加记录
		contentCategoryMapper.insert(contentCategory);
		//查看父节点的isParent列是否为true，如果不是true就改为true
		TbContentCategory parentCat=contentCategoryMapper.selectByPrimaryKey(parentId);
		//判断是否为true
		if(!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		//返回结果
		return TaotaoResult.ok(contentCategory);
	}
}
