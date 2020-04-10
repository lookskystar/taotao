package com.taotao.controller;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.uitl.FtpUtil;

public class FTPTest {
	@Test
	public void testFTPClient() throws Exception{
		//创建一个FTPClient对象。
		FTPClient ftpClient=new FTPClient();
		//创建ftp连接。
		ftpClient.connect("192.168.0.13",21);
		//登入FTP服务器，使用户名和密码
		ftpClient.login("andy", "12345678");
		//IO流读取上传文件
		FileInputStream inputStream=new FileInputStream(new File("E:\\test1.jpg"));
		//设置为被动模式(如上传文件夹成功，不能上传文件，注释这行，否则报错refused:connect  )
		ftpClient.enterLocalPassiveMode();
		//设置服务端上传路径
		ftpClient.changeWorkingDirectory("/home/www/vftpandy/2018/11/17");
		//修改上传文件格式
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		//上传文本
		//第一个参数：服务器文档名。第二个参数：上传文档的inputStream。
		ftpClient.storeFile("test.jpg", inputStream);
		//关闭连接
		ftpClient.logout();
	}
	
	@Test
	public void testFtpUtil()throws Exception{
		FileInputStream inputStream=new FileInputStream(new File("E:\\hello.jpg"));
		FtpUtil.uploadFile
		("192.168.0.13", 21, "andy", "12345678", 
		  "/home/www/vftpandy", "/2018/11/17", "2.jpg", 
		  inputStream);
	}
	
}
