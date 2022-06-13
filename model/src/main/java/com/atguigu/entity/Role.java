package com.atguigu.entity;

import lombok.Data;

@Data
public class Role extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//角色名称   
	private String roleName;
	//角色编码   
	private String roleCode;
	//描述   
	private String description;
}

