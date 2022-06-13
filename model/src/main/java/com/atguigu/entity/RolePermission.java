package com.atguigu.entity;

import lombok.Data;

@Data
public class RolePermission extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//roleId   
	private Long roleId;
	//permissionId   
	private Long permissionId;
}

