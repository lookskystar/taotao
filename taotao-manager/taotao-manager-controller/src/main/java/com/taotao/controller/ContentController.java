package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.uitl.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

//内容管理
@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult insertContent(TbContent content) {
		//要把TbContent类属性和content-add.jsp表单对上,如果没有就单独处理
		TaotaoResult result =contentService.insertContent(content);
		return result;
	}
}
