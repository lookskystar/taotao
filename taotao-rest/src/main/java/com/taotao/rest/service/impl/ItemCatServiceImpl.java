package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

//分类列表菜单实现类
@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public CatResult getItemCatList() {
		// 用递归实现菜单的查询
		CatResult catResult = new CatResult();
		catResult.setData(getCatList(0));

		return catResult;
	}

	// 查询分类列表方法
	private List<?> getCatList(long parentId) {
		// 创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		// 返回值list
		List resultList = new ArrayList<>();
		
		//添加计数器，解决分类列数据超长问题（14条内含）
		int count=0;
		// 向list中添加节点（第一层）
		for (TbItemCat tbItemCat : list) {
			// 判断是否为父节点
			if (tbItemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				// n只有第一层加a标签，第二层n就不用加了,所以要做判断
				if (parentId == 0) {
					// 第一层
					// 从category.json中取第一个n的值,改成如下
					catNode.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + 
							tbItemCat.getName() + "</a>");
				} else {
					// 第二层，不用拼a标签
					catNode.setName(tbItemCat.getName());
				}
				// 从category.json中取第一个u的值,改成如下
				catNode.setUrl("/products/" + tbItemCat.getId() + ".html");

				// 调用递归
				catNode.setItem(getCatList(tbItemCat.getId()));
				//不是叶子节点，添加一个catNode
				resultList.add(catNode);
				
				count++;
				//第一层只取14条记录
				if(parentId == 0&&count>=14) {
					break;
				}
				
				//如果是叶子节点，添加一个字符串
			}else {
				resultList.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
			}
		}
		return resultList;
	}
}
