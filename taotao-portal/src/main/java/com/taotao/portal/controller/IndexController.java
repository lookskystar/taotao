package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.uitl.TaotaoResult;
import com.taotao.portal.service.ContentService;

/**
 * index控制类
 * @author Administrator
 */
@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;

	@RequestMapping("/index")
	public String showIndex(Model model){
		String adjson=contentService.getContentList();
		model.addAttribute("ad1",adjson);
		return "index";
	}
	
	@RequestMapping(value="/httpclient/post",method=RequestMethod.POST,
			        produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String testPost1(String username,String password){
		return "username:"+username+"\tpassword:"+password;
	}
	
	@RequestMapping(value="/httpclient/post",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult testPost2(String username,String password){
		String result="username:"+username+"\tpassword:"+password;
		System.out.println("result="+result);
		return TaotaoResult.ok();
	}
}
