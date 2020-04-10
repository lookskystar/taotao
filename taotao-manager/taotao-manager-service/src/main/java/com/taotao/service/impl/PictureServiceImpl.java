package com.taotao.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.taotao.common.uitl.FtpUtil;
import com.taotao.common.uitl.IDUtils;
import com.taotao.service.PictureService;

/**
 * 图片上传服务
 * @author Andy
 */
@Service
public class PictureServiceImpl implements PictureService {
	//读取taotao-manager-controller/src/main/resources/
	//resource.properties资源文件
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
	

	@Override
	public Map uploadPicture(MultipartFile uploadFile) {
		Map resultMap=new HashMap<>();
		try {
			//生成一个新文件名
			//取原文件文件名
			String oldName=uploadFile.getOriginalFilename();
			//生成新文件名(可以使用UUID)
			//UUID.randomUUID();
			//这里用自定义的生成ID方式
			String newName=IDUtils.genImageName();
			newName=newName+
					oldName.substring(oldName.lastIndexOf("."));
			
			//图片上传
			String imagePath=new DateTime().toString("/yyyy/MM/dd");
			boolean result=FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT,
					FTP_USERNAME, 
					FTP_PASSWORD, FTP_BASE_PATH, imagePath, 
					newName, uploadFile.getInputStream());
			//返回结果
			if(!result){
				resultMap.put("error",1);
				resultMap.put("message","文件上传失败");
				return resultMap;
			}
			resultMap.put("error",0);
			resultMap.put("url",IMAGE_BASE_URL+imagePath+"/"+newName);
			return resultMap;
		} catch (Exception e) {
			resultMap.put("error",1);
			resultMap.put("message","文件上传发生异常");
			return resultMap;
		}
	}
}
