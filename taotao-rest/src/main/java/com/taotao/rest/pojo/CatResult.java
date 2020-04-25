package com.taotao.rest.pojo;

import java.util.List;

//分类列表菜单json实体2
public class CatResult {
	//这个变量名和json的根名一致，否则页面会解析不了
	private List<?> data;
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	
}
