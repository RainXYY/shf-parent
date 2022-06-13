package com.atguigu.entity;

import lombok.Data;

@Data
public class Dict extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//上级id
	private Long parentId;
	//名称
	private String name;
	//编码
	private String dictCode;
}

