package com.taotao.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.taotao.common.uitl.HttpClientUtil;
import com.taotao.common.uitl.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;


//内容管理
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;

	@Override
	public TaotaoResult insertContent(TbContent content) {
		//补全pojo内容，根据tb_content进行补全
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		
		//添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + 
					content.getCategoryId());
		} catch (Exception e) {
			//可以发邮件或者是短信提醒异常
			e.printStackTrace();
		}
	
		return TaotaoResult.ok();
	}
}
