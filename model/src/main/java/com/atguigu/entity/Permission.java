package com.atguigu.entity;

import lombok.Data;

import java.util.List;
@Data
public class Permission extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//所属上级   
	private Long parentId;
	//权限名称   
	private String name;
	//菜单路径   
	private String url;
	//权限标识   
	private String code;
	//类型(1:菜单,2:按钮)   
	private Integer type;
	//排序
	private Integer sort;

	// 层级
	private Integer level;
	// 下级列表
	private List<Permission> children;
	//所属上级
	private String parentName;
}

