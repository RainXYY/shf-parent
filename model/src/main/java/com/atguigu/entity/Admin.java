package com.atguigu.entity;

import lombok.Data;

import java.util.List;
@Data
public class Admin extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//用户名   
	private String username;
	//密码   
	private String password;
	//姓名   
	private String name;
	//手机   
	private String phone;
	//头像地址   
	private String headUrl;
	//描述   
	private String description;

	private List<Role> roleList;
}

