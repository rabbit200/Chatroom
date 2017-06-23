package com.qq.regist;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class UserInformation {

	public Properties userInfomation;

	//ע��һ���û�
	public void insert(String userName, String userPassword) {
		try {
			userInfomation = new Properties();
			InputStream is;
			OutputStream os;
			is = new FileInputStream("C:/Users/uonel/Downloads/QQ/QQ/src/userInfo.properties");
			os = new FileOutputStream("C:/Users/uonel/Downloads/QQ/QQ/src/userInfo.properties", true);
			userInfomation.load(is);
			// ���û���������洢���ڴ���
			userInfomation.setProperty(userName, userPassword);
			// ���û��������뱣�浽�ļ���
			userInfomation.store(os, null);

		} catch (FileNotFoundException e1) {
			System.out.println("�ļ�userInfo.propertiesû���ҵ� ");
		} catch (IOException e) {
			System.out.println("д userInfo.properties ����");
		}
	}

	//�жϴ��û����Ƿ����
	public boolean isExist(String userName) {
		try {
			userInfomation = new Properties();
			InputStream is;
			is = new FileInputStream("C:/Users/uonel/Downloads/QQ/QQ/src/userInfo.properties");
			userInfomation.load(is);
			if (userInfomation.containsKey(userName)) {
				return true;
			}

		} catch (FileNotFoundException e1) {
			System.out.println("�ļ�userInfo.propertiesû���ҵ� ");
		} catch (IOException e) {
			System.out.println("д userInfo.properties ����");
		}
		return false;
	}

}
