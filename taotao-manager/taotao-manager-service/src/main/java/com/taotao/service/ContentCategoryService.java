package com.taotao.service;

import java.util.List;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.uitl.TaotaoResult;

//内容分类管理
public interface ContentCategoryService {
	List<EUTreeNode> getCategoryList(long parentId);
	TaotaoResult insertContentCategory(long parentId,String name);
}
