package com.atguigu.entity;
import lombok.Data;

@Data
public class AdminRole extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//角色id   
	private Long roleId;
	//用户id   
	private Long adminId;
}

