package com.taotao.rest.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

//分类列表菜单json实体1
public class CatNode {
	//在转换为json数据格式后，默认key名是name，如果加上@JsonProperty("n")，key名就会成n
	@JsonProperty("n") 
	private String name;
	@JsonProperty("u")
	private String url;
	@JsonProperty("i")
	private List<?> item;//？代表任意类型
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<?> getItem() {
		return item;
	}
	public void setItem(List<?> item) {
		this.item = item;
	}
}
